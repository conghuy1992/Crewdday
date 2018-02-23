package com.dazone.crewdday.model;

import java.io.Serializable;

public class DDayModel extends Object implements Serializable {
    String DaysTextColor;
    String sectionTitle;
    long DayNo;
    long GroupNo;
    int TagNo;
    String GroupName;
    String Title;
    int RemainingDays;
    String DDayDate;
    int RegUserNo;
    String RegUserName;
    int CountOfSharers;
    String content;
    String CurrentIndexString;
    int DDayTypeNo;
    int DirectorUserNo;
    String DirectorName;
    boolean CanHide;
    boolean CanManage;
    boolean IsHidden;
    long HiddenDataNo;
    boolean IsCompleted;
    long CompletedRecordNo;
    boolean IsNew;

    public boolean isNew() {
        return IsNew;
    }

    public void setIsNew(boolean isNew) {
        IsNew = isNew;
    }

    public String getDaysTextColor() {
        return DaysTextColor;
    }

    public void setDaysTextColor(String daysTextColor) {
        DaysTextColor = daysTextColor;
    }

    public boolean isHidden() {
        return IsHidden;
    }

    public void setIsHidden(boolean isHidden) {
        IsHidden = isHidden;
    }

    public long getHiddenDataNo() {
        return HiddenDataNo;
    }

    public void setHiddenDataNo(long hiddenDataNo) {
        HiddenDataNo = hiddenDataNo;
    }

    public boolean isCompleted() {
        return IsCompleted;
    }

    public void setIsCompleted(boolean isCompleted) {
        IsCompleted = isCompleted;
    }

    public long getCompletedRecordNo() {
        return CompletedRecordNo;
    }

    public void setCompletedRecordNo(long completedRecordNo) {
        CompletedRecordNo = completedRecordNo;
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

    @Override
    public String toString() {
        return "DDayModel{" +
                "sectionTitle='" + sectionTitle + '\'' +
                ", ddayId=" + DayNo +
                ", GroupNo=" + GroupNo +
                ", TagNo=" + TagNo +
                ", GroupName='" + GroupName + '\'' +
                ", title='" + Title + '\'' +
                ", RemainingDays=" + RemainingDays +
                ", ddayDate='" + DDayDate + '\'' +
                ", RegUserNo=" + RegUserNo +
                ", regUserName='" + RegUserName + '\'' +
                ", CountOfSharers=" + CountOfSharers +
                ", content='" + content + '\'' +
                ", CurrentIndexString='" + CurrentIndexString + '\'' +
                ", DDayTypeNo=" + DDayTypeNo +
                ", DirectorUserNo=" + DirectorUserNo +
                ", directorName='" + DirectorName + '\'' +
                ", DirectorPhotoUrl='" + DirectorPhotoUrl + '\'' +
                '}';
    }

    public String getDirectorName() {
        return DirectorName;
    }

    public void setDirectorName(String directorName) {
        this.DirectorName = directorName;
    }

    String DirectorPhotoUrl;

    public int getDirectorNo() {
        return DirectorUserNo;
    }

    public void setDirectorNo(int directorNo) {
        this.DirectorUserNo = directorNo;
    }

    public String getAvatar() {
        return DirectorPhotoUrl;
    }

    public void setAvatar(String avatar) {
        this.DirectorPhotoUrl = avatar;
    }

    public int getType() {
        return DDayTypeNo;
    }

    public void setType(int type) {
        this.DDayTypeNo = type;
    }

    public String getCurrentIdexString() {
        return CurrentIndexString;
    }

    public void setCurrentIdexString(String currentIdexString) {
        this.CurrentIndexString = currentIdexString;
    }

    public String getSectionTitle() {
        return sectionTitle;
    }

    public void setSectionTitle(String sectionTitle) {
        this.sectionTitle = sectionTitle;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public long getDdayId() {
        return DayNo;
    }

    public void setDdayId(long ddayId) {
        this.DayNo = ddayId;
    }

    public long getGroupId() {
        return GroupNo;
    }

    public void setGroupId(long groupId) {
        this.GroupNo = groupId;
    }

    public int getTagId() {
        return TagNo;
    }

    public void setTagId(int tagId) {
        this.TagNo = tagId;
    }

    public String getGroupName() {
        return GroupName;
    }

    public void setGroupName(String groupName) {
        this.GroupName = groupName;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        this.Title = title;
    }

    public int getRemainingDays() {
        return RemainingDays;
    }

    public void setRemainingDays(int remainingDays) {
        this.RemainingDays = remainingDays;
    }

    public String getDdayDate() {
        return DDayDate;
    }

    public void setDdayDate(String ddayDate) {
        this.DDayDate = ddayDate;
    }

    public int getRegUserId() {
        return RegUserNo;
    }

    public void setRegUserId(int regUserId) {
        this.RegUserNo = regUserId;
    }

    public String getRegUserName() {
        return RegUserName;
    }

    public void setRegUserName(String regUserName) {
        this.RegUserName = regUserName;
    }

    public int getCountOfSharers() {
        return CountOfSharers;
    }

    public void setCountOfSharers(int countOfSharers) {
        this.CountOfSharers = countOfSharers;
    }
}
