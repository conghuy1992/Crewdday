package com.dazone.crewdday.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by DAZONE on 18/03/16.
 */
public class SpecialDateModel implements Parcelable{
    private String specialDate;
    private boolean doHideAddButton;
    private boolean doShowDeleteButton;

    public SpecialDateModel(){}

    protected SpecialDateModel(Parcel in) {
        specialDate = in.readString();
        doHideAddButton = in.readByte() != 0;
        doShowDeleteButton = in.readByte() != 0;
    }

    public static final Creator<SpecialDateModel> CREATOR = new Creator<SpecialDateModel>() {
        @Override
        public SpecialDateModel createFromParcel(Parcel in) {
            return new SpecialDateModel(in);
        }

        @Override
        public SpecialDateModel[] newArray(int size) {
            return new SpecialDateModel[size];
        }
    };

    public boolean isdoShowDeleteButton() {
        return doShowDeleteButton;
    }

    public void setDoShowDeleteButton(boolean doShowHideButton) {
        this.doShowDeleteButton = doShowHideButton;
    }

    public boolean isDoHideAddButton() {
        return doHideAddButton;
    }

    public void setDoHideAddButton(boolean doHideAddButton) {
        this.doHideAddButton = doHideAddButton;
    }


    public String getSpecialDate() {
        return specialDate;
    }

    public void setSpecialDate(String specialDate) {
        this.specialDate = specialDate;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(specialDate);
        dest.writeByte((byte) (doHideAddButton ? 1 : 0));
        dest.writeByte((byte) (doShowDeleteButton ? 1 : 0));
    }
}
