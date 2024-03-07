package com.ph.bittelasia.meshtv.tv.santagrand.Room.Control;

import android.app.Application;

import com.ph.bittelasia.meshtv.tv.mtvlib.Entity.Control.Annotation.RoomAnnotation;
import com.ph.bittelasia.meshtv.tv.mtvlib.Entity.Control.DAO.MeshDAOManager;
import com.ph.bittelasia.meshtv.tv.mtvlib.Entity.Control.Manager.MeshTVDataManager;
import com.ph.bittelasia.meshtv.tv.santagrand.Room.View.App.TheQuay;

@RoomAnnotation
        (
                isOnline = true,
                updateConfig = true,
                updateGuest = true,
                updateChannel = true,
                updateChannelCategory =  true,
                updateFacility = true,
                updateFacilityCategory = true,
                updateVC = true,
                updateVCCategory = true,
                updateRoomType = true,
                updateWeather = true
        )
public class TheQuayManager extends MeshTVDataManager
{
    //==========================================Variable============================================
    //------------------------------------------Constant--------------------------------------------
    public static final String TAG = TheQuayManager.class.getSimpleName();
    //----------------------------------------------------------------------------------------------
    //-------------------------------------------Static---------------------------------------------
    static TheQuayManager manager = null;
    //----------------------------------------------------------------------------------------------
    //==============================================================================================
    //===========================================Static=============================================
    public static TheQuayManager get()
    {
        if(manager==null)
        {
            manager = new TheQuayManager();
        }
        return manager;
    }
    //==============================================================================================
    //========================================MeshTVDataManager=====================================
    @Override
    public Application getAppContext() {
        return TheQuay.app;
    }
    @Override
    public MeshDAOManager getDAOManager() {
        return TheQuayDAOManager.get();
    }
    //==============================================================================================
}
