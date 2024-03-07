package com.ph.bittelasia.meshtv.tv.santagrand.Netflix.View.Activity;

import android.content.Context;
import android.content.Intent;
import android.util.Log;


import com.ph.bittelasia.meshtv.tv.santagrand.Core.Control.ReportAnalyticsService;
import com.ph.bittelasia.meshtv.tv.santagrand.Core.Model.AppOtherList;
import com.ph.bittelasia.meshtv.tv.santagrand.Core.Control.GetAPIContentTask;
import com.ph.bittelasia.meshtv.tv.santagrand.Netflix.View.Fragment.TheQuayNetflixLoadingFragment;
import com.ph.bittelasia.meshtv.tv.santagrand.R;
import com.ph.bittelasia.meshtv.tv.santagrand.Room.View.App.TheQuay;
import com.ph.bittelasia.packages.Core.Model.AppPackageLoadSetting;
import com.ph.bittelasia.packages.Core.Service.AppWifiIntentService;
import com.ph.bittelasia.packages.Core.View.Activity.LoaderActivity;

import java.util.Timer;
import java.util.TimerTask;


public class TheQuayNetflix extends LoaderActivity {

    //======================================Variable================================================
    //--------------------------------------Constant------------------------------------------------
    private static final String TAG = TheQuayNetflix.class.getSimpleName();
    //----------------------------------------------------------------------------------------------
    //--------------------------------------Instance------------------------------------------------
    public static Timer              timerAppOtherAnalytic = null;
    //----------------------------------------------------------------------------------------------
    //==============================================================================================


    //=====================================LoaderActivity===========================================
    //----------------------------------------------------------------------------------------------

    @Override
    public int setLayout() {
        return R.layout.sg_netflix_layout;
    }

    @Override
    public void setEvents() {
        doAPPAnalytics(this,AppOtherList.NETFLIX.getAppID());
    }

    @Override
    public void isLaunched(boolean b) {
        Log.i(TAG,"@isLaunched: "+b);
    }

    @Override
    public void onPackageLoadSetting(AppPackageLoadSetting setting) {
        try {
            TheQuayNetflixLoadingFragment fragment = new TheQuayNetflixLoadingFragment();
            fragment.setIndex(setting.getGifID());
            fragmentTransaction(R.id.ll_container, fragment, false);
        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void getCounter(int counter) {
        try {
            if (counter >= 60) {
                try {
                    Intent intent = new Intent();
                    intent.setClass(this, AppWifiIntentService.class);
                    intent.putExtra(AppWifiIntentService.TAG_TURN_ON, false);
                    startService(intent);

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }catch (Exception e)
        {
            e.printStackTrace();
        }
        Log.e(TAG,"@getCounter: "+counter );
    }

    //----------------------------------------------------------------------------------------------
    //==============================================================================================

    //========================================Method================================================
    //----------------------------------------------------------------------------------------------
    /**
     * Method to send analytics by clicking the app
     * @param appID in int assigned from the server
     */
    public static void doAPPAnalytics(final Context context, final int appID)
    {
        try {
            stopAnalytics();
            timerAppOtherAnalytic = new Timer();
            TimerTask task = new TimerTask() {
                @Override
                public void run() {
                        Intent intentReport = new Intent();
                        intentReport.setClass(context, ReportAnalyticsService.class);
                        intentReport.putExtra(ReportAnalyticsService.REPORT, GetAPIContentTask.API_OTHERS_ANALYTICS);
                        intentReport.putExtra(ReportAnalyticsService.APPID, appID + "");
                        intentReport.putExtra(ReportAnalyticsService.ISTICK, true);
                        intentReport.putExtra(ReportAnalyticsService.DEVICEID, TheQuay.app.getDevice_id());
                        context.startService(intentReport);
                        timerAppOtherAnalytic.cancel();
                        timerAppOtherAnalytic.purge();
                }
            };
            timerAppOtherAnalytic.schedule(task, 1000);
        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    /**
     * Method to stop the timerAppAnalytic for analytics
     */

    public static void stopAnalytics()
    {
        try {
            if (timerAppOtherAnalytic != null) {
                timerAppOtherAnalytic.purge();
                timerAppOtherAnalytic.cancel();
            }
        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    //----------------------------------------------------------------------------------------------
    //==============================================================================================

}
