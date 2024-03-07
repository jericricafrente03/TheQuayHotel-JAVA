package com.ph.bittelasia.meshtv.tv.santagrand.Airmedia.Model;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ph.bittelasia.meshtv.tv.mtvlib.Core.Control.Util.ResourceManager.MeshResourceManager;
import com.ph.bittelasia.meshtv.tv.santagrand.Core.Control.TheQuayListAdapter;
import com.ph.bittelasia.meshtv.tv.santagrand.R;


import java.util.ArrayList;

public class TheQuayGridFragmentAdapter extends TheQuayListAdapter {


    //===================================TheQuayListAdapter=========================================
    //----------------------------------------------------------------------------------------------
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
            final AirMediaPlatform platform = ((AirMediaPlatform) getObjects().get(position));
            final Context context = parent.getContext().getApplicationContext();
            final ViewHolder viewHolder;

            if (convertView == null) {
                convertView = LayoutInflater.from(context).inflate(R.layout.sg_airmedia_grid_item, parent, false);
                viewHolder = new ViewHolder(convertView);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }

            if (position == 0) {
                viewHolder.iv_icon.setImageDrawable(context.getResources().getDrawable(MeshResourceManager.get().getResourceId("ANDROID")));
            } else if (position == 1) {
                viewHolder.iv_icon.setImageDrawable(context.getResources().getDrawable(MeshResourceManager.get().getResourceId("IOS")));
            } else if (position == 2) {
                viewHolder.iv_icon.setImageDrawable(context.getResources().getDrawable(MeshResourceManager.get().getResourceId("WINDOWS")));
            }

            viewHolder.getView().setOnClickListener(v -> {
                if (getClickedListener() != null) {
                    getClickedListener().onClicked(platform);
                }
            });
        }catch (Exception e)
        {
            e.printStackTrace();
        }

        return convertView;
    }
    //----------------------------------------------------------------------------------------------
    //==============================================================================================



    //========================================ViewHolder============================================
    //----------------------------------------------------------------------------------------------
    @SuppressWarnings("SpellCheckingInspection")
    static class ViewHolder{

        final ImageView    iv_icon;
        final TextView     tv_desc;

        View view;

        ViewHolder(View itemView) {
            setView(itemView);
            tv_desc   = itemView.findViewById(R.id.tv_platform);
            iv_icon   = itemView.findViewById(R.id.iv_platform);
        }

        void setView(View view) {
            this.view = view;
        }

        View getView() {
            return view;
        }
    }
    //----------------------------------------------------------------------------------------------
    //==============================================================================================

}
