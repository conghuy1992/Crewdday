package com.dazone.crewdday.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.dazone.crewdday.Cons;
import com.dazone.crewdday._Application;

import java.io.IOException;


public class PreferenceUtilities {
    private SharedPreferences mPreferences;

    private final String USER_JSON_INFO = "user_json";
    private final String ACCESSTOKEN = "accesstoken";

    private final String KEY_GCM = "googlecloudmsg";
    private final String DOMAIN = "DOMAIN";
    private final String KEY_CURRENT_SERVICE_DOMAIN = "currentServiceDomain";
    private final String KEY_CURRENT_COMPANY_DOMAIN = "currentCompanyDomain";
    private final String KEY_CURRENT_COMPANY_NO = "currentCompanyNo";
    private final String KEY_CURRENT_MOBILE_SESSION_ID = "currentMobileSessionId";
    private final String KEY_CURRENT_USER_ID = "currentUserID";
    private final String KEY_SECTION_FRAGMENT_ALL = "keyALL";
    private final String KEY_SECTION_FRAGMENT_MINUS = "keyMinus";
    private final String KEY_SECTION_FRAGMENT_COMPLETE = "keyComplete";
    private final String KEY_SECTION_FRAGMENT_PLUS = "keyPlus";
    private final String KEY_USER_NAME = "username";
    private final String KEY_EMAIL = "email";
    private final String KEY_PermissionType = "PermissionType";
    private final String KEY_id = "Id";
    private final String KEY_DepartNo = "DepartNo";

    private final String KEY_AVATAR = "avatar";
    private final String KEY_USER_ID = "userId";
    private final String KEY_COMPANY_NAME = "companyName";
    private final String KEY_SHARE_DDAY_SHOW = "KEY_SHARE_DDAY_SHOW";
    private final String KEY_REMOVE = "KEY_REMOVE";
    private final String KEY_SET_SHARE_DDAY_SHOW_FT = "KEY_SET_SHARE_DDAY_SHOW_FT";

    private final String KEY_SELECTED_GROUP = "KEY_SELECTED_GROUP";
    private final String KEY_SEARCH_TEXT = "KEY_SEARCH_TEXT";

    private final String KEY_GROUP_POS = "KEY_GROUP_POS";
    private final String KEY_GROUP_ID = "KEY_GROUP_ID";
    private final String KEY_DDAY_TYPE = "KEY_DDAY_TYPE";
    private final String KEY_ALREADY_DDAY_TYPE = "KEY_ALREADY_DDAY_TYPE";
    private final String KEY_SHARERS_LIST = "KEY_SHARERS_LIST";
    private final String KEY_PERSON_CONCERNED_LIST = "KEY_PERSON_CONCERNED_LIST";
    private final String KEY_ADMINS_LIST = "KEY_ADMINS_LIST";


    private final String KEY_SPECIAL_LIST = "KEY_SPECIAL_LIST";
    private final String KEY_SPECIAL_SPECIFIC_RO = "KEY_SPECIAL_SPECIFIC_RO";
    private final String KEY_SPECIAL_PLUS_RO = "KEY_SPECIAL_PLUS_RO";
    private final String KEY_SPECIAL_CALENDAR = "KEY_SPECIAL_CALENDAR";
    private final String KEY_SPECIAL_FT = "KEY_SPECIAL_FT";


    private final String KEY_DAILY_INTERVAL_POSITION = "DAILY_INTERVAL_POSITION";
    private final String KEY_DAILY_INTERVAL = "DAILY_INTERVAL";
    private final String KEY_DAILY_START_TYPE_POSITION = "DAILY_START_TYPE_POSITION";
    private final String KEY_DAILY_START_TYPE = "DAILY_START_TYPE";
    private final String KEY_DAILY_HOLIDAY_POSITION = "DAILY_HOLIDAY_POSITION";
    private final String KEY_DAILY_HOLIDAY_TYPE = "DAILY_HOLIDAY_TYPE";
    private final String KEY_DAILY_START_DATE = "START_DATE";
    private final String KEY_DAILY_END_DATE = "END_DATE";
    private final String KEY_DAILY_CHECKBOX = "CHECKBOX";
    private final String KEY_DAILY_RO = "KEY_DAILY_RO";


    private final String KEY_WEEKLY_INTERVAL_POSITION = "KEY_WEEKLY_INTERVAL_POSITION";
    private final String KEY_WEEKLY_INTERVAL = "KEY_WEEKLY_INTERVAL";
    private final String KEY_WEEKLY_SPECIFIC_DAYS = "KEY_WEEKLY_SPECIFIC_DAYS";
    private final String KEY_WEEKLY_HOLIDAY_POSITION = "KEY_WEEKLY_HOLIDAY_POSITION";
    private final String KEY_WEEKLY_HOLIDAY_TYPE = "KEY_WEEKLY_HOLIDAY_TYPE";
    private final String KEY_WEEKLY_START_DATE = "KEY_WEEKLY_START_DATE";
    private final String KEY_WEEKLY_END_DATE = "KEY_WEEKLY_END_DATE";
    private final String KEY_WEEKLY_CHECKBOX = "KEY_WEEKLY_CHECKBOX";
    private final String KEY_WEEKLY_RO = "KEY_WEEKLY_RO";


    private final String KEY_MONTHLY_INTERVAL_POSITION = "KEY_MONTHLY_INTERVAL_POSITION";
    private final String KEY_MONTHLY_INTERVAL = "KEY_MONTHLY_INTERVAL";
    private final String KEY_MONTHLY_HOLIDAY_POSITION = "KEY_MONTHLY_HOLIDAY_POSITION";
    private final String KEY_MONTHLY_HOLIDAY_TYPE = "KEY_MONTHLY_HOLIDAY_TYPE";
    private final String KEY_MONTHLY_START_DATE = "KEY_MONTHLY_START_DATE";
    private final String KEY_MONTHLY_END_DATE = "KEY_MONTHLY_END_DATE";
    private final String KEY_MONTHLY_CHECKBOX = "KEY_MONTHLY_CHECKBOX";
    private final String KEY_MONTHLY_RO_1 = "KEY_MONTHLY_RO_1";
    private final String KEY_MONTHLY_RO_2 = "KEY_MONTHLY_RO_2";
    private final String KEY_MONTHLY_LIST = "KEY_MONTHLY_LIST";
    private final String KEY_MONTHLY_POSITION = "KEY_MONTHLY_POSITION";
    private final String KEY_MONTHLY_POSITION_ADDED = "KEY_MONTHLY_POSITION_ADDED";
    private final String KEY_MONTHLY_DATE_RO_EMPTY = "KEY_MONTHLY_DATE_RO_EMPTY";
    private final String KEY_MONTHLY_WEEK_RO_EMPTY = "KEY_MONTHLY_WEEK_RO_EMPTY";


    private final String KEY_ANNUALLY_INTERVAL_POSITION = "KEY_ANNUALLY_INTERVAL_POSITION";
    private final String KEY_ANNUALLY_INTERVAL = "KEY_ANNUALLY_INTERVAL";
    private final String KEY_ANNUALLY_HOLIDAY_POSITION = "KEY_ANNUALLY_HOLIDAY_POSITION";
    private final String KEY_ANNUALLY_HOLIDAY_TYPE = "KEY_ANNUALLY_HOLIDAY_TYPE";
    private final String KEY_ANNUALLY_START_DATE = "KEY_ANNUALLY_START_DATE";
    private final String KEY_ANNUALLY_END_DATE = "KEY_ANNUALLY_END_DATE";
    private final String KEY_ANNUALLY_CHECKBOX = "KEY_ANNUALLY_CHECKBOX";
    private final String KEY_ANNUALLY_LIST = "KEY_ANNUALLY_LIST";
    private final String KEY_ANNUALLY_DATE_RO_EMPTY = "KEY_ANNUALLY_DATE_RO_EMPTY";
    private final String KEY_ANNUALLY_WEEK_RO_EMPTY = "KEY_ANNUALLY_WEEK_RO_EMPTY";
    private final String KEY_ANNUALLY_RO_1 = "KEY_ANNUALLY_RO_1";
    private final String KEY_ANNUALLY_RO_2 = "KEY_ANNUALLY_RO_2";

    private final String NOTIFI_MAIL = "notifi_newmail";
    private final String NOTIFI_SOUND = "notifi_sound";
    private final String NOTIFI_VIBRATE = "notifi_vibrate";
    private final String NOTIFI_TIME = "notifi_time";
    private final String START_TIME = "starttime";
    private final String END_TIME = "endtime";
    private final String TIME_ZONE = "timezone_off";


    private final String DOMAIN_LOGIN = "DOMAIN_LOGIN";
    private final String USERNAME_LOGIN = "USERNAME_LOGIN";
    private final String PASSWORD_LOGIN = "PASSWORD_LOGIN";
    private final String FIRST_LOGIN = "FIRST_LOGIN";

    public void setFIRST_LOGIN(boolean b) {
        mPreferences.edit().putBoolean(FIRST_LOGIN, b).apply();
    }

    public boolean getFIRST_LOGIN() {
        return mPreferences.getBoolean(FIRST_LOGIN, true);
    }

    public PreferenceUtilities() {
        mPreferences = _Application.getInstance().getApplicationContext().getSharedPreferences("CrewApproval_Prefs", Context.MODE_PRIVATE);
    }

    public void setPASSWORD_LOGIN(String domain) {
        mPreferences.edit().putString(PASSWORD_LOGIN, domain).apply();
    }

    public String getPASSWORD_LOGIN() {
        return mPreferences.getString(PASSWORD_LOGIN, "");
    }

    public void setUSERNAME_LOGIN(String domain) {
        mPreferences.edit().putString(USERNAME_LOGIN, domain).apply();
    }

    public String getUSERNAME_LOGIN() {
        return mPreferences.getString(USERNAME_LOGIN, "");
    }

    public void setDOMAIN_LOGIN(String domain) {
        mPreferences.edit().putString(DOMAIN_LOGIN, domain).apply();
    }

    public String getDOMAIN_LOGIN() {
        return mPreferences.getString(DOMAIN_LOGIN, "");
    }


    public void setTIME_ZONE(int domain) {
        mPreferences.edit().putInt(TIME_ZONE, domain).apply();
    }

    public int getTIME_ZONE() {
        return mPreferences.getInt(TIME_ZONE, DeviceUtilities.getTimeZoneOffset());
    }

    public void setEND_TIME(String domain) {
        mPreferences.edit().putString(END_TIME, domain).apply();
    }

    public String getEND_TIME() {
        return mPreferences.getString(END_TIME, "PM 06:00");
    }

    public void setSTART_TIME(String domain) {
        mPreferences.edit().putString(START_TIME, domain).apply();
    }

    public String getSTART_TIME() {
        return mPreferences.getString(START_TIME, "AM 08:00");
    }

    public void setNOTIFI_TIME(boolean b) {
        mPreferences.edit().putBoolean(NOTIFI_TIME, b).apply();
    }

    public boolean getNOTIFI_TIME() {
        return mPreferences.getBoolean(NOTIFI_TIME, false);
    }

    public void setNOTIFI_VIBRATE(boolean b) {
        mPreferences.edit().putBoolean(NOTIFI_VIBRATE, b).apply();
    }

    public boolean getNOTIFI_VIBRATE() {
        return mPreferences.getBoolean(NOTIFI_VIBRATE, true);
    }

    public void setNOTIFI_MAIL(boolean b) {
        mPreferences.edit().putBoolean(NOTIFI_MAIL, b).apply();
    }

    public boolean getNOTIFI_MAIL() {
        return mPreferences.getBoolean(NOTIFI_MAIL, true);
    }

    public void setNOTIFI_SOUND(boolean b) {
        mPreferences.edit().putBoolean(NOTIFI_SOUND, b).apply();
    }

    public boolean getNOTIFI_SOUND() {
        return mPreferences.getBoolean(NOTIFI_SOUND, true);
    }


    public void setGCMregistrationid(String domain) {
        mPreferences.edit().putString(KEY_GCM, domain).apply();
    }

    public String getGCMregistrationid() {
        return mPreferences.getString(KEY_GCM, "");
    }

    public void setDomain(String domain) {
        mPreferences.edit().putString(DOMAIN, domain).apply();
    }

    public String getDomain() {
        return mPreferences.getString(DOMAIN, "");
    }

    public void setCurrentServiceDomain(String domain) {
        mPreferences.edit().putString(KEY_CURRENT_SERVICE_DOMAIN, domain).apply();
    }

    public String getCurrentServiceDomain() {
        return mPreferences.getString(KEY_CURRENT_SERVICE_DOMAIN, "");
    }

    public void setCurrentCompanyDomain(String domain) {
//        Log.e("domain",domain);
        mPreferences.edit().putString(KEY_CURRENT_COMPANY_DOMAIN, domain).apply();
    }

    public String getCurrentCompanyDomain() {
//        Log.e("getCurrentCompanyDomain", mPreferences.getString(KEY_CURRENT_COMPANY_DOMAIN, ""));
        return mPreferences.getString(KEY_CURRENT_COMPANY_DOMAIN, "");
    }

    public void setCurrentCompanyNo(int companyNo) {
        mPreferences.edit().putInt(KEY_CURRENT_COMPANY_NO, companyNo).apply();
    }

    public int getCurrentCompanyNo() {
        return mPreferences.getInt(KEY_CURRENT_COMPANY_NO, 0);
    }

    public void setCurrentMobileSessionId(String sessionId) {
        mPreferences.edit().putString(KEY_CURRENT_MOBILE_SESSION_ID, sessionId).apply();
    }

    public String getCurrentMobileSessionId() {
        return mPreferences.getString(KEY_CURRENT_MOBILE_SESSION_ID, "");
    }


    public void putBooleanValue(String KEY, boolean value) {
        mPreferences.edit().putBoolean(KEY, value).apply();
    }


    public void putUserData(String userDataJson, String accessToken) {
        mPreferences.edit().putString(ACCESSTOKEN, accessToken).apply();
        mPreferences.edit().putString(USER_JSON_INFO, userDataJson).apply();
    }

    public String getUserJson() {
        return mPreferences.getString(USER_JSON_INFO, "");
    }

    public void removeUserData() {
        mPreferences.edit().remove(ACCESSTOKEN).apply();
        mPreferences.edit().remove(USER_JSON_INFO).apply();
    }


    public void setSelectedGroup(String userName) {
        mPreferences.edit().putString(KEY_SELECTED_GROUP, userName).apply();
    }

    public String getSelectedGroup() {
        return mPreferences.getString(KEY_SELECTED_GROUP, "");
    }


    public void setSearchText(String userName) {
        mPreferences.edit().putString(KEY_SEARCH_TEXT, userName).apply();
    }

    public String getSearchText() {
        return mPreferences.getString(KEY_SEARCH_TEXT, "");
    }


// ----------------------------------------------------------------------------------------------

    public void setUserName(String userName) {
        mPreferences.edit().putString(KEY_USER_NAME, userName).apply();
    }

    public String getUserName() {
        return mPreferences.getString(KEY_USER_NAME, "");
    }

    public void setDepartNo(int PermissionType) {
        mPreferences.edit().putInt(KEY_DepartNo, PermissionType).apply();
    }

    public int getDepartNo() {
        return mPreferences.getInt(KEY_DepartNo, 0);
    }

    public void setId(int PermissionType) {
        mPreferences.edit().putInt(KEY_id, PermissionType).apply();
    }

    public int getId() {
        return mPreferences.getInt(KEY_id, 0);
    }

    public void setPermissionType(int PermissionType) {
        mPreferences.edit().putInt(KEY_PermissionType, PermissionType).apply();
    }

    public int getPermissionType() {
        return mPreferences.getInt(KEY_PermissionType, 0);
    }

    public void setEmail(String email) {
        mPreferences.edit().putString(KEY_EMAIL, email).apply();
    }

    public String getEmail() {
        return mPreferences.getString(KEY_EMAIL, "");
    }

    public void setAvatar(String email) {
        mPreferences.edit().putString(KEY_AVATAR, email).apply();
    }

    public String getAvatar() {
        return mPreferences.getString(KEY_AVATAR, "");
    }

    public void setUserId(String email) {
        mPreferences.edit().putString(KEY_USER_ID, email).apply();
    }

    public String getUserId() {
        return mPreferences.getString(KEY_USER_ID, "");
    }

    public void setCompanyName(String email) {
        mPreferences.edit().putString(KEY_COMPANY_NAME, email).apply();
    }

    public String getCompanyName() {
        return mPreferences.getString(KEY_COMPANY_NAME, "");
    }

    // ----------------------------------------------------------------------------------------------

    public void setCurrentUserID(String userID) {
        mPreferences.edit().putString(KEY_CURRENT_USER_ID, userID).apply();
    }

    public String getCurrentUserID() {
        return mPreferences.getString(KEY_CURRENT_USER_ID, "");
    }


    //-----------------------------------------------------------------------------------------------

    public void setSectionFragmentAll(String section) {
        mPreferences.edit().putString(KEY_SECTION_FRAGMENT_ALL, section).apply();
    }

    public void setSectionFragmentMinus(String section) {
        mPreferences.edit().putString(KEY_SECTION_FRAGMENT_MINUS, section).apply();
    }

    public void setSectionFragmentComplete(String section) {
        mPreferences.edit().putString(KEY_SECTION_FRAGMENT_COMPLETE, section).apply();
    }

    public void setSectionFragmentPlus(String section) {
        mPreferences.edit().putString(KEY_SECTION_FRAGMENT_PLUS, section).apply();
    }

    public String getSectionFragmentAll() {
        return mPreferences.getString(KEY_SECTION_FRAGMENT_ALL, "");
    }

    public String getSectionFragmentMinus() {
        return mPreferences.getString(KEY_SECTION_FRAGMENT_MINUS, "");
    }

    public String getSectionFragmentComplete() {
        return mPreferences.getString(KEY_SECTION_FRAGMENT_COMPLETE, "");
    }

    public String getSectionFragmentPlus() {
        return mPreferences.getString(KEY_SECTION_FRAGMENT_PLUS, "");
    }


    public void setGroupPos(String groupPos) {
        mPreferences.edit().putString(KEY_GROUP_POS, groupPos).apply();
    }

    public String getGroupPos() {
        return mPreferences.getString(KEY_GROUP_POS, "");
    }

    public void setShareDdayShow(String groupPos) {
        mPreferences.edit().putString(KEY_SHARE_DDAY_SHOW, groupPos).apply();
    }

    public String getShareDdayShow() {
        return mPreferences.getString(KEY_SHARE_DDAY_SHOW, Cons.TRUE);
    }


    public void setShareDdayShowFT(String groupPos) {
        mPreferences.edit().putString(KEY_SET_SHARE_DDAY_SHOW_FT, groupPos).apply();
    }

    public String getShareDdayShowFT() {
        return mPreferences.getString(KEY_SET_SHARE_DDAY_SHOW_FT, "");
    }

    public void resetGroupPos() {
        mPreferences.edit().putString(KEY_GROUP_POS, "0").apply();
    }

    public void setGroupId(String groupPos) {
        mPreferences.edit().putString(KEY_GROUP_ID, groupPos).apply();
    }

    public String getGroupId() {
        return mPreferences.getString(KEY_GROUP_ID, "");
    }

    public void resetGroupId() {
        mPreferences.edit().putString(KEY_GROUP_ID, "").apply();
    }


    public void setDdayType(String groupPos) {
        mPreferences.edit().putString(KEY_DDAY_TYPE, groupPos).apply();
    }

    public String getDdayType() {
        return mPreferences.getString(KEY_DDAY_TYPE, "");
    }

    public void resetDdayType() {
        mPreferences.edit().putString(KEY_DDAY_TYPE, "1").apply();
    }


    //Already D+Day
    public void setAlreadyDdayType(String groupPos) {
        mPreferences.edit().putString(KEY_ALREADY_DDAY_TYPE, groupPos).apply();
    }

    public String getAlreadyDdayType() {
        return mPreferences.getString(KEY_ALREADY_DDAY_TYPE, "");
    }

    public void resetAlreadyDdayType() {
        mPreferences.edit().putString(KEY_ALREADY_DDAY_TYPE, Cons.FALSE).apply();
    }


    public void setSharersList(String groupPos) {
        mPreferences.edit().putString(KEY_SHARERS_LIST, groupPos).apply();
    }

    public String getSharersList() {
        return mPreferences.getString(KEY_SHARERS_LIST, "");
    }


    public void setPersonConcernedList(String groupPos) {
        mPreferences.edit().putString(KEY_PERSON_CONCERNED_LIST, groupPos).apply();
    }

    public String getPersonConcernedList() {
        return mPreferences.getString(KEY_PERSON_CONCERNED_LIST, "");
    }


    public void setAdminsList(String groupPos) {
        mPreferences.edit().putString(KEY_ADMINS_LIST, groupPos).apply();
    }

    public String getAdminsList() {
        return mPreferences.getString(KEY_ADMINS_LIST, "");
    }

    //-------------------------------------Special dday----------------------------------------------


    public void setSpecialSpecificRO(String section) {
        mPreferences.edit().putString(KEY_SPECIAL_SPECIFIC_RO, section).apply();
    }

    public String getSpecialSpecificRO() {
        return mPreferences.getString(KEY_SPECIAL_SPECIFIC_RO, "");
    }

    public void resetSpecialSpecificRO() {
        mPreferences.edit().putString(KEY_SPECIAL_SPECIFIC_RO, "").apply();
    }


    public void setSpecialPlusRO(String section) {
        mPreferences.edit().putString(KEY_SPECIAL_PLUS_RO, section).apply();
    }

    public String getSpecialPlusRO() {
        return mPreferences.getString(KEY_SPECIAL_PLUS_RO, "");
    }

    public void resetSpecialPlusRO() {
        mPreferences.edit().putString(KEY_SPECIAL_PLUS_RO, "").apply();
    }


    public void saveSpecialList(String json) throws IOException {
        mPreferences.edit().putString(KEY_SPECIAL_LIST, json).apply();
    }

    public String getSpecialList() throws IOException {
        return mPreferences.getString(KEY_SPECIAL_LIST, "");
    }


    public void resetSpecialList() {
        mPreferences.edit().putString(KEY_SPECIAL_LIST, "").apply();
    }


    public void setSpecialCalendar(String section) {
        mPreferences.edit().putString(KEY_SPECIAL_CALENDAR, section).apply();
    }

    public String getSpecicalCalendar() {
        return mPreferences.getString(KEY_SPECIAL_CALENDAR, "");
    }


    public void resetSpecicalCalendar() {
        mPreferences.edit().putString(KEY_SPECIAL_CALENDAR, String.valueOf(Cons.SOLAR_TYPE)).apply();
    }


    public void setSpecialFT(String section) {
        mPreferences.edit().putString(KEY_SPECIAL_FT, section).apply();
    }

    public String getSpecialFT() {
        return mPreferences.getString(KEY_SPECIAL_FT, "");
    }


    public void resetSpecialFT() {
        mPreferences.edit().putString(KEY_SPECIAL_FT, Cons.FALSE).apply();
    }

    //--------------------------------------Daily dday---------------------------------------------

    public void setDailyIntervalValue(String position) {
        mPreferences.edit().putString(KEY_DAILY_INTERVAL_POSITION, position).apply();
    }


    public String getDailyIntervalPos() {
        return mPreferences.getString(KEY_DAILY_INTERVAL_POSITION, "");
    }


    public void setDailyIntervalText(String interval) {
        mPreferences.edit().putString(KEY_DAILY_INTERVAL, interval).apply();
    }

    public String getDailyIntervalText() {
        return mPreferences.getString(KEY_DAILY_INTERVAL, "");
    }

    public void setDailyOptionPos(String position) {
        mPreferences.edit().putString(KEY_DAILY_START_TYPE_POSITION, position).apply();
    }

    public String getDailyOptionPos() {
        return mPreferences.getString(KEY_DAILY_START_TYPE_POSITION, "");
    }

    public void setDailyStartTypeText(String startType) {
        mPreferences.edit().putString(KEY_DAILY_START_TYPE, startType).apply();
    }

    public String getDailyStartTypeText() {
        return mPreferences.getString(KEY_DAILY_START_TYPE, "");
    }

    public void setDailyHolidayPos(String position) {
        mPreferences.edit().putString(KEY_DAILY_HOLIDAY_POSITION, position).apply();
    }


    public String getDailyHolidayPos() {
        return mPreferences.getString(KEY_DAILY_HOLIDAY_POSITION, "");
    }

    public void setDailyHolidayTypeText(String position) {
        mPreferences.edit().putString(KEY_DAILY_HOLIDAY_TYPE, position).apply();
    }

    public String getDailyHolidayTypeText() {
        return mPreferences.getString(KEY_DAILY_HOLIDAY_TYPE, "");
    }

    public void setDailyStartDate(String startDate) {
        mPreferences.edit().putString(KEY_DAILY_START_DATE, startDate).apply();
    }

    public String getDailyStartDate() {
        return mPreferences.getString(KEY_DAILY_START_DATE, "");
    }

    public void setDailyEndDate(String endDate) {
        mPreferences.edit().putString(KEY_DAILY_END_DATE, endDate).apply();
    }

    public String getDailyEndDate() {
        return mPreferences.getString(KEY_DAILY_END_DATE, "");
    }


    public void setDailyCheckbox(String checkbox) {
        mPreferences.edit().putString(KEY_DAILY_CHECKBOX, checkbox).apply();
    }

    public String getDailyCheckbox() {
        return mPreferences.getString(KEY_DAILY_CHECKBOX, "");
    }

    public void setDailyRO(String checkbox) {
        mPreferences.edit().putString(KEY_DAILY_RO, checkbox).apply();
    }

    public String getDailyRO() {
        return mPreferences.getString(KEY_DAILY_RO, "");
    }

    public void resetDailyRO() {
        mPreferences.edit().putString(KEY_DAILY_RO, "").apply();
    }


    //------------------------------------
    public void resetDailyIntervalValue() {
        mPreferences.edit().putString(KEY_DAILY_INTERVAL_POSITION, "0").apply();
    }

    public void resetDailyIntervalText() {
        mPreferences.edit().putString(KEY_DAILY_INTERVAL, "").apply();
    }

    public void resetDailyStartTypeValue() {
        mPreferences.edit().putString(KEY_DAILY_START_TYPE_POSITION, "0").apply();
    }

    public void resetDailyStartTypeText() {
        mPreferences.edit().putString(KEY_DAILY_START_TYPE, "").apply();
    }

    public void resetDailyHolidayValue() {
        mPreferences.edit().putString(KEY_DAILY_HOLIDAY_POSITION, "0").apply();
    }

    public void resetDailyHolidayType() {
        mPreferences.edit().putString(KEY_DAILY_HOLIDAY_TYPE, "").apply();
    }

    public void resetDailyStartDate() {
        mPreferences.edit().putString(KEY_DAILY_START_DATE, "").apply();
    }

    public void resetDailyEndDate() {
        mPreferences.edit().putString(KEY_DAILY_END_DATE, "").apply();
    }

    public void resetDailyCheckbox() {
        mPreferences.edit().putString(KEY_DAILY_CHECKBOX, "").apply();
    }

    //----------------------------------Weekly dday------------------------------------------


    public void setWeeklyIntervalPos(String position) {
        mPreferences.edit().putString(KEY_WEEKLY_INTERVAL_POSITION, position).apply();
    }


    public String getWeeklyIntervalPos() {
        return mPreferences.getString(KEY_WEEKLY_INTERVAL_POSITION, "");
    }


    public void setWeeklyIntervalText(String interval) {
        mPreferences.edit().putString(KEY_WEEKLY_INTERVAL, interval).apply();
    }

    public String getWeeklyIntervaText() {
        return mPreferences.getString(KEY_WEEKLY_INTERVAL, "");
    }

    public void setWeeklySpecificDays(String specificDays) {
        mPreferences.edit().putString(KEY_WEEKLY_SPECIFIC_DAYS, specificDays).apply();
    }

    public String getWeeklySpecificDays() {
        return mPreferences.getString(KEY_WEEKLY_SPECIFIC_DAYS, "");
    }


    public void setWeeklyHolidayPos(String position) {
        mPreferences.edit().putString(KEY_WEEKLY_HOLIDAY_POSITION, position).apply();
    }


    public String getWeeklyHolidayPos() {
        return mPreferences.getString(KEY_WEEKLY_HOLIDAY_POSITION, "");
    }

    public void setWeeklyHolidayText(String position) {
        mPreferences.edit().putString(KEY_WEEKLY_HOLIDAY_TYPE, position).apply();
    }

    public String getWeeklyHolidayText() {
        return mPreferences.getString(KEY_WEEKLY_HOLIDAY_TYPE, "");
    }

    public void setWeeklyStartDate(String startDate) {
        mPreferences.edit().putString(KEY_WEEKLY_START_DATE, startDate).apply();
    }

    public String getWeeklyStartDate() {
        return mPreferences.getString(KEY_WEEKLY_START_DATE, "");
    }

    public void setWeeklyEndDate(String endDate) {
        mPreferences.edit().putString(KEY_WEEKLY_END_DATE, endDate).apply();
    }

    public String getWeeklyEndDate() {
        return mPreferences.getString(KEY_WEEKLY_END_DATE, "");
    }


    public void setWeeklyCheckbox(String checkbox) {
        mPreferences.edit().putString(KEY_WEEKLY_CHECKBOX, checkbox).apply();
    }

    public String getWeeklyCheckbox() {
        return mPreferences.getString(KEY_WEEKLY_CHECKBOX, "");
    }

    public void setWeeklyRO(String checkbox) {
        mPreferences.edit().putString(KEY_WEEKLY_RO, checkbox).apply();
    }

    public String getWeeklyRO() {
        return mPreferences.getString(KEY_WEEKLY_RO, "");
    }

    public void resetWeeklyRO() {
        mPreferences.edit().putString(KEY_WEEKLY_RO, "").apply();
    }

    //------------------------------------
    public void resetWeeklyIntervalValue() {
        mPreferences.edit().putString(KEY_WEEKLY_INTERVAL_POSITION, "0").apply();
    }

    public void resetWeeklyInterval() {
        mPreferences.edit().putString(KEY_WEEKLY_INTERVAL, "").apply();
    }

    public void resetWeeklySpecificDays() {
        mPreferences.edit().putString(KEY_WEEKLY_SPECIFIC_DAYS, "").apply();
    }


    public void resetWeeklyHolidayValue() {
        mPreferences.edit().putString(KEY_WEEKLY_HOLIDAY_POSITION, "0").apply();
    }

    public void resetWeeklyHolidayType() {
        mPreferences.edit().putString(KEY_WEEKLY_HOLIDAY_TYPE, "").apply();
    }

    public void resetWeeklyStartDate() {
        mPreferences.edit().putString(KEY_WEEKLY_START_DATE, "").apply();
    }

    public void resetWeeklyEndDate() {
        mPreferences.edit().putString(KEY_WEEKLY_END_DATE, "").apply();
    }

    public void resetWeeklyCheckbox() {
        mPreferences.edit().putString(KEY_WEEKLY_CHECKBOX, "").apply();
    }


    //--------------------------------------Monthly list----------------------------------------//


    public void setMonthlyCurrentItemPosition(String position) {
        mPreferences.edit().putString(KEY_MONTHLY_POSITION, position).apply();
    }


    public String getMonthlyCurrentItemPosition() {
        return mPreferences.getString(KEY_MONTHLY_POSITION, "");
    }


    public void setMonthlyPositionAdded(String position) {
        mPreferences.edit().putString(KEY_MONTHLY_POSITION_ADDED, position).apply();
    }


    public String getMonthlyPositionAdded() {
        return mPreferences.getString(KEY_MONTHLY_POSITION_ADDED, "");
    }

    public void setMonthlyDateROEmpty(String position) {
        mPreferences.edit().putString(KEY_MONTHLY_DATE_RO_EMPTY, position).apply();
    }


    public String getMonthlyDateROEmpty() {
        return mPreferences.getString(KEY_MONTHLY_DATE_RO_EMPTY, "");
    }


    public void setMonthlyWeekROEmpty(String position) {
        mPreferences.edit().putString(KEY_MONTHLY_WEEK_RO_EMPTY, position).apply();
    }


    public String getMonthlyWeekROEmpty() {
        return mPreferences.getString(KEY_MONTHLY_WEEK_RO_EMPTY, "");
    }


    public void setMonthlyIntervalPos(String position) {
        mPreferences.edit().putString(KEY_MONTHLY_INTERVAL_POSITION, position).apply();
    }


    public String getMonthlyIntervalPos() {
        return mPreferences.getString(KEY_MONTHLY_INTERVAL_POSITION, "");
    }


    public void setMonthlyIntervalText(String interval) {
        mPreferences.edit().putString(KEY_MONTHLY_INTERVAL, interval).apply();
    }

    public String getMonthlyIntervalText() {
        return mPreferences.getString(KEY_MONTHLY_INTERVAL, "");
    }

    public void setMonthlyHolidayPos(String position) {
        mPreferences.edit().putString(KEY_MONTHLY_HOLIDAY_POSITION, position).apply();
    }


    public String getMonthlyHolidayPos() {
        return mPreferences.getString(KEY_MONTHLY_HOLIDAY_POSITION, "");
    }

    public void setMonthlyHolidayText(String position) {
        mPreferences.edit().putString(KEY_MONTHLY_HOLIDAY_TYPE, position).apply();
    }

    public String getMonthlyHolidayText() {
        return mPreferences.getString(KEY_MONTHLY_HOLIDAY_TYPE, "");
    }

    public void setMonthlyStartDate(String startDate) {
        mPreferences.edit().putString(KEY_MONTHLY_START_DATE, startDate).apply();
    }

    public String getMonthlyStartDate() {
        return mPreferences.getString(KEY_MONTHLY_START_DATE, "");
    }

    public void setMonthlyEndDate(String endDate) {
        mPreferences.edit().putString(KEY_MONTHLY_END_DATE, endDate).apply();
    }

    public String getMonthlyEndDate() {
        return mPreferences.getString(KEY_MONTHLY_END_DATE, "");
    }


    public void setMonthlyCheckbox(String checkbox) {
        mPreferences.edit().putString(KEY_MONTHLY_CHECKBOX, checkbox).apply();
    }

    public String getMonthlyCheckbox() {
        return mPreferences.getString(KEY_MONTHLY_CHECKBOX, "");
    }


    public void setMonthlyRO_1(String checkbox) {
        mPreferences.edit().putString(KEY_MONTHLY_RO_1, checkbox).apply();
    }

    public String getMonthlyRO_1() {
        return mPreferences.getString(KEY_MONTHLY_RO_1, "");
    }

    public void resetMonthlyRO_1() {
        mPreferences.edit().putString(KEY_MONTHLY_RO_1, "").apply();
    }

    public void setMonthlyRO_2(String checkbox) {
        mPreferences.edit().putString(KEY_MONTHLY_RO_2, checkbox).apply();
    }

    public String getMonthlyRO_2() {
        return mPreferences.getString(KEY_MONTHLY_RO_2, "");
    }

    public void resetMonthlyRO_2() {
        mPreferences.edit().putString(KEY_MONTHLY_RO_2, "").apply();
    }


    //------------------------------------
    public void resetMonthlyIntervalValue() {
        mPreferences.edit().putString(KEY_MONTHLY_INTERVAL_POSITION, "0").apply();
    }

    public void resetMonthlyInterval() {
        mPreferences.edit().putString(KEY_MONTHLY_INTERVAL, "").apply();
    }

    public void resetMonthlyHolidayValue() {
        mPreferences.edit().putString(KEY_MONTHLY_HOLIDAY_POSITION, "0").apply();
    }

    public void resetMonthlyHolidayType() {
        mPreferences.edit().putString(KEY_MONTHLY_HOLIDAY_TYPE, "").apply();
    }

    public void resetMonthlyStartDate() {
        mPreferences.edit().putString(KEY_MONTHLY_START_DATE, "").apply();
    }

    public void resetMonthlyEndDate() {
        mPreferences.edit().putString(KEY_MONTHLY_END_DATE, "").apply();
    }

    public void resetMonthlyCheckbox() {
        mPreferences.edit().putString(KEY_MONTHLY_CHECKBOX, "").apply();
    }

    public void saveMonthlyList(String json) throws IOException {
        mPreferences.edit().putString(KEY_MONTHLY_LIST, json).apply();
    }

    public String getMonthlyList() throws IOException {
        return mPreferences.getString(KEY_MONTHLY_LIST, "");
    }

    public void resetMonthlyList() {
        mPreferences.edit().putString(KEY_MONTHLY_LIST, "").apply();
    }


    //------------------------------------Annually list-------------------------------------//

    public void setAnnuallyIntervalPos(String position) {
        mPreferences.edit().putString(KEY_ANNUALLY_INTERVAL_POSITION, position).apply();
    }


    public String getAnnuallyIntervalPos() {
        return mPreferences.getString(KEY_ANNUALLY_INTERVAL_POSITION, "");
    }


    public void setAnnuallyIntervalText(String interval) {
        mPreferences.edit().putString(KEY_ANNUALLY_INTERVAL, interval).apply();
    }

    public String getAnnuallyIntervalText() {
        return mPreferences.getString(KEY_ANNUALLY_INTERVAL, "");
    }

    public void setAnnuallyHolidayPos(String position) {
        mPreferences.edit().putString(KEY_ANNUALLY_HOLIDAY_POSITION, position).apply();
    }


    public String getAnnuallyHolidayPos() {
        return mPreferences.getString(KEY_ANNUALLY_HOLIDAY_POSITION, "");
    }

    public void setAnnuallyHolidayText(String position) {
        mPreferences.edit().putString(KEY_ANNUALLY_HOLIDAY_TYPE, position).apply();
    }

    public String getAnnuallyHolidayText() {
        return mPreferences.getString(KEY_ANNUALLY_HOLIDAY_TYPE, "");
    }

    public void setAnnuallyStartDate(String startDate) {
        mPreferences.edit().putString(KEY_ANNUALLY_START_DATE, startDate).apply();
    }

    public String getAnnuallyStartDate() {
        return mPreferences.getString(KEY_ANNUALLY_START_DATE, "");
    }

    public void setAnnuallyEndDate(String endDate) {
        mPreferences.edit().putString(KEY_ANNUALLY_END_DATE, endDate).apply();
    }

    public String getAnnuallyEndDate() {
        return mPreferences.getString(KEY_ANNUALLY_END_DATE, "");
    }


    public void setAnnuallyCheckbox(String checkbox) {
        mPreferences.edit().putString(KEY_ANNUALLY_CHECKBOX, checkbox).apply();
    }

    public String getAnnuallyCheckbox() {
        return mPreferences.getString(KEY_ANNUALLY_CHECKBOX, "");
    }

    public void setAnnuallyRO_1(String checkbox) {
        mPreferences.edit().putString(KEY_ANNUALLY_RO_1, checkbox).apply();
    }

    public String getAnnuallyRO_1() {
        return mPreferences.getString(KEY_ANNUALLY_RO_1, "");
    }


    public void setAnnuallyDateROEmpty(String checkbox) {
        mPreferences.edit().putString(KEY_ANNUALLY_DATE_RO_EMPTY, checkbox).apply();
    }

    public String getAnnuallyDateROEmpty() {
        return mPreferences.getString(KEY_ANNUALLY_DATE_RO_EMPTY, "");
    }


    public void setAnnuallyWeekROEmpty(String checkbox) {
        mPreferences.edit().putString(KEY_ANNUALLY_WEEK_RO_EMPTY, checkbox).apply();
    }

    public String getAnnuallyWeekROEmpty() {
        return mPreferences.getString(KEY_ANNUALLY_WEEK_RO_EMPTY, "");
    }

    public void setAnnuallyRO_2(String checkbox) {
        mPreferences.edit().putString(KEY_ANNUALLY_RO_2, checkbox).apply();
    }

    public String getAnnuallyRO_2() {
        return mPreferences.getString(KEY_ANNUALLY_RO_2, "");
    }

    //------------------------------------
    public void resetAnnuallyIntervalPos() {
        mPreferences.edit().putString(KEY_ANNUALLY_INTERVAL_POSITION, "0").apply();
    }

    public void resetAnnuallyInterval() {
        mPreferences.edit().putString(KEY_ANNUALLY_INTERVAL, "").apply();
    }

    public void resetAnnuallyHolidayPos() {
        mPreferences.edit().putString(KEY_ANNUALLY_HOLIDAY_POSITION, "0").apply();
    }

    public void resetAnnuallyHolidayType() {
        mPreferences.edit().putString(KEY_ANNUALLY_HOLIDAY_TYPE, "").apply();
    }

    public void resetAnnuallyStartDate() {
        mPreferences.edit().putString(KEY_ANNUALLY_START_DATE, "").apply();
    }

    public void resetAnnuallyEndDate() {
        mPreferences.edit().putString(KEY_ANNUALLY_END_DATE, "").apply();
    }

    public void resetAnnuallyCheckbox() {
        mPreferences.edit().putString(KEY_ANNUALLY_CHECKBOX, "").apply();
    }

    public void saveAnnuallyList(String json) throws IOException {
        mPreferences.edit().putString(KEY_ANNUALLY_LIST, json).apply();
    }

    public String getAnnuallyList() throws IOException {
        return mPreferences.getString(KEY_ANNUALLY_LIST, "");
    }

    public void resetAnnuallyList() {
        mPreferences.edit().putString(KEY_ANNUALLY_LIST, "").apply();
    }


}