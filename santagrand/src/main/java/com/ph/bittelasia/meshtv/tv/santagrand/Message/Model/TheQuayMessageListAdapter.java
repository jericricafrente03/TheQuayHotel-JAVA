package com.ph.bittelasia.meshtv.tv.santagrand.Message.Model;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ph.bittelasia.meshtv.tv.mtvlib.Entity.Model.Message.MeshMessage;
import com.ph.bittelasia.meshtv.tv.santagrand.Core.Control.TheQuayListAdapter;
import com.ph.bittelasia.meshtv.tv.santagrand.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Locale;

public class TheQuayMessageListAdapter extends TheQuayListAdapter {

    //=====================================Variable=================================================
    //-------------------------------------Constant-------------------------------------------------
    private final String DATE_FORMAT = "dd MMMM yyyy";
    private final String TIME_FORMAT = "hh:mm aa";
    //----------------------------------------------------------------------------------------------
    //==============================================================================================


    //===================================TheQuayListAdapter=========================================
    //----------------------------------------------------------------------------------------------
    @Override
    public <T> void filterObjects(ArrayList<T> filters, Object object, CharSequence charSequence) {

    }

    @SuppressWarnings({"unchecked", "ConstantConditions"})
    @Override
    public <T> ArrayList sortObjects(ArrayList<T> objects) {
        ArrayList<MeshMessage> messages = ((ArrayList<MeshMessage>) objects);
        Collections.sort(messages, (o1, o2) -> {

            int        result = -1;
            Date       date1 = null;
            Date       date2 = null;
            DateFormat format;
            try {
                format = new SimpleDateFormat(MeshMessage.DATE_FORMAT, Locale.ENGLISH);
                date1 = format.parse(o1.getMessg_datetime());
                date2 = format.parse(o2.getMessg_datetime());
            }catch (Exception e)
            {
                e.printStackTrace();
            }
            if(date1.compareTo(date2)==0)
                result = 0;
            else if(date1.compareTo(date2) < 0)
                result = 1;
            return result;
        });
        return messages;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        try {
            final MeshMessage message = ((MeshMessage) getObjects().get(position));
            final ViewHolder viewHolder;
            if (convertView == null)
            {
                convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.sg_message_item, parent, false);
                viewHolder = new ViewHolder(convertView);
                convertView.setTag(viewHolder);
            }
            else
            {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            viewHolder.tv_subject.setText((message.getMessg_subject()+""));
            viewHolder.tv_date.setText((new SimpleDateFormat(DATE_FORMAT, Locale.US).format(new SimpleDateFormat(MeshMessage.DATE_FORMAT,Locale.US).parse(message.getMessg_datetime()))));
            viewHolder.tv_time.setText(new SimpleDateFormat(TIME_FORMAT,Locale.US).format(new SimpleDateFormat(MeshMessage.DATE_FORMAT,Locale.US).parse(message.getMessg_datetime())));
            viewHolder.iv_status.setImageDrawable(parent.getContext().getResources().getDrawable(message.getMessg_status()>1?R.drawable.msg_read:R.drawable.msg_unread));

            viewHolder.getView().setOnClickListener(v ->{
                if(getClickedListener()!=null)
                {
                    getClickedListener().onClicked(getObjects().get(position));
                }
            });
            viewHolder.getView().setFocusable(true);
            viewHolder.getView().setOnFocusChangeListener((v, hasFocus) -> {
                ViewGroup vg = (ViewGroup)v;
                if(hasFocus)
                {
                    vg.setBackgroundColor(vg.getContext().getResources().getColor(R.color.dirtyBrown));
                }
                else
                {
                    vg.setBackgroundColor(vg.getContext().getResources().getColor(R.color.transparent));
                }
            });
            viewHolder.getView().setOnHoverListener((v, event) -> {
                v.requestFocus();
                return false;
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

        final TextView  tv_date;
        final TextView  tv_time;
        final TextView  tv_subject;
        final ImageView iv_status;

        View view;

        ViewHolder(View itemView) {
            setView(itemView);
            tv_date      = itemView.findViewById(R.id.tv_date);
            tv_time      = itemView.findViewById(R.id.tv_time);
            tv_subject   = itemView.findViewById(R.id.tv_subject);
            iv_status    = itemView.findViewById(R.id.iv_status);
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

