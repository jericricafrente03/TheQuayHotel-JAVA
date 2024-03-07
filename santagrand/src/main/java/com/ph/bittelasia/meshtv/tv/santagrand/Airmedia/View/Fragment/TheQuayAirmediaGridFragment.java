package com.ph.bittelasia.meshtv.tv.santagrand.Airmedia.View.Fragment;

import android.content.Context;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.ph.bittelasia.meshtv.tv.mtvlib.Core.Control.Listener.MeshListItemClickedListener;
import com.ph.bittelasia.meshtv.tv.santagrand.Airmedia.Model.AirMediaPlatform;
import com.ph.bittelasia.meshtv.tv.santagrand.Airmedia.Model.TheQuayGridFragmentAdapter;
import com.ph.bittelasia.meshtv.tv.santagrand.R;

import java.util.ArrayList;

public class TheQuayAirmediaGridFragment extends Fragment {

    //======================================Variable================================================
    //--------------------------------------Constant------------------------------------------------
    public static final String TAG = TheQuayAirmediaGridFragment.class.getSimpleName();
    //--------------------------------------Instance------------------------------------------------
    private       TheQuayGridFragmentAdapter  adapter;
    private       ArrayList<AirMediaPlatform> platforms;
    private       MeshListItemClickedListener clickedListener;
    //----------------------------------------View--------------------------------------------------
    private       GridView   gv_air;
    //----------------------------------------------------------------------------------------------
    //==============================================================================================



    //=====================================LifeCycle================================================
    //----------------------------------------------------------------------------------------------
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.sg_airmedia_grid,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        gv_air = view.findViewById(R.id.gv_air);
        setEvent();
    }
    //----------------------------------------------------------------------------------------------
    //==============================================================================================



    //=====================================Method===================================================
    //-------------------------------------Setter---------------------------------------------------
    public void setClickedListener(MeshListItemClickedListener clickedListener) {
        this.clickedListener = clickedListener;
    }
    //----------------------------------------------------------------------------------------------
    //-------------------------------------Getter---------------------------------------------------
    public MeshListItemClickedListener getClickedListener() {
        return clickedListener;
    }
    //----------------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------------
    public final void setEvent()
    {
        try {
            platforms = new ArrayList<>();
            platforms.add(new AirMediaPlatform(AirMediaPlatform.names.ANDROID, R.drawable.and));
            platforms.add(new AirMediaPlatform(AirMediaPlatform.names.IOS, R.drawable.ios));
            platforms.add(new AirMediaPlatform(AirMediaPlatform.names.WINDOWS, R.drawable.windows));
            adapter = new TheQuayGridFragmentAdapter();
            adapter.setObjects(platforms,false);
            adapter.setClickedListener(o -> {
                if(getClickedListener()!=null)
                {
                    getClickedListener().onClicked(o);
                }
            });
            gv_air.setAdapter(adapter);
        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    //----------------------------------------------------------------------------------------------
    //==============================================================================================
}
