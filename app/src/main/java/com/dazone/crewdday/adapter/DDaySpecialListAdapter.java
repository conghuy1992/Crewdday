package com.dazone.crewdday.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dazone.crewdday.Cons;
import com.dazone.crewdday.R;
import com.dazone.crewdday._Application;
import com.dazone.crewdday.mInterface.UpdateStringROCallback;
import com.dazone.crewdday.model.SpecialDateModel;
import com.dazone.crewdday.util.DialogUtils;
import com.dazone.crewdday.util.Helper;
import com.dazone.crewdday.util.PreferenceUtilities;
import com.dazone.crewdday.util.StringUtils;
import com.dazone.crewdday.util.TimeUtils;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.util.ArrayList;

/**
 * Created by Admin on 3/19/2016.
 */
public class DDaySpecialListAdapter extends BaseAdapter implements View.OnClickListener, DatePickerDialog.OnDateSetListener {


    ArrayList<SpecialDateModel> specialDateModels;
    ListView listView;
    ViewHolder viewHolder;
    private Context mContext;
    private int mPosition;
    private PreferenceUtilities mPref;

    public DDaySpecialListAdapter(Context context, ArrayList<SpecialDateModel> arrayList, ListView listView, boolean firstTime) {
        this.mContext = context;
        this.specialDateModels = arrayList;
        this.listView = listView;
        this.mPref = _Application.getInstance().getPreferenceUtilities();

    }

    public int getCount() {
        return specialDateModels.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        SpecialDateModel specialDateModel = specialDateModels.get(position);

        if (convertView == null) {
            // inflate the GridView item layout
            LayoutInflater inflater = LayoutInflater.from(mContext);
            convertView = inflater.inflate(R.layout.item_special_list, parent, false);

            viewHolder = new ViewHolder();
            viewHolder.dateContainer = (RelativeLayout) convertView.findViewById(R.id.start_day_chose_container);
            viewHolder.tvSpecialDate = (TextView) convertView.findViewById(R.id.txt_start_day_chose);
            viewHolder.ivAdd = (ImageView) convertView.findViewById(R.id.img_add_special_day);
            viewHolder.ivDelete = (ImageView) convertView.findViewById(R.id.img_remove_special_day);
            viewHolder.dateContainer.setOnClickListener(this);
            viewHolder.ivAdd.setOnClickListener(this);
            viewHolder.ivDelete.setOnClickListener(this);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        if (mPref.getDdayType().equals(String.valueOf(Cons.DDAY_PLUS_MODE))) {
            viewHolder.ivAdd.setVisibility(View.INVISIBLE);
        }

        viewHolder.dateContainer.setTag(position);
        viewHolder.ivAdd.setTag(position);
        viewHolder.ivDelete.setTag(position);

        viewHolder.tvSpecialDate.setText(specialDateModel.getSpecialDate());
        if (specialDateModel.isDoHideAddButton()) {
            viewHolder.ivAdd.setVisibility(View.INVISIBLE);
        } else {
            viewHolder.ivAdd.setVisibility(View.VISIBLE);
        }


        if (specialDateModel.isdoShowDeleteButton()) {
            viewHolder.ivDelete.setVisibility(View.VISIBLE);
        } else {
            viewHolder.ivDelete.setVisibility(View.INVISIBLE);
        }

        return convertView;
    }


    @Override
    public void onClick(View v) {

        final int position = (int) v.getTag();
        SpecialDateModel specialDateModel = specialDateModels.get(position);
        int id = v.getId();
        switch (id) {
            case R.id.img_add_special_day:
                // Hide "+" button of previous item
                specialDateModel.setDoHideAddButton(true);

                String previousItemDate = specialDateModel.getSpecialDate();
                //get the previous item millisecond
                long currentItemMilli = TimeUtils.convertDateToMillisecond(previousItemDate);
                //plus 1 day for current item
                long nextItemMilli = currentItemMilli + 86400000;

                String nextItemDate = TimeUtils.convertMillisecondToDate(nextItemMilli);


                SpecialDateModel specialDateModel2 = new SpecialDateModel();
                specialDateModel2.setDoShowDeleteButton(true);
                specialDateModel2.setSpecialDate(nextItemDate);

                specialDateModels.add(specialDateModel2);
                // mCallback.updateSpecialModelList(arrayList);
                notifyDataSetChanged();
                Helper.getListViewSize(listView);
                break;
            case R.id.img_remove_special_day:
                if (specialDateModels.size() != 0 && specialDateModels.size() > 1) {
                    if (position == specialDateModels.size() - 1) {
                        SpecialDateModel specialDateModel3 = specialDateModels.get(position - 1);
                        specialDateModel3.setDoHideAddButton(false);
                        notifyDataSetChanged();
                    }
                }
                specialDateModels.remove(position);
                // mCallback.updateSpecialModelList(arrayList);
                notifyDataSetChanged();
                Helper.getListViewSize(listView);
                break;
            case R.id.start_day_chose_container:
                mPosition = position;
                String date = specialDateModels.get(position).getSpecialDate();
                DialogUtils.openCalendarDialog(mContext, date, DDaySpecialListAdapter.this);
                break;
            default:
                break;
        }
    }


    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {

        String newMonth = null;
        String newDay = null;
        if (dayOfMonth < 10) {
            newDay = "0" + dayOfMonth;
        } else {
            newDay = String.valueOf(dayOfMonth);
        }

        if ((monthOfYear + 1) < 10) {
            newMonth = "0" + (monthOfYear + 1);
        } else {
            newMonth = String.valueOf(monthOfYear + 1);
        }

        String date = newDay + "/" + newMonth + "/" + year;
        String newDate = TimeUtils.convertToSimpleDate2(date);

        SpecialDateModel specialDateModel = specialDateModels.get(mPosition);
        //Set new day
        specialDateModel.setSpecialDate(newDate);
        notifyDataSetChanged();
    }

    private static class ViewHolder {
        RelativeLayout dateContainer;
        TextView tvSpecialDate;
        ImageView ivAdd;
        ImageView ivDelete;
    }
}
