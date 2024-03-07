package com.ph.bittelasia.meshtv.tv.santagrand.TV.Model;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ph.bittelasia.meshtv.tv.mtvlib.Core.Control.Util.ResourceManager.MeshResourceManager;
import com.ph.bittelasia.meshtv.tv.mtvlib.Entity.Model.Channel.MeshChannel;
import com.ph.bittelasia.meshtv.tv.santagrand.Core.Control.TheQuayListAdapter;
import com.ph.bittelasia.meshtv.tv.santagrand.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class TheQuayTVChannelAdapter extends TheQuayListAdapter
{

    //===================================TheQuayListAdapter=========================================
    //----------------------------------------------------------------------------------------------

    @SuppressWarnings("unchecked")
    @Override
    public <T> void filterObjects(ArrayList<T> filters, Object object, CharSequence charSequence) {
        if (object instanceof MeshChannel) {
            MeshChannel channel=(MeshChannel) object;
            if(channel.getChannel_title() .contentEquals(charSequence))
            {
                filters.add((T)channel);
            }
        }
    }

    @Override
    public <T> ArrayList sortObjects(ArrayList<T> objects) {
        return objects;
    }


    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        try {
            final MeshChannel channel = ((MeshChannel) getObjects().get(position));
            final ViewHolder viewHolder;
            if (convertView == null)
            {
                convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.grid_item, parent, false);
                viewHolder = new ViewHolder(convertView);
                convertView.setTag(viewHolder);
            }
            else
            {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            viewHolder.tv_desc.setText(channel.getChannel_description());

            try {
                Picasso.get().load(channel.getChannel_image()).into(viewHolder.iv_icon);
            }catch (Exception e)
            {
                MeshResourceManager.displayLiveImageFor(parent.getContext(), viewHolder.iv_icon,channel.getChannel_image());
            }

            viewHolder.gv_parent.setFocusable(true);
            viewHolder.gv_parent.setOnFocusChangeListener((v, hasFocus) -> {
                ViewGroup vg = (ViewGroup)v;
                if(hasFocus)
                    vg.setBackground(v.getContext().getResources().getDrawable(R.drawable.box_selected));
                else
                    vg.setBackground(v.getContext().getResources().getDrawable(R.drawable.box));
            });
            viewHolder.gv_parent.setOnHoverListener((v, event) -> {
                v.requestFocus();
                return false;
            });
            viewHolder.gv_parent.setOnClickListener(v -> {
                if(getClickedListener()!=null)
                {
                    getClickedListener().onClicked(channel);
                    setSelectedItem(position);
                    v.requestFocus();
                }
            });
            if(getSelectedItem()==position) {
                viewHolder.gv_parent.requestFocus();
            }

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

        final LinearLayout gv_parent;
        final ImageView    iv_icon;
        final TextView     tv_desc;

        View view;

        ViewHolder(View itemView) {
            setView(itemView);
            gv_parent = itemView.findViewById(R.id.gv_parent);
            tv_desc   = itemView.findViewById(R.id.tv_desc);
            iv_icon   = itemView.findViewById(R.id.iv_icon);
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

