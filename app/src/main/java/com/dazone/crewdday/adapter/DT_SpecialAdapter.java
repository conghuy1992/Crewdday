package com.dazone.crewdday.adapter;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dazone.crewdday.Cons;
import com.dazone.crewdday.R;
import com.dazone.crewdday.activity.DetailViewActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by maidinh on 7/6/2016.
 */
public class DT_SpecialAdapter extends BaseAdapter {
    String TAG = "SpecialAdapter";
    ArrayList<String> arrayList;
    Context context;


    String get_cur_date = "";
    String str = "";

    public DT_SpecialAdapter(Context context, ArrayList<String> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
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
        convertView = inflater.inflate(R.layout.special_adapter_layout, null);
        final TextView tv_date = (TextView) convertView.findViewById(R.id.tv_date);
        ImageView img_remove = (ImageView) convertView.findViewById(R.id.img_remove);
        ImageView img_add = (ImageView) convertView.findViewById(R.id.img_add);
        if (DetailViewActivity.sp_ddaytype_int != 1) {
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
        }
        Calendar c = Calendar.getInstance();
        String str2 = arrayList.get(position);
        int year = Integer.parseInt(str2.split("-")[0]);
        int month = Integer.parseInt(str2.split("-")[1]);
        int date = Integer.parseInt(str2.split("-")[2]);
        c.set(year, month - 1, date);
        String day = str2;
        day = day.replace("(", "");
        day = day.replace(")", "");
        tv_date.setText(day);
        img_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Cur_Date = DetailViewActivity.arr_lv_special.get(DetailViewActivity.arr_lv_special.size() - 1);
                int date = Integer.parseInt(Cur_Date.split("-")[2]);
                DetailViewActivity.date_special = date + 1;
                DetailViewActivity.month_special = Integer.parseInt(Cur_Date.split("-")[1]);
                if (DetailViewActivity.date_special > Cons.day_of_month(DetailViewActivity.month_special)) {
                    DetailViewActivity.date_special = 1;
                    DetailViewActivity.month_special += 1;
                }
                if (DetailViewActivity.month_special > 12) {
                    DetailViewActivity.month_special = 1;
                    DetailViewActivity.year += 1;
                }
                str = DetailViewActivity.year + "-" + Cons.getfulldate(DetailViewActivity.month_special) + "-" + Cons.getfulldate(DetailViewActivity.date_special);

                if (check_contain_add(str)) {
                    while (check_contain_add(str)) {
                        DetailViewActivity.date_special++;
                        if (DetailViewActivity.date_special > Cons.day_of_month(DetailViewActivity.month_special)) {
                            DetailViewActivity.date_special = 1;
                            DetailViewActivity.month_special += 1;
                        }
                        if (DetailViewActivity.month_special > 12) {
                            DetailViewActivity.month_special = 1;
                            DetailViewActivity.year += 1;
                        }
                        str = DetailViewActivity.year + "-" + Cons.getfulldate(DetailViewActivity.month_special) + "-" + Cons.getfulldate(DetailViewActivity.date_special);
                        if (!check_contain_add(str)) {
                            DetailViewActivity.arr_lv_special.add(str);
                            DetailViewActivity.specialAdapter.notifyDataSetChanged();
                            View item = DetailViewActivity.specialAdapter.getView(0, null, DetailViewActivity.special_lv_day);
                            item.measure(0, 0);
                            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, (int) (arrayList.size() * item.getMeasuredHeight()));
                            DetailViewActivity.special_lv_day.setLayoutParams(params);
                            break;
                        }
                    }
                } else {
                    DetailViewActivity.arr_lv_special.add(str);
                    DetailViewActivity.specialAdapter.notifyDataSetChanged();
                    View item = DetailViewActivity.specialAdapter.getView(0, null, DetailViewActivity.special_lv_day);
                    item.measure(0, 0);
                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, (int) (arrayList.size() * item.getMeasuredHeight()));
                    DetailViewActivity.special_lv_day.setLayoutParams(params);
                }
            }
        });
        img_remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (arrayList.size() > 1) {
                    DetailViewActivity.arr_lv_special.remove(position);
                    DetailViewActivity.specialAdapter.notifyDataSetChanged();
                    View item = DetailViewActivity.specialAdapter.getView(0, null, DetailViewActivity.special_lv_day);
                    item.measure(0, 0);
                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, (int) (arrayList.size() * item.getMeasuredHeight()));
                    DetailViewActivity.special_lv_day.setLayoutParams(params);
                } else {

                }
            }
        });
        tv_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                get_cur_date = tv_date.getText().toString();
                ShowDateDialog(position);
            }
        });

        return convertView;
    }


    String choose_day = "";

    public boolean check_contain_add(String s) {
        for (int i = 0; i < DetailViewActivity.arr_lv_special.size(); i++) {
            String str = DetailViewActivity.arr_lv_special.get(i);
            String str2 = s;
            if (str.equals(str2))
                return true;
        }
        return false;
    }

    public boolean check_contain(String s) {
        for (int i = 0; i < DetailViewActivity.arr_lv_special.size(); i++) {
            if (DetailViewActivity.arr_lv_special.get(i).equals(s))
                return true;
        }
        return false;
    }

    public void ShowDateDialog(final int position) {
        DatePickerDialog dpd = new DatePickerDialog(context,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        DetailViewActivity.sa_y = year;
                        DetailViewActivity.sa_m = monthOfYear + 1;
                        DetailViewActivity.sa_d = dayOfMonth;
//                        Log.e(TAG, "year:" + year + " monthOfYear:" + monthOfYear + " dayOfMonth:" + dayOfMonth);
                        int month = monthOfYear + 1;
                        String cur = DetailViewActivity.arr_lv_special.get(position);
                        choose_day = year + "-" + Cons.getfulldate(month) + "-" + Cons.getfulldate(dayOfMonth);
                        if (choose_day.trim().equals(get_cur_date.trim())) {
                        } else {
                            if (check_contain(choose_day)) {
                                AlertDialog.Builder adb = new AlertDialog.Builder(context);
                                adb.setTitle(context.getResources().getString(R.string.Dday));
                                adb.setMessage(context.getString(R.string.notify_chooseday));
                                adb.setPositiveButton(context.getResources().getString(R.string.ok), new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                });
                                adb.create().show();
                            } else {
                                DetailViewActivity.arr_lv_special.set(position, choose_day);
                                int _size = DetailViewActivity.arr_lv_special.size();
                                String temp = "";
                                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                                for (int i = 0; i < _size; i++) {
                                    String _date = DetailViewActivity.arr_lv_special.get(i);
                                    for (int j = 0; j <= i; j++) {
                                        String _date2 = DetailViewActivity.arr_lv_special.get(j);
                                        try {
                                            Date date1 = sdf.parse(_date);
                                            Date date2 = sdf.parse(_date2);
                                            if (date2.after(date1)) {
                                                temp = DetailViewActivity.arr_lv_special.get(i);
                                                DetailViewActivity.arr_lv_special.set(i, DetailViewActivity.arr_lv_special.get(j));
                                                DetailViewActivity.arr_lv_special.set(j, temp);
                                            }
                                        } catch (ParseException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }
                                DetailViewActivity.specialAdapter.notifyDataSetChanged();
                            }
                        }

                    }
                }, DetailViewActivity.sa_y, DetailViewActivity.sa_m - 1, DetailViewActivity.sa_d);
        dpd.show();
    }
}
