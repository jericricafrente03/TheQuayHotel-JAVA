package com.ph.bittelasia.meshtv.tv.santagrand.TV.View.Fragment;

import android.app.Fragment;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import androidx.annotation.Nullable;

import com.ph.bittelasia.meshtv.tv.santagrand.R;

public class SantagrandLoadingFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.player_loading2,container,false);
    }
}
