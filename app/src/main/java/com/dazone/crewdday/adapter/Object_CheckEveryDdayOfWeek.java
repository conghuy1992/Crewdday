package com.dazone.crewdday.adapter;

/**
 * Created by maidinh on 16/5/2016.
 */
public class Object_CheckEveryDdayOfWeek {
    public String TypeName;
    public String StartDate;
    public String EndDate;
    public String SpecificDayOfWeek;
    public int HolidayCondition;
    public int Interval;

    public int getInterval() {
        return Interval;
    }

    public void setInterval(int interval) {
        Interval = interval;
    }

    public String getTypeName() {
        return TypeName;
    }

    public void setTypeName(String typeName) {
        TypeName = typeName;
    }

    public String getStartDate() {
        return StartDate;
    }

    public void setStartDate(String startDate) {
        StartDate = startDate;
    }

    public String getEndDate() {
        return EndDate;
    }

    public void setEndDate(String endDate) {
        EndDate = endDate;
    }


    public String getSpecificDayOfWeek() {
        return SpecificDayOfWeek;
    }

    public void setSpecificDayOfWeek(String specificDayOfWeek) {
        SpecificDayOfWeek = specificDayOfWeek;
    }

    public int getHolidayCondition() {
        return HolidayCondition;
    }

    public void setHolidayCondition(int holidayCondition) {
        HolidayCondition = holidayCondition;
    }
}
