package com.ph.bittelasia.meshtv.tv.santagrand.Core.View.Activity;

import android.app.Activity;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.provider.Settings;

import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;


import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.ph.bittelasia.meshtv.tv.meshxmpplibrary.Core.Control.Manager.MeshXMPPManager;
import com.ph.bittelasia.meshtv.tv.mtvlib.Core.Control.Annotation.View.Activity.AttachFragment;
import com.ph.bittelasia.meshtv.tv.mtvlib.Core.Control.Preference.Control.Manager.MeshTVPreferenceManager;
import com.ph.bittelasia.meshtv.tv.mtvlib.Core.Control.Util.RemoteControl.KR301KeyCode;
import com.ph.bittelasia.meshtv.tv.mtvlib.Core.View.Activity.MeshTVActivity;

import com.ph.bittelasia.meshtv.tv.mtvlib.Entity.Control.RealtimeManager.MeshRealtimeManager;
import com.ph.bittelasia.meshtv.tv.santagrand.Airmedia.Model.HotspotManager;
import com.ph.bittelasia.meshtv.tv.santagrand.Airmedia.View.Activity.TheQuayAirMedia;
import com.ph.bittelasia.meshtv.tv.santagrand.Core.Control.ReportAnalyticsService;
import com.ph.bittelasia.meshtv.tv.santagrand.Core.Model.AppMainList;
import com.ph.bittelasia.meshtv.tv.santagrand.Core.Control.ConnectionService;
import com.ph.bittelasia.meshtv.tv.santagrand.Core.Control.GetAPIContentTask;
import com.ph.bittelasia.meshtv.tv.santagrand.Core.View.Fragment.TheQuayMessageFragment;
import com.ph.bittelasia.meshtv.tv.santagrand.DateChangerService;
import com.ph.bittelasia.meshtv.tv.santagrand.Explore.View.Activity.TheQuayExplore;
import com.ph.bittelasia.meshtv.tv.santagrand.Info.View.Activity.TheQuayInfo;
import com.ph.bittelasia.meshtv.tv.santagrand.Message.View.Activity.TheQuayMessage;
import com.ph.bittelasia.meshtv.tv.santagrand.R;
import com.ph.bittelasia.meshtv.tv.santagrand.Room.View.App.TheQuay;
import com.ph.bittelasia.meshtv.tv.santagrand.Room.View.Fragment.Core.TheQuayGuestFragment;
import com.ph.bittelasia.meshtv.tv.santagrand.Room.View.Fragment.Core.TheQuayTabFragment;
import com.ph.bittelasia.meshtv.tv.santagrand.Room.View.Fragment.Core.TheQuayWeatherFragment;
import com.ph.bittelasia.meshtv.tv.santagrand.TV.View.Activity.TheQuayTV;
import com.ph.bittelasia.meshtv.tv.santagrand.Weather.View.Activity.TheQuayWeather;
import com.ph.bittelasia.packages.Core.Service.AppWifiIntentService;

import org.jivesoftware.smack.ConnectionListener;
import org.jivesoftware.smack.XMPPConnection;

import java.util.Timer;
import java.util.TimerTask;

public abstract class TheQuayActivity extends MeshTVActivity implements ConnectionListener, TheQuayGuestFragment.GuestProfileCallBack, TheQuayMessageFragment.MessageCallBack
{
    //==========================================Variable============================================
    //------------------------------------------Constant--------------------------------------------
    public static final String        TAG = TheQuayActivity.class.getSimpleName();
    public static TheQuayActivity     activity;

    //----------------------------------------------------------------------------------------------
    //------------------------------------------Fragment--------------------------------------------
    private TheQuayGuestFragment   guestFragment;
    public  TheQuayMessageFragment messageFragment;
    private TheQuayWeatherFragment weatherFragment;
    private TheQuayTabFragment     tabFragment;
    //----------------------------------------------------------------------------------------------
    //------------------------------------------Instance--------------------------------------------
    private  boolean           isPingSent  = false;
    public   boolean           isConnected = false;
    public   boolean           isTabMode   = false;
    public   boolean           isHotspotOn = false;
    public   boolean           isRestarted = false;
    public   HotspotManager    hotspotManager;
    public   Timer             timerAppAnalytic = null;
    //----------------------------------------------------------------------------------------------
    //==============================================================================================

    //==========================================LifeCycle===========================================
    //----------------------------------------------------------------------------------------------
    @Override
    protected void onStart() {
        super.onStart();
        try
        {
            Intent i = getPackageManager().getLaunchIntentForPackage("com.ph.bittelasia.tv.meshtvsystemapp");
            startActivity(i);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        try
        {
            activity = this;
            if (hotspotManager == null)
                hotspotManager = new HotspotManager(getApplicationContext());
            if (!isRestarted)
            {
                if (getActivityName() != null)
                {
                    if (getActivityName().equals(TheQuayTV.TAG))
                        doAPPAnalytics(AppMainList.TV.getAppID());
                    else if (getActivityName().equals(TheQuayAirMedia.TAG))
                        doAPPAnalytics(AppMainList.AIRMEDIA.getAppID());
                    else if (getActivityName().equals(TheQuayInfo.TAG))
                        doAPPAnalytics(AppMainList.FACILITIES.getAppID());
                    else if (getActivityName().equals(TheQuayExplore.TAG))
                        doAPPAnalytics(AppMainList.VIRTUAL_CONCIERGE.getAppID());
                    else if (getActivityName().equals(TheQuayWeather.TAG))
                        doAPPAnalytics(AppMainList.WEATHER.getAppID());
                    else if (getActivityName().equals(TheQuayMessage.TAG))
                        doAPPAnalytics(AppMainList.MESSAGING.getAppID());
                }
            }
            if (isRestarted)
            {
                isPingSent = true;
            }
            isRestarted = false;
            Log.i(TAG, "@onStart");
        }catch (Exception e)
        {
            e.printStackTrace();
        }
        checkSystemWritePermission();
    }

    @Override
    protected void onRestart() {
        super.onRestart();

        isConnected = false;
        isRestarted = true;

        //Todo: Uncomment this if you want to install it in Lan Connection
//        WifiManager wifiManager = (WifiManager)getApplicationContext().getSystemService(Context.WIFI_SERVICE);
//        wifiManager.setWifiEnabled(false);


//
//
//        Intent intentStart = new Intent();
//        intentStart.setClass(this, ConnectionService.class);
//        this.startService(intentStart);

    }

    @Override
    public void onResume() {
        super.onResume();
        checkMessage();
        disableWifi();
        if(getActivityName().equals(TheQuayAirMedia.TAG))
        {
            // TODO: 7/3/2019 uncomment
            /**
             * Todo: Uncomment if you want to users to connect to hotspot
            */

//            if(hotspotManager!=null)
//            {
//                if(!hotspotManager.isApOn() && !isHotspotOn) {
//                    hotspotManager.createNewNetwork(MeshTVPreferenceManager.getRoom(this) + "", "12345678");
//                    isHotspotOn =true;
//                }
//
//                Log.i(TAG, "hotspot: is on? " + hotspotManager.isApOn());
//            }



            /**Todo: comment this if and uncomment the hotspot
            Intent intent =  new Intent(this, AppWifiIntentService.class);
            intent.putExtra(AppWifiIntentService.TAG_TURN_ON,false);
            startService(intent);
             **/
        }else{
            if(hotspotManager!=null)
               hotspotManager.turnWifiApOff();
        }

        Intent intentStart = new Intent();
        intentStart.setClass(this, DateChangerService.class);
        startService(intentStart);

        weatherFragment.updateDisplay();

    }
    //----------------------------------------------------------------------------------------------
    //==============================================================================================


    //=======================================Fragment===============================================
    //----------------------------------------------------------------------------------------------
    @AttachFragment(container = R.id.ll_guest,tag = "guest",order=1)
    public Fragment attachGuestFragment()
    {
        guestFragment=new TheQuayGuestFragment();
        return  guestFragment;
    }

    @AttachFragment(container = R.id.ll_weather,tag="location",order = 2)
    public Fragment attachHotelWeather()
    {
        weatherFragment = new TheQuayWeatherFragment();
        return weatherFragment;
    }


    @AttachFragment(container = R.id.ll_badge,tag = "message",order=4)
    public Fragment attachNotificationFragment()
    {
        messageFragment = new TheQuayMessageFragment();
        messageFragment.isXMPPConnected(isConnected);
        return  messageFragment;
    }
    //----------------------------------------------------------------------------------------------
    //==============================================================================================

    //==========================================Navigation==========================================
    //----------------------------------------------------------------------------------------------
    @Override
    public boolean dispatchKeyEvent(KeyEvent event)
    {
        switch (KR301KeyCode.getEquivalent(event.getKeyCode()))
        {
            case KeyEvent.KEYCODE_DPAD_LEFT:
                if(event.getAction()==KeyEvent.ACTION_UP)
                {
                    if(isTabMode)
                    {
                        if(tabFragment!=null)
                        {
                            tabFragment.moveLeft();
                            return false;
                        }
                    }
                }

                return super.dispatchKeyEvent(event);

            case KeyEvent.KEYCODE_DPAD_RIGHT:
                if(event.getAction()==KeyEvent.ACTION_UP)
                {
                    if(isTabMode)
                    {
                        if(tabFragment!=null)
                        {
                            tabFragment.moveRight();
                            return false;
                        }
                    }
                }

                return super.dispatchKeyEvent(event);


            case KeyEvent.KEYCODE_DPAD_CENTER:
                if(event.getAction()==KeyEvent.ACTION_UP)
                {
                    if(isTabMode)
                    {
                        Log.i(TAG,"(MVRS 03-12-19) TABMODE ON\n");
                        if(tabFragment!=null)
                        {
                            Log.i(TAG,"(MVRS 03-12-19) TAB FOUND\n");
                            tabFragment.ok();
                            return false;
                        }
                        else
                        {
                            Log.i(TAG,"(MVRS 03-12-19) TAB NOT FOUND\n");
                        }
                    }
                    else
                    {
                        Log.i(TAG,"(MVRS 03-12-19) TABMODE OFF\n");
                    }
                }
                return super.dispatchKeyEvent(event);
        }

        return super.dispatchKeyEvent(event);
    }
    //----------------------------------------------------------------------------------------------
    //==============================================================================================

    //=================================MessageCallBack==============================================
    //----------------------------------------------------------------------------------------------
    @Override
    public void count(int c) {
       if(messageFragment!=null)
       {
           messageFragment.checkMessage(c);
       }
    }
    //----------------------------------------------------------------------------------------------
    //==============================================================================================
    //==================================ConnectionListener==========================================
    //----------------------------------------------------------------------------------------------
    @Override
    public void connected(XMPPConnection connection) {
        try {
            Log.i(TAG, "@connected: " + connection.isConnected());
            isConnected = connection.isConnected();
            if(messageFragment!=null)
            {
                messageFragment.isXMPPConnected(isConnected);
            }
            if(guestFragment!=null)
            {
                if(guestFragment.tv_sgguest.getText().toString().isEmpty())
                {
                    guestFragment.requestData();
                    if(messageFragment!=null)
                        messageFragment.requestData();
                }
            }
            if (isConnected && isPingSent) {
                MeshXMPPManager.sendMessage("meshtv", MeshRealtimeManager.REQ_PING);
                isPingSent = false;
                Log.e(TAG,"@connected: isPing");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void authenticated(XMPPConnection connection, boolean resumed) {
        Log.i(TAG,"@authenticated: isConnnected: "+connection.isConnected());
    }

    @Override
    public void connectionClosed() {
        Log.e(TAG,"@connectionClosed");
    }

    @Override
    public void connectionClosedOnError(Exception e) {
        Log.e(TAG,"@connectionClosedOnError");
    }

    @Override
    public void reconnectionSuccessful() {
        Log.i(TAG,"@reconnectionSuccessful");
    }

    @Override
    public void reconnectingIn(int seconds) {
        Log.i(TAG,"@reconnectingIn");
    }

    @Override
    public void reconnectionFailed(Exception e) {
        Log.e(TAG,"@reconnectionFailed");
    }
    //----------------------------------------------------------------------------------------------
    //==============================================================================================

    //======================================GuestProfileCallBack====================================
    //----------------------------------------------------------------------------------------------
    @Override
    public void getName(String name) {
        TheQuay.app.setProfileListener(() -> name);
    }
    //----------------------------------------------------------------------------------------------
    //==============================================================================================

    //==========================================Method==============================================
    //----------------------------------------------------------------------------------------------
    public TheQuayTabFragment getTabFragment() {
        return tabFragment;
    }
    /**
     * Method to disable wifi if on
     */
    @SuppressWarnings("ConstantConditions")
    private void disableWifi()
    {
        try {
            WifiManager wifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
            if (wifiManager.isWifiEnabled()) {
                Intent intent = new Intent();
                intent.setClass(getApplicationContext(), AppWifiIntentService.class);
                intent.putExtra(AppWifiIntentService.TAG_TURN_ON, false);
                getApplicationContext().startService(intent);
            }
        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    /**
     * Checking for permission
     */
    private boolean checkSystemWritePermission() {
        boolean retVal = true;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            retVal = Settings.System.canWrite(this);
            Log.d(TAG, "Can Write Settings: " + retVal);
            if(retVal){
                Log.i(TAG,"Write Allowed");
            }else{
                Log.i(TAG,"Write Not Allowed");
                openAndroidPermissionsMenu();
            }
        }
        return retVal;
    }

    /**
     * Start an actvity to allow app for permission
     */
    private void openAndroidPermissionsMenu() {
        Intent intent = new Intent(Settings.ACTION_MANAGE_WRITE_SETTINGS);
        intent.setData(Uri.parse("package:" + TheQuayActivity.this.getPackageName()));
        startActivity(intent);
    }

    public void checkMessage()
    {

    }
    /**
     * Method to Add Fragment
     * @param layout_id is the id of fragment
     * @param fragment is {@link Fragment Fragment} to be displayed
     * @param addToBackStack is boolean if allow to add in a backstack
     */
    public final void addFragmentTransaction(int layout_id, Fragment fragment, boolean addToBackStack)
    {
        try {
            if (!fragment.isAdded()) {
                FragmentTransaction fg = this.getSupportFragmentManager().beginTransaction();
                if (addToBackStack)
                    fg.add(layout_id, fragment, fg.getClass().getSimpleName()).addToBackStack(null).commitAllowingStateLoss();
                else
                    fg.add(layout_id, fragment, fg.getClass().getSimpleName()).commitAllowingStateLoss();
                hideKeyboard(this);
            }
        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    /**
     * Method to Remove Fragment
     * @param fragment is the existing fragment
     */
    public final void removeFragmentTransaction(Fragment fragment)
    {
        try {
            if (fragment != null)
                getSupportFragmentManager().beginTransaction().remove(fragment).commitAllowingStateLoss();
        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    /**
     *
     * @param id id of the fragment container
     * @param fragment is Fragment
     * @param TAG is String TAG to assign
     */
    public void replaceFragment(int id,Fragment fragment,String TAG)
    {
        try {
            getSupportFragmentManager().beginTransaction().replace(id, fragment, TAG).commitAllowingStateLoss();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    /**
     * Method to automatically hide the keyboard when the activity starts
     * @param activity is {@link Activity Activity} to assign
     */

    private void hideKeyboard(Activity activity) {
        InputMethodManager inputManager = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        View currentFocusedView = activity.getCurrentFocus();
        if (currentFocusedView != null) {
            inputManager.hideSoftInputFromWindow(currentFocusedView.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    /**
     * Method to send analytics by clicking the app
     * @param appID in int assigned from the server
     */

    public void doAPPAnalytics(final int appID)
    {
        stopAnalytics();
        timerAppAnalytic =new Timer();
        TimerTask task=new TimerTask() {
            @Override
            public void run() {

                Intent intentReport = new Intent();
                intentReport.setClass(TheQuayActivity.this, ReportAnalyticsService.class);
                intentReport.putExtra(ReportAnalyticsService.REPORT,GetAPIContentTask.API_TV_APP_ANALYTICS);
                intentReport.putExtra(ReportAnalyticsService.APPID,appID+"");
                intentReport.putExtra(ReportAnalyticsService.ISTICK, true);
                intentReport.putExtra(ReportAnalyticsService.DEVICEID, TheQuay.app.getDevice_id());
                startService(intentReport);
                timerAppAnalytic.cancel();
                timerAppAnalytic.purge();
            }
        };
        timerAppAnalytic.schedule(task, 1000);
    }

    /**
     * Method to stop the timerAppAnalytic for analytics
     */

    public void stopAnalytics()
    {
        try
        {
            if (timerAppAnalytic != null)
            {
                timerAppAnalytic.purge();
                timerAppAnalytic.cancel();
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    //----------------------------------------------------------------------------------------------
    //==============================================================================================

    //=========================================AbstractMethod=======================================
    //----------------------------------------------------------------------------------------------
    public abstract String getActivityName();
    //----------------------------------------------------------------------------------------------
    //==============================================================================================
}
