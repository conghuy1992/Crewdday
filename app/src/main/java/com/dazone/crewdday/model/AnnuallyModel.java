package com.dazone.crewdday.model;

/**
 * Created by DAZONE on 23/03/16.
 */
public class AnnuallyModel {

    boolean doHideAddButton;
    boolean dateChecked;
    int viewType;
    int firstMonthPos;
    int secondMonthPos;
    int datePos;
    int weekPos;
    int dayPos;
    int lunarPos;
    String annuallyRepeat;
    String firstMonthString;
    String secondMonthString;
    String dateString;
    String weekString;
    String dayString;
    String lunarString;

    String annuallySolarRO;
    String annuallyLunarRO;
    String annuallyIntercalaryRO;
    String annuallyWeekRO;

    public int getLunarPos() {
        return lunarPos;
    }

    public void setLunarPos(int lunarPos) {
        this.lunarPos = lunarPos;
    }

    public void setLunarString(String lunarString) {
        this.lunarString = lunarString;
    }

    public String getLunarString() {
        return lunarString;
    }

    public boolean isDateChecked() {
        return dateChecked;
    }

    public void setDateChecked(boolean dateChecked) {
        this.dateChecked = dateChecked;
    }

    public int getFirstMonthPos() {
        return firstMonthPos;
    }

    public void setFirstMonthPos(int firstMonthPos) {
        this.firstMonthPos = firstMonthPos;
    }

    public int getSecondMonthPos() {
        return secondMonthPos;
    }

    public void setSecondMonthPos(int secondMonthPos) {
        this.secondMonthPos = secondMonthPos;
    }

    public int getDatePos() {
        return datePos;
    }

    public void setDatePos(int datePos) {
        this.datePos = datePos;
    }

    public int getWeekPos() {
        return weekPos;
    }

    public void setWeekPos(int weekPos) {
        this.weekPos = weekPos;
    }

    public int getDayPos() {
        return dayPos;
    }

    public void setDayPos(int dayPos) {
        this.dayPos = dayPos;
    }

    public int getViewType() {
        return viewType;
    }

    public void setViewType(int viewType) {
        this.viewType = viewType;
    }

    public String getFirstMonthString() {
        return firstMonthString;
    }

    public void setFirstMonthString(String firstMonthString) {
        this.firstMonthString = firstMonthString;
    }

    public String getSecondMonthString() {
        return secondMonthString;
    }

    public void setSecondMonthString(String secondMonthString) {
        this.secondMonthString = secondMonthString;
    }

    public String getAnnuallyRepeat() {
        return annuallyRepeat;
    }

    public void setAnnuallyRepeat(String annuallyRepeat) {
        this.annuallyRepeat = annuallyRepeat;
    }

    public boolean isDoHideAddButton() {
        return doHideAddButton;
    }

    public void setDoHideAddButton(boolean doHideAddButton) {
        this.doHideAddButton = doHideAddButton;
    }


    public String getDateString() {
        return dateString;
    }

    public void setDateString(String dateString) {
        this.dateString = dateString;
    }

    public String getWeekString() {
        return weekString;
    }

    public void setWeekString(String weekString) {
        this.weekString = weekString;
    }

    public String getDayString() {
        return dayString;
    }

    public void setDayString(String dayString) {
        this.dayString = dayString;
    }
}

