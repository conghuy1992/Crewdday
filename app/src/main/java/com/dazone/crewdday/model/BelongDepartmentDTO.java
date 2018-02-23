package com.dazone.crewdday.model;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class BelongDepartmentDTO implements Serializable {
    @SerializedName("UserNo")
    private int UserNo;
    @SerializedName("DepartNo")
    private int DepartNo;
    @SerializedName("PositionNo")
    private int PositionNo;
    @SerializedName("DutyNo")
    private int DutyNo;
    @SerializedName("IsDefault")
    private boolean IsDefault;
    @SerializedName("DepartName")
    private String DepartName;
    @SerializedName("DepartSortNo")
    private int DepartSortNo;
    @SerializedName("PositionName")
    private String PositionName;
    @SerializedName("PositionSortNo")
    private int PositionSortNo;
    @SerializedName("DutyName")
    private String DutyName;
    @SerializedName("DutySortNo")
    private int DutySortNo;

    @Override
    public String toString() {
        return "BelongDepartmentDTO{" +
                "UserNo=" + UserNo +
                ", DepartNo=" + DepartNo +
                ", PositionNo=" + PositionNo +
                ", DutyNo=" + DutyNo +
                ", IsDefault=" + IsDefault +
                ", DepartName='" + DepartName + '\'' +
                ", DepartSortNo=" + DepartSortNo +
                ", PositionName='" + PositionName + '\'' +
                ", PositionSortNo=" + PositionSortNo +
                ", DutyName='" + DutyName + '\'' +
                ", DutySortNo=" + DutySortNo +
                '}';
    }

    public int getUserNo() {
        return UserNo;
    }

    public void setUserNo(int userNo) {
        UserNo = userNo;
    }

    public int getDepartNo() {
        return DepartNo;
    }

    public void setDepartNo(int departNo) {
        DepartNo = departNo;
    }

    public int getPositionNo() {
        return PositionNo;
    }

    public void setPositionNo(int positionNo) {
        PositionNo = positionNo;
    }

    public int getDutyNo() {
        return DutyNo;
    }

    public void setDutyNo(int dutyNo) {
        DutyNo = dutyNo;
    }

    public boolean isDefault() {
        return IsDefault;
    }

    public void setIsDefault(boolean isDefault) {
        IsDefault = isDefault;
    }

    public String getDepartName() {
        return DepartName;
    }

    public void setDepartName(String departName) {
        DepartName = departName;
    }

    public int getDepartSortNo() {
        return DepartSortNo;
    }

    public void setDepartSortNo(int departSortNo) {
        DepartSortNo = departSortNo;
    }

    public String getPositionName() {
        return PositionName;
    }

    public void setPositionName(String positionName) {
        PositionName = positionName;
    }

    public int getPositionSortNo() {
        return PositionSortNo;
    }

    public void setPositionSortNo(int positionSortNo) {
        PositionSortNo = positionSortNo;
    }

    public String getDutyName() {
        return DutyName;
    }

    public void setDutyName(String dutyName) {
        DutyName = dutyName;
    }

    public int getDutySortNo() {
        return DutySortNo;
    }

    public void setDutySortNo(int dutySortNo) {
        DutySortNo = dutySortNo;
    }
}
