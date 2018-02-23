package com.dazone.crewdday.activity;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.dazone.crewdday.Cons;
import com.dazone.crewdday.GCM.RegistrationIntentService;
import com.dazone.crewdday.R;
import com.dazone.crewdday._Application;
import com.dazone.crewdday.mInterface.LoginCallback;
import com.dazone.crewdday.mInterface.LoginDto;
import com.dazone.crewdday.mInterface.LoginSuccessCallback;
import com.dazone.crewdday.model.AutoLoginObject;
import com.dazone.crewdday.model.SoftKeyboardDetectorView;
import com.dazone.crewdday.other.ErrorDto;
import com.dazone.crewdday.other.Statics;
import com.dazone.crewdday.util.ConnectionUtils;
import com.dazone.crewdday.util.DeviceUtilities;
import com.dazone.crewdday.util.PreferenceUtilities;
import com.dazone.crewdday.util.WebClient;
import com.fasterxml.jackson.databind.JsonNode;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.gson.Gson;

import java.io.IOException;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    String TAG = "LoginActivity";
    private final int MY_PERMISSIONS_REQUEST_CODE = 1;
    private final static int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
    GoogleCloudMessaging gcm;
    //        AtomicInteger msgId = new AtomicInteger();
    Context context;
    String regid;


    String notificationOptions = "";

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

    private boolean checkPlayServices() {
        int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
                GooglePlayServicesUtil.getErrorDialog(resultCode, this,
                        PLAY_SERVICES_RESOLUTION_REQUEST).show();
            } else {
                Log.e(TAG, "This device is not supported.");
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
//            Log.e(TAG, "regID:" + regID);
            preferenceUtilities.setGCMregistrationid(regid);
            ConnectionUtils.getInstance().InsertDevice(regid, notificationOptions);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode != MY_PERMISSIONS_REQUEST_CODE) {
            return;
        }

        boolean isGranted = true;

        for (int result : grantResults) {
            if (result != PackageManager.PERMISSION_GRANTED) {
                isGranted = false;
                break;
            }
        }

        if (isGranted) {
            startApplication();
        } else {
            finish();
        }
    }

    private void startApplication() {
        findViewsOfActivity();
        initViewsOfActivity();
    }

    private boolean checkPermissions() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            return false;
        }

        return true;
    }

    private void setPermissions() {
        ActivityCompat.requestPermissions(this, new String[]{
                Manifest.permission.WRITE_EXTERNAL_STORAGE
        }, MY_PERMISSIONS_REQUEST_CODE);
    }

    ProgressDialog progressDialog;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

//        if (checkPermissions()) {
//            startApplication();
//        } else {
//            setPermissions();
//        }

        startApplication();

        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.colorPrimaryDrark));
        }

        final SoftKeyboardDetectorView softKeyboardDetectorView = new SoftKeyboardDetectorView(this);
        addContentView(softKeyboardDetectorView, new FrameLayout.LayoutParams(-1, -1));

        softKeyboardDetectorView.setOnShownKeyboard(new SoftKeyboardDetectorView.OnShownKeyboardListener() {
            @Override
            public void onShowSoftKeyboard() {
                img_login_logo.setVisibility(View.GONE);
                ll_login_sign_up.setVisibility(View.GONE);

                RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) tv_login_logo_text.getLayoutParams();
                params.addRule(RelativeLayout.ALIGN_PARENT_TOP);
                tv_login_logo_text.setLayoutParams(params);
            }
        });

        softKeyboardDetectorView.setOnHiddenKeyboard(new SoftKeyboardDetectorView.OnHiddenKeyboardListener() {
            @Override
            public void onHiddenSoftKeyboard() {
                img_login_logo.setVisibility(View.VISIBLE);
                ll_login_sign_up.setVisibility(View.VISIBLE);

                RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) tv_login_logo_text.getLayoutParams();
                params.addRule(RelativeLayout.ALIGN_PARENT_TOP, 0);
                tv_login_logo_text.setLayoutParams(params);
            }
        });

        Intent intent = new Intent();
        intent.setAction("com.dazone.crewcloud.account.get");
        intent.putExtra("senderPackageName", this.getPackageName());
        intent.addFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);
        sendBroadcast(intent);

        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(BROADCAST_ACTION);
        registerReceiver(accountReceiver, intentFilter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(accountReceiver);
        accountReceiver = null;
    }

    public static String BROADCAST_ACTION = "com.dazone.crewcloud.account.receive";

    private BroadcastReceiver accountReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String receiverPackageName = intent.getExtras().getString("receiverPackageName");
            Log.d(TAG, "receiverPackageName:" + receiverPackageName);
            if (LoginActivity.this.getPackageName().equals(receiverPackageName)) {
                //String senderPackageName = intent.getExtras().getString("senderPackageName");
                String companyID = intent.getExtras().getString("companyID");
                String userID = intent.getExtras().getString("userID");
                if (!TextUtils.isEmpty(companyID) && !TextUtils.isEmpty(userID)) {
                    showDialogAutoLogin(companyID, userID);
                }
            }
        }
    };

    public void showDialogAutoLogin(final String companyID, final String UserID) {
        LayoutInflater inflater = getLayoutInflater();
        View alertLayout = inflater.inflate(R.layout.auto_login, null);
        TextView tvCompany = (TextView) alertLayout.findViewById(R.id.tvCompany);
        TextView tvUser = (TextView) alertLayout.findViewById(R.id.tvUser);
        tvCompany.setText(": " + companyID);
        tvUser.setText(": " + UserID);

        AlertDialog.Builder adb = new AlertDialog.Builder(this);
        adb.setTitle(getResources().getString(R.string.autoLogin));
        adb.setView(alertLayout);
        adb.setPositiveButton(getResources().getString(R.string.del_group_y), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                {
                    PreferenceUtilities mPreferenceUtilities = _Application.getInstance().getPreferenceUtilities();
                    String organizationId = companyID;
                    if (organizationId.contains(".")) {
                        if (organizationId.contains("crewcloud.net")) {
                            mPreferenceUtilities.setCurrentServiceDomain("http://www.crewcloud.net");
                            organizationId = organizationId.substring(0, organizationId.indexOf("."));
                            mPreferenceUtilities.setCurrentCompanyDomain(organizationId + ".crewcloud.net");
                        } else if (organizationId.contains("bizsw.co.kr")) {
                            if (organizationId.equals("bizsw.co.kr")) {
                                mPreferenceUtilities.setCurrentServiceDomain("http://www.bizsw.co.kr:8080");
                                mPreferenceUtilities.setCurrentCompanyDomain("vn." + organizationId + ":8080");
                            } else {
                                mPreferenceUtilities.setCurrentServiceDomain("http://www.bizsw.co.kr:8080");
                                mPreferenceUtilities.setCurrentCompanyDomain(organizationId + ":8080");
                            }
                        } else {
                            mPreferenceUtilities.setCurrentServiceDomain("http://" + organizationId);
                            mPreferenceUtilities.setCurrentCompanyDomain(organizationId);
                        }
                    } else {
                        mPreferenceUtilities.setCurrentServiceDomain("http://www.crewcloud.net");
                        mPreferenceUtilities.setCurrentCompanyDomain(organizationId + ".crewcloud.net");
                    }

                    WebClient.autoLogin(mPreferenceUtilities.getCurrentCompanyDomain(), UserID, mPreferenceUtilities.getCurrentServiceDomain(), new LoginSuccessCallback() {
                        @Override
                        public void onLoginSuccessCallback(String str) {
                            Log.d(TAG, "str:" + str);
                            AutoLoginObject ob = new Gson().fromJson(str, AutoLoginObject.class);
                            PreferenceUtilities preferenceUtilities = _Application.getInstance().getPreferenceUtilities();
                            preferenceUtilities.setCurrentCompanyNo(ob.getCompanyNo());
                            preferenceUtilities.setCurrentMobileSessionId(ob.getSession());
                            preferenceUtilities.setCurrentUserID(UserID);
                            preferenceUtilities.setUserName(ob.getFullName());
                            preferenceUtilities.setEmail(ob.getMailAddress());
                            preferenceUtilities.setUserId(ob.getUserID());
                            preferenceUtilities.setAvatar(ob.getAvatar());
                            preferenceUtilities.setCompanyName(ob.getNameCompany());
                            preferenceUtilities.setPermissionType(ob.getPermissionType());
                            preferenceUtilities.setId(ob.getId());
                            preferenceUtilities.setTIME_ZONE(DeviceUtilities.getTimeZoneOffset());
                            preferenceUtilities.setDomain(companyID);

                            createGCM();
                            Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                            startActivity(intent);
                            finish();
                        }

                        @Override
                        public void onLoginFailedCallback() {
                            Toast.makeText(LoginActivity.this, R.string.message_network_unstable, Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }
        });

        adb.setNegativeButton(getResources().getString(R.string.del_group_n), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        adb.create().show();
    }

    private AutoCompleteTextView et_login_organization;
    private EditText et_login_username, et_login_password;
    private RelativeLayout btn_login_start;
    private RelativeLayout login_btn_sign_up;
    private ImageView img_login_logo;
    private LinearLayout ll_login_sign_up;
    private TextView tv_login_logo_text;

    private void findViewsOfActivity() {
        Log.d(TAG, "findViewsOfActivity");
        progressDialog = new ProgressDialog(this);
//        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setMessage("Loading...");
        tv_login_logo_text = (TextView) findViewById(R.id.tv_login_logo_text);
        ll_login_sign_up = (LinearLayout) findViewById(R.id.ll_login_sign_up);
        img_login_logo = (ImageView) findViewById(R.id.img_login_logo);
        et_login_organization = (AutoCompleteTextView) findViewById(R.id.et_login_organization);
        et_login_username = (EditText) findViewById(R.id.et_login_username);
        et_login_password = (EditText) findViewById(R.id.et_login_password);
        btn_login_start = (RelativeLayout) findViewById(R.id.btn_login_start);
        login_btn_sign_up = (RelativeLayout) findViewById(R.id.login_btn_sign_up);
        login_btn_sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });


        PreferenceUtilities pref = _Application.getInstance().getPreferenceUtilities();
        String domain = pref.getDOMAIN_LOGIN();
        String userName = pref.getUSERNAME_LOGIN();
        String passWord = pref.getPASSWORD_LOGIN();

        if (domain.length() != 0 && userName.length() != 0 && passWord.length() != 0) {
            Log.d(TAG, "domain:" + domain);
            Log.d(TAG, "userName:" + userName);
            Log.d(TAG, "passWord:" + passWord);
            et_login_organization.setText(domain);
            et_login_username.setText(userName);
            et_login_password.setText(passWord);
        }

    }

    PreferenceUtilities preferenceUtilities;

    private void initViewsOfActivity() {
        Log.d(TAG, "initViewsOfActivity");
        preferenceUtilities = _Application.getInstance().getPreferenceUtilities();

//        et_login_organization.setText(preferenceUtilities.getCurrentCompanyDomain());
//        et_login_username.setText(preferenceUtilities.getCurrentUserID());


        btn_login_start.setOnClickListener(this);

        notificationOptions = "{" + "\"enabled\": true," +
                "\"sound\": true," +
                "\"vibrate\": true," +
                "\"notitime\": false," +
                "\"starttime\": \"08:00\"," +
                "\"endtime\": \"18:00\"" + "}";
        notificationOptions = notificationOptions.trim();
//        Log.e(TAG, notificationOptions);
    }

    @Override
    public void onClick(View view) {
        int idOfView = view.getId();

        if (idOfView == R.id.btn_login_start) {
            startLoginProcess();
        }
    }


    private String mLogin_userName = "";
    private String mLogin_password = "";
    private String server_site = "";
    private String company_domain = "";
    private String temp_server_site = "";

    private void startLoginProcess() {
        Log.d(TAG, "startLoginProcess");
        String organizationId = et_login_organization.getText().toString().toLowerCase();
        mLogin_userName = et_login_username.getText().toString().trim();
        mLogin_password = et_login_password.getText().toString().trim();
        server_site = et_login_organization.getText().toString().trim();

//        if (TextUtils.isEmpty(organizationId)) {
//            Toast.makeText(this, R.string.loginActivity_message_organizationId, Toast.LENGTH_LONG).show();
//            return;
//        } else if (TextUtils.isEmpty(mLogin_userName)) {
//            Toast.makeText(this, R.string.loginActivity_message_userName, Toast.LENGTH_LONG).show();
//            return;
//        } else if (TextUtils.isEmpty(mLogin_password)) {
//            Toast.makeText(this, R.string.loginActivity_message_password, Toast.LENGTH_LONG).show();
//            return;
//        }
//        PreferenceUtilities mPreferenceUtilities = _Application.getInstance().getPreferenceUtilities();
//        if (organizationId.contains(".")) {
//            if (organizationId.contains("crewcloud.net")) {
//                mPreferenceUtilities.setCurrentServiceDomain("http://www.crewcloud.net");
//                organizationId = organizationId.substring(0, organizationId.indexOf("."));
//                mPreferenceUtilities.setCurrentCompanyDomain(organizationId + ".crewcloud.net");
//            } else if (organizationId.contains("bizsw.co.kr")) {
//                if (organizationId.equals("bizsw.co.kr")) {
//                    mPreferenceUtilities.setCurrentServiceDomain("http://www.bizsw.co.kr:8080");
//                    mPreferenceUtilities.setCurrentCompanyDomain("vn." + organizationId + ":8080");
//                } else {
//                    mPreferenceUtilities.setCurrentServiceDomain("http://www.bizsw.co.kr:8080");
////                    Log.e(TAG, organizationId);
//                    mPreferenceUtilities.setCurrentCompanyDomain(organizationId + ":8080");
//                }
//            } else {
//                mPreferenceUtilities.setCurrentServiceDomain("http://" + organizationId);
//                mPreferenceUtilities.setCurrentCompanyDomain(organizationId);
//            }
//        } else {
//            mPreferenceUtilities.setCurrentServiceDomain("http://www.crewcloud.net");
//            mPreferenceUtilities.setCurrentCompanyDomain(organizationId + ".crewcloud.net");
//        }
//        new WebClientAsync_HasApplication_v2().execute();

        if (TextUtils.isEmpty(checkStringValue(server_site, mLogin_userName, mLogin_password))) {
            server_site = getServerSite(server_site);
            company_domain = server_site;

            if (!company_domain.startsWith("http")) {
                server_site = "http://" + server_site;
            }
            temp_server_site = server_site;
            if (temp_server_site.contains(".bizsw.co.kr")) {
                temp_server_site = "http://www.bizsw.co.kr:8080";
            } else {
                if (temp_server_site.contains("crewcloud")) {
                    temp_server_site = "http://www.crewcloud.net";
                }
            }
            Log.d(TAG, "company_domain:" + company_domain);
            Log.d(TAG, "temp_server_site:" + temp_server_site);
            progressDialog.show();
            WebClient.login(DeviceUtilities.getLanguageCode(),
                    DeviceUtilities.getTimeZoneOffset(), company_domain,
                    mLogin_userName, mLogin_password, DeviceUtilities.getOSVersion(),
                    temp_server_site, new LoginCallback() {
                        @Override
                        public void onSuccess(LoginDto loginDto) {
                            progressDialog.dismiss();
                            preferenceUtilities.setCurrentCompanyDomain(company_domain);
                            preferenceUtilities.setCurrentServiceDomain(temp_server_site);
                            loginSuccess(loginDto);
                        }

                        @Override
                        public void onFail(ErrorDto errorDto) {
                            Log.d(TAG, "errorDto:" + errorDto.message);
                            progressDialog.dismiss();
                            String error_msg = errorDto.message;

                            if (TextUtils.isEmpty(error_msg)) {
                                error_msg = getString(R.string.connection_falsed);
                            }

                            showSaveDialog(error_msg);
                        }
                    });
        } else {
            displayAddAlertDialog(getString(R.string.app_name), checkStringValue(server_site, mLogin_userName, mLogin_password), getString(R.string.ok), null,
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    }, null);
        }
    }

    void loginSuccess(LoginDto loginDto) {
//        Log.d(TAG,"loginSuccess:"+new Gson().toJson(loginDto));
        PreferenceUtilities preferenceUtilities = _Application.getInstance().getPreferenceUtilities();
        preferenceUtilities.setCurrentCompanyNo(loginDto.getCompanyNo());
        preferenceUtilities.setCurrentMobileSessionId(loginDto.getSession());
        preferenceUtilities.setCurrentUserID(mLogin_userName);
        preferenceUtilities.setUserName(loginDto.getFullName());
        preferenceUtilities.setEmail(loginDto.getMailAddress());
        preferenceUtilities.setUserId(loginDto.getUserID());
        preferenceUtilities.setAvatar(loginDto.getAvatar());
        preferenceUtilities.setCompanyName(loginDto.getNameCompany());
        preferenceUtilities.setPermissionType(loginDto.getPermissionType());
        Log.d(TAG, "loginDto.getId():" + loginDto.getId());
        preferenceUtilities.setId(loginDto.getId());
        preferenceUtilities.setTIME_ZONE(DeviceUtilities.getTimeZoneOffset());
        preferenceUtilities.setDomain(et_login_organization.getText().toString().toLowerCase());
        preferenceUtilities.setDOMAIN_LOGIN(et_login_organization.getText().toString().trim());
        preferenceUtilities.setUSERNAME_LOGIN(et_login_username.getText().toString().trim());
        preferenceUtilities.setPASSWORD_LOGIN(et_login_password.getText().toString().trim());
        createGCM();
        Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
        startActivity(intent);
        finish();
    }

    private String getServerSite(String server_site) {
        String[] domains = server_site.split("[.]");

        if (server_site.contains(".bizsw.co.kr") && !server_site.contains("8080")) {
            return server_site.replace(".bizsw.co.kr", ".bizsw.co.kr:8080");
        }

        if (domains.length <= 1 || server_site.contains("crewcloud")) {
            return domains[0] + ".crewcloud.net";
        } else {
            return server_site;
        }
    }

//    private class WebClientAsync_HasApplication_v2 extends AsyncTask<Void, Void, Void> {
//        private boolean mIsFailed;
//        private boolean mHasApplication;
//        private String mMessage;
//
//        @Override
//        protected Void doInBackground(Void... params) {
//            WebClient.HasApplication_v2(DeviceUtilities.getLanguageCode(),
//                    DeviceUtilities.getTimeZoneOffset(), _Application.getProjectCode(),
//                    "http://" + _Application.getInstance().getPreferenceUtilities().getCurrentCompanyDomain(), new WebClient.OnWebClientListener() {
//                        @Override
//                        public void onSuccess(JsonNode jsonNode) {
//                            Log.d(TAG, "onSuccess");
//                            try {
//                                mIsFailed = false;
//                                mHasApplication = jsonNode.get("HasApplication").asBoolean();
//                                mMessage = jsonNode.get("Message").asText();
//                            } catch (Exception e) {
//                                e.printStackTrace();
//
//                                mIsFailed = true;
//                                mHasApplication = false;
//                                mMessage = getString(R.string.message_network_unstable);
//                            }
//                        }
//
//                        @Override
//                        public void onFailure() {
//                            Log.d(TAG, "onFailure");
//                            mIsFailed = true;
//                            mHasApplication = false;
//                            mMessage = getString(R.string.message_network_unstable);
//                        }
//                    });
//
//            return null;
//        }
//
//        @Override
//        protected void onPostExecute(Void aVoid) {
//            super.onPostExecute(aVoid);
//
//            if (mIsFailed) {
//                Toast.makeText(LoginActivity.this, mMessage, Toast.LENGTH_LONG).show();
//            } else {
//                if (mHasApplication) {
//                    new WebClientAsync_Login_v2().execute();
//                } else {
//                    Toast.makeText(LoginActivity.this, mMessage, Toast.LENGTH_LONG).show();
//                }
//            }
//        }
//    }

//    private class WebClientAsync_Login_v2 extends AsyncTask<Void, Void, Void> {
//        private boolean mIsSuccess;
//        private String mErrorMessage;
//        private JsonNode mDataJsonNode;
//        private String mName;
//        private String mEmail;
//        private String mUserId;
//        private String mCompanyName;
//        private String mAvatar;
//        int PermissionType, id;
//
//        @Override
//        protected void onPreExecute() {
//            super.onPreExecute();
//            progressDialog.show();
//        }
//
//        @Override
//        protected Void doInBackground(Void... params) {
//            PreferenceUtilities preferenceUtilities = _Application.getInstance().getPreferenceUtilities();
//
//            WebClient.Login_v2(DeviceUtilities.getLanguageCode(),
//                    DeviceUtilities.getTimeZoneOffset(), preferenceUtilities.getCurrentCompanyDomain(),
//                    mLogin_userName, mLogin_password, DeviceUtilities.getOSVersion(),
//                    preferenceUtilities.getCurrentServiceDomain(), new WebClient.OnWebClientListener() {
//                        @Override
//                        public void onSuccess(JsonNode jsonNode) {
////                            Log.e(TAG, jsonNode.toString());
//                            try {
//                                mIsSuccess = (jsonNode.get("success").asInt() == 1);
//                                mName = (jsonNode.get(Cons.DATA).get(Cons.FULL_NAME).toString());
//                                mEmail = (jsonNode.get(Cons.DATA).get(Cons.MAIL_ADDRESS).toString());
//                                mAvatar = (jsonNode.get(Cons.DATA).get(Cons.AVATAR).toString());
//                                mUserId = (jsonNode.get(Cons.DATA).get(Cons.USER_ID).toString());
//                                mCompanyName = (jsonNode.get(Cons.DATA).get(Cons.COMPANY_NAME).toString());
//
//                                PermissionType = Integer.parseInt(jsonNode.get(Cons.DATA).get("PermissionType").toString());
//                                id = Integer.parseInt(jsonNode.get(Cons.DATA).get("Id").toString());
//                                if (!mIsSuccess) {
//                                    mErrorMessage = jsonNode.get("error").get("message").asText();
//                                } else {
//                                    mDataJsonNode = jsonNode.get("data");
//                                }
//                            } catch (Exception e) {
//                                e.printStackTrace();
//
//                                mIsSuccess = false;
//                                mErrorMessage = getString(R.string.message_network_unstable);
//                            }
//                        }
//
//                        @Override
//                        public void onFailure() {
//                            mIsSuccess = false;
//                            mErrorMessage = getString(R.string.message_network_unstable);
//                        }
//                    });
//
//            return null;
//        }
//
//        @Override
//        protected void onPostExecute(Void aVoid) {
//            super.onPostExecute(aVoid);
//            progressDialog.dismiss();
//
//            if (mIsSuccess) {
//                try {
////                    Log.d(TAG,"mDataJsonNode:"+new Gson().toJson(mDataJsonNode));
//                    PreferenceUtilities preferenceUtilities = _Application.getInstance().getPreferenceUtilities();
//                    preferenceUtilities.setCurrentCompanyNo(mDataJsonNode.get("CompanyNo").asInt());
//                    preferenceUtilities.setCurrentMobileSessionId(mDataJsonNode.get("session").asText());
//                    preferenceUtilities.setCurrentUserID(mLogin_userName);
//                    preferenceUtilities.setUserName(mName);
//                    preferenceUtilities.setEmail(mEmail);
//                    preferenceUtilities.setUserId(mUserId);
//                    preferenceUtilities.setAvatar(mAvatar);
//                    preferenceUtilities.setCompanyName(mCompanyName);
//                    preferenceUtilities.setPermissionType(PermissionType);
//                    preferenceUtilities.setId(id);
//                    preferenceUtilities.setTIME_ZONE(DeviceUtilities.getTimeZoneOffset());
//                    preferenceUtilities.setDomain(et_login_organization.getText().toString().toLowerCase());
//                    preferenceUtilities.setDOMAIN_LOGIN(et_login_organization.getText().toString().trim());
//                    preferenceUtilities.setUSERNAME_LOGIN(et_login_username.getText().toString().trim());
//                    preferenceUtilities.setPASSWORD_LOGIN(et_login_password.getText().toString().trim());
//
//                    createGCM();
//
//                    Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
//                    startActivity(intent);
//                    finish();
//                } catch (Exception e) {
//                    e.printStackTrace();
//                    Toast.makeText(LoginActivity.this, getString(R.string.message_network_unstable), Toast.LENGTH_LONG).show();
//                }
//            } else {
//                Toast.makeText(LoginActivity.this, mErrorMessage, Toast.LENGTH_LONG).show();
//            }
//        }
//    }

    private String checkStringValue(String server_site, String username, String password) {
        String result = "";
        if (TextUtils.isEmpty(server_site)) {
            if (TextUtils.isEmpty(result)) {
                result += getString(R.string.loginActivity_organizationID);
            } else {
                result += ", " + getString(R.string.loginActivity_organizationID);
            }
        }
        if (TextUtils.isEmpty(username)) {
            if (TextUtils.isEmpty(result)) {
                result += getString(R.string.login_username);
            } else {
                result += ", " + getString(R.string.login_username);
            }
        }
        if (TextUtils.isEmpty(password)) {
            if (TextUtils.isEmpty(result)) {
                result += getString(R.string.loginActivity_password);
            } else {
                result += ", " + getString(R.string.loginActivity_password);
            }
        }
        if (TextUtils.isEmpty(result)) {
            return result;
        } else {
            return result += " " + getString(R.string.login_empty_input);
        }
    }

    private void showSaveDialog(String message) {
        displayAddAlertDialog(getString(R.string.app_name), message, getString(R.string.ok), null,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                }, null);
    }

    public void displayAddAlertDialog(String title, String content, String positiveTitle, String negativeTitle, DialogInterface.OnClickListener positiveListener, DialogInterface.OnClickListener negativeListener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title).setMessage(content).setCancelable(false).setPositiveButton(positiveTitle, positiveListener).setNegativeButton(negativeTitle, negativeListener);
        builder.create().show();
    }
}