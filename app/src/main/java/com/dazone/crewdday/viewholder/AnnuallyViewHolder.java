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
import com.dazone.crewdday.adapter.DDayAnnuallyListAdapter;
import com.dazone.crewdday.mInterface.UpdateListValueCallback;
import com.dazone.crewdday.model.AnnuallyModel;
import com.dazone.crewdday.util.LoadArrayUtils;

import java.util.ArrayList;

/**
 * Created by DAZONE on 24/03/16.
 */
public class AnnuallyViewHolder extends RecyclerView.ViewHolder implements UpdateListValueCallback, View.OnClickListener {

    public RadioButton rbDate;
    public RadioButton rbWeekAndDay;
    public TextView tvFirstMonth;
    public TextView tvSecondMonth;
    public TextView tvCalendar;
    public TextView tvDate;
    public TextView tvWeek;
    public TextView tvDay;
    public RelativeLayout firstMonthContainer;
    public RelativeLayout secondMonthContainer;
    public RelativeLayout calendarContainer;
    public RelativeLayout dateContainer;
    public RelativeLayout weekContainer;
    public RelativeLayout dayContainer;
    public ImageView ivAdd;
    public ImageView ivDelete;
    private DDayAnnuallyListAdapter mAdapter;
    private ArrayList<AnnuallyModel> annuallyModels;
    private String[] calendarArray;
    private StringBuilder solar;
    private StringBuilder lunar;
    private StringBuilder intercalary;
    private StringBuilder mWeekStringBuilder;

    public AnnuallyViewHolder(Context context, View itemView, ArrayList<AnnuallyModel> list,
                              StringBuilder solar,
                              StringBuilder lunar,
                              StringBuilder inter,
                              StringBuilder mWeekStringBuilder, DDayAnnuallyListAdapter adapter) {
        super(itemView);
        this.solar = solar;
        this.lunar = lunar;
        this.intercalary = inter;
        this.mWeekStringBuilder = mWeekStringBuilder;
        this.mAdapter = adapter;
        annuallyModels = list;
        rbDate = (RadioButton) itemView.findViewById(R.id.rb_date);
        rbWeekAndDay = (RadioButton) itemView.findViewById(R.id.rb_week_and_day);

        tvFirstMonth = (TextView) itemView.findViewById(R.id.txt_first_month);
        tvSecondMonth = (TextView) itemView.findViewById(R.id.txt_second_month);
        tvCalendar = (TextView) itemView.findViewById(R.id.txt_calendar);
        tvDate = (TextView) itemView.findViewById(R.id.txt_date);
        tvWeek = (TextView) itemView.findViewById(R.id.txt_week);
        tvDay = (TextView) itemView.findViewById(R.id.txt_day);

        firstMonthContainer = (RelativeLayout) itemView.findViewById(R.id.first_month_container);
        secondMonthContainer = (RelativeLayout) itemView.findViewById(R.id.second_month_container);
        calendarContainer = (RelativeLayout) itemView.findViewById(R.id.calendar_container);
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

    private void bindData(Context context, View view) {


        String[] dateArray = LoadArrayUtils.getDateArray(context);
        String[] weekArray = LoadArrayUtils.getWeekArray(context);
        String[] dayArray = LoadArrayUtils.getDayArray(context);
        String[] monthArray = LoadArrayUtils.getMonthArray(context);
        String[] calendarArray = LoadArrayUtils.getCalendar(context);


        OptionDiaglogManager date = new OptionDiaglogManager(view, false, Cons.NO_VALUES,
                Cons.FRAGMENT_TYPE_ANNUALLY, Cons.SELECT_DATE,
                R.id.date_container, R.id.txt_date, dateArray);
        date.setUpdateValueCallback(this);
        date.setmType(Cons.ANNUALLY);

        OptionDiaglogManager week = new OptionDiaglogManager(view, false, Cons.NO_VALUES,
                Cons.FRAGMENT_TYPE_ANNUALLY, Cons.SELECT_WEEK,
                R.id.week_container, R.id.txt_week, weekArray);
        week.setUpdateValueCallback(this);
        week.setmType(Cons.ANNUALLY);

        OptionDiaglogManager day = new OptionDiaglogManager(view, false, Cons.NO_VALUES,
                Cons.FRAGMENT_TYPE_ANNUALLY, Cons.SELECT_DAY,
                R.id.day_container, R.id.txt_day, dayArray);
        day.setUpdateValueCallback(this);
        day.setmType(Cons.ANNUALLY);

        OptionDiaglogManager firstMonth = new OptionDiaglogManager(view, false, Cons.NO_VALUES,
                Cons.FRAGMENT_TYPE_ANNUALLY, Cons.SELECT_MONTH,
                R.id.first_month_container, R.id.txt_first_month, monthArray);
        firstMonth.setUpdateValueCallback(this);
        firstMonth.setmType(Cons.ANNUALLY);


        OptionDiaglogManager secondMonth = new OptionDiaglogManager(view, false, Cons.NO_VALUES,
                Cons.FRAGMENT_TYPE_ANNUALLY, Cons.SELECT_MONTH,
                R.id.second_month_container, R.id.txt_second_month, monthArray);
        secondMonth.setUpdateValueCallback(this);
        secondMonth.setmType(Cons.ANNUALLY);


        OptionDiaglogManager calendar = new OptionDiaglogManager(view, false, Cons.NO_VALUES,
                Cons.FRAGMENT_TYPE_ANNUALLY, Cons.SELECT_CALENDAR,
                R.id.calendar_container, R.id.txt_calendar, calendarArray);
        calendar.setUpdateValueCallback(this);
        calendar.setmType(Cons.ANNUALLY);


    }

    @Override
    public void updateValue(String text, int position, int type) {
        int itemPosition = getAdapterPosition();
        AnnuallyModel annuallyModel = annuallyModels.get(itemPosition);
        switch (type) {
            case Cons.FIRST_MONTH:
                annuallyModel.setFirstMonthString(text);
                annuallyModel.setFirstMonthPos(position);
                annuallyModel.setDateChecked(true);
                mAdapter.notifyDataSetChanged();
                // StringUtils.switchAnnuallyRO(mDateStringBuilder,mWeekStringBuilder,annuallyModel,Cons.TO_DATE,this);
                break;
            case Cons.CALENDAR:
                annuallyModel.setLunarString(text);
                annuallyModel.setLunarPos(position);
                annuallyModel.setDateChecked(true);
                mAdapter.notifyDataSetChanged();
                break;
            case Cons.DATE:
                annuallyModel.setDateString(text);
                annuallyModel.setDatePos(position);
                annuallyModel.setDateChecked(true);
                mAdapter.notifyDataSetChanged();
                break;
            case Cons.SECOND_MONTH:
                annuallyModel.setSecondMonthString(text);
                annuallyModel.setSecondMonthPos(position);
                annuallyModel.setDateChecked(false);
                mAdapter.notifyDataSetChanged();
                break;
            case Cons.WEEK:
                annuallyModel.setWeekString(text);
                annuallyModel.setWeekPos(position);
                annuallyModel.setDateChecked(false);
                mAdapter.notifyDataSetChanged();
                break;
            case Cons.DAY:
                annuallyModel.setDayString(text);
                annuallyModel.setDayPos(position);
                annuallyModel.setDateChecked(false);
                mAdapter.notifyDataSetChanged();
                break;

        }

    }

    @Override
    public void onClick(View v) {
        int itemPosition = getAdapterPosition();
        AnnuallyModel annuallyModel = annuallyModels.get(itemPosition);
        int id = v.getId();
        switch (id) {
            case R.id.rb_date:
                annuallyModel.setDateChecked(true);
                mAdapter.notifyDataSetChanged();
                break;
            case R.id.rb_week_and_day:
                annuallyModel.setDateChecked(false);
                mAdapter.notifyDataSetChanged();
                break;
        }
    }

}
