package com.dazone.crewdday.other;


import java.util.ArrayList;

/**
 * Created by THANHTUNG on 23/12/2015.
 */
public interface OnGetAllOfUser {
    void onGetAllOfUserSuccess(ArrayList<PersonData> list);
    void onGetAllOfUserFail(ErrorDto errorData);
}
