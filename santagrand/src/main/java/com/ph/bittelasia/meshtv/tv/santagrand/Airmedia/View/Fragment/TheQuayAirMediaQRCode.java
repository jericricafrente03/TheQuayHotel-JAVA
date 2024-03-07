package com.ph.bittelasia.meshtv.tv.santagrand.Airmedia.View.Fragment;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ph.bittelasia.meshtv.tv.santagrand.R;

public class TheQuayAirMediaQRCode extends MeshTVDialogFragment {

    ImageView iv_qr;
    TextView tv_text;

    @Override
    public void setIDs(View view)
    {
        try {
            iv_qr = view.findViewById(R.id.iv_qr);
            tv_text = view.findViewById(R.id.tv_text);
        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void setContent()
    {
        try
        {
            iv_qr.setImageDrawable(getContext().getResources().getDrawable(R.drawable.qr_airmedia));
            tv_text.setText(getContext().getResources().getString(R.string.qr));
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }


    @Override
    public int setLayout()
    {
        return R.layout.qr_layout;
    }

    @Override
    public int setConstraintLayout()
    {
        return R.id.cl_layout;
    }

    @Override
    public double setPercentageWidth() {
        return 0.20;
    }

    @Override
    public double setPercentageHeight() {
        return 0.45;
    }
}

