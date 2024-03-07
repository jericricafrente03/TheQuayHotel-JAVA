package com.ph.bittelasia.meshtv.tv.santagrand.TV.View.Fragment;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.GridView;

import com.ph.bittelasia.meshtv.tv.mtvlib.Core.Control.Annotation.View.General.Layout;
import com.ph.bittelasia.meshtv.tv.mtvlib.Core.Control.Annotation.View.Widget.BindWidget;
import com.ph.bittelasia.meshtv.tv.mtvlib.Core.Control.Listener.MeshListItemClickedListener;
import com.ph.bittelasia.meshtv.tv.mtvlib.Entity.Control.Listener.MeshChannelListener;
import com.ph.bittelasia.meshtv.tv.mtvlib.Entity.Model.Channel.MeshChannel;
import com.ph.bittelasia.meshtv.tv.santagrand.R;
import com.ph.bittelasia.meshtv.tv.santagrand.Room.Control.TheQuayDAOManager;
import com.ph.bittelasia.meshtv.tv.santagrand.Room.Library.MeshTVRFragment;
import com.ph.bittelasia.meshtv.tv.santagrand.TV.Model.MeshArrayListCallBack;
import com.ph.bittelasia.meshtv.tv.santagrand.TV.Model.TheQuayTVChannelAdapter;

import java.util.ArrayList;

@Layout(R.layout.sg_grid_tv_fragment)
public class TheQuayChannelFragment extends MeshTVRFragment<MeshChannel> implements MeshChannelListener<MeshChannel>

{

    //=======================================Variable===============================================
    //---------------------------------------Constant-----------------------------------------------
    public static final String TAG = TheQuayChannelFragment.class.getSimpleName();
    //---------------------------------------Instance-----------------------------------------------
    private TheQuayTVChannelAdapter     adapter;
    private MeshListItemClickedListener clickedListener;
    private MeshArrayListCallBack       cb;
    private boolean                     hasPaused;

    //-----------------------------------------View-------------------------------------------------
    @BindWidget(R.id.gv_channels)
    public GridView gridChannels;
    //----------------------------------------------------------------------------------------------
    //==============================================================================================




    //=======================================LifeCycle==============================================
    //----------------------------------------------------------------------------------------------


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        cb=(MeshArrayListCallBack)context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        cb =null;
    }

    @Override
    public void onResume() {
        try {
            super.onResume();
            TheQuayDAOManager.get().addListener(this);
            TheQuayDAOManager.get().retrieve(MeshChannel.class);
            Log.i(TAG, "@onResume");
        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    @Override
    public void onPause() {
        try {
            super.onPause();
            TheQuayDAOManager.get().removeListener(this);
            Log.i(TAG, "@onPause");
            hasPaused = true;
        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    //----------------------------------------------------------------------------------------------
    //==============================================================================================




    //=====================================MeshTVRFragment==========================================
    //----------------------------------------------------------------------------------------------
    @Override
    public void draw(View v) {
        try {
            adapter = new TheQuayTVChannelAdapter();
            adapter.setClickedListener(o -> {
                if (getClickedListener() != null) {
                    getClickedListener().onClicked(o);
                }
            });
            Log.i(TAG, "@draw");
        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void updateDisplay(MeshChannel item) {
        Log.i(TAG,"@updateDisplay");
    }
    //----------------------------------------------------------------------------------------------
    //==============================================================================================


    //===================================MeshChannelListener========================================
    //----------------------------------------------------------------------------------------------
    @Override
    public void onInsertedBulk(ArrayList arrayList) {
        try {
            Log.i(TAG, "@onInsertedBulk");
            if (arrayList != null && arrayList.size() > 0) {
                adapter.setObjects(arrayList, false);
                gridChannels.setAdapter(adapter);
                if (isXMPPConnected)
                    Log.i(TAG, "@onInsertedBulk: " + arrayList.size());
                else
                    Log.e(TAG, "@onInsertedBulk: Not connected to xmpp");
            }
        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void onDeletedBulk(ArrayList arrayList) {
        Log.i(TAG, "@onDeletedBulk");
    }

    @Override
    public void onRetrieved(ArrayList arrayList) {
        try {
            if (arrayList != null && arrayList.size() > 0) {
                adapter.setObjects(arrayList, true);
                gridChannels.setAdapter(adapter);
                if (isXMPPConnected)
                    Log.i(TAG, "@onRetrieved: " + arrayList.size());
                else
                    Log.e(TAG, "@onRetrieved: Not connected to xmpp");
            }
        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void onCleared(Class aClass) {
        Log.i(TAG,"@onCleared -> class: "+aClass.getSimpleName());
    }

    @Override
    public void onDAONotFound(String s) {
        Log.i(TAG,"@onDAONotFound -> "+s);
    }
    //----------------------------------------------------------------------------------------------
    //==============================================================================================


    //=====================================Method===================================================
    //-------------------------------------Setter---------------------------------------------------
    public void setClickedListener(MeshListItemClickedListener clickedListener) {
        this.clickedListener = clickedListener;
    }
    //----------------------------------------------------------------------------------------------
    //-------------------------------------Getter---------------------------------------------------
    public MeshListItemClickedListener getClickedListener() {
        return clickedListener;
    }

    public TheQuayTVChannelAdapter getAdapter() {
        return adapter;
    }
    //----------------------------------------------------------------------------------------------
    //==============================================================================================
}
