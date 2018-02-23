package com.dazone.crewdday.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.dazone.crewdday.Cons;
import com.dazone.crewdday.R;
import com.dazone.crewdday._Application;
import com.dazone.crewdday.activity.HomeActivity;
import com.dazone.crewdday.adapter.HomeDDayBoardAdapter;
import com.dazone.crewdday.mInterface.HomeFragmentCallback;
import com.dazone.crewdday.mInterface.ManageTabFragments;
import com.dazone.crewdday.mInterface.OnLoadMoreListener;
import com.dazone.crewdday.mInterface.TabFragmentCallback;
import com.dazone.crewdday.mInterface.UpdateFragmentTag;
import com.dazone.crewdday.model.DDayModel;
import com.dazone.crewdday.async.DownloadDDayList;
import com.dazone.crewdday.other.Utils;
import com.dazone.crewdday.util.PreferenceUtilities;

import java.util.ArrayList;

public class FragmentHomeMinus extends Fragment implements HomeFragmentCallback, ManageTabFragments, UpdateFragmentTag {
    String TAG = "FragmentHomeMinus";
    RecyclerView recyclerView;
    ArrayList<DDayModel> dDayModelsList;
    ProgressBar progressBar;

    TextView tvEmpty, txt_network_checking;
    Button btn_try_again;

    SwipeRefreshLayout mSwipeRefreshLayout;
    HomeDDayBoardAdapter mDDayListAdapter;
    private static boolean loadedDataFirstTime = false;
    PreferenceUtilities mPref;
    TabFragmentCallback tabFragmentCallback;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home_tabs, container, false);
        mPref = _Application.getInstance().getPreferenceUtilities();
        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.srl);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshList();
            }
        });

        tvEmpty = (TextView) view.findViewById(R.id.txt_empty);
        txt_network_checking = (TextView) view.findViewById(R.id.txt_network_checking);
        btn_try_again = (Button) view.findViewById(R.id.btn_try_again);

        btn_try_again.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                refreshList();
            }
        });

        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_dday_board);
        dDayModelsList = new ArrayList<>();
        progressBar = (ProgressBar) view.findViewById(R.id.progress_bar);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mDDayListAdapter = new HomeDDayBoardAdapter(getActivity(), dDayModelsList, recyclerView,1);
        recyclerView.setAdapter(mDDayListAdapter);

        mDDayListAdapter.setOnClickListenerForTryAgain(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showMoreProgressBar();
            }
        });

        DownloadDDayList downloadDataAsync = new DownloadDDayList(getActivity(),
                Cons.DDAY_MINUS_MODE, "", FragmentHomeMinus.this, false, mSwipeRefreshLayout,HomeActivity.filterTypeForList,HomeActivity.type_loading);
        downloadDataAsync.execute();
        setOnLoadMore();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        loadedDataFirstTime = false;
    }

    @Override
    public void onStop() {
        super.onStop();
    }


    @Override
    public void addItemToListFirstTime(ArrayList<DDayModel> list) {
        if (!loadedDataFirstTime) {
            if (list.size() != 0) {
                dDayModelsList.addAll(list);
                loadedDataFirstTime = true;
            }
        }

    }

    @Override
    public void finishAddItem() {
        mDDayListAdapter.notifyDataSetChanged();
        if (progressBar.getVisibility() == View.VISIBLE) {
            progressBar.setVisibility(View.GONE);
        }
        tabFragmentCallback = (TabFragmentCallback) getActivity();
        if (dDayModelsList.size() == 0) {
            tabFragmentCallback.checkListNull(Cons.DDAY_MINUS, true);
            tvEmpty.setVisibility(View.VISIBLE);
        } else {
            tabFragmentCallback.checkListNull(Cons.DDAY_MINUS, false);
            tvEmpty.setVisibility(View.GONE);
        }
        if (dDayModelsList.size() < 20 && dDayModelsList.size() > 1) {
            String currentIndexString = dDayModelsList.get(dDayModelsList.size() - 2).getCurrentIdexString();
            if (!currentIndexString.equals(Cons.END)) {
                DownloadDDayList downloadDataAsync = new DownloadDDayList(getActivity(), Cons.DDAY_MINUS_MODE,
                        currentIndexString, FragmentHomeMinus.this, true, mSwipeRefreshLayout,HomeActivity.filterTypeForList,HomeActivity.type_loading);
                downloadDataAsync.execute();
            }
        }
    }

    @Override
    public void addMoreItems(ArrayList<DDayModel> list) {
        if (dDayModelsList.size() != 0 && dDayModelsList.size() > 20) {
            dDayModelsList.remove(dDayModelsList.size() - 1);
            mDDayListAdapter.notifyItemRemoved(dDayModelsList.size());
            dDayModelsList.addAll(list);
            mDDayListAdapter.setLoaded();
        }
    }


    @Override
    public void loadFragment() {
        DownloadDDayList downloadDataAsync = new DownloadDDayList(getActivity(),
                Cons.DDAY_MINUS_MODE, "", this, false, mSwipeRefreshLayout, HomeActivity.filterTypeForList,HomeActivity.type_loading);
        downloadDataAsync.execute();
    }


    private void setOnLoadMore() {
        mDDayListAdapter.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {

                if (dDayModelsList.size() != 0) {
                    String currentIndexString = null;
                    currentIndexString = dDayModelsList.get(dDayModelsList.size() - 2).getCurrentIdexString();
                    if (!currentIndexString.equals(Cons.END)) {
                        //add null , so the adapter will check view_type and show progress bar at bottom
                        dDayModelsList.add(null);
                        mDDayListAdapter.notifyItemInserted(dDayModelsList.size() - 1);

                        //   remove progress item
                        //add items one by one

                        DownloadDDayList downloadDataAsync = new DownloadDDayList(getActivity(), Cons.DDAY_MINUS_MODE,
                                currentIndexString, FragmentHomeMinus.this, true, mSwipeRefreshLayout,HomeActivity.filterTypeForList,HomeActivity.type_loading);
                        downloadDataAsync.execute();
                    }
                }
            }
        });

    }


    public void refreshList() {
        dDayModelsList.clear();
        loadedDataFirstTime = false;

        txt_network_checking.setVisibility(View.GONE);
        btn_try_again.setVisibility(View.GONE);

        DownloadDDayList downloadDataAsync = new DownloadDDayList(getActivity(), Cons.DDAY_MINUS_MODE, "", this, false, mSwipeRefreshLayout,HomeActivity.filterTypeForList,HomeActivity.type_loading);
        downloadDataAsync.execute();
    }

    @Override
    public void updateTag(int oldTag, int newTag) {
        for (int i = 0; i < dDayModelsList.size(); i++) {
            if (dDayModelsList.get(i).getTagId() == oldTag) {
                dDayModelsList.get(i).setTagId(newTag);
            }
        }
        mDDayListAdapter.notifyDataSetChanged();
    }

    @Override
    public void refreshTagList() {
        dDayModelsList.clear();
        loadedDataFirstTime = false;
        tabFragmentCallback.checkListNull(Cons.DDAY_MINUS, true);
    }

    @Override
    public void showNetworkErrorMessage() {
        if (Utils.isNetworkAvailable()) {
            txt_network_checking.setText(R.string.message_network_unstable);
        } else {
            txt_network_checking.setText(R.string.message_network_checking);
        }

        if (dDayModelsList.size() == 0) {
            txt_network_checking.setVisibility(View.VISIBLE);
            btn_try_again.setVisibility(View.VISIBLE);

            progressBar.setVisibility(View.GONE);

            if (mSwipeRefreshLayout != null) {
                if (mSwipeRefreshLayout.isRefreshing()) {
                    mSwipeRefreshLayout.setRefreshing(false);
                }
            }
        } else {
            dDayModelsList.remove(dDayModelsList.size() - 1);
            mDDayListAdapter.notifyItemRemoved(dDayModelsList.size());

            DDayModel tryAgain = new DDayModel();
            tryAgain.setType(Cons.TRY_AGAIN);

            dDayModelsList.add(tryAgain);
            mDDayListAdapter.notifyItemInserted(dDayModelsList.size() - 1);

            recyclerView.smoothScrollToPosition(dDayModelsList.size() - 1);
        }
    }

    @Override
    public void onFail() {

    }

    public void showMoreProgressBar() {
        dDayModelsList.remove(dDayModelsList.size() - 1);
        mDDayListAdapter.notifyItemRemoved(dDayModelsList.size());

        String currentIndexString = dDayModelsList.get(dDayModelsList.size() - 2).getCurrentIdexString();

        if (!currentIndexString.equals(Cons.END)) {
            dDayModelsList.add(null);
            mDDayListAdapter.notifyItemInserted(dDayModelsList.size() - 1);
            DownloadDDayList downloadDataAsync = new DownloadDDayList(getActivity(), Cons.DDAY_ALL_MODE, currentIndexString, FragmentHomeMinus.this, true, mSwipeRefreshLayout, HomeActivity.filterTypeForList, HomeActivity.type_loading);
            downloadDataAsync.execute();
        }
    }
}