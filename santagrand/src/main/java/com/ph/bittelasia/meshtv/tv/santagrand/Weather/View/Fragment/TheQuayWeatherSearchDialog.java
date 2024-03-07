package com.ph.bittelasia.meshtv.tv.santagrand.Weather.View.Fragment;

import android.content.Context;
import android.view.View;
import android.widget.ListView;

import com.ph.bittelasia.meshtv.tv.mtvlib.Core.Control.Listener.MeshTVFragmentListener;
import com.ph.bittelasia.meshtv.tv.santagrand.R;
import com.ph.bittelasia.meshtv.tv.santagrand.Weather.Model.TheQuayCountrySearchAdapter;

import java.util.ArrayList;

public class TheQuayWeatherSearchDialog extends TheQuayFilterDialog {

    //==================================Variable====================================================
    //----------------------------------Constant----------------------------------------------------
    public static TheQuayWeatherSearchDialog dialog;
    //----------------------------------------------------------------------------------------------
    //------------------------------------View------------------------------------------------------
    private ListView lv_search;
    //----------------------------------------------------------------------------------------------
    //----------------------------------Instance----------------------------------------------------
    private ArrayList                   arrayList;
    private TheQuayCountrySearchAdapter adapter;
    private MeshTVFragmentListener      cb;
    //----------------------------------------------------------------------------------------------
    //==============================================================================================


    public static TheQuayWeatherSearchDialog dialog(ArrayList arrayList)
    {
        dialog =new TheQuayWeatherSearchDialog();
        dialog.arrayList=arrayList;
        return dialog;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        cb=(MeshTVFragmentListener)context;
    }


    @Override
    public void onDetach() {
        super.onDetach();
        cb=null;
    }

    @Override
    public void setIDs(View view) {
      lv_search = view.findViewById(R.id.lv_search);
    }

    @Override
    public void setContent() {
     try {
         if (arrayList != null && arrayList.size() > 0) {
             adapter = new TheQuayCountrySearchAdapter();
             adapter.setObjects(arrayList, false);
             adapter.setClickedListener(o -> {
                 if (cb != null) {
                     cb.onClicked(o);
                     dismiss();
                 }
             });
             lv_search.setAdapter(adapter);
             lv_search.setFocusable(true);
             lv_search.requestFocus();
         }
     }catch (Exception e)
     {
         e.printStackTrace();
     }
    }

    @Override
    public int setLayout() {
        return R.layout.weather_search_dialog;
    }

    @Override
    public int setConstraintLayout() {
        return R.id.cl_layout;
    }

    @Override
    public double setPercentageWidth() {
        return 0.25;
    }

    @Override
    public double setPercentageHeight() {
        return 0.60;
    }

}
