package com.ph.bittelasia.meshtv.tv.santagrand.Weather.View.Fragment;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.GridView;

import com.ph.bittelasia.meshtv.tv.mtvlib.Core.Control.Annotation.View.General.Layout;
import com.ph.bittelasia.meshtv.tv.mtvlib.Core.Control.Annotation.View.Widget.BindWidget;
import com.ph.bittelasia.meshtv.tv.mtvlib.Core.Control.Util.ResourceManager.MeshResourceManager;
import com.ph.bittelasia.meshtv.tv.mtvlib.Entity.Control.Listener.MeshWeatherForecastListener;
import com.ph.bittelasia.meshtv.tv.mtvlib.Entity.Model.Filter.MeshCountryFilter;
import com.ph.bittelasia.meshtv.tv.mtvlib.Entity.Model.Weather.MeshWeatherForecast;
import com.ph.bittelasia.meshtv.tv.mtvlib.Entity.Model.Weather.MeshWeatherForecastDay;
import com.ph.bittelasia.meshtv.tv.santagrand.Room.Control.TheQuayDAOManager;
import com.ph.bittelasia.meshtv.tv.santagrand.Room.Library.MeshTVRFragment;
import com.ph.bittelasia.meshtv.tv.santagrand.R;
import com.ph.bittelasia.meshtv.tv.santagrand.Weather.Model.TheQuayForecastAdapter;

import java.util.ArrayList;

@Layout(R.layout.sg_forecast_fragment)
public class TheQuayForecastFragment extends MeshTVRFragment<MeshWeatherForecast> implements MeshWeatherForecastListener
{
    //===========================================Variable===========================================
    //-------------------------------------------Constant-------------------------------------------
    public static final String TAG = TheQuayForecastFragment.class.getSimpleName();
    //----------------------------------------------------------------------------------------------
    //---------------------------------------------View---------------------------------------------
    @BindWidget(R.id.gv_weather)
    public GridView gv_weather;
    //----------------------------------------------------------------------------------------------
    //-------------------------------------------Instance-------------------------------------------
    private ArrayList<MeshWeatherForecastDay> forecastsDays;
    private String country = "SG";
    private String city = "Singapore";
    private ForecastListener fcListener = null;
    private boolean isCelsius;
    public ArrayList arrayList;
    //----------------------------------------------------------------------------------------------
    //==============================================================================================
    //==========================================LifeCycle===========================================

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            if (getActivity() instanceof ForecastListener) {
                fcListener = (ForecastListener) getActivity();
            }
        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        fcListener = null;

    }

    //==============================================================================================
    //============================================Static============================================
    public static TheQuayForecastFragment get(String country)
    {
        TheQuayForecastFragment fragment = new TheQuayForecastFragment();
        try {
            Log.i(TAG, "(0226) Static\n");
            fragment.setCountry(country);
        }catch (Exception e)
        {
            e.printStackTrace();
        }
        return fragment;
    }
    //==============================================================================================
    //===========================================Constructor========================================
    public TheQuayForecastFragment()
    {
        Log.i(TAG,"(0226) Constructor\n");
        this.forecastsDays = new ArrayList<>();
    }
    //==============================================================================================
    //===========================================LifeCycle==========================================
    @Override
    public void onResume()
    {
        try {
            super.onResume();
            Log.i(TAG, "(0226) onResume\n");
            TheQuayDAOManager.get().addListener(this);
            TheQuayDAOManager.get().retrieve(MeshWeatherForecast.class);
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
            Log.i(TAG, "(0226) onPause\n");
            TheQuayDAOManager.get().removeListener(this);
        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    //==============================================================================================
    //============================================Method============================================
    //--------------------------------------------Action--------------------------------------------
    public void updateWeather()
    {
        try {
            Log.i(TAG, "(0226) updateWeather\n");
            MeshCountryFilter filter = new MeshCountryFilter();
            filter.setCountry(country);
            TheQuayDAOManager.get().filter(MeshWeatherForecast.class, filter);
        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    private void display()
    {
        try {
            Log.i(TAG, "(0226) updateWeather\n");
            Log.i(TAG, "(0226) WEATHER Country:" + country + "\n");
            Log.i(TAG, "(0226) WEATHER size:" + forecastsDays.size() + "\n");
            Log.i(TAG, "(0226) isCelsius:" + isCelsius + "\n");
            if (gv_weather != null) {
                if (forecastsDays.size() > 0) {
                    if (fcListener != null) {
                        fcListener.update(city, forecastsDays.get(0));
                    }
                    forecastsDays.remove(0);
                    TheQuayForecastAdapter adapter =
                            new TheQuayForecastAdapter
                                    (
                                            getActivity(),
                                            gv_weather,
                                            R.layout.sg_weather_item,
                                            forecastsDays,
                                            isCelsius
                                    );
                    gv_weather.setAdapter
                            (
                                    adapter
                            );
                }

            }
        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    private void loadForeCast(ArrayList arrayList)
    {
        try {
            Log.i(TAG, "(0226) loadForeCast\n");
            for (Object o : arrayList) {
                if (o instanceof MeshWeatherForecast) {
                    MeshWeatherForecast forecast = (MeshWeatherForecast) o;
                    if (forecast.getCountry().equals(country)) {
                        city = forecast.getCity();
                        if (this.forecastsDays == null) {
                            this.forecastsDays = new ArrayList<>();
                        }
                        this.forecastsDays.clear();
                        this.forecastsDays.addAll(forecast.getForecasts());
                        Log.i(TAG, "(0226) country filtered: " + forecast.getCountry());
                    }
                    Log.i(TAG, "(0226) country: " + forecast.getCountry());
                }
            }
        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    public void search(String country)
    {
        try {
            for (Object o : arrayList) {
                if (o instanceof MeshWeatherForecast) {
                    MeshWeatherForecast forecast = (MeshWeatherForecast) o;
                    if (forecast.getCountry().equals(country)) {
                        city = forecast.getCity();
                        if (this.forecastsDays == null) {
                            this.forecastsDays = new ArrayList<>();
                        }
                        this.forecastsDays.clear();
                        this.forecastsDays.addAll(forecast.getForecasts());
                        Log.i(TAG, "(0226) country filtered: " + forecast.getCountry());
                    }
                    Log.i(TAG, "(0226) country: " + forecast.getCountry());
                }

            }
            display();
        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    //----------------------------------------------------------------------------------------------
    //--------------------------------------------Getter--------------------------------------------
    public String getCountry()
    {
        Log.i(TAG,"(0226) getCountry\n");
        return country;
    }
    //----------------------------------------------------------------------------------------------
    //--------------------------------------------Setter--------------------------------------------
    public void setCountry(String country)
    {
        Log.i(TAG,"(0226) setCountry\n");
        this.country = country.toUpperCase();
    }
    //----------------------------------------------------------------------------------------------
    public void setCelcius(boolean celsius) {
        try {
            isCelsius = celsius;
            if (gv_weather != null) {
                if (forecastsDays.size() > 0) {
                    gv_weather.setAdapter
                            (
                                    new TheQuayForecastAdapter
                                            (
                                                    getActivity(),
                                                    gv_weather,
                                                    R.layout.sg_weather_item,
                                                    forecastsDays,
                                                    isCelsius
                                            )
                            );
                }

            }
        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    //----------------------------------------------------------------------------------------------
    //==============================================================================================
    //===================================MeshWeatherForecastListener================================
    @Override
    public void onInsertedBulk(ArrayList arrayList)
    {
        try {
            this.arrayList = arrayList;
            loadForeCast(arrayList);
            display();
        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void onDeletedBulk(ArrayList arrayList)
    {
        try {
            MeshCountryFilter filter = new MeshCountryFilter();
            filter.setCountry(country);
            TheQuayDAOManager.get().filter(MeshWeatherForecast.class, filter);
        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void onRetrieved(ArrayList arrayList)
    {
        try {
            if (arrayList.size() > 0) {
                this.arrayList = arrayList;
                loadForeCast(arrayList);
                display();
            }
        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    @Override
    public void onCleared(Class aClass)
    {
        try {
            MeshCountryFilter filter = new MeshCountryFilter();
            filter.setCountry(country);
            TheQuayDAOManager.get().filter(MeshWeatherForecast.class, filter);
        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    @Override
    public void onDAONotFound(String s)
    {

    }
    //==============================================================================================
    //=========================================MeshTVRFragment======================================
    @Override
    public void draw(View v)
    {
        display();
    }
    @Override
    public void updateDisplay(MeshWeatherForecast item)
    {
        display();
    }
    //==============================================================================================
    //===========================================DateUpdate=========================================
    public interface ForecastListener
    {
        public abstract void update(String city, MeshWeatherForecastDay day);
    }
    //==============================================================================================
}
