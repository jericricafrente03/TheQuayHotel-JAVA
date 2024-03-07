package com.ph.bittelasia.meshtv.tv.santagrand.Room.Library;

import android.os.Bundle;
import android.os.Handler;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.ph.bittelasia.meshtv.tv.mtvlib.Core.Control.Annotation.Manager.MeshAnnotationManager;
import com.ph.bittelasia.meshtv.tv.mtvlib.Core.Control.Annotation.View.General.Layout;
import com.ph.bittelasia.meshtv.tv.mtvlib.Entity.Control.Listener.MeshDAOListener;
import com.ph.bittelasia.meshtv.tv.santagrand.Core.Control.TheQuayListAdapter;
import com.ph.bittelasia.meshtv.tv.santagrand.Room.Control.TheQuayDAOManager;

import java.lang.reflect.Method;
import java.util.ArrayList;

public abstract class MeshTVRFragment<T> extends Fragment
{
    //=========================================Variable=============================================
    //-----------------------------------------Constant---------------------------------------------
    public static final String TAG = MeshTVRFragment.class.getSimpleName();
    //----------------------------------------------------------------------------------------------
    //----------------------------------------Annotations-------------------------------------------
    Layout layout;
    //----------------------------------------------------------------------------------------------
    //------------------------------------------View------------------------------------------------
    View v = null;
    //----------------------------------------------------------------------------------------------
    //-----------------------------------------Methods----------------------------------------------
    private ArrayList<Method> initMethods;
    private ArrayList<Method> termMethods;
    private ArrayList<Method> timedMethods;
    public  ArrayList         copyList;
    public  boolean           isXMPPConnected;
    //----------------------------------------------------------------------------------------------
    //-----------------------------------------Instance---------------------------------------------
    T item;
    Handler h;
    //----------------------------------------------------------------------------------------------
    //==============================================================================================
    //=========================================LifeCycle============================================
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState)
    {
        View v = inflater.inflate(layout.value(),container,false);
        return v;
    }

    @Override
    public final void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        MeshAnnotationManager.bindWidgets(this);
        draw(view);
    }
    @Override
    public void onResume()
    {
        super.onResume();
        MeshAnnotationManager.startTimedMethods(timedMethods, h, this);
    }
    @Override
    public void onPause() {
        super.onPause();
        MeshAnnotationManager.stopTimedMethods(h);

    }
    //==============================================================================================
    //==========================================Method==============================================
    //------------------------------------------Action----------------------------------------------
    private void init()
    {
        try {
            layout = getClass().getAnnotation(Layout.class);
            h = new Handler();
            initMethods = new ArrayList<>();
            initMethods.addAll(MeshAnnotationManager.getInit(getClass()));
            termMethods = new ArrayList<>();
            termMethods.addAll(MeshAnnotationManager.getTerminate(getClass()));
            timedMethods = new ArrayList<>();
            timedMethods.addAll(MeshAnnotationManager.getTimed(getClass()));
            MeshAnnotationManager.runMethods(initMethods, this);
        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    //----------------------------------------------------------------------------------------------
    //------------------------------------------Getter----------------------------------------------
    public View getV() {
        return v;
    }
    public ArrayList<Method> getInitMethods() { return initMethods; }
    public ArrayList<Method> getTermMethods() { return termMethods; }
    public ArrayList<Method> getTimedMethods() { return timedMethods; }
    public T getItem() { return item; }

    public void isXMPPConnected(boolean isXMPPConnected) {
        this.isXMPPConnected = isXMPPConnected;
    }
    //----------------------------------------------------------------------------------------------
    //------------------------------------------Setter----------------------------------------------
    public void setItem(T item)
    {
        this.item = item;
        updateDisplay(this.item);
    }
    //----------------------------------------------------------------------------------------------
    //==============================================================================================
    //=========================================Abstract=============================================
    public abstract void draw(View v);
    public abstract void updateDisplay(T item);
    //==============================================================================================


    //=========================================Method===============================================
    //----------------------------------------------------------------------------------------------
    public <T,A extends TheQuayListAdapter> void update(Class<T> c, A adapter, Object object, final int position)
    {
        try {
            copyList = null;
            TheQuayDAOManager.get().retrieve(c);
            TheQuayDAOManager.get().addListener(new MeshDAOListener() {
                @Override
                public void onInsertedBulk(ArrayList arrayList) {
                    try {
                        copyList = arrayList;
                        if (position >= 0) {
                            copyList.set(position, object);
                        }
                        TheQuayDAOManager.get().clear(c);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onDeletedBulk(ArrayList arrayList) {

                }

                @Override
                public void onRetrieved(ArrayList arrayList) {

                }

                @Override
                public void onCleared(Class aClass) {
                    try {
                        TheQuayDAOManager.get().insert(copyList);
                        adapter.notifyDataSetChanged();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onDAONotFound(String s) {

                }
            });
        }catch (Exception e)
        {
          e.printStackTrace();
        }
    }
    //----------------------------------------------------------------------------------------------
    //==============================================================================================
}
