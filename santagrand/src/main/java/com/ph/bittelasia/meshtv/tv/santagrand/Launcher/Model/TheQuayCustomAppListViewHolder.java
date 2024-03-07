package com.ph.bittelasia.meshtv.tv.santagrand.Launcher.Model;

import android.widget.ImageView;
import android.widget.TextView;

import com.ph.bittelasia.meshtv.tv.mtvlib.Core.Control.Annotation.View.Widget.BindWidget;
import com.ph.bittelasia.meshtv.tv.mtvlib.Core.Object.VH.MeshTVVHolder;
import com.ph.bittelasia.meshtv.tv.santagrand.R;

public class TheQuayCustomAppListViewHolder extends MeshTVVHolder
{
    @BindWidget(R.id.iv_icon)
    public ImageView iv_image;

    @BindWidget(R.id.tv_name)
    public TextView tv_text;

    @BindWidget(R.id.tv_badge)
    public TextView tv_badge;

    public ImageView getIv_image() {
        return iv_image;
    }

    public TextView getTv_text() {
        return tv_text;
    }

    public TextView getTv_badge() {
        return tv_badge;
    }
}
