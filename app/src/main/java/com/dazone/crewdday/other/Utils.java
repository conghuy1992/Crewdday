package com.dazone.crewdday.other;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.CursorLoader;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.inputmethod.InputMethodManager;

import com.dazone.crewdday._Application;

import java.util.ArrayList;
import java.util.List;

public class Utils {
    /**
     * Check if network available or not
     *
     * @return true-network available; false-network not available
     */
    public static boolean isNetworkAvailable() {
        NetworkInfo networkInfo = getNetworkInfo();
        return (networkInfo != null && networkInfo.isConnectedOrConnecting());
    }

    private static NetworkInfo getNetworkInfo() {
        ConnectivityManager connectivityManager = (ConnectivityManager) _Application.getInstance().getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        return connectivityManager.getActiveNetworkInfo();

    }

    public static boolean isWifiEnable() {
        NetworkInfo networkInfo = getNetworkInfo();
        return (networkInfo != null && networkInfo.getType() == ConnectivityManager.TYPE_WIFI);
    }
    /**
     *Function check wifi enable
     *
     * @return
     *//*
    public static boolean isWifiEnable()
    {
        NetworkInfo networkInfo =getNetworkInfo();
        return (networkInfo != null&&networkInfo.getType() == ConnectivityManager.TYPE_WIFI);
    }
    */

    /**
     * Function print logs as Debug
     * <p/>
     * //@param logs
     *//*
    public static void printLogs(String logs) {
            if(logs==null)
                return;
            int maxLogSize = 4000;
            if(logs.length()>maxLogSize) {
                for (int i = 0; i <= logs.length() / maxLogSize; i++) {
                    int start = i * maxLogSize;
                    int end = (i + 1) * maxLogSize;
                    end = end > logs.length() ? logs.length() : end;
                    Log.d(Statics.TAG, logs.substring(start, end));
                }
            }
            else
            {
                Log.d(Statics.TAG, logs);
            }
    }
*/
    public static String getString(int stringID) {
        return _Application.getInstance().getApplicationContext().getResources().getString(stringID);
    }

    public static String getUniqueDeviceId(Context context) {
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
       /*
        * getDeviceId() function Returns the unique device ID.
        * for example,the IMEI for GSM and the MEID or ESN for CDMA phones.
        */
        String deviceId = telephonyManager.getDeviceId();


       /*
        * getSubscriberId() function Returns the unique subscriber ID,
        * for example, the IMSI for a GSM phone.
        */
        if (TextUtils.isEmpty(deviceId)) {
            deviceId = telephonyManager.getSubscriberId();
        }

        /*
        * Settings.Secure.ANDROID_ID returns the unique DeviceID
        * Works for Android 2.2 and above
        */
        if (TextUtils.isEmpty(deviceId)) {
            deviceId = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
        }

        /*
        * returns the MacAddress
        */
        if (TextUtils.isEmpty(deviceId)) {
            WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
            WifiInfo wInfo = wifiManager.getConnectionInfo();
            deviceId = wInfo.getMacAddress();
        }

        return deviceId;

    }

    public static boolean checkStringValue(String... params) {
        for (String param : params) {
            if (TextUtils.isEmpty(param.trim())) {
                return false;
            }
            if (param.contains("\n") && TextUtils.isEmpty(param.replace("\n", ""))) {
                return false;
            }
        }
        return true;
    }

    public static int getDimenInPx(int id) {
        return (int) _Application.getInstance().getApplicationContext().getResources().getDimension(id);
    }

    public static void addFragmentToActivity(FragmentManager fragmentManager, Fragment fragment, int frameLayout, boolean isSaveStack) {
        addFragmentToActivity(fragmentManager, fragment, frameLayout, isSaveStack, null);
    }

    public static void addFragmentToActivity(FragmentManager fragmentManager, Fragment fragment, int frameLayout, boolean isSaveStack, String tag) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        if (TextUtils.isEmpty(tag)) {
            transaction.add(frameLayout, fragment);
        } else {
            transaction.add(frameLayout, fragment, tag);
        }

        if (isSaveStack) {
            transaction.addToBackStack(null);
        }
        transaction.commit();
    }

    public static void replaceFragment(FragmentManager fragmentManager, Fragment fragment, int frameLayout, boolean isSaveStack) {
        replaceFragment(fragmentManager, fragment, frameLayout, isSaveStack, null);
    }

    public static void replaceFragment(FragmentManager fragmentManager, Fragment fragment, int frameLayout, boolean isSaveStack, String tag) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        if (TextUtils.isEmpty(tag)) {
            transaction.replace(frameLayout, fragment);
        } else {
            transaction.replace(frameLayout, fragment, tag);
        }

        if (isSaveStack) {
            transaction.addToBackStack(null);
        }

        transaction.commit();
    }


    public static void hideKeyboard(Activity activity) {
        try {
            InputMethodManager inputManager = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            inputManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
    }

    public static String getRealPathFromURI(Context context, Uri contentUri) {
        String pathFile = "";
        try {
            String[] proj = {MediaStore.Images.Media.DATA};
            CursorLoader loader = new CursorLoader(context, contentUri, proj, null, null, null);
            Cursor cursor = loader.loadInBackground();
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            pathFile = cursor.getString(column_index);
            cursor.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return pathFile;
    }

    public static int getScreenHeight(Activity activity) {
        DisplayMetrics dm = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
        int height = dm.heightPixels;
        return height;
    }

    public static int getScreenWidth(Activity activity) {
        DisplayMetrics dm = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
        return dm.widthPixels;
    }

    public static int getStatusBarHeight(Activity activity) {
        int result = 0;
        int resourceId = activity.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = activity.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    public static int getActionbarHeight(Activity activity) {
        int actionBarHeight = 0;
        TypedValue tv = new TypedValue();
        if (activity.getTheme().resolveAttribute(android.R.attr.actionBarSize, tv, true)) {
            actionBarHeight = TypedValue.complexToDimensionPixelSize(tv.data, activity.getResources().getDisplayMetrics());
        }
        return actionBarHeight;
    }

    public static int getContentHeight(int offset, Activity activity) {
        return getScreenHeight(activity) - getActionbarHeight(activity) - getStatusBarHeight(activity) - offset;
    }

}
