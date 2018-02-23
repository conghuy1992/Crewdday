package com.dazone.crewdday.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dazone.crewdday.Cons;
import com.dazone.crewdday.R;
import com.dazone.crewdday._Application;
import com.dazone.crewdday.mInterface.UpdateListCallback;
import com.dazone.crewdday.model.MonthlyModel;
import com.dazone.crewdday.util.Helper;
import com.dazone.crewdday.util.PreferenceUtilities;
import com.dazone.crewdday.viewholder.MonthlyViewHolder;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * Created by DAZONE on 23/03/16.
 */
public class DDayMonthlyListAdapter extends RecyclerView.Adapter<MonthlyViewHolder> {
    String TAG = "DDayMonthlyListAdapter";
    int interval;
    String startDate;
    String endDate;
    int holiday;

    String[] dateArray;
    String[] weekArray;
    String[] dayArray;

    ArrayList<MonthlyModel> list;
    RecyclerView recyclerView;
    private Context mContext;
    private UpdateListCallback mCallback;
    private boolean mFirstTime;
    private PreferenceUtilities mPref;

    int currentDatePosition;
    int currentWeekPosition;
    int currentDayPosition;
    ArrayList<String> dateAdded;

    public void setDateAdded(ArrayList<String> dateAdded) {
        this.dateAdded = dateAdded;
    }

    public DDayMonthlyListAdapter() {
    }

    public DDayMonthlyListAdapter(Context context, ArrayList<MonthlyModel> list,
                                  RecyclerView recyclerView, boolean firstTime, UpdateListCallback callback) {
        this.list = list;
        this.mContext = context;
        this.recyclerView = recyclerView;
        this.mCallback = callback;
        this.mFirstTime = firstTime;
        this.mPref = _Application.getInstance().getPreferenceUtilities();

        dateAdded = new ArrayList<String>();

        interval = Integer.parseInt(mPref.getMonthlyIntervalPos()) + 1;
        holiday = Integer.parseInt(mPref.getMonthlyHolidayPos()) + 1;
        startDate = mPref.getMonthlyStartDate();
        endDate = mPref.getMonthlyEndDate();

        getExistedValuable();

    }

    public ArrayList<String> getDateAdded() {
        return dateAdded;
    }


    public void setDayArray(String[] dayArray) {
        this.dayArray = dayArray;
    }

    public void setDateArray(String[] dateArray) {
        this.dateArray = dateArray;
    }

    public void setWeekArray(String[] weekArray) {
        this.weekArray = weekArray;
    }

    public void setCurrentDatePosition(int currentDatePosition) {
        this.currentDatePosition = currentDatePosition;
    }


    public void setCurrentDayPosition(int currentDayPosition) {
        this.currentDayPosition = currentDayPosition;
    }

    public void setCurrentWeekPosition(int currentWeekPosition) {
        this.currentWeekPosition = currentWeekPosition;
    }

    @Override
    public int getItemCount() {
        //returns the number of elements the RecyclerView will display
        return list.size();
    }

    @Override
    public int getItemViewType(int position) {
        MonthlyModel monthlyModel = list.get(position);
        if (monthlyModel != null)
            return monthlyModel.getViewType();

        return 0;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public MonthlyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;

        if (viewType == Cons.THE_FIRST) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_monthly_list_1, parent, false);
        } else if (viewType == Cons.THE_REST) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_monthly_list_2, parent, false);
        }

        MonthlyViewHolder holder = new MonthlyViewHolder(mContext, view, list, this);
        holder.setDateArray(dateArray);
        holder.setWeekArray(weekArray);
        holder.setDayArray(dayArray);
        return holder;
    }

    @Override
    public void onBindViewHolder(final MonthlyViewHolder holder, final int position) {

        final MonthlyModel monthlyModel = list.get(position);

        if (position == 0) {
            holder.ivDelete.setVisibility(View.GONE);
        }

        //Set button "+" visibility
        if (monthlyModel.isDoHideAddButton() == true) {
            holder.ivAdd.setVisibility(View.INVISIBLE);
        } else if (monthlyModel.isDoHideAddButton() == false) {
            holder.ivAdd.setVisibility(View.VISIBLE);
        }

        if (monthlyModel.getDateString() != null) {
            holder.tvDate.setText(monthlyModel.getDateString());
        }

        if (monthlyModel.getWeekString() != null) {
            holder.tvWeek.setText(monthlyModel.getWeekString());
            holder.tvDay.setText(monthlyModel.getDayString());
        }

        if (monthlyModel.isDateChecked()) {
            holder.rbDate.setChecked(true);
            holder.rbWeekAndDay.setChecked(false);
        } else {
            holder.rbDate.setChecked(false);
            holder.rbWeekAndDay.setChecked(true);
        }


        if (list.size() == dateArray.length) {
            holder.ivAdd.setEnabled(false);
        } else {
            holder.ivAdd.setEnabled(true);
        }

        setButtonAddClickListener(holder, monthlyModel);
        setButtonDeleteClickListener(holder, position);
        //setRadioButtonClickListener(holder, monthlyModel);


        if (mFirstTime) {
            currentDatePosition = monthlyModel.getDatePos();
            currentWeekPosition = monthlyModel.getWeekPos();
            currentDayPosition = monthlyModel.getDayPos();
            dateAdded.add(String.valueOf(currentDatePosition));

            mFirstTime = false;
        } else {
            currentDatePosition = monthlyModel.getDatePos();
        }

    }

    private void setButtonAddClickListener(final MonthlyViewHolder holder, final MonthlyModel previousMonthlyModel) {
        //Set button "+" click listener
        holder.ivAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e(TAG, "click");

                currentDatePosition = currentDatePosition + 1;
                currentDayPosition = currentDayPosition + 1;

                //DATE
                //Recycle the date array
                if (currentDatePosition == dateArray.length) {
                    for (int i = 0; i < dateArray.length; i++) {
                        if (!dateAdded.contains(String.valueOf(i))) {
                            currentDatePosition = i;
//                            Log.e("POSITION-AFTER-RECYCLE", currentDatePosition + "");
                            break;
                        }
                    }
                }

//                Log.e("current-position-1",currentDatePosition+"");

                if (dateAdded.contains(String.valueOf(currentDatePosition))) {
                    for (int i = 0; i < dateArray.length; i++) {
//                        Log.e("current-position-2",currentDatePosition+i+"");
                        if (!dateAdded.contains(String.valueOf(currentDatePosition + i))) {
                            currentDatePosition = currentDatePosition + i;
//                            Log.e("POSITION-IGNORE",currentDatePosition+"");
                            break;
                        }
                    }
                }


                //WEEK - DAY
                if (currentDayPosition == dayArray.length) {
                    currentDayPosition = 0;
                    currentWeekPosition = currentWeekPosition + 1;
                    if (currentWeekPosition == weekArray.length) {
                        currentWeekPosition = 0;
                    }
                }


                //Hide button add
                previousMonthlyModel.setDoHideAddButton(true);

                MonthlyModel monthlyModel = new MonthlyModel();
                monthlyModel.setDateString(dateArray[currentDatePosition]);
                monthlyModel.setWeekString(weekArray[currentWeekPosition]);
                monthlyModel.setDayString(dayArray[currentDayPosition]);
                monthlyModel.setDatePos(currentDatePosition);
                monthlyModel.setWeekPos(currentWeekPosition);
                monthlyModel.setDayPos(currentDayPosition);
                monthlyModel.setViewType(Cons.THE_REST);
                monthlyModel.setDateChecked(true);
                list.add(monthlyModel);
                mCallback.updateMonthlyModelList(list);
                notifyDataSetChanged();

                //Set item height
                int screenWidth = Helper.getScreenWidth(mContext);
                ViewGroup.LayoutParams params = recyclerView.getLayoutParams();
                params.height += Cons.MONTHLY_RECYCLERVIEW_HEIGH_CHANGE;
                ;
                params.width = screenWidth;
                recyclerView.setLayoutParams(params);

                notifyItemChanged(previousMonthlyModel.getDatePos());

                dateAdded.add(String.valueOf(currentDatePosition));
                for (int i = 0; i < dateAdded.size(); i++) {
                    Log.e(TAG, dateAdded.get(i));
                }
                savePositionAdded();
            }
        });
    }

    private void setButtonDeleteClickListener(final MonthlyViewHolder holder, final int position) {
        holder.ivDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dateAdded.remove(String.valueOf(currentDatePosition + 1));
                savePositionAdded();
                if (position == (list.size() - 1)) {
                    currentDatePosition = currentDatePosition - 1;
                    currentDayPosition = currentDayPosition - 1;
                }


                //  MonthlyModel monthlyModel_1 = list.get(position);
               /* if (monthlyModel_1.isDateChecked()) {
                    StringUtils.removeMonthlyRO(mDateStringBuilder, monthlyModel_1, DDayMonthlyListAdapter.this);
                } else {
                    StringUtils.removeMonthlyRO(mWeekStringBuilder, monthlyModel_1, DDayMonthlyListAdapter.this);
                }*/

                if (list.size() != 0 && list.size() > 1) {
                    if (position == recyclerView.getChildCount() - 1) {
                        MonthlyModel monthlyModel = list.get(position - 1);
                        monthlyModel.setDoHideAddButton(false);
                        notifyItemChanged(position - 1);
                    }
                }

                //Show "+" button of item 1
                if (list.size() == 2) {
                    MonthlyModel monthlyModel = list.get(0);
                    monthlyModel.setDoHideAddButton(false);
                    notifyItemChanged(0);
                }

                list.remove(position);
                mCallback.updateMonthlyModelList(list);
                notifyDataSetChanged();


                //Set item height
                int screenWidth = Helper.getScreenWidth(mContext);
                ViewGroup.LayoutParams params = recyclerView.getLayoutParams();
                params.height -= Cons.MONTHLY_RECYCLERVIEW_HEIGH_CHANGE;
                ;
                params.width = screenWidth;
                recyclerView.setLayoutParams(params);


            }
        });
    }

    private void getExistedValuable() {
        if (!mPref.getMonthlyCurrentItemPosition().isEmpty()) {
            currentDatePosition = Integer.parseInt(mPref.getMonthlyCurrentItemPosition());
        }

        if (!mPref.getMonthlyPositionAdded().isEmpty()) {
            Gson gson = new Gson();
            String json = mPref.getMonthlyPositionAdded();
            Type type = new TypeToken<ArrayList<String>>() {
            }.getType();
            dateAdded = gson.fromJson(json, type);
        }
    }

    public void saveCurrentPosition() {
        mPref.setMonthlyCurrentItemPosition(String.valueOf(currentDatePosition));
    }

    public void savePositionAdded() {
        Gson gson = new Gson();
        String json = gson.toJson(dateAdded);
        Log.e(TAG, "json:"+json);
        mPref.setMonthlyPositionAdded(json);
    }

}
