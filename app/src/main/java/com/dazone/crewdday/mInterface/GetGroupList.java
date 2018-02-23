package com.dazone.crewdday.mInterface;

import com.dazone.crewdday.model.GroupModel;

import java.util.ArrayList;


/**
 * Created by maidinh on 3/27/2017.
 */

public interface GetGroupList {
    void onSuccess(ArrayList<GroupModel> listGroup);

    void onFail();
}
