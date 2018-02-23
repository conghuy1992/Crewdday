package com.dazone.crewdday.viewholder;

import android.view.View;

/**
 * Created by DAZONE on 14/03/16.
 */
public abstract class BaseAddNewViewHolder {

    //optionType use for OptionDialogManager
    public BaseAddNewViewHolder(View v,boolean endTimeExisted,int optionType,
                                String repeatType, String dialogTitle,
                                int containerResourceId, int tvResoureId,String[] array) {
        setup(v,endTimeExisted,optionType,repeatType,dialogTitle,containerResourceId,tvResoureId,array);
    }

    public abstract void setup(View v,boolean endTimeExisted,int optionType,String repeatType,String dialogTitle,int resourceId,int tvResoureId,String[] array);
}
