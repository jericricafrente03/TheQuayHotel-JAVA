package com.ph.bittelasia.meshtv.tv.santagrand.Room.View.App;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;

import android.util.Log;

import androidx.multidex.MultiDexApplication;

import com.ph.bittelasia.meshtv.tv.meshxmpplibrary.Core.Control.Manager.MeshXMPPManager;
import com.ph.bittelasia.meshtv.tv.meshxmpplibrary.Core.Control.Preference.MeshXMPPPreference;
import com.ph.bittelasia.meshtv.tv.meshxmpplibrary.Core.Model.MeshXMPPUser;
import com.ph.bittelasia.meshtv.tv.meshxmpplibrary.DataListener.MeshTVMessageListener;
import com.ph.bittelasia.meshtv.tv.mtvlib.Config.ConfigAPI.ConfirmUpdateTask;
import com.ph.bittelasia.meshtv.tv.mtvlib.Config.ConfigManager;
import com.ph.bittelasia.meshtv.tv.mtvlib.Config.ConfigUpdateListener.ConfigUpdateListener;
import com.ph.bittelasia.meshtv.tv.mtvlib.Config.FileDownloader.FileDownloaderTask;
import com.ph.bittelasia.meshtv.tv.mtvlib.Config.Preference.ConfigPreference;
import com.ph.bittelasia.meshtv.tv.mtvlib.Core.Control.API.Listeners.MeshRequestListener;
import com.ph.bittelasia.meshtv.tv.mtvlib.Core.Control.API.POST.MeshRegisterTask;
import com.ph.bittelasia.meshtv.tv.mtvlib.Core.Control.App.MeshTVAppManager;
import com.ph.bittelasia.meshtv.tv.mtvlib.Core.Control.Preference.Control.Manager.MeshTVPreferenceManager;
import com.ph.bittelasia.meshtv.tv.mtvlib.Core.Control.Util.FileReader.MeshTVFileManager;
import com.ph.bittelasia.meshtv.tv.mtvlib.Entity.Control.API.GET.Request.MeshGetConfig;
import com.ph.bittelasia.meshtv.tv.mtvlib.Entity.Control.API.GET.Request.MeshGetCustomer;
import com.ph.bittelasia.meshtv.tv.mtvlib.Entity.Control.API.Manager.MeshAPIManager;
import com.ph.bittelasia.meshtv.tv.mtvlib.Entity.Control.RealtimeManager.MeshRealtimeManager;
import com.ph.bittelasia.meshtv.tv.santagrand.Core.Model.AppOtherList;
import com.ph.bittelasia.meshtv.tv.santagrand.Core.Control.ConnectionService;
import com.ph.bittelasia.meshtv.tv.santagrand.Core.Control.GetAPIContentTask;
import com.ph.bittelasia.meshtv.tv.santagrand.Core.Control.GuestProfileListener;
import com.ph.bittelasia.meshtv.tv.santagrand.Core.Control.PostAPIContentTask;
import com.ph.bittelasia.meshtv.tv.santagrand.DateChangerService;
import com.ph.bittelasia.meshtv.tv.santagrand.Netflix.Model.NetFlixSettings;
import com.ph.bittelasia.meshtv.tv.santagrand.Room.Control.TheQuayDAOManager;
import com.ph.bittelasia.meshtv.tv.santagrand.Room.Control.TheQuayManager;
import com.ph.bittelasia.meshtv.tv.santagrand.Room.Control.TheQuayRealTimeManager;
import com.ph.bittelasia.tv.systemappinterface.Broadcaster.SystemBroadcaster;

import org.jivesoftware.smack.ConnectionListener;
import org.jivesoftware.smack.XMPPConnection;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;


public class TheQuay extends MultiDexApplication implements ConnectionListener, MeshTVMessageListener, MeshRequestListener, ConfigUpdateListener
{
    //===========================================Variable===========================================
    //-------------------------------------------Constant-------------------------------------------
    public static final String TAG = TheQuay.class.getSimpleName()+"Application";
    //----------------------------------------------------------------------------------------------
    //---------------------------------------------Static-------------------------------------------
    public static TheQuay app;
    //----------------------------------------------------------------------------------------------
    //---------------------------------------------Instance-----------------------------------------
    private ConnectionListener    connectionListener;
    private GuestProfileListener  profileListener;
    private MeshRegisterTask      registerTask;
    private String                deviceID;
    public  boolean               isRegistered=false;
    //---------------------------------------------------------------------------------------------
    //==============================================================================================
    //=============================================LifeCycle========================================
    //----------------------------------------------------------------------------------------------
    @Override
    public void onCreate()
    {
        super.onCreate();
        try
        {
            updateHome();
            app = this;
            connectionListener = this;
            MeshXMPPManager.setListener(this);
            TheQuayDAOManager.get().init();

            MeshTVPreferenceManager.updateIPTV(this);
            registerTask = new MeshRegisterTask(this, this);
            registerTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, null);
            postAppOtherList();
            ConfigManager.get().addUpdateListener(this);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        ConfigManager.get().clearUpdateListener();
    }

    //==============================================================================================
    //========================================XMPPConnectionListener================================
    //----------------------------------------------------------------------------------------------
    @Override
    public void connected(XMPPConnection connection)
    {
        Log.i(TAG,"(WVRS-03-11-19) Connected to XMPP\n");
    }

    @Override
    public void authenticated(XMPPConnection connection, boolean resumed)
    {
        try
        {
            Log.i(TAG, "@authenticated");
            if (connection.isConnected())
            {

                MeshXMPPManager.sendMessage("mars2",MeshTVPreferenceManager.getRoom(getApplicationContext())+" connected");

                if (profileListener != null)
                {
                    if (profileListener.getProfile() == null)
                    {
                        MeshAPIManager.get().request(this, new MeshGetCustomer(), true);
                        MeshAPIManager.get().request(this,new MeshGetConfig(),true);
                    }
                }
                if (registerTask != null)
                {
                    if (!isRegistered)
                    {
                        registerTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, null);
                        postAppOtherList();
                    }
                }
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void connectionClosed()
    {
        Log.e(TAG,"@connectionClosed");
    }

    @Override
    public void connectionClosedOnError(Exception e)
    {
        Log.e(TAG,"@connectionClosedOnError");
    }

    @Override
    public void reconnectionSuccessful()
    {
        Log.i(TAG,"@reconnectionSuccessful");
    }

    @Override
    public void reconnectingIn(int seconds)
    {
        Log.i(TAG,"@reconnectingIn");
    }

    @Override
    public void reconnectionFailed(Exception e)
    {
        Log.e(TAG,"@reconnectionFailed");
    }
    //----------------------------------------------------------------------------------------------
    //==============================================================================================
    //=======================================MeshTVMessageListener==================================
    //----------------------------------------------------------------------------------------------
    @Override
    public void receiveMessage(String s, String s1)
    {
        try
        {
            Log.i(TAG, "(WVRS-03-11-19) Received:(" + s + "): " + s1 + "\n");
            if(s1.startsWith("config_update "))
            {
                Log.i(TAG, "(WVR) CONFIG_UPDATE DETECTED\n");
                s1 = s1.replace("config_update ","");
                ConfigManager.get().update(getApplicationContext(), new ConfirmUpdateTask.Listener() {
                    @Override
                    public void onSuccess(String s)
                    {

                    }

                    @Override
                    public void onFailed(String s) {
                        Log.i(TAG, "(WVR) CONFIG_UPDATE FAILED:"+s+"\n");
                    }
                }, s1);
            }
            else if(s1.startsWith("DL_BITTEL"))
            {
                s1 = s1.replace("DL_BITTEL ","");
                new FileDownloaderTask
                        (
                                getApplicationContext(),
                                new FileDownloaderTask.Listener()
                                {
                                    @Override
                                    public void onDownloadResult(File file, String s)
                                    {
                                        if(file!=null)
                                        {
                                            Log.i(TAG,s+" was downloaded!\n");
                                        }
                                        else
                                        {
                                            Log.i(TAG,s+" was not downloaded!\n");
                                        }
                                    }
                                },s1).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,null);
            }
            else
            {
                boolean isNetflix = NetFlixSettings.isNetflix(s1);
                if(isNetflix)
                {
                    NetFlixSettings.getSetting(this,s1);
                }
                if (s1.equals(MeshRealtimeManager.REQ_PING))
                {
                    GetAPIContentTask task =   new GetAPIContentTask(this, GetAPIContentTask.API_DEVICE_STATUS);
                    if(profileListener!=null)
                    {
                        if(profileListener.getProfile()!=null)
                        {
                            task.setGuest(profileListener.getProfile());
                        }
                    }
                    task.execute();
                    return;
                }
                if (!TheQuayRealTimeManager.get().request(getBaseContext(), s, s1))
                {
                    Log.i(TAG, "(WVRS-03-11-19) Message Received:(" + s + "): " + s1 + "\n");
                }
            }
        }
        catch (Exception e)
        {
           e.printStackTrace();
        }
    }
    //----------------------------------------------------------------------------------------------
    //==============================================================================================
    //======================================MeshRequestListener=====================================
    //----------------------------------------------------------------------------------------------
    @Override
    public void onFailed(String s)
    {
        Log.i(TAG, "(WVRS) Registration Failed:" + s + "\n");
    }

    @Override
    public void onResult(String s)
    {
        try
        {
            Log.i(TAG, "(WVRS) Registered:" + s + "\n");
            if(s.contains("failed"))
            {
                Intent intentStart = new Intent();
                intentStart.setClass(TheQuay.this, ConnectionService.class);
                startService(intentStart);
                isRegistered=false;
            }
            else
            {
                TheQuayManager.get().seekUpdates();
                new XMPPConnectTask(connectionListener).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, null);
                deviceID = new JSONObject(s).getString("device_id") + "";
                isRegistered = true;
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpdated(String s, String s1)
    {
        try {
            Log.i(TAG, "(WVR) UPDATED: " + s + "(" + s1 + ")" + "\n");
            if (s.equals("home.txt")) {
                Log.i(TAG, "(WVR) CHECKING VERSION OF: " + getPackageName() + "\n");
                if (!ConfigManager.get().isUpdated(getApplicationContext(), getPackageName())) {
                    Log.i(TAG, "(WVR) APP IS NOT UPDATED\n");
                    new FileDownloaderTask
                            (
                                    getApplicationContext(),
                                    new FileDownloaderTask.Listener() {
                                        @Override
                                        public void onDownloadResult(File file, String s) {
                                            if (s.equals("iptv.apk")) {

                                                Log.i(TAG, "(WVR) IPTV APK Detetcted: " + getPackageName() + "\n");

                                                try {
                                                    Log.i(TAG, "(WVR) Launching loading screen\n");
                                                    Intent i = getPackageManager().getLaunchIntentForPackage("com.ph.bittelasia.tv.santagrandloading");
                                                    startActivity(i);
                                                    Log.i(TAG, "(WVR) Loading screen launched\n");
                                                } catch (Exception e) {
                                                    e.printStackTrace();
                                                }

                                                Log.i(TAG, "(WVR) Broadcasted system message\n");
                                                SystemBroadcaster.reinstall(getApplicationContext(), "iptv.apk", "com.ph.bittelasia.meshtv.tv.santagrand");

                                            } else {
                                                Log.i(TAG, "(WVR) APK Detetcted: " + s + "\n");
                                            }
                                        }
                                    },
                                    "iptv.apk").executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, null);
                } else {
                    Log.i(TAG, "(WVR) APP IS UPDATED\n");
                }
            }
        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    //----------------------------------------------------------------------------------------------
    //==============================================================================================
    //=============================================XMPP=============================================
    public class XMPPConnectTask extends AsyncTask<Void,Void,Void>
    {
        ConnectionListener listener;

        public XMPPConnectTask(ConnectionListener listener)
        {
            this.listener = listener;
        }

        @Override
        protected Void doInBackground(Void... voids)
        {
            try {

                MeshXMPPPreference.enablePLAIN(getApplicationContext(), true);
                MeshXMPPPreference.enableDIGESTMD5(getApplicationContext(), true);
                MeshXMPPPreference.enableSCRAMSHA1(getApplicationContext(), true);
                MeshXMPPPreference.setShouldReport(getApplicationContext(), true);
                MeshXMPPPreference.setReportTo(getApplicationContext(), "mars2");
                MeshXMPPUser user =
                        new MeshXMPPUser
                                (
                                        MeshTVPreferenceManager.getXUsername(getApplicationContext()),
                                        MeshTVPreferenceManager.getPassword(getApplicationContext()),
                                        true,
                                        "mars2"
                                );
                user.setDomain("localhost");
                user.setHost(MeshTVPreferenceManager.getXMPPHost(getApplicationContext()));
                user.setPort(MeshTVPreferenceManager.getXMPPPort(getApplicationContext()));
                MeshXMPPManager.connect(getApplicationContext(), listener, user);
            }catch (Exception e)
            {
                e.printStackTrace();
            }

            return null;
        }
    }
    //----------------------------------------------------------------------------------------------
    //==============================================================================================


    //====================================Method====================================================
    //----------------------------------------------------------------------------------------------
    public void setProfileListener(GuestProfileListener profileListener) {
        this.profileListener = profileListener;
    }
    //----------------------------------------------------------------------------------------------
    public String getDevice_id() {
        return deviceID;
    }
    //----------------------------------------------------------------------------------------------
    public void postAppOtherList()
    {
        try {
            PostAPIContentTask       postContextTask  = new PostAPIContentTask(this);
            ArrayList<JSONObject>      otherAppObjects = new ArrayList<>();

            otherAppObjects.add(PostAPIContentTask.otherAppObject(AppOtherList.NETFLIX, true));
            otherAppObjects.add(PostAPIContentTask.otherAppObject(AppOtherList.FACEBOOK, true));
            otherAppObjects.add(PostAPIContentTask.otherAppObject(AppOtherList.YOUTUBE, true));
            otherAppObjects.add(PostAPIContentTask.otherAppObject(AppOtherList.TWITTER, true));

            postContextTask.setApi("other_app_response");
            postContextTask.setObjects(otherAppObjects);
            postContextTask.setOnPostResult((s)-> Log.i(TAG,"@postAppOtherList -> "+s));
            postContextTask.execute();

        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    private void updateHome()
    {
        try {
            if (ConfigPreference.getLastUpdate(getApplicationContext(), "home.txt") == null) {
                new CheckHomeConfig().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, null);
            }
        }catch (Exception e)
        {
            e.printStackTrace();
        }

    }
    //----------------------------------------------------------------------------------------------
    //==============================================================================================
    //==========================================HomeUpdater=========================================
    public class CheckHomeConfig extends AsyncTask<Void,Void,Void>
    {
        @Override
        protected Void doInBackground(Void... voids)
        {
            try {

                if (MeshTVFileManager.isExisting("home.txt")) {
                    try {
                        JSONObject o = new JSONObject(MeshTVFileManager.readFile("home.txt"));
                        if (o.has("updated_at")) {
                            ConfigPreference.setLastUpdate(getApplicationContext(), "home.txt", o.getString("updated_at"));
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }catch (Exception e)
            {
                e.printStackTrace();
            }

            return null;
        }


        @Override
        protected void onPostExecute(Void aVoid)
        {
            super.onPostExecute(aVoid);
        }
    }
    //==============================================================================================
}
