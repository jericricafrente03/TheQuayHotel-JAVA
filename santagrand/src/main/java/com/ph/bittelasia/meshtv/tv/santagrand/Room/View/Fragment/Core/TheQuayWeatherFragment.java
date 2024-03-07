package com.ph.bittelasia.meshtv.tv.santagrand.Room.View.Fragment.Core;

import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextClock;
import android.widget.TextView;

import com.ph.bittelasia.meshtv.tv.mtvlib.Core.Control.Annotation.View.General.Layout;
import com.ph.bittelasia.meshtv.tv.mtvlib.Core.Control.Annotation.View.Widget.BindWidget;
import com.ph.bittelasia.meshtv.tv.mtvlib.Core.Control.Preference.Control.Manager.MeshTVPreferenceManager;
import com.ph.bittelasia.meshtv.tv.mtvlib.Entity.Control.Listener.MeshWeatherForecastListener;
import com.ph.bittelasia.meshtv.tv.mtvlib.Entity.Model.Weather.MeshWeatherForecast;
import com.ph.bittelasia.meshtv.tv.santagrand.R;
import com.ph.bittelasia.meshtv.tv.santagrand.Room.Control.TheQuayDAOManager;
import com.ph.bittelasia.meshtv.tv.santagrand.Room.Library.MeshTVRFragment;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

@Layout(R.layout.weather_layout)
public class TheQuayWeatherFragment extends MeshTVRFragment<MeshWeatherForecast>
    implements MeshWeatherForecastListener
{
    //=========================================Variable=============================================
    //------------------------------------------Constant--------------------------------------------
    public static final String DATE_FORMAT = "MM/dd/yyyy";
    public static final String TIME_FORMAT = "hh:mm aa";
    //----------------------------------------------------------------------------------------------
    //-------------------------------------------View-----------------------------------------------
    @BindWidget(R.id.tv_country)
    public TextView tv_country;
    @BindWidget(R.id.tv_time)
    public TextClock tv_time;
    @BindWidget(R.id.tv_date)
    public TextView tv_date;
    @BindWidget(R.id.tv_temperature)
    public TextView tv_temperature;
    @BindWidget(R.id.iv_weather)
    public ImageView iv_weather;
    //----------------------------------------------------------------------------------------------
    //------------------------------------------Instance--------------------------------------------

    //----------------------------------------------------------------------------------------------
    //==============================================================================================
    //==========================================LifeCycle===========================================

    @Override
    public void onResume()
    {
        try {
            super.onResume();
            TheQuayDAOManager.get().addListener(this);
            Log.i(TAG, "(MVRS-0225) CITY:" + MeshTVPreferenceManager.getHotelCity(getActivity()) + "\n");
            Log.i(TAG, "(MVRS-0225) COUNTRY:" + MeshTVPreferenceManager.getHotelCountry(getActivity()) + "\n");
            Log.i(TAG, "(MVRS-0225) LOGO:" + MeshTVPreferenceManager.getHotelLogo(getActivity()) + "\n");
        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void onPause()
    {
        try {
            super.onPause();
            TheQuayDAOManager.get().removeListener(this);
        }catch (Exception e)
        {
            e.printStackTrace();
        }

    }

    //==============================================================================================
    //===========================================Method=============================================
    //-------------------------------------------Action---------------------------------------------
    private void update()
    {

    }
    //----------------------------------------------------------------------------------------------
    //-------------------------------------------Display--------------------------------------------
    public void updateDisplay()
    {
        try
        {
            Date d = new Date();
            SimpleDateFormat df = new SimpleDateFormat(DATE_FORMAT);
            tv_date.setText(df.format(d));
            tv_temperature.setText((MeshTVPreferenceManager.getHotelWeatherMainTemp(getActivity())+" °C"));
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    //----------------------------------------------------------------------------------------------
    //==============================================================================================
    //===============================MeshWeatherForecastListener====================================
    @Override
    public void onInsertedBulk(ArrayList arrayList)
    {

    }

    @Override
    public void onDeletedBulk(ArrayList arrayList)
    {

    }

    @Override
    public void onRetrieved(ArrayList arrayList)
    {
        try {
            if (arrayList.size() > 0) {
                if (arrayList.get(0) instanceof MeshWeatherForecast) {
                    MeshWeatherForecast forecast = (MeshWeatherForecast) arrayList.get(0);

                }
            }
        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void onCleared(Class aClass)
    {

    }

    @Override
    public void onDAONotFound(String s)
    {

    }

    //==============================================================================================
    //=====================================MeshTVRFragment==========================================
    @Override
    public void draw(View v)
    {
        updateDisplay();
    }
    @Override
    public void updateDisplay(MeshWeatherForecast item)
    {
        updateDisplay();
    }
    //==============================================================================================
}
