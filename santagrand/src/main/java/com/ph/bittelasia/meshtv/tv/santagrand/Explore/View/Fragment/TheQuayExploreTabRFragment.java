package com.ph.bittelasia.meshtv.tv.santagrand.Explore.View.Fragment;

import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ph.bittelasia.meshtv.tv.mtvlib.Core.Control.Annotation.View.General.Layout;
import com.ph.bittelasia.meshtv.tv.mtvlib.Core.Control.Util.ResourceManager.MeshResourceManager;
import com.ph.bittelasia.meshtv.tv.mtvlib.Entity.Control.Listener.MeshVCCategoryListener;
import com.ph.bittelasia.meshtv.tv.mtvlib.Entity.Model.VC.MeshVCCategory;
import com.ph.bittelasia.meshtv.tv.santagrand.R;
import com.ph.bittelasia.meshtv.tv.santagrand.Room.Control.TheQuayDAOManager;
import com.ph.bittelasia.meshtv.tv.santagrand.Room.View.Fragment.Core.TabLayoutAnnotation;
import com.ph.bittelasia.meshtv.tv.santagrand.Room.View.Fragment.Core.TheQuayTabFragment;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
@Layout(R.layout.sg_explore_tabs)
@TabLayoutAnnotation
        (
                tabLayout = R.id.tab_explore ,
                itemLayout = R.layout.sg_branch ,
                itemSelectedLayout = R.layout.sg_branch_selected
        )
public class TheQuayExploreTabRFragment extends TheQuayTabFragment<MeshVCCategory>
        implements MeshVCCategoryListener
{
    //============================================Variable==========================================
    //--------------------------------------------Constant------------------------------------------
    public static final String TAG = TheQuayExploreTabRFragment.class.getSimpleName();
    //----------------------------------------------------------------------------------------------
    //----------------------------------------------View--------------------------------------------
    private TextView  tv_name;
    private ImageView iv_preview;
    private LinearLayout ll_branch;
    //---------------------------------------------Instance-----------------------------------------
    public  boolean isUpdated=false;
    //----------------------------------------------------------------------------------------------
    //==============================================================================================
    //=============================================LifeCycle========================================
    @Override
    public void onResume()
    {
        try {
            super.onResume();
            Log.i(TAG, "(MVRS) - onResume() =================================================\n");
            TheQuayDAOManager.get().addListener(this);
            TheQuayDAOManager.get().retrieve(MeshVCCategory.class);
            Log.i(TAG, "(MVRS) - onResume() =================================================\n");
            Log.i("steward-tab", "onResume()");
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
            Log.i(TAG, "(MVRS) - onPause() =================================================\n");
            TheQuayDAOManager.get().removeListener(this);
            Log.i(TAG, "(MVRS) - onPause() =================================================\n");
            Log.i("steward-tab", "onPause()");
        }catch (Exception e)
        {
            e.printStackTrace();
        }

    }
    //==============================================================================================
    //============================================Method============================================
    //==============================================================================================
    //====================================MeshVCCategoryListener====================================
    @Override
    public void onInsertedBulk(ArrayList arrayList)
    {
        try {
            Log.i(TAG, "(MVRS) - onInsertedBulk() =================================================\n");
            setObjects(arrayList);
            isUpdated = true;
            if (tb != null)
                tb.onTabsDrawn(true);
            Log.i(TAG, "(MVRS) - onInsertedBulk() =================================================\n");
            Log.i("steward-tab", "onInsertedBulk()");
        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void onDeletedBulk(ArrayList arrayList) {
        Log.i(TAG,"(MVRS) - onDeletedBulk() =================================================\n");
        Log.i(TAG,"(MVRS) - onDeletedBulk() =================================================\n");
        Log.i("steward-tab","onDeletedBulk()");

    }

    @Override
    public void onRetrieved(ArrayList arrayList)
    {
        try {
            Log.i(TAG, "(MVRS) - onRetrieved() =================================================\n");
            setObjects(arrayList);
            Log.i(TAG, "(MVRS) - onRetrieved() Retrieved:" + arrayList.size() + "\n");
            Log.i(TAG, "(MVRS) - onRetrieved() =================================================\n");
            Log.i("steward-tab", "onRetrieved()");
        }catch (Exception e)
        {
            e.printStackTrace();
        }

    }

    @Override
    public void onCleared(Class aClass)
    {
        Log.i(TAG,"(MVRS) - onCleared() =================================================\n");
        Log.i(TAG,"(MVRS) - onCleared() =================================================\n");
        Log.i("steward-tab","onCleared()");
    }

    @Override
    public void onDAONotFound(String s)
    {
        Log.i(TAG,"(MVRS) - onDAONotFound() =================================================\n");
        Log.i(TAG,"(MVRS) - onDAONotFound() =================================================\n");
        Log.i("steward-tab","onDAONotFound()");
    }
    //==============================================================================================
    //========================================MeshTVRFragment=======================================

    @Override
    public void launch(MeshVCCategory object) {
        Log.i(TAG,"(MVRS) - launch() =================================================\n");
        Log.i(TAG,"(MVRS) - launch() =================================================\n");
        Log.i("steward-tab","launch()");
    }

    @Override
    public void onTabsDrawn()
    {
        Log.i(TAG,"(MVRS) - onTabsDrawn() =================================================\n");
        focusOnTab();
        Log.i(TAG,"(MVRS) - onTabsDrawn() =================================================\n");
        Log.i("steward-tab","onTabsDrawn()");
    }

    @Override
    public void draw(View v, MeshVCCategory object, boolean isSelected,int index)
    {
        try{
            try
            {
                tv_name    = v.findViewById(R.id.tv_name) ;
                iv_preview =  v.findViewById(R.id.iv_preview);
                ll_branch  = v.findViewById(R.id.ll_branch);
                ll_branch.setOnClickListener((view) -> {
                        isClicked=true;
                        getTabs().getTabAt(index).select();
                        ok();
                });
                ll_branch.setFocusable(true);
                ll_branch.setOnFocusChangeListener((vw,hasFocus)-> {
                    if(hasFocus)
                    {
                        ll_branch.setBackground(getResources().getDrawable(R.drawable.box_branch_selected));
                    }else
                    {
                        ll_branch.setBackground(getResources().getDrawable(R.drawable.box_branch));
                    }
                });
                ll_branch.setOnHoverListener((vh, event) ->{
                        vh.requestFocus();
                        return false;
                });
                tv_name.setText((" "+object.getName()));
                Picasso.get().load(object.getImg_preview()).into(iv_preview);
            } catch (Exception e) {
                MeshResourceManager.displayLiveImageFor(getActivity(),iv_preview,object.getImg_preview());
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        Log.i(TAG,"(MVRS) - draw() =================================================\n");
        Log.i(TAG,"(MVRS) - draw() =================================================\n");
        Log.i("steward-tab","draw()");
    }

    @Override
    public void draw(View v)
    {
        super.draw(v);
        Log.i(TAG,"(MVRS) - draw2() =================================================\n");
        Log.i(TAG,"(MVRS) - draw2() =================================================\n");
        Log.i("steward-tab","draw 2()");
    }

    //==============================================================================================
}
