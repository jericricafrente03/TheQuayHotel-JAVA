package com.ph.bittelasia.meshtv.tv.santagrand.Message.View.Activity;

import android.util.Log;

import androidx.fragment.app.Fragment;

import com.ph.bittelasia.meshtv.tv.mtvlib.Core.Control.Annotation.View.Activity.ActivitySetting;
import com.ph.bittelasia.meshtv.tv.mtvlib.Core.Control.Annotation.View.Activity.AttachFragment;
import com.ph.bittelasia.meshtv.tv.mtvlib.Core.Control.Annotation.View.General.Layout;
import com.ph.bittelasia.meshtv.tv.mtvlib.Core.Control.Listener.MeshTVFragmentListener;
import com.ph.bittelasia.meshtv.tv.mtvlib.Entity.Control.RealtimeManager.MeshRealtimeManager;
import com.ph.bittelasia.meshtv.tv.santagrand.Core.View.Activity.TheQuayActivity;
import com.ph.bittelasia.meshtv.tv.santagrand.Message.View.Fragment.TheQuayMessageDisplay;
import com.ph.bittelasia.meshtv.tv.santagrand.Message.View.Fragment.TheQuayMessageInbox;
import com.ph.bittelasia.meshtv.tv.santagrand.R;
import com.ph.bittelasia.meshtv.tv.santagrand.Room.Control.TheQuayDAOManager;
import com.ph.bittelasia.meshtv.tv.santagrand.Room.Control.TheQuayRealTimeManager;

@Layout(R.layout.sg_activity_message)
@ActivitySetting()
public class TheQuayMessage extends TheQuayActivity implements MeshTVFragmentListener
{

    //=================================Variable=====================================================
    //---------------------------------Constant-----------------------------------------------------
    public final static String TAG = TheQuayMessage.class.getSimpleName();
    //---------------------------------Instance-----------------------------------------------------
    private TheQuayMessageInbox   messageInbox;
    private TheQuayMessageDisplay messageDisplay;
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


    //==================================Fragment====================================================
    //----------------------------------------------------------------------------------------------

    @AttachFragment(container = R.id.ll_inbox,tag="inbox",order=2)
    public Fragment attachInbox()
    {
        messageInbox=new TheQuayMessageInbox();
        return messageInbox;
    }

    @AttachFragment(container = R.id.ll_display,tag="display",order=3)
    public Fragment attachDisplay()
    {
        messageDisplay=new TheQuayMessageDisplay();
        return messageDisplay;
    }
    //----------------------------------------------------------------------------------------------
    //==============================================================================================



    //==================================MeshTVFragmentListener======================================
    //----------------------------------------------------------------------------------------------
    @Override
    public void onClicked(Object o) {
        try {
            Log.i(TAG, "@onClicked -> " + o.getClass().getSimpleName());
            messageDisplay.updateDisplay(o);
            replaceFragment( R.id.ll_inbox,new TheQuayMessageInbox(),"inbox");
        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void onSelected(Object o) {

    }
    //----------------------------------------------------------------------------------------------
    //==============================================================================================

}
