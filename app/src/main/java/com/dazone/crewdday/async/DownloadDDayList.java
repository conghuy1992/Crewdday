package com.dazone.crewdday.async;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.widget.Toast;

import com.dazone.crewdday._Application;
import com.dazone.crewdday.activity.HomeActivity;
import com.dazone.crewdday.mInterface.ConnectionFailedCallback;
import com.dazone.crewdday.mInterface.HomeFragmentCallback;
import com.dazone.crewdday.mInterface.OnGetDDayModelList;
import com.dazone.crewdday.model.DDayModel;
import com.dazone.crewdday.util.ConnectionUtils;
import com.dazone.crewdday.util.PreferenceUtilities;

import java.util.ArrayList;

public class DownloadDDayList extends AsyncTask<Void, Void, Void> {
    int mode;
    String currentIndexString;
    Context context;
    HomeFragmentCallback homeFragmentCallback;
    ArrayList<DDayModel> list;
    boolean loadMore;
    PreferenceUtilities prefUtils;
    SwipeRefreshLayout mSwipeRefreshLayout;
    int filterTypeForList;
    int type;
    String TAG = "DownloadDDayList";
    private boolean mIsNetworkError = false;

    public DownloadDDayList(Context context, int mode, String currentIndexString, HomeFragmentCallback homeFragmentCallback, boolean loadMore, SwipeRefreshLayout mSwipeRefreshLayout, int filterTypeForList, int type) {
        this.context = context;
        this.mode = mode;
        this.loadMore = loadMore;
        this.currentIndexString = currentIndexString;
        this.homeFragmentCallback = homeFragmentCallback;
        this.prefUtils = _Application.getInstance().getPreferenceUtilities();
        this.mSwipeRefreshLayout = mSwipeRefreshLayout;
        this.filterTypeForList = filterTypeForList;
        this.type = type;
    }

    @Override
    protected void onPreExecute() {

        if (isCancelled()) {
            return;
        }

        if (type == 1) {
            HomeActivity.instance._show_pr();
        }
    }

    @Override
    protected Void doInBackground(Void... params) {
        if (prefUtils.getGroupId().isEmpty()) {
            DownloadDDayList.this.cancel(true);

        }

//        list = ConnectionUtils.getInstance().getDDayModelList(new ConnectionFailedCallback() {
//            @Override
//            public void onConnectionFailed() {
//                mIsNetworkError = true;
//            }
//        }, mode, currentIndexString, filterTypeForList);
//        if (list == null)
//            list = new ArrayList<>();


        // edit

        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        Log.d(TAG, "onPostExecute");
        ConnectionUtils.getInstance().getDDayModelList_v2(mode, currentIndexString, filterTypeForList, new OnGetDDayModelList() {
            @Override
            public void onSuccess(ArrayList<DDayModel> lst) {
                HomeActivity.type_loading = 0;
                list = lst;
                if (!mIsNetworkError) {
                    homeFragmentCallback.addItemToListFirstTime(list);
                }
                if (mIsNetworkError) {
                    homeFragmentCallback.showNetworkErrorMessage();
                    return;
                }
                if (loadMore) {
                    homeFragmentCallback.addMoreItems(list);
                }
                homeFragmentCallback.finishAddItem();
                if (type == 1) {
                    HomeActivity.instance._hide_pr();
                }
                if (mSwipeRefreshLayout != null) {
                    if (mSwipeRefreshLayout.isRefreshing()) {
                        mSwipeRefreshLayout.setRefreshing(false);
                    }
                }
                HomeActivity.type_loading = 0;
                HomeActivity.instance.updateListDDayPlus();
            }

            @Override
            public void onFail() {
                HomeActivity.type_loading = 0;
                if (type == 1) {
                    HomeActivity.instance._hide_pr();
                }
                homeFragmentCallback.onFail();
            }
        });

    }
}