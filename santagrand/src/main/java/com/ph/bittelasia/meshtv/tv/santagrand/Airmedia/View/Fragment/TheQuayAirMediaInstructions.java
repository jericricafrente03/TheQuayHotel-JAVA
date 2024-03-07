package com.ph.bittelasia.meshtv.tv.santagrand.Airmedia.View.Fragment;


import android.view.View;
import android.widget.ListView;
import android.widget.TextView;


import com.ph.bittelasia.meshtv.tv.mtvlib.AirMedia.Control.MeshTVAirmediaControl;
import com.ph.bittelasia.meshtv.tv.mtvlib.AirMedia.Model.MeshAirmediaInstructions;
import com.ph.bittelasia.meshtv.tv.mtvlib.Core.Control.Annotation.View.General.Layout;
import com.ph.bittelasia.meshtv.tv.mtvlib.Core.Control.Annotation.View.Widget.BindWidget;
import com.ph.bittelasia.meshtv.tv.mtvlib.Core.Control.Preference.Control.Manager.MeshTVPreferenceManager;
import com.ph.bittelasia.meshtv.tv.mtvlib.Core.View.Fragment.MeshTVFragment;
import com.ph.bittelasia.meshtv.tv.santagrand.Airmedia.Model.TheQuayAirMediaListAdapter;
import com.ph.bittelasia.meshtv.tv.santagrand.Netflix.Model.NetFlixSettings;
import com.ph.bittelasia.meshtv.tv.santagrand.R;
import com.ph.bittelasia.meshtv.tv.santagrand.Room.View.App.TheQuay;

import java.util.ArrayList;

/**
 * Created by steward on 12/26/17.
 */
@Layout(R.layout.sg_airmedia_instruction_layout)
public class TheQuayAirMediaInstructions extends MeshTVFragment {

    //===================================Variable===================================================
    //-----------------------------------Constant---------------------------------------------------
    public static TheQuayAirMediaInstructions fragment;
    //----------------------------------------------------------------------------------------------
    //-----------------------------------Instance---------------------------------------------------
    private String                              platform;
    private TheQuayAirMediaListAdapter          adapter;
    private ArrayList<MeshAirmediaInstructions> instructions;
    //----------------------------------------------------------------------------------------------
    //==============================================================================================


    //========================================Views=================================================
    //----------------------------------------------------------------------------------------------
    @BindWidget(R.id.tv_credentials)
    public TextView tv_credentials;

    @BindWidget(R.id.tv_label)
    public TextView tv_label;


    @BindWidget(R.id.lv_instruction)
    public ListView lv_instruction;
    //----------------------------------------------------------------------------------------------
    //==============================================================================================



    //====================================Constructor===============================================
    //----------------------------------------------------------------------------------------------
    public static TheQuayAirMediaInstructions get(String platform)
    {
        try {
            fragment = new TheQuayAirMediaInstructions();
            fragment.platform = platform;
        }catch (Exception e)
        {
            e.printStackTrace();
        }
        return fragment;
    }
    //----------------------------------------------------------------------------------------------
    //==============================================================================================


    //===================================MeshTVFragment=============================================
    //----------------------------------------------------------------------------------------------
    @Override
    protected void onDrawDone(View view) {
        try {
            setInstruction();
        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    @Override
    protected void onDataUpdated(ArrayList arrayList) {

    }

    @Override
    protected void onNewData(Object o) {

    }

    @Override
    protected void onDataUpdated(Object o, int i) {

    }

    @Override
    protected void onDeleteData(int i) {

    }

    @Override
    protected void onClearData() {

    }

    @Override
    protected void onDataNotFound(Class aClass) {

    }

    @Override
    protected void refresh() {

    }

    @Override
    protected void update(Object o) {

    }
    //----------------------------------------------------------------------------------------------
    //==============================================================================================


    //======================================Method==================================================
    //----------------------------------------------------------------------------------------------

    public String getPlatform() {
        return platform;
    }

    public void setInstruction()
    {
        try
        {
            adapter = new TheQuayAirMediaListAdapter();
            tv_credentials.setText(("Username: "+MeshTVPreferenceManager.getRoom(getActivity())+"   |   Password: 12345678"));
            //tv_credentials.setText(("WIFI SSID: "+NetFlixSettings.readSSID(TheQuay.app)));
            if(tv_label!=null)
            {
                tv_label.setText(getPlatform());
            }
            if (getPlatform().equals("ANDROID"))
            {
                instructions = MeshTVAirmediaControl.getAndroidInstructions(getContext(), false);
            }
            else if(getPlatform().equals("IOS"))
            {
                instructions = MeshTVAirmediaControl.getIOSInstructions(getContext(), false);
            }
            else
            {
                instructions = MeshTVAirmediaControl.getWindowsInstructions(getContext(), false);
            }
            adapter.setObjects(instructions,false);
            lv_instruction.setAdapter(adapter);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    //----------------------------------------------------------------------------------------------
    //==============================================================================================
}
