package com.ph.bittelasia.meshtv.tv.santagrand.Room.View.Fragment.Core;

import android.app.Activity;
import android.content.Context;

import android.view.View;


import androidx.viewpager.widget.ViewPager;

import com.ph.bittelasia.meshtv.tv.mtvlib.Core.Control.Annotation.View.General.Layout;
import com.ph.bittelasia.meshtv.tv.santagrand.Room.Library.MeshTVRFragment;
import com.ph.bittelasia.meshtv.tv.santagrand.Room.Model.TheQuayViewPagerAdapter;

import java.util.ArrayList;

public abstract class TheQuayViewPagerRFragment<T> extends MeshTVRFragment<T>
{
    //=========================================Variable=============================================
    //-----------------------------------------Constant---------------------------------------------
    public static final String TAG = TheQuayViewPagerRFragment.class.getSimpleName();
    //----------------------------------------------------------------------------------------------
    //------------------------------------------View------------------------------------------------
    public ViewPager vp_pages;
    //----------------------------------------------------------------------------------------------
    //-----------------------------------------Instance---------------------------------------------
    PagerCallBack cb;
    TheQuayViewPagerAdapter adapter;
    PagerAnnotation pagerAnnotation;
    //----------------------------------------------------------------------------------------------
    //==============================================================================================
    //==============================================LifeCycle=======================================
    @Override
    public void onAttach(Context context)
    {
        try {
            super.onAttach(context);
            if (getActivity() instanceof PagerCallBack) {
                cb = (PagerCallBack) getActivity();
            }
            pagerAnnotation = getClass().getAnnotation(PagerAnnotation.class);
        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    @Override
    public void onAttach(Activity activity)
    {
        try {
            super.onAttach(activity);
            if (getActivity() instanceof PagerCallBack) {
                cb = (PagerCallBack) getActivity();
            }
        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void draw(View view)
    {
        try {
            if (pagerAnnotation != null) {
                vp_pages = (ViewPager) view.findViewById(pagerAnnotation.vpLayout());
            }
        }catch (Exception e)
        {
            e.printStackTrace();
        }

    }
    @Override
    public void onDetach()
    {
        super.onDetach();
        cb = null;
    }
    //==============================================================================================
    //==========================================Method==============================================
    //------------------------------------------Getter----------------------------------------------
    public ViewPager getVp_pages() { return vp_pages; }
    public PagerCallBack getCb() { return cb; }
    public TheQuayViewPagerAdapter getAdapter() {
        return adapter;
    }
    public int getCurrentPage()
    {
        return vp_pages.getCurrentItem();
    }
    //----------------------------------------------------------------------------------------------
    //------------------------------------------Setter----------------------------------------------
    public void setItems(ArrayList<T> items)
    {
        try {
            vp_pages.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                }

                @Override
                public void onPageSelected(int position) {
                    if (cb != null) {
                        cb.selectedFromPager(adapter.getItems().get(position), position);
                        setCurrent(position);
                    }
                }

                @Override
                public void onPageScrollStateChanged(int state) {

                }

            });
            adapter = setAdapter(items);
            if (adapter != null) {
                vp_pages.setAdapter(adapter);
            }
        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    //----------------------------------------------------------------------------------------------
    //-----------------------------------------------CRUD-------------------------------------------
    public void addObject(final T object)
    {
        try {
            if (getActivity() != null) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        ArrayList<T> temp = new ArrayList<>();
                        if (adapter != null) {
                            temp.addAll(adapter.getItems());
                        }
                        temp.add(object);
                        adapter = setAdapter(temp);
                        if (adapter != null) {
                            vp_pages.setAdapter(adapter);
                            pagesPopulated();
                        }

                        if (cb != null) {
                            cb.insertToPager(object);
                        } else {
                        }
                        vp_pages.requestFocus();
                    }
                });
            }
        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public void setObjects(final ArrayList<T> objects)
    {
        try {
            if (getActivity() != null) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        adapter = setAdapter(objects);
                        if (adapter != null) {
                            vp_pages.setAdapter(adapter);
                            pagesPopulated();
                        }

                    }
                });
            }
        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    public void clearObjects()
    {
        try {
            if (getActivity() != null) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        adapter = setAdapter(new ArrayList<T>());
                        if (adapter != null) {
                            vp_pages.setAdapter(adapter);
                        }
                        if (cb != null) {
                            cb.clear();
                        }
                    }
                });
            }
        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    public void updateObject(final T object)
    {
        try {
            if (getActivity() != null) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ArrayList<T> temp = new ArrayList<>();
                        temp.addAll(adapter.getItems());
                        temp.add(object);
                        int position = 0;
                        boolean isFound = false;
                        for (T o : temp) {
                            if (isSame(object, o)) {
                                isFound = true;
                                break;
                            }
                            position++;
                        }
                        if (isFound) {

                            temp.set(position, object);
                            adapter = setAdapter(temp);
                            if (adapter != null) {
                                vp_pages.setAdapter(adapter);
                                pagesPopulated();
                            }

                        }
                        adapter.notifyDataSetChanged();
                        if (cb != null) {
                            cb.updateFromPager(object, position);
                        }

                    }
                });
            }
        }catch (Exception e)
        {
            e.printStackTrace();
        }

    }
    public void deleteObject(final T object)
    {
        try {
            if (getActivity() != null) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ArrayList<T> temp = new ArrayList<>();
                        vp_pages.setAdapter(setAdapter(temp));
                        temp.addAll(adapter.getItems());
                        temp.add(object);
                        int position = 0;
                        boolean isFound = false;
                        for (T o : temp) {
                            if (isSame(object, o)) {
                                isFound = true;
                                break;
                            }
                            position++;
                        }
                        if (isFound) {
                            temp.remove(object);
                            adapter = setAdapter(temp);
                            if (adapter != null) {
                                vp_pages.setAdapter(adapter);
                            }
                        }
                        if (cb != null) {
                            cb.deleteFromPager(object, position);
                        }

                    }
                });
            }

        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    public void clear()
    {
        try {
            if (getActivity() != null) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        adapter = setAdapter(new ArrayList<T>());
                        if (adapter != null) {
                            vp_pages.setAdapter(adapter);
                            clearObjects();
                        }

                    }
                });
            }

        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    public void pagesPopulated()
    {
        try {
            if (getActivity() != null) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            onPagesPopulated();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    //----------------------------------------------------------------------------------------------

    //==============================================================================================
    //=======================================PagerCallBack==========================================
    public interface PagerCallBack
    {
        void insertToPager(Object o);
        void updateFromPager(Object o, int index);
        void deleteFromPager(Object o, int index);
        void selectedFromPager(Object o, int index);
        void clear();
    }
    //==============================================================================================
    //==========================================Abstract============================================
    public abstract void setCurrent(int current);
    public abstract void onPagesPopulated();
    public abstract boolean isSame(T o1, T o2);
    public abstract TheQuayViewPagerAdapter setAdapter(ArrayList<T> items);
    //==============================================================================================
}
