package com.dazone.crewdday.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.TextView;
import android.widget.Toast;

import com.dazone.crewdday.Cons;
import com.dazone.crewdday.GCM.RegistrationIntentService;
import com.dazone.crewdday.R;
import com.dazone.crewdday._Application;
import com.dazone.crewdday.other.Statics;
import com.dazone.crewdday.other.Utils;
import com.dazone.crewdday.util.ConnectionUtils;
import com.dazone.crewdday.util.DeviceUtilities;
import com.dazone.crewdday.util.PreferenceUtilities;
import com.dazone.crewdday.util.WebClient;
import com.fasterxml.jackson.databind.JsonNode;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.gcm.GoogleCloudMessaging;

import java.io.IOException;
import java.lang.ref.WeakReference;

public class IntroActivity extends AppCompatActivity {
    TextView nameapp;

    public int getHour(TextView tv) {
        int h = 0;
        String str[] = tv.getText().toString().trim().split(" ");
//        Log.e(TAG, str[1].split(":")[0]);
        h = Integer.parseInt(str[1].split(":")[0]);
        if (str[0].equalsIgnoreCase("PM")) h += 12;
        return h;
    }

    public int getMinute(TextView tv) {
        return Integer.parseInt(tv.getText().toString().split(" ")[1].split(":")[1]);
    }

    public String getFullHour(TextView tv) {
        String hour = "", minute = "";
        int h = getHour(tv);
        int m = getMinute(tv);
        if (h < 10) hour = "0" + h;
        else hour = "" + h;
        if (m < 10) minute = "0" + m;
        else minute = "" + m;
        String text = hour + ":" + minute;
        return text;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.colorPrimaryDrark));
        }
        if (Utils.isNetworkAvailable()) {
            _start();
        } else {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(IntroActivity.this, R.string.message_network_checking, Toast.LENGTH_LONG).show();
                    finish();
                }
            }, 1000);
        }
    }

    public void _start() {
        TextView tv1 = (TextView) findViewById(R.id.tv1);
        TextView tv2 = (TextView) findViewById(R.id.tv2);
        preferenceUtilities = _Application.getInstance().getPreferenceUtilities();

        String s = preferenceUtilities.getSTART_TIME();
        String s2 = preferenceUtilities.getEND_TIME();

        if (s.length() == 0) {
            s = "AM 08:00";
            preferenceUtilities.setSTART_TIME(s);
        }
        if (s2.length() == 0) {
            s2 = "PM 06:00";
            preferenceUtilities.setEND_TIME(s2);
        }
        tv1.setText(s);
        tv2.setText(s2);

        notificationOptions = "{" +
                "\"enabled\": " + preferenceUtilities.getNOTIFI_MAIL() + "," +
                "\"sound\": " + preferenceUtilities.getNOTIFI_SOUND() + "," +
                "\"vibrate\": " + preferenceUtilities.getNOTIFI_VIBRATE() + "," +
                "\"notitime\": " + preferenceUtilities.getNOTIFI_TIME() + "," +
                "\"starttime\": \"" + getFullHour(tv1) + "\"," +
                "\"endtime\": \"" + getFullHour(tv2) + "\"" + "}";
        notificationOptions = notificationOptions.trim();

        nameapp = (TextView) findViewById(R.id.nameapp);
        Typeface tf = Typeface.createFromAsset(getAssets(),
                "fonts/Roboto-Regular.ttf");
        nameapp.setTypeface(tf);
        // for test checkout file
//        Thread thread = new Thread(new UpdateRunnable());
//        thread.setDaemon(true);
//        thread.start();

        //for update play store
//        mActivityHandler.sendEmptyMessageDelayed(2, 1000);
        startApplication();
    }

    private final ActivityHandler mActivityHandler = new ActivityHandler(this);

    private static class ActivityHandler extends Handler {
        private final WeakReference<IntroActivity> mWeakActivity;

        public ActivityHandler(IntroActivity activity) {
            mWeakActivity = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            final IntroActivity activity = mWeakActivity.get();

            if (activity != null) {
                if (msg.what == 2) {
                    activity.handlerUpdate();
                }
            }
        }
    }

    public void handlerUpdate() {
        if (!flag) {
            existIdLogin = 0;

            Intent intentGo = new Intent(IntroActivity.this, WelcomeActivity.class);
            intentGo.putExtra(Cons.exist_Id_Login, existIdLogin);
            startActivity(intentGo);
            finish();
        }
    }

    // ----------------------------------------------------------------------------------------------

    boolean autoLogin = false;

    private void startApplication() {
        PreferenceUtilities preferenceUtilities = _Application.getInstance().getPreferenceUtilities();

        if (!TextUtils.isEmpty(preferenceUtilities.getCurrentMobileSessionId())) {
            new WebClientAsync_HasApplication_v2().execute();
            createGCM();
//            Log.e(TAG, "currentMobileSessionId != null");
            autoLogin = true;

        } else {
            autoLogin = false;
//            Log.e(TAG, "currentMobileSessionId = null");
            preferenceUtilities.setCurrentMobileSessionId("");
            preferenceUtilities.setCurrentCompanyNo(0);

//            Intent intent = new Intent(this, LoginActivity.class);

//            Intent intentGo = new Intent(IntroActivity.this, WelcomeActivity.class);
//            intentGo.putExtra(Cons.exist_Id_Login, existIdLogin);
//            startActivity(intentGo);
//            finish();

            Intent intent = new Intent();
            intent.setAction("com.dazone.crewcloud.account.get");
            intent.putExtra("senderPackageName", this.getPackageName());
            intent.addFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);
            sendBroadcast(intent);
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction(BROADCAST_ACTION);
            registerReceiver(br, intentFilter);
            mActivityHandler.sendEmptyMessageDelayed(2, 1000);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (!autoLogin) {
            if (br != null) {
                try {
                    unregisterReceiver(br);
                } catch(Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    int existIdLogin = 0;
    boolean flag = false;
    public static String BROADCAST_ACTION = "com.dazone.crewcloud.account.receive";

    BroadcastReceiver br = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String receiverPackageName = intent.getExtras().getString("receiverPackageName");
            if (receiverPackageName.equals(IntroActivity.this.getPackageName())) {
                flag = true;
                String senderPackageName = intent.getExtras().getString("senderPackageName");
                String companyID = intent.getExtras().getString("companyID");
                String userID = intent.getExtras().getString("userID");
                if (!TextUtils.isEmpty(companyID) && !TextUtils.isEmpty(userID)) {
                    existIdLogin = 1;
                    Intent intentGo = new Intent(IntroActivity.this, WelcomeActivity.class);
                    intentGo.putExtra(Cons.exist_Id_Login, existIdLogin);
                    startActivity(intentGo);
                    finish();
                } else {
                    existIdLogin = 0;
                    Intent intentGo = new Intent(IntroActivity.this, WelcomeActivity.class);
                    intentGo.putExtra(Cons.exist_Id_Login, existIdLogin);
                    startActivity(intentGo);
                    finish();
                }
            }
            unregisterReceiver(br);
            br = null;
        }
    };

    private class WebClientAsync_HasApplication_v2 extends AsyncTask<Void, Void, Void> {
        private boolean mIsFailed;
        private boolean mHasApplication;
        private String mMessage;

        @Override
        protected Void doInBackground(Void... params) {
            PreferenceUtilities preferenceUtilities = _Application.getInstance().getPreferenceUtilities();

            WebClient.HasApplication_v2(DeviceUtilities.getLanguageCode(), DeviceUtilities.getTimeZoneOffset(), _Application.getProjectCode(),
                    "http://" + preferenceUtilities.getCurrentCompanyDomain(), new WebClient.OnWebClientListener() {
                        @Override
                        public void onSuccess(JsonNode jsonNode) {
                            try {
                                mIsFailed = false;
                                mHasApplication = jsonNode.get("HasApplication").asBoolean();
                                mMessage = jsonNode.get("Message").asText();
                            } catch (Exception e) {
                                e.printStackTrace();

                                mIsFailed = true;
                                mHasApplication = false;
                                mMessage = getString(R.string.message_network_unstable);
                            }
                        }

                        @Override
                        public void onFailure() {
                            mIsFailed = true;
                            mHasApplication = false;
                            mMessage = getString(R.string.message_network_unstable);
                        }
                    });

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            if (mIsFailed) {
                Toast.makeText(IntroActivity.this, mMessage, Toast.LENGTH_LONG).show();
                finish();
            } else {
                if (mHasApplication) {
                    new WebClientAsync_CheckSessionUser_v2().execute();
                } else {
                    Toast.makeText(IntroActivity.this, mMessage, Toast.LENGTH_LONG).show();
                }
            }
        }
    }

    private class WebClientAsync_CheckSessionUser_v2 extends AsyncTask<Void, Void, Void> {
        private boolean mIsFailed;
        private boolean mIsSuccess;
        private String mName;
        private String mEmail;
        private String mAvatar;
        private String mUserId;
        private String mCompanyName;

        @Override
        protected Void doInBackground(Void... params) {
            PreferenceUtilities preferenceUtilities = _Application.getInstance().getPreferenceUtilities();

            WebClient.CheckSessionUser_v2(DeviceUtilities.getLanguageCode(),
                    DeviceUtilities.getTimeZoneOffset(), preferenceUtilities.getCurrentMobileSessionId(),
                    "http://" + preferenceUtilities.getCurrentCompanyDomain(),
                    new WebClient.OnWebClientListener() {
                        @Override
                        public void onSuccess(JsonNode jsonNode) {
                            mIsFailed = false;

                            try {
                                mName = (jsonNode.get(Cons.DATA).get(Cons.FULL_NAME).toString());
                                mEmail = (jsonNode.get(Cons.DATA).get(Cons.MAIL_ADDRESS).toString());
                                mAvatar = (jsonNode.get(Cons.DATA).get(Cons.AVATAR).toString());
                                mUserId = (jsonNode.get(Cons.DATA).get(Cons.USER_ID).toString());
                                mCompanyName = (jsonNode.get(Cons.DATA).get(Cons.COMPANY_NAME).toString());
                                mIsSuccess = (jsonNode.get("success").asInt() == 1);
                            } catch (Exception e) {
                                e.printStackTrace();
                                mIsSuccess = false;
                            }
                        }

                        @Override
                        public void onFailure() {
                            mIsFailed = true;
                            mIsSuccess = false;
                        }
                    });

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            if (mIsSuccess) {
//                Log.e("mIsSuccess", "mIsSuccess");
                Intent intent = new Intent(IntroActivity.this, HomeActivity.class);
                PreferenceUtilities preferenceUtilities = _Application.getInstance().getPreferenceUtilities();
                preferenceUtilities.setUserName(mName);
                preferenceUtilities.setEmail(mEmail);
                preferenceUtilities.setUserId(mUserId);
                preferenceUtilities.setAvatar(mAvatar);
                preferenceUtilities.setCompanyName(mCompanyName);
                startActivity(intent);
                finish();
            } else {
//                Log.e("mIsSuccess", "!mIsSuccess");
                PreferenceUtilities preferenceUtilities = _Application.getInstance().getPreferenceUtilities();

                preferenceUtilities.setCurrentMobileSessionId("");
                preferenceUtilities.setCurrentCompanyNo(0);

//                Intent intent = new Intent(IntroActivity.this, LoginActivity.class);
                Intent intent = new Intent(IntroActivity.this, WelcomeActivity.class);
                startActivity(intent);
                finish();
            }
        }
    }

    private class WebClientAsync_Logout_v2 extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... params) {
            PreferenceUtilities preferenceUtilities = _Application.getInstance().getPreferenceUtilities();

            WebClient.Logout_v2(preferenceUtilities.getCurrentMobileSessionId(),
                    "http://" + preferenceUtilities.getCurrentCompanyDomain(), new WebClient.OnWebClientListener() {
                        @Override
                        public void onSuccess(JsonNode jsonNode) {
                        }

                        @Override
                        public void onFailure() {
                        }
                    });

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            PreferenceUtilities preferenceUtilities = _Application.getInstance().getPreferenceUtilities();
            preferenceUtilities.setCurrentMobileSessionId("");
            preferenceUtilities.setCurrentCompanyNo(0);
            preferenceUtilities.setCurrentServiceDomain("");
            preferenceUtilities.setCurrentCompanyDomain("");
            preferenceUtilities.setCurrentUserID("");

            finish();
        }
    }

    private final static int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;

    private boolean checkPlayServices() {
        int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
                GooglePlayServicesUtil.getErrorDialog(resultCode, this,
                        PLAY_SERVICES_RESOLUTION_REQUEST).show();
            } else {
                finish();
            }
            return false;
        }
        return true;
    }

    private void registerInBackground() {
        new register().execute("");
    }

    String msg = "";

    public class register extends AsyncTask<String, Void, Void> {
        @Override
        protected void onPreExecute() {
        }

        @Override
        protected Void doInBackground(String... params) {
            try {
                if (gcm == null) {
                    gcm = GoogleCloudMessaging.getInstance(context);
                }
                regid = gcm.register(Statics.GOOGLE_SENDER_ID);
                msg = "Device registered, registration ID=" + regid;
            } catch (IOException ex) {
                msg = "Error :" + ex.getMessage();
            }
            return null;
        }

        protected void onPostExecute(Void unused) {
            preferenceUtilities.setGCMregistrationid(regid);
            ConnectionUtils.getInstance().InsertDevice(regid, notificationOptions);
        }
    }

    PreferenceUtilities preferenceUtilities;
    GoogleCloudMessaging gcm;
    String regid;
    Context context;
    public static String notificationOptions = "";

    private void createGCM() {
        context = getApplicationContext();
        if (checkPlayServices()) {
//            gcm = GoogleCloudMessaging.getInstance(this);
//            regID = preferenceUtilities.getGCMregistrationid();
//            if (regID.isEmpty()) {
//                registerInBackground();
//            } else {
//                ConnectionUtils.InsertDevice(regID, notificationOptions);
//            }
            Intent intent = new Intent(this, RegistrationIntentService.class);
            startService(intent);
        } else {
//            dismissProgressDialog();
//            callActivity(MainActivity.class);
//            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        }
    }
}