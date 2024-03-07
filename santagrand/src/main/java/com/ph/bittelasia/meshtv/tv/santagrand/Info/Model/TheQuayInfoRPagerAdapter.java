package com.ph.bittelasia.meshtv.tv.santagrand.Info.Model;



import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.ph.bittelasia.meshtv.tv.mtvlib.Entity.Model.Facility.MeshFacility;
import com.ph.bittelasia.meshtv.tv.mtvlib.Entity.Model.Facility.MeshFacility;
import com.ph.bittelasia.meshtv.tv.santagrand.Explore.Model.TheQuayExplorerRPagerAdapter;
import com.ph.bittelasia.meshtv.tv.santagrand.Info.View.Fragment.TheQuayInfoDisplayFragment;
import com.ph.bittelasia.meshtv.tv.santagrand.Room.Model.TheQuayViewPagerAdapter;

import java.util.ArrayList;

public class TheQuayInfoRPagerAdapter extends TheQuayViewPagerAdapter<MeshFacility>
{
    //========================================Variable==============================================
    //----------------------------------------Constant----------------------------------------------
    public static final String TAG = TheQuayExplorerRPagerAdapter.class.getSimpleName();
    //----------------------------------------------------------------------------------------------
    //==============================================================================================

    //=============================================Constructor======================================
    public TheQuayInfoRPagerAdapter(FragmentManager fm, ArrayList<MeshFacility> items) {
        super(fm, items);
    }
    //=========================================================================================

    //==========================================TheQuayViewPagerAdapter=============================
    @Override
    public Fragment getFragment(MeshFacility object) {
        return TheQuayInfoDisplayFragment.geT(object);
    }

    @Override
    public boolean isSame(MeshFacility o1, MeshFacility o2) {
        return o1.getId()==o2.getId();
    }
    //==============================================================================================
}
