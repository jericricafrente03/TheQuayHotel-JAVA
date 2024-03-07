package com.ph.bittelasia.meshtv.tv.santagrand.Core.View.Fragment;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ph.bittelasia.meshtv.tv.mtvlib.Core.Control.Annotation.View.General.Layout;
import com.ph.bittelasia.meshtv.tv.mtvlib.Core.Control.Annotation.View.Widget.BindWidget;
import com.ph.bittelasia.meshtv.tv.mtvlib.Entity.Control.Listener.MeshMessageListener;
import com.ph.bittelasia.meshtv.tv.mtvlib.Entity.Model.Message.MeshMessage;
import com.ph.bittelasia.meshtv.tv.santagrand.Message.View.Activity.TheQuayMessage;
import com.ph.bittelasia.meshtv.tv.santagrand.R;
import com.ph.bittelasia.meshtv.tv.santagrand.Room.Control.TheQuayDAOManager;
import com.ph.bittelasia.meshtv.tv.santagrand.Room.Library.MeshTVRFragment;

import java.util.ArrayList;

@Layout(R.layout.sg_notification)
public class TheQuayMessageFragment extends MeshTVRFragment<MeshMessage> implements MeshMessageListener<MeshMessage>
{
    //==========================================Variable============================================
    //------------------------------------------Constant--------------------------------------------
    public static final String TAG = TheQuayMessageFragment.class.getSimpleName();
    //----------------------------------------------------------------------------------------------
    //--------------------------------------------View----------------------------------------------
    @BindWidget(R.id.iv_notification)
    public ImageView iv_notification;
    @BindWidget(R.id.tv_badge)
    public TextView tv_badge;
    //------------------------------------------Instance--------------------------------------------
    private MessageCallBack cb;
    public  boolean launcherOpen =false;
    //----------------------------------------------------------------------------------------------
    //==============================================================================================
    //==========================================LifeCycle===========================================

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        cb = (MessageCallBack)context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        cb=null;
    }

    @Override
    public void onResume() {
        super.onResume();
        requestData();
        Log.i(TAG,"@onResume");
    }


    @Override
    public void onPause() {
        super.onPause();
        TheQuayDAOManager.get().removeListener(this);
        Log.i(TAG,"@onPause");
    }
    //==============================================================================================
    //===========================================Method=============================================
    public void requestData()
    {
        TheQuayDAOManager.get().addListener(this);
        TheQuayDAOManager.get().retrieve(MeshMessage.class);
    }

    public void checkMessage(int c)
    {
        try
        {
            if(!isLauncherOpen() && isXMPPConnected) {
                if (c > 0) {
                    if (tv_badge != null) {
                        tv_badge.setVisibility(View.VISIBLE);
                        tv_badge.setText(("" + c));
                    }
                    if (iv_notification != null) {
                        iv_notification.setVisibility(View.VISIBLE);
                    }
                } else {
                    if (tv_badge != null) {
                        tv_badge.setVisibility(View.GONE);
                    }
                    if (iv_notification != null) {
                        iv_notification.setVisibility(View.GONE);
                    }
                }
                Log.i(TAG, "@checkMessage: count=" + c);
            }else
            {
                if (tv_badge != null) {
                    tv_badge.setVisibility(View.GONE);
                }
                if (iv_notification != null) {
                    iv_notification.setVisibility(View.GONE);
                }
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public boolean isLauncherOpen() {
        return launcherOpen;
    }

    //==============================================================================================
    //========================================MeshTVFragment========================================

    @SuppressWarnings("ConstantConditions")
    @Override
    public void draw(View v) {
        try
        {

                if(iv_notification!=null)
                {
                    iv_notification.setVisibility(View.VISIBLE);
                    iv_notification.setClickable(true);
                    iv_notification.setOnClickListener
                            (
                                    view -> {
                                        Intent i = new Intent(getContext(), TheQuayMessage.class);
                                        startActivityForResult(i, 100);
                                    }
                            );
                    iv_notification.setOnFocusChangeListener((vw,hasFocus)->
                    {
                       if(hasFocus)
                       {
                           iv_notification.setImageDrawable(getContext().getResources().getDrawable(R.drawable.app_message_hl));
                       }
                       else
                       {
                           iv_notification.setImageDrawable(getContext().getResources().getDrawable(R.drawable.app_message));
                       }

                    });
                    iv_notification.setOnHoverListener((v1, event) -> {
                        v1.requestFocus();
                        return false;
                    });
                    iv_notification.setFocusable(true);
                }
                iv_notification.setVisibility(View.GONE);
                tv_badge.setVisibility(View.GONE);
            Log.i(TAG,"@draw");
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void updateDisplay(MeshMessage item) {
        Log.i(TAG,"@updateDisplay");
    }

    @SuppressWarnings("unchecked")
    @Override
    public void onInsertedBulk(ArrayList arrayList) {
        try {
            int count = 0;
            if (arrayList != null && arrayList.size() > 0) {
                if (arrayList.get(0) instanceof MeshMessage) {
                    for (MeshMessage m : (ArrayList<MeshMessage>) arrayList) {
                        if (m.getMessg_status() < 2) {
                            count++;
                        }
                    }
                    launcherOpen = false;
                    checkMessage(count);
                }
                if (cb != null) {
                    cb.count(count);
                }
            }
            Log.i(TAG, "@onInsertedBulk");
        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void onDeletedBulk(ArrayList arrayList) {
        Log.i(TAG,"@onDeletedBulk");
    }

    @SuppressWarnings("unchecked")
    @Override
    public void onRetrieved(ArrayList arrayList) {
        int count =0;
        if(arrayList!=null && arrayList.size()>0)
        {
            if(arrayList.get(0) instanceof MeshMessage)
            {
                for(MeshMessage m: (ArrayList<MeshMessage>) arrayList)
                {
                    if(m.getMessg_status() < 2)
                    {

                        count++;
                    }
                }
                launcherOpen =false;
                checkMessage(count);
            }
            if(cb!=null)
            {
                cb.count(count);
            }
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
    //==============================================================================================

    //=========================================CallBack=============================================
    //----------------------------------------------------------------------------------------------
    public interface MessageCallBack
    {
        void count(int c);
    }
    //----------------------------------------------------------------------------------------------
    //==============================================================================================
}
