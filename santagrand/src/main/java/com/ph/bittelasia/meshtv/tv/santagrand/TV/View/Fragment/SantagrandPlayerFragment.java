package com.ph.bittelasia.meshtv.tv.santagrand.TV.View.Fragment;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.MediaController;
import android.widget.VideoView;

import androidx.annotation.Nullable;

import com.ph.bittelasia.meshtv.tv.santagrand.Core.Control.GetAPIContentTask;
import com.ph.bittelasia.meshtv.tv.santagrand.Core.Control.ReportAnalyticsService;
import com.ph.bittelasia.meshtv.tv.santagrand.R;
import com.ph.bittelasia.meshtv.tv.santagrand.Room.View.App.TheQuay;

import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class SantagrandPlayerFragment extends Fragment {

    //=============================Variable=========================================================
    //-----------------------------Constant---------------------------------------------------------
    private static final String TAG = "video-steward";
    //-----------------------------Instance---------------------------------------------------------
    private ScheduledExecutorService mScheduledExecutorService;
    private OnEndCallBack cb;
    private int mCurrentposition = 0;
    private int counter = 0;
    private Timer loadTimer;
    private Timer  analyticsTimer;
    private String channelUri;
    private String channelTitle;
    private int   channelID;
    //-----------------------------View-------------------------------------------------------------
    private VideoView vv_video;
    //----------------------------Final-------------------------------------------------------------
    Runnable updateCurrentPosition = new Runnable(){
        @Override
        public void run() {
            if(vv_video!=null)
            {
                mCurrentposition = vv_video.getCurrentPosition();
                Log.d(TAG,  Calendar.getInstance().getTime()+ "; position -> "+mCurrentposition + ",  uri = "+getUri()+ ", title = "+getChannelTitle());
                if(mCurrentposition==0)
                {
                    Log.e(TAG,  Calendar.getInstance().getTime()+ "; position -> "+mCurrentposition + ",  uri = "+getUri()+ ", title = "+getChannelTitle());
                    counter++;
                    if(counter>=3)
                        if(cb!=null)
                        {
                            cb.playerStopped(null);
                        }
                }
                else
                {
                    counter=0;
                    if(cb!=null)
                    {
                        cb.playerLoad();
                    }
                    vv_video.start();
                }
            }
        }
    };
    //----------------------------------------------------------------------------------------------
    //==============================================================================================


    //=======================================LifeCycle==============================================
    //----------------------------------------------------------------------------------------------
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.video_layout2,container,false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        try {
            super.onViewCreated(view, savedInstanceState);
            vv_video = view.findViewById(R.id.vv_video);
            MediaController mediaController = new MediaController(getActivity());
            mediaController.setAnchorView(vv_video);
            mScheduledExecutorService = new ScheduledThreadPoolExecutor(1);
            mScheduledExecutorService.scheduleWithFixedDelay(new Runnable() {
                @Override
                public void run() {
                    vv_video.post(updateCurrentPosition);
                }
            }, 3000, 1000, TimeUnit.MILLISECONDS);
            Uri uri = Uri.parse(getUri());
            vv_video.setVideoURI(uri);
            vv_video.requestFocus();
            vv_video.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    mp.setLooping(true);
                    Log.i(TAG, "isPlaying() -> " + mp.isPlaying());
                }
            });
            vv_video.start();

            if (loadTimer != null) {
                loadTimer.cancel();
                loadTimer.purge();
                loadTimer = null;
            }
            loadTimer = new Timer();

            TimerTask task = new TimerTask() {
                @Override
                public void run() {
                    if (counter < 3) {
                        stopAnalytics();
                    }
                }
            };
            loadTimer.schedule(task, 3000);
            doChannelAnalytics(view.getContext(), getChannelID());
        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        try {
            if (vv_video.isPlaying()) {
                if(cb!=null)
                    cb.playerStart();
            } else {
                if(cb!=null)
                    cb.playerStopped(getUri());
            }
        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    //----------------------------------------------------------------------------------------------
    //==============================================================================================


    //==================================Method======================================================
    //----------------------------------------------------------------------------------------------

    /**
     * Method to set the title of the Video
     * @param title is String
     */
    public void setTitle(String title) {
        this.channelTitle = title;
    }
    /**
     * Method to set the id of Video
     * @param id is int
     */
    public void setId(int id) {
        this.channelID = id;
    }
    /**
     * Method to set the URI of Video
     * @param uri is String
     */
    public void setChannelUri(String uri) {
        this.channelUri = uri;
    }

    /**
     * Method to set CallBack
     * @param cb is OnEndCallBack
     */
    public void setCb(OnEndCallBack cb) {
        this.cb = cb;
    }

    /**
     * Method to get id of the video
     * @return int
     */
    public int getChannelID() {
        return channelID;
    }

    /**
     * Method to get the URI of the Video
     * @return String
     */
    public String getUri() {
        return channelUri + "";
    }
    /**
     * Method to get the title of the Video
     * @return String
     */
    public String getChannelTitle() {
        return channelTitle + "";
    }
    /**
     * Method to send analytics by clicking the app
     * @param c is Context
     * @param channelID in int id of channel
     */

    public void doChannelAnalytics(Context c, final int channelID)
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
                        Log.i("video-steward","@doChannelAnalytics: channel id: "+channelID);
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
    //----------------------------------------------------------------------------------------------
    //==============================================================================================

    //=====================================CallBack=================================================
    //----------------------------------------------------------------------------------------------
    public interface OnEndCallBack{
        void playerLoad();
        void playerStart();
        void playerStopped(String path);
    }
    //----------------------------------------------------------------------------------------------
    //==============================================================================================
}
