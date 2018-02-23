package com.dazone.crewdday.mInterface;

import com.dazone.crewdday.adapter.NotifyTime;

import java.util.ArrayList;

/**
 * Created by maidinh on 31/5/2016.
 */
public interface GetNotifyTime {
    void onGetNotifyTimeSuccess(ArrayList<NotifyTime> arr);
}
