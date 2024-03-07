package com.ph.bittelasia.meshtv.tv.santagrand.TV.View.Fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.ph.bittelasia.Views.PlayerFragment;
import com.ph.bittelasia.meshtv.tv.santagrand.Core.Control.ReportAnalyticsService;
import com.ph.bittelasia.meshtv.tv.santagrand.Core.Control.GetAPIContentTask;
import com.ph.bittelasia.meshtv.tv.santagrand.Room.View.App.TheQuay;

import java.util.Timer;
import java.util.TimerTask;

public class TheQuayPlayerViewFragment extends PlayerFragment {

    //=======================================Variable===============================================
    //---------------------------------------Instance-----------------------------------------------
    public static final String TAG = TheQuayPlayerViewFragment.class.getSimpleName();
    //---------------------------------------Constant-----------------------------------------------
    private OnEndCallBack cb;
    private Timer  loadTimer;
    private Timer  analyticsTimer;
	private String url;
    private int    channelID;
	private int    counter=0;
	//----------------------------------------------------------------------------------------------
    //==============================================================================================



    //======================================LifeCycle===============================================
    //----------------------------------------------------------------------------------------------



    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        cb=(OnEndCallBack)context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        cb=null;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        try {
            counter=0;
            if (cb != null) {
                cb.playerStart();
            }
            if(loadTimer!=null)
            {
                loadTimer.cancel();
                loadTimer.purge();
                loadTimer =null;
            }
            loadTimer      = new Timer();

            TimerTask task = new TimerTask() {
                @Override
                public void run() {
                    if (counter < 3) {
                        stopAnalytics();
                        onEnd();
                    }
                }
            };
            loadTimer.schedule(task, 3000);
            doChannelAnalytics(view.getContext(),getChannelID());
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    //----------------------------------------------------------------------------------------------
    //==============================================================================================

    //====================================PlayerFragment============================================
    //----------------------------------------------------------------------------------------------
    @Override
    public String getPath() {
        return getUrl();
    }

    @Override
    public void onBufferChanged(float v) {

    }

    @Override
    public void onLoadComplete() {

    }


    @Override
    public void onChanging() {
        try {
            counter++;
            Log.i(TAG, "counter: " + counter);
            if(counter==4)
            {
                if(cb!=null)
                {
                    cb.playerLoad();
                }
            }
            if (counter > 10)
            {
                if (loadTimer != null)
                {
                    loadTimer.cancel();
                    loadTimer.purge();
                    loadTimer =null;
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            Log.e(TAG,e.getMessage()+"");
        }
    }

    @Override
    public void onError(String s) {
        try {
            if (cb != null) {
                cb.playerStopped(getUrl());
                Log.i(TAG, "onError: " + s);
            }
        }catch (Exception e)
        {
            e.printStackTrace();
        }

    }

    @Override
    public void onEnd() {
        try {
            if (cb != null) {
                cb.playerStopped(getUrl());
                Log.e(TAG, "@onEnd");
            }
        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    //----------------------------------------------------------------------------------------------
    //==============================================================================================



    //========================================Method================================================
    //----------------------------------------------------------------------------------------------
    public void setUrl(String url) {
        this.url = url;
    }

    public void setChannelID(int channelID) {
        this.channelID = channelID;
    }

    public String getUrl() {
        return url;
    }

    public int getChannelID() {
        return channelID;
    }

    //----------------------------------------------------------------------------------------------
    /**
     * Method to send analytics by clicking the app
     * @param c is Context
     * @param channelID in int id of channel
     */

    public void doChannelAnalytics(Context c,final int channelID)
    {
        try {
            stopAnalytics();
            analyticsTimer = new Timer();
            if (c != null) {
                TimerTask task = new TimerTask() {
                    @Override
                    public void run() {

                        Intent intentReport = new Intent();
                        intentReport.setClass(c, ReportAnalyticsService.class);
                        intentReport.putExtra(ReportAnalyticsService.REPORT, GetAPIContentTask.API_TV_CH_ANALYTICS);
                        intentReport.putExtra(ReportAnalyticsService.CHANNELID, channelID + "");
                        intentReport.putExtra(ReportAnalyticsService.ISTICK, true);
                        intentReport.putExtra(ReportAnalyticsService.DEVICEID, TheQuay.app.getDevice_id());
                        c.startService(intentReport);
                        analyticsTimer.cancel();
                        analyticsTimer.purge();
                        analyticsTimer=null;
                        Log.i(TAG,"@doChannelAnalytics: channel id: "+channelID);
                    }
                };
                analyticsTimer.schedule(task, 30000);
            }
        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    /**
     * Method to stop the timerAppAnalytic for analytics
     */

    public void stopAnalytics()
    {
        try {
            if (analyticsTimer != null) {
                analyticsTimer.purge();
                analyticsTimer.cancel();
                analyticsTimer = null;
            }
        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    //==============================================================================================


    //==============================CallBack========================================================
    //----------------------------------------------------------------------------------------------
    public interface OnEndCallBack{
        void playerLoad();
        void playerStart();
        void playerStopped(String path);
    }
    //----------------------------------------------------------------------------------------------
    //==============================================================================================
}
