package com.dazone.crewdday.mInterface;

import android.view.View;

import java.util.Calendar;

/**
 * Created by david on 5/20/15.
 */
public interface DatePickerDialogListener {
    void onFinishEditDialog(Calendar mDate, int type, String repeatType);

    void onFinishEditDialog(View view, String text, String[] array, String repeatType,int type);

    void onFinishEditDialog(String text, String[] array, int optionType, String repeatType);
}
