package com.ph.bittelasia.meshtv.tv.santagrand.Core.View.Fragment;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Environment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.ph.bittelasia.meshtv.tv.mtvlib.Core.Control.Annotation.View.General.Layout;
import com.ph.bittelasia.meshtv.tv.mtvlib.Core.Control.Data.Control.Listener.MeshAppListener;
import com.ph.bittelasia.meshtv.tv.mtvlib.Core.Object.Apps.MeshApp;
import com.ph.bittelasia.meshtv.tv.santagrand.R;
import com.ph.bittelasia.meshtv.tv.santagrand.Room.View.App.TheQuay;
import com.ph.bittelasia.packages.Core.Control.AppTextFileConnection;
import com.ph.bittelasia.packages.Core.Service.AppWifiIntentService;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;


/**
 * @author Steward M. Apostol
 * @version 1.0
 * @since January 2019
 */

public abstract class TheQuayHotelAppFragment extends Fragment {

    //==========================================Variable============================================
    //------------------------------------------Constant--------------------------------------------
    public final String TAG = TheQuayHotelAppFragment.class.getSimpleName();
    //------------------------------------------Instance--------------------------------------------
    private Layout                     layout;
    private MeshAppListener            meshAppListener;
    private String                     APPDESC_FILENAME  = "app_config.txt";
    //----------------------------------------------------------------------------------------------
    //==============================================================================================



    //==========================================Life Cycle==========================================
    //----------------------------------------------------------------------------------------------
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        layout  = getClass().getAnnotation(Layout.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(layout.value(),container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        draw(view);
    }
    //----------------------------------------------------------------------------------------------
    //==============================================================================================


    //=======================================Abstract Method========================================
    //----------------------------------------------------------------------------------------------
    public abstract void draw(View v);
    //----------------------------------------------------------------------------------------------
    //==============================================================================================



    //=======================================Method=================================================
    //----------------------------------------------------------------------------------------------

    /**
     * Method to set the Listener for {@link MeshApp MeshApp}
     * @param meshAppListener is {@link MeshAppListener MeshAppListener}
     */
    public void setMeshAppListener(MeshAppListener meshAppListener) {
        this.meshAppListener = meshAppListener;
    }
    //----------------------------------------------------------------------------------------------
    /**
     * Method to get a json object from the content of the textfile
     * @param directory is the parent directory of the file
     * @param filename is the filename to read
     * @return JSONObject
     */
    @SuppressWarnings("UnusedAssignment")
    public final JSONObject getData(String directory, String filename)
    {
        FileInputStream fis = null;
        try {

            File root= new File(directory,filename);
            fis = new FileInputStream(root);
        }catch (Exception e)
        {
          e.printStackTrace();
        }
        return readFile(getContext(),fis,true);
    }
    //----------------------------------------------------------------------------------------------

    /**
     * Method to read a content
     * @param context is Context
     * @param fis is FileInputStream
     * @param isJsonObject is boolean
     * @param <F> is generic type of what object result you want
     * @return F
     */
    @SuppressWarnings({"SameParameterValue","UnusedReturnValue", "ConstantConditions", "unchecked"})
    private static <F> F readFile(Context context, FileInputStream fis, boolean isJsonObject)
    {
        JSONObject jsonObject =null;
        StringBuilder stringBuilder=null;

        try {
            if(fis==null)
                return null;
            else
            {
                stringBuilder = new StringBuilder();
                int content;
                String str;
                while ((content = fis.read()) != -1) {
                    str = ((char) content) + "";
                    stringBuilder.append(str);
                }
                fis.close();
                if(isJsonObject)
                    jsonObject = new JSONObject(stringBuilder.toString());
            }
        } catch (Exception ignored) {

        }
        return  isJsonObject?(F)jsonObject: (F)stringBuilder.toString();
    }
    //----------------------------------------------------------------------------------------------
    /**
     * Method to get the List of App from a texfile
     */
    public final void getAppList()
    {
        try {
            ArrayList<MeshApp> meshApps=new ArrayList<>();
            String appLists = "";
            File root = createDirectory();
            @SuppressLint("SdCardPath")
            File apps =new File("/sdcard/Android/"+APPDESC_FILENAME);
            Log.e(TAG, "getAppList: exist -> "+apps.exists() );
            if(!apps.exists())
            {
              appLists =  AppTextFileConnection.readFromRaw(TheQuay.app, R.raw.get_all_app_desc);
              AppTextFileConnection.writeFile(TheQuay.app, root,appLists,APPDESC_FILENAME);
                Log.i(TAG,"(MVRS-APPS) - app exist\n");
            }
            else
            {
                Log.i(TAG,"(MVRS-APPS) - app does not exist\n");
            }

            @SuppressLint("SdCardPath") JSONArray array = new JSONArray(getData("/sdcard/Android/",APPDESC_FILENAME).getString("apps"));
            for (int ctr = 0; ctr < array.length(); ++ctr)
            {
                MeshApp app = new MeshApp(array.getString(ctr));
                if (app.getEnabled() == 1)
                {
                    meshApps.add(app);
                }
            }
            if(this.meshAppListener!=null)
            {
                if (meshApps.size() > 0)
                {
                    this.meshAppListener.onAppsLoaded(meshApps);
                }
                else {
                    this.meshAppListener.onAppsNotFound();
                }
            }else
            {
                Log.e(TAG,"@getAppList -> "+MeshAppListener.class.getSimpleName()+" has not yet set. Please implement "+MeshAppListener.class.getSimpleName()+" and add setMeshAppListener(this)");
            }

        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    //----------------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------------
    @SuppressWarnings("ResultOfMethodCallIgnored")
    public File createDirectory()
    {
        File file = new File(Environment.getExternalStorageDirectory() + "/Android/");
        try
        {
            if (!file.exists()) {
                file.mkdirs();
            }
        }catch (Exception e)
        {
            e.printStackTrace();
        }
        return file;
    }
    //==============================================================================================
}
