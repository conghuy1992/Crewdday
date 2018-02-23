package com.dazone.crewdday.util;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;

import com.dazone.crewdday._Application;

/**
 * Created by DAZONE on 25/03/16.
 */
public class Utils {

    public static int getDimenInPx(int id)
    {
        return (int) _Application.getInstance().getApplicationContext().getResources().getDimension(id);
    }

    public static void addFragmentToActivity(FragmentManager fragmentManager, Fragment fragment, int frameLayout, boolean isSaveStack){
        addFragmentToActivity(fragmentManager, fragment, frameLayout, isSaveStack,null);
    }

    public static void addFragmentToActivity(FragmentManager fragmentManager, Fragment fragment, int frameLayout, boolean isSaveStack,String tag){
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        if(TextUtils.isEmpty(tag))
        {
            transaction.add(frameLayout, fragment);
        }
        else {
            transaction.add(frameLayout, fragment, tag);
        }

        if(isSaveStack) {
            transaction.addToBackStack(null);
        }
        transaction.commit();
    }
}
