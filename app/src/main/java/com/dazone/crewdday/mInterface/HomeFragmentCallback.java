package com.dazone.crewdday.mInterface;

import com.dazone.crewdday.model.DDayModel;

import java.util.ArrayList;

public interface HomeFragmentCallback {
    void addItemToListFirstTime(ArrayList<DDayModel> list);
    void finishAddItem();
    void addMoreItems(ArrayList<DDayModel> list);
    void showNetworkErrorMessage();
    void onFail();
}
