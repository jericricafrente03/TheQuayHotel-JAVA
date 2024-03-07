package com.ph.bittelasia.meshtv.tv.santagrand.Airmedia.Model;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ph.bittelasia.meshtv.tv.mtvlib.AirMedia.Model.MeshAirmediaInstructions;
import com.ph.bittelasia.meshtv.tv.santagrand.Core.Control.TheQuayListAdapter;
import com.ph.bittelasia.meshtv.tv.santagrand.R;

import java.util.ArrayList;


public class TheQuayAirMediaListAdapter extends TheQuayListAdapter{


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
            final MeshAirmediaInstructions instructions = ((MeshAirmediaInstructions) getObjects().get(position));
            final Context context = parent.getContext().getApplicationContext();
            final ViewHolder viewHolder;

            if (convertView == null) {
                convertView = LayoutInflater.from(context).inflate(R.layout.sg_airmedia_list_item_layout, parent, false);
                viewHolder = new ViewHolder(convertView);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }


            viewHolder.tv_number.setText(((instructions.getNo() <= 0 ? "    " : instructions.getNo() + ".) ") + ""));
            viewHolder.tv_req.setText(Html.fromHtml(instructions.getText()));
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

        final TextView     tv_number;
        final TextView     tv_req;

        View view;

        ViewHolder(View itemView) {
            setView(itemView);
            tv_req = itemView.findViewById(R.id.tv_req);
            tv_number = itemView.findViewById(R.id.tv_number);
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
