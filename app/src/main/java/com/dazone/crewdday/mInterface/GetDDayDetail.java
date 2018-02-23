package com.dazone.crewdday.mInterface;

import com.dazone.crewdday.model.DDayDetailModel;

/**
 * Created by maidinh on 3/27/2017.
 */

public interface GetDDayDetail {
    void onSuccess(DDayDetailModel ddayDetailModel);

    void onFail();
}
