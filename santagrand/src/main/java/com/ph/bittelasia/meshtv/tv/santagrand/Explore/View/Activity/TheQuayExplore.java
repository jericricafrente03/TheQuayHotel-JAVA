package com.ph.bittelasia.meshtv.tv.santagrand.Explore.View.Activity;


import android.util.Log;
import android.view.View;

import androidx.fragment.app.Fragment;

import com.ph.bittelasia.meshtv.tv.mtvlib.Core.Control.Annotation.View.Activity.ActivitySetting;
import com.ph.bittelasia.meshtv.tv.mtvlib.Core.Control.Annotation.View.Activity.AttachFragment;
import com.ph.bittelasia.meshtv.tv.mtvlib.Core.Control.Annotation.View.General.Layout;


import com.ph.bittelasia.meshtv.tv.mtvlib.Entity.Model.VC.MeshVC;
import com.ph.bittelasia.meshtv.tv.mtvlib.Entity.Model.VC.MeshVCCategory;
import com.ph.bittelasia.meshtv.tv.santagrand.Core.View.Activity.TheQuayActivity;
import com.ph.bittelasia.meshtv.tv.santagrand.Explore.View.Fragment.TheQuayExploreTabRFragment;

import com.ph.bittelasia.meshtv.tv.santagrand.Explore.View.Fragment.TheQuayExploreViewPagerRFragment;
import com.ph.bittelasia.meshtv.tv.santagrand.R;

@Layout(R.layout.sg_activity_explore)
@ActivitySetting()
public class TheQuayExplore extends TheQuayActivity implements TheQuayExploreViewPagerRFragment.PagerCallBack, TheQuayExploreTabRFragment.TabCallBack
{
    //===========================================Variable===========================================
    //-------------------------------------------Constant-------------------------------------------
    public static final String TAG = TheQuayExplore.class.getSimpleName();
    //----------------------------------------------------------------------------------------------
    //-------------------------------------------Fragment-------------------------------------------
    public TheQuayExploreViewPagerRFragment     vpFragment;
    public TheQuayExploreTabRFragment           tabFragment;
    //----------------------------------------------------------------------------------------------
    //-------------------------------------------Instance-------------------------------------------
    public MeshVC                               vc;
    public boolean                              isInitial = true;
    private boolean                             isDisplaying=false;
    //----------------------------------------------------------------------------------------------
    //==============================================================================================
    //============================================Activity==========================================
    //----------------------------------------------------------------------------------------------
    @Override
    public void onBackPressed()
    {
        try {
            Log.i(TAG, "(MeshTV-MVRS 0214) - onBackPressed\n");
            if (findViewById(R.id.ll_tabs).getVisibility() == View.GONE) {
                isTabMode = true;
                isDisplaying = false;
                findViewById(R.id.ll_tabs).setVisibility(View.VISIBLE);
                tabFragment = new TheQuayExploreTabRFragment();
                getSupportFragmentManager().beginTransaction().replace(R.id.ll_tabs, tabFragment, "VP").commitAllowingStateLoss();
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


    //======================================TheQuayActivity=========================================
    //----------------------------------------------------------------------------------------------
    @Override
    public String getActivityName() {
        return TAG;
    }
    //----------------------------------------------------------------------------------------------
    //==============================================================================================

    //============================================Fragment==========================================
    //----------------------------------------------------------------------------------------------
    @AttachFragment(container = R.id.ll_tabs,tag="tab",order = 1)
    public Fragment attachCategories()
    {
        try {
            Log.i(TAG, "(MeshTV-MVRS 0214) - Attaching categories\n");
            isTabMode = true;
            findViewById(R.id.ll_tabs).setVisibility(View.VISIBLE);
            tabFragment = new TheQuayExploreTabRFragment();
        }catch (Exception e)
        {
            e.printStackTrace();
        }
        return tabFragment;
    }
    //----------------------------------------------------------------------------------------------
    //==============================================================================================
    //==========================================TabCallBack=========================================
    @Override
    public void insertToTab(Object o)
    {Log.i(TAG," (MeshTV-MVRS 0214) - insertToTab - ==========================================\n");}
    @Override
    public void updateFromTab(Object o, int index)
    {Log.i(TAG," (MeshTV-MVRS 0214) - updateFromTab - ==========================================\n");}
    @Override
    public void deleteFromTab(Object o, int index)
    {Log.i(TAG," (MeshTV-MVRS 0214) - deleteFromTab - ==========================================\n");}
    @Override
    public void clickedFromTab(Object o, int index)
    {
        try {
            Log.i(TAG, " (MeshTV-MVRS 0214) - clickedFromTab - ==========================================\n");
            if (o instanceof MeshVCCategory) {
                isTabMode = false;
                isDisplaying = true;
                MeshVCCategory category = (MeshVCCategory) o;
                findViewById(R.id.ll_tabs).setVisibility(View.GONE);
                vpFragment = TheQuayExploreViewPagerRFragment.get(category.getId());
                getSupportFragmentManager().beginTransaction().replace(R.id.ll_main, vpFragment, "VP").commitAllowingStateLoss();
            }
        }catch (Exception e)
        {
            e.printStackTrace();
        }

    }
    @Override
    public void onTabsDrawn(boolean isUpdated)
    {
        try {
            Log.i(TAG, " (MeshTV-MVRS 0214) - onTabsDrawn - ==========================================\n");
            if(isUpdated)
            {
                isTabMode=true;
                isDisplaying = true;
                tabFragment = new TheQuayExploreTabRFragment();
                findViewById(R.id.ll_tabs).setVisibility(View.VISIBLE);
                getSupportFragmentManager().beginTransaction().replace(R.id.ll_tabs, tabFragment, "VP").commitAllowingStateLoss();
            }
            Log.i(TAG, " (MeshTV-MVRS 0214) - onTabsDrawn - replaced \n");
        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    //----------------------------------------------------------------------------------------------
    //==============================================================================================
    //=========================================PagerCallBack========================================
    //----------------------------------------------------------------------------------------------
    @Override
    public void insertToPager(Object o)
    {
        Log.i(TAG,"(MeshTV-MVRS 0214) - insertToPager!");
    }
    @Override
    public void updateFromPager(Object o, int index)
    {
        Log.i(TAG,"(MeshTV-MVRS 0214) - updateFromPager!");
    }

    @Override
    public void deleteFromPager(Object o, int index)
    {
        Log.i(TAG,"(MeshTV-MVRS 0214) - deleteFromPager!");
    }

    @Override
    public void selectedFromPager(Object o, int index)
    {
        Log.i(TAG,"(MeshTV-MVRS 0214) - selectedFromPager!");
    }

    @Override
    public void selectedFromTab(Object o, int index)
    {
        try {
            if (tabFragment != null) {
                if (isInitial) {
                    Log.i(TAG, "(MeshTV-MVRS 0214) - selectedFromTab Initial!");
                    isInitial = false;
                } else {
                    if (tabFragment.isClicked)
                        if (o instanceof MeshVCCategory) {
                            isTabMode = false;
                            isDisplaying = true;
                            MeshVCCategory category = (MeshVCCategory) o;
                            findViewById(R.id.ll_tabs).setVisibility(View.GONE);
                            vpFragment = TheQuayExploreViewPagerRFragment.get(category.getId());
                            getSupportFragmentManager().beginTransaction().replace(R.id.ll_main, vpFragment, "VP").commitAllowingStateLoss();
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

    }
    //----------------------------------------------------------------------------------------------
    //==============================================================================================
}
