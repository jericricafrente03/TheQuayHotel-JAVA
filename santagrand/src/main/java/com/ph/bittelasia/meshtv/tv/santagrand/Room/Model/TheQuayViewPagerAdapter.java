package com.ph.bittelasia.meshtv.tv.santagrand.Room.Model;



import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import java.util.ArrayList;

public abstract class TheQuayViewPagerAdapter<T> extends FragmentStatePagerAdapter
{
    //==========================================Variable============================================
    //------------------------------------------Constant--------------------------------------------
    public static final String TAG = TheQuayViewPagerAdapter.class.getSimpleName();
    //----------------------------------------------------------------------------------------------
    //------------------------------------------Instance--------------------------------------------
    ArrayList<T> items;
    //----------------------------------------------------------------------------------------------
    //==============================================================================================
    //=========================================Constructor==========================================
    public TheQuayViewPagerAdapter(FragmentManager fm, ArrayList<T> items)
    {
        super(fm);
        this.items = new ArrayList<>();
        this.items.addAll(items);
    }
    //==============================================================================================
    //=============================================Method===========================================
    //---------------------------------------------Getter-------------------------------------------
    public ArrayList<T> getItems(){return this.items;}
    //----------------------------------------------------------------------------------------------
    //==============================================================================================
    //====================================FragmentStatePagerAdapter=================================
    @Override
    public Fragment getItem(int position) {
        return getFragment(items.get(position));
    }
    @Override
    public int getCount(){return items.size();}
    //==============================================================================================
    //============================================Abstract==========================================
    public abstract Fragment getFragment(T object);
    public abstract boolean isSame(T o1,T o2);
    //==============================================================================================

}
