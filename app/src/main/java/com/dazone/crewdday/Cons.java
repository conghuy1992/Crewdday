package com.dazone.crewdday;

import android.os.Build;
import android.util.Log;

import com.dazone.crewdday.other.PersonData;

import java.util.ArrayList;

public class Cons {

    private static _Application application = _Application.getInstance();


    public static final int INDEX_SPECIAL = 0;
    public static final int INDEX_DAILY = 1;
    public static final int INDEX_WEEK = 2;
    public static final int INDEX_MONTH = 3;
    public static final int INDEX_YEAR = 4;


    public static String exist_Id_Login = "existIdLogin";
    public static final String COLOR_DAY_PLUS = "#277bcd";
    //    public static final String COLOR_CAN_HIDDEN = "#00690a";
    public static final String COLOR_CAN_HIDDEN = "#ff66ff";
    public static final String COLOR_RED = "#ff0000";
    //    public static final String COLOR_DDAY = "#4b9800";
    public static final String COLOR_DDAY = "#277bcd";
    public static final int REQUEST_CODE = 1;
    public static final String UPDATE_SUCCESS = "UPDATE_SUCCESS";
    public static final String SELECT_DATE = "Select Date";
    public static final String SELECT_WEEK = "Select Week";
    public static final String SELECT_DAY = "Select Day";
    public static final String SELECT_MONTH = "Select Month";
    public static final String SELECT_CALENDAR = "Select Calendar";
    public static final String SELECT_FREQUENCY = "Select Frequency";
    public static final String SELECT_OPTION = "Select Option";
    public static final String SELECT_HOLIDAY = "Select Holiday";
    public static final String SELECT_GROUP = "Select Group";
    public static final int FILE_PICKER_SELECT = 100;
    public static final int REQUEST_TIMEOUT_MS = 15000;
    public static final String BUNDLE_LIST_PERSON = "listPerson";
    public static final String BUNDLE_TYPE = "BUNDLE_TYPE";
    public static final String BUNDLE_ORG_DISPLAY_SELECTED_ONLY = "isDisplaySelectedOnly";
    public static final int ORGANIZATION_TO_ACTIVITY = 300;
    public static final int ORGANIZATION_TO_ACTIVITY_2 = 301;
    public static final int ORGANIZATION_TO_ACTIVITY_3 = 302;


    public static final int DETAILS_SUCCESS = 2;


    public static final String DETAILS_UPDATE_LIST_PRE = "DETAILS_UPDATE_LIST_PRE";
    public static final String KEY_DDAY_ID = "KEY_DDAY_ID";
    public static final String KEY_DDAY_REMAINING_DAYS = "Dday Remaining Days";
    public static final String KEY_DDAY_DATE = "Dday Date";
    public static final String KEY_DDAY_DIRECTOR_NO = "Dday Director No";
    public static final String KEY_DDAY_DIRECTOR_NAME = "Dday Director Name";
    public static final String KEY_DDAY_GROUP_ID = "KEY_DDAY_GROUP_ID";
    public static final String KEY_EDIT = "KEY_EDIT";
    public static final String KEY_DETAIL_MODEL = "KEY_DETAIL_MODEL";
    public static final String KEY_NOTIFI_TIME = "KEY_NOTIFI_TIME";
    public static final String KEY_CAN_HIDE = "KEY_CAN_HIDE";
    public static final String KEY_MANAGER = "KEY_MANAGER";
    public static final String KEY_IS_COMPLETED_RECORD = "KEY_IS_COMPLETED_RECORD";
    public static final String KEY_IS_COMPLETED = "KEY_IS_COMPLETED";
    public static final String KEY_IS_HIDDEN = "KEY_IS_HIDDEN";
    public static final String KEY_IS_HIDDEN_NO = "KEY_IS_HIDDEN_NO";
    public static final String KEY_IS_NEW = "KEY_IS_NEW";

    public static final String NULL_RESPONSE = "anyType{}";
    public static final String KEY_GROUP = "Group";
    public static final String ADD_USER = "ADD_USER";

    public static final String USERNO = "USERNO";
    public static final String NAME = "NAME";
    public static final String NAME_EN = "NAME_EN";
    public static final String MAIL = "MAIL";
    public static final String COMPANY = "COMPANY";
    public static final String OBJECT_PERSON = "OBJECT_PERSON";
    //Home Tab
    public static final String DDAY_ALL = "All";
    public static final String DDAY_MINUS = "D-Day";
    public static final String DDAY_COMPLETE = "D-Day Complete";
    public static final String DDAY_PLUS = "D+Day";


    public static final int KEY_GET_ALL_GROUP = -1;
    public static final int DDAY_ALL_MODE = 0;
    public static final int DDAY_MINUS_MODE = 1;
    public static final int DDAY_COMPLETE_MODE = 2;
    public static final int DDAY_PLUS_MODE = 3;
    public static final int DDAY_DEL_MODE = 0;

    //Home Menu
    public static final String ADD_GROUP = "Add Group";
    public static final String ALL_DDAY = "All Dday";
    public static final String MY_DDAY = "My Dday";
    public static final String SHARE_DDAY = "Share Dday";
    public static final String SHARE_DDAY_SHOW = "Share Dday Show";

    public static final int MENU_TYPE_NORMAL = 2;
    public static final int MENU_TYPE_SUB = 3;
    public static final int MENU_TYPE_OTHER = 4;

    public static final int NEW_GROUP_POSITION = 2;
    public static String prefname = "my_data_checked_state";

    public static final String FRAGMENT_TYPE_DAILY = "Daily";
    public static final String FRAGMENT_TYPE_WEEKLY = "Weekly";
    public static final String FRAGMENT_TYPE_MONTHLY = "Monthly";
    public static final String FRAGMENT_TYPE_ANNUALLY = "Annually";

    public static final int LIST_ITEMS = 1;
    public static final int PROGRESS_BAR = 2;
    public static final int TRY_AGAIN = 3;

    public static final int THE_FIRST = 1;
    public static final int THE_REST = 2;

    public static final String NULL = "Null";

    public static final String DATA = "data";
    public static final String USER_ID = "userID";
    public static final String Id = "Id";
    public static final String FULL_NAME = "FullName";
    public static final String MAIL_ADDRESS = "MailAddress";
    public static final String AVATAR = "avatar";
    public static final String END = "end";
    public static final String COMPANY_NAME = "NameCompany";

    //Special
    public static final int ADD = 0;
    public static final int DELETE = 1;
    public static final int SOLAR_TYPE = 1;
    public static final int LUNAR_TYPE = 0;
    public static final int INTERCALARY_TYPE = -1;

    //Daily
    public static final String D = "D";
    public static final String W = "W";
    public static final String M = "M";
    public static final String Y = "Y";
    public static final int RIGHT_DAY = 1;
    public static final int PREVIOUS_DAY = 2;
    public static final int NEXT_DAY = 3;
    public static final int OVERLOOK = 4;

    public static final int NO_VALUES = -1;
    public static final int INTERVAL = 1;
    public static final int OPTION = 2;
    public static final int HOLIDAY = 3;

    //Monthly
    public static final int DATE = 1;
    public static final int WEEK = 2;
    public static final int DAY = 3;
    public static final int FIRST_MONTH = 4;
    public static final int SECOND_MONTH = 5;
    public static final int CALENDAR = 6;


    //Spinner array

    //format date
    public static final String DATE_FORMAT_YYYY_MM_DD = "yyyy-MM-dd";

    //width of recyclerview change
    public static final int MONTHLY_RECYCLERVIEW_HEIGH_CHANGE = (int) (application.getResources().getDimension(R.dimen.list_monthly_item_height) / application.getResources().getDisplayMetrics().density);
    public static final int MONTHLY_RECYCLERVIEW_WIDTH_CHANGE = (int) (application.getResources().getDimension(R.dimen.list_monthly_item_width) / application.getResources().getDisplayMetrics().density);
    public static final int ANNUALLY_RECYCLERVIEW_HEIGH_CHANGE = (int) (application.getResources().getDimension(R.dimen.list_annually_item_height) / application.getResources().getDisplayMetrics().density);

    public static final String CHECKED = "CHECKED";
    public static final String UNCHECKED = "UNCHECKED";


    public static final int DATE_TYPE = 1;
    public static final int WEEK_TYPE = 2;

    public static final String TRUE = "true";
    public static final String FALSE = "false";


    public static final int MONTHLY = 1;
    public static final int ANNUALLY = 2;

    public static final int SHARER_TYPE = 1;
    public static final int PERSON_CONCERNED_TYPE = 2;
    public static final int ADMIN_TYPE = 3;

    public static final String START_DATE = "StartDate";
    public static final String END_DATE = "EndDate";
    public static final String INTERVAL_STRING = "Interval";
    public static final String HOLIDAY_CONDITION = "HolidayCondition";

    public static final String DDAY_COMPLETE_TYPE = "Completion D-Day";
    public static final String DDAY_MINUS_TYPE = "D-Day";
    public static final String DDAY_PLUS_TYPE = "D+Day";

    public static String CheckSpecificDday = "CheckSpecificDday";
    public static String CheckDPlusDay = "CheckDPlusDay";
    public static String CheckEveryDayDday = "CheckEveryDayDday";
    public static String CheckEveryDdayOfWeek = "CheckEveryDdayOfWeek";
    public static String CheckEveryMonthWeekDday = "CheckEveryMonthWeekDday";
    public static String CheckEveryMonthSpecificDday = "CheckEveryMonthSpecificDday";
    public static String CheckEveryYearDday = "CheckEveryYearDday";
    public static String CheckEveryYearWeek = "CheckEveryYearWeek";

    public static int day_of_month(int month) {
        if (month == 2)
            return 28;
        if (month == 1 || month == 3 || month == 5 || month == 7 || month == 8 || month == 10 || month == 12)
            return 31;
        else
            return 30;
    }

    public static int getLunar(int Lunar) {
        int pos = 0;
        if (Lunar == 1) pos = 0;
        else if (Lunar == 0) pos = 1;
        else pos = 2;
        return pos;
    }

    public static int return_Lunar(int pos) {
        int Lunar = 1;
        if (pos == 0) Lunar = 1;
        else if (pos == 1) Lunar = 0;
        else Lunar = -1;
        return Lunar;
    }

    public static String getAndroidVersion() {
        String release = Build.VERSION.RELEASE;
//        int sdkVersion = Build.VERSION.SDK_INT;
        return release;
    }

    public static String getfulldate(int month) {
        String date = "";
        if (month < 10) date = "0" + month;
        else date = "" + month;
        return date;
    }

    public static String date_finish = "2100-12-31";

    public static String formmat_date(String date) {
        String day = "";
        int year = Integer.parseInt(date.substring(0, 4));
        int month = Integer.parseInt(date.substring(4, 6));
        int ngay = Integer.parseInt(date.substring(6, 8));
        day = year + "-" + getfulldate(month) + "-" + getfulldate(ngay);
        return day;
    }

    public static boolean check_admin(int userNo, ArrayList<PersonData> arrayList) {
        if (arrayList != null) {
            for (int i = 0; i < arrayList.size(); i++) {
                if (arrayList.get(i).getUserNo() == userNo)
                    return true;
            }
        }
        return false;
    }

    public static int getType(int DepartNo, int UserNo) {
        if (DepartNo != 0) {
            if (UserNo == 0) // category
                return 1;
            else
                return 2; // user
        } else {
            return 0; // not in organization
        }
    }

    public static void longInfo(String TAG, String str) {
        if (str.length() > 4000) {
            Log.d(TAG, str.substring(0, 4000));
            longInfo(TAG, str.substring(4000));
        } else
            Log.d(TAG, str);
    }
}
