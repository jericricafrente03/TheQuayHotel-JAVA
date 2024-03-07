package com.ph.bittelasia.meshtv.tv.santagrand.Room.Control;

import android.app.Application;

import com.ph.bittelasia.meshtv.tv.mtvlib.Entity.Control.DAO.MeshAirMediaInstructionDAO;
import com.ph.bittelasia.meshtv.tv.mtvlib.Entity.Control.DAO.MeshChannelCategoryDAO;
import com.ph.bittelasia.meshtv.tv.mtvlib.Entity.Control.DAO.MeshChannelDAO;
import com.ph.bittelasia.meshtv.tv.mtvlib.Entity.Control.DAO.MeshDAOManager;
import com.ph.bittelasia.meshtv.tv.mtvlib.Entity.Control.DAO.MeshFacilityCategoryDAO;
import com.ph.bittelasia.meshtv.tv.mtvlib.Entity.Control.DAO.MeshFacilityDAO;
import com.ph.bittelasia.meshtv.tv.mtvlib.Entity.Control.DAO.MeshMessageDAO;
import com.ph.bittelasia.meshtv.tv.mtvlib.Entity.Control.DAO.MeshRoomTypeDAO;
import com.ph.bittelasia.meshtv.tv.mtvlib.Entity.Control.DAO.MeshStatDAO;
import com.ph.bittelasia.meshtv.tv.mtvlib.Entity.Control.DAO.MeshVCCategoryDAO;
import com.ph.bittelasia.meshtv.tv.mtvlib.Entity.Control.DAO.MeshVCDAO;
import com.ph.bittelasia.meshtv.tv.mtvlib.Entity.Control.DAO.MeshWeatherForecastDAO;
import com.ph.bittelasia.meshtv.tv.santagrand.Room.View.App.TheQuay;

public class TheQuayDAOManager extends MeshDAOManager
{

    //===========================================Variable===========================================
    //-------------------------------------------Constant-------------------------------------------
    public static final String TAG = TheQuayDAOManager.class.getSimpleName();

    @Override
    public MeshStatDAO getStatDAO() {
        return null;
    }

    //----------------------------------------------------------------------------------------------
    //-------------------------------------------Constant-------------------------------------------
    static TheQuayDAOManager manager = null;
    //----------------------------------------------------------------------------------------------
    //==============================================================================================
    //=============================================Static===========================================
    public static TheQuayDAOManager get()
    {
        if(manager == null)
        {
            manager = new TheQuayDAOManager();
        }
        return manager;
    }
    //==============================================================================================
    //=========================================MeshDAOManager=======================================
    @Override
    public MeshAirMediaInstructionDAO getAirMediaInstructionDAO() { return TheQuayDatabase.get().getMeshAirMediaInstructionDAO(); }
    @Override
    public MeshChannelCategoryDAO getChannelCategoryDAO() {return TheQuayDatabase.get().getMeshChannelCategoryDAO(); }
    @Override
    public MeshChannelDAO getChannelDAO() {
        return TheQuayDatabase.get().getMeshChannelDAO();
    }
    @Override
    public MeshFacilityDAO getFacilityDAO() { return TheQuayDatabase.get().getMeshFacilityDAO(); }
    @Override
    public MeshFacilityCategoryDAO getFacilityCategoryDAO() { return TheQuayDatabase.get().getMeshFacilityCategoryDAO(); }
    @Override
    public MeshMessageDAO getMessageDAO() { return TheQuayDatabase.get().getMeshMessageDAO(); }
    @Override
    public MeshVCCategoryDAO getVCCategory() { return TheQuayDatabase.get().getMeshVCCategoryDAO(); }
    @Override
    public MeshVCDAO getVC() { return TheQuayDatabase.get().getMeshVCDAO(); }
    @Override
    public MeshWeatherForecastDAO getWeatherForecast() { return TheQuayDatabase.get().getMeshWeatherForecastDAO(); }
    @Override
    public MeshRoomTypeDAO getRoomTypeDAO() { return TheQuayDatabase.get().getMeshRoomTypeDAO(); }
    @Override
    public Application getApplicationContext() { return TheQuay.app; }
    //==============================================================================================
}
