package com.dazone.crewdday.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.Spinner;

import com.dazone.crewdday.R;
import com.dazone.crewdday.activity.DDayActivity;

import java.util.ArrayList;

/**
 * Created by maidinh on 12/5/2016.
 */
public class YearAdapter extends BaseAdapter {
    String TAG = "YearAdapter";
    Context context;
    ArrayList<ObjectAddYear> arrayList;
    ListView lv_month;

    public YearAdapter(Context context, ArrayList<ObjectAddYear> arrayList, ListView lv_month) {
        this.context = context;
        this.arrayList = arrayList;
        this.lv_month = lv_month;
    }

    @Override
    public int getCount() {
        return arrayList.size();
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.layout_adapter_year, null);
        ImageView img_remove = (ImageView) convertView.findViewById(R.id.img_remove);
        ImageView img_add = (ImageView) convertView.findViewById(R.id.img_add);
        RadioButton rb_1 = (RadioButton) convertView.findViewById(R.id.rb_1);
        RadioButton rb_2 = (RadioButton) convertView.findViewById(R.id.rb_2);
        Spinner sp_month1 = (Spinner) convertView.findViewById(R.id.sp_month1);
        Spinner sp_month2 = (Spinner) convertView.findViewById(R.id.sp_month2);
        Spinner sp_dayofmonth = (Spinner) convertView.findViewById(R.id.sp_dayofmonth);
        Spinner sp_week = (Spinner) convertView.findViewById(R.id.sp_week);
        Spinner sp_calendar = (Spinner) convertView.findViewById(R.id.sp_calendar);
        Spinner sp_dayofweek = (Spinner) convertView.findViewById(R.id.sp_dayofweek);

        ArrayAdapter adapter_sp_month1 = new ArrayAdapter<String>(context, R.layout.layout_month_android6, DDayActivity.arr_month);
        adapter_sp_month1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ArrayAdapter adapter_sp_month2 = new ArrayAdapter<String>(context, R.layout.layout_month_android6, DDayActivity.arr_month);
        adapter_sp_month2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ArrayAdapter adapter_sp_dayofmonth = new ArrayAdapter<String>(context, R.layout.layout_month_android6, DDayActivity.month_date);
        adapter_sp_dayofmonth.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ArrayAdapter adapter_sp_week = new ArrayAdapter<String>(context, R.layout.layout_month_android6, DDayActivity.month_week);
        adapter_sp_week.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ArrayAdapter adapter_sp_calendar = new ArrayAdapter<String>(context, R.layout.layout_month_android6, DDayActivity.arr_calendar);
        adapter_sp_calendar.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ArrayAdapter adapter_sp_dayofweek = new ArrayAdapter<String>(context, R.layout.layout_month_android6, DDayActivity.month_day_of_week);
        adapter_sp_dayofweek.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

//        Month_Adapter adapter_sp_month1 = new Month_Adapter(context, DDayActivity.arr_month);
//        Month_Adapter adapter_sp_month2 = new Month_Adapter(context, DDayActivity.arr_month);
//        Month_Adapter adapter_sp_dayofmonth = new Month_Adapter(context, DDayActivity.month_date);
//        Month_Adapter adapter_sp_week = new Month_Adapter(context, DDayActivity.month_week);
//        Month_Adapter adapter_sp_calendar = new Month_Adapter(context, DDayActivity.arr_calendar);
//        Month_Adapter adapter_sp_dayofweek = new Month_Adapter(context, DDayActivity.month_day_of_week);

        sp_dayofweek.setAdapter(adapter_sp_dayofweek);
        sp_dayofweek.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                arrayList.get(position).setDayofweek(pos);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        sp_dayofweek.setSelection(arrayList.get(position).getDayofweek());
        sp_calendar.setAdapter(adapter_sp_calendar);
        sp_calendar.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                arrayList.get(position).setCalendar(pos);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        sp_calendar.setSelection(arrayList.get(position).getCalendar());
        sp_week.setAdapter(adapter_sp_week);
        sp_week.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                arrayList.get(position).setWeek(pos);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        sp_week.setSelection(arrayList.get(position).getWeek());
        sp_dayofmonth.setAdapter(adapter_sp_dayofmonth);
        sp_dayofmonth.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                arrayList.get(position).setDayofmonth(pos);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        sp_dayofmonth.setSelection(arrayList.get(position).getDayofmonth());

        sp_month1.setAdapter(adapter_sp_month1);
        sp_month1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                arrayList.get(position).setMonth(pos);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        sp_month1.setSelection(arrayList.get(position).getMonth());

        sp_month2.setAdapter(adapter_sp_month2);
        sp_month2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                arrayList.get(position).setMonth2(pos);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        sp_month2.setSelection(arrayList.get(position).getMonth2());

        if (arrayList.get(position).getId() == 0) rb_1.setChecked(true);
        else rb_1.setChecked(false);
        if (arrayList.get(position).getId() == 1) rb_2.setChecked(true);
        else rb_2.setChecked(false);
        rb_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                arrayList.get(position).setId(0);
            }
        });
        rb_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                arrayList.get(position).setId(1);
            }
        });
        if (arrayList.size() == 1) {
            if (position == 0) {
                img_add.setVisibility(View.VISIBLE);
            }
        } else {
            if (position == 0) {
            } else if (position == arrayList.size() - 1) {
                img_add.setVisibility(View.VISIBLE);
                img_remove.setVisibility(View.VISIBLE);
            } else {
                img_remove.setVisibility(View.VISIBLE);
            }
        }
        img_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ObjectAddYear objectAddYear = new ObjectAddYear();

                objectAddYear.setId(0);
                objectAddYear.setMonth(DDayActivity.month_year);
                objectAddYear.setMonth2(DDayActivity.month_year);
                objectAddYear.setDayofweek(DDayActivity.dayofweek_year);
                objectAddYear.setDayofmonth(DDayActivity.day_year);
                objectAddYear.setCalendar(0);
                objectAddYear.setWeek(DDayActivity.week_of_month);

                DDayActivity.arrayList_year.add(objectAddYear);
                DDayActivity.yearAdapter.notifyDataSetChanged();

                setheight_lv_month(lv_month);
            }
        });
        img_remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DDayActivity.arrayList_year.remove(position);
                DDayActivity.yearAdapter.notifyDataSetChanged();

                setheight_lv_month(lv_month);
            }
        });
        return convertView;
    }

    public void setheight_lv_month(ListView lv_repeat) {
        int totalHeight = lv_repeat.getPaddingTop() + lv_repeat.getPaddingBottom();
        int desiredWidth = View.MeasureSpec.makeMeasureSpec(lv_repeat.getWidth(), View.MeasureSpec.AT_MOST);

        for (int i = 0; i < DDayActivity.yearAdapter.getCount(); i++) {
            View listItem = DDayActivity.yearAdapter.getView(i, null, lv_repeat);
            if (listItem != null) {
                listItem.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                listItem.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
                totalHeight += listItem.getMeasuredHeight();
            }
        }
        ViewGroup.LayoutParams params = lv_repeat.getLayoutParams();
        params.height = totalHeight + (lv_repeat.getDividerHeight() * (lv_repeat.getCount() - 1));
        lv_repeat.setLayoutParams(params);
        lv_repeat.requestLayout();
    }
}
