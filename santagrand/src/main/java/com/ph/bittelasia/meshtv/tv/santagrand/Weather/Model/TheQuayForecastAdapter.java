package com.ph.bittelasia.meshtv.tv.santagrand.Weather.Model;

import android.content.Context;
import android.widget.GridView;

import com.ph.bittelasia.meshtv.tv.mtvlib.Core.Control.Annotation.View.ViewHolder.ViewHolderLayout;
import com.ph.bittelasia.meshtv.tv.mtvlib.Core.Control.Util.ResourceManager.MeshResourceManager;
import com.ph.bittelasia.meshtv.tv.mtvlib.Core.Object.Adapter.MeshTVAdapter;
import com.ph.bittelasia.meshtv.tv.mtvlib.Core.Object.VH.MeshTVVHolder;
import com.ph.bittelasia.meshtv.tv.mtvlib.Entity.Model.Weather.MeshWeatherForecastDay;
import com.ph.bittelasia.meshtv.tv.santagrand.R;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
@ViewHolderLayout(layout = R.layout.sg_weather_item,layoutCLicked =R.layout.sg_weather_item,layoutSelected = R.layout.sg_weather_item)
public class TheQuayForecastAdapter extends MeshTVAdapter<MeshWeatherForecastDay>
{
    //==============================================Variable========================================
    //----------------------------------------------Constant----------------------------------------
    public static final String TAG = TheQuayForecastAdapter.class.getSimpleName();
    public static final String UNIT_CELCIUS = " °C";
    public static final String UNIT_FAREN   = " °F";
    //----------------------------------------------------------------------------------------------
    //----------------------------------------------Instance----------------------------------------
    boolean isCelcius = true;
    //----------------------------------------------------------------------------------------------
    //==============================================================================================
    //=============================================Constructor======================================
    public TheQuayForecastAdapter(Context context, GridView gv_view, int layoutResourceId, ArrayList<MeshWeatherForecastDay> data,boolean isCelcius)
    {
        super(context, gv_view, layoutResourceId, data);
        this.isCelcius=isCelcius;
    }
    //==============================================================================================
    //===========================================MeshTVAdapter======================================
    @Override
    public MeshTVVHolder setViewHolder()
    {
        return new TheQuayForecastViewHolder();
    }

    @Override
    public void updateRow(MeshTVVHolder meshTVVHolder, MeshWeatherForecastDay meshWeatherForecastDay)
    {
        try
        {
            MeshWeatherForecastDay weatherForecast = (MeshWeatherForecastDay) meshWeatherForecastDay;
            TheQuayForecastViewHolder v = (TheQuayForecastViewHolder) meshTVVHolder;

                try
                {
                    v.getTv_date().setText(new SimpleDateFormat("EEE dd MMM").format(weatherForecast.getDate()));
                }
                catch(Exception e)
                {
                    e.printStackTrace();
                }
                v.getTv_temp().setText(isCelcius?String.format("%.1f",weatherForecast.getTemp())+ UNIT_CELCIUS:(String.format("%.1f",((weatherForecast.getTemp()*9)/5)+32))+ UNIT_FAREN);
                v.getTv_desc().setText(weatherForecast.getDescription());
                MeshResourceManager.displayLiveImageFor(getContext(),v.getIv_icon(),weatherForecast.getIcon());
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }
    //==============================================================================================
}
