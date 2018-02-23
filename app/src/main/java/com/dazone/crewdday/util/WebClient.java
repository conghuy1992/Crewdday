package com.dazone.crewdday.util;

import android.text.TextUtils;
import android.util.Log;

import com.android.volley.Request;
import com.dazone.crewdday._Application;
import com.dazone.crewdday.mInterface.IF_UpdateDepartment_Enabled;
import com.dazone.crewdday.mInterface.IF_UpdateDepartment_Name;
import com.dazone.crewdday.mInterface.IF_UpdatePass;
import com.dazone.crewdday.mInterface.InsertDepartmentCallback;
import com.dazone.crewdday.mInterface.LoginCallback;
import com.dazone.crewdday.mInterface.LoginDto;
import com.dazone.crewdday.mInterface.LoginSuccessCallback;
import com.dazone.crewdday.other.ErrorDto;
import com.dazone.crewdday.other.WebServiceManager;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class WebClient {
    public static String TAG = "WebClient";
    private final static ObjectMapper mJSONObjectMapper = new ObjectMapper();
    private static final String AUTO_LOGIN = "/UI/WebService/WebServiceCenter.asmx/AutoLogin";
    private static final String SERVICE_URL_LOGIN_V2 = "/UI/WebService/WebServiceCenter.asmx/Login_v2";
    private static final String SERVICE_URL_LOGOUT_V2 = "/UI/WebService/WebServiceCenter.asmx/Logout_v2";
    private static final String SERVICE_URL_CHECK_SESSION_USER_V2 = "/UI/WebService/WebServiceCenter.asmx/CheckSessionUser_v2";
    private static final String SERVICE_URL_HAS_APPLICATION_V2 = "/UI/WebService/WebServiceCenter.asmx/HasApplication_v2";
    private static final String InsertDepartment = "/UI/WebService/WebServiceCenter.asmx/InsertDepartment";
    private static final String UpdateDepartment_Enabled = "/UI/WebService/WebServiceCenter.asmx/UpdateDepartment_Enabled";
    private static final String UpdateDepartment_Name = "/UI/WebService/WebServiceCenter.asmx/UpdateDepartment_Name";
    public static final String GetDepartmentsAlsoDisabled = "/UI/WebService/WebServiceCenter.asmx/GetDepartmentsAlsoDisabled";
    public static final String UpdateUser_Enabled = "/UI/WebService/WebServiceCenter.asmx/UpdateUser_Enabled";
    public static final String SERVICE_URL_UPDATE_PASSWORD = "/UI/WebService/WebServiceCenter.asmx/UpdatePassword";


    public interface OnWebClientListener {
        void onSuccess(JsonNode jsonNode);

        void onFailure();
    }

    public static void UpdateDepartment_Enabled(int departNo, String _domain, boolean enabled, final IF_UpdateDepartment_Enabled Callback) {
        String url = "http://" + _domain + UpdateDepartment_Enabled;
        Log.d(TAG, url);
        Map<String, Object> params = new HashMap<>();
        params.put("sessionId", _Application.getInstance().getPreferenceUtilities().getCurrentMobileSessionId());
        params.put("timeZoneOffset", DeviceUtilities.getTimeZoneOffset());
        params.put("languageCode", LanguageUtils.getPhoneLanguage());
        params.put("departNo", departNo);
        params.put("enabled", enabled);
        WebServiceManager webServiceManager = new WebServiceManager();
        webServiceManager.doJsonObjectRequest(Request.Method.POST, url, new JSONObject(params), new WebServiceManager.RequestListener<String>() {
            @Override
            public void onSuccess(String response) {
                Log.d(TAG, "UpdateDepartment_Enabled: " + response);
//                Cons.longInfo(TAG,response);
                Callback.onSuccess();
            }

            @Override
            public void onFailure(ErrorDto error) {
                Log.d(TAG, "UpdateDepartment_Enabled onFailure");
                Callback.onFail();
            }
        });
    }


    public static void UpdateDepartment_Name(int departNo, String name, String _domain, final IF_UpdateDepartment_Name Callback) {
        String url = "http://" + _domain + UpdateDepartment_Name;
        Log.d(TAG, url);
        Log.d(TAG, "departNo: " + departNo);
        Log.d(TAG, "name: " + name);
        Log.d(TAG, "_domain: " + _domain);

        Map<String, Object> params = new HashMap<>();
        params.put("sessionId", _Application.getInstance().getPreferenceUtilities().getCurrentMobileSessionId());
        params.put("timeZoneOffset", DeviceUtilities.getTimeZoneOffset());
        params.put("languageCode", LanguageUtils.getPhoneLanguage());
        params.put("departNo", departNo);
        params.put("name", name);
        WebServiceManager webServiceManager = new WebServiceManager();
        webServiceManager.doJsonObjectRequest(Request.Method.POST, url, new JSONObject(params), new WebServiceManager.RequestListener<String>() {
            @Override
            public void onSuccess(String response) {
                Log.d(TAG, "UpdateDepartment_Name: " + response);
                Callback.onSuccess();
            }

            @Override
            public void onFailure(ErrorDto error) {
                Log.d(TAG, "UpdateDepartment_Name onFailure");
                Callback.Fail();
            }
        });
    }

    public static void InsertDepartment(int parentNo, String name, String _domain, final InsertDepartmentCallback Callback) {
        String url = "http://" + _domain + InsertDepartment;
        Log.d(TAG, url);
        Log.d(TAG, "departNo: " + parentNo);
        Log.d(TAG, "name: " + name);
        Log.d(TAG, "_domain: " + _domain);

        Map<String, Object> params = new HashMap<>();
        params.put("sessionId", _Application.getInstance().getPreferenceUtilities().getCurrentMobileSessionId());
        params.put("timeZoneOffset", DeviceUtilities.getTimeZoneOffset());
        params.put("languageCode", LanguageUtils.getPhoneLanguage());
        params.put("parentNo", parentNo);
        params.put("name", name);
        WebServiceManager webServiceManager = new WebServiceManager();
        webServiceManager.doJsonObjectRequest(Request.Method.POST, url, new JSONObject(params), new WebServiceManager.RequestListener<String>() {
            @Override
            public void onSuccess(String response) {
                Log.d(TAG, "InsertDepartment: " + response);
                Callback.onInsertDepartmentSuccess();
            }

            @Override
            public void onFailure(ErrorDto error) {
                Log.e(TAG, "InsertDepartment onFailure");
                Callback.Fail();
            }
        });
    }


    public static void autoLogin(String companyDomain, String userID, String _domain, final LoginSuccessCallback onSuccess) {
        String url = _domain + AUTO_LOGIN;
        Log.d(TAG, url);
        Map<String, Object> params = new HashMap<>();
        params.put("languageCode", LanguageUtils.getPhoneLanguage());
        params.put("timeZoneOffset", DeviceUtilities.getTimeZoneOffset());
        params.put("companyDomain", companyDomain);
        params.put("userID", userID);
        params.put("mobileOSVersion", DeviceUtilities.getOSVersion());

        WebServiceManager webServiceManager = new WebServiceManager();
        webServiceManager.doJsonObjectRequest(Request.Method.POST, url, new JSONObject(params), new WebServiceManager.RequestListener<String>() {
            @Override
            public void onSuccess(String response) {
                onSuccess.onLoginSuccessCallback(response);
            }

            @Override
            public void onFailure(ErrorDto error) {
                onSuccess.onLoginFailedCallback();
            }
        });
    }

    private static String getJSONStringFromUrl(String methodUrl, Map<String, Object> params) {
        HttpURLConnection urlConnection = null;

        InputStream inputStream = null;
        InputStreamReader inputStreamReader = null;
        BufferedReader bufferedReader = null;

        try {
            URL url = new URL(methodUrl);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setDoOutput(true);
            urlConnection.setDoInput(true);
            urlConnection.setRequestMethod("POST");
            urlConnection.addRequestProperty("Content-Type", "application/json");

            OutputStream outputStream = urlConnection.getOutputStream();
            outputStream.write(mJSONObjectMapper.writeValueAsString(params).getBytes());
            outputStream.flush();
            outputStream.close();

            if (urlConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                inputStream = urlConnection.getInputStream();
                inputStreamReader = new InputStreamReader(inputStream);
                bufferedReader = new BufferedReader(inputStreamReader);

                StringBuilder sb = new StringBuilder();
                String s;

                while ((s = bufferedReader.readLine()) != null) {
                    sb.append(s);
                }

                return sb.toString();
            }

            return "";
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if (inputStreamReader != null) {
                try {
                    inputStreamReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if (urlConnection != null) {
                try {
                    urlConnection.disconnect();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        return "";
    }

    // ----------------------------------------------------------------------------------------------


    public static void Login_v2(String languageCode, int timeZoneOffset, String companyDomain, String userID, String password, String mobileOSVersion, String _domain, OnWebClientListener listener) {
        Map<String, Object> mapParams = new HashMap<>();
        mapParams.put("languageCode", languageCode);
        mapParams.put("timeZoneOffset", timeZoneOffset);
        mapParams.put("companyDomain", companyDomain);
        mapParams.put("userID", userID);
        mapParams.put("password", password);
        mapParams.put("mobileOSVersion", mobileOSVersion);

        String result = getJSONStringFromUrl(_domain + SERVICE_URL_LOGIN_V2, mapParams);
        Log.d("Login_v2", _domain + SERVICE_URL_LOGIN_V2);
        if (TextUtils.isEmpty(result)) {
            listener.onFailure();
        }

        try {
            listener.onSuccess(mJSONObjectMapper.readTree(result).get("d"));

        } catch (Exception e) {
            e.printStackTrace();
            listener.onFailure();
        }
    }

    public static void Logout_v2(String sessionId, String _domain, OnWebClientListener listener) {
        Map<String, Object> mapParams = new HashMap<>();
        mapParams.put("sessionId", sessionId);


        String result = getJSONStringFromUrl(_domain + SERVICE_URL_LOGOUT_V2, mapParams);


        if (TextUtils.isEmpty(result)) {
            listener.onFailure();
        }

        try {
            listener.onSuccess(mJSONObjectMapper.readTree(result).get("d"));
        } catch (Exception e) {
            e.printStackTrace();
            listener.onFailure();
        }
    }

    public static void CheckSessionUser_v2(String languageCode, int timeZoneOffset, String sessionId, String _domain, OnWebClientListener listener) {
        Map<String, Object> mapParams = new HashMap<>();
        mapParams.put("languageCode", languageCode);
        mapParams.put("timeZoneOffset", timeZoneOffset);
        mapParams.put("sessionId", sessionId);

        Log.d("sessionId", sessionId);
        Log.d("timeZoneOffset", timeZoneOffset + "");
        Log.d("languageCode", languageCode);

        String result = getJSONStringFromUrl(_domain + SERVICE_URL_CHECK_SESSION_USER_V2, mapParams);

        if (TextUtils.isEmpty(result)) {
            listener.onFailure();
        }

        try {
            listener.onSuccess(mJSONObjectMapper.readTree(result).get("d"));
        } catch (Exception e) {
            e.printStackTrace();
            listener.onFailure();
        }
    }

    public static void HasApplication_v2(String languageCode, int timeZoneOffset, String projectCode, String _domain, OnWebClientListener listener) {
        Map<String, Object> mapParams = new HashMap<>();
//        Log.e("HasApplication_v2",projectCode);
        mapParams.put("languageCode", languageCode);
        mapParams.put("timeZoneOffset", timeZoneOffset);
        mapParams.put("projectCode", projectCode);

        String result = getJSONStringFromUrl(_domain + SERVICE_URL_HAS_APPLICATION_V2, mapParams);
        Log.d("HasApplication_v2", _domain + SERVICE_URL_HAS_APPLICATION_V2);
        if (TextUtils.isEmpty(result)) {
            listener.onFailure();
        }

        try {
            listener.onSuccess(mJSONObjectMapper.readTree(result).get("d"));
        } catch (Exception e) {
            e.printStackTrace();
            listener.onFailure();
        }
    }

    public static void login(String languageCode, int timeZoneOffset, String companyDomain, String userID, String password,
                             String mobileOSVersion, String _domain, final LoginCallback callback) {
        Log.d(TAG, "companyDomain:" + companyDomain);
        Log.d(TAG, "_domain:" + _domain);
        final String url = _domain + SERVICE_URL_LOGIN_V2;
        Log.d(TAG, "Login_v2 url:" + url);
        Map<String, Object> params = new HashMap<>();
        params.put("languageCode", languageCode);
        params.put("timeZoneOffset", timeZoneOffset);
        params.put("companyDomain", companyDomain);
        params.put("userID", userID);
        params.put("password", password);
        params.put("mobileOSVersion", mobileOSVersion);

        WebServiceManager webServiceManager = new WebServiceManager();
        webServiceManager.doJsonObjectRequest_v2(Request.Method.POST, url, new JSONObject(params), new WebServiceManager.RequestListener<String>() {
            @Override
            public void onSuccess(String response) {
                Log.d(TAG, "login onSuccess:" + response);
                LoginDto dto = new Gson().fromJson(response, LoginDto.class);
                callback.onSuccess(dto);
            }

            @Override
            public void onFailure(ErrorDto error) {
                callback.onFail(error);
                Log.d(TAG, "login ErrorDto:" + error.message);
            }
        });
    }




}