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
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dazone.crewdday.Cons;
import com.dazone.crewdday.R;
import com.dazone.crewdday._Application;
import com.dazone.crewdday.activity.HomeActivity;
import com.dazone.crewdday.adapter.HomeDDayBoardAdapter;
import com.dazone.crewdday.async.DownloadDDayList;
import com.dazone.crewdday.mInterface.HomeFragmentCallback;
import com.dazone.crewdday.mInterface.ManageTabFragments;
import com.dazone.crewdday.mInterface.OnLoadMoreListener;
import com.dazone.crewdday.mInterface.TabFragmentCallback;
import com.dazone.crewdday.mInterface.UpdateFragmentTag;
import com.dazone.crewdday.model.DDayDetailModel;
import com.dazone.crewdday.model.DDayModel;
import com.dazone.crewdday.other.Utils;
import com.dazone.crewdday.util.PreferenceUtilities;

import java.util.ArrayList;

public class FragmentHomeAll extends Fragment implements HomeFragmentCallback, ManageTabFragments, UpdateFragmentTag {
    private RecyclerView recyclerView;
    private ArrayList<DDayModel> dDayModelsList;
    private ProgressBar progressBar;
    private String TAG = "FragmentHomeAll";
    private TextView txtEmpty, txt_network_checking;
    private Button btn_try_again;

    private SwipeRefreshLayout mSwipeRefreshLayout;
    public HomeDDayBoardAdapter mDDayListAdapter;
    private boolean loadedDataFirstTime = false;
    private TabFragmentCallback tabFragmentCallback;

    public static FragmentHomeAll instance = null;
    private RelativeLayout lnNoData;
    private PreferenceUtilities pref;

    public void update_new_data(DDayDetailModel obj) {
        mDDayListAdapter.update_new_data(obj);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView");
        View view = inflater.inflate(R.layout.fragment_home_tabs, container, false);
        instance = this;
        pref = _Application.getInstance().getPreferenceUtilities();
        tabFragmentCallback = (TabFragmentCallback) getActivity();
        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.srl);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                HomeActivity.type_loading = 0;
                refreshList();
            }
        });
        lnNoData = (RelativeLayout) view.findViewById(R.id.lnNoData);
        txtEmpty = (TextView) view.findViewById(R.id.txt_empty);
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
        mDDayListAdapter = new HomeDDayBoardAdapter(getActivity(), dDayModelsList, recyclerView, 1);
        recyclerView.setAdapter(mDDayListAdapter);

        mDDayListAdapter.setOnClickListenerForTryAgain(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showMoreProgressBar(true);
            }
        });

        DownloadDDayList downloadDataAsync = new DownloadDDayList(getActivity(), Cons.DDAY_ALL_MODE, "",
                FragmentHomeAll.this, false, mSwipeRefreshLayout, HomeActivity.filterTypeForList, HomeActivity.type_loading);
        downloadDataAsync.execute();
        setOnLoadMore();

        return view;
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
        Log.d(TAG, "addItemToListFirstTime");
        if (!loadedDataFirstTime) {
            if (list.size() != 0) {
                dDayModelsList.addAll(list);
                loadedDataFirstTime = true;
                mDDayListAdapter.setLoaded();
            }
        }
    }

    public void startLoading() {
        if (txtEmpty != null) {
            txtEmpty.setVisibility(View.GONE);
        }

        if (progressBar != null) {
            progressBar.setVisibility(View.VISIBLE);
        }
    }

    public void hideIntroView() {
        if (lnNoData != null)
            lnNoData.setVisibility(View.GONE);
    }

    boolean isHaveData = false;

    public boolean getIsHaveData() {
        return isHaveData;
    }

    @Override
    public void finishAddItem() {
        Log.d(TAG, "finishAddItem");
        if (HomeActivity.currentFragmentPos == 0) {
            HomeActivity.instance.hideMenuSort();
        }
        mDDayListAdapter.notifyDataSetChanged();
        if (progressBar.getVisibility() == View.VISIBLE) {
            progressBar.setVisibility(View.GONE);
        }
        if (dDayModelsList.size() == 0) {
            isHaveData = false;
            if (pref.getFIRST_LOGIN()) {
                boolean b = false;
                if (FragmentHomePlus.instance != null)
                    b = FragmentHomePlus.instance.getIsHaveData();
                if (!b)
                    lnNoData.setVisibility(View.VISIBLE);
            } else {
                hideIntroView();
            }

            try {
                tabFragmentCallback.checkListNull(Cons.DDAY_ALL, true);
            } catch (Exception e) {
                e.printStackTrace();
            }
            HomeActivity.show_day = false;
            txtEmpty.setVisibility(View.VISIBLE);
        } else {
            pref.setFIRST_LOGIN(false);
            isHaveData = true;
            if (FragmentHomePlus.instance != null) FragmentHomePlus.instance.hideIntroView();
            HomeActivity.show_day = true;
            tabFragmentCallback.checkListNull(Cons.DDAY_ALL, false);
            txtEmpty.setVisibility(View.GONE);
            if (HomeActivity.currentFragmentPos == 0) {
                HomeActivity.instance.check_item_menu_show();
            }
        }
        if (dDayModelsList.size() < 30 && dDayModelsList.size() > 1) {
            String currentIndexString = dDayModelsList.get(dDayModelsList.size() - 2).getCurrentIdexString();
            if (!currentIndexString.equals(Cons.END)) {
                DownloadDDayList downloadDataAsync = new DownloadDDayList(getActivity(), Cons.DDAY_ALL_MODE, currentIndexString, FragmentHomeAll.this, true, mSwipeRefreshLayout, HomeActivity.filterTypeForList, HomeActivity.type_loading);
                downloadDataAsync.execute();
            }
        }
        if (HomeActivity.isFirstRun) {
            HomeActivity.isFirstRun = false;
            int length = dDayModelsList.size();
            int startPosition = 0;
            for (int i = 0; i < length; i++) {
                if (dDayModelsList.get(i).getRemainingDays() <= 0) {
                    startPosition = i;
                    break;
                }
            }
            ((LinearLayoutManager) recyclerView.getLayoutManager()).scrollToPositionWithOffset(startPosition - 1, dDayModelsList.size());
        }
    }

    @Override
    public void addMoreItems(ArrayList<DDayModel> list) {
        Log.d(TAG, "addMoreItems");
        if (dDayModelsList.size() != 0 /*&& dDayModelsList.size() > 20*/) {
            dDayModelsList.remove(dDayModelsList.size() - 1);
            mDDayListAdapter.notifyItemRemoved(dDayModelsList.size());
            dDayModelsList.addAll(list);
            mDDayListAdapter.setLoaded();
        }
    }

    @Override
    public void loadFragment() {
        DownloadDDayList downloadDataAsync = new DownloadDDayList(getActivity(), Cons.DDAY_ALL_MODE, "", this, false, mSwipeRefreshLayout, HomeActivity.filterTypeForList, HomeActivity.type_loading);
        downloadDataAsync.execute();
    }

    public void setOnLoadMore() {
        mDDayListAdapter.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                if (dDayModelsList.size() != 0) {
                    try {
                        String currentIndexString = dDayModelsList.get(dDayModelsList.size() - 2).getCurrentIdexString();
                        if (!currentIndexString.equals(Cons.END)) {
                            dDayModelsList.add(null);
                            mDDayListAdapter.notifyItemInserted(dDayModelsList.size() - 1);
                            DownloadDDayList downloadDataAsync = new DownloadDDayList(getActivity(), Cons.DDAY_ALL_MODE, currentIndexString, FragmentHomeAll.this, true, mSwipeRefreshLayout, HomeActivity.filterTypeForList, HomeActivity.type_loading);
                            downloadDataAsync.execute();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });
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
        tabFragmentCallback.checkListNull(Cons.DDAY_ALL, true);
    }

    public void refreshList() {
        dDayModelsList.clear();
        mDDayListAdapter.notifyDataSetChanged();
        loadedDataFirstTime = false;

        txt_network_checking.setVisibility(View.GONE);
        btn_try_again.setVisibility(View.GONE);

        DownloadDDayList downloadDataAsync = new DownloadDDayList(getActivity(), Cons.DDAY_ALL_MODE, "", FragmentHomeAll.this, false, mSwipeRefreshLayout, HomeActivity.filterTypeForList, HomeActivity.type_loading);
        downloadDataAsync.execute();
    }

    public void updateList(int position) {
        loadedDataFirstTime = false;
        txt_network_checking.setVisibility(View.GONE);
        btn_try_again.setVisibility(View.GONE);
        mDDayListAdapter.notifyItemRemoved(position);
        progressBar.setVisibility(View.GONE);
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
        progressBar.setVisibility(View.GONE);
        txtEmpty.setVisibility(View.VISIBLE);
    }

    public void showMoreProgressBar(boolean removeLastItem) {
        if (removeLastItem) {
            dDayModelsList.remove(dDayModelsList.size() - 1);
            mDDayListAdapter.notifyItemRemoved(dDayModelsList.size());
        }

        String currentIndexString;

        if (removeLastItem) {
            currentIndexString = dDayModelsList.get(dDayModelsList.size() - 2).getCurrentIdexString();
        } else {
            currentIndexString = dDayModelsList.get(dDayModelsList.size() - 1).getCurrentIdexString();
        }

        if (!currentIndexString.equals(Cons.END)) {
            dDayModelsList.add(null);
            mDDayListAdapter.notifyItemInserted(dDayModelsList.size() - 1);
            DownloadDDayList downloadDataAsync = new DownloadDDayList(getActivity(), Cons.DDAY_ALL_MODE, currentIndexString, FragmentHomeAll.this, true, mSwipeRefreshLayout, HomeActivity.filterTypeForList, HomeActivity.type_loading);
            downloadDataAsync.execute();
        }
    }
}