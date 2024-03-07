package com.ph.bittelasia.meshtv.tv.santagrand.Room.View.Fragment.Core;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.ph.bittelasia.meshtv.tv.mtvlib.Core.Control.Annotation.View.General.Layout;
import com.ph.bittelasia.meshtv.tv.mtvlib.Core.Control.Annotation.View.Widget.BindWidget;
import com.ph.bittelasia.meshtv.tv.mtvlib.Core.Control.Preference.Control.Manager.MeshTVPreferenceManager;
import com.ph.bittelasia.meshtv.tv.mtvlib.Entity.Control.API.GET.Listener.MeshGetCustomerListener;
import com.ph.bittelasia.meshtv.tv.mtvlib.Entity.Control.API.GET.Request.MeshGetCustomer;
import com.ph.bittelasia.meshtv.tv.mtvlib.Entity.Control.API.Manager.MeshAPIManager;
import com.ph.bittelasia.meshtv.tv.mtvlib.Entity.Control.Listener.MeshGuestListener;
import com.ph.bittelasia.meshtv.tv.mtvlib.Entity.Model.Guest.MeshGuest;
import com.ph.bittelasia.meshtv.tv.santagrand.R;
import com.ph.bittelasia.meshtv.tv.santagrand.Room.Control.TheQuayDAOManager;
import com.ph.bittelasia.meshtv.tv.santagrand.Room.Library.MeshTVRFragment;

import java.util.ArrayList;

@Layout(R.layout.guest_fragment)
public class TheQuayGuestFragment extends MeshTVRFragment<MeshGuest>
        implements
            MeshGuestListener,
            MeshGetCustomerListener
{
    //============================================Variable==========================================
    //--------------------------------------------Constant------------------------------------------
    public static final String TAG = TheQuayGuestFragment.class.getSimpleName();
    //----------------------------------------------------------------------------------------------
    //-------------------------------------------Instance-------------------------------------------
    private GuestProfileCallBack cb;
    //----------------------------------------------------------------------------------------------
    //---------------------------------------------View---------------------------------------------
    @BindWidget(R.id.tv_sgguest)
    public TextView tv_sgguest;
    @BindWidget(R.id.tv_sgroom)
    public TextView tv_sgroom;
    //----------------------------------------------------------------------------------------------
    //==============================================================================================
    //============================================LifeCycle=========================================


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        cb =(GuestProfileCallBack)context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        cb=null;
    }

    @Override
    public void onResume()
    {
        super.onResume();
        Log.i(TAG,"(SG-TQH) - onResume()\n");
        requestData();

    }
    @Override
    public void onPause()
    {
        try {
            super.onPause();
            TheQuayDAOManager.get().removeListener(this);
            MeshAPIManager.get().removeListener(this);
        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    //==============================================================================================
    //=============================================Method===========================================
    //---------------------------------------------Action-------------------------------------------
    public void requestData()
    {
        try {
            TheQuayDAOManager.get().addListener(this);
            MeshAPIManager.get().addListener(this);
            MeshAPIManager.get().request(getActivity(), new MeshGetCustomer(), true);
        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    private void display()
    {
        try {
            if (getItem() != null) {
                String name = getItem().getFirstname() + " " + getItem().getLastname();
                if (name.length() > 20) {
                    name = getItem().getFirstname().substring(0, 0) + ". " + getItem().getLastname();
                }
                if (name.length() > 20) {
                    name = name.substring(0, 19);
                }
                if (cb != null) {
                    cb.getName(getItem().getFirstname());
                }
                tv_sgguest.setText((getItem().getFirstname() + " " + getItem().getLastname()));
            }
        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    //----------------------------------------------------------------------------------------------
    //==============================================================================================
    //=======================================MeshGetCustomerListener================================
    @Override
    public void onResult(Class aClass, ArrayList<Object> arrayList) {
        try {
            Log.i(TAG, "(SG-TQH) - RETRIEVED FROM API:" + arrayList.size() + "\n");
            TheQuayDAOManager.get().insert(arrayList);
        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void onFailed(Class aClass, String s) {

    }
    //==============================================================================================
    //=========================================MeshGuestListener====================================
    @Override
    public void onInsertedBulk(ArrayList arrayList)
    {
        try {
            if (arrayList.size() > 0) {
                Object sample = arrayList.get(0);
                if (sample instanceof MeshGuest) {
                    MeshGuest guest = (MeshGuest) sample;
                    setItem(guest);
                    display();
                }
            }
        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    @Override
    public void onDeletedBulk(ArrayList arrayList)
    {

    }

    @Override
    public void onRetrieved(ArrayList arrayList)
    {
        try {
            if (arrayList.size() > 0) {
                Object sample = arrayList.get(0);
                if (sample instanceof MeshGuest) {
                    MeshGuest guest = (MeshGuest) sample;
                    setItem(guest);
                    display();
                }
            }
        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void onCleared(Class aClass)
    {

    }

    @Override
    public void onDAONotFound(String s)
    {

    }
    //==============================================================================================
    //=========================================MeshTVRFragment======================================
    @Override
    public void draw(View v)
    {
        try {
            tv_sgroom.setText(MeshTVPreferenceManager.getRoom(getActivity()));
        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    @Override
    public void updateDisplay(MeshGuest item)
    {
        try {
            display();
        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    //==============================================================================================

    //==========================================CallBack============================================
    //----------------------------------------------------------------------------------------------
    public interface GuestProfileCallBack
    {
        void getName(String name);
    }
}
