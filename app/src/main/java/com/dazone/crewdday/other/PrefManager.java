package com.dazone.crewdday.other;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by maidinh on 19/7/2016.
 */
public class PrefManager {
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    Context _context;

    // shared pref mode
    int PRIVATE_MODE = 0;

    // Shared preferences file name
    private static final String PREF_NAME = "crewdday-welcome";

    private static final String IS_FIRST_TIME_LAUNCH = "IsFirstTimeLaunch";
    private static final String CHOOSE_CORCERNED = "CHOOSE_CORCERNED";
    private static final String CHECK_LIST_ORGANIZATION_AGAIN = "CHECK_LIST_ORGANIZATION_AGAIN";

    public PrefManager(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }


    public void setRestoreList(boolean isFirstTime) {
        editor.putBoolean(CHECK_LIST_ORGANIZATION_AGAIN, isFirstTime);
        editor.commit();
    }

    public boolean isRestoreList() {
        return pref.getBoolean(CHECK_LIST_ORGANIZATION_AGAIN, false);
    }

    public void setChooseCorcerned(boolean isFirstTime) {
        editor.putBoolean(CHOOSE_CORCERNED, isFirstTime);
        editor.commit();
    }

    public boolean isChooseCorcerne() {
        return pref.getBoolean(CHOOSE_CORCERNED, true);
    }

    public void setFirstTimeLaunch(boolean isFirstTime) {
        editor.putBoolean(IS_FIRST_TIME_LAUNCH, isFirstTime);
        editor.commit();
    }

    public boolean isFirstTimeLaunch() {
        return pref.getBoolean(IS_FIRST_TIME_LAUNCH, true);
    }

}