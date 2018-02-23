package com.dazone.crewdday.activity;

import android.app.Dialog;
import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.dazone.crewdday.Cons;
import com.dazone.crewdday.R;
import com.dazone.crewdday._Application;
import com.dazone.crewdday.adapter.DividerDecoration;
import com.dazone.crewdday.adapter.HomeMenuAdapter;
import com.dazone.crewdday.adapter.HomeTabAdapter;
import com.dazone.crewdday.fragment.FragmentHomeAll;
import com.dazone.crewdday.fragment.FragmentHomePlus;
import com.dazone.crewdday.mInterface.ConnectionFailedCallback;
import com.dazone.crewdday.mInterface.GetGroupList;
import com.dazone.crewdday.mInterface.ManageTabFragments;
import com.dazone.crewdday.mInterface.MenuCallback;
import com.dazone.crewdday.mInterface.TabFragmentCallback;
import com.dazone.crewdday.mInterface.UpdateFragmentTag;
import com.dazone.crewdday.mInterface.onGetCountOfUnreadedDdays;
import com.dazone.crewdday.model.DDayDetailModel;
import com.dazone.crewdday.model.DDayModel;
import com.dazone.crewdday.model.GroupModel;
import com.dazone.crewdday.model.MenuItemModel;
import com.dazone.crewdday.other.HttpRequest;
import com.dazone.crewdday.other.OrganizationActivity;
import com.dazone.crewdday.other.OrganizationUserDBHelper;
import com.dazone.crewdday.other.PersonData;
import com.dazone.crewdday.other.PrefManager;
import com.dazone.crewdday.other.Utils;
import com.dazone.crewdday.util.ConnectionUtils;
import com.dazone.crewdday.util.DeviceUtilities;
import com.dazone.crewdday.util.PreferenceUtilities;
import com.google.gson.Gson;
import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

public class HomeActivity extends AppCompatActivity implements ConnectionFailedCallback, TabFragmentCallback, MenuCallback {
    public static int currentFragmentPos;
    public static boolean isFirstRun = true;
    String TAG = "HomeActivity";
    private HomeMenuAdapter mMenuAdapter;
    DrawerLayout drawer;
    private ArrayList<GroupModel> groupModels;
    public static ArrayList<GroupModel> groupModelsHome;
    public static HomeActivity instance = null;
    private boolean isDdayAllLoaded = true;
    private boolean isDdayMinusLoaded = false;
    private boolean isDdayPlusLoaded = false;
    private boolean isDdayCompleteLoaded = false;

    private boolean isDdayAllListNull = true;
    private boolean isDdayMinusListNull = true;
    private boolean isDdayPlusListNull = true;
    private boolean isDdayCompleteListNull = true;
    public static int intShare = 1;
    public static boolean isManagerTool = false;
    private String mName;
    private String mEmail;
    private String mAvatar;
    private String mUrl;

    private Toolbar mToolbar;
    TextView tvTitle;
    EditText edtSearch;
    ImageView imgStopSearch;
    View underline;

    public static String sleep = "Not yet";
    HomeTabAdapter adapter;
    ViewPager viewPager;
    boolean isShare = false;
    PreferenceUtilities mPref;
    List<MenuItemModel> menuItems;
    public static FloatingActionButton fab;
    UpdateFragmentTag fragmentAll;
    UpdateFragmentTag fragmentMinus;
    UpdateFragmentTag fragmentPlus;
    UpdateFragmentTag fragmentComplete;
    Menu mMenu;
    public static boolean insertVersion2 = false;

    public static int InProgress = 0x00000002;
    public static int Completed = 0x00000004;
    public static int Hiden = 0x00000008;
    public static int filterTypeForList = 0x00000002;
    public static int newList = 0x00000010;
    public static int gotoOrganizationActivity = 0;// 0: gotoOrganizationActivity from ManageTool, 1: gotoOrganizationActivity from AddnewActivity and DetailAvtivity
    ProgressDialog progressDialog;

    public void _show_pr() {
        if (progressDialog != null)
            if (!progressDialog.isShowing())
                progressDialog.show();
    }

    public void _hide_pr() {
        if (progressDialog != null)
            if (progressDialog.isShowing())
                progressDialog.dismiss();
    }

    public static int type_loading = 0; // 0: swipe - 1: progressdialog
    public static String name_gr = "";
    public static String newgr = "";
    public static boolean show_day = true;
    public static boolean show_plus = true;
    public static int checkAlarm = 0;
    public static int checkUpdateList = 0;// 0: dont update list manager tool, 1: update list manager tool

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);


        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.colorPrimaryDrark));
        }
        mPref = _Application.getInstance().getPreferenceUtilities();
//        _Application.getInstance().shortcut(0);

        isFirstRun = true;

        checkUpdateList = 0;
        checkAlarm = 0;
        instance = this;
        group_id = 0;
        show_day = true;
        show_plus = true;
        currentFragmentPos = 0;
        name_gr = getResources().getString(R.string.Undefined);
        group_name = getResources().getString(R.string.Undefined);
        newgr = getResources().getString(R.string.newgr);

//        int Permissiontype = mPref.getPermissionType();
//        if (Permissiontype == 1) {
//            isManagerTool = true;
//        } else {
//            isManagerTool = false;
//        }
        isManagerTool = false;

        this.filterTypeForList = 0x00000002;
        HomeActivity.type_loading = 0;
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.setCanceledOnTouchOutside(false);

        if (Utils.isNetworkAvailable()) {
            OrganizationUserDBHelper.clearData();
            HttpRequest.getInstance().getDepartment(null);
        }


        // Clear all notification
        NotificationManager nMgr = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        nMgr.cancelAll();
        RelativeLayout imgLogout = (RelativeLayout) findViewById(R.id.imgLogout);
        imgLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toLogOutAct();
            }
        });


        GroupId = "-1";
        mPref.setGroupId(GroupId);
        mPref.setSelectedGroup("1");
        isShare = Boolean.parseBoolean(mPref.getShareDdayShow());
        menuItems = new ArrayList<>();
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        tvTitle = (TextView) mToolbar.findViewById(R.id.txt_title);
        imgStopSearch = (ImageView) mToolbar.findViewById(R.id.img_stop_search);
        edtSearch = (EditText) mToolbar.findViewById(R.id.edt_search);
        edtSearch.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) fab.hide();
//                else fab.show();
            }
        });
        underline = mToolbar.findViewById(R.id.underLine);
        tvTitle.setText(getResources().getString(R.string.all));

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, DDayActivity.class);
                startActivity(intent);
            }
        });

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        initImageLoader();
        initializeTabs();
        initializeMenu();

        Intent intent = getIntent();

        if (intent.getStringExtra("Sleep") != null) {
            sleep = intent.getStringExtra("Sleep");
        }

        SharedPreferences pre = getSharedPreferences(Cons.prefname, MODE_PRIVATE);
        currentFragmentPos = pre.getInt("currenttab", 0);
        listsort = pre.getBoolean("listsort", true);
        listremove = pre.getBoolean("listremove", false);

        if (listremove)
            HomeActivity.filterTypeForList = HomeActivity.InProgress | HomeActivity.Completed | HomeActivity.Hiden;

    }

//    public void update_mn_sort() {
//        mn_asc.setChecked(true);
//        mn_dsc.setChecked(false);
//        listsort = true;
//    }

    public static String GroupId = "-1";

    @Override
    protected void onResume() {
        super.onResume();
        checkUpdateList = 0;
        gotoOrganizationActivity = 0;
        FragmentHomePlus.flag = false;
        mPref.setGroupId(GroupId);
//        mPref.setSelectedGroup("1");
        if (mPref.getShareDdayShow().isEmpty()) {
            mPref.setShareDdayShow(Cons.TRUE);
        }
        int timezone = mPref.getTIME_ZONE();
        int Cur = DeviceUtilities.getTimeZoneOffset();
        if (timezone != Cur) {
            mPref.setTIME_ZONE(Cur);
            ConnectionUtils.getInstance().UpdateTimeZone(mPref.getGCMregistrationid());
        }
        SharedPreferences pre = getSharedPreferences(Cons.prefname, MODE_PRIVATE);
        boolean success = pre.getBoolean(Cons.UPDATE_SUCCESS, false);
        if (success) {
            if (HomeActivity.currentFragmentPos == 0) {
                if (FragmentHomeAll.instance != null)
                    FragmentHomeAll.instance.startLoading();
            } else {
                if (FragmentHomePlus.instance != null)
                    FragmentHomePlus.instance.startLoad();
            }
            if (badgeCount > 0) {
                if (CountOfUnreadedDdays != 0)
                    updatelist(badgeCount);
            } else {
                if (CountOfUnreadedDdays != 0) {
                    updateListAfterRemoveNewGroup();
                    setClickAfterBadgeCountNull();
                }
                CountOfUnreadedDdays = 0;
            }

            FragmentHomeAll.instance.refreshList();
            FragmentHomePlus.instance.refreshList();
            SharedPreferences.Editor editor = pre.edit();
            editor.putBoolean(Cons.UPDATE_SUCCESS, false);
            editor.commit();
        } else {
        }
        viewPager.setCurrentItem(currentFragmentPos);
        PrefManager prefManager = new PrefManager(this);
        if (prefManager.isRestoreList()) {
            OrganizationUserDBHelper.clearData();
            HttpRequest.getInstance().getDepartment(null);
            prefManager.setRestoreList(false);
        } else {
        }

        if (!mPref.getFIRST_LOGIN()) {
            Log.d(TAG, "NOT FIRST_LOGIN");
            // hide intro view
            if (FragmentHomeAll.instance != null) FragmentHomeAll.instance.hideIntroView();
            if (FragmentHomePlus.instance != null) FragmentHomePlus.instance.hideIntroView();
        } else {
            Log.d(TAG, "FIRST_LOGIN");
        }
    }

    public void _show_mn_search() {
        if (!edtSearch.isShown())
            if (menu_search != null)
                menu_search.setVisible(true);
    }

    private void initializeTabs() {

        //Tab layout initialize
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
//        tabLayout.addTab(tabLayout.newTab().setText(Cons.DDAY_ALL));
        tabLayout.addTab(tabLayout.newTab().setText(Cons.DDAY_MINUS));
//        tabLayout.addTab(tabLayout.newTab().setText(Cons.DDAY_COMPLETE));
        tabLayout.addTab(tabLayout.newTab().setText(Cons.DDAY_PLUS));

        View v2 = LayoutInflater.from(this).inflate(R.layout.layout_custom_text_tablayout, null);
        final TextView tv2 = (TextView) v2.findViewById(R.id.tv);
        tv2.setText(getResources().getString(R.string.dday_minus));
        tv2.setGravity(Gravity.CENTER_HORIZONTAL);
        View v4 = LayoutInflater.from(this).inflate(R.layout.layout_custom_text_tablayout, null);
        final TextView tv4 = (TextView) v4.findViewById(R.id.tv);
        tv4.setText(getResources().getString(R.string.dday_plus));
        tv4.setGravity(Gravity.CENTER_HORIZONTAL);
//
        tabLayout.getTabAt(0).setCustomView(v2);
        tabLayout.getTabAt(1).setCustomView(v4);
//        tabLayout.getTabAt(2).setCustomView(v3);
//        tabLayout.getTabAt(3).setCustomView(v4);

        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        tabLayout.setSelectedTabIndicatorColor(Color.parseColor("#ffffff"));

        viewPager = (ViewPager) findViewById(R.id.pager);
//        Log.e(TAG, "num:" + tabLayout.getTabCount());
        adapter = new HomeTabAdapter
                (getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setOffscreenPageLimit(1);
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        currentFragmentPos = viewPager.getCurrentItem();
        fragmentAll = (UpdateFragmentTag) adapter.instantiateItem(viewPager, 0);
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getPosition() != 1) {
                    if (!fab.isShown())
                        _show_fab();
                    if (show_day) {
                        _show_mn_search();
                        showMenuFilter();
                    } else {
//                        menu_search.setVisible(false);
//                        hideMenuFilter();
                    }
                    hideMenuSort();
                } else {
                    if (FragmentHomePlus.flag)
                        fab.hide();
                    if (show_plus) {
                        showMenuSort();
                        _show_mn_search();
                    } else {
//                        hideMenuSort();
//                        menu_search.setVisible(false);
                    }
//                    hideMenuFilter();
                }

                viewPager.setCurrentItem(tab.getPosition());
                int position = tab.getPosition();
                currentFragmentPos = position;

                ManageTabFragments loadFragment;
                switch (position) {
                    case 0:
                        if (isDdayAllListNull) {
                            isDdayAllLoaded = false;
                        } else {
                            isDdayAllLoaded = true;
                        }
                        if (!isDdayAllLoaded) {
                            if (!mPref.getSectionFragmentAll().isEmpty()) {
                                mPref.setSectionFragmentAll("");
                            }
                            fragmentAll = (UpdateFragmentTag) adapter.instantiateItem(viewPager, 0);
                            loadFragment = (ManageTabFragments) adapter.instantiateItem(viewPager, 0);
                            loadFragment.loadFragment();
                            isDdayAllLoaded = true;
                        }
                        break;

                    case 1:
                        if (isDdayMinusListNull) {
                            isDdayMinusLoaded = false;
                        }
                        if (!isDdayMinusLoaded) {
                            if (!mPref.getSectionFragmentMinus().isEmpty()) {
                                mPref.setSectionFragmentMinus("");
                            }
                            fragmentMinus = (UpdateFragmentTag) adapter.instantiateItem(viewPager, 1);
                            loadFragment = (ManageTabFragments) adapter.instantiateItem(viewPager, 1);
                            loadFragment.loadFragment();
                            isDdayMinusLoaded = true;
                        }
                        break;

                    case 2:
                        if (isDdayCompleteListNull) {
                            isDdayCompleteLoaded = false;
                        }
                        if (!isDdayCompleteLoaded) {
                            if (!mPref.getSectionFragmentComplete().isEmpty()) {
                                mPref.setSectionFragmentComplete("");
                            }
                            fragmentComplete = (UpdateFragmentTag) adapter.instantiateItem(viewPager, 2);
                            loadFragment = (ManageTabFragments) adapter.instantiateItem(viewPager, 2);
                            loadFragment.loadFragment();
                            isDdayCompleteLoaded = true;
                        }
                        break;

                    case 3:
                        if (isDdayPlusListNull) {
                            isDdayPlusLoaded = false;
                        }
                        if (!isDdayPlusLoaded) {
                            if (!mPref.getSectionFragmentPlus().isEmpty()) {
                                mPref.setSectionFragmentPlus("");
                            }
                            fragmentPlus = (UpdateFragmentTag) adapter.instantiateItem(viewPager, 3);
                            loadFragment = (ManageTabFragments) adapter.instantiateItem(viewPager, 3);
                            loadFragment.loadFragment();
                            isDdayPlusLoaded = true;
                        }
                        break;

                    default:
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    public void _show_fab() {
        if (!edtSearch.isShown())
            fab.show();
    }

    // Initialize Universal Image Loader
    private void initImageLoader() {
        DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
                .cacheOnDisc(true).cacheInMemory(true)
                .imageScaleType(ImageScaleType.EXACTLY).build();

        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
                getApplicationContext())
                .defaultDisplayImageOptions(defaultOptions)
                .memoryCache(new WeakMemoryCache())
                .discCacheSize(100 * 1024 * 1024).build();

        ImageLoader.getInstance().init(config);
    }

    public static int group_id = 0;
    public static String group_name = "";
    int CountOfUnreadedDdays = 0;
    public static int badgeCount = 0;

    public void updatelist(int CountOfDays) {
        mMenuAdapter.update(CountOfDays);
    }

    public void setClickAfterBadgeCountNull() {
        mMenuAdapter.setClickAfterBadge(1);
    }

    public void updateListAfterRemoveNewGroup() {
        mMenuAdapter.removeNewGroup();
    }

    private void initializeMenu() {
        List<MenuItemModel> mMenuItemList = fillMenuData();
        mMenuAdapter = new HomeMenuAdapter(this, mMenuItemList, this);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.sliding_menu);
        recyclerView.setAdapter(mMenuAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerDecoration(this));

        //Get group from web and add to menu
        ConnectionUtils.getInstance().GetCountOfUnreadedDdays(new onGetCountOfUnreadedDdays() {
            @Override
            public void onGetCountOfUnreadedDdaysSuccess(int number) {
//                _Application.getInstance().shortcut(number);
                CountOfUnreadedDdays = number;
                badgeCount = number;
//                new GetGroupListFromWeb().execute();
                GetGroupListFromWeb();
            }

            @Override
            public void onFail() {
//                new GetGroupListFromWeb().execute();
                GetGroupListFromWeb();
            }
        });

        PreferenceUtilities prefUtils = _Application.getInstance().getPreferenceUtilities();
        String serviceDomain = prefUtils.getCurrentServiceDomain();


        String name = prefUtils.getUserName();
        String email = prefUtils.getEmail();
        String avatar = prefUtils.getAvatar();


        //Remove brackets from the string
        mName = name.replaceAll("\\p{P}", "");
        mEmail = email.replaceAll("\"", "");
        mAvatar = avatar.replaceAll("\"", "");


        mUrl = serviceDomain + mAvatar;

        ImageLoader imageLoader = ImageLoader.getInstance();

        TextView tvName = (TextView) findViewById(R.id.txt_name);
        TextView tvEmail = (TextView) findViewById(R.id.txt_email);
        ImageView ivProfile = (ImageView) findViewById(R.id.img_profile);
        ivProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, LogoutActivity.class);
                startActivity(intent);
            }
        });
        Log.d(TAG, "mUrl:" + mUrl);
        imageLoader.displayImage(mUrl, ivProfile);
        tvName.setText(mName);
        tvEmail.setText(mEmail);
    }

    private List<MenuItemModel> fillMenuData() {
        menuItems.add(new MenuItemModel(getResources().getString(R.string.addGroup), R.drawable.menu_left_ic_add, Cons.MENU_TYPE_NORMAL, 0));
        return menuItems;
    }

    public void toLogOutAct() {
//        Intent intent = new Intent(HomeActivity.this, LogoutActivity.class);
        Intent intent = new Intent(HomeActivity.this, SettingPage.class);
        startActivity(intent);
    }

    @Override
    public void onConnectionFailed() {

        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                final Dialog dialog = new Dialog(HomeActivity.this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.dialog_connection_error);
                final TextView tvConfirm = (TextView) dialog.findViewById(R.id.txt_confirm);
                tvConfirm.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //finish();
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        });
    }

    @Override
    public void checkListNull(String type, boolean result) {
        switch (type) {
            case Cons.DDAY_ALL:
                isDdayAllListNull = result;
                break;
            case Cons.DDAY_MINUS:
                isDdayMinusListNull = result;
                break;
            case Cons.DDAY_COMPLETE:
                isDdayCompleteListNull = result;
                break;
            case Cons.DDAY_PLUS:
                isDdayPlusListNull = result;
                break;
        }
    }

    @Override
    public void refreshFragments(String text, String groupId) {

        if (!mPref.getGroupId().equals(groupId)) {
            // must save pref first
            mPref.setGroupId(groupId);
            tvTitle.setText(text);
            //viewPager.setCurrentItem(0);
            adapter.notifyDataSetChanged();
        }
        drawer.closeDrawers();
    }

    public void update_list_share() {
        adapter.notifyDataSetChanged();
    }

    @Override
    public void refreshFragments() {
        //viewPager.setCurrentItem(0);
        adapter.notifyDataSetChanged();
        drawer.closeDrawers();
    }

    @Override
    public void refresh(MenuItemModel menuItemModel, int position) {

        switch (HomeActivity.currentFragmentPos) {
            case 0:
                updateFragment(fragmentAll, fragmentComplete, fragmentMinus, fragmentPlus,
                        menuItemModel.getTagNo(), position);
                break;
            case 1:
                updateFragment(fragmentMinus, fragmentComplete, fragmentAll, fragmentPlus,
                        menuItemModel.getTagNo(), position);
                break;
            case 2:
                updateFragment(fragmentComplete, fragmentAll, fragmentMinus, fragmentPlus,
                        menuItemModel.getTagNo(), position);
                break;
            case 3:
                updateFragment(fragmentPlus, fragmentComplete, fragmentMinus, fragmentAll,
                        menuItemModel.getTagNo(), position);
                break;
        }
    }

    public void refreshCountOfUnreadDays() {
        ConnectionUtils.getInstance().GetCountOfUnreadedDdays(new onGetCountOfUnreadedDdays() {
            @Override
            public void onGetCountOfUnreadedDdaysSuccess(int number) {
//                _Application.getInstance().shortcut(number);
                CountOfUnreadedDdays = number;
                badgeCount = number;

                if (number == 0) {
                    mMenuAdapter.removeNewGroup();
                } else {
                    mMenuAdapter.update(number);
                }
            }

            @Override
            public void onFail() {
            }
        });
    }

    private void updateFragment(UpdateFragmentTag update, UpdateFragmentTag refresh1,
                                UpdateFragmentTag refresh2, UpdateFragmentTag refresh3, int oldTag, int newTag) {
        if (update != null) {
            update.updateTag(oldTag, newTag);
        }

        if (refresh1 != null) {
            refresh1.refreshTagList();
        }
        if (refresh2 != null) {
            refresh2.refreshTagList();
        }

        if (refresh3 != null) {
            refresh3.refreshTagList();
        }
    }

    public void getNewgroupModelsHome() {
//        new UpdategroupModelsHome().execute();
        UpdategroupModelsHome();
    }

    void UpdategroupModelsHome() {
        ConnectionUtils.getInstance().getGroupModelList_v2(new GetGroupList() {
            @Override
            public void onSuccess(ArrayList<GroupModel> listGroup) {
                groupModelsHome = listGroup;
            }

            @Override
            public void onFail() {
                Toast.makeText(getApplicationContext(), "Can not get GroupList", Toast.LENGTH_SHORT).show();
            }
        });
    }

//    private class UpdategroupModelsHome extends AsyncTask<Void, String, String> {
//        private String name = null;
//
//        @Override
//        protected String doInBackground(Void... params) {
//            Log.d(TAG, "getGroupModelList 1");
//            groupModelsHome = ConnectionUtils.getInstance().getGroupModelList();
//            return name;
//        }
//
//        @Override
//        protected void onPostExecute(String result) {
//            super.onPostExecute(result);
//        }
//    }

    void GetGroupListFromWeb() {
        ConnectionUtils.getInstance().getGroupModelList_v2(new GetGroupList() {
            @Override
            public void onSuccess(ArrayList<GroupModel> listGroup) {
                //from doInBackground
                groupModels = listGroup;
                groupModelsHome = listGroup;
                if (CountOfUnreadedDdays != 0) {
                    GroupModel groupModel = new GroupModel();
                    groupModel.setGroupNo(-2);
                    groupModel.setName(getResources().getString(R.string.newgr));
                    groupModel.setTagNo(0);
                    groupModel.setCountOfDays(CountOfUnreadedDdays);
                    groupModels.add(groupModel);
                }
                Collections.sort(groupModels, Collections.reverseOrder());
                if (isManagerTool) {
                    GroupModel groupModel = new GroupModel();
                    groupModel.setGroupNo(-1000);
                    groupModel.setName(getResources().getString(R.string.managementtools));
                    groupModel.setTagNo(0);
                    groupModel.setCountOfDays(1);
                    groupModels.add(0, groupModel);
                }

                // from onPostExecute
                int sum = 0;
                if (CountOfUnreadedDdays != 0) {
                    for (int i = 0; i < groupModels.size(); i++) {
                        if (i < groupModels.size() - 1)
                            sum += groupModels.get(i).getCountOfDays();
                    }
                } else {
                    for (int i = 0; i < groupModels.size(); i++) {
                        if (i < groupModels.size())
                            sum += groupModels.get(i).getCountOfDays();
                    }
                }
                MenuItemModel menuItem = new MenuItemModel();
                menuItem.setText(getResources().getString(R.string.all));
                menuItem.setImageId(R.drawable.detail_ic_content);
                menuItem.setType(Cons.MENU_TYPE_SUB);
                menuItem.setCountOfDays(sum);
                menuItem.setGroupId(-1);
                menuItem.setIsClicked(true);
                menuItems.add(menuItem);
                for (int i = 0; i < groupModels.size(); i++) {
                    mMenuAdapter.insert(Cons.NEW_GROUP_POSITION, groupModels.get(i));
                }
                // menuItems.add(new MenuItemModel(Cons.SHARE_DDAY_SHOW, 0, Cons.MENU_TYPE_OTHER));
                mMenuAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFail() {
                Toast.makeText(getApplicationContext(), "Can not get GroupList", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void hideMenuFilter() {
        if (menu_filter != null)
            menu_filter.setVisible(false);
    }

    public void showMenuFilter() {
        if (menu_filter != null)
            if (!edtSearch.isShown())
                menu_filter.setVisible(true);
    }

    public void hideMenuSort() {
        if (menu_sort != null)
            menu_sort.setVisible(false);
    }

    public void showMenuSort() {
        if (menu_sort != null)
            if (!edtSearch.isShown())
                menu_sort.setVisible(true);
    }

    public void check_item_menu_show() {
        _show_mn_search();
        showMenuFilter();
        if (currentFragmentPos == 0) {

        } else {
            showMenuSort();
        }
    }

    MenuItem menu_filter, menu_sort, inprocess, hashide, mn_asc, mn_dsc, menu_search;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home, menu);

        mMenu = menu;
        if (menu != null) {
            menu_search = menu.findItem(R.id.menu_search);
            mn_asc = menu.findItem(R.id.asc);
            mn_dsc = menu.findItem(R.id.dsc);
            hashide = menu.findItem(R.id.hashide);
            inprocess = menu.findItem(R.id.inprocess);

            if (isShare) {
                hashide.setChecked(true);
            }

            if (listremove) {
                inprocess.setChecked(true);
            }

            if (listsort) {
                mn_asc.setChecked(true);
            } else {
                mn_dsc.setChecked(true);
            }

            menu_filter = menu.findItem(R.id.menu_filter);
            menu_sort = menu.findItem(R.id.menu_sort);
            hideMenuSort();
        }

        return true;
    }

    int menu_search_click = 0;

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.menu_search) {
            hideMenuFilter();
            hideMenuSort();
            drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
            menu_search_click = 1;
            fab.hide();
//            final MenuItem item2 = mMenu.findItem(R.id.menu_search);
//            item2.setVisible(false);
            menu_search.setVisible(false);
            tvTitle.setVisibility(View.GONE);
            edtSearch.setVisibility(View.VISIBLE);
            underline.setVisibility(View.VISIBLE);
            setSearchFunction(edtSearch);

            mToolbar.setNavigationIcon(R.drawable.mnubar_ic_back);
            mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    handler_back_search(item2);
                    handler_back_search(menu_search);
                }
            });

            edtSearch.requestFocus();
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(edtSearch, InputMethodManager.SHOW_IMPLICIT);
            return true;
        } else if (id == R.id.menu_filter) {
        } else if (id == R.id.menu_sort) {
        } else if (id == R.id.inprocess) {


            if (inprocess.isChecked()) {
                HomeActivity.filterTypeForList = HomeActivity.InProgress;
                inprocess.setChecked(false);
                listremove = false;
            } else {

                HomeActivity.filterTypeForList = HomeActivity.InProgress | HomeActivity.Completed | HomeActivity.Hiden;
                inprocess.setChecked(true);
                listremove = true;
            }

            HomeActivity.type_loading = 1;
            FragmentHomeAll.instance.refreshList();
            FragmentHomePlus.instance.refreshList();
        } else if (id == R.id.hashide) {

            if (hashide.isChecked()) {
                mPref.setShareDdayShow(Cons.FALSE);
                hashide.setChecked(false);
            } else {
                mPref.setShareDdayShow(Cons.TRUE);
                hashide.setChecked(true);
            }
            update_list_share();
        } else if (id == R.id.asc) {
            listsort = true;
            mn_asc.setChecked(true);
            mn_dsc.setChecked(false);
            FragmentHomePlus.instance.sort_asc();
        } else if (id == R.id.dsc) {
            listsort = false;
            mn_asc.setChecked(false);
            mn_dsc.setChecked(true);
            FragmentHomePlus.instance.sort_dsc();
        }

        return super.onOptionsItemSelected(item);
    }

    public void updateListDDayPlus() {
        if (mn_asc != null) {
            if (mn_asc.isChecked()) {
                FragmentHomePlus.instance.sort_asc();
            } else {
                FragmentHomePlus.instance.sort_dsc();
            }
        }
    }

    public void handler_back_search(MenuItem item2) {
        drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
        menu_search_click = 0;
        if (!mPref.getSearchText().isEmpty()) {
            resetSectionTitle();
            mPref.setSearchText("");
            adapter.notifyDataSetChanged();
        }

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(HomeActivity.this, drawer, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        tvTitle.setVisibility(View.VISIBLE);
        edtSearch.setVisibility(View.INVISIBLE);
        edtSearch.setText(null);
        underline.setVisibility(View.INVISIBLE);
        imgStopSearch.setVisibility(View.INVISIBLE);
        item2.setVisible(true);
        fab.show();
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(edtSearch.getWindowToken(), 0);

        if (currentFragmentPos == 0) {

        } else {
            showMenuSort();
        }

        showMenuFilter();
    }

    private void setSearchFunction(final EditText edtSearch) {
        edtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                imgStopSearch.setVisibility(View.VISIBLE);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        edtSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    resetSectionTitle();
                    mPref.setSearchText(edtSearch.getText().toString());
                    adapter.notifyDataSetChanged();
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(edtSearch.getWindowToken(), 0);
                }
                return false;
            }
        });

        imgStopSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edtSearch.setText(null);
                v.setVisibility(View.GONE);
            }
        });
    }

    private void resetSectionTitle() {
        mPref.setSectionFragmentAll("");
        mPref.setSectionFragmentMinus("");
        mPref.setSectionFragmentComplete("");
        mPref.setSectionFragmentPlus("");
    }

    private void resetValues() {
        mPref.setSharersList("");
        mPref.setPersonConcernedList("");
        mPref.setAdminsList("");
        mPref.setSearchText("");
        mPref.resetGroupId();
        mPref.setSelectedGroup("1");

        mPref.resetGroupPos();
        mPref.resetDdayType();
        mPref.resetAlreadyDdayType();
        //--Special--//
        mPref.resetSpecialList();
        mPref.resetSpecialSpecificRO();
        mPref.resetSpecialPlusRO();
        mPref.setSpecialSpecificRO("");
        mPref.resetSpecicalCalendar();
        mPref.resetSpecialFT();
        //--Daily--//
        mPref.resetDailyRO();
        mPref.resetDailyIntervalValue();
        mPref.resetDailyIntervalText();
        mPref.resetDailyStartDate();
        mPref.resetDailyEndDate();
        mPref.resetDailyCheckbox();
        mPref.resetDailyStartTypeValue();
        mPref.resetDailyStartTypeText();
        mPref.resetDailyHolidayValue();
        mPref.resetDailyHolidayType();
        //--Weekly--//
        mPref.resetWeeklyRO();
        mPref.resetWeeklyIntervalValue();
        mPref.resetWeeklyInterval();
        mPref.resetWeeklyStartDate();
        mPref.resetWeeklyEndDate();
        mPref.resetWeeklyCheckbox();
        mPref.resetWeeklyHolidayValue();
        mPref.resetWeeklyHolidayType();
        mPref.resetWeeklySpecificDays();

        //--Monthly--//

        mPref.resetMonthlyIntervalValue();
        mPref.resetMonthlyInterval();
        mPref.resetMonthlyStartDate();
        mPref.resetMonthlyEndDate();
        mPref.resetMonthlyCheckbox();
        mPref.resetMonthlyHolidayValue();
        mPref.resetMonthlyHolidayType();
        mPref.resetMonthlyList();
        mPref.resetMonthlyRO_1();
        mPref.resetMonthlyRO_2();
        mPref.setMonthlyPositionAdded("");
        mPref.setMonthlyCurrentItemPosition("");

        //--Annually--//

        mPref.resetAnnuallyIntervalPos();
        mPref.resetAnnuallyInterval();
        mPref.resetAnnuallyStartDate();
        mPref.resetAnnuallyEndDate();
        mPref.resetAnnuallyCheckbox();
        mPref.resetAnnuallyHolidayPos();
        mPref.resetAnnuallyHolidayType();
        mPref.resetAnnuallyList();
        mPref.setAnnuallyRO_1("");
        mPref.setAnnuallyRO_2("");
    }

    @Override
    protected void onStop() {
        super.onStop();
        isDdayPlusLoaded = false;
        isDdayCompleteLoaded = false;
        isDdayMinusLoaded = false;
        mPref.setSectionFragmentAll("");
        mPref.setSectionFragmentMinus("");
        mPref.setSectionFragmentComplete("");
        mPref.setSectionFragmentPlus("");

        mPref.setSearchText("");
//        mPref.setSelectedGroup("1");
        mPref.resetGroupPos();
        mPref.resetGroupId();
        mPref.resetDdayType();
        mPref.resetAlreadyDdayType();

//        resetValues();
    }

    private Handler myHandler = new Handler();
    private Runnable myRunnable = new Runnable() {
        public void run() {
            mIsExit = false;
        }
    };
    private boolean mIsExit;

    @Override
    public void onBackPressed() {

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (menu_search_click == 1) {
//            MenuItem item2 = mMenu.findItem(R.id.menu_search);
//            handler_back_search(item2);
            handler_back_search(menu_search);
        } else if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            if (!isTaskRoot()) {
                super.onBackPressed();
            } else {
                if (mIsExit) {
                    super.onBackPressed();
                } else {
                    // press 2 times to exit app feature
                    this.mIsExit = true;
                    String Str = "";
//                Log.e("LL", Locale.getDefault().getLanguage());
                    if (Locale.getDefault().getLanguage().equalsIgnoreCase("vi")) {
                        Str = "Click thêm lần nữa ứng dụng sẽ được đóng";
                    } else if (Locale.getDefault().getLanguage().equalsIgnoreCase("ko")) {
                        Str = "'뒤로'버튼을 한번 더 누르시면 종료됩니다.";
                    } else {
                        Str = "Press back again to quit.";
                    }
                    Toast.makeText(this, Str, Toast.LENGTH_SHORT).show();
                    myHandler.postDelayed(myRunnable, 2000);
                }
            }
        }
    }

    public void startOrganizationActivity() {
        ArrayList<PersonData> selectedList = new ArrayList<PersonData>();
        checkUpdateList = 1;
        Intent i = new Intent(this, OrganizationActivity.class);
        i.putParcelableArrayListExtra(Cons.BUNDLE_LIST_PERSON, selectedList);
        i.putExtra(Cons.BUNDLE_TYPE, 1); //1: ShareType
        i.putExtra(Cons.BUNDLE_ORG_DISPLAY_SELECTED_ONLY, false);
        startActivity(i);
//        startActivityForResult(i, Cons.ORGANIZATION_TO_ACTIVITY);
    }

    boolean listremove = false;
    boolean listsort = true;

    @Override
    protected void onPause() {
        super.onPause();
        savingPreferences();
    }

    public boolean returnSort() {
        return listsort;
    }

    public void savingPreferences() {
        SharedPreferences pre = getSharedPreferences(Cons.prefname, MODE_PRIVATE);
        SharedPreferences.Editor editor = pre.edit();
        editor.putBoolean("listsort", listsort);
        editor.putBoolean("listremove", listremove);
        editor.putInt("currenttab", currentFragmentPos);
        editor.commit();
    }

    private int DETAILS_REQUEST_CODE = 1;

    public void _edit(DDayModel dDayModel) {
        Intent intent = new Intent(this, DetailViewActivity.class);
        intent.putExtra(Cons.KEY_DDAY_ID, dDayModel.getDdayId());
        intent.putExtra(Cons.KEY_DDAY_REMAINING_DAYS, dDayModel.getRemainingDays());
        intent.putExtra(Cons.KEY_DDAY_DATE, dDayModel.getDdayDate());
        intent.putExtra(Cons.KEY_DDAY_DIRECTOR_NAME, dDayModel.getDirectorName());
        intent.putExtra(Cons.KEY_DDAY_GROUP_ID, dDayModel.getGroupId());
        intent.putExtra(Cons.KEY_CAN_HIDE, dDayModel.isCanHide());
        intent.putExtra(Cons.KEY_IS_COMPLETED, dDayModel.isCompleted());
        intent.putExtra(Cons.KEY_IS_COMPLETED_RECORD, dDayModel.getCompletedRecordNo());
        intent.putExtra(Cons.KEY_IS_HIDDEN, dDayModel.isHidden());
        intent.putExtra(Cons.KEY_IS_HIDDEN_NO, dDayModel.getHiddenDataNo());
        intent.putExtra(Cons.KEY_MANAGER, dDayModel.isCanManage());
        intent.putExtra(Cons.KEY_IS_NEW, dDayModel.isNew());

        startActivityForResult(intent, DETAILS_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == DETAILS_REQUEST_CODE) {
            handlerResult(requestCode, resultCode, data);
        } else super.onActivityResult(requestCode, resultCode, data);
    }

    void handlerResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Cons.DETAILS_SUCCESS) {
            DDayDetailModel obj = data.getParcelableExtra(Cons.DETAILS_UPDATE_LIST_PRE);
            if (obj != null) {
//                Log.d(TAG, "handlerResult:" + new Gson().toJson(obj));
                if (FragmentHomeAll.instance != null)
                    FragmentHomeAll.instance.update_new_data(obj);
                if (FragmentHomePlus.instance != null)
                    FragmentHomePlus.instance.update_new_data(obj);
            }
        }
    }
}