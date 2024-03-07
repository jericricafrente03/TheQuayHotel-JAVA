package com.ph.bittelasia.meshtv.tv.santagrand.Core.Control;

import android.app.IntentService;
import android.content.Intent;

import android.util.Log;

import androidx.annotation.Nullable;

import com.ph.bittelasia.meshtv.tv.meshxmpplibrary.Core.Control.Manager.MeshXMPPManager;
import com.ph.bittelasia.meshtv.tv.meshxmpplibrary.Core.Control.Preference.MeshXMPPPreference;
import com.ph.bittelasia.meshtv.tv.meshxmpplibrary.Core.Model.MeshXMPPUser;
import com.ph.bittelasia.meshtv.tv.mtvlib.Core.Control.Preference.Control.Manager.MeshTVPreferenceManager;
import com.ph.bittelasia.meshtv.tv.santagrand.Core.View.Activity.TheQuayActivity;
import com.ph.bittelasia.meshtv.tv.santagrand.Room.Control.TheQuayDAOManager;


public class ConnectionService extends IntentService {

    //====================================Variable==================================================
    //------------------------------------Instance--------------------------------------------------
    private static final String TAG = ConnectionService.class.getSimpleName();
    //----------------------------------------------------------------------------------------------
    //==============================================================================================



    //==================================Constructor=================================================
    //----------------------------------------------------------------------------------------------
    public ConnectionService()
    {
        super(TAG);
    }
    //----------------------------------------------------------------------------------------------
    //==============================================================================================


    //====================================IntentService=============================================
    //----------------------------------------------------------------------------------------------
    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        try {
            if(TheQuayActivity.activity !=null)
            while (!TheQuayActivity.activity.isConnected ) {
                TheQuayDAOManager.get().init();
                MeshTVPreferenceManager.updateIPTV(this);
                MeshXMPPPreference.enablePLAIN(getApplicationContext(),true);
                MeshXMPPPreference.enableDIGESTMD5(getApplicationContext(),true);
                MeshXMPPPreference.enableSCRAMSHA1(getApplicationContext(),true);
                MeshXMPPPreference.setShouldReport(getApplicationContext(),true);
                MeshXMPPPreference.setReportTo(getApplicationContext(),"mars2");
                MeshXMPPUser user =
                        new MeshXMPPUser
                                (
                                        MeshTVPreferenceManager.getXUsername(getApplicationContext()),
                                        MeshTVPreferenceManager.getPassword(getApplicationContext()),
                                        true,
                                        "mars2"
                                );
                user.setDomain("localhost");
                user.setHost(MeshTVPreferenceManager.getXMPPHost(getApplicationContext()));
                user.setPort(MeshTVPreferenceManager.getXMPPPort(getApplicationContext()));
                MeshXMPPManager.connect(getApplicationContext(), TheQuayActivity.activity,user);
                try {
                    Thread.sleep(10000L);
                } catch (InterruptedException ignore) {}
            }
        }catch (Exception e)
        {
            Log.e(TAG,"@onHandleIntent->"+e.getMessage());
            e.printStackTrace();
        }
    }
    //----------------------------------------------------------------------------------------------
    //==============================================================================================

}
