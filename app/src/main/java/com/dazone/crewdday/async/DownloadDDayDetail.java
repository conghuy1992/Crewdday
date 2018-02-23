package com.dazone.crewdday.async;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import com.dazone.crewdday.mInterface.ConnectionFailedCallback;
import com.dazone.crewdday.mInterface.DetailViewCallback;
import com.dazone.crewdday.mInterface.GetDDayDetail;
import com.dazone.crewdday.model.DDayDetailModel;
import com.dazone.crewdday.util.ConnectionUtils;

public class DownloadDDayDetail extends AsyncTask<Void, Void, Void> {
    long ddayId;
    Context context;
    DetailViewCallback detailViewCallback;
    DDayDetailModel dDayDetailModel;
    ProgressDialog mProgressDialog;

    private boolean mIsNetworkError = false;

    public DownloadDDayDetail(Context context, long ddayId, DetailViewCallback detailViewCallback) {
        this.context = context;
        this.ddayId = ddayId;
        this.detailViewCallback = detailViewCallback;
    }

    @Override
    protected void onPreExecute() {
        mProgressDialog = new ProgressDialog(context);
        mProgressDialog.setCanceledOnTouchOutside(false);
        mProgressDialog.setMessage("Loading...");
        mProgressDialog.show();
    }

    @Override
    protected Void doInBackground(Void... params) {
//        dDayDetailModel = ConnectionUtils.getInstance().getDDayDetailModel(new ConnectionFailedCallback() {
//            @Override
//            public void onConnectionFailed() {
//                mIsNetworkError = true;
//            }
//        }, ddayId, false);
//
//        detailViewCallback.intializeDdayDetail(dDayDetailModel);

        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {

        ConnectionUtils.getInstance().getDDayDetailModel_v2(ddayId, false, new GetDDayDetail() {
            @Override
            public void onSuccess(DDayDetailModel ddayDetailModel) {
                dDayDetailModel = ddayDetailModel;
                detailViewCallback.intializeDdayDetail(dDayDetailModel);
                detailViewCallback.updateUI();
                mProgressDialog.dismiss();
            }

            @Override
            public void onFail() {
                detailViewCallback.intializeDdayDetail(dDayDetailModel);
                mIsNetworkError = true;
                detailViewCallback.onError();
                mProgressDialog.dismiss();
            }
        });


//        if (mIsNetworkError) {
//            detailViewCallback.onError();
//        } else {
//            detailViewCallback.updateUI();
//        }
//
//        mProgressDialog.dismiss();
    }
}