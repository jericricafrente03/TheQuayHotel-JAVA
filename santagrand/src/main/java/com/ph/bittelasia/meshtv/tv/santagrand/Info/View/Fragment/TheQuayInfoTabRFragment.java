package com.ph.bittelasia.meshtv.tv.santagrand.Info.View.Fragment;

import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.common.base.MoreObjects;
import com.ph.bittelasia.meshtv.tv.mtvlib.Core.Control.Annotation.View.General.Layout;
import com.ph.bittelasia.meshtv.tv.mtvlib.Core.Control.Util.ResourceManager.MeshResourceManager;
import com.ph.bittelasia.meshtv.tv.mtvlib.Entity.Control.Listener.MeshFacilityCategoryListener;
import com.ph.bittelasia.meshtv.tv.mtvlib.Entity.Model.Facility.MeshFacilityCategory;
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
public class TheQuayInfoTabRFragment extends TheQuayTabFragment<MeshFacilityCategory> implements MeshFacilityCategoryListener
{
    //============================================Variable==========================================
    //--------------------------------------------Constant------------------------------------------
    public static final String TAG = TheQuayInfoTabRFragment.class.getSimpleName();
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
        super.onResume();
        Log.i(TAG,"(MVRS) - onResume() =================================================\n");
        TheQuayDAOManager.get().addListener(this);
        TheQuayDAOManager.get().retrieve(MeshFacilityCategory.class);
        Log.i(TAG,"(MVRS) - onResume() =================================================\n");

    }
    @Override
    public void onPause()
    {
        super.onPause();
        Log.i(TAG,"(MVRS) - onPause() =================================================\n");
        TheQuayDAOManager.get().removeListener(this);
        Log.i(TAG,"(MVRS) - onPause() =================================================\n");

    }
    //==============================================================================================
    //============================================Method============================================
    //--------------------------------------------Action--------------------------------------------
    private void display()
    {
        Log.i(TAG,"(MVRS) - display() =================================================\n");
        Log.i(TAG,"(MVRS) - display() - \n");
        Log.i(TAG,"(MVRS) - display() =================================================\n");
    }
    //----------------------------------------------------------------------------------------------
    //==============================================================================================
    //====================================MeshVCCategoryListener====================================
    @Override
    public void onInsertedBulk(ArrayList arrayList)
    {
        Log.i(TAG,"(MVRS) - onInsertedBulk() =================================================\n");
        setObjects(arrayList);
        isUpdated=true;
        if(tb!=null)
            tb.onTabsDrawn(true);
        Log.i(TAG,"(MVRS) - onInsertedBulk() =================================================\n");
    }

    @Override
    public void onDeletedBulk(ArrayList arrayList)
    {
        Log.i(TAG,"(MVRS) - onDeletedBulk() =================================================\n");
        Log.i(TAG,"(MVRS) - onDeletedBulk() =================================================\n");
    }

    @Override
    public void onRetrieved(ArrayList arrayList)
    {
        Log.i(TAG,"(MVRS) - onRetrieved() =================================================\n");
        setObjects(arrayList);
        Log.i(TAG,"(MVRS) - onRetrieved() Retrieved:"+arrayList.size()+"\n");
        display();
        Log.i(TAG,"(MVRS) - onRetrieved() =================================================\n");
    }

    @Override
    public void onCleared(Class aClass)
    {
        Log.i(TAG,"(MVRS) - onCleared() =================================================\n");
        Log.i(TAG,"(MVRS) - onCleared() =================================================\n");
    }

    @Override
    public void onDAONotFound(String s)
    {
        Log.i(TAG,"(MVRS) - onDAONotFound() =================================================\n");
        Log.i(TAG,"(MVRS) - onDAONotFound() =================================================\n");

    }
    //==============================================================================================
    //========================================MeshTVRFragment=======================================

    @Override
    public void launch(MeshFacilityCategory object) {
        Log.i(TAG,"(MVRS) - launch() =================================================\n");
        Log.i(TAG,"(MVRS) - launch() =================================================\n");
    }

    @Override
    public void onTabsDrawn()
    {
        Log.i(TAG,"(MVRS) - onTabsDrawn() =================================================\n");
        focusOnTab();
        Log.i(TAG,"(MVRS) - onTabsDrawn() =================================================\n");
    }

    @Override
    public void draw(View v, MeshFacilityCategory object, boolean isSelected,int index)
    {
        Log.i(TAG,"(MVRS) - draw() =================================================\n");
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
                tv_name.setText((" "+object.getCategory_name()));
                Picasso.get().load(object.getImg_preview()).into(iv_preview);
            } catch (Exception e) {
                MeshResourceManager.displayLiveImageFor(getActivity(),iv_preview,object.getImg_preview());
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        Log.i(TAG,"(MVRS) - draw() =================================================\n");
    }

    @Override
    public void draw(View v)
    {
        super.draw(v);
        Log.i(TAG,"(MVRS) - draw2() =================================================\n");
        display();
        Log.i(TAG,"(MVRS) - draw2() =================================================\n");
    }

    //==============================================================================================
}
