package com.ph.bittelasia.meshtv.tv.santagrand.TV.View.Activity;

import android.app.Activity;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;

import android.util.Log;
import android.view.KeyEvent;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.ph.bittelasia.meshtv.tv.santagrand.R;
import com.ph.bittelasia.meshtv.tv.santagrand.TV.View.Fragment.SantagrandLoadingFragment;
import com.ph.bittelasia.meshtv.tv.santagrand.TV.View.Fragment.SantagrandPlayerFragment;

import java.util.List;

import eu.chainfire.libsuperuser.Shell;

public class SantagrandPlayerActivity extends Activity implements  SantagrandPlayerFragment.OnEndCallBack {

    //=========================================Variable=============================================
   //-----------------------------------------Constant---------------------------------------------
    private static final String TAG = SantagrandPlayerActivity.class.getSimpleName();
    public static final String URI = "channel_uri";
    public static final String TITLE = "channel_title";
    public static final String ID = "channel_id";
    public static SantagrandPlayerActivity app;
    //----------------------------------------Instance----------------------------------------------
    private boolean mIsReceiverRegistered = false;
    private String channelUri = null;
    private String channelTitle = null;
    private String suVersion = null;
    private String suVersionInternal = null;
    private int    channelID = 0;
    private boolean suAvailable = false;
    private List<String> suResult = null;
    //----------------------------------------------------------------------------------------------
    //------------------------------------------Views-----------------------------------------------
    private TextView tv_channel;
    private SantagrandPlayerFragment videoFragment;
    private SantagrandLoadingFragment loadingFragment;
//----------------------------------------------------------------------------------------------
//==============================================================================================

    //=====================================OnEndCallBack============================================
//----------------------------------------------------------------------------------------------
    @Override
    public void playerLoad() {
        try {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (loadingFragment != null)
                        getFragmentManager().beginTransaction().remove(loadingFragment).commitAllowingStateLoss();
                }
            });
        }catch (Exception e)
        {
            e.printStackTrace();
        }

    }

    @Override
    public void playerStart() {
        try {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (loadingFragment != null)
                        getFragmentManager().beginTransaction().remove(loadingFragment).commitAllowingStateLoss();
                }
            });
        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void playerStopped(String path) {
        try {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (loadingFragment != null)
                        getFragmentManager().beginTransaction().replace(R.id.loader, loadingFragment).commitAllowingStateLoss();
                }
            });
        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }
//----------------------------------------------------------------------------------------------
//==============================================================================================


    //=====================================LifeCycle================================================
//----------------------------------------------------------------------------------------------
    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        try {
            Log.e(TAG, event.getKeyCode() + " code");
            switch (event.getAction()) {
                case KeyEvent.ACTION_DOWN:
                    switch (event.getKeyCode()) {
                        case KeyEvent.KEYCODE_BACK:
                            return true;
                        case KeyEvent.KEYCODE_DPAD_UP:
                        case KeyEvent.KEYCODE_PAGE_UP:
                        case KeyEvent.KEYCODE_CHANNEL_UP:
                            Log.e(TAG, event.getKeyCode() + "down - up");
                            return true;
                        case KeyEvent.KEYCODE_DPAD_DOWN:
                        case KeyEvent.KEYCODE_PAGE_DOWN:
                        case KeyEvent.KEYCODE_CHANNEL_DOWN:
                            Log.e(TAG, event.getKeyCode() + "down - down");
                            return true;
                    }
                    break;
                case KeyEvent.ACTION_UP:
                    switch (event.getKeyCode()) {
                        case KeyEvent.KEYCODE_BACK:
                            super.onBackPressed();
                            return true;
                        case KeyEvent.KEYCODE_DPAD_UP:
                        case KeyEvent.KEYCODE_PAGE_UP:
                        case KeyEvent.KEYCODE_CHANNEL_UP:
                            Log.e(TAG, event.getKeyCode() + "up - up");
                            setChannel(true);
                            return true;
                        case KeyEvent.KEYCODE_DPAD_DOWN:
                        case KeyEvent.KEYCODE_PAGE_DOWN:
                        case KeyEvent.KEYCODE_CHANNEL_DOWN:
                            Log.e(TAG, event.getKeyCode() + "up - down");
                            setChannel(false);
                            return true;
                    }
                    break;
                default:
                    // exec("am force-stop com");

            }
        }catch (Exception e)
        {
            e.printStackTrace();
        }

        return super.dispatchKeyEvent(event);
    }


    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        playUI(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            app = this;
            setContentView(R.layout.sg_player_activity);
            loadingFragment = new SantagrandLoadingFragment();
            Intent intent = getIntent();
            playUI(intent);
            playerStopped(null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //----------------------------------------------------------------------------------------------
    //==============================================================================================


    //=====================================Method===================================================
    //----------------------------------------------------------------------------------------------
    public void playUI(Intent intent) {
        try {
            if (intent.hasExtra(URI)) {
                String uri = intent.getStringExtra(URI);
                if (uri != null) {
                    videoFragment = new SantagrandPlayerFragment();
                    videoFragment.setChannelUri(uri);
                    videoFragment.setCb(this);
                    try {
                        getFragmentManager().beginTransaction().remove(videoFragment).commitAllowingStateLoss();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    getFragmentManager().beginTransaction().replace(R.id.player, videoFragment, "player").commitAllowingStateLoss();
                }
            }
            if (intent.hasExtra(TITLE)) {

                String title = intent.getStringExtra(TITLE);

                channelTitle = intent.getStringExtra(TITLE) + "";
                channelUri = intent.getStringExtra(URI) + "";

                tv_channel = findViewById(R.id.tv_channel);

                if (title != null) {
                    tv_channel.setText(title + "");
                }
            }
            if (intent.hasExtra(ID))
            {
                int id = intent.getIntExtra(ID,0);
                channelID = id;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @SuppressWarnings("deprecation")
    public void exec(String adb) {
        try {
            Log.i(TAG, "@exec --> command is " + adb);
            suAvailable = Shell.SU.available();
            if (suAvailable) {
                suVersion = Shell.SU.version(false);
                suVersionInternal = Shell.SU.version(true);
                suResult = Shell.SU.run(new String[]{
                        adb
                });
                Log.i(TAG, "@exec --> " + suResult);
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setChannel(boolean isPageUp)
    {
        try {
            if (TheQuayTV.channelList != null) {
                if (isPageUp) {
                    if (channelID < TheQuayTV.channelList.size())
                        channelID++;
                    if (channelID >= TheQuayTV.channelList.size())
                        channelID = 0;
                } else {

                    if (channelID >= 0)
                        channelID--;
                    if (channelID < 0)
                        channelID = TheQuayTV.channelList.size()-1;
                }
                if (TheQuayTV.channelList.size() > 0) {
                    try {
                        channelID    = TheQuayTV.channelList.get(channelID).getId();
                        channelUri   = TheQuayTV.channelList.get(channelID).getChannel_uri();
                        channelTitle = TheQuayTV.channelList.get(channelID).getChannel_title();
                        if(tv_channel!=null)
                            tv_channel.setText(channelTitle);
                        if(videoFragment==null)
                            videoFragment = new SantagrandPlayerFragment();
                        videoFragment.setChannelUri(channelUri);
                        videoFragment.setCb(this);
                        getFragmentManager().beginTransaction().remove(videoFragment).commitAllowingStateLoss();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    getFragmentManager().beginTransaction().replace(R.id.player, videoFragment, "player").commitAllowingStateLoss();
                    Log.i(TAG,"@channel id: "+channelID);
                }

            }
        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
//----------------------------------------------------------------------------------------------
//==============================================================================================