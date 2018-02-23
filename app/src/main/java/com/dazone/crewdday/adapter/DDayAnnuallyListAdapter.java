package com.dazone.crewdday.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dazone.crewdday.Cons;
import com.dazone.crewdday.R;
import com.dazone.crewdday._Application;
import com.dazone.crewdday.mInterface.UpdateListCallback;
import com.dazone.crewdday.model.AnnuallyModel;
import com.dazone.crewdday.util.Helper;
import com.dazone.crewdday.util.PreferenceUtilities;
import com.dazone.crewdday.util.TimeUtils;
import com.dazone.crewdday.viewholder.AnnuallyViewHolder;

import java.util.ArrayList;

/**
 * Created by DAZONE on 22/03/16.
 */
public class DDayAnnuallyListAdapter extends RecyclerView.Adapter<AnnuallyViewHolder> {

    int currentDatePosition;
    int currentWeekPosition;
    int currentDayPosition;


    int interval;
    int holiday;
    String startDate;
    String endDate;
    String[] monthArray;
    String[] dateArray;
    String[] weekArray;
    String[] dayArray;


    private ArrayList<AnnuallyModel> mList;
    private Context mContext;
    private RecyclerView mRecyclerView;

    int monthPos;
    int datePos;
    int weekPos;
    int dayPos;

    private boolean mFirstTime;
    private PreferenceUtilities mPref;
    private UpdateListCallback mCallback;
    private StringBuilder solar;
    private StringBuilder lunar;
    private StringBuilder inter;
    private StringBuilder mWeekStringBuilder;


    public DDayAnnuallyListAdapter(Context context, ArrayList<AnnuallyModel> list,
                                   RecyclerView recyclerView, boolean firstTime, UpdateListCallback callback) {
        this.mList = list;
        this.mContext = context;
        this.mRecyclerView = recyclerView;
        this.mCallback = callback;
        this.mFirstTime = firstTime;
        this.mPref = _Application.getInstance().getPreferenceUtilities();


        String today = TimeUtils.getCurrentDayFormatted_1();
        String[] todayArray = today.split("/");

        solar = new StringBuilder();
        lunar = new StringBuilder();
        inter = new StringBuilder();
        mWeekStringBuilder = new StringBuilder();


        interval = Integer.parseInt(mPref.getAnnuallyIntervalPos()) + 1;
        holiday = Integer.parseInt(mPref.getAnnuallyHolidayPos()) + 1;
        startDate = mPref.getMonthlyStartDate();
        endDate = mPref.getMonthlyEndDate();
    }


    public void setMonthArray(String[] monthArray) {
        this.monthArray = monthArray;
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

    public void setMonthPos(int monthPos) {
        this.monthPos = monthPos;
    }

    public void setDatePos(int datePos) {
        this.datePos = datePos;
    }

    public void setWeekPos(int weekPos) {
        this.weekPos = weekPos;
    }

    public void setDayPos(int dayPos) {
        this.dayPos = dayPos;
    }

    @Override
    public int getItemCount() {
        //returns the number of elements the RecyclerView will display
        return mList.size();
    }

    @Override
    public int getItemViewType(int position) {
        AnnuallyModel annuallyModel = mList.get(position);
        if (annuallyModel != null)
            return annuallyModel.getViewType();

        return 0;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public AnnuallyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;

        if (viewType == Cons.THE_FIRST) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_annually_list_1, parent, false);
        } else if (viewType == Cons.THE_REST) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_annually_list_2, parent, false);
        }

        AnnuallyViewHolder holder = new AnnuallyViewHolder(mContext, view, mList, solar, lunar,
                inter, mWeekStringBuilder, this);

        return holder;
    }

    @Override
    public void onBindViewHolder(final AnnuallyViewHolder holder, final int position) {

        final AnnuallyModel annuallyModel = mList.get(position);


        if (position == 0) {
            holder.ivDelete.setVisibility(View.GONE);
        }


        //Set button "+" visibility
        if (annuallyModel.isDoHideAddButton() == true) {
            holder.ivAdd.setVisibility(View.INVISIBLE);
        } else if (annuallyModel.isDoHideAddButton() == false) {
            holder.ivAdd.setVisibility(View.VISIBLE);
        }

        if(annuallyModel.getLunarString()!= null){
            holder.tvCalendar.setText(annuallyModel.getLunarString());
        }
        holder.tvFirstMonth.setText(annuallyModel.getFirstMonthString());
        holder.tvSecondMonth.setText(annuallyModel.getSecondMonthString());
        holder.tvDate.setText(annuallyModel.getDateString());
        holder.tvWeek.setText(annuallyModel.getWeekString());
        holder.tvDay.setText(annuallyModel.getDayString());

        if (annuallyModel.isDateChecked()) {
            holder.rbDate.setChecked(true);
            holder.rbWeekAndDay.setChecked(false);
        } else {
            holder.rbDate.setChecked(false);
            holder.rbWeekAndDay.setChecked(true);
        }

        setButtonAddClickListener(holder, annuallyModel, position);
        setButtonDeleteClickListener(holder, position);


        if (mFirstTime) {
            mFirstTime = false;
        }


    }


    private void setButtonAddClickListener(final AnnuallyViewHolder holder,
                                           final AnnuallyModel previousAnnuallyModel, final int position) {
        //Set button "+" click listener
        holder.ivAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Hide button add
                previousAnnuallyModel.setDoHideAddButton(true);
                //holder.ivAdd.setVisibility(View.INVISIBLE);
                AnnuallyModel annuallyModel = new AnnuallyModel();
                annuallyModel.setFirstMonthString(monthArray[monthPos]);
                annuallyModel.setSecondMonthString(monthArray[monthPos]);
                annuallyModel.setDateString(dateArray[datePos]);
                annuallyModel.setWeekString(weekArray[weekPos]);
                annuallyModel.setDayString(dayArray[dayPos]);
                annuallyModel.setFirstMonthPos(monthPos);
                annuallyModel.setSecondMonthPos(monthPos);
                annuallyModel.setDatePos(datePos);
                annuallyModel.setWeekPos(weekPos);
                annuallyModel.setDayPos(dayPos);
                annuallyModel.setLunarPos(0);
                annuallyModel.setViewType(Cons.THE_REST);
                annuallyModel.setDateChecked(true);
                mList.add(annuallyModel);
                mCallback.updateAnnuallyModelList(mList);
                notifyDataSetChanged();

                //Set item height
                int screenWidth = Helper.getScreenWidth(mContext);
                ViewGroup.LayoutParams params = mRecyclerView.getLayoutParams();
                params.height += Cons.ANNUALLY_RECYCLERVIEW_HEIGH_CHANGE;
                params.width = Cons.MONTHLY_RECYCLERVIEW_WIDTH_CHANGE;
                mRecyclerView.setLayoutParams(params);


                notifyItemChanged(previousAnnuallyModel.getDatePos());
            }
        });
    }

    private void setButtonDeleteClickListener(final AnnuallyViewHolder holder, final int position) {
        holder.ivDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (mList.size() != 0 && mList.size() > 1) {
                    if (position == mRecyclerView.getChildCount() - 1) {
                        AnnuallyModel monthlyModel = mList.get(position - 1);
                        monthlyModel.setDoHideAddButton(false);
                        notifyItemChanged(position - 1);
                    }
                }

                //Show "+" button of item 1
                if (mList.size() == 2) {
                    AnnuallyModel annuallyModel = mList.get(0);
                    annuallyModel.setDoHideAddButton(false);
                    notifyItemChanged(0);
                }

                mList.remove(position);
                mCallback.updateAnnuallyModelList(mList);
                notifyDataSetChanged();


                //Set item height
                int screenWidth = Helper.getScreenWidth(mContext);
                ViewGroup.LayoutParams params = mRecyclerView.getLayoutParams();
                params.height -= Cons.ANNUALLY_RECYCLERVIEW_HEIGH_CHANGE;
                params.width = Cons.MONTHLY_RECYCLERVIEW_WIDTH_CHANGE;
                mRecyclerView.setLayoutParams(params);


            }
        });
    }


}