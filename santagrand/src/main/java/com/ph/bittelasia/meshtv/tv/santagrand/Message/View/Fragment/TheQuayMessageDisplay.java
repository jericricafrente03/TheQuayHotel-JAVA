package com.ph.bittelasia.meshtv.tv.santagrand.Message.View.Fragment;

import android.text.Html;
import android.view.View;
import android.widget.TextView;

import com.ph.bittelasia.meshtv.tv.mtvlib.Core.Control.Annotation.View.General.Layout;
import com.ph.bittelasia.meshtv.tv.mtvlib.Core.Control.Annotation.View.Widget.BindWidget;
import com.ph.bittelasia.meshtv.tv.mtvlib.Entity.Model.Message.MeshMessage;
import com.ph.bittelasia.meshtv.tv.santagrand.R;
import com.ph.bittelasia.meshtv.tv.santagrand.Room.Library.MeshTVRFragment;

import java.text.SimpleDateFormat;
import java.util.Locale;

@Layout(R.layout.sg_message_display)
public class TheQuayMessageDisplay extends MeshTVRFragment {

    String DATE_FORMAT = "dd MMMM yyyy | hh:mm aa";

    @BindWidget(R.id.tv_sender)
    public   TextView tv_sender;
    @BindWidget(R.id.tv_subject)
    public  TextView tv_subject;
    @BindWidget(R.id.tv_date)
    public  TextView tv_date;
    @BindWidget(R.id.tv_message)
    public  TextView tv_message;

    
    
    
    

    @Override
    public void draw(View v) {
        
    }

    @Override
    public void updateDisplay(Object item) {
        try
        {
            MeshMessage message =(MeshMessage)item;
            tv_sender.setText(message.getMessg_from());
            tv_subject.setText(message.getMessg_subject());
            tv_date.setText(message.getMessg_datetime());
            tv_message.setText(Html.fromHtml(message.getMessg_text()));
            tv_date.setText(new SimpleDateFormat(DATE_FORMAT, Locale.US).format(new SimpleDateFormat(MeshMessage.DATE_FORMAT,Locale.US).parse(message.getMessg_datetime())));
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

}

