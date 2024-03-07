package com.ph.bittelasia.meshtv.tv.santagrand.Launcher.View.Activity;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;

import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;


import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.ph.bittelasia.meshtv.tv.maintenance.Control.MeshControl;
import com.ph.bittelasia.meshtv.tv.mtvlib.AirMedia.Control.MeshTVAirmediaControl;
import com.ph.bittelasia.meshtv.tv.mtvlib.Core.Control.Annotation.View.Activity.ActivitySetting;
import com.ph.bittelasia.meshtv.tv.mtvlib.Core.Control.Annotation.View.Activity.AttachFragment;
import com.ph.bittelasia.meshtv.tv.mtvlib.Core.Control.Annotation.View.General.Layout;
import com.ph.bittelasia.meshtv.tv.mtvlib.Core.Control.Listener.BroadcastListeners.MeshTVAirmediaListener;
import com.ph.bittelasia.meshtv.tv.mtvlib.Core.Control.Preference.Control.Manager.MeshTVPreferenceManager;
import com.ph.bittelasia.meshtv.tv.mtvlib.Core.Control.Util.RemoteControl.KR301KeyCode;
import com.ph.bittelasia.meshtv.tv.mtvlib.PackageManager.Control.MeshTVGenericPackageListener;
import com.ph.bittelasia.meshtv.tv.mtvlib.PackageManager.Control.MeshTVPackageManager;
import com.ph.bittelasia.meshtv.tv.santagrand.Core.View.Activity.TheQuayActivity;
import com.ph.bittelasia.meshtv.tv.santagrand.Launcher.View.Fragment.TheQuayAppListFragment;
import com.ph.bittelasia.meshtv.tv.santagrand.Room.View.Fragment.Core.TheQuayLogoFragment;
import com.ph.bittelasia.meshtv.tv.santagrand.R;

import org.jivesoftware.smack.XMPPConnection;

@Layout(R.layout.sg_activity_launcher)
@ActivitySetting()
public class TheQuayHotelLauncher extends TheQuayActivity implements MeshTVGenericPackageListener
{
    //===========================================Variable===========================================
    //-------------------------------------------Constant-------------------------------------------
    public static final String TAG = TheQuayHotelLauncher.class.getSimpleName();
    public static TheQuayHotelLauncher launcher;
    //----------------------------------------------------------------------------------------------
    //-------------------------------------------Instance-------------------------------------------

    //----------------------------------------------------------------------------------------------
    //-------------------------------------------Fragment-------------------------------------------
    private TheQuayLogoFragment    logoFragment;
    private TheQuayAppListFragment appListFragment;
    private BroadcastReceiver      receiver;
    //----------------------------------------------------------------------------------------------
    //==============================================================================================
    //===========================================Fragment===========================================
    @AttachFragment(container = R.id.rl_logo,tag = "logo",order=3)
    public Fragment attachLogoFragment()
    {
        logoFragment = new TheQuayLogoFragment();
        return logoFragment;
    }
    //==============================================================================================
    //=========================================AttachFragment=======================================
    //----------------------------------------------------------------------------------------------
    @AttachFragment(container =  R.id.ll_apps,tag="applist",order = 1)
    public Fragment attachAppList()
    {
        appListFragment = new TheQuayAppListFragment();
        return appListFragment;
    }
    //----------------------------------------------------------------------------------------------
    //==============================================================================================
    //======================================TheQuayActivity=========================================
    //----------------------------------------------------------------------------------------------
    @Override
    public String getActivityName() {
        return TAG;
    }
    @Override
    public void connected(XMPPConnection connection)
    {
        super.connected(connection);
        try
        {
           if( connection.isConnected())
           {
               if(logoFragment!=null)
               {
                   logoFragment.requestData();
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
    //=========================================LifeCycle============================================
    //----------------------------------------------------------------------------------------------
    @Override
    protected void onStart()
    {
        super.onStart();
        launcher=this;
//        Intent i = new Intent();
//        i.setComponent(new ComponentName("com.ph.bittelasia.meshtv.tv.wifimanager", "com.ph.bittelasia.meshtv.tv.wifimanager.TimeUpdateService"));
//        i.putExtra("baseUrl", MeshTVPreferenceManager.getHTTPHost(this) + ":" + MeshTVPreferenceManager.getHTTPPort(this));
//        activity.startService(i);
        Log.i(TAG,"host : "+MeshTVPreferenceManager.getHTTPHost(this) + ":" + MeshTVPreferenceManager.getHTTPPort(this));
    }
    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState)
    {
        try {
            super.onPostCreate(savedInstanceState);
            Button send = findViewById(R.id.send);
            send.setOnClickListener
                    (v ->
                            {
                                try {
                                    final Intent intent = new Intent();
                                    intent.setAction("android.intent.action.INSTALL");
                                    intent.putExtra("filename", Environment.getExternalStorageDirectory().getPath() + "/android/airmedia.apk");
                                    intent.addFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);
                                    intent.setComponent(new ComponentName("com.ph.bittelasia.maintananceapp", "com.ph.bittelasia.maintananceapp.MaintainanceReceiver"));
                                    sendBroadcast(intent);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                    );
        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    @Override
    public void onResume()
    {
        super.onResume();
        try
        {
            receiver = MeshTVPackageManager.listen(this, this);
            MeshControl.unInstall(this,"com.waxrain.airplaydmr");
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    @Override
    public void onPause()
    {
        try {
            super.onPause();
            MeshTVPackageManager.ignore(this, receiver);
        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    //----------------------------------------------------------------------------------------------
    //==============================================================================================
    //=====================================MeshTVGenericPackageListener=============================
    //----------------------------------------------------------------------------------------------
    @Override
    public void onInstalled(String s) {
        Log.i(TAG, "@onInstalled: "+s);
    }

    @Override
    public void onUninstalled(String s) {
        Log.i(TAG, "@onUninstalled: "+s);
    }
    //----------------------------------------------------------------------------------------------
    //==============================================================================================

    //=================================MessageCallBack==============================================
    //----------------------------------------------------------------------------------------------
    @Override
    public void count(int c)
    {
        try
        {
            if (appListFragment != null)
            {
                appListFragment.getNotif(c);
            }
            messageFragment.launcherOpen=true;
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    //----------------------------------------------------------------------------------------------
    //==============================================================================================

}
