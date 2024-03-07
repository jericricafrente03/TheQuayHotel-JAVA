package com.ph.bittelasia.meshtv.tv.santagrand.Room.Control;

import com.ph.bittelasia.meshtv.tv.mtvlib.Entity.Control.DAO.MeshDAOManager;
import com.ph.bittelasia.meshtv.tv.mtvlib.Entity.Control.RealtimeManager.MeshRealtimeManager;

public class TheQuayRealTimeManager extends MeshRealtimeManager
{
    //==============================================Variable========================================
    //----------------------------------------------Constant----------------------------------------
    public static final String TAG = TheQuayRealTimeManager.class.getSimpleName();
    //----------------------------------------------------------------------------------------------
    //-----------------------------------------------Static-----------------------------------------
    private static TheQuayRealTimeManager manager = null;
    //----------------------------------------------------------------------------------------------
    //==============================================================================================
    //===============================================Static=========================================
    public static TheQuayRealTimeManager get()
    {
        if(manager==null)
        {
            manager = new TheQuayRealTimeManager();
        }
        return manager;
    }
    //==============================================================================================
    //==============================================Constructor=====================================
    private TheQuayRealTimeManager()
    {
        super();
    }
    //==============================================================================================
    //=========================================MeshRealtimeManager==================================
    @Override
    public MeshDAOManager getDAOManager()
    {
        return TheQuayDAOManager.get();
    }

}
