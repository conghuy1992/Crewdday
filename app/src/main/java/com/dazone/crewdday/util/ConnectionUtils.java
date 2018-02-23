package com.dazone.crewdday.util;


import android.util.Log;

import com.android.volley.Request;
import com.dazone.crewdday.Cons;
import com.dazone.crewdday._Application;
import com.dazone.crewdday.activity.HomeActivity;
import com.dazone.crewdday.adapter.NotifyTime;
import com.dazone.crewdday.mInterface.DeleteCompletedRecordSuccess;
import com.dazone.crewdday.mInterface.DeleteCoveredDaySuccess;
import com.dazone.crewdday.mInterface.DeleteSuccess;
import com.dazone.crewdday.mInterface.ConnectionFailedCallback;
import com.dazone.crewdday.mInterface.GetDDayDetail;
import com.dazone.crewdday.mInterface.GetGroupList;
import com.dazone.crewdday.mInterface.GetGroups;
import com.dazone.crewdday.mInterface.GetNotifyTime;
import com.dazone.crewdday.mInterface.IFRepetitionString;
import com.dazone.crewdday.mInterface.IF_UpdatePass;
import com.dazone.crewdday.mInterface.InsertCoveredDaySuccess;
import com.dazone.crewdday.mInterface.InsertDdaySuccess;
import com.dazone.crewdday.mInterface.MakeComplete;
import com.dazone.crewdday.mInterface.OnGetDDayModelList;
import com.dazone.crewdday.mInterface.UpdateGroupCalllback;
import com.dazone.crewdday.mInterface.UpdateSuccess;
import com.dazone.crewdday.mInterface.deleteGroupCallBack;
import com.dazone.crewdday.mInterface.insertGroupCallBack;
import com.dazone.crewdday.mInterface.onDeleteDeviceSuccess;
import com.dazone.crewdday.mInterface.onGetCountOfUnreadedDdays;
import com.dazone.crewdday.mInterface.updateGroupNameCallBack;
import com.dazone.crewdday.mInterface.updateGroupTagCallBack;
import com.dazone.crewdday.model.DDayDetailModel;
import com.dazone.crewdday.model.DDayDetalMode;
import com.dazone.crewdday.model.DDayDetalModeShare;
import com.dazone.crewdday.model.DDayModel;
import com.dazone.crewdday.model.DDayModelList;
import com.dazone.crewdday.model.DDayModelList_2;
import com.dazone.crewdday.model.Employee;
import com.dazone.crewdday.model.GroupModel;
import com.dazone.crewdday.model.MenuItemModel;
import com.dazone.crewdday.model.ObjectGetGroups;
import com.dazone.crewdday.other.*;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ConnectionUtils {

    public static String TAG = "ConnectionUtils";
    public static final String NAMESPACE = "http://crewcloud.net/";

//    public static final String SOAP_ACTION_GET_DETAIL = NAMESPACE + "GetDetailOfDay";
//    public static final String METHOD_NAME_GET_DETAIL = "GetDetailOfDay";

//    public static final String SOAP_ACTION_GET_GROUP = NAMESPACE + "GetGroups";
//    public static final String METHOD_NAME_GET_GROUP = "GetGroups";

//    public static final String SOAP_ACTION_GET_LIST = NAMESPACE + "GetListOfDDays_v2";
//    public static final String METHOD_NAME_GET_LIST = "GetListOfDDays_v2";


    public static PreferenceUtilities mPref = _Application.getInstance().getPreferenceUtilities();
    public static String serviceDomain = "http://" + _Application.getInstance().getPreferenceUtilities().getCurrentCompanyDomain();
    public static String URL;
    public static ConnectionUtils mInstance;

    public static ConnectionUtils getInstance() {
        if (null == mInstance) {
            mInstance = new ConnectionUtils();
        }
        serviceDomain = "http://" + _Application.getInstance().getPreferenceUtilities().getCurrentCompanyDomain();
        URL = serviceDomain + "/UI/dday/MobileDataService.asmx";
        return mInstance;
    }


    public void getGroupModelList_v2(final GetGroupList callback) {
        String url = URL + "/GetGroups";

        Map<String, Object> params = new HashMap<>();
        params.put("sessionId", mPref.getCurrentMobileSessionId());
        params.put("languageCode", LanguageUtils.getPhoneLanguage());
        params.put("timeZoneOffset", DeviceUtilities.getTimeZoneOffset());

        WebServiceManager webServiceManager = new WebServiceManager();
        webServiceManager.doJsonObjectRequest(Request.Method.POST, url, new JSONObject(params), new WebServiceManager.RequestListener<String>() {
            @Override
            public void onSuccess(String response) {
                Type listType = new TypeToken<ArrayList<GroupModel>>() {
                }.getType();
                ArrayList<GroupModel> listGroup = new Gson().fromJson(response, listType);
                for (GroupModel obj : listGroup) {
                    if (obj.getName().length() == 0) {
                        obj.setName(HomeActivity.name_gr);
                    }
                }
                if (listGroup == null) listGroup = new ArrayList<GroupModel>();
                callback.onSuccess(listGroup);
            }

            @Override
            public void onFailure(ErrorDto error) {
                callback.onFail();
                Log.e(TAG, "getGroupModelList_v2 onFailure");
            }
        });
    }


//    public ArrayList<GroupModel> getGroupModelList() {
//        ArrayList<GroupModel> list = new ArrayList<>();
//        SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME_GET_GROUP);
//        request.addProperty("sessionId", mPref.getCurrentMobileSessionId());
//        request.addProperty("languageCode", LanguageUtils.getPhoneLanguage());
//        request.addProperty("timeZoneOffset", DeviceUtilities.getTimeZoneOffset());
//        SoapSerializationEnvelope soapEnvelop;
//        soapEnvelop = new SoapSerializationEnvelope(SoapEnvelope.VER12);
//        soapEnvelop.dotNet = true;
//        soapEnvelop.setOutputSoapObject(request);
//        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
//
//        try {
//            androidHttpTransport.call(SOAP_ACTION_GET_GROUP, soapEnvelop);
//            SoapObject response = (SoapObject) soapEnvelop.getResponse();
////            Log.e(TAG, response.toString());
//            SoapObject soapGroupList = (SoapObject) response.getProperty("data");
//            int itemCount = soapGroupList.getPropertyCount();
//            for (int i = 0; i < itemCount; i++) {
//                SoapObject objectReponse = (SoapObject) soapGroupList.getProperty(i);
//                int tagNo = Integer.parseInt(objectReponse.getProperty("TagNo").toString());
//                String name = objectReponse.getProperty("Name").toString();
//                long groupId = Long.parseLong(objectReponse.getProperty("GroupNo").toString());
//                int countOfDays = Integer.parseInt(objectReponse.getProperty("CountOfDays").toString());
//                if (name.equals(Cons.NULL_RESPONSE)) {
//                    name = HomeActivity.name_gr;
//                }
//                GroupModel groupModel = new GroupModel();
//                groupModel.setGroupNo(groupId);
//                groupModel.setName(name);
//                groupModel.setCountOfDays(countOfDays);
//                groupModel.setTagNo(tagNo);
//                list.add(groupModel);
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        } catch (XmlPullParserException e) {
//            e.printStackTrace();
//        }
//        return list;
//    }

    public void getDDayModelList_v2(int mode, String index, int filterTypeForList, final OnGetDDayModelList callback) {
        String url = URL + "/GetListOfDDays_v2";
        Log.d(TAG, "getDDayModelList_v2 url:" + url);
        long groupNo = Long.parseLong(mPref.getGroupId());
        if (groupNo == -2) {
            groupNo = -1;
        }
        if (HomeActivity.group_name.equals(HomeActivity.newgr)) {
            filterTypeForList = HomeActivity.newList;
            groupNo = -1;
        }
        Map<String, Object> params = new HashMap<>();
        params.put("sessionId", mPref.getCurrentMobileSessionId());
        params.put("languageCode", LanguageUtils.getPhoneLanguage());
        params.put("timeZoneOffset", DeviceUtilities.getTimeZoneOffset());
        params.put("groupNo", groupNo);
        boolean isShare = Boolean.parseBoolean(mPref.getShareDdayShow());
        params.put("isShare", isShare);
        params.put("indexString", index);
        params.put("ddayUsingType", mode);
        params.put("filterTypeForList", filterTypeForList);
        if (mPref.getSearchText().isEmpty()) {
            params.put("searchText", "");
        } else {
            params.put("searchText", mPref.getSearchText());
        }

        WebServiceManager webServiceManager = new WebServiceManager();
        webServiceManager.doJsonObjectRequest(Request.Method.POST, url, new JSONObject(params), new WebServiceManager.RequestListener<String>() {
            @Override
            public void onSuccess(String response) {
//                Log.d(TAG, "response:" + response);
                ArrayList<DDayModel> list = new ArrayList<>();
                DDayModelList modelList = new Gson().fromJson(response, DDayModelList.class);
                List<DDayModelList_2> ListOfDateGroupsForDisplaying = modelList.getListOfDateGroupsForDisplaying();
                if (ListOfDateGroupsForDisplaying != null) {
                    for (DDayModelList_2 obj : ListOfDateGroupsForDisplaying) {
                        List<DDayModel> ListOfDaysForDisplaying = obj.getListOfDaysForDisplaying();
                        if (ListOfDaysForDisplaying != null) {
                            for (DDayModel model : ListOfDaysForDisplaying) {
                                DDayModel ddayModel = model;
                                ddayModel.setType(Cons.LIST_ITEMS);
                                int directorNo = model.getDirectorNo();
                                if (directorNo != 0) {
                                } else {
                                    ddayModel.setDirectorName(Cons.NULL);
                                    ddayModel.setDirectorNo(0);
                                }
                                ddayModel.setCurrentIdexString(modelList.getCurrentIndexString());
                                list.add(ddayModel);
                            }
                        }
                    }
                }
                if (list == null) list = new ArrayList<DDayModel>();
                callback.onSuccess(list);
            }

            @Override
            public void onFailure(ErrorDto error) {
                Log.e(TAG, "getDDayModelList_v2 onFailure");
                callback.onFail();
            }
        });
    }

    public static void longInfo(String TAG, String str) {
        if (str.length() > 4000) {
            Log.d(TAG, str.substring(0, 4000));
            longInfo(TAG, str.substring(4000));
        } else
            Log.d(TAG, str);
    }

//    public ArrayList<DDayModel> getDDayModelList(ConnectionFailedCallback error, int mode, String index, int filterTypeForList) {
//        ArrayList<DDayModel> list = new ArrayList<>();
//        long groupNo = Long.parseLong(mPref.getGroupId());
//        if (groupNo == -2) {
//            groupNo = -1;
//        }
//        if (HomeActivity.group_name.equals(HomeActivity.newgr)) {
//            filterTypeForList = HomeActivity.newList;
//            groupNo = -1;
//        }
//        SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME_GET_LIST);
//        request.addProperty("sessionId", mPref.getCurrentMobileSessionId());
//        request.addProperty("languageCode", LanguageUtils.getPhoneLanguage());
//        request.addProperty("timeZoneOffset", DeviceUtilities.getTimeZoneOffset());
//        request.addProperty("groupNo", groupNo);
//        boolean isShare = Boolean.parseBoolean(mPref.getShareDdayShow());
//        request.addProperty("isShare", isShare);
//        request.addProperty("indexString", index);
//        request.addProperty("ddayUsingType", mode);
//        request.addProperty("filterTypeForList", filterTypeForList);
//
//        if (mPref.getSearchText().isEmpty()) {
//            request.addProperty("searchText", "");
//        } else {
//            request.addProperty("searchText", mPref.getSearchText());
//        }
//
//        if (!index.equals(Cons.END)) {
//            SoapSerializationEnvelope soapEnvelop;
//            soapEnvelop = new SoapSerializationEnvelope(SoapEnvelope.VER12);
//            soapEnvelop.dotNet = true;
//            soapEnvelop.setOutputSoapObject(request);
//
//            HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
//
//            try {
//                androidHttpTransport.call(SOAP_ACTION_GET_LIST, soapEnvelop);
//                SoapObject response = (SoapObject) soapEnvelop.getResponse();
//                SoapObject soapList = (SoapObject) response.getProperty("data");
//
//                if (soapList.getPropertyCount() == 0) {
//                    error.onConnectionFailed();
//                } else {
//                    String currentIndexString = soapList.getProperty(0).toString();
//                    String listOfDDayGroup = soapList.getProperty(1).toString();
//
//                    if (!listOfDDayGroup.equals(Cons.NULL_RESPONSE)) {
//                        SoapObject soapChildList1 = (SoapObject) soapList.getProperty(1);
//                        int itemCount2 = soapChildList1.getPropertyCount();
//
//                        for (int i = 0; i < itemCount2; i++) {
//                            SoapObject soapChildList2 = (SoapObject) soapChildList1.getProperty(i);
//                            SoapObject soapChildList3 = (SoapObject) soapChildList2.getProperty("ListOfDaysForDisplaying");
//
//                            for (int j = 0; j < soapChildList3.getPropertyCount(); j++) {
//                                SoapObject soapChildList4 = (SoapObject) soapChildList3.getProperty(j);
//                                long groupId = Long.parseLong(soapChildList4.getProperty("GroupNo").toString());
//                                list = getListHelper(soapChildList4, list, currentIndexString, groupId);
//                            }
//                        }
//                    }
//                }
//
//            } catch (Exception e) {
//                e.printStackTrace();
//                error.onConnectionFailed();
//            }
//        }
//        return list;
//    }

    //    public ArrayList<DDayModel> getListHelper(SoapObject soapChildList4, ArrayList<DDayModel> list, String currentIdexString, long groupId) {
//        String title = soapChildList4.getProperty("Title").toString();
//        int tagId = Integer.parseInt(soapChildList4.getProperty("TagNo").toString());
//        int remainingDay = Integer.parseInt(soapChildList4.getProperty("RemainingDays").toString());
//        String date = soapChildList4.getProperty("DDayDate").toString();
//        long ddayId = Long.parseLong(soapChildList4.getProperty("DayNo").toString());
//        String avatar = soapChildList4.getProperty("DirectorPhotoUrl").toString();
//        int directorNo = Integer.parseInt(soapChildList4.getProperty("DirectorUserNo").toString());
//        int RegUserNo = Integer.parseInt(soapChildList4.getProperty("RegUserNo").toString());
//        String RegUserName = soapChildList4.getProperty("RegUserName").toString();
//        boolean CanHide = Boolean.valueOf(soapChildList4.getProperty("CanHide").toString());
//        boolean CanManage = Boolean.valueOf(soapChildList4.getProperty("CanManage").toString());
//        boolean IsHidden = Boolean.valueOf(soapChildList4.getProperty("IsHidden").toString());
//        boolean IsCompleted = Boolean.valueOf(soapChildList4.getProperty("IsCompleted").toString());
//        int HiddenDataNo = Integer.parseInt(soapChildList4.getProperty("HiddenDataNo").toString());
//        int CompletedRecordNo = Integer.parseInt(soapChildList4.getProperty("CompletedRecordNo").toString());
//        String DaysTextColor = soapChildList4.getProperty("DaysTextColor").toString();
//        boolean IsNew = Boolean.valueOf(soapChildList4.getProperty("IsNew").toString());
//        ///////////////////
//        DDayModel ddayModel = new DDayModel();
//        ddayModel.setIsNew(IsNew);
//        ddayModel.setDaysTextColor(DaysTextColor);
//        ddayModel.setHiddenDataNo(HiddenDataNo);
//        ddayModel.setCompletedRecordNo(CompletedRecordNo);
//        ddayModel.setIsHidden(IsHidden);
//        ddayModel.setIsCompleted(IsCompleted);
//        ddayModel.setCanHide(CanHide);
//        ddayModel.setCanManage(CanManage);
//        ddayModel.setRegUserName(RegUserName);
//        ddayModel.setRegUserId(RegUserNo);
//        ddayModel.setDdayDate(date);
//        ddayModel.setRemainingDays(remainingDay);
//        ddayModel.setTagId(tagId);
//        ddayModel.setDdayId(ddayId);
//        ddayModel.setTitle(title);
//        ddayModel.setType(Cons.LIST_ITEMS);
//        ddayModel.setCurrentIdexString(currentIdexString);
//        ddayModel.setAvatar(avatar);
//        ddayModel.setGroupId(groupId);
//        String directorName;
//
//        if (directorNo != 0) {
//            directorName = soapChildList4.getProperty("DirectorName").toString();
//            ddayModel.setDirectorName(directorName);
//            ddayModel.setDirectorNo(directorNo);
//        } else {
//            directorName = Cons.NULL;
//            ddayModel.setDirectorName(directorName);
//            ddayModel.setDirectorNo(0);
//        }
//
//        list.add(ddayModel);
//        return list;
//    }

    public void getDDayDetailModel_v2(final long ddayId, final boolean isFromDDayList, final GetDDayDetail callback) {
        String url = URL + "/GetDetailOfDay";
        Map<String, Object> params = new HashMap<>();
        params.put("sessionId", mPref.getCurrentMobileSessionId());
        params.put("languageCode", LanguageUtils.getPhoneLanguage());
        params.put("timeZoneOffset", DeviceUtilities.getTimeZoneOffset());
        params.put("dayNo", ddayId);

        WebServiceManager webServiceManager = new WebServiceManager();
        webServiceManager.doJsonObjectRequest(Request.Method.POST, url, new JSONObject(params), new WebServiceManager.RequestListener<String>() {
            @Override
            public void onSuccess(String response) {
                Log.d(TAG,"response:"+response);
                if (response.length() > 0 && response.startsWith("{")) {
                    DDayDetalMode obj = new Gson().fromJson(response, DDayDetalMode.class);
                    DDayDetailModel ddayDetailModel = new DDayDetailModel();
                    ddayDetailModel.setTitle(obj.getTitle());
                    String content = obj.getContent();
                    if (content == null || content.length() == 0) content = Cons.NULL_RESPONSE;
                    ddayDetailModel.setContent(content);
                    if (!isFromDDayList) {
                        int groupId = obj.getGroupNo();
                        String ddayType = obj.getDDayType();
                        ddayDetailModel.setDdayType(ddayType);
                        //Get writer name
                        String writer = obj.getRegUserName();
                        int RegUserNo = obj.getRegUserNo();
                        boolean canEdit = obj.isCanEdit();
                        //Get group name
                        String groupName = "Undentified";
                        if (groupId != 0) {
                            groupName = obj.getGroupName();
                        }
                        //Get type of repeat
                        String stringRepeatOptions = obj.getRepeatOptions();
                        //Type of repeat
                        String typeOfRepeat = Cons.NULL;
                        //Start date
                        String startDate = Cons.NULL;
                        //End date
                        String endDate = Cons.NULL;
                        //Remove brackets
                        String stringAfterRemoveBrackets = stringRepeatOptions.replaceAll("[\\[\\]]", "");
                        if (!stringRepeatOptions.equals(Cons.NULL_RESPONSE)) {
                            try {
                                JSONObject jsonObject = new JSONObject(stringAfterRemoveBrackets);
                                typeOfRepeat = jsonObject.getString("TypeName");
                                if (!typeOfRepeat.equals("CheckSpecificDday")) {
                                    startDate = jsonObject.getString("StartDate");
                                    endDate = jsonObject.getString("EndDate");
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        //Get repeat
                        String repeat = obj.getRepeat().trim();
                        int directorNo = obj.getUserNoOfDirector();
                        String directorName = Cons.NULL;
                        int directorDepartNo = 0;
                        if (directorNo != 0) {
                            directorName = obj.getUserNameOfDirector();
                            directorDepartNo = obj.getDepartNoOfDirector();
                        }
                        //
                        ArrayList<Employee> sharerList = new ArrayList<>();

                        // edit share
                        List<DDayDetalModeShare> lstShares = obj.getSharers();
                        if (lstShares != null) {
                            if (lstShares.size() > 0) {
                                for (DDayDetalModeShare dto : lstShares) {
                                    int userNo = dto.getUserNo();
                                    int departNo = dto.getDepartNo();
                                    String username;
                                    if (userNo != 0) {
                                        username = dto.getUserName();
                                    } else {
                                        username = "@" + dto.getDepartName();
                                    }
                                    Employee employee = new Employee();
                                    employee.setUserName(username);
                                    employee.setUserNo(userNo);
                                    employee.setDepartNo(departNo);
                                    sharerList.add(employee);
                                }
                            }
                        }
                        //
                        ArrayList<Employee> managerList = new ArrayList<>();
                        // edit manager
                        List<DDayDetalModeShare> lstManager = obj.getManagers();
                        if (lstManager != null) {
                            if (lstManager.size() > 0) {
                                for (DDayDetalModeShare dto : lstManager) {
                                    int userNo = dto.getUserNo();
                                    int departNo = dto.getDepartNo();
                                    String username;
                                    if (userNo != 0) {
                                        username = dto.getUserName();
                                    } else {
                                        username = "@" + dto.getDepartName();
                                    }
                                    Employee employee = new Employee();
                                    employee.setUserName(username);
                                    employee.setUserNo(userNo);
                                    employee.setDepartNo(departNo);
                                    managerList.add(employee);
                                }
                            }
                        }

                        // add

                        ddayDetailModel.setCanHide(obj.isCanHide());
                        ddayDetailModel.setDdayId(ddayId);
                        ddayDetailModel.setCanManage(obj.isCanManage());
                        ddayDetailModel.setManagersList(managerList);
                        ddayDetailModel.setSharersList(sharerList);
                        ddayDetailModel.setGroupName(groupName);
                        ddayDetailModel.setWriter(writer);
                        ddayDetailModel.setRegUserNo(RegUserNo);
                        ddayDetailModel.setCanEdit(canEdit);
                        ddayDetailModel.setStartDate(startDate);
                        ddayDetailModel.setEndDate(endDate);
                        ddayDetailModel.setTypeOfRepeat(typeOfRepeat);
                        ddayDetailModel.setStringRepeatOptions(stringRepeatOptions);
                        ddayDetailModel.setRepeatString(repeat);
                        ddayDetailModel.setDirectorUsername(directorName);
                        ddayDetailModel.setDirectorNo(directorNo);
                        ddayDetailModel.setDirectorDepartNo(directorDepartNo);
                    }
                    callback.onSuccess(ddayDetailModel);
                } else {
                    callback.onFail();
                }

            }

            @Override
            public void onFailure(ErrorDto error) {
                callback.onFail();
                Log.d(TAG, "getDDayDetailModel_v2 ErrorDto");
            }
        });
    }

//    public DDayDetailModel getDDayDetailModel(ConnectionFailedCallback error, long ddayId, boolean isFromDDayList) {
//        DDayDetailModel ddayDetailModel = new DDayDetailModel();
//        SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME_GET_DETAIL);
//        request.addProperty("sessionId", mPref.getCurrentMobileSessionId());
//        request.addProperty("languageCode", LanguageUtils.getPhoneLanguage());
//        request.addProperty("timeZoneOffset", DeviceUtilities.getTimeZoneOffset());
//        request.addProperty("dayNo", ddayId);
//        SoapSerializationEnvelope soapEnvelop = new SoapSerializationEnvelope(SoapEnvelope.VER12);
//        soapEnvelop.dotNet = true;
//        soapEnvelop.setOutputSoapObject(request);
//        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
//        try {
//            androidHttpTransport.call(SOAP_ACTION_GET_DETAIL, soapEnvelop);
//            SoapObject response = (SoapObject) soapEnvelop.getResponse();
//            SoapObject soapList = (SoapObject) response.getProperty("data");
////            Log.e(TAG,soapList.toString());
//            //Get title
//            String title = soapList.getProperty("Title").toString();
//            ddayDetailModel.setTitle(title);
//            //Get content
//            String content = soapList.getProperty("Content").toString();
//            ddayDetailModel.setContent(content);
//            if (!isFromDDayList) {
//                //Get group id
//                int groupId = Integer.parseInt(soapList.getProperty("GroupNo").toString());
//                //Get dday type
//                String ddayType = soapList.getProperty("DDayType").toString();
//                ddayDetailModel.setDdayType(ddayType);
//                //Get writer name
//                String writer = soapList.getProperty("RegUserName").toString();
//                int RegUserNo = Integer.parseInt(soapList.getProperty("RegUserNo").toString());
//                //Get director username
//                // String directorUsername = soapList.getProperty("UserNameOfDirector").toString();
//                //Get permission to edit
//                String stringCanEdit = soapList.getProperty("CanEdit").toString();
//                boolean canEdit;
//                if (stringCanEdit.equalsIgnoreCase("true") || stringCanEdit.equalsIgnoreCase("false")) {
//                    canEdit = Boolean.valueOf(stringCanEdit);
//                } else {
//                    canEdit = false;
//                }
//                //Get group name
//                String groupName = "Undentified";
//                if (groupId != 0) {
//                    groupName = soapList.getProperty("GroupName").toString();
//                }
//                //Get type of repeat
//                String stringRepeatOptions = soapList.getProperty("RepeatOptions").toString();
//                //Type of repeat
//                String typeOfRepeat = Cons.NULL;
//                //Start date
//                String startDate = Cons.NULL;
//                //End date
//                String endDate = Cons.NULL;
//                //Remove brackets
//                String stringAfterRemoveBrackets = stringRepeatOptions.replaceAll("[\\[\\]]", "");
//                if (!stringRepeatOptions.equals(Cons.NULL_RESPONSE)) {
//                    try {
//                        JSONObject jsonObject = new JSONObject(stringAfterRemoveBrackets);
//                        typeOfRepeat = jsonObject.getString("TypeName");
//                        if (!typeOfRepeat.equals("CheckSpecificDday")) {
//                            startDate = jsonObject.getString("StartDate");
//                            endDate = jsonObject.getString("EndDate");
//                        }
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//                }
//                //Get repeat
//                String repeat = soapList.getProperty("Repeat").toString();
//                int directorNo = Integer.parseInt(soapList.getProperty("UserNoOfDirector").toString());
//                String directorName = Cons.NULL;
//                int directorDepartNo = 0;
//                if (directorNo != 0) {
//                    directorName = soapList.getProperty("UserNameOfDirector").toString();
//                    directorDepartNo = Integer.parseInt(soapList.getProperty("DepartNoOfDirector").toString());
//                }
//                //
//                SoapObject sharers = (SoapObject) soapList.getProperty("Sharers");
//                String sharerResponse = sharers.toString();
//                ArrayList<Employee> sharerList = new ArrayList<>();
//                if (!sharerResponse.equals(Cons.NULL_RESPONSE)) {
//                    for (int i = 0; i < sharers.getPropertyCount(); i++) {
//                        SoapObject userForDisplaying = (SoapObject) sharers.getProperty(i);
//                        int userNo = Integer.parseInt(userForDisplaying.getProperty("UserNo").toString());
//                        int departNo = Integer.parseInt(userForDisplaying.getProperty("DepartNo").toString());
//                        String username;
//                        if (userNo != 0) {
//                            username = userForDisplaying.getProperty("UserName").toString();
//                        } else {
//                            username = "@" + userForDisplaying.getProperty("DepartName").toString();
//                        }
//                        Employee employee = new Employee();
//                        employee.setUserName(username);
//                        employee.setUserNo(userNo);
//                        employee.setDepartNo(departNo);
//                        sharerList.add(employee);
//                    }
//                }
//                //
//                SoapObject managers = (SoapObject) soapList.getProperty("Managers");
//                String managerResponse = managers.toString();
//                ArrayList<Employee> managerList = new ArrayList<>();
//                if (!managerResponse.equals(Cons.NULL_RESPONSE)) {
//                    for (int i = 0; i < managers.getPropertyCount(); i++) {
//                        SoapObject userForDisplaying = (SoapObject) managers.getProperty(i);
//                        int userNo = Integer.parseInt(userForDisplaying.getProperty("UserNo").toString());
//                        int departNo = Integer.parseInt(userForDisplaying.getProperty("DepartNo").toString());
//                        String username;
//                        if (userNo != 0) {
//                            username = userForDisplaying.getProperty("UserName").toString();
//                        } else {
//                            username = "@" + userForDisplaying.getProperty("DepartName").toString();
//                        }
//                        Employee employee = new Employee();
//                        employee.setUserName(username);
//                        employee.setUserNo(userNo);
//                        employee.setDepartNo(departNo);
//                        managerList.add(employee);
//                    }
//                }
//                ddayDetailModel.setManagersList(managerList);
//                ddayDetailModel.setSharersList(sharerList);
//                ddayDetailModel.setGroupName(groupName);
//                ddayDetailModel.setWriter(writer);
//                ddayDetailModel.setRegUserNo(RegUserNo);
//                ddayDetailModel.setCanEdit(canEdit);
//                ddayDetailModel.setStartDate(startDate);
//                ddayDetailModel.setEndDate(endDate);
//                ddayDetailModel.setTypeOfRepeat(typeOfRepeat);
//                ddayDetailModel.setStringRepeatOptions(stringRepeatOptions);
//                ddayDetailModel.setRepeatString(repeat);
//                ddayDetailModel.setDirectorUsername(directorName);
//                ddayDetailModel.setDirectorNo(directorNo);
//                ddayDetailModel.setDirectorDepartNo(directorDepartNo);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            error.onConnectionFailed();
//        }
//
//
//        return ddayDetailModel;
//    }

    public void insertGroup(final MenuItemModel menuItemModel, final insertGroupCallBack insertGroupCallBack) {
        String InsertGroup = URL + "/InsertGroup";
        String url = InsertGroup;
        Map<String, Object> params = new HashMap<>();
        params.put("sessionId", mPref.getCurrentMobileSessionId());
        params.put("languageCode", LanguageUtils.getPhoneLanguage());
        params.put("timeZoneOffset", DeviceUtilities.getTimeZoneOffset());
        params.put("tagNo", menuItemModel.getTagNo());
        params.put("name", menuItemModel.getText());
        WebServiceManager webServiceManager = new WebServiceManager();
        webServiceManager.doJsonObjectRequest(Request.Method.POST, url, new JSONObject(params), new WebServiceManager.RequestListener<String>() {
            @Override
            public void onSuccess(String response) {
                insertGroupCallBack.onSuccess(Long.parseLong(response));
            }

            @Override
            public void onFailure(ErrorDto error) {
                Log.e(TAG, "insertGroup onFailure");
            }
        });
    }

    public void updateGroupName(final MenuItemModel menuItemModel, final updateGroupNameCallBack Callback) {
        String UpdateNameOfGroup = URL + "/UpdateNameOfGroup";
        String url = UpdateNameOfGroup;
        Map<String, Object> params = new HashMap<>();
        params.put("sessionId", mPref.getCurrentMobileSessionId());
        params.put("languageCode", LanguageUtils.getPhoneLanguage());
        params.put("timeZoneOffset", DeviceUtilities.getTimeZoneOffset());
        params.put("groupNo", menuItemModel.getGroupId());
        params.put("name", menuItemModel.getText());
        WebServiceManager webServiceManager = new WebServiceManager();
        webServiceManager.doJsonObjectRequest(Request.Method.POST, url, new JSONObject(params), new WebServiceManager.RequestListener<String>() {
            @Override
            public void onSuccess(String response) {
//                Log.e(TAG, "UpdateNameOfGroup:" + response);
                Callback.onSuccess();
            }

            @Override
            public void onFailure(ErrorDto error) {
                Log.e(TAG, "UpdateNameOfGroup onFailure");
            }
        });
    }

    public void updateGroupTag(final MenuItemModel menuItemModel, final updateGroupTagCallBack callBack) {
        String UpdateTagNoOfGroup = URL + "/UpdateTagNoOfGroup";
        String url = UpdateTagNoOfGroup;
        Map<String, Object> params = new HashMap<>();
        params.put("sessionId", mPref.getCurrentMobileSessionId());
        params.put("languageCode", LanguageUtils.getPhoneLanguage());
        params.put("timeZoneOffset", DeviceUtilities.getTimeZoneOffset());
        params.put("groupNo", menuItemModel.getGroupId());
        params.put("tagNo", menuItemModel.getTagNo());
        WebServiceManager webServiceManager = new WebServiceManager();
        webServiceManager.doJsonObjectRequest(Request.Method.POST, url, new JSONObject(params), new WebServiceManager.RequestListener<String>() {
            @Override
            public void onSuccess(String response) {
                Log.d(TAG, "UpdateTagNoOfGroup:" + response);
                callBack.onSuccess();
            }

            @Override
            public void onFailure(ErrorDto error) {
                Log.e(TAG, "UpdateTagNoOfGroup onFailure");
            }
        });
    }

    public void deleteGroup(final MenuItemModel menuItemModel, final deleteGroupCallBack callBack) {
        String DeleteGroup = URL + "/DeleteGroup";
        String url = DeleteGroup;
        Map<String, Object> params = new HashMap<>();
        params.put("sessionId", mPref.getCurrentMobileSessionId());
        params.put("languageCode", LanguageUtils.getPhoneLanguage());
        params.put("timeZoneOffset", DeviceUtilities.getTimeZoneOffset());
        params.put("groupNo", menuItemModel.getGroupId());
        WebServiceManager webServiceManager = new WebServiceManager();
        webServiceManager.doJsonObjectRequest(Request.Method.POST, url, new JSONObject(params), new WebServiceManager.RequestListener<String>() {
            @Override
            public void onSuccess(String response) {
//                Log.e(TAG, "DeleteGroup:" + response);
                callBack.onSuccess();
            }

            @Override
            public void onFailure(ErrorDto error) {
                Log.e(TAG, "DeleteGroup onFailure");
            }
        });
    }


    public void insert_v2(int groupNo, int type, String repeatOption,
                          String title, String content, final String JSON_SHARER_TYPE,
                          final String JSON_PERSON_CONCERNED_TYPE, final String JSON_ADMIN_TYPE,
                          final InsertDdaySuccess insertDdaySuccess, final ArrayList<NotifyTime> arrayList, boolean canHide) {
        String INSERT_DDAY = URL + "/InsertDay";
        String url = INSERT_DDAY;

//        Log.e(TAG, mPref.getCurrentMobileSessionId());

        Map<String, Object> params = new HashMap<>();
        params.put("sessionId", mPref.getCurrentMobileSessionId());
        params.put("languageCode", LanguageUtils.getPhoneLanguage());
        params.put("timeZoneOffset", DeviceUtilities.getTimeZoneOffset());
        Log.d(TAG, "sessionId:" + mPref.getCurrentMobileSessionId());
        params.put("groupNo", groupNo);
        params.put("typeNo", type);
        params.put("repeatOptions", repeatOption);
        params.put("title", title);
        params.put("content", content);
        params.put("canHide", canHide);

        WebServiceManager webServiceManager = new WebServiceManager();
        webServiceManager.doJsonObjectRequest(Request.Method.POST, url, new JSONObject(params), new WebServiceManager.RequestListener<String>() {
            @Override
            public void onSuccess(String response) {
//                Log.d(TAG, "insert_v2:" + response);
                int ddayId = Integer.parseInt(response);
                InsertNotification(ddayId, arrayList);
                if (JSON_SHARER_TYPE.length() > 0) {
                    share1(ddayId, JSON_SHARER_TYPE);
                }
                if (JSON_PERSON_CONCERNED_TYPE.length() > 0) {
                    share2(ddayId, JSON_PERSON_CONCERNED_TYPE);
                }
                if (JSON_ADMIN_TYPE.length() > 0) {
                    share3(ddayId, JSON_ADMIN_TYPE);
                }
                insertDdaySuccess.onInsertSuccess();
            }

            @Override
            public void onFailure(ErrorDto error) {
                Log.e(TAG, "insert_v2 onFailure");
            }
        });
    }

    public void update_v2(final int groupNo, final long ddayNo, final int type, final String repeatOption,
                          final String title, final String content, final UpdateSuccess updateSuccess,
                          final String JSON_SHARER_TYPE, final String JSON_PERSON_CONCERNED_TYPE, final String JSON_ADMIN_TYPE,
                          final long notiNo, final ArrayList<NotifyTime> arrayList, boolean canHide) {
//        Log.e(TAG,"groupNo:"+groupNo);
        String UPDATE_DDAY = URL + "/UpdateDay";
        String url = UPDATE_DDAY;
        Map<String, Object> params = new HashMap<>();
        params.put("sessionId", mPref.getCurrentMobileSessionId());
        params.put("languageCode", LanguageUtils.getPhoneLanguage());
        params.put("timeZoneOffset", DeviceUtilities.getTimeZoneOffset());
        params.put("dayNo", ddayNo);
        params.put("groupNo", groupNo);
        params.put("typeNo", type);
        params.put("repeatOptions", repeatOption);
        params.put("title", title);
        params.put("content", content);
        params.put("canHide", canHide);

        WebServiceManager webServiceManager = new WebServiceManager();
        webServiceManager.doJsonObjectRequest(Request.Method.POST, url, new JSONObject(params), new WebServiceManager.RequestListener<String>() {
            @Override
            public void onSuccess(String response) {
//                Log.e("update",response);
                DeleteNotification(ddayNo);
                UpdateNotification(notiNo, arrayList, ddayNo);
                Delete_Share(ddayNo, JSON_SHARER_TYPE);
                Delete_Director(ddayNo, JSON_PERSON_CONCERNED_TYPE);
                Delete_Manager(ddayNo, JSON_ADMIN_TYPE);
                updateSuccess.onUpdateSuccess();
            }

            @Override
            public void onFailure(ErrorDto error) {
                Log.e(TAG, "update_v2 onFailure");
            }
        });
    }

    public void delete_v2(final long id, final DeleteSuccess deleteSuccess) {
        String DELETE_DDAY = URL + "/DeleteDay";
        String url = DELETE_DDAY;
        Log.d(TAG, "url:" + url);
        Map<String, Object> params = new HashMap<>();
        params.put("sessionId", mPref.getCurrentMobileSessionId());
        params.put("languageCode", LanguageUtils.getPhoneLanguage());
        params.put("timeZoneOffset", DeviceUtilities.getTimeZoneOffset());
        params.put("dayNo", id);

        Log.d(TAG, "delete_v2:" + new Gson().toJson(params));

        WebServiceManager webServiceManager = new WebServiceManager();
        webServiceManager.doJsonObjectRequest(Request.Method.POST, url, new JSONObject(params), new WebServiceManager.RequestListener<String>() {
            @Override
            public void onSuccess(String response) {
//                Log.e(TAG, "onSuccess" + response);
                DeleteNotification(id);
                deleteSuccess.onDeleteSuccess();
                Log.e(TAG, "delete_v2 onSuccess:" + response);
            }

            @Override
            public void onFailure(ErrorDto error) {
                Log.e(TAG, "delete_v2 onFailure");
            }
        });

    }

    public void getgroup(final GetGroups getGroups) {
        String GETGROUP_DDAY = URL + "/GetGroups";
        String url = GETGROUP_DDAY;
        Map<String, Object> params = new HashMap<>();
        params.put("sessionId", mPref.getCurrentMobileSessionId());
        params.put("languageCode", LanguageUtils.getPhoneLanguage());
        params.put("timeZoneOffset", DeviceUtilities.getTimeZoneOffset());

        WebServiceManager webServiceManager = new WebServiceManager();
        webServiceManager.doJsonObjectRequest(Request.Method.POST, url, new JSONObject(params), new WebServiceManager.RequestListener<String>() {
            @Override
            public void onSuccess(String response) {

                Type listType = new TypeToken<List<ObjectGetGroups>>() {
                }.getType();
                List<ObjectGetGroups> listDtos = new Gson().fromJson(response, listType);
                if (getGroups != null)
                    getGroups.onGetGroupSuccess(listDtos);
            }

            @Override
            public void onFailure(ErrorDto error) {
                Log.e(TAG, "getgroup onFailure");
            }
        });
    }

//    public void getDDayDetail(long ddayId) {
//        String GET_DETAIL_OF_DAY = URL + "/GetDetailOfDay";
//        String url = GET_DETAIL_OF_DAY;
//        Map<String, Object> params = new HashMap<>();
//        params.put("sessionId", mPref.getCurrentMobileSessionId());
//        params.put("languageCode", LanguageUtils.getPhoneLanguage());
//        params.put("timeZoneOffset", DeviceUtilities.getTimeZoneOffset());
//        params.put("dayNo", ddayId);
//        WebServiceManager webServiceManager = new WebServiceManager();
//        webServiceManager.doJsonObjectRequest(Request.Method.POST, url, new JSONObject(params), new WebServiceManager.RequestListener<String>() {
//            @Override
//            public void onSuccess(String response) {
////                Log.e(TAG, "response:" + response);
//            }
//
//            @Override
//            public void onFailure(ErrorDto error) {
//                Log.e(TAG, "getDDayDetail onFailure");
//            }
//        });
//    }

    public void share3(long id, String jsonString) {
        String InsertManagers = URL + "/InsertManagers_v2";
        String url = InsertManagers;
        ArrayList<Integer> arrayList = new ArrayList<Integer>();
        if (jsonString.length() > 0) {
            Gson gson = new Gson();
            Type type = new TypeToken<ArrayList<PersonData>>() {
            }.getType();
            ArrayList<PersonData> personData = gson.fromJson(jsonString, type);
            for (int i = 0; i < personData.size(); i++) {
                arrayList.add(personData.get(i).getUserNo());
            }
        } else {
            arrayList.add(0);
        }
        Map<String, Object> params = new HashMap<>();
        params.put("sessionId", mPref.getCurrentMobileSessionId());
        params.put("languageCode", LanguageUtils.getPhoneLanguage());
        params.put("timeZoneOffset", DeviceUtilities.getTimeZoneOffset());
        params.put("dayNo", id);
        params.put("listOfUsers", arrayList);
        params.put("isNotification", HomeActivity.insertVersion2);
        WebServiceManager webServiceManager = new WebServiceManager();
        webServiceManager.doJsonObjectRequest(Request.Method.POST, url, new JSONObject(params), new WebServiceManager.RequestListener<String>() {
            @Override
            public void onSuccess(String response) {
//                Log.e(TAG, "InsertManagers:" + response);
            }

            @Override
            public void onFailure(ErrorDto error) {
//                Log.e(TAG, "InsertManagers onFailure");
            }
        });
    }

    public void share2(long id, String jsonString) {
        String InsertDirector = URL + "/InsertDirector";
        String url = InsertDirector;
        int userNo = 0;
        if (jsonString.length() > 0) {
            Gson gson = new Gson();
            Type type = new TypeToken<ArrayList<PersonData>>() {
            }.getType();
            ArrayList<PersonData> personData = gson.fromJson(jsonString, type);
            userNo = personData.get(personData.size() - 1).getUserNo();
        }
        Map<String, Object> params = new HashMap<>();
        params.put("sessionId", mPref.getCurrentMobileSessionId());
        params.put("languageCode", LanguageUtils.getPhoneLanguage());
        params.put("timeZoneOffset", DeviceUtilities.getTimeZoneOffset());
        params.put("dayNo", id);
        params.put("userNo", userNo);
        WebServiceManager webServiceManager = new WebServiceManager();
        webServiceManager.doJsonObjectRequest(Request.Method.POST, url, new JSONObject(params), new WebServiceManager.RequestListener<String>() {
            @Override
            public void onSuccess(String response) {
//                Log.e(TAG, "response:" + response);
            }

            @Override
            public void onFailure(ErrorDto error) {
//                Log.e(TAG, "share2 onFailure");
            }
        });
    }

    public void share1(long id, String jsonString) {
        String InsertSharers = URL + "/InsertSharers_v2";
        String url = InsertSharers;
        ArrayList<Integer> userNo = new ArrayList<Integer>();
        ArrayList<Integer> DepartNo = new ArrayList<Integer>();
        if (jsonString.length() > 0) {
            Gson gson = new Gson();
            Type type = new TypeToken<ArrayList<PersonData>>() {
            }.getType();
            ArrayList<PersonData> personData = gson.fromJson(jsonString, type);
            for (int i = 0; i < personData.size(); i++) {
//                if (personData.get(i).getUserNo() != 0)
//                    personData.get(i).setDepartNo(0);
//                userNo.add(personData.get(i).getUserNo());
//                DepartNo.add(personData.get(i).getDepartNo());
                int UserNo = personData.get(i).getUserNo();
                if (UserNo == 0) {
                    if (!ListUtils.check_root_folder(personData, personData.get(i))) {
                        userNo.add(personData.get(i).getUserNo());
                        DepartNo.add(personData.get(i).getDepartNo());
                    }
                } else {
                    if (!ListUtils.check_root_user(personData, personData.get(i))) {
                        userNo.add(personData.get(i).getUserNo());
                        DepartNo.add(0);
                    }
                }
            }

        } else {
            userNo.add(0);
            DepartNo.add(0);
        }
//        for (int i = 0; i < userNo.size(); i++) {
//            Log.e(TAG, "userNo:" + userNo.get(i) + " DepartNo:" + DepartNo.get(i));
//        }
        Map<String, Object> params = new HashMap<>();
        params.put("sessionId", mPref.getCurrentMobileSessionId());
        params.put("languageCode", LanguageUtils.getPhoneLanguage());
        params.put("timeZoneOffset", DeviceUtilities.getTimeZoneOffset());
        params.put("dayNo", id);
        params.put("listOfUsers", userNo);
        params.put("listOfDepartments", DepartNo);
        params.put("isNotification", HomeActivity.insertVersion2);
        Log.d(TAG,new Gson().toJson(params));
        WebServiceManager webServiceManager = new WebServiceManager();
        webServiceManager.doJsonObjectRequest(Request.Method.POST, url, new JSONObject(params), new WebServiceManager.RequestListener<String>() {
            @Override
            public void onSuccess(String response) {
                Log.e(TAG, "InsertSharers:" + response);
            }

            @Override
            public void onFailure(ErrorDto error) {
//                Log.e(TAG, "InsertSharers onFailure");
            }
        });

    }

    public void getList_home(int mode, String index, int filterTypeForList) {
        Map<String, Object> params = new HashMap<>();
        params.put("sessionId", mPref.getCurrentMobileSessionId());
        params.put("languageCode", LanguageUtils.getPhoneLanguage());
        params.put("timeZoneOffset", DeviceUtilities.getTimeZoneOffset());

        long groupNo = Long.parseLong(mPref.getGroupId());
        if (groupNo == 2) groupNo = -1;
        params.put("groupNo", groupNo);

        boolean isShare = Boolean.parseBoolean(mPref.getShareDdayShow());

        params.put("isShare", isShare);
        params.put("indexString", index);
        params.put("ddayUsingType", mode);
        params.put("filterTypeForList", filterTypeForList);

        if (mPref.getSearchText().isEmpty()) {
            params.put("searchText", "");
        } else {
            params.put("searchText", mPref.getSearchText());
        }

        WebServiceManager webServiceManager = new WebServiceManager();
        webServiceManager.doJsonObjectRequest(Request.Method.POST, URL + "/GetListOfDDays_v2", new JSONObject(params), new WebServiceManager.RequestListener<String>() {
            @Override
            public void onSuccess(String response) {
//                Log.e(TAG, "response:" + response);
            }

            @Override
            public void onFailure(ErrorDto error) {
                Log.e(TAG, "getList_home onFailure");
            }
        });
    }

    public void insertCompletedRecord(final long ddayId, final String completedDate, final String comment, final MakeComplete makeComplete) {
        String InsertCompletedRecord = URL + "/InsertCompletedRecord";
        String url = InsertCompletedRecord;
        Map<String, Object> params = new HashMap<>();
        params.put("sessionId", mPref.getCurrentMobileSessionId());
        params.put("languageCode", LanguageUtils.getPhoneLanguage());
        params.put("timeZoneOffset", DeviceUtilities.getTimeZoneOffset());
        params.put("dayNo", ddayId);
        params.put("completedDate", completedDate);
        params.put("comment", comment);
        WebServiceManager webServiceManager = new WebServiceManager();
        webServiceManager.doJsonObjectRequest(Request.Method.POST, url, new JSONObject(params), new WebServiceManager.RequestListener<String>() {
            @Override
            public void onSuccess(String response) {
                makeComplete.onSuccessMakeComplete(Long.parseLong(response));
            }

            @Override
            public void onFailure(ErrorDto error) {
            }
        });
    }

    public void InsertDevice(String regid, String notificationOptions) {
        String url = URL + "/InsertAndroidDevice";
        Log.d(TAG, url);

        Map<String, Object> params = new HashMap<>();
        params.put("sessionId", mPref.getCurrentMobileSessionId());
        params.put("languageCode", LanguageUtils.getPhoneLanguage());
        params.put("timeZoneOffset", DeviceUtilities.getTimeZoneOffset());
        params.put("deviceID", regid);
        params.put("osVersion", "Android " + Cons.getAndroidVersion());
        params.put("notificationOptions", notificationOptions);


        WebServiceManager webServiceManager = new WebServiceManager();
        webServiceManager.doJsonObjectRequest(Request.Method.POST, url, new JSONObject(params), new WebServiceManager.RequestListener<String>() {
            @Override
            public void onSuccess(String response) {
//                Log.e(TAG, "response:" + response);
            }

            @Override
            public void onFailure(ErrorDto error) {
                Log.e(TAG, " InsertDevice onFailure");
            }
        });
    }

    public void DeleteDevice(final onDeleteDeviceSuccess success) {
        String url = URL + "/DeleteAndroidDevice";
        Map<String, Object> params = new HashMap<>();
//        Log.e(TAG, mPref.getCurrentMobileSessionId());
//        Log.e(TAG, LanguageUtils.getPhoneLanguage());
//        Log.e(TAG,"getTimeZoneOffset:"+ DeviceUtilities.getTimeZoneOffset());
        params.put("sessionId", mPref.getCurrentMobileSessionId());
        params.put("languageCode", LanguageUtils.getPhoneLanguage());
        params.put("timeZoneOffset", DeviceUtilities.getTimeZoneOffset());
        WebServiceManager webServiceManager = new WebServiceManager();
        webServiceManager.doJsonObjectRequest(Request.Method.POST, url, new JSONObject(params), new WebServiceManager.RequestListener<String>() {
            @Override
            public void onSuccess(String response) {
//                Log.e(TAG, "response:" + response);
                success.DeleteSuccess();
            }

            @Override
            public void onFailure(ErrorDto error) {
                Log.e(TAG, "DeleteDevice onFailure");
            }
        });
    }

    public void UpdateAndroidDevice(String deviceID, String notificationOptions) {
//        Log.e(TAG,notificationOptions);
        String UpdateAndroidDevice_NotificationOptions = URL + "/UpdateAndroidDevice_NotificationOptions";
        String url = UpdateAndroidDevice_NotificationOptions;
//        Log.e(TAG, "getTimeZoneOffset:" + DeviceUtilities.getTimeZoneOffset());
        Map<String, Object> params = new HashMap<>();
        params.put("sessionId", mPref.getCurrentMobileSessionId());
        params.put("languageCode", LanguageUtils.getPhoneLanguage());
        params.put("timeZoneOffset", DeviceUtilities.getTimeZoneOffset());
        params.put("deviceID", deviceID);
        params.put("notificationOptions", notificationOptions);
        WebServiceManager webServiceManager = new WebServiceManager();
        webServiceManager.doJsonObjectRequest(Request.Method.POST, url, new JSONObject(params), new WebServiceManager.RequestListener<String>() {
            @Override
            public void onSuccess(String response) {
//                Log.e(TAG, "response:" + response);
            }

            @Override
            public void onFailure(ErrorDto error) {
                Log.e(TAG, "UpdateAndroidDevice onFailure");
            }
        });
    }

    public void UpdateTimeZone(String deviceID) {
        String UpdateAndroidDevice_TimezoneOffset = URL + "/UpdateAndroidDevice_TimezoneOffset";
        String url = UpdateAndroidDevice_TimezoneOffset;
        Map<String, Object> params = new HashMap<>();
        params.put("sessionId", mPref.getCurrentMobileSessionId());
        params.put("languageCode", LanguageUtils.getPhoneLanguage());
        params.put("timeZoneOffset", DeviceUtilities.getTimeZoneOffset());
        params.put("deviceID", deviceID);
        WebServiceManager webServiceManager = new WebServiceManager();
        webServiceManager.doJsonObjectRequest(Request.Method.POST, url, new JSONObject(params), new WebServiceManager.RequestListener<String>() {
            @Override
            public void onSuccess(String response) {
//                Log.e(TAG, "response:" + response);
            }

            @Override
            public void onFailure(ErrorDto error) {
                Log.e(TAG, "UpdateTimeZone onFailure");
            }
        });
    }

    public void InsertNotification(long dayNo, ArrayList<NotifyTime> arrayList) {
        String InsertNotification = URL + "/InsertNotification";
        String url = InsertNotification;
        for (int i = 0; i < arrayList.size(); i++) {
            if (arrayList.get(i).getNotificationType() == 0) {
            } else {
                int notificationType = arrayList.get(i).getNotificationType() - 1;
                String hour = arrayList.get(i).getNotificationTime().substring(0, 2);
                String minute = arrayList.get(i).getNotificationTime().substring(2, 4);
                String notificationTime = hour + ":" + minute + ":00";
//                Log.e(TAG, "notificationTime:" + notificationTime);
                Map<String, Object> params = new HashMap<>();
                params.put("sessionId", mPref.getCurrentMobileSessionId());
                params.put("languageCode", LanguageUtils.getPhoneLanguage());
                params.put("timeZoneOffset", DeviceUtilities.getTimeZoneOffset());
                params.put("dayNo", dayNo);
                params.put("notificationType", notificationType);
                params.put("notificationTime", notificationTime);
                Log.d(TAG, "notificationType:" + notificationType);
                Log.d(TAG, "notificationTime:" + notificationTime);
                WebServiceManager webServiceManager = new WebServiceManager();
                webServiceManager.doJsonObjectRequest(Request.Method.POST, url, new JSONObject(params), new WebServiceManager.RequestListener<String>() {
                    @Override
                    public void onSuccess(String response) {
//                        Log.e(TAG, "InsertNotification:" + response);
                    }

                    @Override
                    public void onFailure(ErrorDto error) {
                        Log.e(TAG, "InsertNotification onFailure");
                    }
                });
            }
        }

    }

    public void GetNotification(long dayNo, final GetNotifyTime getNotifyTime) {
//        Log.e(TAG,"dayNo:"+dayNo);
        String GetNotifications = URL + "/GetNotifications";
        String url = GetNotifications;
        Map<String, Object> params = new HashMap<>();
        params.put("sessionId", mPref.getCurrentMobileSessionId());
        params.put("languageCode", LanguageUtils.getPhoneLanguage());
        params.put("timeZoneOffset", DeviceUtilities.getTimeZoneOffset());
        params.put("dayNo", dayNo);
        WebServiceManager webServiceManager = new WebServiceManager();
        webServiceManager.doJsonObjectRequest(Request.Method.POST, url, new JSONObject(params), new WebServiceManager.RequestListener<String>() {
            @Override
            public void onSuccess(String response) {
//                Log.e(TAG, "GetNotifications:" + response);
//                NotifyTime notifyTime = new Gson().fromJson(response, NotifyTime.class);

                Gson gson = new Gson();
                Type type = new TypeToken<ArrayList<NotifyTime>>() {
                }.getType();
                ArrayList<NotifyTime> arr = gson.fromJson(response, type);

                getNotifyTime.onGetNotifyTimeSuccess(arr);
            }

            @Override
            public void onFailure(ErrorDto error) {
                Log.e(TAG, "GetNotification onFailure");
            }
        });
    }

    public void DeleteNotification(long dayNo) {
        String DeleteNotification = URL + "/DeleteNotification";
        String url = DeleteNotification;
        Map<String, Object> params = new HashMap<>();
        params.put("sessionId", mPref.getCurrentMobileSessionId());
        params.put("languageCode", LanguageUtils.getPhoneLanguage());
        params.put("timeZoneOffset", DeviceUtilities.getTimeZoneOffset());
        params.put("dayNo", dayNo);
        WebServiceManager webServiceManager = new WebServiceManager();
        webServiceManager.doJsonObjectRequest(Request.Method.POST, url, new JSONObject(params), new WebServiceManager.RequestListener<String>() {
            @Override
            public void onSuccess(String response) {
//                Log.e(TAG, "DeleteNotification:" + response);

            }

            @Override
            public void onFailure(ErrorDto error) {
                Log.e(TAG, "DeleteNotification onFailure");
            }
        });
    }

    public void UpdateNotification(long c, ArrayList<NotifyTime> arrayList, long ddayNo) {
        String UpdateNotification = URL + "/UpdateNotification";
        String url = UpdateNotification;
        if (arrayList.size() > 0) {
            for (int i = 0; i < arrayList.size(); i++) {
                int no = arrayList.get(i).getNotificationType();
                int notificationType = 0;
                if (no == 0) notificationType = 2;
                else
                    notificationType = no - 1;
                int notiNo = arrayList.get(i).getNotiNo();
                String hour = arrayList.get(i).getNotificationTime().substring(0, 2);
                String minute = arrayList.get(i).getNotificationTime().substring(2, 4);
                String notificationTime = hour + ":" + minute + ":00";
                if (notiNo == 0) {
                    ArrayList<NotifyTime> arr = new ArrayList<NotifyTime>();
                    arr.add(arrayList.get(i));
                    InsertNotification(ddayNo, arr);
                } else {
                    Map<String, Object> params = new HashMap<>();
                    params.put("sessionId", mPref.getCurrentMobileSessionId());
                    params.put("languageCode", LanguageUtils.getPhoneLanguage());
                    params.put("timeZoneOffset", DeviceUtilities.getTimeZoneOffset());
                    params.put("notiNo", notiNo);
                    params.put("notificationType", notificationType);
                    params.put("notificationTime", notificationTime);

                    WebServiceManager webServiceManager = new WebServiceManager();
                    webServiceManager.doJsonObjectRequest(Request.Method.POST, url, new JSONObject(params), new WebServiceManager.RequestListener<String>() {
                        @Override
                        public void onSuccess(String response) {
//                                Log.e(TAG, "UpdateNotification:" + response);
                        }

                        @Override
                        public void onFailure(ErrorDto error) {
                            Log.e(TAG, "UpdateNotification onFailure");
                        }
                    });

                }
            }
        }
    }

    public void InsertCoveredDay(long ddayNo, String coveredDate, final InsertCoveredDaySuccess insertCoveredDaySuccess) {
        String InsertCoveredDay = URL + "/InsertCoveredDay";
        String url = InsertCoveredDay;
        Map<String, Object> params = new HashMap<>();
        params.put("sessionId", mPref.getCurrentMobileSessionId());
        params.put("languageCode", LanguageUtils.getPhoneLanguage());
        params.put("timeZoneOffset", DeviceUtilities.getTimeZoneOffset());
        params.put("dayNo", ddayNo);
        params.put("coveredDate", coveredDate); // coveredDate: 2016-07-13

        WebServiceManager webServiceManager = new WebServiceManager();
        webServiceManager.doJsonObjectRequest(Request.Method.POST, url, new JSONObject(params), new WebServiceManager.RequestListener<String>() {
            @Override
            public void onSuccess(String response) {
                insertCoveredDaySuccess.onInsertCoveredDaySuccess(Long.parseLong(response));
            }

            @Override
            public void onFailure(ErrorDto error) {
                Log.e(TAG, "InsertCoveredDay onFailure");
            }
        });
    }

    public void UpdateGroupNoOfDay(long ddayNo, long groupNo, final UpdateGroupCalllback updateGroupCalllback) {
        String UpdateGroupNoOfDay = URL + "/UpdateGroupNoOfDay";
        String url = UpdateGroupNoOfDay;
        Map<String, Object> params = new HashMap<>();
        params.put("sessionId", mPref.getCurrentMobileSessionId());
        params.put("languageCode", LanguageUtils.getPhoneLanguage());
        params.put("timeZoneOffset", DeviceUtilities.getTimeZoneOffset());
        params.put("dayNo", ddayNo);
        params.put("groupNo", groupNo);
        Log.d(TAG, "UpdateGroupNoOfDay param:" + new Gson().toJson(params));
        WebServiceManager webServiceManager = new WebServiceManager();
        webServiceManager.doJsonObjectRequest(Request.Method.POST, url, new JSONObject(params), new WebServiceManager.RequestListener<String>() {
            @Override
            public void onSuccess(String response) {
                Log.d(TAG, "UpdateGroupNoOfDay:" + response);
                if (response.equals("true"))
                    updateGroupCalllback.onUpdateSuccess();
                else updateGroupCalllback.onUpdateFail();

            }

            @Override
            public void onFailure(ErrorDto error) {
                updateGroupCalllback.onUpdateFail();
                Log.e(TAG, "UpdateGroupNoOfDay onFailure");
            }
        });
    }

    public void DeleteCompletedRecord(long recordNo, final DeleteCompletedRecordSuccess callback) {
        String DeleteCompletedRecord = URL + "/DeleteCompletedRecord";
        String url = DeleteCompletedRecord;
        Map<String, Object> params = new HashMap<>();
        params.put("sessionId", mPref.getCurrentMobileSessionId());
        params.put("languageCode", LanguageUtils.getPhoneLanguage());
        params.put("timeZoneOffset", DeviceUtilities.getTimeZoneOffset());
        params.put("recordNo", recordNo);

        WebServiceManager webServiceManager = new WebServiceManager();
        webServiceManager.doJsonObjectRequest(Request.Method.POST, url, new JSONObject(params), new WebServiceManager.RequestListener<String>() {
            @Override
            public void onSuccess(String response) {
//                Log.e(TAG, "DeleteCompletedRecord:" + response);
                callback.onDeleteCompletedRecordSuccess();
            }

            @Override
            public void onFailure(ErrorDto error) {
                Log.e(TAG, "DeleteCompletedRecord onFailure");
            }
        });
    }

    public void DeleteCoveredDay(long dataNo, final DeleteCoveredDaySuccess callback) {
        String DeleteCoveredDay = URL + "/DeleteCoveredDay";
        String url = DeleteCoveredDay;
        Map<String, Object> params = new HashMap<>();
        params.put("sessionId", mPref.getCurrentMobileSessionId());
        params.put("languageCode", LanguageUtils.getPhoneLanguage());
        params.put("timeZoneOffset", DeviceUtilities.getTimeZoneOffset());
        params.put("dataNo", dataNo);

        WebServiceManager webServiceManager = new WebServiceManager();
        webServiceManager.doJsonObjectRequest(Request.Method.POST, url, new JSONObject(params), new WebServiceManager.RequestListener<String>() {
            @Override
            public void onSuccess(String response) {
//                Log.e(TAG, "DeleteCoveredDay:" + response);
                callback.onDeleteCoveredDaySuccess();
            }

            @Override
            public void onFailure(ErrorDto error) {
//                Log.e(TAG, "DeleteCoveredDay onFailure");
            }
        });
    }

    public void Delete_Share(final long dayNo, final String JSON_SHARER_TYPE) {
        String DeleteSharers = URL + "/DeleteSharers";
        String url = DeleteSharers;
        Map<String, Object> params = new HashMap<>();
        params.put("sessionId", mPref.getCurrentMobileSessionId());
        params.put("languageCode", LanguageUtils.getPhoneLanguage());
        params.put("timeZoneOffset", DeviceUtilities.getTimeZoneOffset());
        params.put("dayNo", dayNo);

        WebServiceManager webServiceManager = new WebServiceManager();
        webServiceManager.doJsonObjectRequest(Request.Method.POST, url, new JSONObject(params), new WebServiceManager.RequestListener<String>() {
            @Override
            public void onSuccess(String response) {
//                Log.e(TAG, "Delete_Share:" + response);
                share1(dayNo, JSON_SHARER_TYPE);
            }

            @Override
            public void onFailure(ErrorDto error) {
//                Log.e(TAG, "Delete_Share onFailure");
                share1(dayNo, JSON_SHARER_TYPE);
            }
        });
    }

    public void Delete_Director(final long dayNo, final String JSON_PERSON_CONCERNED_TYPE) {
        String DeleteDirector = URL + "/DeleteDirector";
        String url = DeleteDirector;
        Map<String, Object> params = new HashMap<>();
        params.put("sessionId", mPref.getCurrentMobileSessionId());
        params.put("languageCode", LanguageUtils.getPhoneLanguage());
        params.put("timeZoneOffset", DeviceUtilities.getTimeZoneOffset());
        params.put("dayNo", dayNo);

        WebServiceManager webServiceManager = new WebServiceManager();
        webServiceManager.doJsonObjectRequest(Request.Method.POST, url, new JSONObject(params), new WebServiceManager.RequestListener<String>() {
            @Override
            public void onSuccess(String response) {
//                Log.e(TAG, "DeleteDirector:" + response);
                share2(dayNo, JSON_PERSON_CONCERNED_TYPE);
            }

            @Override
            public void onFailure(ErrorDto error) {
//                Log.e(TAG, "DeleteDirector onFailure");
                share2(dayNo, JSON_PERSON_CONCERNED_TYPE);
            }
        });
    }

    public void Delete_Manager(final long dayNo, final String JSON_ADMIN_TYPE) {
        String DeleteManagers = URL + "/DeleteManagers";
        String url = DeleteManagers;
        Map<String, Object> params = new HashMap<>();
        params.put("sessionId", mPref.getCurrentMobileSessionId());
        params.put("languageCode", LanguageUtils.getPhoneLanguage());
        params.put("timeZoneOffset", DeviceUtilities.getTimeZoneOffset());
        params.put("dayNo", dayNo);

        WebServiceManager webServiceManager = new WebServiceManager();
        webServiceManager.doJsonObjectRequest(Request.Method.POST, url, new JSONObject(params), new WebServiceManager.RequestListener<String>() {
            @Override
            public void onSuccess(String response) {
//                Log.e(TAG, "Delete_Manager:" + response);
                share3(dayNo, JSON_ADMIN_TYPE);
            }

            @Override
            public void onFailure(ErrorDto error) {
//                Log.e(TAG, "Delete_Manager onFailure");
                share3(dayNo, JSON_ADMIN_TYPE);
            }
        });
    }

    public void GetCountOfUnreadedDdays(final onGetCountOfUnreadedDdays callback) {
        String GetCountOfUnreadedDdays = URL + "/GetCountOfUnreadedDdays";
        String url = GetCountOfUnreadedDdays;
        Map<String, Object> params = new HashMap<>();
        params.put("sessionId", mPref.getCurrentMobileSessionId());
        params.put("languageCode", LanguageUtils.getPhoneLanguage());
        params.put("timeZoneOffset", DeviceUtilities.getTimeZoneOffset());
        WebServiceManager webServiceManager = new WebServiceManager();
        webServiceManager.doJsonObjectRequest(Request.Method.POST, url, new JSONObject(params), new WebServiceManager.RequestListener<String>() {
            @Override
            public void onSuccess(String response) {
//                Log.e(TAG, "GetCountOfUnreadedDdays onSuccess:" + response);
                callback.onGetCountOfUnreadedDdaysSuccess(Integer.parseInt(response));
            }

            @Override
            public void onFailure(ErrorDto error) {
                callback.onFail();
                Log.e(TAG, "GetCountOfUnreadedDdays onFailure:");
            }
        });
    }

    public void GetRepetitionString(String repeatOptions, final IFRepetitionString ifRepetitionString) {
        String GetRepetitionString = URL + "/GetRepetitionString";
        String url = GetRepetitionString;
//        repeatOptions="[{TypeName:\"CheckDPlusDay\",StartDate:\"20160725\",Lunar:1}]";
//        Log.e(TAG, repeatOptions);
        Map<String, Object> params = new HashMap<>();
        params.put("sessionId", mPref.getCurrentMobileSessionId());
        params.put("languageCode", LanguageUtils.getPhoneLanguage());
        params.put("timeZoneOffset", DeviceUtilities.getTimeZoneOffset());
        params.put("repeatOptions", repeatOptions);

        WebServiceManager webServiceManager = new WebServiceManager();
        webServiceManager.doJsonObjectRequest(Request.Method.POST, url, new JSONObject(params), new WebServiceManager.RequestListener<String>() {
            @Override
            public void onSuccess(String response) {
                ifRepetitionString.onGetRepetitionStringSuccess(response);
//                Log.e(TAG, "GetRepetitionString onSuccess:" + response);
            }

            @Override
            public void onFailure(ErrorDto error) {
//                Log.e(TAG, "GetRepetitionString onFailure:");
            }
        });
    }

    public void updatePassword(String _domain, String originalPassword, String newPassword, final IF_UpdatePass callback) {
        final String url = _domain + WebClient.SERVICE_URL_UPDATE_PASSWORD;
        Log.d(TAG, "updatePassword url:" + url);
        Map<String, Object> params = new HashMap<>();
        params.put("languageCode", LanguageUtils.getPhoneLanguage());
        params.put("timeZoneOffset", DeviceUtilities.getTimeZoneOffset());
        params.put("sessionId", mPref.getCurrentMobileSessionId());
        params.put("originalPassword", originalPassword);
        params.put("newPassword", newPassword);
        WebServiceManager webServiceManager = new WebServiceManager();
        webServiceManager.doJsonObjectRequest(Request.Method.POST, url, new JSONObject(params), new WebServiceManager.RequestListener<String>() {
            @Override
            public void onSuccess(String response) {
                if (response.length() > 0 && response.startsWith("{")) {
                    try {
                        Log.d(TAG, "updatePassword response:" + response);
                        onUpdate obj = new Gson().fromJson(response, onUpdate.class);
                        if (obj != null) {
                            boolean success = obj.isSuccess();
                            String newSessionID = obj.getNewSessionID();
                            if (success && newSessionID.length() > 0) {
                                callback.onSuccess(newSessionID);
                            } else {
                                callback.onFail();
                            }
                        } else {
                            callback.onFail();
                        }
                    } catch (Exception e) {
                        callback.onFail();
                        e.printStackTrace();
                    }
                } else {
                    callback.onFail();
                }

            }

            @Override
            public void onFailure(ErrorDto error) {
                Log.d(TAG, "updatePassword error");
                callback.onFail();
            }
        });
    }

    public class onUpdate {
        boolean success;
        String newSessionID;

        public onUpdate() {
        }

        public onUpdate(boolean success, String newSessionID) {
            this.success = success;
            this.newSessionID = newSessionID;
        }

        public boolean isSuccess() {
            return success;
        }

        public void setSuccess(boolean success) {
            this.success = success;
        }

        public String getNewSessionID() {
            return newSessionID;
        }

        public void setNewSessionID(String newSessionID) {
            this.newSessionID = newSessionID;
        }
    }
}