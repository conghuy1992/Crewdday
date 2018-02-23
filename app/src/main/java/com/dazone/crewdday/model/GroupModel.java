package com.dazone.crewdday.model;

import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.Comparator;

/**
 * Created by DAZONE on 07/03/16.
 */

public class GroupModel extends Object implements Parcelable, Comparable<GroupModel> {
    long GroupNo;
    int UserNo;
    int TagNo;
    int SortNo;
    String ModDate="";
    String Name="";
    int CountOfDays;

    public GroupModel() {
    }

    private GroupModel(Parcel parcel) {
        GroupNo = parcel.readLong();
        UserNo = parcel.readInt();
        TagNo = parcel.readInt();
        SortNo = parcel.readInt();
        ModDate = parcel.readString();
        Name = parcel.readString();
        CountOfDays = parcel.readInt();
    }

    public int getCountOfDays() {
        return CountOfDays;
    }

    public void setCountOfDays(int countOfDays) {
        this.CountOfDays = countOfDays;
    }

    public long getGroupNo() {
        return GroupNo;
    }

    public void setGroupNo(long groupNo) {
        this.GroupNo = groupNo;
    }

    public int getUserNo() {
        return UserNo;
    }

    public void setUserNo(int userNo) {
        this.UserNo = userNo;
    }

    public int getTagNo() {
        return TagNo;
    }

    public void setTagNo(int tagNo) {
        this.TagNo = tagNo;
    }

    public int getSortNo() {
        return SortNo;
    }

    public void setSortNo(int sortNo) {
        this.SortNo = sortNo;
    }

    public String getModDate() {
        return ModDate;
    }

    public void setModDate(String modDate) {
        this.ModDate = modDate;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        this.Name = name;
    }


    @Override
    public int compareTo(GroupModel another) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            return Long.compare(this.GroupNo, another.GroupNo);
        } else {
            Long long1 = new Long(this.GroupNo);
            Long long2 = new Long(another.GroupNo);
            return long1.compareTo(long2);
        }
    }


    public static final Creator<GroupModel> CREATOR = new Creator<GroupModel>() {
        @Override
        public GroupModel createFromParcel(Parcel parcel) {
            return new GroupModel(parcel);
        }

        @Override
        public GroupModel[] newArray(int size) {
            return new GroupModel[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(GroupNo);
        dest.writeInt(UserNo);
        dest.writeInt(TagNo);
        dest.writeInt(SortNo);
        dest.writeString(ModDate);
        dest.writeString(Name);
        dest.writeInt(CountOfDays);
    }


}

