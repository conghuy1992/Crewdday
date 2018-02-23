package com.dazone.crewdday.other;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Created by david on 12/23/15.
 */
public class TimeUtils {

    public static final Calendar FIRST_DAY_OF_TIME;
    public static final Calendar LAST_DAY_OF_TIME;
    public static final int DAYS_OF_TIME;
    public static final int MONTHS_OF_TIME;
    public static final int YEARS_OF_TIME;

    static {
        FIRST_DAY_OF_TIME = Calendar.getInstance();
        FIRST_DAY_OF_TIME.set(Calendar.getInstance().get(Calendar.YEAR)-100, Calendar.JANUARY, 1);
        LAST_DAY_OF_TIME = Calendar.getInstance();
        LAST_DAY_OF_TIME.set(Calendar.getInstance().get(Calendar.YEAR)+100, Calendar.DECEMBER, 31);
        DAYS_OF_TIME = (int) ((LAST_DAY_OF_TIME.getTimeInMillis() - FIRST_DAY_OF_TIME.getTimeInMillis()) / (24 * 60 * 60 * 1000));
        MONTHS_OF_TIME = (LAST_DAY_OF_TIME.get(Calendar.YEAR) - FIRST_DAY_OF_TIME.get(Calendar.YEAR))*12 + LAST_DAY_OF_TIME.get(Calendar.MONTH) - FIRST_DAY_OF_TIME.get(Calendar.MONTH);
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
        cal.add(Calendar.YEAR,position/12);
        cal.add(Calendar.MONTH, position%12);
        return cal;
    }
    public static Calendar getYearForPosition(int position) throws IllegalArgumentException {
        if (position < 0) {
            throw new IllegalArgumentException("position cannot be negative");
        }
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(FIRST_DAY_OF_TIME.getTimeInMillis());
        cal.add(Calendar.YEAR,position);
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

    public static Calendar getFirstDayOfMonth(Calendar cal)
    {
        cal.set(Calendar.DAY_OF_MONTH, 1);
        return cal;
    }
    public static Calendar getEndDayOfMonth(Calendar cal)
    {
        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH) );
        return cal;
    }
    public static Calendar getFirstMonOfYear(Calendar cal)
    {
        cal.set(Calendar.MONTH, 0);
        return cal;
    }
    public static int getDayOffset(Calendar cal)
    {
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
    public static List<CalendarDto> getDatesFromTime(long timeMillis,int grid_count)
    {
        List<CalendarDto> dtos = new ArrayList<>();
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(timeMillis);
        cal = getFirstDayOfMonth(cal);
        int offset = -getDayOffset(cal);
        cal.add(Calendar.DATE, offset);
        for(int i = 0;i<grid_count;i++)
        {
            CalendarDto dto = new CalendarDto();
            dto.setTimeInMillis(cal.getTimeInMillis());
            cal.add(Calendar.DATE, 1);
            dtos.add(dto);
        }
        return dtos;
    }
    public static List<Long> getTimeForMonth(long timeMillis,int grid_count)
    {
        List<Long> dtos = new ArrayList<>();
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(timeMillis);
        cal = getFirstMonOfYear(cal);
        for(int i = 0;i<grid_count;i++)
        {
            dtos.add(cal.getTimeInMillis());
            cal.add(Calendar.MONTH, 1);
        }
        return dtos;
    }
    public static List<CalendarDto> getListCalForMonth(long timeMillis,int grid_count)
    {
        List<CalendarDto> dtos = new ArrayList<>();
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(timeMillis);
        cal = getFirstDayOfMonth(cal);
        for(int i = 0;i<grid_count;i++)
        {
            CalendarDto dto = new CalendarDto();
            dto.setTimeInMillis(cal.getTimeInMillis());
            cal.add(Calendar.DATE, 1);
            dtos.add(dto);
        }
        return dtos;
    }



    public static int getCurrentMonth(long timeInMilliSecond) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(timeInMilliSecond);
        return cal.get(Calendar.MONTH);
    }

    public static int getTimezoneOffsetInMinutes(){
        TimeZone tz = TimeZone.getDefault();
        int offsetMinutes = tz.getRawOffset() / 60000;
        return offsetMinutes;
    }

    public static boolean isSameDate(long day1,long day2)
    {
        Calendar cal1 = Calendar.getInstance();
        Calendar cal2 = Calendar.getInstance();
        cal1.setTimeInMillis(day1);
        cal2.setTimeInMillis(day2);
        return cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) &&
                cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR);
    }
    public static boolean isSaturday(long millis)
    {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(millis);

        if(cal.get(Calendar.DAY_OF_WEEK)==Calendar.SATURDAY)
        {
            return true;
        }
        return false;
    }
    public static boolean isSunday(long millis)
    {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(millis);
        if(cal.get(Calendar.DAY_OF_WEEK)==Calendar.SUNDAY)
        {
            return true;
        }
        return false;
    }


    public static long getTimeFromString (String timeString){
        long time;
        try {
            String tempTimeString = timeString;
            tempTimeString = tempTimeString.replace("/Date(","");
            tempTimeString = tempTimeString.replace(")/","");
            time = Long.valueOf(tempTimeString);
        }catch (Exception e){
            return 0;
        }
        return time;
    }
    public static int getNumberWeekOfYear (int year){
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, Calendar.DECEMBER);
        cal.set(Calendar.DAY_OF_MONTH, 31);

        int ordinalDay = cal.get(Calendar.DAY_OF_YEAR);
        int weekDay = cal.get(Calendar.DAY_OF_WEEK) - 1; // Sunday = 0
        return (ordinalDay - weekDay + 10) / 7;
    }
}
