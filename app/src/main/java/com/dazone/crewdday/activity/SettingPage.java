package com.dazone.crewdday.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.dazone.crewdday.BuildConfig;
import com.dazone.crewdday.R;
import com.dazone.crewdday._Application;
import com.dazone.crewdday.fragment.FragmentHomeAll;
import com.dazone.crewdday.fragment.FragmentHomePlus;
import com.dazone.crewdday.mInterface.InsertCoveredDaySuccess;
import com.dazone.crewdday.mInterface.onDeleteDeviceSuccess;
import com.dazone.crewdday.util.ConnectionUtils;
import com.dazone.crewdday.util.PreferenceUtilities;
import com.dazone.crewdday.util.WebClient;
import com.fasterxml.jackson.databind.JsonNode;
import com.nostra13.universalimageloader.core.ImageLoader;

/**
 * Created by maidinh on 30/5/2016.
 */
public class SettingPage extends AppCompatActivity implements View.OnClickListener {
    ImageView img_avatar;
    LinearLayout ln_profile, ln_general, ln_notify, ln_logout,ln_about;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting_page_layout);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.colorPrimaryDrark));
        }
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.mnubar_ic_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        ln_about = (LinearLayout) findViewById(R.id.ln_about);
        ln_profile = (LinearLayout) findViewById(R.id.ln_profile);
        ln_general = (LinearLayout) findViewById(R.id.ln_general);
        ln_notify = (LinearLayout) findViewById(R.id.ln_notify);
        ln_logout = (LinearLayout) findViewById(R.id.ln_logout);
        ln_profile.setOnClickListener(this);
        ln_general.setOnClickListener(this);
        ln_notify.setOnClickListener(this);
        ln_about.setOnClickListener(this);
        ln_logout.setOnClickListener(this);
        PreferenceUtilities prefUtils = _Application.getInstance().getPreferenceUtilities();
        String serviceDomain = prefUtils.getCurrentServiceDomain();
        String avatar = prefUtils.getAvatar();
        String newAvatar = avatar.replaceAll("\"", "");
        String mUrl = serviceDomain + newAvatar;
        ImageLoader imageLoader = ImageLoader.getInstance();
        img_avatar = (ImageView) findViewById(R.id.img_avatar);
        imageLoader.displayImage(mUrl, img_avatar);
    }

    @Override
    public void onClick(View v) {
        if (v == ln_profile) {
            Intent intent = new Intent(SettingPage.this, LogoutActivity.class);
            startActivity(intent);
        } else if (v == ln_general) {
            Toast.makeText(getApplicationContext(), "undev", Toast.LENGTH_SHORT).show();
        } else if (v == ln_notify) {
            Intent intent = new Intent(SettingPage.this, NotificationPage.class);
            startActivity(intent);
        } else if (v == ln_logout) {
            Log_out();
        }else if(v==ln_about)
        {
            showInfor();
        }
    }
    void showInfor() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getResources().getString(R.string.crewchat_version));

        String versionName = BuildConfig.VERSION_NAME;
        String user_version = getResources().getString(R.string.user_version) + " " + versionName;

        String msg = user_version;
        builder.setMessage(msg);

        builder.setPositiveButton(getResources().getString(R.string.confirm), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();

//        Button b = dialog.getButton(DialogInterface.BUTTON_POSITIVE);
//        if (b != null) {
//            b.setTextColor(ContextCompat.getColor(this, R.color.black));
//        }

    }
    public void Log_out() {
        AlertDialog.Builder adb = new AlertDialog.Builder(this);
        adb.setTitle(getResources().getString(R.string.Dday));
        adb.setMessage(getResources().getString(R.string.asklogout));
        adb.setPositiveButton(getResources().getString(R.string.ok), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ConnectionUtils.getInstance().DeleteDevice(new onDeleteDeviceSuccess() {
                    @Override
                    public void DeleteSuccess() {
                        new WebClientAsync_Logout_v2().execute();
                    }
                });
            }
        });
        adb.setNegativeButton(getResources().getString(R.string.cancel), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        adb.create().show();

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

            final PreferenceUtilities preferenceUtilities = _Application.getInstance().getPreferenceUtilities();

            preferenceUtilities.setCurrentMobileSessionId("");
            preferenceUtilities.setCurrentCompanyNo(0);
            preferenceUtilities.setCurrentServiceDomain("");
            preferenceUtilities.setCurrentCompanyDomain("");
            preferenceUtilities.setCurrentUserID("");
            preferenceUtilities.setSectionFragmentPlus("");
            preferenceUtilities.setSectionFragmentComplete("");
            preferenceUtilities.setSectionFragmentMinus("");
            preferenceUtilities.setSectionFragmentAll("");
            preferenceUtilities.setUserName("");
            preferenceUtilities.setEmail("");
            preferenceUtilities.setUserId("");
            preferenceUtilities.setAvatar("");
            preferenceUtilities.setCompanyName("");
            preferenceUtilities.setGCMregistrationid("");
            preferenceUtilities.setNOTIFI_MAIL(true);
            preferenceUtilities.setNOTIFI_SOUND(true);
            preferenceUtilities.setNOTIFI_VIBRATE(true);
            preferenceUtilities.setNOTIFI_TIME(false);
            preferenceUtilities.setSTART_TIME("");
            preferenceUtilities.setEND_TIME("");
            _Application.getInstance().removeshortcut();

            String domain = preferenceUtilities.getDOMAIN_LOGIN();
            String userName = preferenceUtilities.getUSERNAME_LOGIN();
            String passWord = preferenceUtilities.getPASSWORD_LOGIN();

            SharedPreferences pre = getSharedPreferences
                    ("CrewApproval_Prefs", MODE_PRIVATE);
            //tạo đối tượng Editor để lưu thay đổi
            SharedPreferences.Editor editor = pre.edit();
            editor.clear();

             PreferenceUtilities pref = _Application.getInstance().getPreferenceUtilities();
            pref.setDOMAIN_LOGIN(domain);
            pref.setUSERNAME_LOGIN(userName);
            pref.setPASSWORD_LOGIN(passWord);

            Intent intent = new Intent(SettingPage.this, LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
    }
}
