package com.ph.bittelasia.meshtv.tv.santagrand.Room.View.Fragment.Core;

import android.content.Context;
import android.graphics.Color;

import android.util.Log;
import android.view.View;


import com.google.android.material.tabs.TabLayout;
import com.ph.bittelasia.meshtv.tv.santagrand.Room.Library.MeshTVRFragment;

import java.util.ArrayList;

public abstract class TheQuayTabFragment<T> extends MeshTVRFragment<T>
{
    //=========================================Variable=============================================
    //-----------------------------------------Constant---------------------------------------------
    public static final String TAG = TheQuayTabFragment.class.getSimpleName();
    //----------------------------------------------------------------------------------------------
    //-------------------------------------------View-----------------------------------------------
    private TabLayout tb_items;
    //----------------------------------------------------------------------------------------------
    //-----------------------------------------Instance---------------------------------------------
    private ArrayList<T> selection;
    private int selected;
    public  TabCallBack tb = null;
    public  boolean isClicked=false;
    private TabLayoutAnnotation tabLayout;
    //----------------------------------------------------------------------------------------------
    //==============================================================================================
    //=========================================LifeCycle============================================

    @Override
    public void onAttach(Context context)
    {
        try {
            super.onAttach(context);
            if (getActivity() instanceof TabCallBack) {
                tb = (TabCallBack) getActivity();
            }
            selection = new ArrayList<>();
            tabLayout = getClass().getAnnotation(TabLayoutAnnotation.class);
        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void onDetach()
    {
        super.onDetach();
        tb = null;
    }

    //==============================================================================================
    //==========================================Method==============================================
    //------------------------------------------Action----------------------------------------------
    public void selectCurrent()
    {
        try {
            if (tb_items != null) {
                tb_items.getTabAt(selected).select();
            }
        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    //----------------------------------------------------------------------------------------------
    //------------------------------------------Getter----------------------------------------------
    public ArrayList<T> getSelection() {
        return selection;
    }
    public int getSelected() {
        return selected;
    }
    public TabLayoutAnnotation getTabLayout() {
        return tabLayout;
    }
    public TabLayout getTabs()
    {
        return tb_items;
    }
    //----------------------------------------------------------------------------------------------
    //------------------------------------------Setter----------------------------------------------
    public void setSelected(int selected) {
        this.selected = selected;
    }
    public void setObjects(final ArrayList<T> objectList)
    {
        try {
            Log.i(TAG, "(MVRS) - setObjects() =================================================\n");
            if (getActivity() != null) {
                getActivity().runOnUiThread
                        (
                                new Runnable() {
                                    @Override
                                    public void run() {
                                        Log.i(TAG, "(MVRS) - runOnUiThread() =================================================\n");
                                        Log.i(TAG, "(MVRS) - runOnUiThread() =================================================\n");
                                        Log.i("steward", "(MVRS) - runOnUiThread() =================================================\n");
                                        try {
                                            tb_items.removeAllTabs();
                                            selection = new ArrayList<>();
                                            selection.addAll(objectList);
                                            if (selection.size() < 1) {
                                                update();
                                            } else {
                                                tb_items.removeAllTabs();
                                                for (T o : selection) {
                                                    tb_items.addTab(tb_items.newTab(), false);
                                                }
                                                ArrayList<View> views = new ArrayList<>();
                                                int ctr = 0;
                                                for (final T o : selection) {
                                                    View temp = null;
                                                    int position = tb_items.getSelectedTabPosition();
                                                    Log.i(TAG, "(MeshTV-MVRS-0219) - COMPARING:" + position + "vs" + ctr + "\n");
                                                    Log.i(TAG, "(MeshTV-MVRS-0219) - SELECTED\n");
                                                    temp = getActivity().getLayoutInflater().inflate(tabLayout.itemLayout(), tb_items, false);
                                                    final View v2 = temp;
                                                    TabLayout.Tab tab = tb_items.getTabAt(ctr);
                                                    v2.setClickable(true);
                                                    draw(v2, selection.get(ctr), false, ctr);
                                                    tab.setCustomView(v2);
                                                    Log.i(TAG, "(MeshTV-MVRS-0219) - COMPARE DONE:" + position + "vs" + ctr + "\n");
                                                    ctr++;
                                                    views.add(v2);
                                                }
                                                tb_items.addFocusables(views, View.FOCUS_RIGHT);
                                                tb_items.addChildrenForAccessibility(views);
                                                try {
                                                    selectCurrent();
                                                    tb_items.getTabAt(selected).select();
                                                    onTabsDrawn();
                                                    tb.onTabsDrawn(false);
                                                } catch (Exception e) {
                                                    e.printStackTrace();
                                                }
                                            }

                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }
                        );
            }
            Log.i(TAG, "(MVRS) - setObjects() =================================================\n");
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public void clearObjects()
    {
        Log.i(TAG,"(MVRS) - clearObjects() =================================================\n");
        if(getActivity()!=null)
        {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    selection.clear();
                    tb_items.removeAllTabs();
                }
            });
        }
        Log.i(TAG,"(MVRS) - clearObjects() =================================================\n");
    }
    public void updateObject(T object)
    {}
    public void deleteObject(final int position)
    {
        if(getActivity()!=null)
        {
            getActivity().runOnUiThread
                    (
                            new Runnable()
                            {
                                @Override
                                public void run()
                                {
                                    selection.remove(position);
                                    tb_items.removeTabAt(position);
                                    update();
                                }
                            });
        }
    }
    public void clear()
    {
        Log.i(TAG,"(MVRS) - clear() =================================================\n");
        if(getActivity()!=null)
        {
            getActivity().runOnUiThread
                    (
                            new Runnable()
                            {
                                @Override
                                public void run()
                                {
                                    Log.i(TAG,"(MVRS) - runOnUiThread() =================================================\n");

                                    selection.clear();
                                    tb_items.removeAllTabs();

                                    Log.i(TAG,"(MVRS) - runOnUiThread() =================================================\n");
                                }
                            }
                    );
        }
        Log.i(TAG,"(MVRS) - clear() =================================================\n");
    }
    public void update()
    {
        Log.i(TAG,"(MVRS) - update() =================================================\n");
        if(getActivity()!=null)
        {
            getActivity().runOnUiThread
                    (
                            new Runnable()
                            {
                                @Override
                                public void run()
                                {
                                    try
                                    {
                                        Log.i(TAG,"(MVRS) - runOnUiThread() =================================================\n");
                                        Log.i(TAG,"(MVRS) - runOnUiThread() =================================================\n");
                                    }
                                    catch (Exception e)
                                    {
                                        e.printStackTrace();
                                    }
                                }
                            }
                    );
        }
        Log.i(TAG,"(MVRS) - update() =================================================\n");
    }
    //----------------------------------------------------------------------------------------------
    //-----------------------------------------Navigation-------------------------------------------
    public void moveLeft()
    {
        Log.i(TAG,"(MVRS) - moveLeft() =================================================\n");
        if(tb_items!=null)
        {
            if(tb_items.getTabCount()>0)
            {
                int current = tb_items.getSelectedTabPosition();
                current--;
                if(current<0)
                {
                    current = tb_items.getTabCount()-1;
                }
                selected = current;
                tb_items.getTabAt(selected).select();
                select();
            }
            else
            {
                Log.i(TAG,"(MVRS-0225) - ok() No Tabs\n");

            }

        }

        Log.i(TAG,"(MVRS) - moveLeft() =================================================\n");
    }
    public void moveRight()
    {
        Log.i(TAG,"(MVRS) - moveRight() =================================================\n");
        if(tb_items!=null)
        {
            if(tb_items.getTabCount()>0)
            {
                int current = tb_items.getSelectedTabPosition();
                current++;
                if(current>tb_items.getTabCount()-1)
                {
                    current =0;
                }

                selected = current;
                tb_items.getTabAt(selected).select();
                select();
            }
            else
            {
                Log.i(TAG,"(MVRS-0225) - ok() No Tabs\n");

            }

        }
        Log.i(TAG,"(MVRS) - moveRight() =================================================\n");
    }
    public void select()
    {
        if(tb_items.getTabCount()>0)
        {
            try
            {

                launch(selection.get(tb_items.getSelectedTabPosition()));
                tb.selectedFromTab(selection.get(tb_items.getSelectedTabPosition()),tb_items.getSelectedTabPosition());
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
        else
        {
            Log.i(TAG,"(MVRS-0225) - ok() No Tabs\n");

        }
        Log.i(TAG,"(MVRS) - select() =================================================\n");

        Log.i(TAG,"(MVRS) - select() =================================================\n");
    }
    public void ok()
    {

        Log.i(TAG,"(MVRS) - ok() =================================================\n");
        if(tb_items.getTabCount()>0)
        {

            try
            {
                launch(selection.get(tb_items.getSelectedTabPosition()));
                if(tb!=null && isClicked)
                {
                    tb.clickedFromTab(selection.get(tb_items.getSelectedTabPosition()),tb_items.getSelectedTabPosition());
                    isClicked=false;
                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
        else
        {
            Log.i(TAG,"(MVRS-0225) - ok() No Tabs\n");

        }
        Log.i(TAG,"(MVRS) - ok() =================================================\n");
    }
    //----------------------------------------------------------------------------------------------
    //-------------------------------------------Action---------------------------------------------
    public void focusOnTab()
    {
        if(tb_items!=null)
        {
            tb_items.requestFocus();
        }
    }
    //----------------------------------------------------------------------------------------------
    //-------------------------------------------CRUD-----------------------------------------------
    public void addObject(final T object)
    {
        if(getActivity()!=null)
        {
            getActivity().runOnUiThread(new Runnable()
            {
                @Override
                public void run()
                {
                    selection.add(object);
                    ArrayList<T> temp = new ArrayList<>();
                    temp.addAll(selection);
                }
            });
        }
    }
    //----------------------------------------------------------------------------------------------
    //==============================================================================================
    //======================================MeshTVRFragment=========================================
    @Override
    public void draw(View v)
    {
        if(tabLayout!=null)
        {
            tb_items = (TabLayout) v.findViewById(tabLayout.tabLayout());
            if(tb_items!=null)
            {
                tb_items.setSelectedTabIndicatorColor(Color.argb(0,0,0,0));
                tb_items.setTabGravity(TabLayout.GRAVITY_FILL);
                tb_items.addOnTabSelectedListener(listener);

            }

        }
    }
    @Override
    public void updateDisplay(T item)
    {

    }
    //==============================================================================================
    //===========================================Listener===========================================
    TabLayout.OnTabSelectedListener listener = new TabLayout.OnTabSelectedListener()
    {
        @Override
        public void onTabSelected(TabLayout.Tab tab)
        {
            if(tb_items!=null)
            {
                try
                {

                    Log.i(TAG,"(MeshTV-MVRS 0214) tabSelected - =================================================\n");
                    View v = tb_items.getTabAt(tab.getPosition()).getCustomView();

                    draw(v,selection.get(tab.getPosition()),true,tab.getPosition());

                    if(tb!=null)
                    {
                        tb.selectedFromTab(selection.get(tab.getPosition()),tab.getPosition());
                        setSelected(tab.getPosition());
                        View temp = getActivity().getLayoutInflater().inflate(tabLayout.itemSelectedLayout(),tb_items,false);
                        draw(temp,selection.get(tab.getPosition()),true,tab.getPosition());
                        tab.setCustomView(temp);
                        if(selection.size()>1)
                        {
                            int cur = tab.getPosition();
                            int prev = -1;
                            int next = -1;
                            selected = cur;
                            prev = cur>0?cur-1:selection.size()-1;
                            next = cur<selection.size()-1?cur+1:0;
                            Log.i(TAG,"(MeshTV-MVRS 0214) tabSelected - PREV:"+prev+"\n");
                            Log.i(TAG,"(MeshTV-MVRS 0214) tabSelected - NEXT:"+next+"\n");

                            View prevView = getActivity().getLayoutInflater().inflate(tabLayout.itemLayout(),tb_items,false);
                            draw(prevView,selection.get(prev),false,prev);
                            tb_items.getTabAt(prev).setCustomView(prevView);

                            View nextView = getActivity().getLayoutInflater().inflate(tabLayout.itemLayout(),tb_items,false);
                            draw(nextView,selection.get(next),false,next);
                            tb_items.getTabAt(next).setCustomView(nextView);
                            tb_items.getTabAt(next).setCustomView(nextView);
                        }
                        tb_items.invalidate();
                        Log.i(TAG,"(MeshTV-MVRS 0214) tabSelected - TB Selected from TAB\n");
                        Log.i(TAG,"(MeshTV-MVRS 0214) tabSelected - TB Selected from TAB:"+tab.getPosition()+"\n");
                    }
                    else
                    {
                        Log.e(TAG,"(MeshTV-MVRS 0214) tabSelected - TB not found\n");
                    }
                    Log.i(TAG,"(MeshTV-MVRS 0214) tabSelected - =================================================\n");
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }

        }

        @Override
        public void onTabUnselected(TabLayout.Tab tab)
        {

//            Log.i(TAG,"(MeshTV-MVRS 0214) onTabUnselected - =================================================\n");
//            Log.i(TAG,"(MeshTV-MVRS 0214) onTabUnselected - Unselected:"+tab.getPosition()+"\n");
//            View temp = getActivity().getLayoutInflater().inflate(tabLayout.itemLayout(),tb_items,false);
//            draw(temp,selection.get(tab.getPosition()),false);
//            tab.setCustomView(temp);
//            tb_items.invalidate();
//            Log.i(TAG,"(MeshTV-MVRS 0214) onTabUnselected - =================================================\n");
        }

        @Override
        public void onTabReselected(TabLayout.Tab tab)
        {
            Log.i(TAG,"(MeshTV-MVRS 0214) onTabReselected - =================================================\n");

            Log.i(TAG,"(MeshTV-MVRS 0214) onTabReselected - =================================================\n");
        }
    };
    //==============================================================================================
    //===========================================TabCallBack========================================
    public interface TabCallBack
    {
        void insertToTab(Object o);
        void updateFromTab(Object o, int index);
        void deleteFromTab(Object o, int index);
        void clickedFromTab(Object o, int index);
        void selectedFromTab(Object o, int index);
        void clear();
        void onTabsDrawn(boolean isUpdated);
    }
    //==============================================================================================
    //==============================================Abstract========================================
    public abstract void launch(T object);
    public abstract void onTabsDrawn();
    public abstract void draw(View v,T object,boolean isSelected,int index);
    //==============================================================================================
}
