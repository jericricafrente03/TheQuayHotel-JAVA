package com.ph.bittelasia.meshtv.tv.santagrand.Info.View.Activity;


import android.util.Log;
import android.view.View;

import androidx.fragment.app.Fragment;

import com.ph.bittelasia.meshtv.tv.mtvlib.Core.Control.Annotation.View.Activity.ActivitySetting;
import com.ph.bittelasia.meshtv.tv.mtvlib.Core.Control.Annotation.View.Activity.AttachFragment;
import com.ph.bittelasia.meshtv.tv.mtvlib.Core.Control.Annotation.View.General.Layout;
import com.ph.bittelasia.meshtv.tv.mtvlib.Entity.Model.Facility.MeshFacility;
import com.ph.bittelasia.meshtv.tv.mtvlib.Entity.Model.Facility.MeshFacilityCategory;
import com.ph.bittelasia.meshtv.tv.mtvlib.Entity.Model.VC.MeshVCCategory;
import com.ph.bittelasia.meshtv.tv.santagrand.Core.View.Activity.TheQuayActivity;
import com.ph.bittelasia.meshtv.tv.santagrand.Explore.View.Fragment.TheQuayExploreTabRFragment;
import com.ph.bittelasia.meshtv.tv.santagrand.Explore.View.Fragment.TheQuayExploreViewPagerRFragment;
import com.ph.bittelasia.meshtv.tv.santagrand.Info.View.Fragment.TheQuayInfoTabRFragment;
import com.ph.bittelasia.meshtv.tv.santagrand.Info.View.Fragment.TheQuayInfoViewPagerRFragment;
import com.ph.bittelasia.meshtv.tv.santagrand.R;
//
@Layout(R.layout.sg_activity_info)
@ActivitySetting()
public class TheQuayInfo extends TheQuayActivity
        implements
        TheQuayInfoTabRFragment.TabCallBack,
        TheQuayInfoViewPagerRFragment.PagerCallBack
{
    //==============================================Variable========================================
    //----------------------------------------------Constant----------------------------------------
    public static final String TAG = TheQuayInfo.class.getSimpleName();
    //----------------------------------------------------------------------------------------------
    //----------------------------------------------Fragment----------------------------------------
    private TheQuayInfoTabRFragment tabRFragment;
    private TheQuayInfoViewPagerRFragment pagerFragment;
    //----------------------------------------------------------------------------------------------
    //----------------------------------------------Instance----------------------------------------
    public  MeshFacilityCategory          category;
    public  MeshFacility                  facility;
    public  boolean                       isInitial = true;
    private boolean                       isDisplaying=false;
    //----------------------------------------------------------------------------------------------
    //==============================================================================================
    //==============================================Activity========================================
    //----------------------------------------------------------------------------------------------
    @Override
    public void onBackPressed()
    {
        try {
            if (findViewById(R.id.ll_tabs).getVisibility() == View.GONE) {
                isTabMode = true;
                isDisplaying = false;
                findViewById(R.id.ll_tabs).setVisibility(View.VISIBLE);
                tabRFragment = new TheQuayInfoTabRFragment();
                getSupportFragmentManager().beginTransaction().replace(R.id.ll_tabs, tabRFragment, "VP").commitAllowingStateLoss();
                Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.ll_main);
                if (fragment != null) {
                    getSupportFragmentManager().beginTransaction().remove(fragment).commit();
                }
            } else {
                super.onBackPressed();
            }
        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    //----------------------------------------------------------------------------------------------
    //==============================================================================================
    //==============================================Fragment========================================
    //----------------------------------------------------------------------------------------------
    @AttachFragment(container = R.id.ll_tabs,tag="tab",order = 1)
    public Fragment attachCategories()
    {
        try {
            isTabMode = true;
            findViewById(R.id.ll_tabs).setVisibility(View.VISIBLE);
            tabRFragment = new TheQuayInfoTabRFragment();
        }catch (Exception e)
        {
            e.printStackTrace();
        }
        return tabRFragment;
    }
    //----------------------------------------------------------------------------------------------
    //==============================================================================================

    //==========================================TheQuayActivity=====================================
    //----------------------------------------------------------------------------------------------
    @Override
    public String getActivityName() {
        return TAG;
    }
    //----------------------------------------------------------------------------------------------
    //==============================================================================================

    //==========================================TheQuayInfoTabRFragment=============================
    @Override
    public void insertToTab(Object o) {

    }

    @Override
    public void updateFromTab(Object o, int index) {

    }

    @Override
    public void deleteFromTab(Object o, int index) {

    }

    @Override
    public void clickedFromTab(Object o, int index)
    {
        try {
            Log.i(TAG, "(MeshTV-MVRS 03-12-19) - Clicked TAB:" + index + "\n");
            if (o instanceof MeshFacilityCategory) {
                isTabMode = false;
                isDisplaying = true;
                MeshFacilityCategory category = (MeshFacilityCategory) o;
                findViewById(R.id.ll_tabs).setVisibility(View.GONE);
                pagerFragment = TheQuayInfoViewPagerRFragment.get(category);
                getSupportFragmentManager().beginTransaction().replace(R.id.ll_main, pagerFragment, "VP").commitAllowingStateLoss();
            }
        }catch (Exception e)
        {
            e.printStackTrace();
        }
        Log.i(TAG," (MeshTV-MVRS 0214) - clickedFromTab - ==========================================\n");

    }

    @Override
    public void selectedFromTab(Object o, int index)
    {
        try {
            Log.i(TAG, "(MeshTV-MVRS 03-12-19) - Selected TAB:" + index + "\n");
            if (tabRFragment != null) {
                if (isInitial) {
                    Log.i(TAG, "(MeshTV-MVRS 0214) - selectedFromTab Initial!");
                    isInitial = false;
                } else {
                    if (tabRFragment.isClicked)
                        if (o instanceof MeshFacilityCategory) {
                            isTabMode = false;
                            isDisplaying = true;
                            MeshFacilityCategory category = (MeshFacilityCategory) o;
                            findViewById(R.id.ll_tabs).setVisibility(View.GONE);
                            pagerFragment = TheQuayInfoViewPagerRFragment.get(category);
                            getSupportFragmentManager().beginTransaction().replace(R.id.ll_main, pagerFragment, "VP").commitAllowingStateLoss();
                        }
                }
            }
        }catch (Exception e)
        {
            e.printStackTrace();
        }

    }

    @Override
    public void clear()
    {
        Log.i(TAG,"(MeshTV-MVRS 03-12-19) - clear TAB:\n");
    }

    @Override
    public void onTabsDrawn(boolean isUpdated)
    {
        try {
            Log.i(TAG, " (MeshTV-MVRS 0214) - onTabsDrawn - ==========================================\n");
            if(isUpdated) {
                if(!isDisplaying) {
                    isTabMode = true;
                    isDisplaying = true;
                    tabRFragment = new TheQuayInfoTabRFragment();
                    findViewById(R.id.ll_tabs).setVisibility(View.VISIBLE);
                    getSupportFragmentManager().beginTransaction().replace(R.id.ll_tabs, tabRFragment, "VP").commitAllowingStateLoss();
                }
                Log.i(TAG, " (MeshTV-MVRS 0214) - onTabsDrawn - replaced \n");
            }
        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    //==============================================================================================
    //=============================================PagerCallBack====================================
    @Override
    public void insertToPager(Object o)
    {
        Log.i(TAG,"(MeshTV-MVRS 03-12-19) - insertToPager PAGER:\n");
    }

    @Override
    public void updateFromPager(Object o, int index)
    {
        Log.i(TAG,"(MeshTV-MVRS 03-12-19) - updateFromPager PAGER:\n");
    }

    @Override
    public void deleteFromPager(Object o, int index)
    {
        Log.i(TAG,"(MeshTV-MVRS 03-12-19) - deleteFromPager PAGER:\n");
    }

    @Override
    public void selectedFromPager(Object o, int index)
    {
        Log.i(TAG,"(MeshTV-MVRS 03-12-19) - selectedFromPager PAGER:"+index+"\n");
    }

    //==============================================================================================
}
