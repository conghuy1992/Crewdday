package com.dazone.crewdday.adapter;

import android.content.Context;
import android.os.Build;
import android.util.Log;
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
import com.dazone.crewdday._Application;
import com.dazone.crewdday.activity.DDayActivity;

import java.util.ArrayList;

/**
 * Created by maidinh on 11/5/2016.
 */
public class MonthAdapter extends BaseAdapter {
    String TAG = "MonthAdapter";
    Context context;
    ArrayList<ObjectAddMonth> arrayList;
    ListView lv_month;

    public MonthAdapter(Context context, ArrayList<ObjectAddMonth> arrayList, ListView lv_month) {
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
        convertView = inflater.inflate(R.layout.layout_adapter_month, null);
        Spinner sp_date = (Spinner) convertView.findViewById(R.id.sp_date);
        Spinner sp_week = (Spinner) convertView.findViewById(R.id.sp_week);
        Spinner sp_day_of_week = (Spinner) convertView.findViewById(R.id.sp_day_of_week);
        ImageView img_remove = (ImageView) convertView.findViewById(R.id.img_remove);
        ImageView img_add = (ImageView) convertView.findViewById(R.id.img_add);
        RadioButton rb_1 = (RadioButton) convertView.findViewById(R.id.rb_1);
        RadioButton rb_2 = (RadioButton) convertView.findViewById(R.id.rb_2);
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
        if (Build.VERSION.SDK_INT >= 23) {
            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) rb_2.getLayoutParams();
            params.setMargins(_Application.getInstance().getDimenInPx(R.dimen.adapter_month), 0, 0, 0);
            rb_2.setLayoutParams(params);

            LinearLayout.LayoutParams params2 = (LinearLayout.LayoutParams) sp_day_of_week.getLayoutParams();
            params2.setMargins(_Application.getInstance().getDimenInPx(R.dimen.adapter_month_sp), 0, 0, 0);
            sp_day_of_week.setLayoutParams(params2);

        }
//        ArrayAdapter adapter_date = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, DDayActivity.month_date);
//        adapter_date.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ArrayAdapter adapter_date = new ArrayAdapter<String>(context, R.layout.layout_month_android6, DDayActivity.month_date);
        adapter_date.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_date.setAdapter(adapter_date);
        sp_date.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                arrayList.get(position).setDate(pos);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        sp_date.setSelection(arrayList.get(position).getDate());

        ArrayAdapter adapter_week = new ArrayAdapter<String>(context, R.layout.layout_month_android6, DDayActivity.month_week);
        adapter_week.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        Month_Adapter adapter_week = new Month_Adapter(context, DDayActivity.month_week);
        sp_week.setAdapter(adapter_week);
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

        ArrayAdapter adapter_day_of_week = new ArrayAdapter<String>(context, R.layout.layout_month_android6, DDayActivity.month_day_of_week);
        adapter_day_of_week.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        Month_Adapter adapter_day_of_week = new Month_Adapter(context, DDayActivity.month_day_of_week);
        sp_day_of_week.setAdapter(adapter_day_of_week);
        sp_day_of_week.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                arrayList.get(position).setDay_of_week(pos);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        sp_day_of_week.setSelection(arrayList.get(position).getDay_of_week());

        img_remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DDayActivity.arrayList_month.remove(position);
                DDayActivity.monthAdapter.notifyDataSetChanged();

                setheight_lv_month(lv_month);
            }
        });
        img_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ObjectAddMonth ob = DDayActivity.arrayList_month.get(DDayActivity.arrayList_month.size() - 1);
                DDayActivity.date_month = ob.getDate();
                DDayActivity.date_of_week_month = ob.getDay_of_week();

                ObjectAddMonth objectAddMonth = new ObjectAddMonth();
                DDayActivity.date_month++;
                if (DDayActivity.date_month > 30)
                    DDayActivity.date_month = 0;
                DDayActivity.date_of_week_month++;
                if (DDayActivity.date_of_week_month > 6)
                    DDayActivity.date_of_week_month = 0;
                objectAddMonth.setId(0);
                objectAddMonth.setDate(DDayActivity.date_month);
                objectAddMonth.setWeek(DDayActivity.week_of_month);
                objectAddMonth.setDay_of_week(DDayActivity.date_of_week_month);
                DDayActivity.arrayList_month.add(objectAddMonth);
                DDayActivity.monthAdapter.notifyDataSetChanged();

                setheight_lv_month(lv_month);

            }
        });
        return convertView;
    }

    public void setheight_lv_month(ListView lv_repeat) {
        int totalHeight = lv_repeat.getPaddingTop() + lv_repeat.getPaddingBottom();
        int desiredWidth = View.MeasureSpec.makeMeasureSpec(lv_repeat.getWidth(), View.MeasureSpec.AT_MOST);

        for (int i = 0; i < DDayActivity.monthAdapter.getCount(); i++) {
            View listItem = DDayActivity.monthAdapter.getView(i, null, lv_repeat);
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
