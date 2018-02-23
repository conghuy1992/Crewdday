package com.dazone.crewdday.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by maidinh on 3/27/2017.
 */

public class DDayDetalMode {
    int DayNo = 0;
    String Title = "";
    String Content = "";
    int DDayTypeNo = 0;
    String DDayType = "";
    String RepeatOptions = "";
    String Repeat = "";
    int GroupNo = 0;
    int TagNo = 0;
    String GroupName = "";
    int RegUserNo = 0;
    String RegUserName = "";
    int DepartNo = 0;
    String DepartName = "";
    int PositionNo = 0;
    String PositionName = "";
    int DutyNo = 0;
    String DutyName = "";
    int UserNoOfDirector = 0;
    String UserNameOfDirector = "";
    int DepartNoOfDirector = 0;
    String DepartNameOfDirector = "";
    int PositionNoOfDirector = 0;
    String PositionNameOfDirector = "";
    int DutyNoOfDirector = 0;
    String DutyNameOfDirector = "";
    List<DDayDetalModeShare> Sharers = new ArrayList<>();
    List<DDayDetalModeShare> Managers = new ArrayList<>();
    boolean CanEdit = false;
    boolean CanEditRepeatOptions = false;
    boolean CanEditDdayType = false;
    boolean CanHide = false;
    boolean CanManage = false;

    public int getDayNo() {
        return DayNo;
    }

    public void setDayNo(int dayNo) {
        DayNo = dayNo;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }

    public int getDDayTypeNo() {
        return DDayTypeNo;
    }

    public void setDDayTypeNo(int DDayTypeNo) {
        this.DDayTypeNo = DDayTypeNo;
    }

    public String getDDayType() {
        return DDayType;
    }

    public void setDDayType(String DDayType) {
        this.DDayType = DDayType;
    }

    public String getRepeatOptions() {
        return RepeatOptions;
    }

    public void setRepeatOptions(String repeatOptions) {
        RepeatOptions = repeatOptions;
    }

    public String getRepeat() {
        return Repeat;
    }

    public void setRepeat(String repeat) {
        Repeat = repeat;
    }

    public int getGroupNo() {
        return GroupNo;
    }

    public void setGroupNo(int groupNo) {
        GroupNo = groupNo;
    }

    public int getTagNo() {
        return TagNo;
    }

    public void setTagNo(int tagNo) {
        TagNo = tagNo;
    }

    public String getGroupName() {
        return GroupName;
    }

    public void setGroupName(String groupName) {
        GroupName = groupName;
    }

    public int getRegUserNo() {
        return RegUserNo;
    }

    public void setRegUserNo(int regUserNo) {
        RegUserNo = regUserNo;
    }

    public String getRegUserName() {
        return RegUserName;
    }

    public void setRegUserName(String regUserName) {
        RegUserName = regUserName;
    }

    public int getDepartNo() {
        return DepartNo;
    }

    public void setDepartNo(int departNo) {
        DepartNo = departNo;
    }

    public String getDepartName() {
        return DepartName;
    }

    public void setDepartName(String departName) {
        DepartName = departName;
    }

    public int getPositionNo() {
        return PositionNo;
    }

    public void setPositionNo(int positionNo) {
        PositionNo = positionNo;
    }

    public String getPositionName() {
        return PositionName;
    }

    public void setPositionName(String positionName) {
        PositionName = positionName;
    }

    public int getDutyNo() {
        return DutyNo;
    }

    public void setDutyNo(int dutyNo) {
        DutyNo = dutyNo;
    }

    public String getDutyName() {
        return DutyName;
    }

    public void setDutyName(String dutyName) {
        DutyName = dutyName;
    }

    public int getUserNoOfDirector() {
        return UserNoOfDirector;
    }

    public void setUserNoOfDirector(int userNoOfDirector) {
        UserNoOfDirector = userNoOfDirector;
    }

    public String getUserNameOfDirector() {
        return UserNameOfDirector;
    }

    public void setUserNameOfDirector(String userNameOfDirector) {
        UserNameOfDirector = userNameOfDirector;
    }

    public int getDepartNoOfDirector() {
        return DepartNoOfDirector;
    }

    public void setDepartNoOfDirector(int departNoOfDirector) {
        DepartNoOfDirector = departNoOfDirector;
    }

    public String getDepartNameOfDirector() {
        return DepartNameOfDirector;
    }

    public void setDepartNameOfDirector(String departNameOfDirector) {
        DepartNameOfDirector = departNameOfDirector;
    }

    public int getPositionNoOfDirector() {
        return PositionNoOfDirector;
    }

    public void setPositionNoOfDirector(int positionNoOfDirector) {
        PositionNoOfDirector = positionNoOfDirector;
    }

    public String getPositionNameOfDirector() {
        return PositionNameOfDirector;
    }

    public void setPositionNameOfDirector(String positionNameOfDirector) {
        PositionNameOfDirector = positionNameOfDirector;
    }

    public int getDutyNoOfDirector() {
        return DutyNoOfDirector;
    }

    public void setDutyNoOfDirector(int dutyNoOfDirector) {
        DutyNoOfDirector = dutyNoOfDirector;
    }

    public String getDutyNameOfDirector() {
        return DutyNameOfDirector;
    }

    public void setDutyNameOfDirector(String dutyNameOfDirector) {
        DutyNameOfDirector = dutyNameOfDirector;
    }

    public List<DDayDetalModeShare> getSharers() {
        return Sharers;
    }

    public void setSharers(List<DDayDetalModeShare> sharers) {
        Sharers = sharers;
    }

    public List<DDayDetalModeShare> getManagers() {
        return Managers;
    }

    public void setManagers(List<DDayDetalModeShare> managers) {
        Managers = managers;
    }

    public boolean isCanEdit() {
        return CanEdit;
    }

    public void setCanEdit(boolean canEdit) {
        CanEdit = canEdit;
    }

    public boolean isCanEditRepeatOptions() {
        return CanEditRepeatOptions;
    }

    public void setCanEditRepeatOptions(boolean canEditRepeatOptions) {
        CanEditRepeatOptions = canEditRepeatOptions;
    }

    public boolean isCanEditDdayType() {
        return CanEditDdayType;
    }

    public void setCanEditDdayType(boolean canEditDdayType) {
        CanEditDdayType = canEditDdayType;
    }

    public boolean isCanHide() {
        return CanHide;
    }

    public void setCanHide(boolean canHide) {
        CanHide = canHide;
    }

    public boolean isCanManage() {
        return CanManage;
    }

    public void setCanManage(boolean canManage) {
        CanManage = canManage;
    }
}

