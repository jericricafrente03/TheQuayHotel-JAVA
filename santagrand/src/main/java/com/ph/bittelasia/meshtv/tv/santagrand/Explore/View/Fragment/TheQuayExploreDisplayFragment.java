package com.ph.bittelasia.meshtv.tv.santagrand.Explore.View.Fragment;

import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ph.bittelasia.meshtv.tv.mtvlib.Core.Control.Annotation.View.General.Layout;
import com.ph.bittelasia.meshtv.tv.mtvlib.Core.Control.Annotation.View.Widget.BindWidget;
import com.ph.bittelasia.meshtv.tv.mtvlib.Core.Control.Util.ResourceManager.MeshResourceManager;
import com.ph.bittelasia.meshtv.tv.mtvlib.Entity.Control.Listener.MeshVCListener;
import com.ph.bittelasia.meshtv.tv.mtvlib.Entity.Model.VC.MeshVC;

import com.ph.bittelasia.meshtv.tv.santagrand.R;
import com.ph.bittelasia.meshtv.tv.santagrand.Room.Library.MeshTVRFragment;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

@Layout(R.layout.sg_branch_frag)
public class TheQuayExploreDisplayFragment extends MeshTVRFragment<MeshVC>
        implements MeshVCListener
{

    //===========================================Variable===========================================
    //-------------------------------------------Constant-------------------------------------------
    public static final String TAG = TheQuayExploreDisplayFragment.class.getSimpleName();
    //----------------------------------------------------------------------------------------------
    //-------------------------------------------Instance-------------------------------------------
    MeshVC vc;
    //----------------------------------------------------------------------------------------------
    //--------------------------------------------View----------------------------------------------
    @BindWidget(R.id.tv_title)
    public TextView tv_name;
    @BindWidget(R.id.tv_desc)
    public TextView tv_desc;
    @BindWidget(R.id.iv_icon)
    public ImageView iv_icon;
    //----------------------------------------------------------------------------------------------
    //==============================================================================================
    //============================================Static============================================
    public static TheQuayExploreDisplayFragment geT(MeshVC vc)
    {
        Log.i(TAG,"(MeshTV-MVRS 0215) - geT() =================================================\n");
        TheQuayExploreDisplayFragment fragment = new TheQuayExploreDisplayFragment();
        fragment.setVc(vc);
        Log.i(TAG,"(MeshTV-MVRS 0215) - geT() =================================================\n");
        Log.i("steward","(MeshTV-MVRS 0215) - geT() =================================================\n");
        return fragment;
    }
    //==============================================================================================

    //==========================================LifeCycle===========================================
    @Override
    public void onResume()
    {
        super.onResume();
        Log.i(TAG,"(MeshTV-MVRS 0215) - onResume() =================================================\n");
        Log.i(TAG,"(MeshTV-MVRS 0215) - onResume() =================================================\n");
        Log.i("steward-display","onResume()");
    }
    @Override
    public void onPause()
    {
        super.onPause();
        Log.i(TAG,"(MeshTV-MVRS 0215) - onPause() =================================================\n");
        Log.i(TAG,"(MeshTV-MVRS 0215) - onPause() =================================================\n");
        Log.i("steward-display","onPause()");
    }
    //==============================================================================================

    //===========================================Method=============================================
    //-------------------------------------------Action---------------------------------------------
    public void display()
    {
        try {
            Log.i(TAG, "(MeshTV-MVRS 0215) - display() =================================================\n");
            if (vc != null && tv_desc != null && tv_name != null && iv_icon != null) {
                try {
                    Log.i(TAG, "(MeshTV-MVRS 0215) - display() Displaying\n");
                    tv_name.setText(vc.getEstablishment_name());
                    tv_desc.setText(Html.fromHtml(vc.getDescription()));
                    try {
                        Picasso.get().load(vc.getImg_url()).into(iv_icon);
                    } catch (Exception e) {
                        MeshResourceManager.displayLiveImageFor(getActivity(), iv_icon, vc.getImg_url());
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
                Log.i(TAG, "(MeshTV-MVRS 0215) - display() IMAGE URL:" + vc.getImg_url() + "\n");
            }
            Log.i(TAG, "(MeshTV-MVRS 0215) - display() =================================================\n");
            Log.i("steward-display", "display()");
        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    //----------------------------------------------------------------------------------------------
    //-------------------------------------------Setter---------------------------------------------
    public void setVc(MeshVC vc) {
        try {
            Log.i(TAG, "(MeshTV-MVRS 0215) - setVc() =================================================\n");
            this.vc = vc;
            display();
            Log.i(TAG, "(MeshTV-MVRS 0215) - setVc() :" + vc.getEstablishment_name() + "\n");
            Log.i(TAG, "(MeshTV-MVRS 0215) - setVc() =================================================\n");
            Log.i("steward-display", "setVc()");
        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    //----------------------------------------------------------------------------------------------
    //-------------------------------------------Getter---------------------------------------------
    public MeshVC getVc() {
        return vc;
    }
    //----------------------------------------------------------------------------------------------
    //==============================================================================================

    //========================================MeshVCListener========================================
    @Override
    public void draw(View view) {
        display();
        Log.i("steward-display","draw()");
    }
    @Override
    public void onInsertedBulk(ArrayList arrayList)
    {
        try {
            for (Object o : arrayList) {
                if (o instanceof MeshVC) {
                    MeshVC vc = (MeshVC) o;
                    if (vc.getId() == getItem().getId()) {
                        setItem(vc);
                        break;
                    }
                }
            }
            Log.i("steward-display", "onInsertedBulk()");
        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    @Override
    public void onDeletedBulk(ArrayList arrayList) {
        Log.i("steward-display","onDeletedBulk()");
    }
    @Override
    public void onRetrieved(ArrayList arrayList) {
        Log.i("steward-display","onRetrieved()");
    }
    //==============================================================================================
    //========================================MeshTVFragment========================================
    @Override
    public void updateDisplay(MeshVC item) {
        display();
        Log.i("steward-display","updateDisplay()");
    }
    @Override
    public void onCleared(Class aClass) {
        Log.i("steward-display","onCleared()");
    }
    @Override
    public void onDAONotFound(String s) {
        Log.i("steward-display","onDAONotFound()");
    }
    //==============================================================================================
}
