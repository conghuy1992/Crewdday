package com.dazone.crewdday.mInterface;

import com.dazone.crewdday.model.DDayDetailModel;

public interface DetailViewCallback {
    void intializeDdayDetail(DDayDetailModel dDayDetailModel);
    void updateUI();
    void onError();
}
