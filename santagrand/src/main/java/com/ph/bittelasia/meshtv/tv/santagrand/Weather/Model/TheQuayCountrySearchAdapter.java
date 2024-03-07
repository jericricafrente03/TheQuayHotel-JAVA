package com.ph.bittelasia.meshtv.tv.santagrand.Weather.Model;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ph.bittelasia.meshtv.tv.mtvlib.Core.Control.Util.Util.MeshTVTimeManager;
import com.ph.bittelasia.meshtv.tv.mtvlib.Entity.Model.Weather.MeshWeatherForecast;
import com.ph.bittelasia.meshtv.tv.santagrand.Core.Control.TheQuayListAdapter;
import com.ph.bittelasia.meshtv.tv.santagrand.R;

import java.util.ArrayList;

public class TheQuayCountrySearchAdapter extends TheQuayListAdapter {



    @Override
    public <T> void filterObjects(ArrayList<T> filters, Object object, CharSequence charSequence) {

    }

    @Override
    public <T> ArrayList sortObjects(ArrayList<T> objects) {
        return objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        try {
            final Context context = parent.getContext().getApplicationContext();
            final ViewHolder viewHolder;

            if (convertView == null) {
                convertView = LayoutInflater.from(context).inflate(R.layout.search_item, parent, false);
                viewHolder = new ViewHolder(convertView);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }

            if (getObjects() != null && getObjects().size() > 0) {
                if (getObjects().get(position) instanceof MeshWeatherForecast) {
                    MeshWeatherForecast forecast = (MeshWeatherForecast) getObjects().get(position);
                    viewHolder.tv_country.setText(("(" + forecast.getCountry() + ")" + " " + MeshTVTimeManager.getTimeZoneName(forecast.getCountry())));
                    viewHolder.getView().setOnClickListener(v -> {
                        if (getClickedListener() != null) {
                            getClickedListener().onClicked(getObjects().get(position));
                        }
                    });
                }
            }
        }catch (Exception e)
        {
            e.printStackTrace();
        }
        return convertView;
    }

    @SuppressWarnings("SpellCheckingInspection")
    static class ViewHolder{

        final TextView tv_country;

        View view;

        ViewHolder(View itemView) {
            setView(itemView);
            tv_country = itemView.findViewById(R.id.tv_search);
        }

        void setView(View view) {
            this.view = view;
        }

        View getView() {
            return view;
        }
    }
}
