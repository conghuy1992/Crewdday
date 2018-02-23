package com.dazone.crewdday.other;

import android.util.Log;

import com.android.volley.Request;
import com.dazone.crewdday.*;
import com.dazone.crewdday.Cons;
import com.dazone.crewdday.activity.HomeActivity;
import com.dazone.crewdday.adapter.MessageDto;
import com.dazone.crewdday.adapter.ob_belongs;
import com.dazone.crewdday.mInterface.BaseHTTPCallBackWithString;
import com.dazone.crewdday.mInterface.IF_GetDepartmentsAlsoDisabled;
import com.dazone.crewdday.mInterface.IF_UpdateDepartment_Enabled;
import com.dazone.crewdday.mInterface.IF_UpdateUser_Enabled;
import com.dazone.crewdday.mInterface.OnGetUserCallback;
import com.dazone.crewdday.model.DDayModel;
import com.dazone.crewdday.model.ProfileUserDTO;
import com.dazone.crewdday.util.DeviceUtilities;
import com.dazone.crewdday.util.LanguageUtils;
import com.dazone.crewdday.util.PreferenceUtilities;
import com.dazone.crewdday.util.WebClient;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class HttpRequest {
    String TAG = "HttpRequest";
    public static HttpRequest mInstance;
    PreferenceUtilities prefUtils = _Application.getInstance().getPreferenceUtilities();
    public static String serviceDomain;

    public String getServiceDomain() {
        return "http://" + _Application.getInstance().getPreferenceUtilities().getCurrentCompanyDomain();
    }

    public static HttpRequest getInstance() {
        if (null == mInstance) {
            mInstance = new HttpRequest();
        }
        serviceDomain = "http://" + _Application.getInstance().getPreferenceUtilities().getCurrentCompanyDomain();
        return mInstance;
    }

    public static String URL_SIGN_UP = "http://www.crewcloud.net/UI/Center/MobileService.asmx/SendConfirmEmail";

    public void signUp(final BaseHTTPCallBackWithString baseHTTPCallBack, final String email) {
        final String url = URL_SIGN_UP;
        Map<String, String> params = new HashMap<>();
        params.put("languageCode", Locale.getDefault().getLanguage().toUpperCase());
        params.put("mailAddress", "" + email);
        WebServiceManager webServiceManager = new WebServiceManager();
        webServiceManager.doJsonObjectRequest(Request.Method.POST, url, new JSONObject(params), new WebServiceManager.RequestListener<String>() {
            @Override
            public void onSuccess(String response) {
                Gson gson = new Gson();
                MessageDto messageDto = gson.fromJson(response, MessageDto.class);
                if (baseHTTPCallBack != null && messageDto != null) {
                    String message = messageDto.getMessage();
                    baseHTTPCallBack.onHTTPSuccess(message);
                }
            }

            @Override
            public void onFailure(ErrorDto error) {
                if (baseHTTPCallBack != null) {
                    baseHTTPCallBack.onHTTPFail(error);
                }
            }
        });
    }

    public void UpdateUser_Enabled(String _domain, int userNo, boolean enabled, final IF_UpdateUser_Enabled callback) {
        String url = "http://" + _domain + WebClient.UpdateUser_Enabled;
        Log.d(TAG, url);
        Map<String, Object> params = new HashMap<>();
        params.put("sessionId", _Application.getInstance().getPreferenceUtilities().getCurrentMobileSessionId());
        params.put("timeZoneOffset", DeviceUtilities.getTimeZoneOffset());
        params.put("languageCode", LanguageUtils.getPhoneLanguage());
        params.put("userNo", userNo);
        params.put("enabled", enabled);
        WebServiceManager webServiceManager = new WebServiceManager();
        webServiceManager.doJsonObjectRequest(Request.Method.POST, url, new JSONObject(params), new WebServiceManager.RequestListener<String>() {
            @Override
            public void onSuccess(String response) {
                Log.d(TAG, "UpdateUser_Enabled: " + response);
                callback.onSuccess();
            }

            @Override
            public void onFailure(ErrorDto error) {
                Log.d(TAG, "UpdateUser_Enabled onFailure");
                callback.onFail();
            }
        });
    }


    public void GetDepartmentsAlsoDisabled(String _domain, final IF_GetDepartmentsAlsoDisabled Callback) {
        String url = "http://" + _domain + WebClient.GetDepartmentsAlsoDisabled;
        Log.d(TAG, url);

        Map<String, Object> params = new HashMap<>();
        params.put("sessionId", _Application.getInstance().getPreferenceUtilities().getCurrentMobileSessionId());
        params.put("timeZoneOffset", DeviceUtilities.getTimeZoneOffset());
        params.put("languageCode", LanguageUtils.getPhoneLanguage());
        WebServiceManager webServiceManager = new WebServiceManager();
        webServiceManager.doJsonObjectRequest(Request.Method.POST, url, new JSONObject(params), new WebServiceManager.RequestListener<String>() {
            @Override
            public void onSuccess(String response) {
//                Log.d(TAG, "GetDepartmentsAlsoDisabled: " + response);
                Cons.longInfo(TAG, response);
                OrganizationUserDBHelper.clearData();
                try {
                    Type listType = new TypeToken<ArrayList<PersonData>>() {
                    }.getType();
                    ArrayList<PersonData> listUser = new Gson().fromJson(response, listType);
                    int serverSiteId = ServerSiteDBHelper.getServerSiteId(serviceDomain);
                    OrganizationUserDBHelper.addTreeUser(listUser, serverSiteId);
                    getAllUser(null);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                Callback.onSuccess();
            }

            @Override
            public void onFailure(ErrorDto error) {
                Log.d(TAG, "GetDepartmentsAlsoDisabled onFailure");
                Callback.onFail();
            }
        });
    }

    public void getDepartment(final OnGetAllOfUser callBack) {
        String url = serviceDomain + Urls.URL_GET_DEPARTMENT;
        Log.d(TAG, url);
        Map<String, String> params = new HashMap<>();
        params.put("sessionId", prefUtils.getCurrentMobileSessionId());
        params.put("languageCode", Locale.getDefault().getLanguage().toUpperCase());
        params.put("timeZoneOffset", TimeUtils.getTimezoneOffsetInMinutes() + "");
        WebServiceManager webServiceManager = new WebServiceManager();
        webServiceManager.doJsonObjectRequest(Request.Method.POST, url, new JSONObject(params), new WebServiceManager.RequestListener<String>() {
            @Override
            public void onSuccess(String response) {
//                Log.d(TAG, "getDepartment:" + response);
//                Cons.longInfo(TAG, response);
                final String s = response;
                try {
                    Log.d(CHECK, "getDepartment");
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            Type listType = new TypeToken<ArrayList<PersonData>>() {
                            }.getType();
                            ArrayList<PersonData> listUser = new Gson().fromJson(s, listType);
                            int serverSiteId = ServerSiteDBHelper.getServerSiteId(serviceDomain);
                            OrganizationUserDBHelper.addTreeUser(listUser, serverSiteId);
                        }
                    }).start();
                    Log.d(CHECK, "finish getDepartment");
                    getAllUser(callBack);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(ErrorDto error) {
//                if(callBack !=null)
            }
        });
    }

    public static String CHECK = "CHECKCHECKCHECK";

    public void getAllUser(final OnGetAllOfUser callBack) {
        String url = serviceDomain + Urls.URL_GET_ALL_USER;
        //String url = "http://test1.suziptong.com/UI/MobileMail3/MobileDataService.asmx/GetAllOfUsers";
        Map<String, String> params = new HashMap<>();
        params.put("sessionId", prefUtils.getCurrentMobileSessionId());
        params.put("languageCode", Locale.getDefault().getLanguage().toUpperCase());
        params.put("timeZoneOffset", TimeUtils.getTimezoneOffsetInMinutes() + "");
        WebServiceManager webServiceManager = new WebServiceManager();
        webServiceManager.doJsonObjectRequest(Request.Method.POST, url, new JSONObject(params), new WebServiceManager.RequestListener<String>() {
            @Override
            public void onSuccess(String response) {
                final String responseCopy = response;
                try {
                    Log.d(CHECK, "getAllUser:");
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            ArrayList<String> listTemp = new ArrayList<String>();
                            Type listType = new TypeToken<ArrayList<PersonData>>() {
                            }.getType();
                            ArrayList<PersonData> listUser = new Gson().fromJson(responseCopy, listType);
                            ArrayList<Integer> integerArrayList = new ArrayList<Integer>();
                            for (int i = 0; i < listUser.size(); i++) {
                                if (listUser.get(i).getBelongsArrayList().size() > 1) {
                                    listTemp.add(new Gson().toJson(listUser.get(i)));
                                    integerArrayList.add(i);
                                }
                            }
                            for (int i = 0; i < integerArrayList.size(); i++) {
                                for (int j = 0; j <= i; j++) {
                                    if (integerArrayList.get(i) > integerArrayList.get(j)) {
                                        int temp = integerArrayList.get(i);
                                        integerArrayList.set(i, integerArrayList.get(j));
                                        integerArrayList.set(j, temp);
                                    }
                                }
                            }
                            for (int i : integerArrayList) {
                                listUser.remove(i);
                            }
                            ArrayList<String> listtem = new ArrayList<String>();
                            for (int k = 0; k < listTemp.size(); k++) {
                                PersonData pd = new Gson().fromJson(listTemp.get(k), PersonData.class);
                                ArrayList<ob_belongs> arrayList2 = pd.getBelongsArrayList();
                                for (int i = 0; i < arrayList2.size(); i++) {
                                    ArrayList<ob_belongs> obBelongsArrayList = new ArrayList<ob_belongs>();
                                    ob_belongs obBelongs = arrayList2.get(i);
                                    obBelongsArrayList.add(obBelongs);
                                    pd.setBelongsArrayList(obBelongsArrayList);
                                    listtem.add(new Gson().toJson(pd));
                                }
                            }
//
                            for (int i = 0; i < listtem.size(); i++) {
                                PersonData ob = new Gson().fromJson(listtem.get(i), PersonData.class);
                                listUser.add(0, ob);
                            }

                            for (int i = 0; i < listUser.size(); i++) {
                                PersonData ob = listUser.get(i);
//                        Log.e(TAG,ob.getFullName()+":"+ob.getDepartNo());
                                ArrayList<ob_belongs> arrayList = ob.getBelongsArrayList();
                                for (int j = 0; j < arrayList.size(); j++) {
                                    ob_belongs obb = arrayList.get(j);
                                    if (obb.isDefault())
                                        if (prefUtils.getId() == obb.getUserNo()) {
                                            prefUtils.setDepartNo(obb.getDepartNo());
                                        }
                                }
                            }
                            int serverSiteId = ServerSiteDBHelper.getServerSiteId(serviceDomain);
                            OrganizationUserDBHelper.addTreeUser(listUser, serverSiteId);
                            Log.d(CHECK, "finish getAllUser:");
                            if (callBack != null)
                                callBack.onGetAllOfUserSuccess(listUser);
                            updateListOrganization();
                        }
                    }).start();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(ErrorDto error) {
                if (callBack != null)
                    callBack.onGetAllOfUserFail(error);
            }
        });
    }


    public void updateListOrganization() {
//        if (HomeActivity.checkUpdateList == 1) {
//            OrganizationFragment.instance.clearList();
//            OrganizationFragment.instance.initOrganizationTree();
//            BaseActionActivity.instance.dismissProgressbar();
//        }
    }

    public void GetUser(int userNo, final OnGetUserCallback callBack) {
        String url = serviceDomain + Urls.URL_GET_USER;
//        Log.e(TAG, root_link + " GetUser:" + url);
        Map<String, Object> params = new HashMap<>();
        params.put("sessionId", prefUtils.getCurrentMobileSessionId());
        params.put("languageCode", Locale.getDefault().getLanguage().toUpperCase());
        params.put("timeZoneOffset", TimeUtils.getTimezoneOffsetInMinutes() + "");
        params.put("userNo", userNo);
        Log.d(TAG, "GetUser:" + new Gson().toJson(params));
        WebServiceManager webServiceManager = new WebServiceManager();
        webServiceManager.doJsonObjectRequest(Request.Method.POST, url, new JSONObject(params), new WebServiceManager.RequestListener<String>() {
            @Override
            public void onSuccess(String response) {
                Log.d(TAG, "GetUser:" + response);
                ProfileUserDTO profileUserDTO = new Gson().fromJson(response, ProfileUserDTO.class);
                if (callBack != null)
                    callBack.onHTTPSuccess(profileUserDTO);
            }

            @Override
            public void onFailure(ErrorDto error) {
                if (callBack != null)
                    callBack.onHTTPFail(error);
                Log.e(TAG, "onFailure");
            }
        });
    }
}
