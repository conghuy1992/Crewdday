package com.dazone.crewdday.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by maidinh on 3/27/2017.
 */

public class DDayModelList {
    String CurrentIndexString = "";
    List<DDayModelList_2> ListOfDateGroupsForDisplaying = new ArrayList<>();

    public String getCurrentIndexString() {
        return CurrentIndexString;
    }

    public void setCurrentIndexString(String currentIndexString) {
        CurrentIndexString = currentIndexString;
    }

    public List<DDayModelList_2> getListOfDateGroupsForDisplaying() {
        return ListOfDateGroupsForDisplaying;
    }

    public void setListOfDateGroupsForDisplaying(List<DDayModelList_2> listOfDateGroupsForDisplaying) {
        ListOfDateGroupsForDisplaying = listOfDateGroupsForDisplaying;
    }
}
