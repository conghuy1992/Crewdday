package com.dazone.crewdday.util;

import android.util.Log;

import com.dazone.crewdday.Cons;
import com.dazone.crewdday._Application;
import com.dazone.crewdday.mInterface.UpdateStringROCallback;
import com.dazone.crewdday.model.AnnuallyModel;
import com.dazone.crewdday.model.MonthlyModel;
import com.dazone.crewdday.model.SpecialDateModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Pattern;

/**
 * Created by Admin on 3/26/2016.
 */
public class StringUtils {
    public static String TAG = "StringUtils";
    private static PreferenceUtilities mPrefUtils = _Application.getInstance().getPreferenceUtilities();

    public static void buildSpecialRO(final ArrayList<SpecialDateModel> specialDateModels) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String ddayType = mPrefUtils.getDdayType();
                int calendarType = Integer.parseInt(mPrefUtils.getSpecicalCalendar());
                if (ddayType.equals(String.valueOf(Cons.DDAY_PLUS_MODE))) {
                    String formattedDate = TimeUtils.convertToRODate(specialDateModels.get(0).getSpecialDate());
                    String repeatOptions = "" +
                            "{" +
                            "TypeName: " + "\"" + "CheckDPlusDay" + "\"" + "," +
                            "StartDate: " + "\"" + formattedDate + "\"" + "," +
                            "Lunar:" + calendarType +
                            "}";
                    mPrefUtils.setSpecialPlusRO(repeatOptions);
                } else {
                    StringBuilder stringBuilder = new StringBuilder();
                    for (int i = 0; i < specialDateModels.size(); i++) {
                        if (i == 0) {
                            String formattedDate = TimeUtils.convertToRODate(specialDateModels.get(0).getSpecialDate());
                            String repeatOptions = "" +
                                    "{" +
                                    "TypeName: " + "\"" + "CheckSpecificDday" + "\"" + "," +
                                    "SpecificDay: " + "\"" + formattedDate + "\"" + "," +
                                    "Lunar:" + calendarType +
                                    "}";
                            stringBuilder.append(repeatOptions);
                        } else {
                            String formattedDate = TimeUtils.convertToRODate(specialDateModels.get(i).getSpecialDate());
                            String repeatOptions = " , " +
                                    "{" +
                                    "TypeName: " + "\"" + "CheckSpecificDday" + "\"" + "," +
                                    "SpecificDay: " + "\"" + formattedDate + "\"" + "," +
                                    "Lunar:" + calendarType +
                                    "}";
                            stringBuilder.insert(stringBuilder.length() - 1, repeatOptions);
                        }
                    }
//                    Log.e(TAG, stringBuilder.toString());
                    mPrefUtils.setSpecialSpecificRO(stringBuilder.toString());
                }

            }
        }).start();

    }

    //--------------------------------------- DAILY ---------------------------------------------

    public static void buildDailyRO(int interval, String startDate, String endDate, String startType, int holidayType) {

        String newStartDate = TimeUtils.convertToRODate(startDate);
        String newEndDate = TimeUtils.convertToRODate(endDate);
        String repeatOption = "" +
                "{" +
                "TypeName: \"CheckEveryDayDday\"," +
                "StartDate: \"" + newStartDate + "\"," +
                "EndDate: \"" + newEndDate + "\"," +
                "StartOption: \"" + startType + "\"," +
                "Interval: " + interval + "," +
                "HolidayCondition: " + holidayType +
                "}";

        mPrefUtils.setDailyRO(repeatOption);

    }

    //--------------------------------------- WEEKLY ---------------------------------------------

    public static void buildWeeklyRO(int interval, String startDate, String endDate, String specificDays, int holidayType) {
//        Log.e(TAG,specificDays);
        String newStartDate = TimeUtils.convertToRODate(startDate);
        String newEndDate = TimeUtils.convertToRODate(endDate);
        String repeatOption = "" +
                "{" +
                "TypeName: \"CheckEveryDdayOfWeek\"," +
                "StartDate: \"" + newStartDate + "\"," +
                "EndDate: \"" + newEndDate + "\"," +
                "Interval: " + interval + "," +
                "SpecificDayOfWeek: " + specificDays + "," +
                "HolidayCondition: " + holidayType +
                "}";
        mPrefUtils.setWeeklyRO(repeatOption);
    }

    //--------------------------------------- MONTHLY ---------------------------------------------


    public static void buildMonthlyRO(final int interval, final String startDate, final String endDate,
                                      final String specific, final int holiday, final int type) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String newStartDate = TimeUtils.convertToRODate(startDate);
                String newEndDate = "";
                if (!endDate.isEmpty()) {
                    newEndDate = TimeUtils.convertToRODate(endDate);
                }
                String repeatOption;
                switch (type) {
                    case Cons.DATE:
                        if (mPrefUtils.getMonthlyDateROEmpty().equals(Cons.TRUE)) {
                            repeatOption = "" +
                                    "{" +
                                    "TypeName: \"CheckEveryMonthSpecificDday\"," +
                                    "StartDate: \"" + newStartDate + "\"," +
                                    "EndDate: \"" + newEndDate + "\"," +
                                    "Interval: " + interval + "," +
                                    "SpecificDD: \"" + specific + "\"," +
                                    "HolidayCondition: " + holiday +
                                    "}";
                            mPrefUtils.setMonthlyRO_1(repeatOption);
                            mPrefUtils.setMonthlyDateROEmpty(Cons.FALSE);
                            // Log.e("DATE", repeatOption);
                        } else {
                            if (!mPrefUtils.getMonthlyRO_1().isEmpty()) {
                                String dateRO = mPrefUtils.getMonthlyRO_1();
                                StringBuilder stringBuilder = new StringBuilder();
                                stringBuilder.append(dateRO);
                                if (newEndDate.isEmpty()) {
                                    stringBuilder.insert(101, "," + specific);
                                } else {
                                    stringBuilder.insert(109, "," + specific);
                                }
                                mPrefUtils.setMonthlyRO_1(stringBuilder.toString());
                                // Log.e("DATE-OTHER", stringBuilder.toString());

                            }
                        }
                        break;
                    case Cons.WEEK:
                        if (mPrefUtils.getMonthlyWeekROEmpty().equals(Cons.TRUE)) {
                            repeatOption = "" +
                                    "{" +
                                    "TypeName: \"CheckEveryMonthWeekDday\"," +
                                    "StartDate: \"" + newStartDate + "\"," +
                                    "EndDate: \"" + newEndDate + "\"," +
                                    "Interval: " + interval + "," +
                                    "SpecificWeek: \"" + specific + "\"," +
                                    "HolidayCondition: " + holiday +
                                    "}";
                            mPrefUtils.setMonthlyRO_2(repeatOption);
                            mPrefUtils.setMonthlyWeekROEmpty(Cons.FALSE);
                            // Log.e("WEEK", repeatOption);
                        } else {
                            if (!mPrefUtils.getMonthlyRO_2().isEmpty()) {
                                String dateRO = mPrefUtils.getMonthlyRO_2();
                                StringBuilder stringBuilder = new StringBuilder();
                                stringBuilder.append(dateRO);
                                if (newEndDate.isEmpty()) {
                                    stringBuilder.insert(100, "," + specific);
                                } else {
                                    stringBuilder.insert(108, "," + specific);
                                }
                                mPrefUtils.setMonthlyRO_2(stringBuilder.toString());
                                // Log.e("WEEK-OTHER", stringBuilder.toString());
                            }
                        }
                        break;
                }
            }
        }).start();

    }


    //---------------------------------- ANNUALLY--------------------------------------------

    public static void buildAnnuallyRO(final int interval, final String startDate, final String endDate,
                                       final String specific, final int calendar, final int holiday, final int type) {


        String newStartDate = TimeUtils.convertToRODate(startDate);
        String newEndDate = "";
        if (!endDate.isEmpty()) {
            newEndDate = TimeUtils.convertToRODate(endDate);
        }
        String repeatOption;
        switch (type) {
            case Cons.DATE_TYPE:
                if (mPrefUtils.getAnnuallyDateROEmpty().equals(Cons.TRUE)) {
                    repeatOption = "" +
                            "{" +
                            "TypeName: \"CheckEveryYearDday\"," +
                            "StartDate: \"" + newStartDate + "\"," +
                            "EndDate: \"" + newEndDate + "\"," +
                            "Interval: " + interval + "," +
                            "SpecificMMDD: \"" + specific + "\"," +
                            "Lunar: " + calendar + "," +
                            "HolidayCondition: " + holiday +
                            "}";

                    mPrefUtils.setAnnuallyRO_1(repeatOption);
                    mPrefUtils.setAnnuallyDateROEmpty(Cons.FALSE);
                    Log.e("DATE", repeatOption);
                } else {
                    if (!mPrefUtils.getAnnuallyRO_1().isEmpty()) {
                        String dateRO = mPrefUtils.getAnnuallyRO_1();
                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append(dateRO);
                        if (newEndDate.isEmpty()) {
                            stringBuilder.insert(97, "," + specific);
                        } else {
                            stringBuilder.insert(105, "," + specific);
                        }
                        mPrefUtils.setAnnuallyRO_1(stringBuilder.toString());
                        Log.e("DATE-OTHER", stringBuilder.toString());

                    }
                }
                break;
            case Cons.WEEK_TYPE:
                if (mPrefUtils.getAnnuallyWeekROEmpty().equals(Cons.TRUE)) {
                    repeatOption = "" +
                            "{" +
                            "TypeName: \"CheckEveryYearWeek\"," +
                            "StartDate: \"" + newStartDate + "\"," +
                            "EndDate: \"" + newEndDate + "\"," +
                            "Interval: " + interval + "," +
                            "SpecificMMnthWeek: \"" + specific + "\"," +
                            "HolidayCondition: " + holiday +
                            "}";
                    mPrefUtils.setAnnuallyRO_2(repeatOption);
                    mPrefUtils.setAnnuallyWeekROEmpty(Cons.FALSE);
                    Log.e("WEEK", repeatOption);
                } else {
                    if (!mPrefUtils.getAnnuallyRO_2().isEmpty()) {
                        String dateRO = mPrefUtils.getAnnuallyRO_2();
                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append(dateRO);
                        if (newEndDate.isEmpty()) {
                            stringBuilder.insert(102, "," + specific);
                        } else {
                            stringBuilder.insert(110, "," + specific);
                        }
                        mPrefUtils.setAnnuallyRO_2(stringBuilder.toString());
                        Log.e("WEEK-OTHER", stringBuilder.toString());
                    }
                }

                break;
        }
    }

}
