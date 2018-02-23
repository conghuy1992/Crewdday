package com.dazone.crewdday.helper;

import java.util.ArrayList;

/**
 * Created by DAZONE on 07/04/16.
 */
public class MonthlyHelper {
    int currentPosition;
    ArrayList<String> dateAdded;

    public int getCurrentPosition() {
        return currentPosition;
    }

    public void setCurrentPosition(int currentPosition) {
        this.currentPosition = currentPosition;
    }

    public ArrayList<String> getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(ArrayList<String> dateAdded) {
        this.dateAdded = dateAdded;
    }
}
