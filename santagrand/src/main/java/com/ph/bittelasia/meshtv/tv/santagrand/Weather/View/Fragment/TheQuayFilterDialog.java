package com.ph.bittelasia.meshtv.tv.santagrand.Weather.View.Fragment;

import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.DialogFragment;

import static android.content.Context.INPUT_METHOD_SERVICE;

public abstract class TheQuayFilterDialog extends DialogFragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(setLayout(),container,false);
        setIDs(view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        try {
            DisplayMetrics displayMetrics = new DisplayMetrics();
            getActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
            int height = displayMetrics.heightPixels;
            int width = displayMetrics.widthPixels;

            double minWidth = (width * (setPercentageWidth() > 0 ? setPercentageWidth() : 1));
            double minHeight = (height * (setPercentageHeight() > 0 ? setPercentageHeight() : 1));

            ConstraintLayout cl_layout = (ConstraintLayout) view.findViewById(setConstraintLayout());
            cl_layout.setMinWidth((int) minWidth);
            cl_layout.setMinHeight((int) minHeight);
            setContent();
        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog  dialog = new Dialog(this.getActivity());
        try {
            dialog.requestWindowFeature(1);
            dialog.getWindow().setFlags(1024, 1024);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
            dialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
            dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
            dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING);
            dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        }catch (Exception e)
        {
            e.printStackTrace();
        }
        return dialog;
    }

    public abstract void setIDs(View view);
    public abstract void setContent();

    public abstract int setLayout();

    public abstract int setConstraintLayout();

    public abstract double setPercentageWidth();

    public abstract double setPercentageHeight();


    private View.OnTouchListener touchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                return true;
            } else if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                dismiss();
            }
            return false;
        }
    };


    public void hideSoftKeyboard() {
        try {
            if (getActivity().getCurrentFocus() != null) {
                InputMethodManager inputMethodManager = (InputMethodManager) getActivity().getSystemService(INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), 0);
            }
        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public void showSoftKeyboard(View view) {
        try {
            InputMethodManager inputMethodManager = (InputMethodManager) getActivity().getSystemService(INPUT_METHOD_SERVICE);
            view.requestFocus();
            inputMethodManager.showSoftInput(view, 0);
        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public boolean isValid(EditText...t)
    {
        boolean valid = false;
        try {
            for (EditText text : t) {
                if (text.getText().toString().isEmpty())
                    valid = false;
                else
                    valid = true;
            }
        }catch (Exception e)
        {
            e.printStackTrace();
        }
        return  valid;
    }

    public String getText(EditText t)
    {
        return t.getText().toString();
    }
}
