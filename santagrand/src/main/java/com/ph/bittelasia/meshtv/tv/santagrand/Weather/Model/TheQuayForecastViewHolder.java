package com.ph.bittelasia.meshtv.tv.santagrand.Weather.Model;


import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ph.bittelasia.meshtv.tv.mtvlib.Core.Object.VH.MeshTVVHolder;
import com.ph.bittelasia.meshtv.tv.mtvlib.Entity.Model.Weather.MeshWeatherForecast;
import com.ph.bittelasia.meshtv.tv.mtvlib.Entity.Model.Weather.MeshWeatherForecastDay;
import com.ph.bittelasia.meshtv.tv.santagrand.R;
public class TheQuayForecastViewHolder extends MeshTVVHolder<MeshWeatherForecastDay>
{
    //==============================================Variable========================================
    ImageView iv_icon;
    TextView tv_desc;
    TextView tv_temp;
    TextView tv_date;
    //==============================================================================================
    //==============================================Method==========================================
    //----------------------------------------------Getter------------------------------------------
    public ImageView getIv_icon() {
        return iv_icon;
    }
    public TextView getTv_desc() {
        return tv_desc;
    }
    public TextView getTv_temp() {
        return tv_temp;
    }
    public TextView getTv_date() {
        return tv_date;
    }
    //----------------------------------------------------------------------------------------------
    //==============================================================================================
    //========================================MeshTVVHolder=========================================
    @Override
    public void initViews(View v)
    {
        try {
            super.initViews(v);
            tv_desc = (TextView) v.findViewById(R.id.tv_desc);
            iv_icon = (ImageView) v.findViewById(R.id.iv_icon);
            tv_temp = (TextView) v.findViewById(R.id.tv_temp);
            tv_date = (TextView) v.findViewById(R.id.tv_date);
        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    //==============================================================================================
}
