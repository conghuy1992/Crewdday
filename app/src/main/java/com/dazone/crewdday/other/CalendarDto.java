package com.dazone.crewdday.other;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by david on 12/23/15.
 */
public class CalendarDto  extends DataDto{

    private long timeInMillis;

    private List<ScheduleDto> scheduleDtos;

    public CalendarDto() {
        scheduleDtos = new ArrayList<>();
        timeInMillis = 0;
    }

    public List<ScheduleDto> getScheduleDtos() {
        return scheduleDtos;
    }

    public void setScheduleDtos(List<ScheduleDto> scheduleDtos) {
        this.scheduleDtos = scheduleDtos;
    }

    public int getCurrentMonth() {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(timeInMillis);
        return cal.get(Calendar.MONTH);
    }

    public long getTimeInMillis() {
        return timeInMillis;
    }

    public void setTimeInMillis(long timeInMillis) {
        this.timeInMillis = timeInMillis;
    }
}
