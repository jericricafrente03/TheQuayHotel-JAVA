package com.ph.bittelasia.meshtv.tv.santagrand.Airmedia.View.Activity;

import android.content.BroadcastReceiver;
import android.os.Bundle;

import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.ph.bittelasia.meshtv.tv.maintenance.Control.MeshControl;
import com.ph.bittelasia.meshtv.tv.mtvlib.AirMedia.Control.MeshTVAirmediaControl;
import com.ph.bittelasia.meshtv.tv.mtvlib.Core.Control.Annotation.View.Activity.ActivitySetting;
import com.ph.bittelasia.meshtv.tv.mtvlib.Core.Control.Annotation.View.Activity.AttachFragment;
import com.ph.bittelasia.meshtv.tv.mtvlib.Core.Control.Annotation.View.General.Layout;
import com.ph.bittelasia.meshtv.tv.mtvlib.Core.Control.Annotation.View.Widget.BindWidget;
import com.ph.bittelasia.meshtv.tv.mtvlib.Core.Control.Listener.BroadcastListeners.MeshTVAirmediaListener;
import com.ph.bittelasia.meshtv.tv.mtvlib.Core.Control.Listener.MeshListItemClickedListener;
import com.ph.bittelasia.meshtv.tv.mtvlib.Core.Control.Preference.Control.Manager.MeshTVPreferenceManager;
import com.ph.bittelasia.meshtv.tv.mtvlib.Core.Control.Util.RemoteControl.KR301KeyCode;
import com.ph.bittelasia.meshtv.tv.mtvlib.PackageManager.Control.MeshTVGenericPackageListener;
import com.ph.bittelasia.meshtv.tv.mtvlib.PackageManager.Control.MeshTVPackageManager;
import com.ph.bittelasia.meshtv.tv.santagrand.Airmedia.Model.AirMediaPlatform;
import com.ph.bittelasia.meshtv.tv.santagrand.Airmedia.View.Fragment.TheQuayAirMediaInstructions;
import com.ph.bittelasia.meshtv.tv.santagrand.Airmedia.View.Fragment.TheQuayAirMediaQRCode;
import com.ph.bittelasia.meshtv.tv.santagrand.Airmedia.View.Fragment.TheQuayAirmediaGridFragment;
import com.ph.bittelasia.meshtv.tv.santagrand.Airmedia.View.Toast.MeshTVToast;
import com.ph.bittelasia.meshtv.tv.santagrand.Core.View.Activity.TheQuayActivity;
import com.ph.bittelasia.meshtv.tv.santagrand.R;

@Layout(R.layout.sg_activity_airmedia)
@ActivitySetting()
public class TheQuayAirMedia extends TheQuayActivity implements MeshListItemClickedListener, MeshTVAirmediaListener, MeshTVGenericPackageListener
{

    //===================================Variable===================================================
    //-----------------------------------Constant---------------------------------------------------
    public  static final String TAG      = TheQuayAirMedia.class.getSimpleName();
    private final String PACKAGE  = "com.waxrain.airplaydmr";
    //-----------------------------------Instance---------------------------------------------------
    private TheQuayAirmediaGridFragment  airmediaGridFragment;
    private TheQuayAirMediaInstructions  instructions;
    private BroadcastReceiver            receiver;
    private boolean                      isDisplaying=false;
    //----------------------------------------------------------------------------------------------
    //-----------------------------------View-------------------------------------------------------
    @BindWidget(R.id.tv_status)
    public TextView tv_status;
    @BindWidget(R.id.iv_status)
    public ImageView iv_status;
    @BindWidget(R.id.ch_wifi)
    public View ch_wifi;
    @BindWidget(R.id.btn_show)
    public Button btn_show;
    //----------------------------------------------------------------------------------------------
    //==============================================================================================
    //==================================Life Cycle==================================================
    //----------------------------------------------------------------------------------------------
    @Override
    public boolean dispatchKeyEvent(KeyEvent event)
    {
        try {
            switch (event.getAction()) {
                case KeyEvent.ACTION_DOWN:
                    switch (KR301KeyCode.getEquivalent(event.getKeyCode())) {
                        case KeyEvent.KEYCODE_BACK:
                            btn_show.setVisibility(View.GONE);
                            return true;
                    }
                    break;
                case KeyEvent.ACTION_UP:
                    switch (KR301KeyCode.getEquivalent(event.getKeyCode())) {
                        case KeyEvent.KEYCODE_BACK:
                            if (!isDisplaying) {
                                isDisplaying = true;
                                getSupportFragmentManager().beginTransaction().replace(R.id.ll_main, airmediaGridFragment, "airmedia").commit();
                                return true;
                            } else {
                                isDisplaying = false;
                                super.onBackPressed();
                                return false;
                            }
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
        super.onStart();
        try {
            receiver = MeshTVPackageManager.listen(this, this);
            Log.i(TAG, "@onStart");
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.i(TAG, "@onStart");
    }

    @Override
    protected void onStop() {
        super.onStop();
        try {
            if (receiver != null)
                MeshTVPackageManager.ignore(this, receiver);
            Log.i(TAG, "@onStop");
        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        try {
            if (btn_show == null) {
                btn_show = findViewById(R.id.btn_show);
            }
            btn_show.setVisibility(View.GONE);
            btn_show.setOnClickListener(v -> new TheQuayAirMediaQRCode().show(getSupportFragmentManager(), "dialog"));
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public String getActivityName() {
        return TAG;
    }


    //----------------------------------------------------------------------------------------------
    //==============================================================================================
    //===================================Fragment===================================================
    //----------------------------------------------------------------------------------------------
    @AttachFragment(container = R.id.ll_main,tag = "airmedia",order=1)
    public Fragment attachAirMedia()
    {
        try {
            airmediaGridFragment = new TheQuayAirmediaGridFragment();
            airmediaGridFragment.setClickedListener(this);
            isDisplaying = true;
        }catch (Exception e)
        {
            e.printStackTrace();
        }
       return airmediaGridFragment;
    }
    //----------------------------------------------------------------------------------------------
    //==============================================================================================
    //===============================MeshListItemClickedListener====================================
    //----------------------------------------------------------------------------------------------
    @Override
    public void onClicked(Object o) {
        try {
            if (o instanceof AirMediaPlatform) {
                AirMediaPlatform platform = (AirMediaPlatform) o;
                btn_show.setVisibility(platform.getName().equals(AirMediaPlatform.names.ANDROID) ? View.VISIBLE : View.GONE);
                isDisplaying = false;
                instructions = TheQuayAirMediaInstructions.get(platform.getName().toString());
                getSupportFragmentManager().beginTransaction().replace(R.id.ll_main, instructions, "instructions").commitAllowingStateLoss();
            }

            Log.i(TAG, "@onClicked: "+o.getClass().getSimpleName());
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    //----------------------------------------------------------------------------------------------
    //==============================================================================================
    //=======================================MeshTVAirmediaListener=================================
    //----------------------------------------------------------------------------------------------
    @Override
    public void airmediaReady() {
        Log.i(TAG, "@airmediaReady");
        try
        {
            tv_status.setText(("Ready"));
            iv_status.setImageDrawable(getResources().getDrawable(R.drawable.ready));
            if(hotspotManager.isApOn())
                ch_wifi.performClick();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void airmediaNotReady() {
        Log.i(TAG, "@airmediaNotReady");
        try
        {
            tv_status.setText(("Not Ready"));
            iv_status.setImageDrawable(getResources().getDrawable(R.drawable.not_ready));
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    //----------------------------------------------------------------------------------------------
    //==============================================================================================



    //=====================================MeshTVGenericPackageListener=============================
    //----------------------------------------------------------------------------------------------
    @Override
    public void onInstalled(String s) {
        try {
            Log.i(TAG, "@onInstalled: " + s);
            if (s.equals(PACKAGE)) {
                MeshTVAirmediaControl.disableToast(this);
                MeshTVAirmediaControl.startAirMedia(this);
                MeshTVAirmediaControl.enableDLNA(this);
                MeshTVAirmediaControl.enableAirplay(this);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void onUninstalled(String s) {
        Log.i(TAG, "@onUninstalled: "+s);
    }
    //----------------------------------------------------------------------------------------------
    //==============================================================================================

}
