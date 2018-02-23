package com.dazone.crewdday.receiver;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.dazone.crewdday._Application;

public class AccountReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String senderPackageName = intent.getExtras().getString("senderPackageName");
        if (!senderPackageName.equals(context.getPackageName())) {
            Intent intentReceive = new Intent();
            intentReceive.setAction("com.dazone.crewcloud.account.receive");
            intentReceive.putExtra("senderPackageName", context.getPackageName());
            intentReceive.putExtra("receiverPackageName", senderPackageName);
            intentReceive.putExtra("companyID", _Application.getInstance().getPreferenceUtilities().getDomain());
            intentReceive.putExtra("userID", _Application.getInstance().getPreferenceUtilities().getUserId().replace("\"", ""));
            intentReceive.addFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);
            context.sendBroadcast(intentReceive);
        }
    }
}