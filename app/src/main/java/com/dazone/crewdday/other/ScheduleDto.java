package com.dazone.crewdday.other;

/**
 * Created by nguyentiendat on 1/12/16.
 */
public class ScheduleDto extends DataDto {
    private int ScheduleNo;
    private int CalendarType;
    private int CalendarNo;

    private String Title;
    private String CalendarColor;

    private String DivisionNo;

    private String StartTime;
    private String EndTime;


    @Override
    public String getTitle() {
        return Title;
    }

    @Override
    public void setTitle(String title) {
        Title = title;
    }

}
