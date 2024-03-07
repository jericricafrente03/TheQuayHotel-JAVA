package com.ph.bittelasia.meshtv.tv.santagrand.Airmedia.Model;

import android.widget.TextView;

import com.ph.bittelasia.meshtv.tv.mtvlib.AirMedia.Model.MeshAirmediaInstructions;
import com.ph.bittelasia.meshtv.tv.mtvlib.Core.Control.Annotation.View.Widget.BindWidget;
import com.ph.bittelasia.meshtv.tv.mtvlib.Core.Object.VH.MeshTVVHolder;
import com.ph.bittelasia.meshtv.tv.santagrand.R;

/**
 * Created by ramil on 12/26/17.
 */

public class TheQuayAirMediaListViewHolder extends MeshTVVHolder<MeshAirmediaInstructions> {

    @BindWidget(R.id.tv_number)
    public TextView tv_number;

    @BindWidget(R.id.tv_req)
    public TextView tv_req;

    public TextView getTv_number() {
        return tv_number;
    }

    public TextView getTv_req() {
        return tv_req;
    }

}
