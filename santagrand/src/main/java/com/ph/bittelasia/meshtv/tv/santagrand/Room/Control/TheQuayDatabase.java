package com.ph.bittelasia.meshtv.tv.santagrand.Room.Control;



import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.ph.bittelasia.meshtv.tv.mtvlib.Entity.Control.DAO.MeshAirMediaInstructionDAO;
import com.ph.bittelasia.meshtv.tv.mtvlib.Entity.Control.DAO.MeshChannelCategoryDAO;
import com.ph.bittelasia.meshtv.tv.mtvlib.Entity.Control.DAO.MeshChannelDAO;
import com.ph.bittelasia.meshtv.tv.mtvlib.Entity.Control.DAO.MeshFacilityCategoryDAO;
import com.ph.bittelasia.meshtv.tv.mtvlib.Entity.Control.DAO.MeshFacilityDAO;
import com.ph.bittelasia.meshtv.tv.mtvlib.Entity.Control.DAO.MeshMessageDAO;
import com.ph.bittelasia.meshtv.tv.mtvlib.Entity.Control.DAO.MeshRoomTypeDAO;
import com.ph.bittelasia.meshtv.tv.mtvlib.Entity.Control.DAO.MeshVCCategoryDAO;
import com.ph.bittelasia.meshtv.tv.mtvlib.Entity.Control.DAO.MeshVCDAO;
import com.ph.bittelasia.meshtv.tv.mtvlib.Entity.Control.DAO.MeshWeatherForecastDAO;
import com.ph.bittelasia.meshtv.tv.mtvlib.Entity.Model.Airmedia.MeshAirMediaInstruction;
import com.ph.bittelasia.meshtv.tv.mtvlib.Entity.Model.Channel.MeshChannel;
import com.ph.bittelasia.meshtv.tv.mtvlib.Entity.Model.Channel.MeshChannelCategory;
import com.ph.bittelasia.meshtv.tv.mtvlib.Entity.Model.Facility.MeshFacility;
import com.ph.bittelasia.meshtv.tv.mtvlib.Entity.Model.Facility.MeshFacilityCategory;
import com.ph.bittelasia.meshtv.tv.mtvlib.Entity.Model.Message.MeshMessage;
import com.ph.bittelasia.meshtv.tv.mtvlib.Entity.Model.RoomType.MeshRoomType;
import com.ph.bittelasia.meshtv.tv.mtvlib.Entity.Model.VC.MeshVC;
import com.ph.bittelasia.meshtv.tv.mtvlib.Entity.Model.VC.MeshVCCategory;
import com.ph.bittelasia.meshtv.tv.mtvlib.Entity.Model.Weather.MeshWeatherForecast;
import com.ph.bittelasia.meshtv.tv.santagrand.Room.View.App.TheQuay;


@Database
        (
                version = 1,
                entities =
                        {
                                MeshAirMediaInstruction.class,
                                MeshChannel.class,
                                MeshChannelCategory.class,
                                MeshFacility.class,
                                MeshFacilityCategory.class,
                                MeshVCCategory.class,
                                MeshVC.class,
                                MeshMessage.class,
                                MeshWeatherForecast.class,
                                MeshRoomType.class
                        },
                exportSchema = false
        )
public abstract class TheQuayDatabase extends RoomDatabase
{
    //============================================Variable==========================================
    //--------------------------------------------Constant------------------------------------------
    public static final String TAG = TheQuayDatabase.class.getSimpleName();
    //----------------------------------------------------------------------------------------------
    //---------------------------------------------Static-------------------------------------------
    static TheQuayDatabase database;
    //----------------------------------------------------------------------------------------------
    //==============================================================================================
    //=============================================Static===========================================
    public static TheQuayDatabase get()
    {
        if(database==null)
        {
            database = Room.databaseBuilder(TheQuay.app,TheQuayDatabase.class,"the_quay_db").build();
        }
        return database;
    }
    //==============================================================================================
    //==============================================DAO=============================================
    public abstract MeshAirMediaInstructionDAO getMeshAirMediaInstructionDAO();
    public abstract MeshChannelDAO getMeshChannelDAO();
    public abstract MeshChannelCategoryDAO getMeshChannelCategoryDAO();
    public abstract MeshFacilityDAO getMeshFacilityDAO();
    public abstract MeshFacilityCategoryDAO getMeshFacilityCategoryDAO();
    public abstract MeshVCCategoryDAO getMeshVCCategoryDAO();
    public abstract MeshVCDAO getMeshVCDAO();
    public abstract MeshMessageDAO getMeshMessageDAO();
    public abstract MeshWeatherForecastDAO getMeshWeatherForecastDAO();
    public abstract MeshRoomTypeDAO getMeshRoomTypeDAO();
    //==============================================================================================


}
