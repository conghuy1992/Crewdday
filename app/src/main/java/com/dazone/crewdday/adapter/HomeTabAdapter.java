package com.dazone.crewdday.adapter;

/**
 * Created by DAZONE on 02/03/16.
 */

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.dazone.crewdday.fragment.FragmentHomeAll;
import com.dazone.crewdday.fragment.FragmentHomePlus;

public class HomeTabAdapter extends FragmentAdvanceStatePagerAdapter {
    int mNumOfTabs;
    int currentIndex;



    public HomeTabAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;

    }

    @Override
    public Fragment getFragmentItem(int position) {
        switch (position) {
            case 0:
                FragmentHomeAll fragmentHomeMinus = new FragmentHomeAll();
                return fragmentHomeMinus;
            case 1:
                FragmentHomePlus fragmentHomePlus = new FragmentHomePlus();
                return fragmentHomePlus;
            default:
                return null;
        }
    }

    @Override
    public void updateFragmentItem(int position, Fragment fragment) {
        switch (position) {
            case 0:
                FragmentHomeAll fragmentHomeMinus = (FragmentHomeAll) fragment;
                fragmentHomeMinus.refreshList();
                break;
            case 1:
                FragmentHomePlus fragmentHomePlus = (FragmentHomePlus) fragment;
                fragmentHomePlus.refreshList();
                break;
        }
    }


    @Override
    public int getCount() {
        return mNumOfTabs;
    }


}