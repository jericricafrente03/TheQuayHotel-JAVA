package com.ph.bittelasia.meshtv.tv.santagrand.Message.View.Fragment;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.ph.bittelasia.meshtv.tv.mtvlib.Core.Control.Annotation.View.General.Layout;
import com.ph.bittelasia.meshtv.tv.mtvlib.Core.Control.Annotation.View.Widget.BindWidget;
import com.ph.bittelasia.meshtv.tv.mtvlib.Core.Control.Listener.MeshTVFragmentListener;
import com.ph.bittelasia.meshtv.tv.mtvlib.Entity.Control.API.GET.API.ReadMessageTask;
import com.ph.bittelasia.meshtv.tv.mtvlib.Entity.Control.Listener.MeshDAOListener;
import com.ph.bittelasia.meshtv.tv.mtvlib.Entity.Control.Listener.MeshMessageListener;
import com.ph.bittelasia.meshtv.tv.mtvlib.Entity.Control.RealtimeManager.MeshRealtimeManager;
import com.ph.bittelasia.meshtv.tv.mtvlib.Entity.Model.Message.MeshMessage;
import com.ph.bittelasia.meshtv.tv.santagrand.Message.Model.TheQuayMessageListAdapter;
import com.ph.bittelasia.meshtv.tv.santagrand.R;
import com.ph.bittelasia.meshtv.tv.santagrand.Room.Control.TheQuayDAOManager;
import com.ph.bittelasia.meshtv.tv.santagrand.Room.Control.TheQuayRealTimeManager;
import com.ph.bittelasia.meshtv.tv.santagrand.Room.Library.MeshTVRFragment;
import java.util.ArrayList;

@Layout(R.layout.sg_message_fragment)
public class TheQuayMessageInbox extends MeshTVRFragment<MeshMessage> implements MeshMessageListener<MeshMessage> {


    //=======================================Variable===============================================
    //---------------------------------------Constant-----------------------------------------------
    private static final String TAG = TheQuayMessageInbox.class.getSimpleName();
    //---------------------------------------Instance-----------------------------------------------
    public  TheQuayMessageListAdapter adapter;
    private MeshTVFragmentListener    cb;
    //----------------------------------------------------------------------------------------------
    //==============================================================================================



    @BindWidget(R.id.lv_list)
    public ListView lv_message;


    //======================================Life Cycle==============================================
    //----------------------------------------------------------------------------------------------
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        cb = (MeshTVFragmentListener)context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        cb=null;
    }

    @Override
    public void onResume() {
        super.onResume();
        TheQuayDAOManager.get().addListener(this);
        TheQuayDAOManager.get().retrieve(MeshMessage.class);
    }

    @Override
    public void onPause() {
        super.onPause();
        TheQuayDAOManager.get().removeListener(this);
    }
    //----------------------------------------------------------------------------------------------
    //==============================================================================================



    //======================================MeshTVRFragment=========================================
    //----------------------------------------------------------------------------------------------
    @Override
    public void draw(View v) {
        adapter = new TheQuayMessageListAdapter();
        Log.i(TAG,"@draw");
    }


    @Override
    public void updateDisplay(MeshMessage item) {
        Log.i(TAG,"@updateDisplay");
    }
    //----------------------------------------------------------------------------------------------
    //==============================================================================================



    //====================================MeshMessageListener=======================================
    //----------------------------------------------------------------------------------------------
    @SuppressWarnings("unchecked")
    @Override
    public void onInsertedBulk(ArrayList arrayList) {
        setEvent(arrayList);
        Log.i(TAG,"@onInsertedBulk -> size: "+arrayList.size());
    }

    @Override
    public void onDeletedBulk(ArrayList arrayList) {
        Log.i(TAG,"@onDeletedBulk -> size: "+arrayList.size());
    }

    @Override
    public void onRetrieved(ArrayList arrayList) {
        Log.i(TAG,"@onInsertedBulk -> size: "+arrayList.size());
        setEvent(arrayList);
    }

    @Override
    public void onCleared(Class aClass) {
        Log.i(TAG,"@onCleared -> class: "+aClass.getSimpleName());
    }

    @Override
    public void onDAONotFound(String s) {
        Log.i(TAG,"@onDAONotFound: "+s);
    }
    //----------------------------------------------------------------------------------------------
    //==============================================================================================


    //==========================================Method==============================================
    //----------------------------------------------------------------------------------------------
    private void setEvent(ArrayList arrayList)
    {
        try {
            adapter.performSort(arrayList);
            adapter.setClickedListener(o -> {
                if (cb != null) {
                    cb.onClicked(o);
                    readMessage((MeshMessage) o);
                    adapter.notifyDataSetChanged();
                }
            });
            lv_message.setAdapter(adapter);
            lv_message.setOnItemClickListener((parent, view, position, id) -> {
                adapter.setSelectedItem(position);
                adapter.notifyDataSetChanged();
            });
        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    //----------------------------------------------------------------------------------------------
    public void readMessage(MeshMessage message)
    {
        new ReadMessageTask(getContext(),  TheQuayDAOManager.get(), message).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }
    //----------------------------------------------------------------------------------------------
    //==============================================================================================

}
