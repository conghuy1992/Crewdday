package com.dazone.crewdday.util;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;

import com.dazone.crewdday.R;
import com.dazone.crewdday.mInterface.FinishLoadingSpinnerCallback;

public class Helper {

    public static void getListViewSize(ListView myListView) {

        ListAdapter myListAdapter = myListView.getAdapter();
        if (myListAdapter == null) {
            //do nothing return null
            return;
        }
        //set listAdapter in loop for getting final size
        int totalHeight = 0;
        for (int size = 0; size < myListAdapter.getCount(); size++) {
            View listItem = myListAdapter.getView(size, null, myListView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }
        //setting listview item in adapter
        ViewGroup.LayoutParams params = myListView.getLayoutParams();
        params.height = totalHeight + (myListView.getDividerHeight() * (myListAdapter.getCount() - 1));
        myListView.setLayoutParams(params);

    }



    public static void loadSpinnerData(final Context context, final int arrayId, final Spinner spinner,
                                       final RelativeLayout container1,
                                       final RelativeLayout container2,
                                       final FinishLoadingSpinnerCallback callback) {
        if (spinner == null) {
            Log.e("Wrong spinner Id", "Wrong spinner Id");
        } else {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    final String[] array = context.getResources().getStringArray(arrayId);
                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                        @Override
                        public void run() {
                            ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, R.layout.spinner_text_choose, R.id.txt_option, array);
                            spinner.setAdapter(adapter);
                            container1.setVisibility(View.GONE);
                            container2.setVisibility(View.VISIBLE);
                            container2.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    spinner.performClick();
                                }
                            });
                            if (callback != null) {
                                callback.finishLoadSpinner();
                            }
                        }
                    });


                }
            }).start();
        }
    }


    public static void loadSpinnerDataWithArray(final Context context, final String[] array, final Spinner spinner, final RelativeLayout container1, final RelativeLayout container2) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, R.layout.spinner_text_choose_16sp, R.id.txt_option, array);
                        spinner.setAdapter(adapter);
                        container1.setVisibility(View.GONE);
                        container2.setVisibility(View.VISIBLE);
                        container2.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                spinner.performClick();
                            }
                        });
                    }
                });


            }
        }).start();
    }


    public static int getScreenWidth(Context context) {
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        int screenWidth = metrics.widthPixels;
        return screenWidth;
    }
}

