package com.dazone.crewdday.mInterface;

import com.dazone.crewdday.model.ObjectGetGroups;

import java.util.List;

/**
 * Created by maidinh on 10/5/2016.
 */
public interface GetGroups {
    void onGetGroupSuccess(List<ObjectGetGroups> listDtos);
}
