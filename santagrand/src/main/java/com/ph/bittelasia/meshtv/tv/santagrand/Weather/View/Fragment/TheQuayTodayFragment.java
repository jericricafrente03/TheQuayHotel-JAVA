package com.ph.bittelasia.meshtv.tv.santagrand.Weather.View.Fragment;

import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ph.bittelasia.meshtv.tv.mtvlib.Core.Control.Annotation.Manager.MeshAnnotationManager;
import com.ph.bittelasia.meshtv.tv.mtvlib.Core.Control.Annotation.View.General.Layout;
import com.ph.bittelasia.meshtv.tv.mtvlib.Core.Control.Annotation.View.Widget.BindWidget;
import com.ph.bittelasia.meshtv.tv.mtvlib.Core.Control.Util.ResourceManager.MeshResourceManager;
import com.ph.bittelasia.meshtv.tv.mtvlib.Entity.Model.Weather.MeshWeatherForecastDay;
import com.ph.bittelasia.meshtv.tv.santagrand.Room.Library.MeshTVRFragment;
import com.ph.bittelasia.meshtv.tv.santagrand.R;
import java.text.SimpleDateFormat;

@Layout(R.layout.sg_today)
public class TheQuayTodayFragment extends MeshTVRFragment<MeshWeatherForecastDay>
{

    //============================================Variable==========================================
    //--------------------------------------------Constant------------------------------------------
    public static final String TAG = TheQuayTodayFragment.class.getSimpleName();
    public static final String UNIT_CELCIUS = " °C";
    public static final String UNIT_FAREN = " °F";
    //----------------------------------------------------------------------------------------------
    //----------------------------------------------View--------------------------------------------
    @BindWidget(R.id.iv_icon)
    public ImageView iv_icon;
    @BindWidget(R.id.tv_temp)
    public TextView tv_temp;
    @BindWidget(R.id.tv_city)
    public TextView tv_city;
    @BindWidget(R.id.tv_date)
    public TextView tv_date;
    @BindWidget(R.id.tv_desc)
    public TextView tv_desc;
    //----------------------------------------------------------------------------------------------
    //--------------------------------------------Instance------------------------------------------
    String city = "Singapore";
    boolean isCelcius = true;
    //----------------------------------------------------------------------------------------------
    //==============================================================================================
    //=============================================Method===========================================
    //---------------------------------------------Action-------------------------------------------
    private void display()
    {
        try {
            Log.i(TAG, "(MVRS-0227) display() =========================================================\n");
            if (getItem() != null && tv_temp != null && tv_city != null && tv_desc != null && iv_icon != null) {
                MeshResourceManager.displayLiveImageFor(getActivity(), iv_icon, getItem().getIcon());
                tv_temp.setText(isCelcius ? String.format("%.1f", getItem().getTemp()) + UNIT_CELCIUS : (String.format("%.1f", ((getItem().getTemp() * 9) / 5) + 32)) + UNIT_FAREN);
                tv_city.setText(city);
                tv_desc.setText(getItem().getDescription());
                try {
                    tv_date.setText(new SimpleDateFormat("dd MMM yyyy").format(getItem().getDate()));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                Log.i(TAG, "(MVRS-0227) display() Displayed\n");
            } else {
                Log.i(TAG, "(MVRS-0227) display() SOMETHING IS MISSING\n");
                Log.i(TAG, "(MVRS-0227) display() getItem:" + (getItem() == null ? "NULL" : "FOUND") + "\n");
                Log.i(TAG, "(MVRS-0227) display() tv_temp:" + (tv_temp == null ? "NULL" : "FOUND") + "\n");
                Log.i(TAG, "(MVRS-0227) display() tv_city:" + (tv_city == null ? "NULL" : "FOUND") + "\n");
                Log.i(TAG, "(MVRS-0227) display() tv_desc:" + (tv_desc == null ? "NULL" : "FOUND") + "\n");
                Log.i(TAG, "(MVRS-0227) display() iv_icon:" + (iv_icon == null ? "NULL" : "FOUND") + "\n");

            }
            Log.i(TAG, "(MVRS-0227) display() =========================================================\n");
        }catch (Exception e)
        {
            e.printStackTrace();
        }

    }
    //----------------------------------------------------------------------------------------------
    //---------------------------------------------Getter-------------------------------------------
    public ImageView getIv_icon() {
        return iv_icon;
    }
    public TextView getTv_temp() {
        return tv_temp;
    }
    public TextView getTv_city() {
        return tv_city;
    }
    public TextView getTv_date() {
        return tv_date;
    }
    public TextView getTv_desc() {
        return tv_desc;
    }
    //----------------------------------------------------------------------------------------------
    //--------------------------------------------Setter--------------------------------------------
    public void setIv_icon(ImageView iv_icon) {
        this.iv_icon = iv_icon;
    }
    public void setTv_temp(TextView tv_temp) {
        this.tv_temp = tv_temp;
    }
    public void setTv_city(TextView tv_city) {
        this.tv_city = tv_city;
    }
    public void setTv_date(TextView tv_date) {
        this.tv_date = tv_date;
    }
    public void setTv_desc(TextView tv_desc) {
        this.tv_desc = tv_desc;
    }
    public void setCity(String city) {
        this.city = city;
        display();
    }
    //----------------------------------------------------------------------------------------------
    public void setCelcius(boolean celsius)
    {
        isCelcius =celsius;
        display();
    }
    //==============================================================================================
    //========================================TheQuayTodayFragment==================================
    @Override
    public void draw(View v)
    {
        try {
            MeshAnnotationManager.bindWidgets(this);
            display();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    @Override
    public void updateDisplay(MeshWeatherForecastDay item)
    {
        try {
            display();
        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    //==============================================================================================
}
