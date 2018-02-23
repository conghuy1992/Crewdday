package com.dazone.crewdday.other;

import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.dazone.crewdday.R;
import com.dazone.crewdday._Application;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class WebServiceManager<T> {
    String TAG = "WebServiceManager";
    private Map<String, String> mHeaders;

    private Request.Priority mPriority;

    public WebServiceManager() {
    }

    WebServiceManager(Map<String, String> headers, Request.Priority priority) {
        mHeaders = headers;
        mPriority = priority;
    }

    public void doJsonObjectRequest(int requestMethod, final String url, final JSONObject bodyParam, final RequestListener<String> listener) {
//        Log.d(TAG, "bodyParam:" + bodyParam);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(requestMethod, url, bodyParam, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d(TAG, "response:" + response);
                try {
//                    if(url.contains(OAUTHUrls.URL_GET_LOGIN)||url.contains(OAUTHUrls.URL_CHECK_DEVICE_TOKEN)
//                            ||url.contains(OAUTHUrls.URL_CHECK_SESSION)||url.contains(OAUTHUrls.URL_INSERT_PHONE_TOKEN)
//                            ||url.contains(OAUTHUrls.URL_LOG_OUT))
//                    {
                    response = new JSONObject(response.getString("d"));

//                    }
                    int isSuccess = response.getInt("success");
                    if (isSuccess == 1) {
                        listener.onSuccess(response.getString("data"));
                    } else {
                        ErrorDto errorDto = new Gson().fromJson(response.getString("error"), ErrorDto.class);
                        if (errorDto == null) {

                            errorDto = new ErrorDto();
                            errorDto.message = Utils.getString(R.string.no_network_error);
                        } else {
                            if (errorDto.code == 0 && !url.contains(OAUTHUrls.URL_CHECK_DEVICE_TOKEN) && !url.contains(OAUTHUrls.URL_CHECK_SESSION)
                                    && !url.contains(OAUTHUrls.URL_INSERT_PHONE_TOKEN)) {
                                // new Prefs().putBooleanValue(Statics.PREFS_KEY_SESSION_ERROR,true);
                                // CrewScheduleApplication.getInstance().getmPrefs().clearLogin();
                                //BaseActivity.Instance.startNewActivity(LoginActivity.class);
                            }
                        }

                        listener.onFailure(errorDto);
                    }

                } catch (JSONException e) {

                    ErrorDto errorDto = new ErrorDto();
                    errorDto.message = Utils.getString(R.string.no_network_error);
                    listener.onFailure(errorDto);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                ErrorDto errorDto = new ErrorDto();
                if (null != error) {
                    Log.d(TAG, "onErrorResponse");
                    listener.onFailure(errorDto);
                }

                if (null != error && null != error.networkResponse) {
                }
                if (null != error && null != error.networkResponse) {
                }
                if (null != error && null != error.networkResponse
                        && error.networkResponse.statusCode == HttpURLConnection.HTTP_UNAUTHORIZED) {
                    errorDto.unAuthentication = true;
                    listener.onFailure(errorDto);
                    Log.d(TAG, "onErrorResponse 2");
                } else if ((null != error && null != error.networkResponse)
                        && (error.networkResponse.statusCode == 500 || error.networkResponse.statusCode == 405)) {
                    listener.onFailure(errorDto);
                    Log.d(TAG, "onErrorResponse 3");
                } else {
                    errorDto.message = Utils.getString(R.string.no_network_error);
                    listener.onFailure(errorDto);
                    Log.d(TAG, "onErrorResponse 4");
                }
            }
        });
        _Application.getInstance().addToRequestQueue(jsonObjectRequest, url);
    }

    public interface RequestListener<T> {
        void onSuccess(T response);

        void onFailure(ErrorDto error);
    }
    public void doJsonObjectRequest_v2(int requestMethod, final String url, final JSONObject bodyParam, final RequestListener<String> listener) {
        Log.d(TAG, "bodyParam:" + bodyParam);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(requestMethod, url, bodyParam, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d(TAG, "response:" + response);
                try {
//                    if(url.contains(OAUTHUrls.URL_GET_LOGIN)||url.contains(OAUTHUrls.URL_CHECK_DEVICE_TOKEN)
//                            ||url.contains(OAUTHUrls.URL_CHECK_SESSION)||url.contains(OAUTHUrls.URL_INSERT_PHONE_TOKEN)
//                            ||url.contains(OAUTHUrls.URL_LOG_OUT))
//                    {
                    response = new JSONObject(response.getString("d"));

//                    }
                    int isSuccess = response.getInt("success");
                    if (isSuccess == 1) {
                        listener.onSuccess(response.getString("data"));
                    } else {
                        ErrorDto errorDto = new Gson().fromJson(response.getString("error"), ErrorDto.class);
                        if (errorDto == null) {

                            errorDto = new ErrorDto();
                            errorDto.message = Utils.getString(R.string.no_network_error);
                        } else {
                            if (errorDto.code == 0 && !url.contains(OAUTHUrls.URL_CHECK_DEVICE_TOKEN) && !url.contains(OAUTHUrls.URL_CHECK_SESSION)
                                    && !url.contains(OAUTHUrls.URL_INSERT_PHONE_TOKEN)) {
                                // new Prefs().putBooleanValue(Statics.PREFS_KEY_SESSION_ERROR,true);
                                // CrewScheduleApplication.getInstance().getmPrefs().clearLogin();
                                //BaseActivity.Instance.startNewActivity(LoginActivity.class);
                            }
                        }

                        listener.onFailure(errorDto);
                    }

                } catch (JSONException e) {

                    ErrorDto errorDto = new ErrorDto();
                    errorDto.message = Utils.getString(R.string.no_network_error);
                    listener.onFailure(errorDto);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                ErrorDto errorDto = new ErrorDto();
                if (null != error) {
                    listener.onFailure(errorDto);
                } else {
                    listener.onFailure(errorDto);
                }
            }
        });
        _Application.getInstance().addToRequestQueue(jsonObjectRequest, url);
    }

}
