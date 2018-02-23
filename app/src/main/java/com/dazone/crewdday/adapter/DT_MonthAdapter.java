package com.dazone.crewdday.adapter;

import android.content.Context;
import android.os.Build;
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
import com.dazone.crewdday.activity.DetailViewActivity;

import java.util.ArrayList;


public class DT_MonthAdapter extends BaseAdapter {
    String TAG = "MonthAdapter";
    Context context;
    ArrayList<ObjectAddMonth> arrayList;
    ListView lv_month;

    public DT_MonthAdapter(Context context, ArrayList<ObjectAddMonth> arrayList, ListView lv_month) {
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
        int resource = R.layout.layout_month_android6;
        if (Build.VERSION.SDK_INT >= 23) {
            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) rb_2.getLayoutParams();
            params.setMargins(_Application.getInstance().getDimenInPx(R.dimen.adapter_month), 0, 0, 0);
            rb_2.setLayoutParams(params);
        }
        ArrayAdapter adapter_date = new ArrayAdapter<String>(context, resource, _Application.getInstance().month_date());
        adapter_date.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        Month_Adapter adapter_date = new Month_Adapter(context, DetailViewActivity.month_date);
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

        ArrayAdapter adapter_week = new ArrayAdapter<String>(context, resource, _Application.getInstance().month_week());
        adapter_week.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        Month_Adapter adapter_week = new Month_Adapter(context, DetailViewActivity.month_week);
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

        ArrayAdapter adapter_day_of_week = new ArrayAdapter<String>(context, resource, _Application.getInstance().month_day_of_week());
        adapter_day_of_week.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        Month_Adapter adapter_day_of_week = new Month_Adapter(context, DetailViewActivity.month_day_of_week);
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
                DetailViewActivity.arrayList_month.remove(position);
                DetailViewActivity.monthAdapter.notifyDataSetChanged();

                setheight_lv_month(lv_month);
            }
        });
        img_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ObjectAddMonth ob = DetailViewActivity.arrayList_month.get(DetailViewActivity.arrayList_month.size() - 1);
                DetailViewActivity.date_month = ob.getDate();
                DetailViewActivity.date_of_week_month = ob.getDay_of_week();

                ObjectAddMonth objectAddMonth = new ObjectAddMonth();
                DetailViewActivity.date_month++;
                if (DetailViewActivity.date_month > 30)
                    DetailViewActivity.date_month = 0;
                DetailViewActivity.date_of_week_month++;
                if (DetailViewActivity.date_of_week_month > 6)
                    DetailViewActivity.date_of_week_month = 0;
                objectAddMonth.setId(0);
                objectAddMonth.setDate(DetailViewActivity.date_month);
                objectAddMonth.setWeek(DetailViewActivity.week_of_month);
                objectAddMonth.setDay_of_week(DetailViewActivity.date_of_week_month);
                DetailViewActivity.arrayList_month.add(objectAddMonth);
                DetailViewActivity.monthAdapter.notifyDataSetChanged();

                setheight_lv_month(lv_month);

            }
        });
        return convertView;
    }

    public void setheight_lv_month(ListView lv_repeat) {
        int totalHeight = lv_repeat.getPaddingTop() + lv_repeat.getPaddingBottom();
        int desiredWidth = View.MeasureSpec.makeMeasureSpec(lv_repeat.getWidth(), View.MeasureSpec.AT_MOST);

        for (int i = 0; i < DetailViewActivity.monthAdapter.getCount(); i++) {
            View listItem = DetailViewActivity.monthAdapter.getView(i, null, lv_repeat);
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
