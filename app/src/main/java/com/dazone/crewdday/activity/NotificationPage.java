package com.dazone.crewdday.activity;

import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;

import com.dazone.crewdday.R;
import com.dazone.crewdday._Application;
import com.dazone.crewdday.util.ConnectionUtils;
import com.dazone.crewdday.util.PreferenceUtilities;

import java.util.Calendar;


public class NotificationPage extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener, View.OnClickListener {
    String TAG = "NotificationPage";
    View view_getNotify, view_sound, view_vibrate, view_Notifi_Time;
    TextView tv_getNoti, tv_sound, tv_vibrate, tv_time, tv_startHour, tv_endHour;
    Switch Switch_notify, Switch_sound, Switch_vibrate, Switch_time;
    PreferenceUtilities mPref;
    String regID = "", notificationOptions = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.notification_page);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

        init();
    }

    public void init() {
        mPref = _Application.getInstance().getPreferenceUtilities();
        regID = mPref.getGCMregistrationid();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        toolbar.setNavigationIcon(R.drawable.mnubar_ic_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        view_getNotify = (View) findViewById(R.id.view_getnotify);
        view_sound = (View) findViewById(R.id.view_sound);
        view_vibrate = (View) findViewById(R.id.view_vibrate);
        view_Notifi_Time = (View) findViewById(R.id.view_notifi_time);
        tv_getNoti = (TextView) view_getNotify.findViewById(R.id.tv_switch);
        tv_sound = (TextView) view_sound.findViewById(R.id.tv_switch);
        tv_vibrate = (TextView) view_vibrate.findViewById(R.id.tv_switch);
        tv_time = (TextView) view_Notifi_Time.findViewById(R.id.tv_switch);
        tv_startHour = (TextView) findViewById(R.id.tv_starthour);
        tv_endHour = (TextView) findViewById(R.id.tv_endhour);

        String _start = mPref.getSTART_TIME();
        String _end = mPref.getEND_TIME();

        if (_start.length() == 0) {
            _start = "AM 08:00";
            mPref.setSTART_TIME(_start);
        }

        if (_end.length() == 0) {
            _end = "PM 06:00";
            mPref.setEND_TIME(_end);
        }

        tv_startHour.setText(_start);
        tv_endHour.setText(_end);
        tv_startHour.setOnClickListener(this);
        tv_endHour.setOnClickListener(this);
        tv_getNoti.setText(getResources().getString(R.string.getnotify));
        tv_sound.setText(getResources().getString(R.string.sound));
        tv_vibrate.setText(getResources().getString(R.string.vibrate));
        tv_time.setText(getResources().getString(R.string.notifi_time));
        Switch_notify = (Switch) view_getNotify.findViewById(R.id.switch1);
        Switch_sound = (Switch) view_sound.findViewById(R.id.switch1);
        Switch_vibrate = (Switch) view_vibrate.findViewById(R.id.switch1);
        Switch_time = (Switch) view_Notifi_Time.findViewById(R.id.switch1);
        Switch_notify.setOnCheckedChangeListener(this);
        boolean flag = mPref.getNOTIFI_MAIL();
        Switch_notify.setChecked(flag);
        if(flag)
        {
            Switch_sound.setEnabled(true);
            Switch_vibrate.setEnabled(true);
            Switch_time.setEnabled(true);
        }
        else
        {
            Switch_sound.setEnabled(false);
            Switch_vibrate.setEnabled(false);
            Switch_time.setEnabled(false);
        }


        Switch_sound.setChecked(mPref.getNOTIFI_SOUND());
        Switch_vibrate.setChecked(mPref.getNOTIFI_VIBRATE());
        Switch_time.setChecked(mPref.getNOTIFI_TIME());
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (isChecked) {
            Log.d(TAG, "isChecked");
            Switch_sound.setEnabled(true);
            Switch_vibrate.setEnabled(true);
            Switch_time.setEnabled(true);
        } else {
            Switch_sound.setEnabled(false);
            Switch_vibrate.setEnabled(false);
            Switch_time.setEnabled(false);
            Log.d(TAG, "Not isChecked");
        }
    }

    @Override
    protected void onPause() {
        super.onPause();

        mPref.setNOTIFI_MAIL(Switch_notify.isChecked());
        mPref.setNOTIFI_SOUND(Switch_sound.isChecked());
        mPref.setNOTIFI_VIBRATE(Switch_vibrate.isChecked());
        mPref.setNOTIFI_TIME(Switch_time.isChecked());
        mPref.setSTART_TIME(tv_startHour.getText().toString().trim());
        mPref.setEND_TIME(tv_endHour.getText().toString().trim());

        notificationOptions = "{" +
                "\"enabled\": " + Switch_notify.isChecked() + "," +
                "\"sound\": " + Switch_sound.isChecked() + "," +
                "\"vibrate\": " + Switch_vibrate.isChecked() + "," +
                "\"notitime\": " + Switch_time.isChecked() + "," +
                "\"starttime\": \"" + getFullHour(tv_startHour) + "\"," +
                "\"endtime\": \"" + getFullHour(tv_endHour) + "\"" + "}";

        notificationOptions = notificationOptions.trim();

        ConnectionUtils.getInstance().UpdateAndroidDevice(regID, notificationOptions);
    }

    @Override
    public void onClick(View v) {
        if (v == tv_startHour) {
            ShowTimerDialog(tv_startHour, tv_endHour);
        } else if (v == tv_endHour) {
            ShowTimerDialogEnd(tv_endHour, tv_startHour);
        }
    }

    public void ShowTimerDialog(final TextView tv, final TextView tv2) {
        Calendar calendar = Calendar.getInstance();
        int mHour = calendar.get(Calendar.HOUR_OF_DAY);
        int mMinute = calendar.get(Calendar.MINUTE);
        TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                new TimePickerDialog.OnTimeSetListener() {

                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        int h = getHour(tv2);
                        int m = getMinute(tv2);
                        if (hourOfDay < h) {
                            tv.setText(getFullHour(hourOfDay, minute));
                        } else if (hourOfDay > h) {
                            tv.setText(getFullHour(hourOfDay, minute));
                            tv2.setText(getFullHour(hourOfDay, minute));
                        } else {
                            if (minute < m) {
                                tv.setText(getFullHour(hourOfDay, minute));
                            } else {
                                tv.setText(getFullHour(hourOfDay, minute));
                                tv2.setText(getFullHour(hourOfDay, minute));
                            }
                        }
                    }
                }, mHour, mMinute, false);
        timePickerDialog.show();
    }

    public void ShowTimerDialogEnd(final TextView tv, final TextView tv2) {
        Calendar calendar = Calendar.getInstance();
        int mHour = calendar.get(Calendar.HOUR_OF_DAY);
        int mMinute = calendar.get(Calendar.MINUTE);
        TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                new TimePickerDialog.OnTimeSetListener() {

                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        int h = getHour(tv2);
                        int m = getMinute(tv2);
                        if (hourOfDay > h) {
                            tv.setText(getFullHour(hourOfDay, minute));
                        } else if (hourOfDay < h) {
                            tv.setText(getFullHour(hourOfDay, minute));
                            tv2.setText(getFullHour(hourOfDay, minute));
                        } else {
                            if (minute > m) {
                                tv.setText(getFullHour(hourOfDay, minute));
                            } else {
                                tv.setText(getFullHour(hourOfDay, minute));
                                tv2.setText(getFullHour(hourOfDay, minute));
                            }
                        }
                    }
                }, mHour, mMinute, false);
        timePickerDialog.show();
    }

    public String getFullHour(int hour, int minute) {
        String str_h, str_m;
        String AM;

        if (hour > 12) {
            AM = "PM";
            hour -= 12;
        } else {
            AM = "AM";
        }

        if (hour < 10) str_h = "0" + hour;
        else str_h = "" + hour;

        if (minute < 10) str_m = "0" + minute;
        else str_m = "" + minute;

        String text = AM + " " + str_h + ":" + str_m;

        return text;
    }

    public int getHour(TextView tv) {
        int h;
        String str[] = tv.getText().toString().split(" ");
        h = Integer.parseInt(str[1].split(":")[0]);
        if (str[0].equalsIgnoreCase("PM")) h += 12;
        return h;
    }

    public int getMinute(TextView tv) {
        int h;
        String str[] = tv.getText().toString().split(" ");
        h = Integer.parseInt(str[1].split(":")[1]);
        return h;
    }

    public String getFullHour(TextView tv) {
        String hour, minute;
        int h = getHour(tv);
        int m = getMinute(tv);
        if (h < 10) hour = "0" + h;
        else hour = "" + h;
        if (m < 10) minute = "0" + m;
        else minute = "" + m;
        String text = hour + ":" + minute;
        return text;
    }
}