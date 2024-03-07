package com.ph.bittelasia.meshtv.tv.santagrand.Core.Control;



import android.widget.BaseAdapter;

import com.ph.bittelasia.meshtv.tv.mtvlib.Core.Control.Listener.MeshListItemClickedListener;

import java.util.ArrayList;


public abstract class TheQuayListAdapter extends BaseAdapter{


        //===========================================Variable===========================================

        //-------------------------------------------Instance-------------------------------------------
        private MeshListItemClickedListener         clickedListener;
        private  ArrayList<?>                       objects;
        private  ArrayList<?>                       filteredObjects;
        private  Object                             object;
        private  int                                selectedItem;
        //----------------------------------------------------------------------------------------------
        //==============================================================================================


        //=========================================Constructor==========================================
        //----------------------------------------------------------------------------------------------
        public TheQuayListAdapter() {
        }
        //----------------------------------------------------------------------------------------------
        //==============================================================================================



        //==========================================BaseAdapter=========================================
        //----------------------------------------------------------------------------------------------
        @Override
        public int getCount() {
            return getObjects().size();
        }

        @Override
        public Object getItem(int i) {
            return getObjects().get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }
        //----------------------------------------------------------------------------------------------
        //==============================================================================================



        //===========================================Methods============================================

        //-------------------------------------------Getter---------------------------------------------

        public Object getObject() {
            return object;
        }

        public MeshListItemClickedListener getClickedListener() {
              return clickedListener;
        }

        public ArrayList<?> getObjects() {
             return objects;
        }

        private ArrayList<?> getFilteredObjects() {
            return filteredObjects;
        }


        public int getSelectedItem() {
            return selectedItem;
        }
        //----------------------------------------------------------------------------------------------

        //-------------------------------------------Setter---------------------------------------------

        public void setObject(Object object) {
            this.object = object;
        }

        public void setClickedListener(MeshListItemClickedListener clickedListener) {
                this.clickedListener = clickedListener;
        }
        public void setObjects(ArrayList<?> objects,boolean performSort) {
            try {
                if(performSort)
                {
                    this.objects = sortObjects(objects);
                    this.filteredObjects = sortObjects(objects);
                }
                else
                {
                    this.objects = objects;
                    this.filteredObjects = objects;
                }
            }catch (Exception e)
            {
                e.printStackTrace();
            }
        }

        public void setSelectedItem(int selectedItem) {
            this.selectedItem = selectedItem;
        }
        //----------------------------------------------------------------------------------------------

        //-------------------------------------Filter Lists---------------------------------------------

        /**
         * Method to Filter Objects based on attributes assigned
         * @param field attribute of Object
         * @param performSort is boolean, if you want to perform sorting of objects
         * @param <T> is the Object to assign when performing filter
         */
        @SuppressWarnings("unchecked")
        public <T> void perFormFiltering(CharSequence field,boolean performSort) {
            try
            {
                ArrayList<T> filters = new ArrayList<>();

                if (field != null && field.length() > 0) {
                    field = field.toString().toLowerCase();
                    for (int i = 0; i < getFilteredObjects().size(); i++) {
                        T object = ((T) getFilteredObjects().get(i));
                        filterObjects(filters, object, field);
                    }
                } else {
                    filters = ((ArrayList<T>) getFilteredObjects());
                }
                setObjects(filters,performSort);
                this.notifyDataSetChanged();
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }

        /**
         * Method to Sort List by Fields
         * @param list is the Lists of the Objects
         */
        @SuppressWarnings("unchecked")
        public void performSort(ArrayList list)
        {
            try
            {
                setObjects(list,true);
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }

        //----------------------------------------------------------------------------------------------
        //==============================================================================================

        //===================================Abstract Methods===========================================
        //----------------------------------------------------------------------------------------------

        /**
         * Method to filter the  of Objects
         * @param filters is the List of Objects
         * @param object an Object to Assign in filtering the ArrayList of objects
         * @param charSequence is the Sequence of character
         * @param <T> is any type of Object
         */
        public abstract <T> void filterObjects(ArrayList<T> filters, Object object, CharSequence charSequence);
        //----------------------------------------------------------------------------------------------
        /**
         * Method to sort object
         * @param objects is the List of Objects
         * @param <T> is any type of objects
         * @return
         */
        public abstract <T> ArrayList sortObjects(ArrayList<T> objects);
        //----------------------------------------------------------------------------------------------
        //==============================================================================================
}
