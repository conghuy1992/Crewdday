package com.dazone.crewdday.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.dazone.crewdday.fragment.FragmentHomeAll;
import com.dazone.crewdday.fragment.FragmentHomePlus;

public class IntroPageAdapter extends FragmentAdvanceStatePagerAdapter {
    int mNumOfTabs;
    int currentIndex;

    public IntroPageAdapter(FragmentManager fm, int NumOfTabs) {
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

    }


    @Override
    public int getCount() {
        return mNumOfTabs;
    }


}