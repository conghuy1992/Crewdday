package com.dazone.crewdday.adapter;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by maidinh on 31/5/2016.
 */
public class NotifyTime implements Parcelable {
    int NotiNo;
    int DayNo;
    int NotificationType;
    String NotificationTime;

    public NotifyTime() {

    }

    public String getNotificationTime() {
        return NotificationTime;
    }

    public void setNotificationTime(String notificationTime) {
        NotificationTime = notificationTime;
    }

    public int getNotiNo() {
        return NotiNo;
    }

    public void setNotiNo(int notiNo) {
        NotiNo = notiNo;
    }

    public int getDayNo() {
        return DayNo;
    }

    public void setDayNo(int dayNo) {
        DayNo = dayNo;
    }

    public int getNotificationType() {
        return NotificationType;
    }

    public void setNotificationType(int notificationType) {
        NotificationType = notificationType;
    }

    protected NotifyTime(Parcel in) {
        NotiNo = in.readInt();
        DayNo = in.readInt();
        NotificationType = in.readInt();
        NotificationTime = in.readString();
    }

    public static final Creator<NotifyTime> CREATOR = new Creator<NotifyTime>() {
        @Override
        public NotifyTime createFromParcel(Parcel in) {
            return new NotifyTime(in);
        }

        @Override
        public NotifyTime[] newArray(int size) {
            return new NotifyTime[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(NotiNo);
        dest.writeInt(DayNo);
        dest.writeInt(NotificationType);
        dest.writeString(NotificationTime);
    }
}
