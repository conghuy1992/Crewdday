package com.dazone.crewdday.activity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TabHost;
import android.widget.TextView;

import com.dazone.crewdday.Cons;
import com.dazone.crewdday.R;
import com.dazone.crewdday._Application;
import com.dazone.crewdday.adapter.AdapterListRepeat;
import com.dazone.crewdday.adapter.AdapterNotifi;
import com.dazone.crewdday.adapter.MonthAdapter;
import com.dazone.crewdday.adapter.NotifyTime;
import com.dazone.crewdday.adapter.ObjectAddMonth;
import com.dazone.crewdday.adapter.ObjectAddYear;
import com.dazone.crewdday.adapter.Object_CheckDPlusDay;
import com.dazone.crewdday.adapter.Object_CheckEveryDayDday;
import com.dazone.crewdday.adapter.Object_CheckEveryDdayOfWeek;
import com.dazone.crewdday.adapter.Object_CheckEveryMonthSpecificDday;
import com.dazone.crewdday.adapter.Object_CheckEveryYearDday;
import com.dazone.crewdday.adapter.Object_CheckSpecificDday;
import com.dazone.crewdday.adapter.SpecialAdapter;
import com.dazone.crewdday.adapter.YearAdapter;
import com.dazone.crewdday.mInterface.GetGroups;
import com.dazone.crewdday.mInterface.InsertDdaySuccess;
import com.dazone.crewdday.mInterface.ItemClickListener;
import com.dazone.crewdday.mInterface.UpdateSpecialList;
import com.dazone.crewdday.mInterface.UpdateSuccess;
import com.dazone.crewdday.model.DDayDetailModel;
import com.dazone.crewdday.model.Employee;
import com.dazone.crewdday.model.ObjectGetGroups;
import com.dazone.crewdday.other.ListUtils;
import com.dazone.crewdday.other.OrganizationActivity;
import com.dazone.crewdday.other.PersonData;
import com.dazone.crewdday.util.ConnectionUtils;
import com.dazone.crewdday.util.PreferenceUtilities;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class DDayActivity extends AppCompatActivity implements View.OnClickListener, InsertDdaySuccess, UpdateSuccess, GetGroups {
    String date_finish = "2100-12-31";
    //    String date_finish = "";
    public static DDayActivity instance = null;
    String TAG = "DDayActivity";
    ImageView img_plus;
    long ddayId;
    Intent intent;
    String title_temp = "", content_temp = "", str1 = "", str2 = "", str3 = "", str4 = "", str5 = "";
    EditText edtTitle;
    EditText edtContent;
    public static AdapterListRepeat adapterListRepeat;
    public static ArrayList<String> temp_list;
    public static ArrayList<String> arrayList_repeat;
    ArrayList<PersonData> resultList;
    ArrayList<PersonData> resultList2;
    ArrayList<PersonData> resultList3;
    LinearLayout ln_checkbox, layout_notify;
    TextView tvAdmin, tvSharer, tvPersonConcerned;
    CheckBox cb_sharer;

    UpdateSpecialList updateSpecialList;
    DDayDetailModel detailModel;
//    Spinner sp_group;

    LinearLayout ln_content, group_parent_container;
    ArrayList<Integer> sp_GroupNo = new ArrayList<Integer>();
    ArrayList<String> sp_Name = new ArrayList<String>();
    private int mType;
    public int typeNo = 2;
    public int groupNo = 0;
    boolean isEdit;
    int c_year, c_month, c_date, day_of_week;
    public static String month_date[];
    public static String arr_notify[];
    public static String month_week[];
    public static String month_day_of_week[];
    public static String arr_month[];
    public static String arr_calendar[];

    public static MonthAdapter monthAdapter;
    String arrayList_frequency[];
    String arrayList_option[];
    String arrayList_holiday[];
    String arrayList_annually[];
//    private RecyclerView rv_sharer, rv_sharer2, rv_sharer3;
//    private StaggeredGridLayoutManager mGridLayoutManager, mGridLayoutManager2, mGridLayoutManager3;
//    private RecyclerViewAdapter mAdapter;
//    RecyclerViewAdapter2 mAdapter2;
//    RecyclerViewAdapter3 mAdapter3;
//    public ArrayList<String> mList, mList2, mList3;

    public void startOrganization() {
        startOrganizationActivity(resultList, Cons.SHARER_TYPE);
    }

    public void startOrganization2() {
        startOrganizationActivity2(resultList2, Cons.PERSON_CONCERNED_TYPE);
    }

    public void startOrganization3() {
        startOrganizationActivity3(resultList3, Cons.ADMIN_TYPE);
    }

    public void remove_pos3(int pos) {
//        resultList3.remove(pos);
//        mList3.remove(pos);
//        mAdapter3.notifyDataSetChanged();
//        Gson gson = new Gson();
//        JSON_ADMIN_TYPE = gson.toJson(resultList3);
//        if (JSON_ADMIN_TYPE.equals("[]")) JSON_ADMIN_TYPE = "";
////        Log.e(TAG, "JSON_ADMIN_TYPE:" + JSON_ADMIN_TYPE);
//        if (mList3.size() == 0) {
//            tvAdmin.setVisibility(View.VISIBLE);
//            tvAdmin.setText(getResources().getString(R.string.co_administrator));
//            tvAdmin.setTextColor(Color.parseColor("#9d9d9d"));
//            rv_sharer3.setVisibility(View.GONE);
//        }
    }

    public void remove_pos2(int pos) {
//        resultList2.remove(pos);
//        mList2.remove(pos);
//        mAdapter2.notifyDataSetChanged();
//        Gson gson = new Gson();
//        JSON_PERSON_CONCERNED_TYPE = gson.toJson(resultList2);
//        if (JSON_PERSON_CONCERNED_TYPE.equals("[]")) JSON_PERSON_CONCERNED_TYPE = "";
////        Log.e(TAG, "JSON_PERSON_CONCERNED_TYPE:" + JSON_PERSON_CONCERNED_TYPE);
//        if (mList2.size() == 0) {
//            tvPersonConcerned.setVisibility(View.VISIBLE);
//            tvPersonConcerned.setText(getResources().getString(R.string.personal_concerned));
//            tvPersonConcerned.setTextColor(Color.parseColor("#9d9d9d"));
//            rv_sharer2.setVisibility(View.GONE);
//        }
    }

    public void remove_pos(int pos) {
//        resultList.remove(pos);
//        mList.remove(pos);
//        mAdapter.notifyDataSetChanged();
//        Gson gson = new Gson();
//        JSON_SHARER_TYPE = gson.toJson(resultList);
//        if (JSON_SHARER_TYPE.equals("[]")) JSON_SHARER_TYPE = "";
////        Log.e(TAG, "JSON_SHARER_TYPE:" + JSON_SHARER_TYPE);
//        if (mList.size() == 0) {
//            tvSharer.setVisibility(View.VISIBLE);
//            tvSharer.setText(getResources().getString(R.string.sharer));
//            tvSharer.setTextColor(Color.parseColor("#9d9d9d"));
//            rv_sharer.setVisibility(View.GONE);
//        }
    }

    public static int sp_ddaytype_int = 0;
    RadioButton special_rb1, special_rb2, special_rb3, rb_ddayminus, rb_ddayplus;
    TextView tv_group, tv_myself;
    PreferenceUtilities prefUtils;
    int myId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dday);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        HomeActivity.gotoOrganizationActivity = 1;
        HomeActivity.insertVersion2 = true;

        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.colorPrimaryDrark));
        }

        prefUtils = _Application.getInstance().getPreferenceUtilities();
        prefUtils.setFIRST_LOGIN(false);
        myId = prefUtils.getId();
        restartjson();
        instance = this;
        groupNo = HomeActivity.group_id;
        if (groupNo < 0) groupNo = 0;
        tv_group = (TextView) findViewById(R.id.tv_group);
//        Log.e(TAG,HomeActivity.group_name);
        tv_group.setText(HomeActivity.group_name);
        group_parent_container = (LinearLayout) findViewById(R.id.group_parent_container);
        ln_checkbox = (LinearLayout) findViewById(R.id.ln_checkbox);
        layout_notify = (LinearLayout) findViewById(R.id.layout_notify);
        group_parent_container.setOnClickListener(this);
        ln_content = (LinearLayout) findViewById(R.id.ln_content);
        img_plus = (ImageView) findViewById(R.id.img_plus);
        img_plus.setOnClickListener(this);
        tv_myself = (TextView) findViewById(R.id.tv_myself);
        tv_myself.setOnClickListener(this);
        special_rb1 = (RadioButton) findViewById(R.id.special_rb1);
        special_rb2 = (RadioButton) findViewById(R.id.special_rb2);
        special_rb3 = (RadioButton) findViewById(R.id.special_rb3);
        special_rb1.setOnClickListener(this);
        special_rb2.setOnClickListener(this);
        special_rb3.setOnClickListener(this);
        rb_ddayminus = (RadioButton) findViewById(R.id.rb_ddayminus);
        rb_ddayplus = (RadioButton) findViewById(R.id.rb_ddayplus);
        rb_ddayminus.setOnClickListener(this);
        rb_ddayplus.setOnClickListener(this);
        arrayList_annually = getResources().getStringArray(R.array.annually_interval_array);
        arrayList_holiday = getResources().getStringArray(R.array.daily_holiday);
        arrayList_option = getResources().getStringArray(R.array.daily_option);
        arrayList_frequency = getResources().getStringArray(R.array.daily_interval_array);
        arr_notify = getResources().getStringArray(R.array.array_notify);
        month_date = getResources().getStringArray(R.array.monthly_date_array);
        month_week = getResources().getStringArray(R.array.monthly_week_array);
        month_day_of_week = getResources().getStringArray(R.array.monthly_day_array);
        arr_month = getResources().getStringArray(R.array.annually_month_array);
        arr_calendar = getResources().getStringArray(R.array.annually_calendar_type_array);
        Calendar calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month_special = calendar.get(Calendar.MONTH) + 1;
        date_special = calendar.get(Calendar.DAY_OF_MONTH);
        c_year = calendar.get(Calendar.YEAR);
        c_month = calendar.get(Calendar.MONTH) + 1;
        c_date = calendar.get(Calendar.DAY_OF_MONTH);
        day_of_week = calendar.get(Calendar.DAY_OF_WEEK);
        sa_year = calendar.get(Calendar.YEAR);
        sa_month = calendar.get(Calendar.MONTH) + 1;
        sa_date = calendar.get(Calendar.DAY_OF_MONTH);

//        sp_group = (Spinner) findViewById(R.id.sp_group);

        edtTitle = (EditText) findViewById(R.id.edt_dday_title);
        edtTitle.setHorizontallyScrolling(false);
        edtTitle.setMaxLines(3);
        edtContent = (EditText) findViewById(R.id.edt_content);

        arrayList_repeat = new ArrayList<String>();
        temp_list = new ArrayList<String>();
        arrayList_repeat.add(getResources().getString(R.string.norepeat));

        edtContent.setHorizontallyScrolling(false);
        edtContent.setMaxLines(3);
        ImageView detail_ic_ask = (ImageView) findViewById(R.id.detail_ic_ask);
        detail_ic_ask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                show_notify(getResources().getString(R.string.detail_ic_ask));
            }
        });
        tvAdmin = (TextView) findViewById(R.id.txt_admin);
        tvSharer = (TextView) findViewById(R.id.txt_sharer);
        tvPersonConcerned = (TextView) findViewById(R.id.txt_person_concerned);
        cb_sharer = (CheckBox) findViewById(R.id.cb_sharer);
//        rv_sharer = (RecyclerView) findViewById(R.id.rv_sharer);
//        rv_sharer2 = (RecyclerView) findViewById(R.id.rv_sharer2);
//        rv_sharer3 = (RecyclerView) findViewById(R.id.rv_sharer3);
//
//        mGridLayoutManager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.HORIZONTAL);
//        mGridLayoutManager2 = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.HORIZONTAL);
//        mGridLayoutManager3 = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.HORIZONTAL);
//        rv_sharer.setItemAnimator(new DefaultItemAnimator());
//        rv_sharer.setLayoutManager(mGridLayoutManager);
//        mList = new ArrayList<String>();
//        mList2 = new ArrayList<String>();
//        mList3 = new ArrayList<String>();
//
//        mAdapter = new RecyclerViewAdapter(mList, 0);
//        rv_sharer.setAdapter(mAdapter);
//        rv_sharer.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), rv_sharer, new ItemClickListener() {
//            @Override
//            public void onClick(View view, int position) {
//            }
//
//            @Override
//            public void onLongClick(View view, int position) {
//                mAdapter.updateTemp(1);
//                mAdapter.notifyDataSetChanged();
//            }
//        }));
//
//        rv_sharer2.setItemAnimator(new DefaultItemAnimator());
//        rv_sharer2.setLayoutManager(mGridLayoutManager2);
//        mAdapter2 = new RecyclerViewAdapter2(mList2, 0);
//        rv_sharer2.setAdapter(mAdapter2);
//        rv_sharer2.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), rv_sharer2, new ItemClickListener() {
//            @Override
//            public void onClick(View view, int position) {
//
//            }
//
//            @Override
//            public void onLongClick(View view, int position) {
//                mAdapter2.updateTemp(1);
//                mAdapter2.notifyDataSetChanged();
//            }
//        }));
//
//        rv_sharer3.setItemAnimator(new DefaultItemAnimator());
//        rv_sharer3.setLayoutManager(mGridLayoutManager3);
//        mAdapter3 = new RecyclerViewAdapter3(mList3, 0);
//        rv_sharer3.setAdapter(mAdapter3);
//        rv_sharer3.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), rv_sharer3, new ItemClickListener() {
//            @Override
//            public void onClick(View view, int position) {
//
//            }
//
//            @Override
//            public void onLongClick(View view, int position) {
//                mAdapter3.updateTemp(1);
//                mAdapter3.notifyDataSetChanged();
//            }
//        }));

        final ViewGroup viewGroup = (ViewGroup) ((ViewGroup) this.findViewById(android.R.id.content)).getChildAt(0);
        final RelativeLayout ln_more = (RelativeLayout) findViewById(R.id.ln_more);
        final LinearLayout ln_show_more = (LinearLayout) findViewById(R.id.ln_show_more);
        TextView tv_show_more = (TextView) findViewById(R.id.tv_show_more);
        tv_show_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ln_show_more.setVisibility(View.GONE);
                ln_more.setVisibility(View.VISIBLE);
            }
        });


        RelativeLayout sharerContainer = (RelativeLayout) findViewById(R.id.sharer_child_container);
        sharerContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startOrganizationActivity(resultList, Cons.SHARER_TYPE);
            }
        });


        RelativeLayout personConcernedContainer = (RelativeLayout) findViewById(R.id.
                person_concerned_child_container);
        personConcernedContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startOrganizationActivity2(resultList2, Cons.PERSON_CONCERNED_TYPE);

            }
        });


        LinearLayout adminContainer = (LinearLayout) findViewById(R.id.admin_container);
        adminContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startOrganizationActivity3(resultList3, Cons.ADMIN_TYPE);

            }
        });

        intent = getIntent();
        isEdit = intent.getBooleanExtra(Cons.KEY_EDIT, false);

        ConnectionUtils.getInstance().getgroup(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.mnubar_ic_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                back_press();
            }
        });

        special_lv_day = (ListView) findViewById(R.id.special_lv_day);
        arr_temp = new ArrayList<String>();
        arr_lv_special = new ArrayList<String>();
        if (arr_adapter_notifi != null)
            arr_adapter_notifi.clear();
        arr_adapter_notifi = new ArrayList<NotifyTime>();

        loadTabs();
        if (isEdit) {
            Update_Dday();
            init_view_tab();
        } else {
            Add_Dday();
            NotifyTime ob = new NotifyTime();
            ob.setNotificationType(0);
            ob.setNotificationTime("0900");
            arr_adapter_notifi.add(ob);
            init_view_tab();
        }
        if (HomeActivity.currentFragmentPos == 1) {
            rb_ddayplus.setChecked(true);
            click_day_plus();
        } else {
            rb_ddayminus.setChecked(true);
        }
    }

    public void notify_back() {
        AlertDialog.Builder adb = new AlertDialog.Builder(DDayActivity.this);
        adb.setTitle(getResources().getString(R.string.Dday));
        adb.setMessage(getResources().getString(R.string.edit_comment_notify));
        adb.setPositiveButton(getResources().getString(R.string.ok), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                restartjson();
                finish();
            }
        });
        adb.setNegativeButton(getResources().getString(R.string.cancel), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        adb.create().show();

    }

//    public void setsizelistview(ListView lv, ArrayList<String> arr_lv_special) {
//        View item = DDayActivity.specialAdapter.getView(0, null, lv);
//        item.measure(0, 0);
//        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, (int) (arr_lv_special.size() * item.getMeasuredHeight()));
//        lv.setLayoutParams(params);
//    }

    public void Start_First() {
        String date = year + getfulldate(month_special) + getfulldate(date_special);
        repeatOption_special = "{TypeName:\"CheckSpecificDday\",SpecificDay:\"" + date + "\",Lunar:1}";
    }

    @Override
    protected void onResume() {
        super.onResume();
        HomeActivity.checkAlarm = 0;
    }

    public void setRefreshSpecialListListerner(UpdateSpecialList callback) {
        updateSpecialList = callback;
    }

    private void startOrganizationActivity(ArrayList<PersonData> selectedList, int type) {
        HomeActivity.intShare = 1;
//        Intent i = new Intent(this, OrganizationActivity.class);
        Intent i = new Intent(this, NewOrganizationChart.class);
        i.putParcelableArrayListExtra(Cons.BUNDLE_LIST_PERSON, selectedList);
        i.putExtra(Cons.BUNDLE_TYPE, type);
        i.putExtra(Cons.BUNDLE_ORG_DISPLAY_SELECTED_ONLY, false);

        Log.d(TAG, "BUNDLE_LIST_PERSON:" + new Gson().toJson(selectedList));
        Log.d(TAG, "type:" + type);

        startActivityForResult(i, Cons.ORGANIZATION_TO_ACTIVITY);
    }

    private void startOrganizationActivity2(ArrayList<PersonData> selectedList, int type) {
        if (selectedList == null) {
            _Application.getInstance().getPrefManager().setChooseCorcerned(true);
        } else {
            if (selectedList.size() == 0) {
                _Application.getInstance().getPrefManager().setChooseCorcerned(true);
            } else {
                _Application.getInstance().getPrefManager().setChooseCorcerned(false);
            }
        }
        HomeActivity.intShare = 2;
//        Intent i = new Intent(this, OrganizationActivity.class);
        Intent i = new Intent(this, NewOrganizationChart.class);
        i.putParcelableArrayListExtra(Cons.BUNDLE_LIST_PERSON, selectedList);
        i.putExtra(Cons.BUNDLE_TYPE, type);
        i.putExtra(Cons.BUNDLE_ORG_DISPLAY_SELECTED_ONLY, false);

        Log.d(TAG, "BUNDLE_LIST_PERSON 2:" + new Gson().toJson(selectedList));
        Log.d(TAG, "type:" + type);

        startActivityForResult(i, Cons.ORGANIZATION_TO_ACTIVITY_2);
    }

    private void startOrganizationActivity3(ArrayList<PersonData> selectedList, int type) {
        HomeActivity.intShare = 3;
//        Intent i = new Intent(this, OrganizationActivity.class);
        Intent i = new Intent(this, NewOrganizationChart.class);
        i.putParcelableArrayListExtra(Cons.BUNDLE_LIST_PERSON, selectedList);
        i.putExtra(Cons.BUNDLE_TYPE, type);
        i.putExtra(Cons.BUNDLE_ORG_DISPLAY_SELECTED_ONLY, false);

        Log.d(TAG, "BUNDLE_LIST_PERSON 3:" + new Gson().toJson(selectedList));
        Log.d(TAG, "type:" + type);

        startActivityForResult(i, Cons.ORGANIZATION_TO_ACTIVITY_3);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        try {
            if (resultCode == Activity.RESULT_OK) {
                switch (requestCode) {
                    case Cons.ORGANIZATION_TO_ACTIVITY:
                        resultList = data.getExtras().getParcelableArrayList(Cons.BUNDLE_LIST_PERSON);
                        mType = data.getExtras().getInt(Cons.BUNDLE_TYPE);

                        Log.d(TAG, "ORGANIZATION_TO_ACTIVITY mType:" + mType);
                        Log.d(TAG, "resultList:" + new Gson().toJson(resultList));
                        handleSelectedOrganizationResult();
                        break;
                    case Cons.ORGANIZATION_TO_ACTIVITY_2:
                        resultList2 = data.getExtras().getParcelableArrayList(Cons.BUNDLE_LIST_PERSON);
                        mType = data.getExtras().getInt(Cons.BUNDLE_TYPE);

                        Log.d(TAG, "ORGANIZATION_TO_ACTIVITY mType:" + mType);
                        Log.d(TAG, "resultList:" + new Gson().toJson(resultList2));

                        handleSelectedOrganizationResult();
                        break;
                    case Cons.ORGANIZATION_TO_ACTIVITY_3:
                        resultList3 = data.getExtras().getParcelableArrayList(Cons.BUNDLE_LIST_PERSON);
                        mType = data.getExtras().getInt(Cons.BUNDLE_TYPE);

                        Log.d(TAG, "ORGANIZATION_TO_ACTIVITY mType:" + mType);
                        Log.d(TAG, "resultList:" + new Gson().toJson(resultList3));

                        handleSelectedOrganizationResult();
                        break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    String JSON_SHARER_TYPE = "", JSON_PERSON_CONCERNED_TYPE = "", JSON_ADMIN_TYPE = "";

    private void handleSelectedOrganizationResult() {
        Gson gson = new Gson();
        String json = "";
        switch (mType) {
            case Cons.SHARER_TYPE:
//                if (resultList.size() > 0) {
//                    mAdapter.updateTemp(0);
//                    mList.clear();
//                    tvSharer.setVisibility(View.GONE);
//                    rv_sharer.setVisibility(View.VISIBLE);
//                    for (int i = 0; i < resultList.size(); i++) {
//                        PersonData ob = resultList.get(i);
//                        mList.add(ob.getFullName());
//                    }
//                    mAdapter.notifyDataSetChanged();
//                } else {
//                    tvSharer.setVisibility(View.VISIBLE);
//                    rv_sharer.setVisibility(View.GONE);
//                }
                if (resultList.size() > 0)
                    ln_checkbox.setVisibility(View.VISIBLE);
                else ln_checkbox.setVisibility(View.GONE);
                tvSharer.setText(ListUtils.getListName(resultList));
                tvSharer.setSelected(true);
                json = gson.toJson(resultList);
                if (json.equals("[]")) json = "";
                JSON_SHARER_TYPE = json;
//                Log.e(TAG, JSON_SHARER_TYPE);
                break;
            case Cons.PERSON_CONCERNED_TYPE:
//                if (resultList2.size() > 0) {
//                    mAdapter2.updateTemp(0);
//                    mList2.clear();
//                    tvPersonConcerned.setVisibility(View.GONE);
//                    rv_sharer2.setVisibility(View.VISIBLE);
//                    for (int i = 0; i < resultList2.size(); i++) {
//                        PersonData ob = resultList2.get(i);
//                        mList2.add(ob.getFullName());
//                    }
//                    mAdapter2.notifyDataSetChanged();
//                } else {
//                    tvPersonConcerned.setVisibility(View.VISIBLE);
//                    rv_sharer2.setVisibility(View.GONE);
//                }
                if (resultList2.size() > 1) {
                    show_notify(getResources().getString(R.string.invalid));
                    tvPersonConcerned.setText("");
                } else {
                    if (resultList2.size() == 0) {
                        tv_myself.setVisibility(View.VISIBLE);
                    } else {
                        if (resultList2.get(0).getUserNo() == myId)
                            tv_myself.setVisibility(View.GONE);
                        else tv_myself.setVisibility(View.VISIBLE);
                    }
                    tvPersonConcerned.setText(ListUtils.getListName(resultList2));
                    tvPersonConcerned.setSelected(true);
                    json = gson.toJson(resultList2);
                    if (json.equals("[]")) json = "";
                    JSON_PERSON_CONCERNED_TYPE = json;

                }
                break;
            case Cons.ADMIN_TYPE:
//                if (resultList3.size() > 0) {
//                    mAdapter3.updateTemp(0);
//                    mList3.clear();
//                    tvAdmin.setVisibility(View.GONE);
//                    rv_sharer3.setVisibility(View.VISIBLE);
//                    for (int i = 0; i < resultList3.size(); i++) {
//                        PersonData ob = resultList3.get(i);
//                        mList3.add(ob.getFullName());
//                    }
//                    mAdapter3.notifyDataSetChanged();
//                } else {
//                    tvAdmin.setVisibility(View.VISIBLE);
//                    rv_sharer3.setVisibility(View.GONE);
//                }

                tvAdmin.setText(ListUtils.getListName(resultList3));
                tvAdmin.setSelected(true);
                json = gson.toJson(resultList3);
                if (json.equals("[]")) json = "";
                JSON_ADMIN_TYPE = json;
                break;
        }
    }

    boolean flag_title = false;

    public void setMarginBottomListNotify(int dimens) {
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) layout_notify.getLayoutParams();
        params.bottomMargin = dimens;
        layout_notify.setLayoutParams(params);
    }

    public void click_day_plus() {
        lv_notify.setVisibility(View.GONE);
        sp_ddaytype_int = 2;
        dday_plus();
        View item = specialAdapter.getView(0, null, DDayActivity.special_lv_day);
        item.measure(0, 0);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, (int) (arr_lv_special.size() * item.getMeasuredHeight()));
        special_lv_day.setLayoutParams(params);
        setTabWhenPLusCheck();
        _DisableCheckBox();
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        switch (id) {
            case R.id.rb_ddayminus:
                lv_notify.setVisibility(View.VISIBLE);
                sp_ddaytype_int = 0;
                dday_minus();
                break;
            case R.id.rb_ddayplus:
                click_day_plus();
                break;
            case R.id.img_plus:
                if (flag_title) {
                    img_plus.setImageResource(R.drawable.ic_arrow_drop_down);
                    ln_content.setVisibility(View.GONE);
                    flag_title = false;
                } else {
                    img_plus.setImageResource(R.drawable.ic_arrow_drop_up);
                    ln_content.setVisibility(View.VISIBLE);
                    flag_title = true;
                }
                break;
            case R.id.group_parent_container:
                dialog_group();
                break;
            case R.id.tv_myself:
                PreferenceUtilities prefUtils = _Application.getInstance().getPreferenceUtilities();
                resultList2 = new ArrayList<PersonData>();
                PersonData ob = new PersonData();
                String name = prefUtils.getUserName().replace("\"", "");
                ob.setDepartNo(prefUtils.getDepartNo());
                ob.setUserNo(prefUtils.getId());
                ob.setFullName(name);
                ob.setNameEn(name);
                resultList2.add(ob);
                JSON_PERSON_CONCERNED_TYPE = new Gson().toJson(resultList2);
                tvPersonConcerned.setTextColor(Color.BLACK);
                tvPersonConcerned.setText(name);
                tv_myself.setVisibility(View.GONE);
                break;
            default:
                break;
        }
    }

    public void dday_minus() {
        if (temp == 1) {
            Start_First();
            specialAdapter.notifyDataSetChanged();
            temp = 0;
        } else {

        }
        typeNo = 2;
        TypeName = "CheckSpecificDday";
        SpecificDay = "SpecificDay";
        _EnableCheckBox();
    }

    public static int sa_year, sa_month, sa_date;

    public void dday_plus() {
        temp = 1;
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        if (arr_lv_special.size() > 0) arr_lv_special.clear();
        arr_lv_special.add(sa_year + "-" + getfulldate(sa_month) + "-" + getfulldate(sa_date));
        specialAdapter.notifyDataSetChanged();
        String date = arr_lv_special.get(0).replace("-", "");
        int a = 1;
        if (special_rb1.isChecked()) a = 1;
        else if (special_rb2.isChecked()) a = 0;
        else a = -1;
        repeatOption_special = "{TypeName:\"CheckDPlusDay\",StartDate:\"" + date + "\",Lunar:" + a + "}";

        typeNo = 3;
        TypeName = "CheckDPlusDay";
        SpecificDay = "StartDate";
        repeatOption_daily = "";
        repeatOption_week = "";
        repeatOption_month = "";
        repeatOption_year = "";
        v1_cb1.setChecked(true);
        v2_cb1.setChecked(false);
        v3_cb1.setChecked(false);
        v4_cb1.setChecked(false);
        v5_cb1.setChecked(false);
    }

    public int temp = 0;
//    String tv_temp_repeat = "";

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_dday, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.menu_ok) {
            if (sp_ddaytype_int == 0) {
                typeNo = 2;
            } else if (sp_ddaytype_int == 1) {
                typeNo = 2;
            } else if (sp_ddaytype_int == 2) {
                typeNo = 3;
            }
            if (sp_ddaytype_int == 2) {
                TypeName = "CheckDPlusDay";
                SpecificDay = "StartDate";
            } else {
                TypeName = "CheckSpecificDday";
                SpecificDay = "SpecificDay";
            }
            final String title = edtTitle.getText().toString().trim();
            final String content = edtContent.getText().toString().trim();
            if (TextUtils.isEmpty(title)) {
                show_notify(getResources().getString(R.string.inputpls));
            } else {
                if (!v1_cb1.isChecked() && !v2_cb1.isChecked() && !v3_cb1.isChecked() && !v4_cb1.isChecked() && !v5_cb1.isChecked()) {
                    show_notify(getString(R.string.select));
                } else {
                    if (isEdit) {
//                        updateDday(groupNo, title, content, ddayId);
                    } else {
                        insertDday(groupNo, title, content);
                    }
                }
            }
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void gotohome() {
//        Intent intent = new Intent(DetailViewActivity.this, HomeActivity.class);
//        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
//        startActivity(intent);
        SharedPreferences pre = getSharedPreferences(Cons.prefname, MODE_PRIVATE);
        SharedPreferences.Editor editor = pre.edit();
        editor.putBoolean(Cons.UPDATE_SUCCESS, true);
        editor.commit();
        finish();
    }

    @Override
    public void onInsertSuccess() {
        if (rb_ddayminus.isChecked()) {
            HomeActivity.currentFragmentPos = 0;
        } else {
            HomeActivity.currentFragmentPos = 1;
        }
        gotohome();

    }

    @Override
    public void onUpdateSuccess() {
        gotohome();
    }

    public static String repeatOption_special = "", repeatOption_daily = "", repeatOption_week = "", repeatOption_month = "", repeatOption_year = "";

    private void insertDday(int groupNo, String title, String content) {
        int a = 1;
        if (special_rb1.isChecked()) a = 1;
        else if (special_rb2.isChecked()) a = 0;
        else a = -1;
        repeatOption_special = "";
        if (arr_lv_special.size() == 1) {
            repeatOption_special = "{TypeName:\"" + TypeName + "\"," + SpecificDay + ":\"" +
                    arr_lv_special.get(0).replace("-", "") + "\",Lunar:" + a + "}";
        } else {
            for (int i = 0; i < arr_lv_special.size(); i++) {
                repeatOption_special += "{TypeName:\"" + TypeName + "\"," + SpecificDay + ":\"" +
                        arr_lv_special.get(i).replace("-", "") + "\",Lunar:" + a + "},";
            }
            repeatOption_special = repeatOption_special.substring(0, repeatOption_special.length() - 1);
        }

        String repeatOption = "[";
        ArrayList<String> arrayList = new ArrayList<String>();
        if (v1_cb1.isChecked())
            arrayList.add(repeatOption_special);
        if (v2_cb1.isChecked()) {
            getJsonDaily();
            arrayList.add(repeatOption_daily);
        }
        if (v3_cb1.isChecked()) {
            getJsonWeek();
            arrayList.add(repeatOption_week);
        }
        if (v4_cb1.isChecked()) {
            getJsonMonth();
            arrayList.add(repeatOption_month);
        }
        if (v5_cb1.isChecked()) {
            getJsonAnnnually();
            arrayList.add(repeatOption_year);
        }
        if (arrayList.size() == 1) {
            repeatOption += arrayList.get(0);
            repeatOption += "]";
        } else {
            for (int i = 0; i < arrayList.size(); i++) {
                repeatOption += arrayList.get(i) + ",";
            }
            repeatOption = repeatOption.substring(0, repeatOption.length() - 1) + "]";
        }

        Log.d(TAG, repeatOption);
        if (cb_sharer.isChecked())
            nodelete = false;
        else
            nodelete = true;
        if (rb_ddayplus.isChecked()) {
            arr_adapter_notifi.clear();
            NotifyTime ob = new NotifyTime();
            ob.setNotificationTime("0900");
            ob.setDayNo(0);
            ob.setNotificationType(0);
            ob.setNotiNo(0);
            arr_adapter_notifi.add(ob);
        }
        if (HomeActivity.checkAlarm == 0) {
        } else {
            for (int i = 0; i < arr_adapter_notifi.size(); i++) {
                arr_adapter_notifi.get(i).setNotificationType(arr_adapter_notifi.get(i).getNotificationType() + 1);
            }
        }
        if (rb_ddayplus.isChecked())
            arr_adapter_notifi.clear();
        Log.d(TAG, "repeatOption:" + repeatOption);
        Log.d(TAG, "arr_adapter_notifi:" + new Gson().toJson(arr_adapter_notifi));
        ConnectionUtils.getInstance().insert_v2(groupNo, typeNo, repeatOption, title, content, JSON_SHARER_TYPE,
                JSON_PERSON_CONCERNED_TYPE, JSON_ADMIN_TYPE, this, arr_adapter_notifi, nodelete);

    }

    boolean nodelete = false;

    public void show_notify(String str) {
        AlertDialog.Builder adb = new AlertDialog.Builder(this);
        adb.setTitle(getResources().getString(R.string.Dday));
        adb.setMessage(str);
        adb.setPositiveButton(getResources().getString(R.string.ok), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        adb.create().show();
    }

    private void updateDday(int groupNo, String title, String content, long ddayId) {
        int a = 1;
        if (special_rb1.isChecked()) a = 1;
        else if (special_rb2.isChecked()) a = 0;
        else a = -1;
        repeatOption_special = "";
        if (arr_lv_special.size() == 1) {
            repeatOption_special = "{TypeName:\"" + TypeName + "\"," + SpecificDay + ":\"" +
                    arr_lv_special.get(0).replace("-", "") + "\",Lunar:" + a + "}";
        } else {
            for (int i = 0; i < arr_lv_special.size(); i++) {
                repeatOption_special += "{TypeName:\"" + TypeName + "\"," + SpecificDay + ":\"" +
                        arr_lv_special.get(i).replace("-", "") + "\",Lunar:" + a + "},";
            }
            repeatOption_special = repeatOption_special.substring(0, repeatOption_special.length() - 1);
        }

        String repeatOption = "[";
        ArrayList<String> arrayList = new ArrayList<String>();
        if (v1_cb1.isChecked())
            arrayList.add(repeatOption_special);
        if (v2_cb1.isChecked()) {
            getJsonDaily();
            arrayList.add(repeatOption_daily);
        }
        if (v3_cb1.isChecked()) {
            getJsonWeek();
            arrayList.add(repeatOption_week);
        }
        if (v4_cb1.isChecked()) {
            getJsonMonth();
            arrayList.add(repeatOption_month);
        }
        if (v5_cb1.isChecked()) {
            getJsonAnnnually();
            arrayList.add(repeatOption_year);
        }
        if (arrayList.size() == 1) {
            repeatOption += arrayList.get(0);
            repeatOption += "]";
        } else {
            for (int i = 0; i < arrayList.size(); i++) {
                repeatOption += arrayList.get(i) + ",";
            }
            repeatOption = repeatOption.substring(0, repeatOption.length() - 1) + "]";
        }
        Log.d(TAG, repeatOption);
//        ConnectionUtils.getInstance().update_v2(groupNo, ddayId, typeNo, repeatOption, title, content, this, JSON_SHARER_TYPE,
//                JSON_PERSON_CONCERNED_TYPE, JSON_ADMIN_TYPE, notiNo, arr_adapter_notify);
    }

    public void restartjson() {
        DDayActivity.sp_ddaytype_int = 0;
        DDayActivity.repeatOption_special = "";
        DDayActivity.repeatOption_daily = "";
        DDayActivity.repeatOption_week = "";
        DDayActivity.repeatOption_month = "";
        DDayActivity.repeatOption_year = "";
    }

    public boolean compare_array_list(ArrayList<String> str1, ArrayList<String> str2) {

        if (str1.size() != str2.size()) {
            return false;
        } else {
            for (int i = 0; i < str1.size(); i++) {
                if (!str1.get(i).equals(str2.get(i)))
                    return false;
            }
        }
        return true;
    }

    public void back_press() {
        if (isEdit) {
            if (!title_temp.equals(edtTitle.getText().toString().trim()) || !content_temp.equals(edtContent.getText().toString().trim()) ||
                    !str1.equals(repeatOption_special) || !str2.equals(repeatOption_daily) || !str3.equals(repeatOption_week) ||
                    !str4.equals(repeatOption_month) || !str5.equals(repeatOption_year) || compare_array_list(arr_temp, arr_lv_special) == false) {
                notify_back();
            } else {
                restartjson();
                finish();
            }
        } else {
            restartjson();
            finish();
        }
    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
//        restartjson();
//        finish();
        back_press();
    }

//    ArrayAdapter<String> sp_group_adapter = null;

    public void dialog_group() {
        final Dialog dialog = new Dialog(this);
        dialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setTitle(getResources().getString(R.string.selectgroup));
        dialog.setContentView(R.layout.dialog_group);
        ListView list_gr = (ListView) dialog.findViewById(R.id.list_gr);
        ArrayAdapter adapter = new ArrayAdapter<String>(this, R.layout.custom_tv_dialog_gr, sp_Name);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        list_gr.setAdapter(adapter);
        list_gr.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                groupNo = sp_GroupNo.get(position);
                tv_group.setText(sp_Name.get(position));
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    @Override
    public void onGetGroupSuccess(List<ObjectGetGroups> listDtos) {
        if (listDtos.size() > 0) {
            sp_GroupNo.clear();
            sp_Name.clear();
            for (int i = 0; i < listDtos.size(); i++) {
                sp_GroupNo.add(listDtos.get(i).getGroupNo());
                if (listDtos.get(i).getName().equals(""))
                    sp_Name.add(getResources().getString(R.string.Undefined));
                else
                    sp_Name.add(listDtos.get(i).getName());
            }
//            sp_group_adapter = new ArrayAdapter<String>(this, R.layout.custom_text_size, sp_Name);
//            sp_group_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//            sp_group.setAdapter(sp_group_adapter);
//            sp_group.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//                @Override
//                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                    groupNo = sp_GroupNo.get(position);
//                }
//
//                @Override
//                public void onNothingSelected(AdapterView<?> parent) {
//
//                }
//            });
//            if (isEdit) {
//                if (!detailModel.getGroupName().equalsIgnoreCase(Cons.Undentified)) {
//                    for (int i = 0; i < listDtos.size(); i++) {
//                        if (detailModel.getGroupName().equalsIgnoreCase(listDtos.get(i).getName())) {
////                            sp_group.setSelection(i);
//                            groupNo = listDtos.get(i).getGroupNo();
//                        }
//                    }
//
//                }
//            }
        }
    }

    public void Add_Dday() {
        Start_First();
        special_rb1.setChecked(true);

    }


    long notiNo = 0;

    public void Update_Dday() {
        setTitle(getResources().getString(R.string.update_dday));
        detailModel = intent.getParcelableExtra(Cons.KEY_DETAIL_MODEL);

        if (detailModel.getDdayType().equalsIgnoreCase("Completion D-Day")) {
            DDayActivity.sp_ddaytype_int = 1;
            rb_ddayminus.setChecked(true);
        } else if (detailModel.getDdayType().equalsIgnoreCase("D+Day")) {
            DDayActivity.sp_ddaytype_int = 2;

            rb_ddayplus.setChecked(true);
            temp = 1;
        } else {
            DDayActivity.sp_ddaytype_int = 0;
            rb_ddayminus.setChecked(true);
        }
        ddayId = intent.getLongExtra(Cons.KEY_DDAY_ID, 0);
        String jsonC = intent.getStringExtra(Cons.KEY_NOTIFI_TIME);
//        Log.e(TAG,jsonC);
        if (jsonC.length() > 0) {
            Gson gson = new Gson();
            Type type = new TypeToken<List<NotifyTime>>() {
            }.getType();
            arr_adapter_notifi = gson.fromJson(jsonC, type);
            for (int i = 0; i < arr_adapter_notifi.size(); i++) {
                NotifyTime ob = arr_adapter_notifi.get(i);
                if (ob.getNotificationType() != 2) {
                    String time = ob.getNotificationTime();
                    time = time.split(":")[0] + time.split(":")[1];
                    ob.setNotificationTime(time);
                    int a = ob.getNotificationType() + 1;
                    ob.setNotificationType(a);
                    arr_adapter_notifi.set(i, ob);
                }
            }
        } else {
            NotifyTime ob = new NotifyTime();
            ob.setNotificationType(0);
            ob.setNotificationTime("0900");
            arr_adapter_notifi.add(ob);
        }

        String json = detailModel.getStringRepeatOptions();
        json = json.replaceAll("(^\\s+|\\s+$)", "");
        String str[] = json.substring(1, detailModel.getStringRepeatOptions().length() - 1).split("\\},\\{");
        ArrayList<String> arrayList = new ArrayList<String>();
        if (str.length == 1) {
            arrayList.add(str[0]);
        } else if (str.length > 1) {
            for (int i = 0; i < str.length; i++) {
                if (i == 0) {
                    arrayList.add(str[i] + "}");
                } else if (i == str.length - 1) {
                    arrayList.add("{" + str[i]);
                } else {
                    arrayList.add("{" + str[i] + "}");
                }
            }
        }
        for (int i = 0; i < arrayList.size(); i++) {
            if (arrayList.get(i).contains("CheckSpecificDday") || arrayList.get(i).contains("CheckDPlusDay"))
                repeatOption_special += arrayList.get(i) + ",";
            if (arrayList.get(i).contains("CheckEveryDayDday"))
                repeatOption_daily += arrayList.get(i) + ",";
            if (arrayList.get(i).contains("CheckEveryDdayOfWeek"))
                repeatOption_week += arrayList.get(i) + ",";
            if (arrayList.get(i).contains("CheckEveryMonthWeekDday") || arrayList.get(i).contains("CheckEveryMonthSpecificDday"))
                repeatOption_month += arrayList.get(i) + ",";
            if (arrayList.get(i).contains("CheckEveryYearDday") || arrayList.get(i).contains("CheckEveryYearWeek"))
                repeatOption_year += arrayList.get(i) + ",";
        }
        if (repeatOption_special.length() > 0) {
            tab.setCurrentTab(0);
            v1_cb1.setChecked(true);
            repeatOption_special = repeatOption_special.substring(0, repeatOption_special.length() - 1);
        } else {
            special_rb1.setChecked(true);
        }
        if (repeatOption_daily.length() > 0) {
            tab.setCurrentTab(1);
            v2_cb1.setChecked(true);
            repeatOption_daily = repeatOption_daily.substring(0, repeatOption_daily.length() - 1);
        }
        if (repeatOption_week.length() > 0) {
            tab.setCurrentTab(2);
            v3_cb1.setChecked(true);
            repeatOption_week = repeatOption_week.substring(0, repeatOption_week.length() - 1);
        }
        if (repeatOption_month.length() > 0) {
            tab.setCurrentTab(3);
            v4_cb1.setChecked(true);
            repeatOption_month = repeatOption_month.substring(0, repeatOption_month.length() - 1);
        }
        if (repeatOption_year.length() > 0) {
            tab.setCurrentTab(4);
            v5_cb1.setChecked(true);
            repeatOption_year = repeatOption_year.substring(0, repeatOption_year.length() - 1);
        }

        // set title
        edtTitle.setText(detailModel.getTitle());
        title_temp = edtTitle.getText().toString().trim();
        if (!detailModel.getContent().equalsIgnoreCase("anyType{}"))
            edtContent.setText(detailModel.getContent());
        content_temp = edtContent.getText().toString().trim();
        ArrayList<Employee> shareList = detailModel.getSharersList();
        if (shareList.size() > 0) {
            String title = "";
            resultList = new ArrayList<PersonData>();
            for (int i = 0; i < shareList.size(); i++) {
                String name = shareList.get(i).getUserName();
                if (!title.contains(name)) {
                    if (i == shareList.size() - 1) {
                        title += name;
                    } else {
                        title += name + ",";
                    }
                    PersonData personData = new PersonData();
                    personData.setFullName(shareList.get(i).getUserName());
                    personData.setDepartNo(shareList.get(i).getDepartNo());
                    personData.setUserNo(shareList.get(i).getUserNo());
                    resultList.add(personData);
                }
            }
            JSON_SHARER_TYPE = new Gson().toJson(resultList);
            tvSharer.setText(title);
            tvSharer.setSelected(true);
        }

        int directorNo = detailModel.getDirectorNo();
        if (directorNo != 0) {
            int DepartNoOfDirector = detailModel.getDirectorDepartNo();
            String directorName = detailModel.getDirectorUsername();
            tvPersonConcerned.setText(directorName);
            resultList2 = new ArrayList<PersonData>();
            PersonData personData = new PersonData();
            personData.setFullName(directorName);
            personData.setDepartNo(DepartNoOfDirector);
            personData.setUserNo(directorNo);
            resultList2.add(personData);
            JSON_PERSON_CONCERNED_TYPE = new Gson().toJson(resultList2);
        }

        ArrayList<Employee> managerList = detailModel.getManagersList();
        if (managerList.size() != 0) {
            String title_managerList = "";
            resultList3 = new ArrayList<PersonData>();
            for (int i = 0; i < managerList.size(); i++) {
                String name = managerList.get(i).getUserName();
                if (!title_managerList.contains(name)) {
                    if (i == managerList.size() - 1) {
                        title_managerList += name;
                    } else {
                        title_managerList += name + ",";
                    }
                    PersonData personData = new PersonData();
                    personData.setFullName(managerList.get(i).getUserName());
                    personData.setDepartNo(managerList.get(i).getDepartNo());
                    personData.setUserNo(managerList.get(i).getUserNo());
                    resultList3.add(personData);
                }
            }
            JSON_ADMIN_TYPE = new Gson().toJson(resultList3);
            tvAdmin.setText(title_managerList);
            tvAdmin.setSelected(true);
        }
        str1 = repeatOption_special;
        str2 = repeatOption_daily;
        str3 = repeatOption_week;
        str4 = repeatOption_month;
        str5 = repeatOption_year;
    }


    public static SpecialAdapter specialAdapter;
    public static ArrayList<String> arr_lv_special;
    ArrayList<String> arr_temp;
    public static int month_special, date_special, year;
    int Lunar = 0;
    String TypeName = "CheckSpecificDday";
    String SpecificDay = "SpecificDay";

    public void dialog_special() {
        if (repeatOption_special.length() == 0) {
            arr_lv_special.add(year + "-" + getfulldate(month_special) + "-" + getfulldate(date_special));
        } else {
            if (repeatOption_special.contains(Cons.CheckDPlusDay)) {
                Object_CheckDPlusDay ob = new Gson().fromJson(repeatOption_special, Object_CheckDPlusDay.class);
                int Lunar = ob.getLunar();
                if (Lunar == 0) special_rb2.setChecked(true);
                else if (Lunar == -1) special_rb3.setChecked(true);
                else special_rb1.setChecked(true);
                arr_lv_special.add(formmat_date(ob.getStartDate()));
            } else {
                String temp = "[" + repeatOption_special + "]";
                Type type = new TypeToken<ArrayList<Object_CheckSpecificDday>>() {
                }.getType();
                ArrayList<Object_CheckSpecificDday> arrayList_s = new Gson().fromJson(temp, type);
                int Lunar = arrayList_s.get(0).getLunar();
                if (Lunar == 0) special_rb2.setChecked(true);
                else if (Lunar == -1) special_rb3.setChecked(true);
                else special_rb1.setChecked(true);
                for (int i = 0; i < arrayList_s.size(); i++) {
                    arr_lv_special.add(formmat_date(arrayList_s.get(i).getSpecificDay()));
                }
            }
        }
        arr_temp = new ArrayList<String>(arr_lv_special);
        specialAdapter = new SpecialAdapter(this, arr_lv_special);
        special_lv_day.setAdapter(specialAdapter);
        View item = DDayActivity.specialAdapter.getView(0, null, DDayActivity.special_lv_day);
        item.measure(0, 0);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, (int) (arr_lv_special.size() * item.getMeasuredHeight()));
        DDayActivity.special_lv_day.setLayoutParams(params);
    }

    String m_temp = "", d_temp = "";
    int Interval = 1, Interval_w = 1, Interval_m = 1, Interval_y = 1;
    String EndDate = "", StartOption = "D";
    int hld[] = {1, 2, 3, 4};
    int HolidayCondition = 1, HolidayCondition_w = 1, HolidayCondition_m = 1, HolidayCondition_y = 1;

    public void dialog_day() {
        int p = 0, so = 0, hd = 0;
        View view_start = (View) findViewById(R.id.view_start);
        View view_end = (View) findViewById(R.id.view_end);
        dl_start = (TextView) view_start.findViewById(R.id.txt_start_day_chose);
        dl_end = (TextView) view_end.findViewById(R.id.txt_start_day_chose);
        dl_loop = (CheckBox) findViewById(R.id.cb_infiniteloop);
        final String ngay = "" + c_year + "-" + Cons.getfulldate(c_month) + "-" + Cons.getfulldate(c_date);
        dl_end.setText(date_finish);
        dl_end.setTextColor(Color.parseColor("#FFFFFF"));
        dl_loop.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    dl_end.setText(date_finish);
                    dl_end.setTextColor(Color.parseColor("#FFFFFF"));
                } else {
                    dl_end.setTextColor(Color.parseColor("#000000"));
                    dl_end.setText(dl_start.getText().toString());
                }
            }
        });
        view_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowDateDialog(dl_start, dl_end, dl_loop.isChecked());
            }
        });
        view_end.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!dl_loop.isChecked())
                    ShowDateDialogEnd(dl_end, dl_start);
            }
        });
        if (repeatOption_daily.length() > 0) {
            Object_CheckEveryDayDday ob = new Gson().fromJson(repeatOption_daily, Object_CheckEveryDayDday.class);
            int inter = ob.getInterval();
            if (inter == 1000) p = arrayList_frequency.length - 1;
            else if (inter == 100) p = arrayList_frequency.length - 2;
            else p = ob.getInterval() - 1;
            String StartOption = ob.getStartOption();
            if (StartOption.equalsIgnoreCase("Y")) so = 1;
            else if (StartOption.equalsIgnoreCase("M")) so = 2;
            else if (StartOption.equalsIgnoreCase("W")) so = 3;
            hd = ob.getHolidayCondition() - 1;
            dl_start.setText(formmat_date(ob.getStartDate()));
            if (ob.getEndDate().length() > 0) {
                dl_loop.setChecked(false);
                dl_end.setText(formmat_date(ob.getEndDate()));
                dl_end.setTextColor(Color.parseColor("#000000"));
            }
        } else {
            dl_start.setText(ngay);
        }
        View view1 = (View) findViewById(R.id.view1);
        TextView tv_frequency = (TextView) view1.findViewById(R.id.tv_item);
        Spinner sp_frequency = (Spinner) view1.findViewById(R.id.sp_item);
        tv_frequency.setText(getResources().getString(R.string.frequency));
        ArrayAdapter adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, arrayList_frequency);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_frequency.setAdapter(adapter);
        sp_frequency.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == arrayList_frequency.length - 1)
                    Interval = 1000;
                else if (position == arrayList_frequency.length - 2)
                    Interval = 100;
                else Interval = position + 1;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        sp_frequency.setSelection(p);
        View view2 = (View) findViewById(R.id.view2);
        TextView tv_option = (TextView) view2.findViewById(R.id.tv_item);
        Spinner sp_option = (Spinner) view2.findViewById(R.id.sp_item);
        tv_option.setText(getResources().getString(R.string.option));

        ArrayAdapter adapter_option = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, arrayList_option);
        adapter_option.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_option.setAdapter(adapter_option);
        final String options[] = {"D", "Y", "M", "W"};
        sp_option.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                StartOption = options[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        sp_option.setSelection(so);
        View view3 = (View) findViewById(R.id.view3);
        TextView tv_holiday = (TextView) view3.findViewById(R.id.tv_item);
        Spinner sp_holiday = (Spinner) view3.findViewById(R.id.sp_item);
        tv_holiday.setText(getResources().getString(R.string.holiday));

        ArrayAdapter adapter_holiday = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, arrayList_holiday);
        adapter_holiday.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_holiday.setAdapter(adapter_holiday);

        sp_holiday.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                HolidayCondition = hld[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        sp_holiday.setSelection(hd);
    }

    CheckBox cb_mon, cb_tue, cb_wed, cb_thu, cb_fri, cb_sat, cb_sun;

    public void dialog_week() {
        View time_week = (View) findViewById(R.id.time_week);
        View view_start = (View) time_week.findViewById(R.id.view_start);
        View view_end = (View) time_week.findViewById(R.id.view_end);

        w_start = (TextView) view_start.findViewById(R.id.txt_start_day_chose);
        w_end = (TextView) view_end.findViewById(R.id.txt_start_day_chose);
        w_loop = (CheckBox) time_week.findViewById(R.id.cb_infiniteloop);
        if (c_month < 10) m_temp = "0" + c_month;
        else m_temp = "" + c_month;
        if (c_date < 10) d_temp = "0" + c_date;
        else d_temp = "" + c_date;
        final String ngay = "" + c_year + "-" + m_temp + "-" + d_temp;
        w_end.setText(date_finish);
        w_end.setTextColor(Color.parseColor("#FFFFFF"));
        w_loop.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    w_end.setText(date_finish);
                    w_end.setTextColor(Color.parseColor("#FFFFFF"));
                } else {
                    w_end.setTextColor(Color.parseColor("#000000"));
                    w_end.setText(w_start.getText().toString());
                }
            }
        });
        view_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowDateDialog(w_start, w_end, w_loop.isChecked());
            }
        });
        view_end.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!w_loop.isChecked())
                    ShowDateDialogEnd(w_end, w_start);
            }
        });
        cb_mon = (CheckBox) findViewById(R.id.cb_mon);
        cb_tue = (CheckBox) findViewById(R.id.cb_tue);
        cb_wed = (CheckBox) findViewById(R.id.cb_wed);
        cb_thu = (CheckBox) findViewById(R.id.cb_thu);
        cb_fri = (CheckBox) findViewById(R.id.cb_fri);
        cb_sat = (CheckBox) findViewById(R.id.cb_sat);
        cb_sun = (CheckBox) findViewById(R.id.cb_sun);
        int p = 0, hd = 0;
        if (repeatOption_week.length() > 0) {
            Object_CheckEveryDdayOfWeek ob = new Gson().fromJson(repeatOption_week, Object_CheckEveryDdayOfWeek.class);
            p = ob.getInterval() - 1;
            hd = ob.getHolidayCondition() - 1;
            w_start.setText(formmat_date(ob.getStartDate()));
            if (ob.getSpecificDayOfWeek().contains("0")) cb_sun.setChecked(true);
            if (ob.getSpecificDayOfWeek().contains("1")) cb_mon.setChecked(true);
            if (ob.getSpecificDayOfWeek().contains("2")) cb_tue.setChecked(true);
            if (ob.getSpecificDayOfWeek().contains("3")) cb_wed.setChecked(true);
            if (ob.getSpecificDayOfWeek().contains("4")) cb_thu.setChecked(true);
            if (ob.getSpecificDayOfWeek().contains("5")) cb_fri.setChecked(true);
            if (ob.getSpecificDayOfWeek().contains("6")) cb_sat.setChecked(true);
            if (ob.getEndDate().length() > 0) {
                w_loop.setChecked(false);
                w_end.setText(formmat_date(ob.getEndDate()));
                w_end.setTextColor(Color.parseColor("#000000"));
            }
        } else {
            w_start.setText(ngay);
            if (day_of_week == 1) cb_sun.setChecked(true);
            else if (day_of_week == 2) cb_mon.setChecked(true);
            else if (day_of_week == 3) cb_tue.setChecked(true);
            else if (day_of_week == 4) cb_wed.setChecked(true);
            else if (day_of_week == 5) cb_thu.setChecked(true);
            else if (day_of_week == 6) cb_fri.setChecked(true);
            else if (day_of_week == 7) cb_sat.setChecked(true);
        }

        View view1_week = (View) findViewById(R.id.view1_week);
        TextView tv_frequency = (TextView) view1_week.findViewById(R.id.tv_item);
        Spinner sp_frequency = (Spinner) view1_week.findViewById(R.id.sp_item);
        tv_frequency.setText(getResources().getString(R.string.frequency));
        final String arrayList_frequency[] = getResources().getStringArray(R.array.weekly_interval_array);
        ArrayAdapter adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, arrayList_frequency);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_frequency.setAdapter(adapter);
        sp_frequency.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Interval_w = position + 1;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        sp_frequency.setSelection(p);
        View view3_week = (View) findViewById(R.id.view2_week);
        TextView tv_holiday = (TextView) view3_week.findViewById(R.id.tv_item);
        Spinner sp_holiday = (Spinner) view3_week.findViewById(R.id.sp_item);
        tv_holiday.setText(getResources().getString(R.string.holiday));
        String arrayList_holiday[] = getResources().getStringArray(R.array.daily_holiday);
        ArrayAdapter adapter_holiday = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, arrayList_holiday);
        adapter_holiday.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_holiday.setAdapter(adapter_holiday);
        sp_holiday.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                HolidayCondition_w = hld[position];

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        sp_holiday.setSelection(hd);
    }

    public static int year_month;
    public static int month_month;
    public static int date_month;
    public static int date_of_week_month;

    public void dialog_month() {
        ListView lv_month = (ListView) findViewById(R.id.lv_month);
        arrayList_month = new ArrayList<ObjectAddMonth>();
        Calendar calendar = Calendar.getInstance();
        year_month = calendar.get(Calendar.YEAR);
        month_month = calendar.get(Calendar.MONTH);
        date_month = calendar.get(Calendar.DAY_OF_MONTH) - 1;
        date_of_week_month = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        View time_month = (View) findViewById(R.id.time_month);
        View view_start = (View) time_month.findViewById(R.id.view_start);
        View view_end = (View) time_month.findViewById(R.id.view_end);
        m_start = (TextView) view_start.findViewById(R.id.txt_start_day_chose);
        m_end = (TextView) view_end.findViewById(R.id.txt_start_day_chose);
        m_loop = (CheckBox) time_month.findViewById(R.id.cb_infiniteloop);
        if (c_month < 10) m_temp = "0" + c_month;
        else m_temp = "" + c_month;
        if (c_date < 10) d_temp = "0" + c_date;
        else d_temp = "" + c_date;
        final String ngay = "" + c_year + "-" + m_temp + "-" + d_temp;
        m_end.setText(date_finish);
        m_end.setTextColor(Color.parseColor("#FFFFFF"));
        m_loop.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    m_end.setText(date_finish);
                    m_end.setTextColor(Color.parseColor("#FFFFFF"));
                } else {
                    m_end.setTextColor(Color.parseColor("#000000"));
                    m_end.setText(m_start.getText().toString());
                }
            }
        });
        view_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowDateDialog(m_start, m_end, m_loop.isChecked());
            }
        });
        view_end.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!m_loop.isChecked())
                    ShowDateDialogEnd(m_end, m_start);
            }
        });
        int p = 0, hd = 0;
        week_of_month = calendar.get(Calendar.WEEK_OF_MONTH);
        if (repeatOption_month.length() > 0) {
            String jsonString = "[" + repeatOption_month + "]";
            Gson gson = new Gson();
            Type type = new TypeToken<ArrayList<Object_CheckEveryMonthSpecificDday>>() {
            }.getType();
            ArrayList<Object_CheckEveryMonthSpecificDday> arrayList = gson.fromJson(jsonString, type);
            p = arrayList.get(0).Interval - 1;
            hd = arrayList.get(0).HolidayCondition - 1;
            m_start.setText(formmat_date(arrayList.get(0).StartDate));
            if (arrayList.get(0).EndDate.length() > 0) {
                m_loop.setChecked(false);
                m_end.setText(formmat_date(arrayList.get(0).EndDate));
                m_end.setTextColor(Color.parseColor("#000000"));
            }
            for (int i = 0; i < arrayList.size(); i++) {
                if (arrayList.get(i).SpecificDD != null) {
                    String str[] = arrayList.get(i).SpecificDD.split(",");
                    for (int j = 0; j < str.length; j++) {
                        ObjectAddMonth objectAddMonth = new ObjectAddMonth();
                        objectAddMonth.setId(0);
                        objectAddMonth.setDate(Integer.parseInt(str[j]) - 1);
                        objectAddMonth.setWeek(week_of_month);
                        objectAddMonth.setDay_of_week(date_of_week_month);
                        arrayList_month.add(objectAddMonth);
                    }
                }
                if (arrayList.get(i).SpecificWeek != null) {
                    String str[] = arrayList.get(i).SpecificWeek.split(",");
                    for (int j = 0; j < str.length; j++) {
                        ObjectAddMonth objectAddMonth = new ObjectAddMonth();
                        String week = str[j].split("")[1];
                        String dayofweek = str[j].split("")[2];
                        if (week.equals("A")) objectAddMonth.setWeek(0);
                        else if (week.equals("0")) objectAddMonth.setWeek(6);
                        else objectAddMonth.setWeek(Integer.parseInt(week));
                        objectAddMonth.setId(1);
                        objectAddMonth.setDate(date_month);
                        objectAddMonth.setDay_of_week(Integer.parseInt(dayofweek));
                        arrayList_month.add(objectAddMonth);
                    }
                }
            }

        } else {
            ObjectAddMonth objectAddMonth = new ObjectAddMonth();
            objectAddMonth.setId(0);
            objectAddMonth.setDate(date_month);
            objectAddMonth.setWeek(week_of_month);
            objectAddMonth.setDay_of_week(date_of_week_month);
            arrayList_month.add(objectAddMonth);
            m_start.setText(ngay);
        }
        monthAdapter = new MonthAdapter(this, arrayList_month, lv_month);
        lv_month.setAdapter(monthAdapter);

        int totalHeight = lv_month.getPaddingTop() + lv_month.getPaddingBottom();
        int desiredWidth = View.MeasureSpec.makeMeasureSpec(lv_month.getWidth(), View.MeasureSpec.AT_MOST);

        for (int i = 0; i < DDayActivity.monthAdapter.getCount(); i++) {
            View listItem = DDayActivity.monthAdapter.getView(i, null, lv_month);
            if (listItem != null) {
                listItem.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                listItem.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
                totalHeight += listItem.getMeasuredHeight();
            }
        }
        ViewGroup.LayoutParams params = lv_month.getLayoutParams();
        params.height = totalHeight + (lv_month.getDividerHeight() * (lv_month.getCount() - 1));
        lv_month.setLayoutParams(params);
        lv_month.requestLayout();

        View view1_month = (View) findViewById(R.id.view1_month);
        TextView tv_frequency = (TextView) view1_month.findViewById(R.id.tv_item);
        Spinner sp_frequency = (Spinner) view1_month.findViewById(R.id.sp_item);
        tv_frequency.setText(getResources().getString(R.string.frequency));
        final String arrayList_frequency[] = getResources().getStringArray(R.array.monthly_interval_array);
        ArrayAdapter adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, arrayList_frequency);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_frequency.setAdapter(adapter);
        sp_frequency.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Interval_m = position + 1;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        sp_frequency.setSelection(p);
        View view3_month = (View) findViewById(R.id.view2_month);
        TextView tv_holiday = (TextView) view3_month.findViewById(R.id.tv_item);
        Spinner sp_holiday = (Spinner) view3_month.findViewById(R.id.sp_item);
        tv_holiday.setText(getResources().getString(R.string.holiday));
        String arrayList_holiday[] = getResources().getStringArray(R.array.daily_holiday);
        ArrayAdapter adapter_holiday = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, arrayList_holiday);
        adapter_holiday.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_holiday.setAdapter(adapter_holiday);

        sp_holiday.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                HolidayCondition_m = hld[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        sp_holiday.setSelection(hd);
    }

    public static ArrayList<ObjectAddMonth> arrayList_month;

    public void dialog_annually() {
        View time_y = (View) findViewById(R.id.time_y);
        View view_start = (View) time_y.findViewById(R.id.view_start);
        View view_end = (View) time_y.findViewById(R.id.view_end);
        y_start = (TextView) view_start.findViewById(R.id.txt_start_day_chose);
        y_end = (TextView) view_end.findViewById(R.id.txt_start_day_chose);
        y_loop = (CheckBox) time_y.findViewById(R.id.cb_infiniteloop);
        if (c_month < 10) m_temp = "0" + c_month;
        else m_temp = "" + c_month;
        if (c_date < 10) d_temp = "0" + c_date;
        else d_temp = "" + c_date;
        final String ngay = "" + c_year + "-" + m_temp + "-" + d_temp;

        y_end.setText(date_finish);
        y_end.setTextColor(Color.parseColor("#FFFFFF"));
        y_loop.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    y_end.setText(date_finish);
                    y_end.setTextColor(Color.parseColor("#FFFFFF"));
                } else {
                    y_end.setTextColor(Color.parseColor("#000000"));
                    y_end.setText(y_start.getText().toString());
                }
            }
        });
        view_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowDateDialog(y_start, y_end, y_loop.isChecked());
            }
        });
        view_end.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!y_loop.isChecked())
                    ShowDateDialogEnd(y_end, y_start);
            }
        });
        arrayList_year = new ArrayList<ObjectAddYear>();
        Calendar calendar = Calendar.getInstance();
        month_year = calendar.get(Calendar.MONTH);
        dayofweek_year = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        day_year = calendar.get(Calendar.DAY_OF_MONTH) - 1;
        week_of_month = calendar.get(Calendar.WEEK_OF_MONTH);
        int pos = 0, hd = 0;
        if (repeatOption_year.length() > 0) {
            String jsonString = "[" + repeatOption_year + "]";
            Gson gson = new Gson();
            Type type = new TypeToken<ArrayList<Object_CheckEveryYearDday>>() {
            }.getType();
            ArrayList<Object_CheckEveryYearDday> arrayList = gson.fromJson(jsonString, type);
            pos = arrayList.get(0).Interval - 1;
            hd = arrayList.get(0).HolidayCondition - 1;
            y_start.setText(formmat_date(arrayList.get(0).StartDate));
            if (arrayList.get(0).EndDate.length() > 0) {
                y_loop.setChecked(false);
                y_end.setText(formmat_date(arrayList.get(0).EndDate));
                y_end.setTextColor(Color.parseColor("#000000"));
            }
            for (int i = 0; i < arrayList.size(); i++) {
                Object_CheckEveryYearDday ob = arrayList.get(i);
                if (ob.TypeName.equals(Cons.CheckEveryYearDday)) {
                    ObjectAddYear objectAddYear = new ObjectAddYear();
                    int month = Integer.parseInt(ob.SpecificMMDD.substring(0, 2));
                    int date = Integer.parseInt(ob.SpecificMMDD.substring(2, 4));
                    int L = 0;
                    if (ob.Lunar == 1) L = 0;
                    else if (ob.Lunar == 0) L = 1;
                    else L = 2;
                    objectAddYear.setId(0);
                    objectAddYear.setMonth(month - 1);
                    objectAddYear.setMonth2(month_year);
                    objectAddYear.setDayofweek(dayofweek_year);
                    if (date == 0)
                        objectAddYear.setDayofmonth(month_date.length - 1);
                    else objectAddYear.setDayofmonth(date - 1);
                    objectAddYear.setCalendar(L);
                    objectAddYear.setWeek(week_of_month);
                    arrayList_year.add(objectAddYear);
                } else {
                    int month = Integer.parseInt(ob.SpecificMMnthWeek.substring(0, 2));
                    String str = ob.SpecificMMnthWeek.substring(2, 4);
                    String week = str.substring(0, 1);
                    String dayofweek = str.substring(1, 2);

                    ObjectAddYear objectAddYear = new ObjectAddYear();
                    objectAddYear.setId(1);
                    objectAddYear.setMonth(month_year);
                    objectAddYear.setMonth2(month - 1);
                    objectAddYear.setDayofweek(Integer.parseInt(dayofweek));
                    objectAddYear.setDayofmonth(day_year);
                    objectAddYear.setCalendar(0);
                    if (week.equals("A"))
                        objectAddYear.setWeek(0);
                    else if (week.equals("0")) objectAddYear.setWeek(6);
                    else objectAddYear.setWeek(Integer.parseInt(week));
                    arrayList_year.add(objectAddYear);
                }
            }
        } else {
            objectAddYear = new ObjectAddYear();
            objectAddYear.setId(0);
            objectAddYear.setMonth(month_year);
            objectAddYear.setMonth2(month_year);
            objectAddYear.setDayofweek(dayofweek_year);
            objectAddYear.setDayofmonth(day_year);
            objectAddYear.setCalendar(0);
            objectAddYear.setWeek(week_of_month);
            arrayList_year.add(objectAddYear);
            y_start.setText(ngay);
        }
        ListView lv_month_y = (ListView) findViewById(R.id.lv_month_y);
        yearAdapter = new YearAdapter(this, arrayList_year, lv_month_y);
        lv_month_y.setAdapter(yearAdapter);

        int totalHeight = lv_month_y.getPaddingTop() + lv_month_y.getPaddingBottom();
        int desiredWidth = View.MeasureSpec.makeMeasureSpec(lv_month_y.getWidth(), View.MeasureSpec.AT_MOST);

        for (int i = 0; i < DDayActivity.yearAdapter.getCount(); i++) {
            View listItem = DDayActivity.yearAdapter.getView(i, null, lv_month_y);
            if (listItem != null) {
                listItem.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                listItem.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
                totalHeight += listItem.getMeasuredHeight();
            }
        }
        ViewGroup.LayoutParams params = lv_month_y.getLayoutParams();
        params.height = totalHeight + (lv_month_y.getDividerHeight() * (lv_month_y.getCount() - 1));
        lv_month_y.setLayoutParams(params);
        lv_month_y.requestLayout();

        View view1_y = (View) findViewById(R.id.view1_y);
        TextView tv_frequency = (TextView) view1_y.findViewById(R.id.tv_item);
        Spinner sp_frequency = (Spinner) view1_y.findViewById(R.id.sp_item);
        tv_frequency.setText(getResources().getString(R.string.frequency));
        final String arrayList_frequency[] = getResources().getStringArray(R.array.annually_interval_array);
        ArrayAdapter adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, arrayList_frequency);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_frequency.setAdapter(adapter);
        sp_frequency.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Interval_y = position + 1;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        sp_frequency.setSelection(pos);
        View view3_y = (View) findViewById(R.id.view2_y);
        TextView tv_holiday = (TextView) view3_y.findViewById(R.id.tv_item);
        Spinner sp_holiday = (Spinner) view3_y.findViewById(R.id.sp_item);
        tv_holiday.setText(getResources().getString(R.string.holiday));
        String arrayList_holiday[] = getResources().getStringArray(R.array.daily_holiday);
        ArrayAdapter adapter_holiday = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, arrayList_holiday);
        adapter_holiday.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_holiday.setAdapter(adapter_holiday);

        sp_holiday.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                HolidayCondition_y = hld[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        sp_holiday.setSelection(hd);
    }

    public static ObjectAddYear objectAddYear;
    public static int month_year, dayofweek_year, day_year, week_of_month;
    public static ArrayList<ObjectAddYear> arrayList_year;
    public static YearAdapter yearAdapter;

    public void ShowDateDialog(final TextView tv, final TextView tv2, final boolean ischeck) {
        DatePickerDialog dpd = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                String day = "", month = "";
                if (dayOfMonth < 10) {
                    day = "0" + dayOfMonth;
                } else {
                    day = "" + dayOfMonth;
                }
                monthOfYear += 1;
                if (monthOfYear < 10) {
                    month = "0" + monthOfYear;
                } else {
                    month = "" + monthOfYear;
                }
                tv.setText("" + year + "-" + month + "-" + day);
                c_year = year;
                c_month = monthOfYear;
                c_date = dayOfMonth;
                if (!ischeck) {
                    try {
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                        Date date1 = sdf.parse(tv.getText().toString().trim());
                        Date date2 = sdf.parse(tv2.getText().toString().trim());
                        if (date1.after(date2)) {
                            tv2.setText("" + year + "-" + month + "-" + day);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }, c_year, c_month - 1, c_date);
        dpd.show();
    }

    public void ShowDateDialogEnd(final TextView tv, final TextView tv2) {
        DatePickerDialog dpd = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                String day = "", month = "";
                if (dayOfMonth < 10) {
                    day = "0" + dayOfMonth;
                } else {
                    day = "" + dayOfMonth;
                }
                monthOfYear += 1;
                if (monthOfYear < 10) {
                    month = "0" + monthOfYear;
                } else {
                    month = "" + monthOfYear;
                }
                try {
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    Date date1 = sdf.parse(tv2.getText().toString().trim());
                    Date date2 = sdf.parse("" + year + "-" + month + "-" + day);
                    if (date1.before(date2)) {
                        tv.setText("" + year + "-" + month + "-" + day);
                        c_year = year;
                        c_month = monthOfYear;
                        c_date = dayOfMonth;
                    } else {
                        tv.setText("" + tv2.getText().toString());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, c_year, c_month - 1, c_date);
        dpd.show();
    }

    String title_repeat = "";

    public String getfulldate(int month) {
        String date = "";
        if (month < 10) date = "0" + month;
        else date = "" + month;
        return date;
    }

    public String get_dayofweek(int day_of_week) {
        String dayofweek = "";
        if (day_of_week == 1) dayofweek = "(" + getResources().getString(R.string.sunday) + ")";
        else if (day_of_week == 2)
            dayofweek = "(" + getResources().getString(R.string.monday) + ")";
        else if (day_of_week == 3)
            dayofweek = "(" + getResources().getString(R.string.tuesday) + ")";
        else if (day_of_week == 4)
            dayofweek = "(" + getResources().getString(R.string.wednesday) + ")";
        else if (day_of_week == 5)
            dayofweek = "(" + getResources().getString(R.string.thursday) + ")";
        else if (day_of_week == 6)
            dayofweek = "(" + getResources().getString(R.string.friday) + ")";
        else if (day_of_week == 7)
            dayofweek = "(" + getResources().getString(R.string.saturday) + ")";
        return dayofweek;
    }

    public String formmat_date(String date) {
        String day = "";
        int year = Integer.parseInt(date.substring(0, 4));
        int month = Integer.parseInt(date.substring(4, 6));
        int ngay = Integer.parseInt(date.substring(6, 8));
        day = year + "-" + getfulldate(month) + "-" + getfulldate(ngay);
        return day;
    }

    public String get_date(Object_CheckSpecificDday ob) {
        String date = ob.getSpecificDay();
        String day = "";
        int year = Integer.parseInt(date.substring(0, 4));
        int month = Integer.parseInt(date.substring(4, 6));
        int ngay = Integer.parseInt(date.substring(6, 8));
        int a = ob.getLunar();
        if (a == 1) a = 0;
        else if (a == 0) a = 1;
        else a = 2;
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month - 1, ngay);
        String title = "";
        if (sp_ddaytype_int == 0) title = "Specific Day";
        else if (sp_ddaytype_int == 1) title = "Completion D-Day";
        day = title + " " + year + "-" + getfulldate(month) + "-" + getfulldate(ngay) + get_dayofweek(calendar.get(Calendar.DAY_OF_WEEK)) + " [" + arr_calendar[a] + "]" + "\n";
        return day;
    }

    public void setTitleRepeat(TextView tv) {
        arrayList_repeat.clear();
        temp_list.clear();
        title_repeat = "";
        String str_temp = "";
        if (repeatOption_special.length() > 0) {
            if (repeatOption_special.contains("CheckSpecificDday")) {

                String jsonString = "[" + repeatOption_special + "]";
                Gson gson = new Gson();
                Type type = new TypeToken<ArrayList<Object_CheckSpecificDday>>() {
                }.getType();
                ArrayList<Object_CheckSpecificDday> object_checkSpecificDday = gson.fromJson(jsonString, type);
                for (int i = 0; i < object_checkSpecificDday.size(); i++) {
                    title_repeat += get_date(object_checkSpecificDday.get(i));
                }
            } else {
                Object_CheckDPlusDay ob = new Gson().fromJson(repeatOption_special, Object_CheckDPlusDay.class);
                String date = ob.getStartDate();
                int year = Integer.parseInt(date.substring(0, 4));
                int month = Integer.parseInt(date.substring(4, 6));
                int ngay = Integer.parseInt(date.substring(6, 8));
                int a = ob.getLunar();
                if (a == 1) a = 0;
                else if (a == 0) a = 1;
                else a = 2;
                Calendar calendar = Calendar.getInstance();
                calendar.set(year, month - 1, ngay);
                get_dayofweek(calendar.get(Calendar.DAY_OF_WEEK));
//                title_repeat = "D+Day" + " " + year + "-" + getfulldate(month) + "-" + getfulldate(ngay) +
//                        get_dayofweek(calendar.get(Calendar.DAY_OF_WEEK)) + " [" + arr_calendar[a] + "]" + "\n";
                title_repeat = year + "-" + getfulldate(month) + "-" + getfulldate(ngay) +
                        get_dayofweek(calendar.get(Calendar.DAY_OF_WEEK)) + "\n";
            }
        }
        if (repeatOption_daily.length() > 0) {
            Object_CheckEveryDayDday ob = new Gson().fromJson(repeatOption_daily, Object_CheckEveryDayDday.class);
            int Interval = ob.getInterval();
            String inter = "";
            if (Interval == 1) inter = "";
            else if (Interval == 1000) inter = "1000";
            else if (Interval == 100) inter = "100";
            else {
                inter = "" + Interval;
            }
            String options = "";
            String holiday = "";
            if (!ob.StartOption.equalsIgnoreCase("D")) {
                if (ob.StartOption.equalsIgnoreCase("Y"))
                    options = "Options: " + arrayList_option[1];
                else if (ob.StartOption.equalsIgnoreCase("M"))
                    options = "Options: " + arrayList_option[2];
                else options = "Options: " + arrayList_option[3];
            }
            holiday = "If holiday: " + arrayList_holiday[ob.HolidayCondition - 1];
            String str = "";
            if (options.length() > 0)
                str = "Repeat every " + inter + " day" + "\n" + "Period: From " + formmat_date(ob.getStartDate()) + "\n" + options + "\n" + holiday;
            else
                str = "Repeat every " + inter + " day" + "\n" + "Period: From " + formmat_date(ob.getStartDate()) + "\n" + holiday;
            str = str.replaceAll("(^\\s+|\\s+$)", "");
            title_repeat += "\n" + str + "\n\n";
            arrayList_repeat.add(str.trim());
            temp_list.add("repeatOption_daily");
        }
        if (repeatOption_week.length() > 0) {
            String str = "", line1 = "", line2 = "", line3 = "";
            Object_CheckEveryDdayOfWeek ob = new Gson().fromJson(repeatOption_week, Object_CheckEveryDdayOfWeek.class);
            String get_dayofweek = ob.getSpecificDayOfWeek();
            if (get_dayofweek.contains("1"))
                get_dayofweek = get_dayofweek.replace("1", " " + getResources().getString(R.string.monday));
            if (get_dayofweek.contains("2"))
                get_dayofweek = get_dayofweek.replace("2", " " + getResources().getString(R.string.tuesday));
            if (get_dayofweek.contains("3"))
                get_dayofweek = get_dayofweek.replace("3", " " + getResources().getString(R.string.wednesday));
            if (get_dayofweek.contains("4"))
                get_dayofweek = get_dayofweek.replace("4", " " + getResources().getString(R.string.thursday));
            if (get_dayofweek.contains("5"))
                get_dayofweek = get_dayofweek.replace("5", " " + getResources().getString(R.string.friday));
            if (get_dayofweek.contains("6"))
                get_dayofweek = get_dayofweek.replace("6", " " + getResources().getString(R.string.saturday));
            if (get_dayofweek.contains("0"))
                get_dayofweek = get_dayofweek.replace("0", " " + getResources().getString(R.string.sunday));
            if (ob.getInterval() == 1) line1 = "Repeat:" + get_dayofweek;
            else line1 = "Repeat: Every " + ob.getInterval() + " weeks" + get_dayofweek;
            line2 = "Period: From " + formmat_date(ob.getStartDate());
            line3 = "If holiday: " + arrayList_holiday[ob.HolidayCondition - 1];
            str = "Repeat every week" + "\n" + line1 + "\n" + line2 + "\n" + line3;
            title_repeat += str + "\n\n";
            arrayList_repeat.add(str.trim());
            temp_list.add("repeatOption_week");
        }
        if (repeatOption_month.length() > 0) {
            String str[] = repeatOption_month.split("\\},\\{");
            String repeat = "", title = "", Period = "", holiday = "", inter = "", sum = "";
            for (int i = 0; i < str.length; i++) {
                if (i == 0) str_temp = str[i] + "}";
                else if (i == str.length - 1) str_temp = "{" + str[i];
                else str_temp = "{" + str[i] + "}";
                if (str_temp.substring(str_temp.length() - 2, str_temp.length() - 1).equalsIgnoreCase("}"))
                    str_temp = str_temp.substring(0, str_temp.length() - 1);

                Object_CheckEveryMonthSpecificDday ob = new Gson().fromJson(str_temp, Object_CheckEveryMonthSpecificDday.class);
                Period = "Period: From " + formmat_date(ob.StartDate);
                holiday = "If holiday: " + arrayList_holiday[ob.HolidayCondition - 1];
                if (ob.Interval == 1) inter = "";
                else inter = "" + ob.Interval;
                if (str_temp.contains("CheckEveryMonthSpecificDday")) {
                    String a = "";
                    String abc[] = ob.SpecificDD.split(",");
                    for (int j = 0; j < abc.length; j++) {
                        if (abc[j].equalsIgnoreCase("1") || abc[j].equalsIgnoreCase("31"))
                            a += abc[j] + "st, ";
                        else if (abc[j].equalsIgnoreCase("2")) a += abc[j] + "nd, ";
                        else if (abc[j].equalsIgnoreCase("3")) a += abc[j] + "rd, ";
                        else if (abc[j].equalsIgnoreCase("00")) a += "Last";
                        else a += abc[j] + "th, ";
                    }
                    String SpecificDD = a + " day of every " + inter + " month";
                    SpecificDD = SpecificDD.replaceAll("(^\\s+|\\s+$)", "");
                    repeat = "Repeat: " + SpecificDD;
                    title = "Repeat the specific days every month";
                } else {
                    String a = "";
                    String abc[] = ob.SpecificWeek.split(",");
                    for (int j = 0; j < abc.length; j++) {
                        String content = "", content2 = "";
                        String str1 = abc[j].trim().split("")[1];
                        String str2 = abc[j].trim().split("")[2];
                        if (str1.equalsIgnoreCase("A")) content = month_week[0];
                        else if (str1.equalsIgnoreCase("0"))
                            content = month_week[month_week.length - 1];
                        else content = month_week[Integer.parseInt(str1)];
                        content2 = month_day_of_week[Integer.parseInt(str2)];
                        a += " " + content + " " + content2 + ",";
                    }
                    if (a.substring(a.length() - 1, a.length()).equalsIgnoreCase(","))
                        a = a.substring(0, a.length() - 1);
                    repeat = "Repeat:" + a;
                    title = "Repeat the specific weeks every month";
                }
                sum += title + "\n" + repeat + "\n" + Period + "\n" + holiday + "\n";
            }
            title_repeat += sum + "\n";
            arrayList_repeat.add(sum.trim());
            temp_list.add("repeatOption_month");
        }
        if (repeatOption_year.length() > 0) {
            String str[] = repeatOption_year.split("\\},\\{");
            String title = "", sum = "", repeat = "", Period = "", holiday = "";
            for (int i = 0; i < str.length; i++) {
                if (i == 0) str_temp = str[i] + "}";
                else if (i == str.length - 1) str_temp = "{" + str[i];
                else str_temp = "{" + str[i] + "}";
                if (str_temp.substring(str_temp.length() - 2, str_temp.length() - 1).equalsIgnoreCase("}"))
                    str_temp = str_temp.substring(0, str_temp.length() - 1);
                Object_CheckEveryYearDday ob = new Gson().fromJson(str_temp, Object_CheckEveryYearDday.class);
                Period = "Period: From " + formmat_date(ob.StartDate);
                holiday = "If holiday: " + arrayList_holiday[ob.HolidayCondition - 1];
                if (str_temp.contains("CheckEveryYearDday")) {
                    String month = arr_month[Integer.parseInt(ob.SpecificMMDD.substring(0, 2)) - 1];
                    String date = "" + Integer.parseInt(ob.SpecificMMDD.substring(2, 4));
                    title = "Repeat the specific days every year";
                    String Solar = "";
                    if (ob.Lunar == 1) Solar = arr_calendar[0];
                    else if (ob.Lunar == 0) Solar = arr_calendar[1];
                    else Solar = arr_calendar[2];
                    repeat = "Repeat: " + arrayList_annually[ob.Interval - 1] + " " + month + " Every " + date + " days" + "(" + Solar + ")";
                } else {
                    title = "Repeat the specific weeks every year";
                    String month = arr_month[Integer.parseInt(ob.SpecificMMnthWeek.substring(0, 2)) - 1];
                    String date = "" + ob.SpecificMMnthWeek.substring(2, 4);
                    String str1 = date.split("")[1];
                    String str2 = date.split("")[2];
                    String w = "", d = "";
                    if (str1.equalsIgnoreCase("A")) w = month_week[0];
                    else if (str1.equalsIgnoreCase("0")) w = month_week[month_week.length - 1];
                    else w = month_week[Integer.parseInt(str1)];
                    d = month_day_of_week[Integer.parseInt(str2)];
                    repeat = "Repeat: " + arrayList_annually[ob.Interval - 1] + " " + month + " " + w + " " + d;
                }
                sum += title + "\n" + repeat + "\n" + Period + "\n" + holiday + "\n";
            }

            title_repeat += sum + "\n";
            arrayList_repeat.add(sum.trim());
            temp_list.add("repeatOption_year");
        }
        if (arrayList_repeat.size() == 0) {
            temp_list.clear();
            arrayList_repeat.add(getResources().getString(R.string.norepeat));
        }

        if (title_repeat.length() == 0) title_repeat = getResources().getString(R.string.norepeat);
//        tv.setText(title_repeat.trim());
    }


    public void ShowDateDialog() {
        final Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog dpd = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
//                        Log.e(TAG, "year:" + year + " monthOfYear:" + monthOfYear + " dayOfMonth:" + dayOfMonth);

                    }
                }, mYear, mMonth, mDay);
        dpd.show();
    }

    private HorizontalScrollView tabsHorizontalScrollView;
    private TabHost tab;
    private CheckBox v1_cb1, v2_cb1, v3_cb1, v4_cb1, v5_cb1;
    private List<Integer> stackList;

    private void removeStack(int index) {
        for (int i = 0; i < stackList.size(); i++) {
            if (stackList.get(i) == index) {
                stackList.remove(i);
                int position = 0;
                try {
                    position = stackList.get(stackList.size() - 1);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                setTabIndex(position);
                Log.d(TAG, "removeStack");
                break;
            }
        }

        for (int a : stackList) {
            Log.d(TAG, "a:" + a);
        }
    }

    private void addStack(int index) {
        Log.d(TAG, "addStack");
        stackList.add(index);
        setTabIndex(index);
    }

    private void setTabIndex(int index) {
        if (tab != null) {
            try {
                tab.setCurrentTab(index);
                View tabView = tab.getCurrentTabView();
                int scrollPos = tabView.getLeft() - (tabsHorizontalScrollView.getWidth() - tabView.getWidth()) / 2;
                tabsHorizontalScrollView.smoothScrollTo(scrollPos, 0);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void _DisableCheckBox() {
//        v1_cb1.setEnabled(false);
        v2_cb1.setEnabled(false);
        v3_cb1.setEnabled(false);
        v4_cb1.setEnabled(false);
        v5_cb1.setEnabled(false);
    }

    public void _EnableCheckBox() {
//        v1_cb1.setEnabled(true);
        v2_cb1.setEnabled(true);
        v3_cb1.setEnabled(true);
        v4_cb1.setEnabled(true);
        v5_cb1.setEnabled(true);
    }

    public void loadTabs() {
        stackList = new ArrayList<>();
        tabsHorizontalScrollView = (HorizontalScrollView) findViewById(R.id.tabsHorizontalScrollView);
        tab = (TabHost) findViewById(R.id.tabhost_day);
        tab.setup();
        TabHost.TabSpec spec;

        View v1 = LayoutInflater.from(this).inflate(R.layout.title_tabhost, null);
        v1_cb1 = (CheckBox) v1.findViewById(R.id.checkBox);
        TextView v1_tv1 = (TextView) v1.findViewById(R.id.tv_cb);
        v1_tv1.setText(getResources().getString(R.string.special_day));
//        if (!isEdit) v1_cb1.setChecked(true);

        View v2 = LayoutInflater.from(this).inflate(R.layout.title_tabhost, null);
        v2_cb1 = (CheckBox) v2.findViewById(R.id.checkBox);
        TextView v2_tv2 = (TextView) v2.findViewById(R.id.tv_cb);
        v2_tv2.setText(getResources().getString(R.string.menu_daily));

        View v3 = LayoutInflater.from(this).inflate(R.layout.title_tabhost, null);
        v3_cb1 = (CheckBox) v3.findViewById(R.id.checkBox);
        TextView v3_tv3 = (TextView) v3.findViewById(R.id.tv_cb);
        v3_tv3.setText(getResources().getString(R.string.menu_week));

        View v4 = LayoutInflater.from(this).inflate(R.layout.title_tabhost, null);
        v4_cb1 = (CheckBox) v4.findViewById(R.id.checkBox);
        TextView v4_tv4 = (TextView) v4.findViewById(R.id.tv_cb);
        v4_tv4.setText(getResources().getString(R.string.menu_month));

        View v5 = LayoutInflater.from(this).inflate(R.layout.title_tabhost, null);
        v5_cb1 = (CheckBox) v5.findViewById(R.id.checkBox);
        TextView v5_tv5 = (TextView) v5.findViewById(R.id.tv_cb);
        v5_tv5.setText(getResources().getString(R.string.annualy));

        spec = tab.newTabSpec("t1");
        spec.setContent(R.id.linearLayout);
        spec.setIndicator(v1);
        tab.addTab(spec);

        spec = tab.newTabSpec("t2");
        spec.setContent(R.id.linearLayout2);
        spec.setIndicator(v2);
        tab.addTab(spec);

        spec = tab.newTabSpec("t3");
        spec.setContent(R.id.linearLayout3);
        spec.setIndicator(v3);
        tab.addTab(spec);

        spec = tab.newTabSpec("t4");
        spec.setContent(R.id.linearLayout4);
        spec.setIndicator(v4);
        tab.addTab(spec);

        spec = tab.newTabSpec("t5");
        spec.setContent(R.id.linearLayout5);
        spec.setIndicator(v5);
        tab.addTab(spec);

        v1_cb1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (rb_ddayplus.isChecked()) {
                    v1_cb1.setChecked(true);
                } else {
                    if (v1_cb1.isChecked()) addStack(Cons.INDEX_SPECIAL);
                    else removeStack(Cons.INDEX_SPECIAL);
                }
            }
        });
        v2_cb1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (rb_ddayplus.isChecked()) {
                    v2_cb1.setChecked(false);
                } else {
                    if (v2_cb1.isChecked()) addStack(Cons.INDEX_DAILY);
                    else removeStack(Cons.INDEX_DAILY);
                }
            }
        });
        v3_cb1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (rb_ddayplus.isChecked()) {
                    v3_cb1.setChecked(false);
                } else {
                    if (v3_cb1.isChecked()) addStack(Cons.INDEX_WEEK);
                    else removeStack(Cons.INDEX_WEEK);
                }
            }
        });
        v4_cb1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (rb_ddayplus.isChecked()) {
                    v4_cb1.setChecked(false);
                } else {
                    if (v4_cb1.isChecked()) addStack(Cons.INDEX_MONTH);
                    else removeStack(Cons.INDEX_MONTH);
                }
            }
        });
        v5_cb1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (rb_ddayplus.isChecked()) {
                    v5_cb1.setChecked(false);
                } else {
                    if (v5_cb1.isChecked()) addStack(Cons.INDEX_YEAR);
                    else removeStack(Cons.INDEX_YEAR);
                }
            }
        });

        tab.setCurrentTab(Cons.INDEX_SPECIAL);

        int totalTabs = tab.getTabWidget().getChildCount();
        for (int i = 0; i < totalTabs; i++) {
            if (i == 0) {
                tab.getTabWidget().getChildAt(i).setBackgroundResource(R.drawable.tab_choose);
            } else {
                tab.getTabWidget().getChildAt(i).setBackgroundResource(R.drawable.tab_not_choose);
            }
        }
        tab.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            public void onTabChanged(String arg0) {
                if (rb_ddayplus.isChecked()) {
                    tab.setCurrentTab(0);
                } else {
                    for (int i = 0; i < tab.getTabWidget().getChildCount(); i++) {
                        tab.getTabWidget().getChildAt(i).setBackgroundResource(R.drawable.tab_not_choose); // unselected
                    }
                    tab.getTabWidget().getChildAt(tab.getCurrentTab()).setBackgroundResource(R.drawable.tab_choose); // selected
                }
            }
        });
    }

    public void setTabWhenPLusCheck() {
        tab.setCurrentTab(0);
        for (int i = 0; i < tab.getTabWidget().getChildCount(); i++) {
            tab.getTabWidget().getChildAt(i).setBackgroundResource(R.drawable.tab_not_choose); // unselected
        }
        tab.getTabWidget().getChildAt(0).setBackgroundResource(R.drawable.tab_choose); // selected
    }

    public static ListView special_lv_day;
    TextView daily_frequency, week_frequency, month_frequency, year_frequency, daily_option;
    TextView daily_holiday, week_holiday, month_holiday, year_holiday,
            dl_start, dl_end, w_start, w_end, m_start, m_end, y_start, y_end;
    Spinner sp_dl_frequence, sp_w_frequence, sp_m_frequence, sp_y_frequence,
            sp_dl_holiday, sp_w_holiday, sp_m_holiday, sp_y_holiday, sp_dl_op;
    CheckBox dl_loop, w_loop, m_loop, y_loop;
    public static AdapterNotifi adapterNotifi;
    public static ArrayList<NotifyTime> arr_adapter_notifi;
    public static ListView lv_notify;

    public void setheight_lv_notify(ListView lv_repeat) {
        int totalHeight = lv_repeat.getPaddingTop() + lv_repeat.getPaddingBottom();
        int desiredWidth = View.MeasureSpec.makeMeasureSpec(lv_repeat.getWidth(), View.MeasureSpec.AT_MOST);

        for (int i = 0; i < DDayActivity.adapterNotifi.getCount(); i++) {
            View listItem = DDayActivity.adapterNotifi.getView(i, null, lv_repeat);
            if (listItem != null) {
                listItem.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                listItem.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
                totalHeight += listItem.getMeasuredHeight();
            }
        }
        ViewGroup.LayoutParams params = lv_repeat.getLayoutParams();
        params.height = totalHeight + (lv_repeat.getDividerHeight() * (lv_repeat.getCount() - 1));
        lv_repeat.setLayoutParams(params);
        lv_repeat.requestLayout();
    }

    public void init_view_tab() {
        lv_notify = (ListView) findViewById(R.id.lv_notify);
        adapterNotifi = new AdapterNotifi(this, arr_adapter_notifi);
        lv_notify.setAdapter(adapterNotifi);


//        setheight_lv_notify(lv_notify);
        dialog_special();
        dialog_day();
        dialog_week();
        dialog_month();
        dialog_annually();
    }

    public void getJsonDaily() {
        repeatOption_daily = "";
        String EndDate = "";
        if (dl_loop.isChecked()) {
            EndDate = "";
        } else {
            EndDate = dl_end.getText().toString().replace("-", "");
        }
        repeatOption_daily = "{TypeName:\"CheckEveryDayDday\",StartDate:\"" +
                dl_start.getText().toString().replace("-", "") +
                "\",EndDate:\"" + EndDate + "\",Interval:" + Interval + ",StartOption:\"" + StartOption +
                "\",HolidayCondition:" + HolidayCondition + "}";
    }

    public void getJsonWeek() {
        repeatOption_week = "";
        String SpecificDayOfWeek = "";
        ArrayList<String> arrayList = new ArrayList<String>();
        if (cb_mon.isChecked()) arrayList.add("1");
        if (cb_tue.isChecked()) arrayList.add("2");
        if (cb_wed.isChecked()) arrayList.add("3");
        if (cb_thu.isChecked()) arrayList.add("4");
        if (cb_fri.isChecked()) arrayList.add("5");
        if (cb_sat.isChecked()) arrayList.add("6");
        if (cb_sun.isChecked()) arrayList.add("0");
        if (arrayList.size() == 0) {
            show_notify("Choose a day of week");
        } else {
            if (arrayList.size() == 1) {
                SpecificDayOfWeek = arrayList.get(0);
            } else {
                for (int i = 0; i < arrayList.size(); i++) {
                    SpecificDayOfWeek += arrayList.get(i) + ",";
                }
                SpecificDayOfWeek = SpecificDayOfWeek.substring(0, SpecificDayOfWeek.length() - 1);
            }

            String EndDate = "";
            if (w_loop.isChecked()) EndDate = "";
            else EndDate = w_end.getText().toString().replace("-", "");
            repeatOption_week = "{TypeName:\"CheckEveryDdayOfWeek\",StartDate:\"" +
                    w_start.getText().toString().replace("-", "") +
                    "\",EndDate:\"" + EndDate + "\",Interval:" +
                    Interval_w + ",SpecificDayOfWeek:\"" + SpecificDayOfWeek + "\",HolidayCondition:" + HolidayCondition_w + "}";
        }
    }

    public void getJsonMonth() {
        repeatOption_month = "";
        String EndDate = "";
        if (m_loop.isChecked()) EndDate = "";
        else EndDate = m_end.getText().toString().replace("-", "");
        if (arrayList_month.size() == 1) {
            String TypeName = "CheckEveryMonthSpecificDday";
            int a = arrayList_month.get(0).getDate() + 1;
            String value_SpecificDD = "" + a;
            if (a == 32) value_SpecificDD = "00";
            String SpecificDD = "SpecificDD";
            if (arrayList_month.get(0).getId() == 1) {
                SpecificDD = "SpecificWeek";
                TypeName = "CheckEveryMonthWeekDday";
                value_SpecificDD = "";
                if (arrayList_month.get(0).getWeek() == 0) {
                    value_SpecificDD = "A";
                } else if (arrayList_month.get(0).getWeek() == 6) {
                    value_SpecificDD = "0";
                } else {
                    value_SpecificDD = "" + arrayList_month.get(0).getWeek();
                }
                value_SpecificDD += arrayList_month.get(0).getDay_of_week();
            }
            repeatOption_month = "{TypeName:\"" + TypeName + "\",StartDate:\"" + m_start.getText().toString().replace("-", "") +
                    "\",EndDate:\"" + EndDate + "\",Interval:" + Interval_m + "," +
                    SpecificDD + ":\"" + value_SpecificDD + "\",HolidayCondition:" + HolidayCondition_m + "}";
        } else {
            String str1 = "";
            String str2 = "";
            String SpecificDD = "";
            String SpecificWeek = "";

            for (int i = 0; i < arrayList_month.size(); i++) {
                if (arrayList_month.get(i).getId() == 1) {
                    String value_SpecificDD = "";
                    if (arrayList_month.get(i).getWeek() == 0) {
                        value_SpecificDD = "A";
                    } else if (arrayList_month.get(i).getWeek() == 6) {
                        value_SpecificDD = "0";
                    } else {
                        value_SpecificDD = "" + arrayList_month.get(i).getWeek();
                    }
                    value_SpecificDD += arrayList_month.get(i).getDay_of_week();
                    SpecificWeek += value_SpecificDD + ",";
                } else {
                    int a = arrayList_month.get(i).getDate() + 1;
                    String b = "" + a;
                    if (a == 32) b = "00";
                    SpecificDD += b + ",";
                }
            }
            if (SpecificDD.length() > 0) {
                SpecificDD = SpecificDD.substring(0, SpecificDD.length() - 1);
                str1 = "{TypeName:\"CheckEveryMonthSpecificDday\",StartDate:\"" + m_start.getText().toString().replace("-", "") +
                        "\",EndDate:\"" + EndDate + "\",Interval:" + Interval_m + ",SpecificDD:\"" +
                        SpecificDD + "\",HolidayCondition:" + HolidayCondition_m + "}";
            }
            if (SpecificWeek.length() > 0) {
                SpecificWeek = SpecificWeek.substring(0, SpecificWeek.length() - 1);
                str2 = "{TypeName:\"CheckEveryMonthWeekDday\",StartDate:\"" + m_start.getText().toString().replace("-", "") +
                        "\",EndDate:\"" + EndDate + "\",Interval:" + Interval_m + ",SpecificWeek:\"" +
                        SpecificWeek + "\",HolidayCondition:" + HolidayCondition_m + "}";
            }
            if (str1.length() > 0 && str2.length() == 0)
                repeatOption_month = str1;
            else if (str1.length() == 0 && str2.length() > 0)
                repeatOption_month = str2;
            else
                repeatOption_month = str1 + "," + str2;
        }
    }

    public void getJsonAnnnually() {
        repeatOption_year = "";
        String TypeName = "";
        String EndDate = "";
        if (y_loop.isChecked()) {
            EndDate = "";
        } else {
            EndDate = y_end.getText().toString().replace("-", "");
        }
        for (int i = 0; i < arrayList_year.size(); i++) {
            if (arrayList_year.get(i).getId() == 0) {
                String SpecificMMDD = "";
                int a = arrayList_year.get(i).getMonth() + 1;
                if (a < 10) SpecificMMDD += "0" + a;
                else SpecificMMDD += a;
                int b = arrayList_year.get(i).getDayofmonth() + 1;
                if (b == 32) {
                    SpecificMMDD += "00";
                } else {
                    if (b < 10) SpecificMMDD += "0" + b;
                    else SpecificMMDD += b;
                }
                if (arrayList_year.get(i).getCalendar() == 0) Lunar = 1;
                else if (arrayList_year.get(i).getCalendar() == 1) Lunar = 0;
                else if (arrayList_year.get(i).getCalendar() == 2) Lunar = -1;
                repeatOption_year += "{TypeName:\"CheckEveryYearDday\",StartDate:\"" + y_start.getText().toString().replace("-", "") +
                        "\",EndDate:\"" + EndDate + "\",Interval:" + Interval_y +
                        ",SpecificMMDD:\"" +
                        SpecificMMDD +
                        "\",Lunar:" + Lunar +
                        ",HolidayCondition:" + HolidayCondition_y + "}" + ",";
            } else {
                String SpecificMMnthWeek = "";
                int a = arrayList_year.get(i).getMonth2() + 1;
                if (a < 10) SpecificMMnthWeek += "0" + a;
                else SpecificMMnthWeek += a;
                int b = arrayList_year.get(i).getWeek();
//                        Log.e(TAG, "b:" + b);
                if (b == 0) {
                    SpecificMMnthWeek += "A" + arrayList_year.get(i).getDayofweek();
                } else if (b == 6) {
                    SpecificMMnthWeek += "0" + arrayList_year.get(i).getDayofweek();
                } else {
                    SpecificMMnthWeek += b + "" + arrayList_year.get(i).getDayofweek();
                }

                repeatOption_year += "{TypeName:\"CheckEveryYearWeek\",StartDate:\"" + y_start.getText().toString().replace("-", "") +
                        "\",EndDate:\"" + EndDate + "\",Interval:" + Interval_y +
                        ",SpecificMMnthWeek:\"" + SpecificMMnthWeek + "\",HolidayCondition:" + HolidayCondition_y + "}" + ",";
            }
        }
        repeatOption_year = repeatOption_year.substring(0, repeatOption_year.length() - 1);
    }

    public static class RecyclerTouchListener implements RecyclerView.OnItemTouchListener {

        private GestureDetector gestureDetector;
        private ItemClickListener clickListener;

        public RecyclerTouchListener(Context context, final RecyclerView recyclerView, final ItemClickListener clickListener) {
            this.clickListener = clickListener;
            gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    return true;
                }

                @Override
                public void onLongPress(MotionEvent e) {
                    View child = recyclerView.findChildViewUnder(e.getX(), e.getY());
                    if (child != null && clickListener != null) {
                        clickListener.onLongClick(child, recyclerView.getChildPosition(child));
                    }
                }
            });
        }

        @Override
        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {

            View child = rv.findChildViewUnder(e.getX(), e.getY());
            if (child != null && clickListener != null && gestureDetector.onTouchEvent(e)) {
                clickListener.onClick(child, rv.getChildPosition(child));
            }
            return false;
        }

        @Override
        public void onTouchEvent(RecyclerView rv, MotionEvent e) {
        }

        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

        }
    }
}
