package com.ph.bittelasia.meshtv.tv.santagrand.Core.Control;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.ph.bittelasia.meshtv.tv.mtvlib.Core.Control.Preference.Control.Manager.MeshTVPreferenceManager;
import com.ph.bittelasia.meshtv.tv.santagrand.Core.Model.AppOtherList;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Iterator;

import javax.net.ssl.HttpsURLConnection;

@SuppressLint("StaticFieldLeak")
public class PostAPIContentTask  extends AsyncTask<Void, Void, String> {

    //=======================================Variable===============================================
    //---------------------------------------Constant-----------------------------------------------
    private final String TAG = PostAPIContentTask.class.getSimpleName();
    //----------------------------------------------------------------------------------------------
    private Context        context;
    private String         api;
    private JSONArray      jsonArrayPost;
    private OnPostResult   onPostResult;
    //----------------------------------------------------------------------------------------------
    //==============================================================================================

    //=====================================Constructor==============================================
    //----------------------------------------------------------------------------------------------
    public PostAPIContentTask(Context context){
        setContext(context);
    }
    //----------------------------------------------------------------------------------------------
    //==============================================================================================


    //=====================================AsyncTask================================================
    //----------------------------------------------------------------------------------------------
    @SuppressWarnings("LoopStatementThatDoesntLoop")
    @Override
    protected String doInBackground(Void... voids) {
        String result ="";
        try{
            if(api!=null && getJsonArrayPost()!=null) {
                String source = MeshTVPreferenceManager.getHTTPHost(getContext()) + ":" + MeshTVPreferenceManager.getHTTPPort(getContext()) + "/index.php/apicall/" + getApi();

                URL url = new URL(source);

                JSONObject postDataParams = new JSONObject();
                JSONObject object = new JSONObject();

                object.put("data", getJsonArrayPost());
                postDataParams.put("data",object);

                Log.i(TAG, postDataParams.toString());

                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(15000 /* milliseconds */);
                conn.setConnectTimeout(15000 /* milliseconds */);
                conn.setRequestMethod("POST");
                conn.setDoInput(true);
                conn.setDoOutput(true);

                OutputStream os = conn.getOutputStream();
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
                writer.write(getPostDataString(postDataParams));

                Log.i(TAG, getPostDataString(postDataParams));

                writer.flush();
                writer.close();
                os.close();

                int responseCode = conn.getResponseCode();
                if (responseCode == HttpsURLConnection.HTTP_OK) {

                    BufferedReader in = new BufferedReader(new
                            InputStreamReader(
                            conn.getInputStream()));

                    StringBuilder sb = new StringBuilder();
                    String line;

                    while ((line = in.readLine()) != null) {
                        sb.append(line);
                        break;
                    }
                    in.close();
                    return sb.toString();

                } else {
                    return "false : " + responseCode;
                }
            }
        }
            catch(Exception e){
            return "Exception: " + e.getMessage();
        }
        return result;
    }


    @Override
    protected void onPostExecute(String result) {
        try {
            if (getOnPostResult() != null) {
                getOnPostResult().onResult(result);
            }
        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    //----------------------------------------------------------------------------------------------
    //==============================================================================================

    //=======================================Method=================================================
    //---------------------------------------Setter-------------------------------------------------
    public void setApi(String api) {
        this.api = api;
    }

    public void setOnPostResult(OnPostResult onPostResult) {
        this.onPostResult = onPostResult;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public void setJsonArrayPost(JSONArray jsonArrayPost) {
        this.jsonArrayPost = jsonArrayPost;
    }

    //----------------------------------------------------------------------------------------------
    //--------------------------------------Getter--------------------------------------------------
    private String getApi() {
        return api;
    }

    public OnPostResult getOnPostResult() {
        return onPostResult;
    }

    private Context getContext() {
        return context;
    }

    private  JSONArray getJsonArrayPost() {
        return jsonArrayPost;
    }
    //----------------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------------
    private String getPostDataString(JSONObject params) throws Exception {
        StringBuilder result = new StringBuilder();
        try {
            boolean first = true;

            Iterator<String> itr = params.keys();

            while (itr.hasNext()) {

                String key = itr.next();
                Object value = params.get(key);

                if (first)
                    first = false;
                else
                    result.append("&");

                result.append(URLEncoder.encode(key, "UTF-8"));
                result.append("=");
                result.append(URLEncoder.encode(value.toString(), "UTF-8"));

            }
        }catch (Exception e)
        {
            e.printStackTrace();
        }
        return result.toString();
    }
    public void setObjects(ArrayList<JSONObject> objects)
    {
        JSONArray dataArray = new JSONArray();
        try
        {
            for(JSONObject o: objects)
            {
                dataArray.put(o);
            }
            setJsonArrayPost(dataArray);
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public static JSONObject otherAppObject(AppOtherList appOtherList, boolean enableAnalytics)
    {
        JSONObject object = new JSONObject();
        try
        {
            object.put("app_id", appOtherList.getAppID()+"");
            object.put("app_name", appOtherList.getName());
            object.put("method", appOtherList.getMethod());
            object.put("enabled_app",enableAnalytics?"yes":"no");
        }catch (Exception e){
            e.printStackTrace();
        }
        return object;
    }
    //----------------------------------------------------------------------------------------------
    //==============================================================================================

    //======================================Interface===============================================
    //----------------------------------------------------------------------------------------------
    public interface OnPostResult
    {
        void onResult(String s);
    }
    //----------------------------------------------------------------------------------------------
    //==============================================================================================
}
