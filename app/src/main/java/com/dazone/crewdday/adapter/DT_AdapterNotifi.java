package com.dazone.crewdday.adapter;

import android.app.TimePickerDialog;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import com.dazone.crewdday.Cons;
import com.dazone.crewdday.R;
import com.dazone.crewdday._Application;
import com.dazone.crewdday.activity.DetailViewActivity;
import com.dazone.crewdday.activity.HomeActivity;

import java.util.ArrayList;
import java.util.Calendar;


public class DT_AdapterNotifi extends BaseAdapter {
    String TAG = "DT_AdapterNotifi";
    ArrayList<NotifyTime> arrayList;
    Context context;
    int temp;
    int temp2 = 0;
    boolean canEdit;

    public DT_AdapterNotifi(Context context, ArrayList<NotifyTime> arrayList, int temp, boolean canEdit) {
        this.context = context;
        this.arrayList = arrayList;
        this.temp = temp;
        this.canEdit = canEdit;
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
        convertView = inflater.inflate(R.layout.layout_adapter_notify, null);
        Spinner sp_notify = (Spinner) convertView.findViewById(R.id.sp_notify);
        final TextView tv_notify = (TextView) convertView.findViewById(R.id.tv_notify);
        ImageView img_remove = (ImageView) convertView.findViewById(R.id.img_remove);
        final ImageView img_add = (ImageView) convertView.findViewById(R.id.img_add);
        ImageView img_notify = (ImageView) convertView.findViewById(R.id.img_notify);
        if (canEdit == false) {
            sp_notify.setClickable(false);
            sp_notify.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    if (event.getAction() == MotionEvent.ACTION_DOWN) {
                        DetailViewActivity.instance.cannot_edit();
                    }
                    return true;
                }
            });
            sp_notify.setBackgroundColor(Color.WHITE);
        }
        if (position == 0) {
            img_notify.setVisibility(View.VISIBLE);
        }
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
        int sp = arrayList.get(position).getNotificationType();
        String sp_text = arrayList.get(position).getNotificationTime();
        int hourOfDay = Integer.parseInt(sp_text.substring(0, 2));
        int minute = Integer.parseInt(sp_text.substring(2, 4));
        String AM = "AM";
        if (hourOfDay > 12) {
            AM = "PM";
            hourOfDay -= 12;
        } else {
            AM = "AM";
        }
        String time = Cons.getfulldate(hourOfDay) + ":" + Cons.getfulldate(minute) + " " + AM;
        tv_notify.setText(time);

//        ArrayAdapter notify_adapter = new ArrayAdapter<String>(context, R.layout.custom_text_size, _Application.getInstance().arr_notify());
        ArrayAdapter notify_adapter;// = new ArrayAdapter<String>(context, R.layout.custom_text_size, _Application.getInstance().arr_notify());
        if (HomeActivity.checkAlarm == 0) {
            notify_adapter = new ArrayAdapter<String>(context, R.layout.custom_text_size, _Application.getInstance().arr_notify());
        } else {
            notify_adapter = new ArrayAdapter<String>(context, R.layout.custom_text_size, _Application.getInstance().arr_notify_2());
        }
        notify_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_notify.setAdapter(notify_adapter);
        sp_notify.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int args, long id) {
//                if (DetailViewActivity.arr_adapter_notify.size() == 1) {
//                    if (args == 0)
//                        DetailViewActivity.instance.setMarginBottomListNotify(_Application.getInstance().getDimenInPx(R.dimen.returnmgbottom));
//                    else DetailViewActivity.instance.setMarginBottomListNotify(_Application.getInstance().getDimenInPx(R.dimen.add_mg_top));
//                }
//                temp2++;
//                if (temp2 > temp) DetailViewActivity.instance._display_item();
//                NotifyTime ob = DetailViewActivity.arr_adapter_notify.get(position);
//                ob.setNotificationType(args);
//                DetailViewActivity.arr_adapter_notify.set(position, ob);
//                if (DetailViewActivity.arr_adapter_notify.size() > 1) {
//                    if (args == 0) {
//                        DetailViewActivity.arr_adapter_notify.remove(position);
//                        DetailViewActivity.adapterNotify.notifyDataSetChanged();
//                        setheight_lv_month(DetailViewActivity.lv_notify);
//                    }
//                } else {
//                    if (args == 0) {
//                        tv_notify.setVisibility(View.INVISIBLE);
//                        img_add.setVisibility(View.GONE);
//                    } else {
//                        tv_notify.setVisibility(View.VISIBLE);
//                        img_add.setVisibility(View.VISIBLE);
//                    }
//                }

                temp2++;
                if (temp2 > temp) DetailViewActivity.instance._display_item();
                NotifyTime ob = DetailViewActivity.arr_adapter_notify.get(position);
                ob.setNotificationType(args);
                DetailViewActivity.arr_adapter_notify.set(position, ob);
                if (HomeActivity.checkAlarm == 0) {
                    if (DetailViewActivity.arr_adapter_notify.size() > 1) {
                        if (args == 0) {
                            DetailViewActivity.arr_adapter_notify.remove(position);
                            DetailViewActivity.adapterNotify.notifyDataSetChanged();
                            setheight_lv_month(DetailViewActivity.lv_notify);
                        }
                    } else {
                        if (args == 0) {
                            tv_notify.setVisibility(View.INVISIBLE);
                            img_add.setVisibility(View.GONE);
                        } else {
                            tv_notify.setVisibility(View.VISIBLE);
                            img_add.setVisibility(View.VISIBLE);
                        }
                    }
                } else {
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        sp_notify.setSelection(sp);
        tv_notify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!canEdit) DetailViewActivity.instance.cannot_edit();
                else
                    ShowTimerDialog(tv_notify, position);
            }
        });
        img_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!canEdit)
                    DetailViewActivity.instance.cannot_edit();
                else {
//                    NotifyTime ob = new NotifyTime();
//                    ob.setNotificationType(1);
//                    ob.setNotificationTime("0900");
//                    DetailViewActivity.arr_adapter_notify.add(ob);
//                    DetailViewActivity.adapterNotify.notifyDataSetChanged();
//                    setheight_lv_month(DetailViewActivity.lv_notify);
//                    DetailViewActivity.instance._display_item();

                    NotifyTime ob = new NotifyTime();
                    ob.setNotificationType(1);
                    ob.setNotificationTime("0900");
                    DetailViewActivity.arr_adapter_notify.add(ob);
                    DetailViewActivity.adapterNotify.notifyDataSetChanged();
                    setheight_lv_month(DetailViewActivity.lv_notify);
                    DetailViewActivity.instance._display_item();
                    if (HomeActivity.checkAlarm == 0) {
                        HomeActivity.checkAlarm = 1;
                        for (int i = 0; i < DetailViewActivity.arr_adapter_notify.size(); i++) {
                            DetailViewActivity.arr_adapter_notify.get(i).setNotificationType(DetailViewActivity.arr_adapter_notify.get(i).getNotificationType() - 1);
                        }
                    }
                }
            }
        });
        img_remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!canEdit)
                    DetailViewActivity.instance.cannot_edit();
                else {
//                    DetailViewActivity.arr_adapter_notify.remove(position);
//                    DetailViewActivity.adapterNotify.notifyDataSetChanged();
//                    setheight_lv_month(DetailViewActivity.lv_notify);
//                    DetailViewActivity.instance._display_item();

                    DetailViewActivity.arr_adapter_notify.remove(position);
                    if (DetailViewActivity.arr_adapter_notify.size() == 1){
                        HomeActivity.checkAlarm = 0;
                        for (int i = 0; i < DetailViewActivity.arr_adapter_notify.size(); i++) {
                            DetailViewActivity.arr_adapter_notify.get(i).setNotificationType(DetailViewActivity.arr_adapter_notify.get(i).getNotificationType() + 1);
                        }
                    }
                    DetailViewActivity.adapterNotify.notifyDataSetChanged();
                    setheight_lv_month(DetailViewActivity.lv_notify);
                    DetailViewActivity.instance._display_item();
                }
            }
        });
        return convertView;
    }

    public void ShowTimerDialog(final TextView tv, final int pos) {
        Calendar c = Calendar.getInstance();
        int mHour = c.get(Calendar.HOUR_OF_DAY);
        int mMinute = c.get(Calendar.MINUTE);
        TimePickerDialog timePickerDialog = new TimePickerDialog(context,
                new TimePickerDialog.OnTimeSetListener() {

                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        int hTemp=hourOfDay;
                        // hourOfDay = 0 -> 12 AM
                        // hourOfDay = 12 -> 12 PM
                        String AM = "AM";
                        if (hourOfDay > 12) {
                            AM = "PM";
                            hourOfDay -= 12;
                        } else {
                            AM = "AM";
                        }
                        String time = Cons.getfulldate(hourOfDay) + ":" + Cons.getfulldate(minute) + " " + AM;
                        tv.setText(time);
                        NotifyTime ob = DetailViewActivity.arr_adapter_notify.get(pos);
                        ob.setNotificationTime(Cons.getfulldate(hTemp) + Cons.getfulldate(minute));
                        DetailViewActivity.arr_adapter_notify.set(pos, ob);
                        DetailViewActivity.instance._display_item();
                    }
                }, mHour, mMinute, false);

        timePickerDialog.show();
    }

    public void setheight_lv_month(ListView lv_repeat) {
        int totalHeight = lv_repeat.getPaddingTop() + lv_repeat.getPaddingBottom();
        int desiredWidth = View.MeasureSpec.makeMeasureSpec(lv_repeat.getWidth(), View.MeasureSpec.AT_MOST);

        for (int i = 0; i < DetailViewActivity.adapterNotify.getCount(); i++) {
            View listItem = DetailViewActivity.adapterNotify.getView(i, null, lv_repeat);
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
