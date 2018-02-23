package com.dazone.crewdday.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by maidinh on 3/27/2017.
 */

public class DDayModelList_2 {
    String TitleColor = "";
    List<DDayModel> ListOfDaysForDisplaying = new ArrayList<>();
    String Title = "";

    public String getTitleColor() {
        return TitleColor;
    }

    public void setTitleColor(String titleColor) {
        TitleColor = titleColor;
    }

    public List<DDayModel> getListOfDaysForDisplaying() {
        return ListOfDaysForDisplaying;
    }

    public void setListOfDaysForDisplaying(List<DDayModel> listOfDaysForDisplaying) {
        ListOfDaysForDisplaying = listOfDaysForDisplaying;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }
}
