package com.dazone.crewdday.GCM;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.dazone.crewdday.Cons;
import com.dazone.crewdday.R;
import com.dazone.crewdday._Application;
import com.dazone.crewdday.activity.HomeActivity;
import com.dazone.crewdday.util.PreferenceUtilities;
import com.google.android.gms.gcm.GoogleCloudMessaging;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Calendar;
import java.util.Random;

public class GcmMessageHandler extends IntentService {
    String TAG = "GcmMessageHandler";
    String mes;
    private Handler handler;

    public GcmMessageHandler() {
        super("GcmMessageHandler");
    }

    @Override
    public void onCreate() {
        // TODO Auto-generated method stub
        super.onCreate();
        handler = new Handler();
    }

    @Override
    protected void onHandleIntent(final Intent intent) {
        Log.d(TAG,"onHandleIntent");
        Bundle extras = intent.getExtras();

//        StringBuilder sb = new StringBuilder(200);
//        for (String key : extras.keySet()) {
//            sb.append(key + ": " + extras.get(key).toString() + ", ");
//        }
//
//        Log.e(TAG, sb.toString());

        GoogleCloudMessaging gcm = GoogleCloudMessaging.getInstance(this);
        // The getMessageType() intent parameter must be the intent you received
        // in your BroadcastReceiver.
        String remainingDays = null;
        boolean sound = false;
        boolean vibrate = false;
        PreferenceUtilities prefUtils = new PreferenceUtilities();
        final String serviceDomain = prefUtils.getCurrentServiceDomain();
        String messageType = gcm.getMessageType(intent);

        if (extras.getString("badgeCount") != null) {
//            Log.e(TAG, "badgeCount" + extras.getString("badgeCount"));
            int badgeCount = Integer.parseInt(extras.getString("badgeCount"));
//            Log.e(TAG,"badgeCount:"+badgeCount);
            _Application.getInstance().shortcut(badgeCount);
            if (HomeActivity.instance != null) {
                HomeActivity.badgeCount = badgeCount;
            }
        } else {
            final String title = extras.getString("title");
            final String directorPhotoUrl = serviceDomain + extras.getString("directorPhotoUrl");
            String s = extras.getString("sound");
            String v = extras.getString("vibrate");
            if (s.equals("true")) sound = true;
            if (v.equals("true")) vibrate = true;

            String re = extras.getString("remainingDays");
            Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH) + 1;
            int date = calendar.get(Calendar.DAY_OF_MONTH);

            if (re.equals("0")) {
                remainingDays = "[D-Day] Today";
            } else if (re.equals("-1")) {
                date += 1;
                if (date > Cons.day_of_month(month)) {
                    date = 1;
                    month += 1;
                }
                if (month > 12) {
                    month = 1;
                    year += 1;
                }
                calendar.set(year, month - 1, date);
                int dayofweek = calendar.get(Calendar.DAY_OF_WEEK);

                String time = year + "-" + Cons.getfulldate(month) + "-" + Cons.getfulldate(date) + " " + get_dayofweek(dayofweek);
                remainingDays = "[D-1] " + time;
            } else if (re.equals("-2")) {
                date += 1;
                if (date > Cons.day_of_month(month)) {
                    date = 1;
                    month += 1;
                }
                if (month > 12) {
                    month = 1;
                    year += 1;
                }
                date += 1;
                if (date > Cons.day_of_month(month)) {
                    date = 1;
                    month += 1;
                }
                if (month > 12) {
                    month = 1;
                    year += 1;
                }
                calendar.set(year, month - 1, date);
                String time = year + "-" + Cons.getfulldate(month) + "-" + Cons.getfulldate(date) + " " + get_dayofweek(calendar.get(Calendar.DAY_OF_WEEK));
                remainingDays = "[D-2] " + time;
            }
            final String remain = remainingDays;
            final boolean so = sound;
            final boolean vi = vibrate;
            new Thread(new Runnable() {
                @Override
                public void run() {
                    new DownloadImageTask(title, remain, intent, so, vi).execute(directorPhotoUrl);
                }
            }).start();
        }
    }

    public void shownotify(String title, String msg, Bitmap bitmap, boolean sound, boolean vibrate) {
        if (vibrate) {
            Vibrator v = (Vibrator) this.getSystemService(Context.VIBRATOR_SERVICE);
            v.vibrate(800);
        }
        PendingIntent contentIntent;
        contentIntent = PendingIntent.getActivity(this, 0, new Intent(this, HomeActivity.class), 0);
        NotificationManager mNotificationManager = (NotificationManager)
                this.getSystemService(Context.NOTIFICATION_SERVICE);

        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.notification_dday)
                        .setLargeIcon(bitmap)
                        .setContentTitle(title)
                        .setStyle(new NotificationCompat.BigTextStyle()
                                .bigText(msg))
                        .setContentText(msg);
        mBuilder.setAutoCancel(true);
        if (sound)
            mBuilder.setDefaults(Notification.DEFAULT_SOUND);
        mBuilder.setContentIntent(contentIntent);
        Random random = new Random();
        int m = random.nextInt(9999 - 1000) + 1000;
        mNotificationManager.notify(m, mBuilder.build());

    }

    public static InputStream input;

    class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {

        Bitmap bitmapOrg;
        String title, remainingDays;
        Intent intent;
        boolean sound, vibrate;

        public DownloadImageTask(String title, String remainingDays, Intent intent, boolean sound, boolean vibrate) {
            this.title = title;
            this.remainingDays = remainingDays;
            this.intent = intent;
            this.sound = sound;
            this.vibrate = vibrate;
        }

        protected Bitmap doInBackground(String... urls) {
            try {
                URL url = new URL(urls[0]);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setDoInput(true);
                connection.connect();
                input = connection.getInputStream();
                bitmapOrg = BitmapFactory.decodeStream(input);
            } catch (Exception e) {
//                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return bitmapOrg;
        }

        protected void onPostExecute(final Bitmap result) {
            shownotify(title, remainingDays, result, sound, vibrate);
            GcmBroadcastReceiver.completeWakefulIntent(intent);
        }
    }

    public String get_dayofweek(int day_of_week) {
        String dayofweek = "";
        if (day_of_week == 1) dayofweek = "(" + getResources().getString(R.string.sunday) + ")";
        else if (day_of_week == 2)
            dayofweek = "(" + getResources().getString(R.string.monday) + ")";
        else if (day_of_week == 3)
            dayofweek = "(" + getResources().getString(R.string.tuesday) + ")";
        else if (day_of_week == 4)
            dayofweek = "(" + getResources().getString(R.string.wednesday) + ")";
        else if (day_of_week == 5)
            dayofweek = "(" + getResources().getString(R.string.thursday) + ")";
        else if (day_of_week == 6)
            dayofweek = "(" + getResources().getString(R.string.friday) + ")";
        else if (day_of_week == 7)
            dayofweek = "(" + getResources().getString(R.string.saturday) + ")";
        return dayofweek;
    }
}