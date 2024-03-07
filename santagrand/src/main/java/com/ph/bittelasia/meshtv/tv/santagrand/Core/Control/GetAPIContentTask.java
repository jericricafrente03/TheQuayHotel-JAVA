package com.ph.bittelasia.meshtv.tv.santagrand.Core.Control;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.ph.bittelasia.meshtv.tv.mtvlib.Core.Control.Preference.Control.Manager.MeshTVPreferenceManager;
import com.ph.bittelasia.meshtv.tv.mtvlib.Core.Control.Util.Util.MeshAPIKeyRetriever;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


public class GetAPIContentTask extends AsyncTask<Void,Void,Void> {

    //=====================================Variable=================================================
    //-------------------------------------Constant-------------------------------------------------
    private String TAG = GetAPIContentTask.class.getSimpleName();
    public final static String API_DEVICE_STATUS    = "get_device_status";
    public final static String API_TV_APP_ANALYTICS = "analytics_tv_app";
    public final static String API_TV_CH_ANALYTICS  = "analytics_tv_channels";
    public final static String API_OTHERS_ANALYTICS = "analytics_other_app";
    //----------------------------------------------------------------------------------------------
    //-------------------------------------Instance-------------------------------------------------
    private Context context;
    private String  api;
    private String  appID;
    private String  otherAppID;
    private String  deviceID;
    private String  channelID;
    private String  contents;
    private String  guest;
    private int     tick;
    //----------------------------------------------------------------------------------------------
    //==============================================================================================


    //===================================Constructor================================================
    //----------------------------------------------------------------------------------------------
    public GetAPIContentTask(Context context, String api){
        setContext(context);
        setApi(api);
    }
    public GetAPIContentTask(Context context){
        setContext(context);
    }
    //----------------------------------------------------------------------------------------------
    //==============================================================================================


    //===================================AsyncTask==================================================
    //----------------------------------------------------------------------------------------------

    @Override
    protected Void doInBackground(Void... voids) {
        String result="";
        try {
            StringBuilder sb = null;
            String source = MeshTVPreferenceManager.getHTTPHost(context) + ":" + MeshTVPreferenceManager.getHTTPPort(context) + "/index.php/apicall/" + getApi();

            if(getApi()!=null) {
                if (getApi().equals(API_DEVICE_STATUS)) {
                    source = source + "/" + MeshAPIKeyRetriever.getAPIKey(context).replaceAll(":", "").toLowerCase();
                    if (getGuest() != null) {
                        if (getGuest().equals("Welcome"))
                            source = source + "/0";
                        else
                            source = source + "/1";
                    }
                } else if (getApi().equals(API_TV_APP_ANALYTICS)) {
                    if (getAppID() != null)
                        source = source + "/" + getAppID();
                    if (getDeviceID() != null)
                        source = source + "/" + getDeviceID();
                    if (getTick() != 0)
                        source = source + "/" + getTick() + "";
                } else if (getApi().equals(API_TV_CH_ANALYTICS)) {
                    if (getChannelID() != null)
                        source = source + "/" + getChannelID();
                    if (getDeviceID() != null)
                        source = source + "/" + getDeviceID();
                    if (getTick() != 0)
                        source = source + "/" + getTick() + "";
                } else if (getApi().equals(API_OTHERS_ANALYTICS)) {
                    if (getOtherAppID() != null)
                        source = source + "/" + getOtherAppID();
                    if (getDeviceID() != null)
                        source = source + "/" + getDeviceID();
                    if (getTick() != 0)
                        source = source + "/" + getTick() + "";
                }

            }
            URL url = new URL(source.replaceAll("\\n", ""));
            HttpURLConnection conn = (HttpURLConnection)url.openConnection();
            conn.setReadTimeout(MeshTVPreferenceManager.getTimeOut(context));
            conn.setConnectTimeout(MeshTVPreferenceManager.getConnectionTimeout(context));
            conn.setRequestMethod("GET");
            conn.setDoInput(true);
            conn.setDoOutput(true);
            conn.connect();
            int statusCode = conn.getResponseCode();
            if (statusCode == 200) {
                sb = new StringBuilder();
                BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String line;
                while ((line = reader.readLine()) != null) {
                    sb.append(line);
                    sb.append("\n");
                }
            }
            conn.disconnect();
            if (sb!=null){
                contents =sb.toString();
            }
            Log.i(TAG,"statusCode: "+statusCode);
            Log.i(TAG,"source: "+source);

        } catch (Exception e) {
           e.printStackTrace();
        }
        return  null;
    }
    //----------------------------------------------------------------------------------------------
    //==============================================================================================

    //=======================================Method=================================================
    //---------------------------------------Setter-------------------------------------------------

    public void setContext(Context context) {
        this.context = context;
    }

    public void setApi(String api) {
        this.api = api;
    }

    public void setAppID(String appID){
        this.appID=appID;
    }

    public void setOtherAppID(String otherAppID) {
        this.otherAppID = otherAppID;
    }

    public void setTick(int tick) {
        this.tick = tick;
    }

    public void setDeviceID(String deviceID) {
        this.deviceID = deviceID;
    }

    public void setChannelID(String channelID) {
        this.channelID = channelID;
    }

    public void setGuest(String guest) {
        this.guest = guest;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }
    //----------------------------------------------------------------------------------------------

    //--------------------------------------Getter--------------------------------------------------

    public Context getContext() {
        return context;
    }

    public String getApi() {
        return api;
    }

    public String getAppID() {
        return appID;
    }

    public String getOtherAppID() {
        return otherAppID;
    }

    public String getDeviceID() {
        return deviceID;
    }

    public String getChannelID() {
        return channelID;
    }

    public int getTick() {
        return tick;
    }

    public String getContents() {
        return contents;
    }
    public String getGuest() {
        return guest;
    }
    //----------------------------------------------------------------------------------------------
    //==============================================================================================
}
