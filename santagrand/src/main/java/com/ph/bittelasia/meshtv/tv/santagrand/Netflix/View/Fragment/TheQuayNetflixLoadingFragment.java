package com.ph.bittelasia.meshtv.tv.santagrand.Netflix.View.Fragment;



import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.ph.bittelasia.meshtv.tv.santagrand.R;
import com.ph.bittelasia.packages.Core.Control.AppReporting;
import com.ph.bittelasia.packages.Core.View.CustomView.GifImageView;
import com.ph.bittelasia.packages.Core.View.Fragment.BittelAsiaFragment;


public class TheQuayNetflixLoadingFragment extends BittelAsiaFragment {

    //=====================================Variable=================================================
    //-------------------------------------Constant-------------------------------------------------
    public static final String TAG = TheQuayNetflixLoadingFragment.class.getSimpleName();
    //--------------------------------------View----------------------------------------------------
    private GifImageView     gifImageView;
    private ConstraintLayout cl_parent;

    //----------------------------------------------------------------------------------------------
    //-------------------------------------Instance-------------------------------------------------
    private int              index;
    private int[]            gifList;
    private boolean          isLoaded;
    //----------------------------------------------------------------------------------------------
    //==============================================================================================


    //==================================BittelAsiaFragment==========================================
    //----------------------------------------------------------------------------------------------
    @Override
    public final int setLayout() {
        return R.layout.sg_loading_fragment;
    }

    @Override
    public final void setIDs(View view) {
        try {
            isLoaded = false;
            gifImageView = view.findViewById(R.id.GifImageView);
            cl_parent = view.findViewById(R.id.cl_parent);
            gifList = new int[]
                    {
                            R.drawable.loading_page1,
                            R.drawable.loading_page2,
                            R.drawable.loading_page3,
                            R.drawable.loading_page4,
                            R.drawable.loading_page5
                    };
            Log.i(TAG, "@setIDs");
        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("ConstantConditions")
    @Override
    public final void setEvents() {
        try {
           getActivity().runOnUiThread(new Runnable(){
                int x=0;
                public void run() {
                    try{
                        gifImageView.setGifImageResource(gifList[getIndex()]);
                    }
                    catch (Exception e)
                    {
                        gifImageView.setGifImageResource(R.drawable.loading_page5);
                    }
                    DisplayMetrics displayMetrics = new DisplayMetrics();
                    getActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

                    int height = displayMetrics.heightPixels;
                    int width = displayMetrics.widthPixels;

                    cl_parent.setMinimumWidth(width);
                    cl_parent.setMinimumHeight(height);

                    Log.i(TAG,"@setEvents: "+(x++));
                }
            });
        } catch (Exception e) {
            AppReporting.bugReport(getContext(),e,TAG,"@setEvents");
        }
    }
    //----------------------------------------------------------------------------------------------
    //==============================================================================================




    //=======================================Method=================================================
    //---------------------------------------Setter-------------------------------------------------
    public void setIndex(int index) {
        this.index = index;
    }

    //----------------------------------------------------------------------------------------------
    //---------------------------------------Getter-------------------------------------------------
    public int getIndex() {
        return index;
    }
    //----------------------------------------------------------------------------------------------
    //===============================================================================================
}
