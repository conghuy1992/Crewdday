package com.dazone.crewdday.GCM;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

import com.dazone.crewdday._Application;
import com.dazone.crewdday.activity.IntroActivity;
import com.dazone.crewdday.util.ConnectionUtils;
import com.dazone.crewdday.util.PreferenceUtilities;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.android.gms.iid.InstanceID;

public class RegistrationIntentService extends IntentService {
    String TAG = "RegistrationIntentService";
    public static final String SENDER_ID = "731703049967";

    public RegistrationIntentService() {
        super("RegistrationIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        try {
            InstanceID instanceID = InstanceID.getInstance(this);
            String token = instanceID.getToken(SENDER_ID, GoogleCloudMessaging.INSTANCE_ID_SCOPE, null);
            PreferenceUtilities mPref = _Application.getInstance().getPreferenceUtilities();
            mPref.setGCMregistrationid(token);
            ConnectionUtils.getInstance().InsertDevice(token, IntroActivity.notificationOptions);
        } catch (Exception e) {
            e.printStackTrace();
            ConnectionUtils.getInstance().InsertDevice("", "");
        }
    }
}