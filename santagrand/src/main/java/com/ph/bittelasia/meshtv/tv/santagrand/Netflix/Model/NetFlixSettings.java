package com.ph.bittelasia.meshtv.tv.santagrand.Netflix.Model;

import android.content.Context;
import android.util.JsonReader;
import android.util.Log;

import com.ph.bittelasia.packages.Core.Control.AppTextFileConnection;

import org.json.JSONArray;
import org.json.JSONObject;

public final class NetFlixSettings {

    public static final String CONNECTION_DEVICES = "connection_devices";
    public static final String LOAD_SETTINGS      = "load_setting";



    public static boolean isNetflix(String s)
    {
        try
        {
            JSONObject app = new JSONObject(s);
            if(app.getString("app").toLowerCase().equals("netflix"))
                return true;
        }catch (Exception e)
        {
            e.printStackTrace();
        }
        return false;
    }
    public static void getSetting(Context c ,String s)
    {
        try {
            JSONObject app = new JSONObject(s);
            if(app.getString("app").toLowerCase().equals("netflix"))
            {
                 JSONArray loadArray =new JSONArray(app.getString("load_setting"));
                 if(loadArray.length()>0)
                 {
                     JSONObject loadObject = new JSONObject(loadArray.get(0).toString());
                     setLoadSettings(c,loadObject.toString());
                     Log.e("steward","load_setting");
                 }
                JSONArray connection =new JSONArray(app.getString("connection_devices"));
                 if(connection.length()>0)
                 {
                     setConnectionSetting(c,connection.toString());
                     Log.e("steward","connection_setting");
                 }
                Log.e("steward","app");
            }else
            {
                Log.e("steward","not app");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void setConnectionSetting(Context c,String s)
    {
        try {
            JSONArray jsonArray = null;
            try {
                jsonArray = new JSONArray(s);
            } catch (Exception e) {
                e.printStackTrace();
            }
            if(jsonArray!=null)
            AppTextFileConnection.writeFromFileDir(c, CONNECTION_DEVICES+".txt", jsonArray.toString() , true);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void setLoadSettings(Context c,String s)
    {
        try {
            if(s!=null)
             AppTextFileConnection.writeFromFileDir(c, LOAD_SETTINGS+".txt", s , true);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static String readSSID(Context c)
    {
        String ssid = "";
        try{
           JSONObject object = AppTextFileConnection.readObjectFromFile(c,true,LOAD_SETTINGS+".txt");
           int deviceID = object.getInt("network_device_id");

           JSONArray array = AppTextFileConnection.readFromFileDir(c,CONNECTION_DEVICES+".txt",true);

            for(int i = 0; i < array.length(); i++) {
                 JSONObject jo = array.getJSONObject(i);
                 if((jo.getInt("network_device_id"))==deviceID)
                 {
                    ssid = jo.getString("ssid");
                    break;
                 }
            }
        }catch (Exception e)
        {
          e.printStackTrace();
        }
        return ssid;
    }
}
