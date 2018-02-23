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
import com.dazone.crewdday.mInterface.HomeFragmentCallback;
import com.dazone.crewdday.mInterface.ManageTabFragments;
import com.dazone.crewdday.mInterface.OnLoadMoreListener;
import com.dazone.crewdday.mInterface.TabFragmentCallback;
import com.dazone.crewdday.mInterface.UpdateFragmentTag;
import com.dazone.crewdday.model.DDayDetailModel;
import com.dazone.crewdday.model.DDayModel;
import com.dazone.crewdday.async.DownloadDDayList;
import com.dazone.crewdday.other.Utils;
import com.dazone.crewdday.util.PreferenceUtilities;

import java.util.ArrayList;

public class FragmentHomePlus extends Fragment implements HomeFragmentCallback, ManageTabFragments, UpdateFragmentTag {
    String TAG = "FragmentHomePlus";
    RecyclerView recyclerView;
    ArrayList<DDayModel> dDayModelsList;
    ProgressBar progressBar;
    private RelativeLayout lnNoData;
    TextView tvEmpty, txt_network_checking;
    Button btn_try_again;
    private PreferenceUtilities pref;

    SwipeRefreshLayout mSwipeRefreshLayout;
    TabFragmentCallback tabFragmentCallback;
    public HomeDDayBoardAdapter mDDayListAdapter;
    private boolean loadedDataFirstTime = false;
    PreferenceUtilities mPref;
    public static FragmentHomePlus instance = null;

    public void startLoad() {
        if (tvEmpty != null)
            tvEmpty.setVisibility(View.GONE);
        if (progressBar != null)
            progressBar.setVisibility(View.VISIBLE);
    }
    public void update_new_data(DDayDetailModel obj) {
        mDDayListAdapter.update_new_data(obj);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home_tabs, container, false);
        instance = this;
        pref = _Application.getInstance().getPreferenceUtilities();
        lnNoData = (RelativeLayout) view.findViewById(R.id.lnNoData);
        tabFragmentCallback = (TabFragmentCallback) getActivity();
        mPref = _Application.getInstance().getPreferenceUtilities();
        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.srl);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // update data here
                HomeActivity.type_loading = 0;
//                HomeActivity.instance.update_mn_sort();
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

        dDayModelsList = new ArrayList<DDayModel>();

        progressBar = (ProgressBar) view.findViewById(R.id.progress_bar);

        recyclerView.setHasFixedSize(true);
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        mDDayListAdapter = new HomeDDayBoardAdapter(getActivity(), dDayModelsList, recyclerView, 2);

        mDDayListAdapter.setOnClickListenerForTryAgain(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showMoreProgressBar();
            }
        });

        recyclerView.setAdapter(mDDayListAdapter);
        //Set Load more listener

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy != 0) {
                    if (dy < 0) {
                        if (!HomeActivity.fab.isShown()) {
//                            HomeActivity.fab.show();
                            HomeActivity.instance._show_fab();
                            flag = false;
                        }
                    } else {
                        if (linearLayoutManager.findLastVisibleItemPosition() == dDayModelsList.size() - 1) {
                            if (HomeActivity.fab.isShown()) {
                                HomeActivity.fab.hide();
                                flag = true;
                            }
                        } else {
                            if (!HomeActivity.fab.isShown()) {
//                                HomeActivity.fab.show();
                                HomeActivity.instance._show_fab();
                                flag = false;
                            }
                        }
                    }
                }
            }
        });
        setOnLoadMore();

        return view;
    }

    public static boolean flag = false;

    @Override
    public void onResume() {
        super.onResume();
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

    int first = 0;


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
//        if (HomeActivity.currentFragmentPos == 1)
//            HomeActivity.instance.hideMenuFilter();
        mDDayListAdapter.notifyDataSetChanged();
        if (progressBar.getVisibility() == View.VISIBLE) {
            progressBar.setVisibility(View.GONE);
        }

        if (dDayModelsList.size() == 0) {
            isHaveData = false;
            if (pref.getFIRST_LOGIN()) {
                boolean b = false;
                if (FragmentHomeAll.instance != null) b = FragmentHomeAll.instance.getIsHaveData();
                if (!b)
                    lnNoData.setVisibility(View.VISIBLE);
            } else {
                hideIntroView();
            }

            HomeActivity.show_plus = false;
            tabFragmentCallback.checkListNull(Cons.DDAY_PLUS, true);
            tvEmpty.setVisibility(View.VISIBLE);
//            if (HomeActivity.currentFragmentPos == 1)
//                HomeActivity.instance.check_item_menu_hide();
        } else {
            pref.setFIRST_LOGIN(false);
            isHaveData = true;
            if (FragmentHomeAll.instance != null) FragmentHomeAll.instance.hideIntroView();
            HomeActivity.show_plus = true;
            tabFragmentCallback.checkListNull(Cons.DDAY_PLUS, false);
            tvEmpty.setVisibility(View.GONE);
            if (HomeActivity.currentFragmentPos == 1)
                HomeActivity.instance.check_item_menu_show();
        }
        if (dDayModelsList.size() < 30 && dDayModelsList.size() > 1) {
            String currentIndexString = dDayModelsList.get(dDayModelsList.size() - 2).getCurrentIdexString();
            if (!currentIndexString.equals(Cons.END)) {
                DownloadDDayList downloadDataAsync = new DownloadDDayList(getActivity(), Cons.DDAY_PLUS_MODE,
                        currentIndexString, FragmentHomePlus.this, true, mSwipeRefreshLayout, HomeActivity.filterTypeForList, HomeActivity.type_loading);
                downloadDataAsync.execute();
            }
        }
        if (first == 0) {
            if (!HomeActivity.instance.returnSort()) {
                sort_dsc();
            }
        }
        first++;
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
        DownloadDDayList downloadDataAsync = new DownloadDDayList(getActivity(), Cons.DDAY_PLUS_MODE, "", this, false, mSwipeRefreshLayout, HomeActivity.filterTypeForList, HomeActivity.type_loading);
        downloadDataAsync.execute();
    }

    private void setOnLoadMore() {
        mDDayListAdapter.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                if (dDayModelsList.size() != 0) {
                    try {
                        String currentIndexString = null;
                        currentIndexString = dDayModelsList.get(dDayModelsList.size() - 2).getCurrentIdexString();
                        if (!currentIndexString.equals(Cons.END)) {
                            //add null , so the adapter will check view_type and show progress bar at bottom
                            dDayModelsList.add(null);
                            mDDayListAdapter.notifyItemInserted(dDayModelsList.size() - 1);


                            DownloadDDayList downloadDataAsync = new DownloadDDayList(getActivity(), Cons.DDAY_PLUS_MODE,
                                    currentIndexString, FragmentHomePlus.this, true, mSwipeRefreshLayout, HomeActivity.filterTypeForList, HomeActivity.type_loading);
                            downloadDataAsync.execute();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });

    }

    public void updateList(int position) {
        loadedDataFirstTime = false;
        txt_network_checking.setVisibility(View.GONE);
        btn_try_again.setVisibility(View.GONE);
        mDDayListAdapter.notifyItemRemoved(position);
        progressBar.setVisibility(View.GONE);
    }

    public void refreshList() {
        dDayModelsList.clear();
        mDDayListAdapter.notifyDataSetChanged();
        loadedDataFirstTime = false;

        txt_network_checking.setVisibility(View.GONE);
        btn_try_again.setVisibility(View.GONE);

        DownloadDDayList downloadDataAsync = new DownloadDDayList(getActivity(), Cons.DDAY_PLUS_MODE, "", this, false, mSwipeRefreshLayout, HomeActivity.filterTypeForList, HomeActivity.type_loading);
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

    public void sort_dsc() {
        for (int i = 0; i < dDayModelsList.size(); i++) {
            for (int j = 0; j <= i; j++) {
                if (dDayModelsList.get(i).getRemainingDays() > dDayModelsList.get(j).getRemainingDays()) {
                    DDayModel temp = dDayModelsList.get(i);
                    dDayModelsList.set(i, dDayModelsList.get(j));
                    dDayModelsList.set(j, temp);
                }
            }
        }
        mDDayListAdapter.notifyDataSetChanged();
    }

    public void sort_asc() {
        for (int i = 0; i < dDayModelsList.size(); i++) {
            for (int j = 0; j <= i; j++) {
                if (dDayModelsList.get(i).getRemainingDays() < dDayModelsList.get(j).getRemainingDays()) {
                    DDayModel temp = dDayModelsList.get(i);
                    dDayModelsList.set(i, dDayModelsList.get(j));
                    dDayModelsList.set(j, temp);
                }
            }
        }
//        Collections.reverse(dDayModelsList);
        mDDayListAdapter.notifyDataSetChanged();
    }

    @Override
    public void refreshTagList() {
        dDayModelsList.clear();
        loadedDataFirstTime = false;
        tabFragmentCallback.checkListNull(Cons.DDAY_PLUS, true);
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
        tvEmpty.setVisibility(View.VISIBLE);
    }

    public void showMoreProgressBar() {
        dDayModelsList.remove(dDayModelsList.size() - 1);
        mDDayListAdapter.notifyItemRemoved(dDayModelsList.size());

        String currentIndexString = dDayModelsList.get(dDayModelsList.size() - 2).getCurrentIdexString();

        if (!currentIndexString.equals(Cons.END)) {
            dDayModelsList.add(null);
            mDDayListAdapter.notifyItemInserted(dDayModelsList.size() - 1);
            DownloadDDayList downloadDataAsync = new DownloadDDayList(getActivity(), Cons.DDAY_ALL_MODE, currentIndexString, FragmentHomePlus.this, true, mSwipeRefreshLayout, HomeActivity.filterTypeForList, HomeActivity.type_loading);
            downloadDataAsync.execute();
        }
    }
}