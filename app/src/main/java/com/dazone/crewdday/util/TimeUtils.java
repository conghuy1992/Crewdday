package com.dazone.crewdday.util;

import android.util.Log;

import com.dazone.crewdday.Cons;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Created by DAZONE on 04/03/16.
 */
public class TimeUtils {

    public static final Calendar FIRST_DAY_OF_TIME;
    public static final Calendar LAST_DAY_OF_TIME;
    public static final int DAYS_OF_TIME;
    public static final int MONTHS_OF_TIME;
    public static final int YEARS_OF_TIME;

    static {
        FIRST_DAY_OF_TIME = Calendar.getInstance();
        FIRST_DAY_OF_TIME.set(Calendar.getInstance().get(Calendar.YEAR) - 100, Calendar.JANUARY, 1);
        LAST_DAY_OF_TIME = Calendar.getInstance();
        LAST_DAY_OF_TIME.set(Calendar.getInstance().get(Calendar.YEAR) + 100, Calendar.DECEMBER, 31);
        DAYS_OF_TIME = (int) ((LAST_DAY_OF_TIME.getTimeInMillis() - FIRST_DAY_OF_TIME.getTimeInMillis()) / (24 * 60 * 60 * 1000));
        MONTHS_OF_TIME = (LAST_DAY_OF_TIME.get(Calendar.YEAR) - FIRST_DAY_OF_TIME.get(Calendar.YEAR)) * 12 + LAST_DAY_OF_TIME.get(Calendar.MONTH) - FIRST_DAY_OF_TIME.get(Calendar.MONTH);
        YEARS_OF_TIME = (LAST_DAY_OF_TIME.get(Calendar.YEAR) - FIRST_DAY_OF_TIME.get(Calendar.YEAR));
    }

    public static Date getEndOfDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 000);
        return calendar.getTime();
    }

    public static Date getStartOfDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    public static int getPositionForMonth(Calendar month) {
        if (month != null) {
            int diffYear = month.get(Calendar.YEAR) - FIRST_DAY_OF_TIME.get(Calendar.YEAR);
            return diffYear * 12 + month.get(Calendar.MONTH) - FIRST_DAY_OF_TIME.get(Calendar.MONTH);
        }
        return 0;
    }

    public static Calendar getMonthForPosition(int position) throws IllegalArgumentException {
        if (position < 0) {
            throw new IllegalArgumentException("position cannot be negative");
        }
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(FIRST_DAY_OF_TIME.getTimeInMillis());
        cal.add(Calendar.YEAR, position / 12);
        cal.add(Calendar.MONTH, position % 12);
        return cal;
    }

    public static Calendar getYearForPosition(int position) throws IllegalArgumentException {
        if (position < 0) {
            throw new IllegalArgumentException("position cannot be negative");
        }
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(FIRST_DAY_OF_TIME.getTimeInMillis());
        cal.add(Calendar.YEAR, position);
        return cal;
    }

    public static int getPositionForYear(Calendar year) throws IllegalArgumentException {
        if (year != null) {
            return (year.get(Calendar.YEAR) - FIRST_DAY_OF_TIME.get(Calendar.YEAR));
        }
        return 0;
    }

    public static Calendar getDayForPosition(int position) throws IllegalArgumentException {
        if (position < 0) {
            throw new IllegalArgumentException("position cannot be negative");
        }

        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(FIRST_DAY_OF_TIME.getTimeInMillis());
        cal.add(Calendar.DAY_OF_YEAR, position);
        return cal;
    }

    public static int getPositionForDay(Calendar day) {
        if (day != null) {
            return (int) ((day.getTimeInMillis() - FIRST_DAY_OF_TIME.getTimeInMillis())
                    / 86400000  //(24 * 60 * 60 * 1000)
            );
        }
        return 0;
    }

    public static String showTime(long date, String defaultPattern) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(defaultPattern, Locale.getDefault());
        return simpleDateFormat.format(new Date(date));
    }

    public static String showTimeWithoutTimeZone(long date, String defaultPattern) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(defaultPattern, Locale.getDefault());
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        return simpleDateFormat.format(new Date(date));
    }

    public static Calendar getFirstDayOfMonth(Calendar cal) {
        cal.set(Calendar.DAY_OF_MONTH, 1);
        return cal;
    }

    public static Calendar getEndDayOfMonth(Calendar cal) {
        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
        return cal;
    }

    public static Calendar getFirstMonOfYear(Calendar cal) {
        cal.set(Calendar.MONTH, 0);
        return cal;
    }

    public static int getDayOffset(Calendar cal) {
        int i = 0;
        switch (cal.get(Calendar.DAY_OF_WEEK)) {
            case Calendar.SUNDAY:
                return 7;
            case Calendar.MONDAY:
                return 1;
            case Calendar.TUESDAY:
                return 2;
            case Calendar.WEDNESDAY:
                return 3;
            case Calendar.THURSDAY:
                return 4;
            case Calendar.FRIDAY:
                return 5;
            case Calendar.SATURDAY:
                return 6;
        }
        return i;
    }


    public static int getCurrentMonth(long timeInMilliSecond) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(timeInMilliSecond);
        return cal.get(Calendar.MONTH);
    }

    public static int getTimezoneOffsetInMinutes() {
        TimeZone tz = TimeZone.getDefault();
        int offsetMinutes = tz.getRawOffset() / 60000;
        return offsetMinutes;
    }

    public static boolean isSameDate(long day1, long day2) {
        Calendar cal1 = Calendar.getInstance();
        Calendar cal2 = Calendar.getInstance();
        cal1.setTimeInMillis(day1);
        cal2.setTimeInMillis(day2);
        return cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) &&
                cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR);
    }

    public static boolean isSaturday(long millis) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(millis);

        if (cal.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY) {
            return true;
        }
        return false;
    }

    public static boolean isSunday(long millis) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(millis);
        if (cal.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
            return true;
        }
        return false;
    }


    public static long getTimeFromString(String timeString) {
        long time;
        try {
            String tempTimeString = timeString;
            tempTimeString = tempTimeString.replace("/Date(", "");
            tempTimeString = tempTimeString.replace(")/", "");
            time = Long.valueOf(tempTimeString);
        } catch (Exception e) {
            return 0;
        }
        return time;
    }

    public static int getNumberWeekOfYear(int year) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, Calendar.DECEMBER);
        cal.set(Calendar.DAY_OF_MONTH, 31);

        int ordinalDay = cal.get(Calendar.DAY_OF_YEAR);
        int weekDay = cal.get(Calendar.DAY_OF_WEEK) - 1; // Sunday = 0
        return (ordinalDay - weekDay + 10) / 7;
    }


    public static String getCurrentDayFormatted_1() {
        DateFormat df = new SimpleDateFormat("yyyy/MM/d/EEEE");
        Date todayInMilli = Calendar.getInstance().getTime();
        String today = df.format(todayInMilli);
        return today;
    }


    public static String getCurrentDayFormatted_2() {
        DateFormat df = new SimpleDateFormat("yyyyMMdd");
        Date todayInMilli = Calendar.getInstance().getTime();
        String today = df.format(todayInMilli);
        return today;
    }

    public static String getCurrentDayFormatted_3() {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Date todayInMilli = Calendar.getInstance().getTime();
        String today = df.format(todayInMilli);
        return today;
    }


    public static String convertFromRODate(String date) {
        String day = Cons.NULL;
        if (!date.equals(Cons.NULL)) {
            try {
                SimpleDateFormat newDateFormat = new SimpleDateFormat("yyyyMMdd");
                Date MyDate = newDateFormat.parse(date);
                newDateFormat.applyPattern("yyyy-MM-dd");
                day = newDateFormat.format(MyDate);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return day;
    }

    public static String convertToRODate(String date) {
        String day = "";
        if (!date.isEmpty()) {
            try {
                SimpleDateFormat newDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                Date MyDate = newDateFormat.parse(date);
                newDateFormat.applyPattern("yyyyMMdd");
                day = newDateFormat.format(MyDate);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return day;
    }

    public static String convertToDayOfWeek(String date) {
        String day = null;
        try {
            SimpleDateFormat newDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date MyDate = newDateFormat.parse(date);
            newDateFormat.applyPattern("yyyy-MM-dd EEEE");
            day = newDateFormat.format(MyDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return day;
    }


    public static String convertToSimpleDate(String date) {
        String day = null;
        try {
            SimpleDateFormat newDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
            Date MyDate = newDateFormat.parse(date);
            newDateFormat.applyPattern("yyyy-MM-dd");
            day = newDateFormat.format(MyDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return day;
    }

    public static String convertToSimpleDate2(String date) {
        String day = null;
        try {
            SimpleDateFormat newDateFormat = new SimpleDateFormat("dd/MM/yyyy");
            Date MyDate = newDateFormat.parse(date);
            newDateFormat.applyPattern("yyyy-MM-dd");
            day = newDateFormat.format(MyDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return day;
    }


    public static long convertDateToMillisecond(String date) {
        String day = null;
        long millisecond = 0;
        try {
            SimpleDateFormat newDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date MyDate = newDateFormat.parse(date);
            millisecond = MyDate.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return millisecond;
    }


    public static String convertMillisecondToDate(long millisecond) {
        Date date = new Date(millisecond);
        SimpleDateFormat newDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String dateText = newDateFormat.format(date);

        return dateText;
    }
}
