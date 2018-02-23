package com.dazone.crewdday;

import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.dazone.crewdday.adapter.DDayMonthlyListAdapter;
import com.dazone.crewdday.mInterface.DatePickerDialogListener;
import com.dazone.crewdday.mInterface.UpdateListValueCallback;
import com.dazone.crewdday.util.DialogUtils;
import com.dazone.crewdday.util.LoadArrayUtils;
import com.dazone.crewdday.util.PreferenceUtilities;
import com.dazone.crewdday.viewholder.BaseAddNewViewHolder;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by DAZONE on 30/03/16.
 */
public class OptionDiaglogManager extends BaseAddNewViewHolder implements DatePickerDialogListener {
    String TAG = "OptionDiaglogManager";
    private RelativeLayout mContainer;
    private TextView mTv;
    private PreferenceUtilities mPref;
    private UpdateListValueCallback callback;
    private int mType;
    DDayMonthlyListAdapter monthlyListAdapter;


    public OptionDiaglogManager(View v, boolean endTimeExisted, int optionType, String repeatType, String dialogTitle,
                                int containterResourceId, int tvResoureId, String[] array) {
        super(v, endTimeExisted, optionType, repeatType, dialogTitle, containterResourceId, tvResoureId, array);
    }

    public void setmType(int mType) {
        this.mType = mType;
    }

    @Override
    public void setup(View v, boolean endTimeExisted, final int optionType, final String repeatType, final String dialogTitle,
                      int containterResourceId, int tvResoureId, final String[] array) {
        monthlyListAdapter = new DDayMonthlyListAdapter();
        mPref = _Application.getInstance().getPreferenceUtilities();
        mContainer = (RelativeLayout) v.findViewById(containterResourceId);
        mTv = (TextView) v.findViewById(tvResoureId);


        mContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //User for Interval,Starttype,Holiday
                if (optionType != Cons.NO_VALUES) {
                    DialogUtils.openOptionDialog(mContainer.getContext(), optionType, dialogTitle, array, repeatType, OptionDiaglogManager.this);
                }
                //Use for others
                else {
                    DialogUtils.openOptionDialog(v, mContainer.getContext(), dialogTitle, array, repeatType, OptionDiaglogManager.this, mType);
                }
            }
        });
    }


    @Override
    public void onFinishEditDialog(String text, String[] array, int optionType, String repeatType) {

        mTv.setText(text);
        int positon = LoadArrayUtils.getItemPosition(text, array);

        if (repeatType != null) {
            switch (repeatType) {
                case Cons.FRAGMENT_TYPE_DAILY:
                    if (optionType == Cons.INTERVAL) {
                        mPref.setDailyIntervalText(text);
                        mPref.setDailyIntervalValue(String.valueOf(positon));
                    } else if (optionType == Cons.OPTION) {
                        mPref.setDailyStartTypeText(text);
                        mPref.setDailyOptionPos(String.valueOf(positon));
                    } else {
                        mPref.setDailyHolidayTypeText(text);
                        mPref.setDailyHolidayPos(String.valueOf(positon));
                    }
                    break;
                case Cons.FRAGMENT_TYPE_WEEKLY:
                    if (optionType == Cons.INTERVAL) {
                        mPref.setWeeklyIntervalText(text);
                        mPref.setWeeklyIntervalPos(String.valueOf(positon));
                    } else {
                        mPref.setWeeklyHolidayText(text);
                        mPref.setWeeklyHolidayPos(String.valueOf(positon));
                    }
                    break;
                case Cons.FRAGMENT_TYPE_MONTHLY:
                    if (optionType == Cons.INTERVAL) {
                        mPref.setMonthlyIntervalText(text);
                        mPref.setMonthlyIntervalPos(String.valueOf(positon));
                    } else {
                        mPref.setMonthlyHolidayText(text);
                        mPref.setMonthlyHolidayPos(String.valueOf(positon));
                    }
                    break;
                case Cons.FRAGMENT_TYPE_ANNUALLY:
                    if (optionType == Cons.INTERVAL) {
                        mPref.setAnnuallyIntervalText(text);
                        mPref.setAnnuallyIntervalPos(String.valueOf(positon));
                    } else {
                        mPref.setAnnuallyHolidayText(text);
                        mPref.setAnnuallyHolidayPos(String.valueOf(positon));
                    }
                    break;
                case Cons.SELECT_GROUP:
                    mPref.setGroupPos(String.valueOf(positon));
                    break;
            }
        }
    }


    @Override
    public void onFinishEditDialog(View view, String text, String[] array, String repeatType, int type) {
        int position = LoadArrayUtils.getItemPosition(text, array);
        Gson gson = new Gson();
        String json = mPref.getMonthlyPositionAdded();
        Type type1 = new TypeToken<ArrayList<String>>() {
        }.getType();
        ArrayList<String> dateAdded = gson.fromJson(json, type1);

        int id = view.getId();

        if (type == Cons.MONTHLY) {
            switch (id) {
                case R.id.date_container:
                    if (!dateAdded.contains(String.valueOf(position))) {
                        callback.updateValue(text, position, Cons.DATE);
                        mTv.setText(text);
                    } else {
                        Toast.makeText(mContainer.getContext(), "This date already existed", Toast.LENGTH_LONG).show();
                    }
                    break;
                case R.id.week_container:
                    callback.updateValue(text, position, Cons.WEEK);
                    mTv.setText(text);
                    break;
                case R.id.day_container:
                    callback.updateValue(text, position, Cons.DAY);
                    mTv.setText(text);
                    break;
            }
        } else {
            switch (id) {
                case R.id.first_month_container:
                    callback.updateValue(text, position, Cons.FIRST_MONTH);
                    mTv.setText(text);
                    break;
                case R.id.second_month_container:
                    callback.updateValue(text, position, Cons.SECOND_MONTH);
                    mTv.setText(text);
                    break;
                case R.id.date_container:
                    //This is "Last day"
                    callback.updateValue(text, position, Cons.DATE);
                    mTv.setText(text);
                    break;
                case R.id.week_container:
                    callback.updateValue(text, position, Cons.WEEK);
                    mTv.setText(text);
                    break;
                case R.id.calendar_container:
                    callback.updateValue(text, position, Cons.CALENDAR);
                    mTv.setText(text);
                    break;
                case R.id.day_container:
                    callback.updateValue(text, position, Cons.DAY);
                    mTv.setText(text);
                    break;
            }
        }


    }

    @Override
    public void onFinishEditDialog(Calendar mDate, int type, String repeatType) {

    }


    public void setUpdateValueCallback(UpdateListValueCallback callback) {
        this.callback = callback;
    }
}
