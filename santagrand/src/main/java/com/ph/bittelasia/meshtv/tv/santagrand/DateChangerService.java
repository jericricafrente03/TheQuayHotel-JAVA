package com.ph.bittelasia.meshtv.tv.santagrand;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

import androidx.annotation.Nullable;

import com.ph.bittelasia.meshtv.tv.mtvlib.Core.Control.Preference.Control.Manager.MeshTVPreferenceManager;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class DateChangerService extends IntentService {


    public DateChangerService() {
        super("DateChangerService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        URL url;
        HttpURLConnection urlConnection = null;
        StringBuilder sb = null;
        try {
            String source = MeshTVPreferenceManager.getHTTPHost(getApplicationContext()) + ":" + MeshTVPreferenceManager.getHTTPPort(getApplicationContext()) + "/index.php/apicall/stb_time";
            url = new URL(source.replaceAll("\\n", ""));

            HttpURLConnection conn = (HttpURLConnection)url.openConnection();
            conn.setReadTimeout(MeshTVPreferenceManager.getTimeOut(getApplicationContext()));
            conn.setConnectTimeout(MeshTVPreferenceManager.getConnectionTimeout(getApplicationContext()));
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
                JSONObject jObject = new JSONObject(sb.toString());

                String time = jObject.getString("time");
                final Intent i= new Intent();
                i.putExtra("date", time);

                i.setAction("android.intent.action.DATE");
                getApplicationContext().sendBroadcast(i);
                Log.i("DateChangerService","sb "+time);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
