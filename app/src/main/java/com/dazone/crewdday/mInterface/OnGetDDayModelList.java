package com.dazone.crewdday.mInterface;

import com.dazone.crewdday.model.DDayModel;

import java.util.ArrayList;

/**
 * Created by maidinh on 3/27/2017.
 */

public interface OnGetDDayModelList {
    void onSuccess(ArrayList<DDayModel> list);

    void onFail();
}
