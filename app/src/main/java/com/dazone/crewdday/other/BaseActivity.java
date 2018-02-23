package com.dazone.crewdday.other;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;

import com.dazone.crewdday.R;
import com.dazone.crewdday._Application;
import com.dazone.crewdday.util.PreferenceUtilities;


public abstract class BaseActivity extends AppCompatActivity {
    protected Context mContext;
    public static BaseActivity Instance = null;
    public PreferenceUtilities prefs;
    private ProgressDialog mProgressDialog;
    protected String server_site;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        mContext = this;
        Instance = this;
        prefs = _Application.getInstance().getPreferenceUtilities();
        server_site = prefs.getCurrentServiceDomain();
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.colorPrimaryDrark));
        }
    }
    @Override
    protected void onResume() {
        super.onResume();
        Instance = this;
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

 /*
    public void showNetworkDialog()
    {
        if(customDialog==null||!customDialog.isShowing()) {
            if (Utils.isWifiEnable()) {
                showAlertDialog(getString(R.string.app_name), getString(R.string.no_connection_error),
                        getString(R.string.string_ok), null, new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                customDialog.dismiss();
                                finish();
                            }
                        }, null);
            } else {
                showAlertDialog(getString(R.string.app_name), getString(R.string.no_wifi_error),
                        getString(R.string.turn_wifi_on), getString(R.string.string_cancel), new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent wireLess = new Intent(
                                        Settings.ACTION_WIFI_SETTINGS);
                                startActivity(wireLess);
                                customDialog.dismiss();
                            }
                        }, new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                customDialog.dismiss();
                                finish();

                            }
                        });
            }
        }
    }*/

    /*
    @Override
    public void onBackPressed() {

        if(!isTaskRoot()) {
            super.onBackPressed();
        }
        else{
            if(mIsExit){
                super.onBackPressed();
            }else{
                // press 2 times to exit app feature
                this.mIsExit = true;
                Toast.makeText(this, R.string.quit_confirm, Toast.LENGTH_SHORT).show();
                myHandler.postDelayed(myRunnable, 2000);
            }
        }

    }*/

    private boolean mIsExit;
    private Handler myHandler = new Handler();
    private Runnable myRunnable = new Runnable() {
        public void run() {
            mIsExit = false;
        }
    };
}

