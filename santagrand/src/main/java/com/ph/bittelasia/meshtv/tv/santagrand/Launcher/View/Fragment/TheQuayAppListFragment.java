package com.ph.bittelasia.meshtv.tv.santagrand.Launcher.View.Fragment;



import android.content.Intent;

import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.ph.bittelasia.meshtv.tv.mtvlib.AirMedia.Control.MeshTVAirmediaControl;
import com.ph.bittelasia.meshtv.tv.mtvlib.Core.Control.Annotation.View.General.Layout;
import com.ph.bittelasia.meshtv.tv.mtvlib.Core.Control.Data.Control.Listener.MeshAppListener;
import com.ph.bittelasia.meshtv.tv.mtvlib.Core.Control.Listener.BroadcastListeners.MeshTVAirmediaListener;
import com.ph.bittelasia.meshtv.tv.mtvlib.Core.Control.Listener.BroadcastListeners.MeshTVPackageListener;
import com.ph.bittelasia.meshtv.tv.mtvlib.Core.Control.Util.RemoteControl.KR301KeyCode;
import com.ph.bittelasia.meshtv.tv.mtvlib.Core.Control.Util.ResourceManager.MeshResourceManager;
import com.ph.bittelasia.meshtv.tv.mtvlib.Core.Object.Apps.MeshApp;
import com.ph.bittelasia.meshtv.tv.mtvlib.Core.View.CustomView.MeshTVCustomList.MeshTVCustomListView;
import com.ph.bittelasia.meshtv.tv.santagrand.Airmedia.View.Activity.TheQuayAirMedia;
import com.ph.bittelasia.meshtv.tv.santagrand.Core.View.Fragment.TheQuayHotelAppFragment;
import com.ph.bittelasia.meshtv.tv.santagrand.Launcher.Model.TheQuayCustomAppListAdapter;
import com.ph.bittelasia.meshtv.tv.santagrand.R;

import java.util.ArrayList;

@Layout(R.layout.app_fragment)
public class TheQuayAppListFragment extends TheQuayHotelAppFragment implements MeshAppListener,View.OnKeyListener, MeshTVAirmediaListener, MeshTVPackageListener, TheQuayCustomAppListAdapter.AppNotificationListener {

    //=========================================Variable=============================================
    //-----------------------------------------Constant---------------------------------------------
    private static final String TAG  = TheQuayAppListFragment.class.getSimpleName();
    //-----------------------------------------Instance---------------------------------------------
    private TheQuayCustomAppListAdapter adapter;
    //----------------------------------------------------------------------------------------------
    //------------------------------------------View------------------------------------------------
    public MeshTVCustomListView list;
    private TextView            notifView;
    //----------------------------------------------------------------------------------------------
    //==============================================================================================
    //========================================Life Cycle============================================
    //----------------------------------------------------------------------------------------------
    @Override
    public void onStart() {
        super.onStart();

        MeshResourceManager.get().add("sgh_default_logo1.png", R.drawable.sgh_default_logo);
        MeshResourceManager.get().add("sgh_default_logo.png", R.drawable.sgh_default_logo);
        MeshResourceManager.get().add("app_watch_tv", R.drawable.app_watch_tv);
        MeshResourceManager.get().add("app_watch_tv_hl", R.drawable.app_watch_tv_hl);
        MeshResourceManager.get().add("app_explore", R.drawable.app_explore);
        MeshResourceManager.get().add("app_explore_hl", R.drawable.app_explore_hl);
        MeshResourceManager.get().add("app_entertain", R.drawable.app_entertain);
        MeshResourceManager.get().add("app_entertain_hl", R.drawable.app_entertain_hl);
        MeshResourceManager.get().add("app_info", R.drawable.app_info);
        MeshResourceManager.get().add("app_info_hl", R.drawable.app_info_hl);
        MeshResourceManager.get().add("app_weather", R.drawable.app_weather);
        MeshResourceManager.get().add("app_weather_hl", R.drawable.app_weather_hl);
        MeshResourceManager.get().add("app_message", R.drawable.app_message);
        MeshResourceManager.get().add("app_message_hl", R.drawable.app_message_hl);
        MeshResourceManager.get().add("app_netflix", R.drawable.app_netflix);
        MeshResourceManager.get().add("app_netflix_hl", R.drawable.app_netflix_hl);
        MeshResourceManager.get().add("app_netflix_hl2", R.drawable.app_netflix_hl2);
        MeshResourceManager.get().add("ANDROID", R.drawable.and);
        MeshResourceManager.get().add("IOS", R.drawable.ios);
        MeshResourceManager.get().add("WINDOWS", R.drawable.windows);
        setMeshAppListener(this);
        getAppList();
        Log.i(TAG,"@onStart");
    }
    //----------------------------------------------------------------------------------------------
    //==============================================================================================

    //===================================TheQuayHotelAppFragment====================================
    //----------------------------------------------------------------------------------------------
    @Override
    public void draw(View v) {
        list =v.findViewById(R.id.clv);
        Log.i(TAG,"@draw");
    }
    //----------------------------------------------------------------------------------------------
    //==============================================================================================


    //======================================View.OnKeyListener======================================
    //----------------------------------------------------------------------------------------------
    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {
        try {
            switch (KR301KeyCode.getEquivalent(event.getKeyCode())) {
                case KeyEvent.KEYCODE_DPAD_LEFT:
                    if (event.getAction() == KeyEvent.ACTION_UP) {
                        list.prev();
                        return true;
                    }
                    return true;
                case KeyEvent.KEYCODE_DPAD_RIGHT:
                    if (event.getAction() == KeyEvent.ACTION_UP) {
                        list.next();
                        return true;
                    }
                    return true;
                case KeyEvent.KEYCODE_DPAD_CENTER:
                    if (event.getAction() == KeyEvent.ACTION_UP) {
                        list.select();
                        return true;
                    }
                    return true;
            }
            Log.i(TAG, "@onKey: " + event.getKeyCode());
            Log.i(TAG, "@onKey: mars-" + KR301KeyCode.getEquivalent(event.getKeyCode()));

        }catch (Exception e)
        {
            e.printStackTrace();
        }
        return onKey(v,keyCode,event);
    }
    //----------------------------------------------------------------------------------------------
    //==============================================================================================


    //====================================MeshAppListener===========================================
    //----------------------------------------------------------------------------------------------
    @Override
    public void onAppsLoaded(ArrayList<MeshApp> arrayList) {
        try
        {
            adapter = new TheQuayCustomAppListAdapter(getActivity(),arrayList,this);
            if (list != null) {
                list.setAdapter(adapter);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        Log.i(TAG,"@onAppsLoaded -> apps  size: "+arrayList.size());
    }
    @Override
    public void onAppsNotFound() {
        Log.i(TAG, "@onAppsNotFound");
    }
    //----------------------------------------------------------------------------------------------
    //==============================================================================================



    //====================================MeshTVAirmediaListener====================================
    //----------------------------------------------------------------------------------------------
    @SuppressWarnings("ConstantConditions")
    @Override
    public void airmediaReady() {
        try {
            Fragment fragment = getActivity().getSupportFragmentManager().findFragmentByTag("toast");
            if (fragment != null) {
                MeshTVAirmediaControl.disableToast(getActivity().getApplicationContext());
                Intent mIntent = new Intent(getActivity(), TheQuayAirMedia.class);
                mIntent.putExtra("ready", true);
                startActivity(mIntent);
            }
        }catch (Exception e)
        {
            e.printStackTrace();
        }
        Log.i(TAG, "@airmediaReady");
    }

    @Override
    public void airmediaNotReady() {
        Log.i(TAG, "@airmediaNotReady");
    }
    //----------------------------------------------------------------------------------------------
    //==============================================================================================



    //===================================MeshTVPackageListener======================================
    //----------------------------------------------------------------------------------------------
    @Override
    public void appInstalled(String s) {
        Log.i(TAG, "@appInstalled: "+s);
    }

    @Override
    public void appUnInstalled(String s) {
        Log.i(TAG, "@appUnInstalled: "+s);
    }

    //----------------------------------------------------------------------------------------------
    //==============================================================================================

    //============================TheQuayCustomAppListAdapter.AppNotificationListener===============
    //----------------------------------------------------------------------------------------------
    @Override
    public void getNotifView(TextView v) {
        notifView = v;
    }

    @Override
    public void getNotif(int count) {
        try
        {
            if(notifView!=null) {
                if (count == 0) {
                    notifView.setVisibility(View.GONE);
                } else {
                    notifView.setVisibility(View.VISIBLE);
                }
                if (count < 10)
                    notifView.setText((" " + count + " "));
                else
                    notifView.setText(("" + count));
            }
        }catch (Exception e)
        {
            e.printStackTrace();

        }
    }

    //----------------------------------------------------------------------------------------------
    //==============================================================================================

}