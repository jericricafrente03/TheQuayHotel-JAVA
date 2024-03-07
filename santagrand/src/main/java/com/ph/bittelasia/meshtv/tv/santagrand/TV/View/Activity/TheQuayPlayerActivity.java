package com.ph.bittelasia.meshtv.tv.santagrand.TV.View.Activity;

import android.content.Intent;
import android.os.Bundle;

import android.util.Log;
import android.view.KeyEvent;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.ph.bittelasia.meshtv.tv.mtvlib.Core.Control.Annotation.View.Activity.ActivitySetting;
import com.ph.bittelasia.meshtv.tv.mtvlib.Core.Control.Annotation.View.General.Layout;
import com.ph.bittelasia.meshtv.tv.mtvlib.Core.View.Activity.MeshTVActivity;
import com.ph.bittelasia.meshtv.tv.mtvlib.Entity.Control.Listener.MeshChannelListener;
import com.ph.bittelasia.meshtv.tv.mtvlib.Entity.Model.Channel.MeshChannel;
import com.ph.bittelasia.meshtv.tv.santagrand.R;
import com.ph.bittelasia.meshtv.tv.santagrand.Room.Control.TheQuayDAOManager;
import com.ph.bittelasia.meshtv.tv.santagrand.TV.View.Fragment.TheQuayPlayerLoadingFragment;
import com.ph.bittelasia.meshtv.tv.santagrand.TV.View.Fragment.TheQuayPlayerViewFragment;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

@SuppressWarnings("deprecation")
@Layout(R.layout.sg_player_activity)
@ActivitySetting()
public class TheQuayPlayerActivity extends MeshTVActivity implements TheQuayPlayerViewFragment.OnEndCallBack , MeshChannelListener<MeshChannel> {


    //===================================Variable===================================================
    //-----------------------------------Constant---------------------------------------------------
    public  static final String   TAG   = TheQuayPlayerActivity.class.getSimpleName();
    public  static final String   URI   = "channel_uri";
    public  static final String   TITLE = "channel_title";
    public  static final String   ID    = "channel_id";
    //-------------------------------------View-----------------------------------------------------
    private TextView                      tv_channel;
    //-----------------------------------Instance---------------------------------------------------
    private TheQuayPlayerLoadingFragment  loadingFragment;
    public  TheQuayPlayerViewFragment     playerFragment;
    private String                        channelUri;
    private String                        channelTitle;
    private Timer                         playAgaintTimer;
    private int                           counter =0;
    private int                           channelID;
    private boolean                       isPlaying =false;
    private ArrayList<MeshChannel>        channels;
    private ArrayList<Integer>            channelIDs;
    private int                           currentID;
    //----------------------------------------------------------------------------------------------
    //==============================================================================================



    //=====================================Life Cycle===============================================
    //----------------------------------------------------------------------------------------------
    @Override
    public boolean dispatchKeyEvent(KeyEvent event)
    {
        try {
            switch (event.getAction()) {
                case KeyEvent.ACTION_DOWN:
                    switch (event.getKeyCode()) {
                        case KeyEvent.KEYCODE_BACK:
                            return true;
                        case KeyEvent.KEYCODE_PAGE_UP:
                        case KeyEvent.KEYCODE_CHANNEL_UP:
                            Log.e(TAG, event.getKeyCode() + " up");
                            return true;
                        case KeyEvent.KEYCODE_PAGE_DOWN:
                        case KeyEvent.KEYCODE_CHANNEL_DOWN:
                            Log.e(TAG, event.getKeyCode() + "down");
                            return true;
                    }
                    break;
                case KeyEvent.ACTION_UP:
                    switch (event.getKeyCode()) {
                        case KeyEvent.KEYCODE_BACK:
                            super.onBackPressed();
                            return true;
                        case KeyEvent.KEYCODE_PAGE_UP:
                        case KeyEvent.KEYCODE_CHANNEL_UP:
                            Log.e(TAG, event.getKeyCode() + " up");
                            setChannel(true);
                            return true;
                        case KeyEvent.KEYCODE_PAGE_DOWN:
                        case KeyEvent.KEYCODE_CHANNEL_DOWN:
                            Log.e(TAG, event.getKeyCode() + "down");
                            setChannel(false);
                            return true;
                    }
                    break;
            }
        }catch (Exception e)
        {
            e.printStackTrace();
        }
        return super.dispatchKeyEvent(event);
    }

    @Override
    protected void onStart() {
        try {
            super.onStart();
            TheQuayDAOManager.get().addListener(this);
            TheQuayDAOManager.get().retrieve(MeshChannel.class);
        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    @Override
    protected void onStop() {
        try {
            super.onStop();
            TheQuayDAOManager.get().removeListener(this);
        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        try {
            channelTitle = getIntent().getStringExtra(TITLE) + "";
            channelUri = getIntent().getStringExtra(URI) + "";
            channelID  = getIntent().getIntExtra(ID,-1);
            tv_channel = findViewById(R.id.tv_channel);
            loadingFragment = new TheQuayPlayerLoadingFragment();
            startPlay(channelUri,channelID);
           // startPlay("udp://@238.0.0.5:6010",1);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    //----------------------------------------------------------------------------------------------
    //==============================================================================================
    //======================================Method==================================================
    //----------------------------------------------------------------------------------------------
    private void startPlay(String  uri,int channelID){
        try
        {
            playerFragment = new TheQuayPlayerViewFragment();
            playerFragment.setUrl(uri);
            playerFragment.setChannelID(channelID);
            getFragmentManager().beginTransaction().replace(R.id.player, playerFragment, "player").commitAllowingStateLoss();
            if(loadingFragment!=null)
               getSupportFragmentManager().beginTransaction().replace(R.id.loader, loadingFragment, "loader").commitAllowingStateLoss();
            else {
                loadingFragment = new TheQuayPlayerLoadingFragment();
                getSupportFragmentManager().beginTransaction().replace(R.id.loader, loadingFragment, "loader").commitAllowingStateLoss();
            }
            if(tv_channel!=null)
              tv_channel.setText(channelTitle);
        }
        catch (Exception e)
        {

        }
    }
    //----------------------------------------------------------------------------------------------
    //==============================================================================================

    //===================================TheQuayPlayerViewFragment.OnEndCallBack====================
    //----------------------------------------------------------------------------------------------
    @Override
    public void playerLoad() {
        try {
            getSupportFragmentManager().beginTransaction().remove(loadingFragment).commitAllowingStateLoss();
        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    @Override
    public void playerStart() {
        try {
            isPlaying = true;
            if (playAgaintTimer != null) {
                playAgaintTimer.cancel();
                playAgaintTimer.purge();
            }
        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void playerStopped(String path) {
        try {
            getFragmentManager().beginTransaction().remove(playerFragment).commitAllowingStateLoss();
            getSupportFragmentManager().beginTransaction().show(loadingFragment).commitAllowingStateLoss();
            isPlaying = false;
            counter = 0;
            if(playAgaintTimer!=null)
            {
                playAgaintTimer.cancel();
                playAgaintTimer.purge();
            }
            playAgaintTimer = new Timer();
            TimerTask task = new TimerTask() {
                @Override
                public void run() {
                    if(isPlaying)
                    {
                        playAgaintTimer.cancel();
                        playAgaintTimer.purge();
                    }
                    if(counter == 0)
                    {
                        if(path!=null)
                           startPlay(path,channelID);
                        else
                            Log.e(TAG,"@playerStopped: path is null");
                        counter++;
                    }
                }
            };
            playAgaintTimer.schedule(task, 1000);
            Log.e(TAG,"@playerStopped: "+path);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    //----------------------------------------------------------------------------------------------
    //==============================================================================================


    @Override
    public void onInsertedBulk(ArrayList arrayList) {
        try {
            Log.i(TAG, "@onInsertedBulk");
            if (arrayList != null && arrayList.size() > 0)
                if (arrayList.get(0) instanceof MeshChannel)
                    for (int x = 0; x < arrayList.size(); x++) {
                        if (channelID == ((MeshChannel) arrayList.get(x)).getId()) {
                            Intent intent = new Intent(this, TheQuayPlayerActivity.class);
                            intent.putExtra(TheQuayPlayerActivity.URI, ((MeshChannel) arrayList.get(x)).getChannel_uri());
                            intent.putExtra(TheQuayPlayerActivity.TITLE, ((MeshChannel) arrayList.get(x)).getChannel_title());
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivityForResult(intent, 100);
                            channelID = ((MeshChannel) arrayList.get(x)).getId();
                            startPlay(((MeshChannel) arrayList.get(x)).getChannel_uri(), channelID);
                            break;
                        }
                    }
        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }


    @Override
    public void onDeletedBulk(ArrayList arrayList) {
        Log.i(TAG,"@onDeletedBulk");
    }

    @Override
    public void onRetrieved(ArrayList arrayList) {
        try {
            if (arrayList.size() > 0) {
                if (arrayList.get(0) instanceof MeshChannel) {
                    channels = new ArrayList<>();
                    channelIDs = new ArrayList<>();
                    channelIDs.clear();
                    channels.clear();
                    for (int x = 0; x < arrayList.size(); x++) {
                        channelIDs.add(((MeshChannel) arrayList.get(x)).getId());
                        channels.add(((MeshChannel) arrayList.get(x)));
                        if (channelID == ((MeshChannel) arrayList.get(x)).getId()) {
                            currentID = x;
                        }
                    }
                    Log.i(TAG, "@onRetrieved: size of MeshChannel =" + arrayList.size());

                }
            }
        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void onCleared(Class aClass) {
        Log.i(TAG,"@onCleared");
    }

    @Override
    public void onDAONotFound(String s) {
        Log.i(TAG,"@onDAONotFound");
    }


    public void setChannel(boolean isPageUp)
    {
        try {
            if (channelIDs != null) {
                if (isPageUp) {
                    if (currentID < channelIDs.size())
                        currentID++;
                    if (currentID >= channelIDs.size())
                        currentID = 0;
                } else {

                    if (currentID >= 0)
                        currentID--;
                    if (currentID < 0)
                        currentID = channelIDs.size()-1;
                }
                if (channels != null) {
                    if (channels.size() > 0) {
                        channelID = channels.get(currentID).getId();
                        startPlay(channels.get(currentID).getChannel_uri(), channelID);
                        if(tv_channel!=null)
                        {
                            tv_channel.setText(channels.get(currentID).getChannel_title());
                        }
                        Log.i(TAG,"@channel id: "+channelID);
                    }
                }

            }
        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
