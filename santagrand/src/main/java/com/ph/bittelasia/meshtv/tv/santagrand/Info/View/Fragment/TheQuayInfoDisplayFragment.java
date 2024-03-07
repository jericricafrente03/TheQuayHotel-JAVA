package com.ph.bittelasia.meshtv.tv.santagrand.Info.View.Fragment;

import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ph.bittelasia.meshtv.tv.mtvlib.Core.Control.Annotation.View.General.Layout;
import com.ph.bittelasia.meshtv.tv.mtvlib.Core.Control.Annotation.View.Widget.BindWidget;
import com.ph.bittelasia.meshtv.tv.mtvlib.Core.Control.Util.ResourceManager.MeshResourceManager;
import com.ph.bittelasia.meshtv.tv.mtvlib.Entity.Control.Listener.MeshFacilityListener;
import com.ph.bittelasia.meshtv.tv.mtvlib.Entity.Model.Facility.MeshFacility;
import com.ph.bittelasia.meshtv.tv.santagrand.Room.Library.MeshTVRFragment;

import com.ph.bittelasia.meshtv.tv.santagrand.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

@Layout(R.layout.sg_branch_frag)
public class TheQuayInfoDisplayFragment extends MeshTVRFragment<MeshFacility>
    implements MeshFacilityListener
{
    //============================================Variable==========================================
    //--------------------------------------------Constant------------------------------------------
    public static final String TAG = TheQuayInfoDisplayFragment.class.getSimpleName();
    //----------------------------------------------------------------------------------------------
    //--------------------------------------------View----------------------------------------------
    @BindWidget(R.id.tv_title)
    public TextView tv_name;
    @BindWidget(R.id.tv_desc)
    public TextView tv_desc;
    @BindWidget(R.id.iv_icon)
    public ImageView iv_icon;
    //----------------------------------------------------------------------------------------------
    //---------------------------------------------Instance-----------------------------------------
    MeshFacility facility;
    //----------------------------------------------------------------------------------------------
    //==============================================================================================
    //============================================Static============================================
    public static TheQuayInfoDisplayFragment geT(MeshFacility facility)
    {
        Log.i(TAG,"(MeshTV-MVRS 0215) - geT() =================================================\n");
        TheQuayInfoDisplayFragment fragment = new TheQuayInfoDisplayFragment();
        fragment.setFacility(facility);
        Log.i(TAG,"(MeshTV-MVRS 0215) - geT() =================================================\n");
        return fragment;
    }
    //==============================================================================================
    //=============================================LifeCycle========================================
    @Override
    public void onResume()
    {
        super.onResume();
        Log.i(TAG,"(MeshTV-MVRS 0215) - onResume() =================================================\n");
        Log.i(TAG,"(MeshTV-MVRS 0215) - onResume() =================================================\n");
    }
    @Override
    public void onPause()
    {
        super.onPause();
        Log.i(TAG,"(MeshTV-MVRS 0215) - onPause() =================================================\n");
        Log.i(TAG,"(MeshTV-MVRS 0215) - onPause() =================================================\n");
    }
    //==============================================================================================

    //==============================================Method==========================================
    //----------------------------------------------Action------------------------------------------
    public void display()
    {
        try {
            if (facility != null && tv_desc != null && tv_name != null && iv_icon != null) {
                try {
                    Log.i(TAG, "(MeshTV-MVRS 0215) - display() Displaying\n");
                    tv_name.setText(facility.getItem_name());
                    tv_desc.setText(Html.fromHtml(facility.getItem_description()));
                    try {
                        Picasso.get().load(facility.getImg_uri()).into(iv_icon);
                    } catch (Exception e) {
                        MeshResourceManager.displayLiveImageFor(getActivity(), iv_icon, facility.getImg_uri());
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
    //----------------------------------------------------------------------------------------------
    //----------------------------------------------Getter------------------------------------------
    public MeshFacility getFacility() {
        return facility;
    }
    //----------------------------------------------------------------------------------------------
    //----------------------------------------------Setter------------------------------------------
    public void setFacility(MeshFacility facility) {
        this.facility = facility;
    }
    //----------------------------------------------------------------------------------------------
    //==============================================================================================

    //=====================================TheQuayInfoDisplayFragment===============================
    @Override
    public void draw(View v)
    {
        display();
    }

    @Override
    public void updateDisplay(MeshFacility item)
    {
        display();
    }


    //==============================================================================================
    //=====================================MeshFacilityListener=============================
    @Override
    public void onInsertedBulk(ArrayList arrayList)
    {
        try {
            for (Object o : arrayList) {
                if (o instanceof MeshFacility) {
                    MeshFacility vc = (MeshFacility) o;
                    if (vc.getId() == getItem().getId()) {
                        setItem(vc);
                        break;
                    }
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
}
