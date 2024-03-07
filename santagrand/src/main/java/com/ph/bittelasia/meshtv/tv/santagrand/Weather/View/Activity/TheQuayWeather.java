package com.ph.bittelasia.meshtv.tv.santagrand.Weather.View.Activity;

import android.os.Bundle;

import android.util.Log;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.ph.bittelasia.meshtv.tv.mtvlib.Core.Control.Annotation.View.Activity.ActivitySetting;
import com.ph.bittelasia.meshtv.tv.mtvlib.Core.Control.Annotation.View.Activity.AttachFragment;
import com.ph.bittelasia.meshtv.tv.mtvlib.Core.Control.Annotation.View.General.Layout;
import com.ph.bittelasia.meshtv.tv.mtvlib.Core.Control.Listener.MeshTVFragmentListener;
import com.ph.bittelasia.meshtv.tv.mtvlib.Core.Control.Preference.Control.Manager.MeshTVPreferenceManager;
import com.ph.bittelasia.meshtv.tv.mtvlib.Entity.Model.Weather.MeshWeatherForecast;
import com.ph.bittelasia.meshtv.tv.mtvlib.Entity.Model.Weather.MeshWeatherForecastDay;
import com.ph.bittelasia.meshtv.tv.santagrand.Core.View.Activity.TheQuayActivity;
import com.ph.bittelasia.meshtv.tv.santagrand.R;
import com.ph.bittelasia.meshtv.tv.santagrand.Weather.View.Fragment.TheQuayForecastFragment;
import com.ph.bittelasia.meshtv.tv.santagrand.Weather.View.Fragment.TheQuayTodayFragment;
import com.ph.bittelasia.meshtv.tv.santagrand.Weather.View.Fragment.TheQuayWeatherSearchDialog;

@Layout(R.layout.sg_activity_weather)
@ActivitySetting()
public class TheQuayWeather extends TheQuayActivity implements TheQuayForecastFragment.ForecastListener, MeshTVFragmentListener
{
    //==========================================Variable============================================
    //------------------------------------------Constant--------------------------------------------
    public static final String TAG = TheQuayWeather.class.getSimpleName();
    //------------------------------------------Instance--------------------------------------------
    private boolean isCelcius=true;
    //----------------------------------------------------------------------------------------------
    //------------------------------------------Fragment--------------------------------------------
    private  TheQuayForecastFragment forecastFragment;
    private  TheQuayTodayFragment    todayFragment;
    //----------------------------------------------------------------------------------------------
    //==============================================================================================

    //========================================LifeCycle=============================================
    //----------------------------------------------------------------------------------------------
    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        try {
            Button bt_room = findViewById(R.id.bt_room);
            bt_room.setOnClickListener(v -> {
                isCelcius = !isCelcius;
                todayFragment.setCelcius(isCelcius);
                forecastFragment.setCelcius(isCelcius);
            });
            Button bt_country = findViewById(R.id.bt_country);
            bt_country.setOnClickListener(v -> {
                Log.e("steward", "size: " + forecastFragment.arrayList.size());
                TheQuayWeatherSearchDialog.dialog(forecastFragment.arrayList).show(getSupportFragmentManager(), "search");
            });
        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    //----------------------------------------------------------------------------------------------
    //==============================================================================================

    //======================================TheQuayActivity=========================================
    //----------------------------------------------------------------------------------------------
    @Override
    public String getActivityName() {
        return TAG;
    }
    //----------------------------------------------------------------------------------------------
    //==============================================================================================
    //===========================================Fragment===========================================
    //----------------------------------------------------------------------------------------------
    @AttachFragment(container = R.id.ll_current,tag="today",order = 9)
    public Fragment attachDaily()
    {
        try {
            Log.i(TAG, "(0226) attachDaily()\n");
            todayFragment = new TheQuayTodayFragment();
            todayFragment.setCity(MeshTVPreferenceManager.getHotelCity(getBaseContext()));
        }catch (Exception e)
        {
            e.printStackTrace();
        }
        return todayFragment;
    }

    @AttachFragment(container = R.id.ll_forecast,tag="forecast",order = 10)
    public Fragment attachForecast()
    {
        try {
            Log.i(TAG, "(0226) attachForecast()\n");
            forecastFragment = TheQuayForecastFragment.get(MeshTVPreferenceManager.getHotelCountry(getBaseContext()));
        }catch (Exception e)
        {
            e.printStackTrace();
        }
        return forecastFragment;
    }
    //----------------------------------------------------------------------------------------------
    //==============================================================================================

    //=========================================ForecastListener=====================================
    //----------------------------------------------------------------------------------------------
    @Override
    public void update(String city, MeshWeatherForecastDay day)
    {
        try {
            if (todayFragment != null) {
                todayFragment.setCity(city);
                todayFragment.setItem(day);
            }
        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    //----------------------------------------------------------------------------------------------
    //==============================================================================================

    //==================================MeshTVFragmentListener======================================
    //----------------------------------------------------------------------------------------------
    @Override
    public void onClicked(Object o) {
        try {
            if (o instanceof MeshWeatherForecast) {
                forecastFragment.search(((MeshWeatherForecast) o).getCountry());
            }
        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void onSelected(Object o) {
        Log.i(TAG,"@onSelected "+o.getClass().getSimpleName());
    }
    //----------------------------------------------------------------------------------------------
    //==============================================================================================
}
