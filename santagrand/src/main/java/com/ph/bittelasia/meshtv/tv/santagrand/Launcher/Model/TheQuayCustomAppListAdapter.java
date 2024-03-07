package com.ph.bittelasia.meshtv.tv.santagrand.Launcher.Model;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiManager;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.ph.bittelasia.meshtv.tv.maintenance.Control.MeshControl;
import com.ph.bittelasia.meshtv.tv.mtvlib.Core.Control.App.MeshTVAppManager;
import com.ph.bittelasia.meshtv.tv.mtvlib.Core.Object.Apps.MeshApp;
import com.ph.bittelasia.meshtv.tv.mtvlib.Core.View.CustomView.MeshTVCustomList.MeshTVCustomAdapter;
import com.ph.bittelasia.meshtv.tv.santagrand.Airmedia.View.Toast.MeshTVToast;
import com.ph.bittelasia.meshtv.tv.santagrand.Core.Model.AppOtherList;
import com.ph.bittelasia.meshtv.tv.santagrand.Netflix.View.Activity.TheQuayNetflix;
import com.ph.bittelasia.meshtv.tv.santagrand.R;
import com.ph.bittelasia.packages.Core.Service.AppWifiIntentService;

import java.util.ArrayList;

public class TheQuayCustomAppListAdapter extends MeshTVCustomAdapter<MeshApp> {

    //=================================Variable=====================================================
    //---------------------------------Constant-----------------------------------------------------
    private String TAG = TheQuayCustomAppListAdapter.class.getSimpleName();
    //----------------------------------------------------------------------------------------------
    //---------------------------------Instance-----------------------------------------------------
    private final Activity  activity;
    private AppNotificationListener listener;
    private final String AIRMEDIA = Environment.getExternalStorageDirectory().getPath()+"/android/airmedia.apk";
    //----------------------------------------------------------------------------------------------
    //==============================================================================================



    @SuppressWarnings("unchecked")
    public TheQuayCustomAppListAdapter(Activity activity, ArrayList items,AppNotificationListener listener)
    {
        super(items);
        this.activity=activity;
        this.listener=listener;
    }

    @Override
    public void bindView(View view, final MeshApp app) {
        try {
            final TheQuayCustomAppListViewHolder vh = new TheQuayCustomAppListViewHolder();
            view.setOnHoverListener(null);
            view.setFocusable(true);

            view.setOnFocusChangeListener((v, hasFocus) -> {
                ViewGroup vg = (ViewGroup) v;

                ViewGroup iv_group = (ViewGroup) vg.getChildAt(0);
                ImageView icon = (ImageView) iv_group.getChildAt(0);
                TextView title = (TextView) vg.getChildAt(1);

                if (hasFocus) {
                    icon.setImageDrawable(vh.getIv_image().getContext().getResources().getDrawable(app.getIconSelected()));
                    title.setTextColor(v.getContext().getResources().getColor(R.color.SG_Gray));
                } else {
                    icon.setImageDrawable(vh.getIv_image().getContext().getResources().getDrawable(app.getIcon()));
                    title.setTextColor(v.getContext().getResources().getColor(R.color.SG_Gold));
                }

            });

            view.setOnHoverListener((v, event) -> {
                v.requestFocus();
                return false;
            });

            view.setOnClickListener(v -> {
                try {
                    if (app.getDisplayName().toLowerCase().contains("netflix")) {

                        Intent i = new Intent();
                        i.setComponent(new ComponentName("com.ph.bittelasia.meshtv.tv.wifimanager", "com.ph.bittelasia.meshtv.tv.wifimanager.NetflixService"));
                        activity.startService(i);
                        TheQuayNetflix.doAPPAnalytics(activity, AppOtherList.NETFLIX.getAppID());

                        Log.i(TAG, "netflix found");
                    } else if (app.getDisplayName().toLowerCase().contains("air media")) {
                        Log.i(TAG, "@install " + AIRMEDIA);
                        MeshControl.install(this.activity, AIRMEDIA);
                        MeshTVToast.makeText(this.activity, R.layout.toast, "Preparing Air Media App...", Toast.LENGTH_LONG).show();
                        MeshTVAppManager.launchForeResult(activity, app.getApp(), app.getId());
                    } else {
                        MeshTVAppManager.launchForeResult(activity, app.getApp(), app.getId());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                Log.e(TAG, "app name: " + app.getDisplayName());
            });

            vh.inflate(view);
            try {
                vh.getTv_text().setText(app.getDisplayName());
                vh.getIv_image().setImageDrawable(vh.getIv_image().getContext().getResources().getDrawable(app.getIcon()));
                if (app.getDisplayName().toLowerCase().contains("message")) {
                    if (listener != null)
                        listener.getNotifView(vh.getTv_badge());

                } else {
                    vh.getTv_badge().setVisibility(View.GONE);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    //==================================AppListListener=============================================
    //----------------------------------------------------------------------------------------------
    public interface AppNotificationListener
    {
        void getNotifView(TextView v);

        void getNotif(int count);
    }
    //----------------------------------------------------------------------------------------------
    //==============================================================================================

}
