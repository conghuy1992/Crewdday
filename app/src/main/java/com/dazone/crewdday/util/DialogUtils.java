package com.dazone.crewdday.util;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.NumberPicker;
import android.widget.TextView;

//import com.afollestad.materialdialogs.MaterialDialog;
import com.dazone.crewdday.R;
import com.dazone.crewdday.mInterface.DatePickerDialogListener;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by DAZONE on 14/03/16.
 */
public class DialogUtils {
    public static void openCalendarDialog(Context context, String date, DatePickerDialog.OnDateSetListener callback) {
        Activity activity = (Activity) context;

        SimpleDateFormat curFormater = new SimpleDateFormat("yyyy-MM-dd");
        Date dateObj = null;
        try {
            dateObj = curFormater.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dateObj);

        DatePickerDialog dpd = DatePickerDialog.newInstance(
                callback,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
        );
        dpd.show(activity.getFragmentManager(), "Datepickerdialog");
        dpd.setAccentColor(Color.parseColor("#277bcd"));
    }


    public static void openOptionDialog(Context context, final int optionType, String dialogTitle, final String[] array, final String repeatType, final DatePickerDialogListener callback) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);
        dialogBuilder.setTitle(dialogTitle);
        dialogBuilder.setItems(array, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {
                String selectedText = array[item].toString();  //Selected item in listview
                callback.onFinishEditDialog(selectedText, array, optionType, repeatType);
            }
        });
        //Create alert dialog object via builder
        AlertDialog alertDialogObject = dialogBuilder.create();

        //Show the dialog
        alertDialogObject.show();

        int dividerId = context.getResources().getIdentifier("titleDivider", "id", "android");
        View divider = alertDialogObject.findViewById(dividerId);
        if (divider != null) {
            divider.setBackgroundColor(Color.parseColor("#277bcd"));
        }
        int textViewId = context.getResources().getIdentifier("alertTitle", "id", "android");
        TextView tv = (TextView) alertDialogObject.findViewById(textViewId);
        if (tv != null) {
            tv.setTextColor(Color.parseColor("#277bcd"));
            tv.setGravity(Gravity.CENTER_VERTICAL);
        }

    }

    public static void openOptionDialog(final View view,Context context, String dialogTitle, final String[] array,
                                        final String repeatType, final DatePickerDialogListener callback,final int type) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);
        dialogBuilder.setTitle(dialogTitle);
        dialogBuilder.setItems(array, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {
                String selectedText = array[item].toString();  //Selected item in listview
                callback.onFinishEditDialog(view,selectedText, array, repeatType,type);
            }
        });
        //Create alert dialog object via builder
        AlertDialog alertDialogObject = dialogBuilder.create();

        //Show the dialog
        alertDialogObject.show();

        int dividerId = context.getResources().getIdentifier("titleDivider", "id", "android");
        View divider = alertDialogObject.findViewById(dividerId);
        if (divider != null) {
            divider.setBackgroundColor(Color.parseColor("#277bcd"));
        }
        int textViewId = context.getResources().getIdentifier("alertTitle", "id", "android");
        TextView tv = (TextView) alertDialogObject.findViewById(textViewId);
        if (tv != null) {
            tv.setTextColor(Color.parseColor("#277bcd"));
            tv.setGravity(Gravity.CENTER_VERTICAL);
        }

    }
}
