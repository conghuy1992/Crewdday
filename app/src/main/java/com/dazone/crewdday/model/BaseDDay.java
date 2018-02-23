package com.dazone.crewdday.model;

import java.io.Serializable;

/**
 * Created by DAZONE on 14/04/16.
 */
public abstract class BaseDDay implements Serializable {
    protected String startDate;
    protected String endDate;
    protected int interval;
    protected int holiday;

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public int getInterval() {
        return interval;
    }

    public void setInterval(int interval) {
        this.interval = interval;
    }

    public int getHoliday() {
        return holiday;
    }

    public void setHoliday(int holiday) {
        this.holiday = holiday;
    }
}
