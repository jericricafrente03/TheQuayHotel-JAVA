package com.ph.bittelasia.meshtv.tv.santagrand.Info.View.Fragment;

import com.ph.bittelasia.meshtv.tv.mtvlib.Core.Control.Annotation.View.General.Layout;
import com.ph.bittelasia.meshtv.tv.mtvlib.Entity.Control.API.GET.Listener.MeshGetFacilityCategoryListener;
import com.ph.bittelasia.meshtv.tv.mtvlib.Entity.Control.Listener.MeshFacilityListener;
import com.ph.bittelasia.meshtv.tv.mtvlib.Entity.Model.Facility.MeshFacility;
import com.ph.bittelasia.meshtv.tv.mtvlib.Entity.Model.Facility.MeshFacilityCategory;
import com.ph.bittelasia.meshtv.tv.mtvlib.Entity.Model.Filter.MeshCategoryFilter;
import com.ph.bittelasia.meshtv.tv.santagrand.Info.Model.TheQuayInfoRPagerAdapter;
import com.ph.bittelasia.meshtv.tv.santagrand.Room.Control.TheQuayDAOManager;
import com.ph.bittelasia.meshtv.tv.santagrand.Room.Model.TheQuayViewPagerAdapter;
import com.ph.bittelasia.meshtv.tv.santagrand.Room.View.Fragment.Core.PagerAnnotation;
import com.ph.bittelasia.meshtv.tv.santagrand.Room.View.Fragment.Core.TheQuayViewPagerRFragment;
import com.ph.bittelasia.meshtv.tv.santagrand.R;
import java.util.ArrayList;
@Layout(R.layout.sg_vp_fragment)
@PagerAnnotation(vpLayout = R.id.vp_pager)
public class TheQuayInfoViewPagerRFragment extends TheQuayViewPagerRFragment<MeshFacility> implements MeshFacilityListener
{
    //===============================================Variable=======================================
    //-----------------------------------------------Constant---------------------------------------
    public static final String TAG = TheQuayInfoViewPagerRFragment.class.getSimpleName();
    //----------------------------------------------------------------------------------------------
    //-------------------------------------------Instance-------------------------------------------
    int category = -1;
    //----------------------------------------------------------------------------------------------
    //==============================================================================================
    //================================================Static========================================
    public static TheQuayInfoViewPagerRFragment get(MeshFacilityCategory category)
    {
        TheQuayInfoViewPagerRFragment fragment = new TheQuayInfoViewPagerRFragment();
        fragment.setCategory(category.getId());
        return fragment;
    }
    //==============================================================================================
    //==========================================Method==============================================
    //------------------------------------------Getter----------------------------------------------
    public int getCategory() {
        return category;
    }
    //----------------------------------------------------------------------------------------------
    //------------------------------------------Setter----------------------------------------------
    public void setCategory(int category) {
        this.category = category;
    }
    //----------------------------------------------------------------------------------------------
    //==============================================================================================
    //=============================================LifeCycle========================================

    @Override
    public void onResume()
    {
        try {
            super.onResume();
            TheQuayDAOManager.get().addListener(this);
            TheQuayDAOManager.get().retrieve(MeshFacility.class);
            update();
        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void onPause()
    {
        try {
            super.onPause();
            TheQuayDAOManager.get().removeListener(this);
        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    //==============================================================================================
    //=======================================TheQuayViewPagerRFragment==============================
    @Override
    public void setCurrent(int current) { }
    @Override
    public void onPagesPopulated()
    {

    }
    @Override
    public boolean isSame(MeshFacility o1, MeshFacility o2)
    {
        return o1.getId()==o2.getId();
    }
    @Override
    public TheQuayViewPagerAdapter setAdapter(ArrayList<MeshFacility> items)
    {
        return new TheQuayInfoRPagerAdapter(getChildFragmentManager(),items);
    }
    //==============================================================================================
    //============================================Method============================================
    public void update()
    {
//        MeshCategoryFilter filter = new MeshCategoryFilter();
//        filter.setId(getCategory());
//        TheQuayDAOManager.get().filter(MeshFacility.class,filter);
    }
    //==============================================================================================
    //=================================MeshFacilityListener=================================

    @Override
    public void onInsertedBulk(ArrayList arrayList)
    {
        try {
            ArrayList<MeshFacility> item = new ArrayList<>();
            if (arrayList != null && arrayList.size() > 0) {
                if (arrayList.get(0) instanceof MeshFacility) {
                    for (int x = 0; x < arrayList.size(); x++) {
                        MeshFacility facility = (MeshFacility) arrayList.get(x);
                        if (facility.getCategory_id() == getCategory()) {
                            item.add(facility);
                        }
                    }
                    setObjects(item);
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
        update();
    }

    @Override
    public void onRetrieved(ArrayList arrayList)
    {
        try {
            ArrayList<MeshFacility> items = new ArrayList<>();
            if (arrayList != null && arrayList.size() > 0) {
                if (arrayList.get(0) instanceof MeshFacility) {
                    for (int x = 0; x < arrayList.size(); x++) {
                        MeshFacility facility = (MeshFacility) arrayList.get(x);
                        if (facility.getCategory_id() == getCategory()) {
                            items.add(facility);
                        }
                    }
                    setObjects(items);
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
        update();
    }

    @Override
    public void onDAONotFound(String s) {

    }

    //==============================================================================================
    //======================================MeshTVRFragment=========================================
    @Override
    public void updateDisplay(MeshFacility item)
    {
        update();
    }
    //==============================================================================================1
}
