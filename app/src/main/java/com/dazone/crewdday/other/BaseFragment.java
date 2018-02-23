package com.dazone.crewdday.other;

import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;

import com.dazone.crewdday.R;


/**
 * Created by david on 1/8/16.
 */
public class BaseFragment extends Fragment {


    protected Toolbar toolbar;

    protected void setupToolBar(int custom_layout_id) {
        toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
        if (toolbar == null)
            return;
        LinearLayout main_Custom_toolbar = (LinearLayout) toolbar.findViewById(R.id.main_Custom_toolbar);
        View.inflate(getContext(), custom_layout_id, main_Custom_toolbar);
    }
}
