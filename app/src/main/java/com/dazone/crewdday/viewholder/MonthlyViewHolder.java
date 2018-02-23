package com.dazone.crewdday.viewholder;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dazone.crewdday.Cons;
import com.dazone.crewdday.OptionDiaglogManager;
import com.dazone.crewdday.R;
import com.dazone.crewdday._Application;
import com.dazone.crewdday.adapter.DDayMonthlyListAdapter;
import com.dazone.crewdday.mInterface.UpdateListValueCallback;
import com.dazone.crewdday.mInterface.UpdateStringROCallback;
import com.dazone.crewdday.model.MonthlyModel;
import com.dazone.crewdday.util.LoadArrayUtils;
import com.dazone.crewdday.util.PreferenceUtilities;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * Created by DAZONE on 23/03/16.
 */
public class MonthlyViewHolder extends RecyclerView.ViewHolder implements UpdateListValueCallback, View.OnClickListener {


    public RadioButton rbDate;
    public RadioButton rbWeekAndDay;
    public TextView tvDate;
    public TextView tvWeek;
    public TextView tvDay;
    public RelativeLayout dateContainer;
    public RelativeLayout weekContainer;
    public RelativeLayout dayContainer;
    public ImageView ivAdd;
    public ImageView ivDelete;
    private DDayMonthlyListAdapter mAdapter;
    private PreferenceUtilities mPref;

    String[] dateArray;
    String[] weekArray;
    String[] dayArray;

    int interval;
    String startDate;
    String endDate;
    int holiday;

    private ArrayList<MonthlyModel> list;

    public MonthlyViewHolder(Context context, View itemView, ArrayList<MonthlyModel> list, DDayMonthlyListAdapter adapter) {
        super(itemView);
        this.mAdapter = adapter;
        this.mPref = _Application.getInstance().getPreferenceUtilities();
        this.list = list;

        rbDate = (RadioButton) itemView.findViewById(R.id.rb_date);
        rbWeekAndDay = (RadioButton) itemView.findViewById(R.id.rb_week_and_day);

        tvDate = (TextView) itemView.findViewById(R.id.txt_date);
        tvWeek = (TextView) itemView.findViewById(R.id.txt_week);
        tvDay = (TextView) itemView.findViewById(R.id.txt_day);

        dateContainer = (RelativeLayout) itemView.findViewById(R.id.date_container);
        weekContainer = (RelativeLayout) itemView.findViewById(R.id.week_container);
        dayContainer = (RelativeLayout) itemView.findViewById(R.id.day_container);

        ivAdd = (ImageView) itemView.findViewById(R.id.img_add_items);
        ivDelete = (ImageView) itemView.findViewById(R.id.img_delete_items);

        rbDate.setOnClickListener(this);
        rbWeekAndDay.setOnClickListener(this);

        itemView.setTag(itemView);

        bindData(context, itemView);
    }


    public void setDateArray(String[] dateArray) {
        this.dateArray = dateArray;
    }

    public void setWeekArray(String[] weekArray) {
        this.weekArray = weekArray;
    }

    public void setDayArray(String[] dayArray) {
        this.dayArray = dayArray;
    }


    private void bindData(Context context, View view) {


        dateArray = context.getResources().getStringArray(R.array.monthly_date_array);
        weekArray = context.getResources().getStringArray(R.array.monthly_week_array);
        dayArray = context.getResources().getStringArray(R.array.monthly_day_array);


        OptionDiaglogManager date = new OptionDiaglogManager(view, false, Cons.NO_VALUES,
                Cons.FRAGMENT_TYPE_MONTHLY, Cons.SELECT_DATE,
                R.id.date_container, R.id.txt_date, dateArray);
        date.setUpdateValueCallback(this);
        date.setmType(Cons.MONTHLY);

        OptionDiaglogManager week = new OptionDiaglogManager(view, false, Cons.NO_VALUES,
                Cons.FRAGMENT_TYPE_MONTHLY, Cons.SELECT_WEEK,
                R.id.week_container, R.id.txt_week, weekArray);
        week.setUpdateValueCallback(this);
        week.setmType(Cons.MONTHLY);

        OptionDiaglogManager day = new OptionDiaglogManager(view, false, Cons.NO_VALUES,
                Cons.FRAGMENT_TYPE_MONTHLY, Cons.SELECT_DAY,
                R.id.day_container, R.id.txt_day, dayArray);
        day.setUpdateValueCallback(this);
        day.setmType(Cons.MONTHLY);


    }

    @Override
    public void updateValue(String text, int position, int type) {

        interval = Integer.parseInt(mPref.getMonthlyIntervalPos()) + 1;
        holiday = Integer.parseInt(mPref.getMonthlyHolidayPos()) + 1;
        startDate = mPref.getMonthlyStartDate();
        endDate = mPref.getMonthlyEndDate();

        int itemPosition = getAdapterPosition();
        MonthlyModel monthlyModel = list.get(itemPosition);


        switch (type) {
            case Cons.DATE:

                Gson gson = new Gson();
                String json = mPref.getMonthlyPositionAdded();
                Type type1 = new TypeToken<ArrayList<String>>() {
                }.getType();
                ArrayList<String> dateAdded = gson.fromJson(json, type1);

                dateAdded.remove(String.valueOf(monthlyModel.getDatePos()));
                dateAdded.add(String.valueOf(position));

                //just change the next item by last item position
                if (itemPosition == list.size() - 1) {
                    mAdapter.setCurrentDatePosition(position);
                }

                monthlyModel.setDateString(text);
                monthlyModel.setDatePos(position);
                monthlyModel.setDateChecked(true);


                mAdapter.setDateAdded(dateAdded);
                mAdapter.notifyDataSetChanged();
                break;

            case Cons.WEEK:

                if (itemPosition == list.size() - 1) {
                    mAdapter.setCurrentWeekPosition(position);
                }

                monthlyModel.setWeekString(text);
                monthlyModel.setWeekPos(position);
                monthlyModel.setDateChecked(false);


                mAdapter.notifyDataSetChanged();
                break;

            case Cons.DAY:

                if (itemPosition == list.size() - 1) {
                    mAdapter.setCurrentDayPosition(position);
                }

                monthlyModel.setDayString(text);
                monthlyModel.setDayPos(position);
                monthlyModel.setDateChecked(false);

                mAdapter.notifyDataSetChanged();
                break;
        }

    }

    private String getOldDate(MonthlyModel monthlyModel, int type) {

        int datePosition;
        String weekPosition;
        String oldDate;

        switch (type) {
            case Cons.DATE:
                datePosition = monthlyModel.getDatePos();
                oldDate = "\"" + datePosition + "\"";
                return oldDate;
            case Cons.WEEK:
                weekPosition = "" + monthlyModel.getWeekPos() + monthlyModel.getDayPos();
                oldDate = "\"" + weekPosition + "\"";
                return oldDate;
            case Cons.DAY:
                weekPosition = "" + monthlyModel.getWeekPos() + monthlyModel.getDayPos();
                oldDate = "\"" + weekPosition + "\"";
                return oldDate;
        }
        return null;
    }

    private String getNewDate(String text, int type) {
        int datePosition;
        String weekPosition;
        String newDate;

        switch (type) {
            case Cons.DATE:
                datePosition = LoadArrayUtils.getItemPosition(text, dateArray);
                newDate = "\"" + datePosition + "\"";
                return newDate;
            case Cons.WEEK:
                weekPosition = "" + LoadArrayUtils.getItemPosition(text, weekArray)
                        + LoadArrayUtils.getItemPosition(text, dayArray);
                newDate = "\"" + weekPosition + "\"";
                return newDate;
            case Cons.DAY:
                weekPosition = "" + LoadArrayUtils.getItemPosition(text, weekArray)
                        + LoadArrayUtils.getItemPosition(text, dayArray);
                newDate = "\"" + weekPosition + "\"";
                return newDate;
        }
        return null;
    }

    private int getRepeatPos(String text, int type) {
        int repeatPosition = -1;
        switch (type) {
            case Cons.DATE:
                repeatPosition = LoadArrayUtils.getItemPosition(text, dateArray);
                break;
            case Cons.WEEK:
                repeatPosition = LoadArrayUtils.getItemPosition(text, weekArray);

                break;
            case Cons.DAY:
                repeatPosition = LoadArrayUtils.getItemPosition(text, dayArray);
                break;
        }
        return repeatPosition;
    }

    @Override
    public void onClick(View v) {
        int itemPosition = getAdapterPosition();
        MonthlyModel monthlyModel = list.get(itemPosition);
        int id = v.getId();
        switch (id) {
            case R.id.rb_date:
                monthlyModel.setDateChecked(true);
                mAdapter.notifyDataSetChanged();
                break;
            case R.id.rb_week_and_day:
                monthlyModel.setDateChecked(false);
                mAdapter.notifyDataSetChanged();
                break;
        }
    }

}
