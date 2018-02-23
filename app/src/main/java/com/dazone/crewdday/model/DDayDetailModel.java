package com.dazone.crewdday.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by DAZONE on 07/03/16.
 */
public class DDayDetailModel implements Parcelable {
    long ddayId;
    boolean canEdit;
    boolean CanManage;
    boolean CanHide;
    int directorNo;
    int directorDepartNo;
    String title;
    String content;
    String ddayType;
    String groupName;
    int RegUserNo;
    String writer;
    String typeOfRepeat;
    String startDate;
    String endDate;
    String directorUsername;
    String repeatString;
    String stringRepeatOptions;
    ArrayList<Employee> managersList;
    ArrayList<Employee> sharersList;

    public boolean isCanHide() {
        return CanHide;
    }

    public void setCanHide(boolean canHide) {
        this.CanHide = canHide;
    }

    public boolean isCanManage() {
        return CanManage;
    }

    public void setCanManage(boolean canManage) {
        CanManage = canManage;
    }

    public int getDirectorDepartNo() {
        return directorDepartNo;
    }

    public void setDirectorDepartNo(int directorDepartNo) {
        this.directorDepartNo = directorDepartNo;
    }

    public int getDirectorNo() {
        return directorNo;
    }

    public void setDirectorNo(int directorNo) {
        this.directorNo = directorNo;
    }

    public static final Creator<DDayDetailModel> CREATOR = new Creator<DDayDetailModel>() {
        @Override
        public DDayDetailModel createFromParcel(Parcel in) {
            return new DDayDetailModel(in);
        }

        @Override
        public DDayDetailModel[] newArray(int size) {
            return new DDayDetailModel[size];
        }
    };

    public long getDdayId() {
        return ddayId;
    }

    public void setDdayId(long ddayId) {
        this.ddayId = ddayId;
    }

    public String getRepeatString() {
        return repeatString;
    }

    public void setRepeatString(String repeatString) {
        this.repeatString = repeatString;
    }


    public ArrayList<Employee> getManagersList() {
        return managersList;
    }


    public ArrayList<Employee> getSharersList() {
        return sharersList;
    }

    public void setSharersList(ArrayList<Employee> sharersList) {
        this.sharersList = sharersList;
    }

    public void setManagersList(ArrayList<Employee> managersList) {
        this.managersList = managersList;
    }

    public String getTypeOfRepeat() {
        return typeOfRepeat;
    }

    public void setTypeOfRepeat(String typeOfRepeat) {
        this.typeOfRepeat = typeOfRepeat;
    }

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

    public String getDirectorUsername() {
        return directorUsername;
    }

    public void setDirectorUsername(String directorUsername) {
        this.directorUsername = directorUsername;
    }

    public boolean isCanEdit() {
        return canEdit;
    }

    public void setCanEdit(boolean canEdit) {
        this.canEdit = canEdit;
    }

    public String getDdayType() {
        return ddayType;
    }

    public void setDdayType(String ddayType) {
        this.ddayType = ddayType;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getWriter() {
        return writer;
    }

    public int getRegUserNo() {
        return RegUserNo;
    }

    public void setRegUserNo(int regUserNo) {
        RegUserNo = regUserNo;
    }

    public void setWriter(String writer) {
        this.writer = writer;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }


    public String getStringRepeatOptions() {
        return stringRepeatOptions;
    }

    public void setStringRepeatOptions(String stringRepeatOptions) {
        this.stringRepeatOptions = stringRepeatOptions;
    }

    @Override
    public int describeContents() {
        return 0;
    }


    public DDayDetailModel() {
    }

    protected DDayDetailModel(Parcel in) {
        ddayId = in.readLong();
        directorNo = in.readInt();
        directorDepartNo = in.readInt();
        RegUserNo= in.readInt();
        title = in.readString();
        content = in.readString();
        ddayType = in.readString();
        groupName = in.readString();
        writer = in.readString();
        typeOfRepeat = in.readString();
        startDate = in.readString();
        endDate = in.readString();
        directorUsername = in.readString();
        repeatString = in.readString();
        stringRepeatOptions = in.readString();
        canEdit = in.readByte() != 0;
        CanManage = in.readByte() != 0;
        CanHide= in.readByte() != 0;
        managersList = in.readArrayList(null);
        sharersList = in.readArrayList(null);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(ddayId);
        dest.writeInt(directorNo);
        dest.writeInt(directorDepartNo);
        dest.writeInt(RegUserNo);

        dest.writeString(title);
        dest.writeString(content);
        dest.writeString(ddayType);
        dest.writeString(groupName);
        dest.writeString(writer);
        dest.writeString(typeOfRepeat);
        dest.writeString(startDate);
        dest.writeString(endDate);
        dest.writeString(directorUsername);
        dest.writeString(repeatString);
        dest.writeString(stringRepeatOptions);
        dest.writeByte((byte) (canEdit ? 1 : 0));
        dest.writeByte((byte) (CanManage ? 1 : 0));
        dest.writeByte((byte) (CanHide ? 1 : 0));

        dest.writeList(managersList);
        dest.writeList(sharersList);

    }
}
