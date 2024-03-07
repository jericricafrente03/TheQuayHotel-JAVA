package com.ph.bittelasia.meshtv.tv.santagrand.Airmedia.Model;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.provider.Settings;
import android.util.Log;

import java.lang.reflect.Method;

/**
 * Created by Steward Marzon Apostol
 */
public class HotspotManager {

    //======================================Variable================================================
    //--------------------------------------Constant------------------------------------------------
    public static final String TAG = HotspotManager.class.getSimpleName();
    //----------------------------------------------------------------------------------------------
    //--------------------------------------Instance------------------------------------------------
    private final WifiManager mWifiManager;
    private Context           context;
    //----------------------------------------------------------------------------------------------
    //==============================================================================================


    //=====================================Constructor==============================================
    //----------------------------------------------------------------------------------------------
    public HotspotManager(Context context) {
        this.context = context;
        mWifiManager = (WifiManager) this.context.getSystemService(Context.WIFI_SERVICE);
    }
    //----------------------------------------------------------------------------------------------
    //==============================================================================================




    //=====================================Method===================================================
    //----------------------------------------------------------------------------------------------

    /**
     * Method forcely write permission to the application
     * @param force is boolean
     */
    public void showWritePermissionSettings(boolean force) {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (force || !Settings.System.canWrite(this.context)) {
                    Intent intent = new Intent(Settings.ACTION_MANAGE_WRITE_SETTINGS);
                    intent.setData(Uri.parse("package:" + this.context.getPackageName()));
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    this.context.startActivity(intent);
                }
            }
        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    //----------------------------------------------------------------------------------------------

    /**
     * Method to check whether wifi hotspot on or off
     * @return boolean
     */
    public boolean isApOn() {
        try {
            Method method = mWifiManager.getClass().getDeclaredMethod("isWifiApEnabled");
            method.setAccessible(true);
            return (Boolean) method.invoke(mWifiManager);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    //----------------------------------------------------------------------------------------------
    /**
     * Method to turn wifiAp hotspot on
     * @return boolean
     */
    public boolean turnWifiApOn() {
        WifiConfiguration wificonfiguration = null;
        try {
            Method method = mWifiManager.getClass().getMethod("setWifiApEnabled", WifiConfiguration.class, boolean.class);
            method.invoke(mWifiManager, wificonfiguration, true);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    //----------------------------------------------------------------------------------------------
    /**
     * Method to turn wifiAp hotspot off
     * @return boolean
     */
    public boolean turnWifiApOff() {
        WifiConfiguration wificonfiguration = null;
        try {
            Method method = mWifiManager.getClass().getMethod("setWifiApEnabled", WifiConfiguration.class, boolean.class);
            method.invoke(mWifiManager, null, false);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    //----------------------------------------------------------------------------------------------

    /**
     * Method to create a network for hotspot
     * @param ssid is String, the SSID
     * @param password is String, the password
     * @return boolean
     */
    public boolean createNewNetwork(String ssid, String password) {
        try {
            mWifiManager.setWifiEnabled(false); // turn off Wifi
            if (isApOn()) {
                turnWifiApOff();
            } else {
                Log.e(TAG, "WifiAp is turned off");

            }
            WifiConfiguration myConfig = new WifiConfiguration();
            myConfig.SSID = ssid;
            myConfig.preSharedKey = password;
            myConfig.allowedKeyManagement.set(4);
            myConfig.allowedAuthAlgorithms.set(WifiConfiguration.AuthAlgorithm.OPEN);
            try {
                Method method = mWifiManager.getClass().getMethod("setWifiApEnabled", WifiConfiguration.class, boolean.class);
                return (Boolean) method.invoke(mWifiManager, myConfig, true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }catch (Exception e)
        {
            e.printStackTrace();
        }
        return false;
    }
    //----------------------------------------------------------------------------------------------
    //===============================================================================================

}