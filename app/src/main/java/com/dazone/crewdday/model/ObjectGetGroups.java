package com.dazone.crewdday.model;

/**
 * Created by maidinh on 10/5/2016.
 */
public class ObjectGetGroups {
    int GroupNo;
    int UserNo;
    String ModDate;
    int TagNo;
    String Name;
    int CountOfDays;
    int SortNo;

    public int getGroupNo() {
        return GroupNo;
    }

    public void setGroupNo(int groupNo) {
        GroupNo = groupNo;
    }

    public int getUserNo() {
        return UserNo;
    }

    public void setUserNo(int userNo) {
        UserNo = userNo;
    }

    public String getModDate() {
        return ModDate;
    }

    public void setModDate(String modDate) {
        ModDate = modDate;
    }

    public int getTagNo() {
        return TagNo;
    }

    public void setTagNo(int tagNo) {
        TagNo = tagNo;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public int getCountOfDays() {
        return CountOfDays;
    }

    public void setCountOfDays(int countOfDays) {
        CountOfDays = countOfDays;
    }

    public int getSortNo() {
        return SortNo;
    }

    public void setSortNo(int sortNo) {
        SortNo = sortNo;
    }
}
