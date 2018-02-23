package com.dazone.crewdday.util;

import android.content.Context;

import com.dazone.crewdday.Cons;
import com.dazone.crewdday.R;
import com.dazone.crewdday.mInterface.LoadArrayCallback;

import java.util.ArrayList;

/**
 * Created by DAZONE on 14/03/16.
 */
public class LoadArrayUtils {
/*
    public static void loadArray(final Context context, final String type, final LoadArrayCallback callback) {

        new Thread(new Runnable() {
            @Override
            public void run() {
                String[] array;
                switch (type) {
                    case Cons.FRAGMENT_TYPE_DAILY:
                        array = context.getResources().getStringArray(R.array.daily_interval_array);
                        callback.loadArrayCallBack(array);
                        break;
                    case Cons.FRAGMENT_TYPE_WEEKLY:
                        array = context.getResources().getStringArray(R.array.weekly_interval_array);
                        callback.loadArrayCallBack(array);
                        break;
                    default:
                        break;

                }
            }
        }).start();
    }*/

    public static String[] getMonthArray(Context context) {
        String[] monthArray = context.getResources().getStringArray(R.array.annually_month_array);
        return monthArray;
    }

    public static String[] getDateArray(Context context) {
        String[] dateArray = context.getResources().getStringArray(R.array.monthly_date_array);
        return dateArray;
    }

    public static String[] getWeekArray(Context context) {
        String[] weekArray = context.getResources().getStringArray(R.array.monthly_week_array);
        return weekArray;
    }

    public static String[] getDayArray(Context context) {
        String[] dayArray = context.getResources().getStringArray(R.array.monthly_day_array);
        return dayArray;
    }


    public static String[] getCalendar(Context context) {
        String[] dayArray = context.getResources().getStringArray(R.array.annually_calendar_type_array);
        return dayArray;
    }


    public static String[] getLunarArray(Context context) {
        String[] dayArray = context.getResources().getStringArray(R.array.annually_calendar_type_array);
        return dayArray;
    }


    public static int getItemPosition(String text, String[] array) {
        int index = -1;
        for (int i = 0; i < array.length; i++) {
            if (array[i].equals(text)) {
                index = i;
                return index;
            }
        }
        return index;
    }

    public static int getItemPosition(String text, ArrayList<String> array) {
        int index = -1;
        for (int i = 0; i < array.size(); i++) {
            if (array.get(i).equals(text)) {
                index = i;
                return index;
            }
        }
        return index;
    }
}
