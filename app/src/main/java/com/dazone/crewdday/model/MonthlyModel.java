package com.dazone.crewdday.model;

/**
 * Created by DAZONE on 21/03/16.
 */
public class MonthlyModel {


    //First item or not
    int viewType;
    int datePos;
    int weekPos;
    int dayPos;
    boolean doHideAddButton;
    boolean dateChecked;
    String dateString;
    String weekString;
    String dayString;



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


    public boolean isDateChecked() {
        return dateChecked;
    }

    public void setDateChecked(boolean dateChecked) {
        this.dateChecked = dateChecked;
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


    public int getViewType() {
        return viewType;
    }

    public void setViewType(int type) {
        this.viewType = type;
    }
}
