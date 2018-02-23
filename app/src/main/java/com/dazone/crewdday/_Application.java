package com.dazone.crewdday;

import android.app.ProgressDialog;
import android.content.Context;
import android.support.multidex.MultiDexApplication;
import android.text.TextUtils;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.dazone.crewdday.other.PrefManager;
import com.dazone.crewdday.util.PreferenceUtilities;

import java.util.HashMap;

import me.leolin.shortcutbadger.ShortcutBadger;

public class _Application extends MultiDexApplication {
    ProgressDialog progressDialog;

    public void showDialog(Context context) {
        progressDialog = new ProgressDialog(context);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setMessage(context.getString(R.string.Loading));
        progressDialog.show();
    }

    public void dismissDialog() {
        if (progressDialog != null)
            if (progressDialog.isShowing())
                progressDialog.dismiss();
    }

    private static PreferenceUtilities mPrefs;

    private HashMap<Object, Object> _data = new HashMap<Object, Object>();

    private static _Application mInstance;
    private static PreferenceUtilities mPreferenceUtilities;
    private static final String TAG = "EmcorApplication";
    private RequestQueue mRequestQueue;

    public static String getProjectCode() {
//        return "_EAPP";
        return "DDay";
    }

    public _Application() {
        super();
    }


    public PrefManager getPrefManager() {
        return new PrefManager(this);
    }

    public void shortcut(int badgeCount) {
        ShortcutBadger.applyCount(this, badgeCount);
    }

    public void removeshortcut() {
        ShortcutBadger.removeCount(this);
    }

    public String[] arrayList_frequency() {
        return getResources().getStringArray(R.array.daily_interval_array);
    }

    public String[] arrayList_option() {
        return getResources().getStringArray(R.array.daily_option);
    }

    public String[] arrayList_holiday() {
        return getResources().getStringArray(R.array.daily_holiday);
    }

    public String[] month_date() {
        return getResources().getStringArray(R.array.monthly_date_array);
    }

    public String[] month_week() {
        return getResources().getStringArray(R.array.monthly_week_array);
    }

    public String[] month_day_of_week() {
        return getResources().getStringArray(R.array.monthly_day_array);
    }

    public String[] arr_month() {
        return getResources().getStringArray(R.array.annually_month_array);
    }

    public String[] arr_calendar() {
        return getResources().getStringArray(R.array.annually_calendar_type_array);
    }

    public String[] arrayList_annually() {
        return getResources().getStringArray(R.array.annually_interval_array);
    }

    public String[] arr_notify() {
        return getResources().getStringArray(R.array.array_notify);
    }

    public String[] arr_notify_2() {
        return getResources().getStringArray(R.array.array_notify_2);
    }

    public String[] sex_array() {
        return getResources().getStringArray(R.array.sex_array);
    }



    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
    }

    public int getDimenInPx(int id) {
        return (int) _Application.getInstance().getApplicationContext().getResources().getDimension(id);
    }


    public static synchronized _Application getInstance() {
        return mInstance;
    }

    public synchronized PreferenceUtilities getPreferenceUtilities() {
        if (mPreferenceUtilities == null) {
            mPreferenceUtilities = new PreferenceUtilities();
        }
        return mPreferenceUtilities;
    }


    public <T> void addToRequestQueue(Request<T> req, String tag) {
        req.setRetryPolicy(new DefaultRetryPolicy(Cons.REQUEST_TIMEOUT_MS, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        // set the default tag if tag is empty
        req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
        getRequestQueue().add(req);
    }

    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        }

        return mRequestQueue;
    }

}
