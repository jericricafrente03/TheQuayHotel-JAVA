package com.ph.bittelasia.meshtv.tv.santagrand.Explore.View.Fragment;

import android.util.Log;

import com.ph.bittelasia.meshtv.tv.mtvlib.Core.Control.Annotation.View.General.Layout;
import com.ph.bittelasia.meshtv.tv.mtvlib.Entity.Control.API.GET.API.GetRequestTask;
import com.ph.bittelasia.meshtv.tv.mtvlib.Entity.Control.Listener.MeshVCListener;
import com.ph.bittelasia.meshtv.tv.mtvlib.Entity.Model.Filter.MeshCategoryFilter;
import com.ph.bittelasia.meshtv.tv.mtvlib.Entity.Model.VC.MeshVC;
import com.ph.bittelasia.meshtv.tv.santagrand.Explore.Model.TheQuayExplorerRPagerAdapter;
import com.ph.bittelasia.meshtv.tv.santagrand.Room.Control.TheQuayDAOManager;
import com.ph.bittelasia.meshtv.tv.santagrand.Room.Model.TheQuayViewPagerAdapter;
import com.ph.bittelasia.meshtv.tv.santagrand.Room.View.Fragment.Core.PagerAnnotation;

import com.ph.bittelasia.meshtv.tv.santagrand.Room.View.Fragment.Core.TheQuayViewPagerRFragment;

import java.util.ArrayList;
import com.ph.bittelasia.meshtv.tv.santagrand.R;

@Layout(R.layout.sg_vp_fragment)
@PagerAnnotation(vpLayout = R.id.vp_pager)
public class TheQuayExploreViewPagerRFragment extends TheQuayViewPagerRFragment<MeshVC> implements MeshVCListener
{
    //===========================================Variable===========================================
    //-------------------------------------------Constant-------------------------------------------
    public static final String TAG = TheQuayExploreViewPagerRFragment.class.getSimpleName();
    //----------------------------------------------------------------------------------------------
    //-------------------------------------------Instance-------------------------------------------
    int category = -1;
    //----------------------------------------------------------------------------------------------
    //==============================================================================================
    //===========================================Static=============================================
    public static TheQuayExploreViewPagerRFragment get(int category)
    {
        Log.i(TAG,"(MeshTV-MVRS 0215) get("+category+")\n");
        TheQuayExploreViewPagerRFragment fragment = new TheQuayExploreViewPagerRFragment();
        fragment.setCategory(category);
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
    //=========================================LifeCycle============================================

    @Override
    public void onResume()
    {
        try {
            super.onResume();
            Log.i(TAG, "(MeshTV-MVRS 0215) - onResume()\n");
            TheQuayDAOManager.get().addListener(this);
            TheQuayDAOManager.get().retrieve(MeshVC.class);
            update();
            Log.i("steward-pager", "onResume()");
        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void onPause()
    {
        super.onPause();
        Log.i(TAG,"(MeshTV-MVRS 0215) - onPause()\n");
        TheQuayDAOManager.get().removeListener(this);
        Log.i("steward-pager","onPause()");
    }

    //==============================================================================================
    //===================================TheQuayViewPagerRFragment==================================
    @Override
    public void onInsertedBulk(ArrayList arrayList)
    {
        try {
            Log.i(TAG, "(MeshTV-MVRS 0215) - insertedBulk()\n");
            if (arrayList != null && arrayList.size() > 0) {
                if (arrayList.get(0) instanceof MeshVC) {
                    ArrayList<MeshVC> vcs = new ArrayList<>();
                    for (int x = 0; x < arrayList.size(); x++) {
                        if (((MeshVC) arrayList.get(x)).getCategory_id() == getCategory()) {
                            vcs.add((MeshVC) arrayList.get(x));
                        }
                    }
                    setObjects(vcs);
                }
                Log.i("steward-pager", "onInsertedBulk()");
            }
        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    @Override
    public void onDeletedBulk(ArrayList arrayList)
    {
        Log.i(TAG,"(MeshTV-MVRS 0215) - onDeletedBulk()\n");
        update();
        Log.i("steward-pager","onDeletedBulk()");
    }
    @Override
    public void onRetrieved(ArrayList arrayList)
    {
        try {
            Log.i(TAG, "(MeshTV-MVRS 0215) - onRetrieved()\n");
            if (arrayList != null && arrayList.size() > 0) {
                if (arrayList.get(0) instanceof MeshVC) {
                    ArrayList<MeshVC> vcs = new ArrayList<>();
                    for (int x = 0; x < arrayList.size(); x++) {
                        if (((MeshVC) arrayList.get(x)).getCategory_id() == getCategory()) {
                            vcs.add((MeshVC) arrayList.get(x));
                        }
                    }
                    setObjects(vcs);
                }
                Log.i("steward-pager", "onRetrieved()");
            }
        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    @Override
    public void onCleared(Class aClass)
    {
        Log.i(TAG,"(MeshTV-MVRS 0215) - onCleared()\n");
        update();
        Log.i("steward-pager","onCleared()");
    }
    @Override
    public void onDAONotFound(String s)
    {
        Log.i(TAG,"(MeshTV-MVRS 0215) - onDAONotFound()\n");
    }
    //==============================================================================================
    //===========================================Method=============================================
    //-------------------------------------------Action---------------------------------------------
    public void update()
    {
//        Log.i(TAG,"(MeshTV-MVRS 0215) - update()\n");
//        MeshCategoryFilter filter = new MeshCategoryFilter();
//        filter.setId(getCategory());
//        TheQuayDAOManager.get().filter(MeshVC.class,filter);
//        Log.i("steward-pager","update()");
    }
    //----------------------------------------------------------------------------------------------
    //==============================================================================================
    //=======================================ViewPagerFragment======================================
    @Override
    public void setCurrent(int current)
    {
        Log.i(TAG,"(MeshTV-MVRS 0215) - setCurrent()\n");
        Log.i("steward-pager","setCurrent()");
    }

    @Override
    public void onPagesPopulated()
    {
        Log.i(TAG,"(MeshTV-MVRS 0215) - onPagesPopulated()\n");
        Log.i("steward-pager","onPagesPopulated()");
    }

    @Override
    public boolean isSame(MeshVC o1, MeshVC o2)
    {
        Log.i(TAG,"(MeshTV-MVRS 0215) - isSame()\n");
        return o1.getId()==o2.getId();
    }

    @Override
    public TheQuayViewPagerAdapter setAdapter(ArrayList<MeshVC> items)
    {
        Log.i(TAG,"(MeshTV-MVRS 0215) - setAdapter()\n");
        Log.i(TAG,"(MeshTV-MVRS 0215) - setAdapter() size:"+items.size()+"\n");
        Log.i("steward-pager","onPagesPopulated()");
        return new TheQuayExplorerRPagerAdapter(getChildFragmentManager(),items);
    }
    @Override
    public void updateDisplay(MeshVC item) {
        Log.i(TAG,"(MeshTV-MVRS 0215) - updateDisplay()\n");
        Log.i("steward-pager","updateDisplay()");
    }

    //==============================================================================================
}
