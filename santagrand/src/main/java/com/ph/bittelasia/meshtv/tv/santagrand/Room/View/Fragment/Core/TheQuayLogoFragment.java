package com.ph.bittelasia.meshtv.tv.santagrand.Room.View.Fragment.Core;

import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.ph.bittelasia.meshtv.tv.mtvlib.Core.Control.Annotation.View.General.Layout;
import com.ph.bittelasia.meshtv.tv.mtvlib.Core.Control.Annotation.View.Widget.BindWidget;
import com.ph.bittelasia.meshtv.tv.mtvlib.Core.Control.Util.ResourceManager.MeshResourceManager;
import com.ph.bittelasia.meshtv.tv.mtvlib.Entity.Control.API.GET.Listener.MeshGetConfigListener;
import com.ph.bittelasia.meshtv.tv.mtvlib.Entity.Control.API.GET.Request.MeshGetConfig;
import com.ph.bittelasia.meshtv.tv.mtvlib.Entity.Control.API.Manager.MeshAPIManager;
import com.ph.bittelasia.meshtv.tv.mtvlib.Entity.Control.Listener.MeshConfigListener;
import com.ph.bittelasia.meshtv.tv.mtvlib.Entity.Model.Config.MeshConfig;
import com.ph.bittelasia.meshtv.tv.mtvlib.Entity.Model.Guest.MeshGuest;
import com.ph.bittelasia.meshtv.tv.santagrand.R;
import com.ph.bittelasia.meshtv.tv.santagrand.Room.Control.TheQuayDAOManager;
import com.ph.bittelasia.meshtv.tv.santagrand.Room.Library.MeshTVRFragment;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

@Layout(R.layout.logo_fragment)
public class TheQuayLogoFragment extends MeshTVRFragment<MeshConfig>
    implements
        MeshConfigListener,
        MeshGetConfigListener
{
    //============================================Variable==========================================
    //--------------------------------------------Constant------------------------------------------
    public static final String TAG = TheQuayLogoFragment.class.getSimpleName();
    //----------------------------------------------------------------------------------------------
    //-----------------------------------------------View-------------------------------------------
    @BindWidget(R.id.iv_logo)
    public ImageView iv_logo;
    //----------------------------------------------------------------------------------------------
    //==============================================================================================
    //============================================LifeCycle=========================================

    @Override
    public void onResume() {
        super.onResume();
        requestData();
    }

    @Override
    public void onPause() {
        super.onPause();
        TheQuayDAOManager.get().removeListener(this);
        MeshAPIManager.get().removeListener(this);

    }

    //==============================================================================================
    //=============================================Method===========================================
    //---------------------------------------------Action-------------------------------------------
    private void display()
    {
        try {
            if (iv_logo != null && getItem() != null) {
                try {
                    try {
                        Picasso.get().load(getItem().getLogo()).into(iv_logo);
                    } catch (Exception e) {
                        MeshResourceManager.displayLiveImageFor(getActivity(), iv_logo, getItem().getLogo());
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    public void requestData(){
        try {
            TheQuayDAOManager.get().addListener(this);
            MeshAPIManager.get().addListener(this);
            MeshAPIManager.get().request(getActivity(), new MeshGetConfig(), true);
        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    //----------------------------------------------------------------------------------------------
    //==============================================================================================
    //=========================================MeshTVRFragment======================================
    @Override
    public void draw(View v) {
            display();
    }

    @Override
    public void updateDisplay(MeshConfig item) {
            display();
    }
    //==============================================================================================
    //=======================================MeshGetConfigListener==================================
    @Override
    public void onResult(Class aClass, ArrayList<Object> arrayList) {
        try {
            TheQuayDAOManager.get().insert(arrayList);
            if (arrayList.size() > 0) {
                MeshConfig config = (MeshConfig) arrayList.get(0);
                setItem(config);
                display();
            }
        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void onFailed(Class aClass, String s) {

    }
    //==============================================================================================
    //=========================================MeshConfigListener===================================
    @Override
    public void onInsertedBulk(ArrayList arrayList)
    {
        try {
            if (arrayList.size() > 0) {
                Object sample = arrayList.get(0);
                if (sample instanceof MeshConfig) {
                    MeshConfig config = (MeshConfig) sample;
                    setItem(config);
                    display();
                }
            }
        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void onDeletedBulk(ArrayList arrayList) {

    }

    @Override
    public void onRetrieved(ArrayList arrayList)
    {
        try {
            if (arrayList.size() > 0) {
                Object sample = arrayList.get(0);
                if (sample instanceof MeshConfig) {
                    MeshConfig config = (MeshConfig) sample;
                    setItem(config);
                    display();
                }
            }
            Log.e("steward", "@onRetrieved: " + arrayList.size() + "");
        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void onCleared(Class aClass) {

    }

    @Override
    public void onDAONotFound(String s) {

    }


    //==============================================================================================
}
