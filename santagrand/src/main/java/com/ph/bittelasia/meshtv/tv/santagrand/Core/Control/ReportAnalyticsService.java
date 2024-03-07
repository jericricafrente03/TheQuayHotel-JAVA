package com.ph.bittelasia.meshtv.tv.santagrand.Core.Control;

import android.app.IntentService;
import android.content.Intent;

import android.util.Log;

import androidx.annotation.Nullable;

public class ReportAnalyticsService extends IntentService {

    //====================================Variable==================================================
    //------------------------------------Constant--------------------------------------------------
    private static final String TAG          = ReportAnalyticsService.class.getSimpleName();
    public  static final String REPORT       = "REPORT";
    public  static final String APPID        = "APPID";
    public  static final String DEVICEID     = "DEVICEID";
    public  static final String CHANNELID    = "CHANNELID";
    public  static final String ISTICK       = "ISTICK";
    //------------------------------------Instance--------------------------------------------------
    private GetAPIContentTask task;
    //----------------------------------------------------------------------------------------------
    //==============================================================================================


    //==================================Constructor=================================================
    //----------------------------------------------------------------------------------------------
    public ReportAnalyticsService()
    {
        super(TAG);
    }
    //----------------------------------------------------------------------------------------------
    //==============================================================================================


    //====================================IntentService=============================================
    //----------------------------------------------------------------------------------------------
    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        try {

            String  report      = intent.getStringExtra(REPORT)+"";
            String  appID       = intent.getStringExtra(APPID)+"";
            String  deviceID    = intent.getStringExtra(DEVICEID)+"";
            String  channelID   = intent.getStringExtra(CHANNELID)+"";
            boolean isTick      = intent.getBooleanExtra(ISTICK,false);

            task =   new GetAPIContentTask(this);

            if(isTick)
                if(report.equals(GetAPIContentTask.API_TV_APP_ANALYTICS))
                {
                    if (appID.isEmpty()) {
                        Log.e(TAG, "@onHandleIntent -> appID is empty");
                    }
                    if (deviceID.isEmpty()) {
                        Log.e(TAG, "@onHandleIntent -> deviceID is empty");
                    }
                    task.setApi(report);
                    task.setAppID(appID);
                    task.setDeviceID(deviceID);
                    task.setTick(1);
                }else if(report.equals(GetAPIContentTask.API_TV_CH_ANALYTICS)) {
                    if (channelID.isEmpty()) {
                        Log.e(TAG, "@onHandleIntent -> channelID is empty");
                    }
                    if (deviceID.isEmpty()) {
                        Log.e(TAG, "@onHandleIntent -> deviceID is empty");
                    }
                    task.setApi(report);
                    task.setChannelID(channelID);
                    task.setDeviceID(deviceID);
                    task.setTick(1);
                }
                else if(report.equals(GetAPIContentTask.API_OTHERS_ANALYTICS))
                {
                    if (appID.isEmpty()) {
                        Log.e(TAG, "@onHandleIntent -> appID is empty");
                    }
                    if (deviceID.isEmpty()) {
                        Log.e(TAG, "@onHandleIntent -> deviceID is empty");
                    }
                    task.setApi(report);
                    task.setOtherAppID(appID);
                    task.setDeviceID(deviceID);
                    task.setTick(1);
                }
             else
                Log.e(TAG,"@onHandleIntent -> isTick is false");
            task.execute();
        }catch (Exception e)
        {
            Log.e(TAG,"@onHandleIntent->"+e.getMessage());
            e.printStackTrace();
        }
    }
    //----------------------------------------------------------------------------------------------
    //==============================================================================================
}
