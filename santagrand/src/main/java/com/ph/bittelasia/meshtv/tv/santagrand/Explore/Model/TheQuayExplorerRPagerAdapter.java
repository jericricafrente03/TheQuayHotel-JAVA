package com.ph.bittelasia.meshtv.tv.santagrand.Explore.Model;



import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.ph.bittelasia.meshtv.tv.mtvlib.Entity.Model.VC.MeshVC;
import com.ph.bittelasia.meshtv.tv.santagrand.Explore.View.Fragment.TheQuayExploreDisplayFragment;
import com.ph.bittelasia.meshtv.tv.santagrand.Room.Model.TheQuayViewPagerAdapter;

import java.util.ArrayList;

public class TheQuayExplorerRPagerAdapter extends TheQuayViewPagerAdapter<MeshVC>
{
    //========================================Variable==============================================
    //----------------------------------------Constant----------------------------------------------
    public static final String TAG = TheQuayExplorerRPagerAdapter.class.getSimpleName();
    //----------------------------------------------------------------------------------------------
    //==============================================================================================

    //======================================Constructor=============================================
    public TheQuayExplorerRPagerAdapter(FragmentManager fm, ArrayList<MeshVC> items)
    {
        super(fm, items);
    }
    //==============================================================================================
    //=====================================TheQuayViewPagerAdapter==================================
    @Override
    public Fragment getFragment(MeshVC object)
    {


        return TheQuayExploreDisplayFragment.geT(object);
    }

    @Override
    public boolean isSame(MeshVC o1, MeshVC o2) {
        return o1.getId()==o2.getId();
    }
    //==============================================================================================
}
