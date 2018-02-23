package com.dazone.crewdday.activity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
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
import android.widget.Toast;

import com.dazone.crewdday.Cons;
import com.dazone.crewdday.R;
import com.dazone.crewdday._Application;
import com.dazone.crewdday.adapter.DT_AdapterNotifi;
import com.dazone.crewdday.adapter.DT_MonthAdapter;
import com.dazone.crewdday.adapter.DT_SpecialAdapter;
import com.dazone.crewdday.adapter.DT_YearAdapter;
import com.dazone.crewdday.adapter.HomeDDayBoardAdapter;
import com.dazone.crewdday.adapter.NotifyTime;
import com.dazone.crewdday.adapter.ObjectAddMonth;
import com.dazone.crewdday.adapter.ObjectAddYear;
import com.dazone.crewdday.adapter.Object_CheckDPlusDay;
import com.dazone.crewdday.adapter.Object_CheckEveryDayDday;
import com.dazone.crewdday.adapter.Object_CheckEveryDdayOfWeek;
import com.dazone.crewdday.adapter.Object_CheckEveryMonthSpecificDday;
import com.dazone.crewdday.adapter.Object_CheckEveryYearDday;
import com.dazone.crewdday.adapter.Object_CheckSpecificDday;
import com.dazone.crewdday.async.DownloadDDayDetail;
import com.dazone.crewdday.fragment.FragmentHomeAll;
import com.dazone.crewdday.fragment.FragmentHomePlus;
import com.dazone.crewdday.mInterface.DeleteCompletedRecordSuccess;
import com.dazone.crewdday.mInterface.DeleteCoveredDaySuccess;
import com.dazone.crewdday.mInterface.DeleteSuccess;
import com.dazone.crewdday.mInterface.DetailViewCallback;
import com.dazone.crewdday.mInterface.GetGroups;
import com.dazone.crewdday.mInterface.GetNotifyTime;
import com.dazone.crewdday.mInterface.IFRepetitionString;
import com.dazone.crewdday.mInterface.InsertCoveredDaySuccess;
import com.dazone.crewdday.mInterface.MakeComplete;
import com.dazone.crewdday.mInterface.UpdateGroupCalllback;
import com.dazone.crewdday.mInterface.UpdateSuccess;
import com.dazone.crewdday.model.DDayDetailModel;
import com.dazone.crewdday.model.DDayModel;
import com.dazone.crewdday.model.Employee;
import com.dazone.crewdday.model.ObjectGetGroups;
import com.dazone.crewdday.other.HttpRequest;
import com.dazone.crewdday.other.ListUtils;
import com.dazone.crewdday.other.OrganizationActivity;
import com.dazone.crewdday.other.OrganizationUserDBHelper;
import com.dazone.crewdday.other.PersonData;
import com.dazone.crewdday.other.Utils;
import com.dazone.crewdday.util.ConnectionUtils;
import com.dazone.crewdday.util.PreferenceUtilities;
import com.dazone.crewdday.util.TimeUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class DetailViewActivity extends AppCompatActivity implements DetailViewCallback, GetGroups, View.OnClickListener, UpdateSuccess {
    String TAG = "DetailViewActivity";
    DDayDetailModel ddayDetailModel = null;
    public static DetailViewActivity instance = null;
    PreferenceUtilities prefUtils;
    int remainingDays;
    long groupId;
    long ddayId;
    String ddayDate;
    boolean CanHide = false;
    TextView tvDate;
    TextView tvRemainingDays;
    EditText tvTitle;
    EditText tvContent;
    LinearLayout ln_gr;
    TextView tvSharer, tvPersonConcerned, tvAdmin, tvWriter;
    RelativeLayout detailViewContainer;
    RelativeLayout titleContainer;
    RelativeLayout commentContainer;
    RelativeLayout repeatContainer;
    RelativeLayout sharerContainer;
    RelativeLayout adminContainer;
    RelativeLayout personConcernedContainer;
    Menu menu;
    TextView tvRepeat;
    int typeNo = 2;
    int id;
    Spinner sp_dday_type;
    public static int sp_ddaytype_int = 0;
    private static boolean isPageLoaded = false;
    long notiNo = 0;
    String JSON_SHARER_TYPE = "", JSON_PERSON_CONCERNED_TYPE = "", JSON_ADMIN_TYPE = "";
    ImageView img_plus;
    boolean IsHidden = false;
    boolean CanManage = false;
    boolean IsNew = false;
    long HiddenDataNo;
    boolean IsCompleted = false;
    long CompletedRecordNo;

    @Override
    public void onBackPressed() {
        back_press();
    }

    void onBack() {
        Intent intent = new Intent();
        if (ddayDetailModel == null) ddayDetailModel = new DDayDetailModel();
//        Log.d(TAG,"send to main:"+new Gson().toJson(ddayDetailModel));
        intent.putExtra(Cons.DETAILS_UPDATE_LIST_PRE, ddayDetailModel);
        setResult(Cons.DETAILS_SUCCESS, intent);
        finish();
    }

    public void back_press() {
        if (item.isVisible()) {
            AlertDialog.Builder adb = new AlertDialog.Builder(DetailViewActivity.this);
            adb.setTitle(getResources().getString(R.string.Dday));
            adb.setMessage(getResources().getString(R.string.edit_comment_notify));
            adb.setPositiveButton(getResources().getString(R.string.del_group_y), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    actionEdit();

                }
            });
            adb.setNegativeButton(getResources().getString(R.string.del_group_n), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    onBack();
                }
            });
            adb.create().show();
        } else {
            if (IsNew)
                goToHome();
            else onBack();
        }
    }

    LinearLayout ln_checkbox;
    CheckBox cb_sharer;
    ImageView detail_ic_ask;
    TextView tv_myself;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_view);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        HomeActivity.insertVersion2 = false;

        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.colorPrimaryDrark));
        }

        instance = this;
        restartjson();
        HomeActivity.gotoOrganizationActivity = 1;

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.mnubar_ic_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                back_press();
            }
        });

        notify = (LinearLayout) findViewById(R.id.notify);
        lv_notify = (ListView) findViewById(R.id.lv_notify);
        ln_checkbox = (LinearLayout) findViewById(R.id.ln_checkbox);
        detail_ic_ask = (ImageView) findViewById(R.id.detail_ic_ask);
        detail_ic_ask.setOnClickListener(this);
        cb_sharer = (CheckBox) findViewById(R.id.cb_sharer);
        cb_sharer.setOnClickListener(this);
        tvDate = (TextView) findViewById(R.id.txt_date);
        tvDate.setOnClickListener(this);
        tvTitle = (EditText) findViewById(R.id.txt_title);
        tvContent = (EditText) findViewById(R.id.txt_dday_title);
        img_plus = (ImageView) findViewById(R.id.img_plus);
        tv_myself = (TextView) findViewById(R.id.tv_myself);
        tv_myself.setOnClickListener(this);
        img_plus.setOnClickListener(this);
        tvTitle.setOnClickListener(this);
        tvContent.setOnClickListener(this);

        tvTitle.setTag(false);
        tvTitle.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!canEdit) {
                    tvTitle.setFocusable(false);
                } else {
                    tvTitle.setTag(true);
                }
            }
        });

        tvTitle.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if ((Boolean) tvTitle.getTag()) {
                    _display_item();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        tvContent.setTag(false);
        tvContent.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!canEdit) {
                    tvContent.setFocusable(false);
                } else {
                    tvContent.setTag(true);
                }
            }
        });

        tvContent.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if ((Boolean) tvContent.getTag()) {
                    _display_item();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        tv_group = (TextView) findViewById(R.id.tv_group);
        tv_group.setText(getResources().getString(R.string.Undefined));
        ln_gr = (LinearLayout) findViewById(R.id.ln_gr);
        ln_gr.setOnClickListener(this);
        tvWriter = (TextView) findViewById(R.id.txt_writer);
        tvPersonConcerned = (TextView) findViewById(R.id.txt_person_concerned);
        tvSharer = (TextView) findViewById(R.id.txt_sharer);
        tvAdmin = (TextView) findViewById(R.id.txt_admin);
        tvRepeat = (TextView) findViewById(R.id.txt_repeat);

        tvRepeat.setOnClickListener(this);
        tvPersonConcerned.setOnClickListener(this);
        tvAdmin.setOnClickListener(this);
        tvSharer.setOnClickListener(this);

        sp_dday_type = (Spinner) findViewById(R.id.sp_dday_type);
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add(getResources().getString(R.string.dday_minus));
        arrayList.add(getResources().getString(R.string.dday_plus));
        ArrayAdapter adapter = new ArrayAdapter<>(this, R.layout.custom_text_size, arrayList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_dday_type.setAdapter(adapter);

        tvRemainingDays = (TextView) findViewById(R.id.txt_remaining_days);
        detailViewContainer = (RelativeLayout) findViewById(R.id.detail_view_container);
        titleContainer = (RelativeLayout) findViewById(R.id.title_container);
        commentContainer = (RelativeLayout) findViewById(R.id.dday_title_container);
        repeatContainer = (RelativeLayout) findViewById(R.id.repeat_container);
        repeatContainer.setOnClickListener(this);
        sharerContainer = (RelativeLayout) findViewById(R.id.sharer_container);
        personConcernedContainer = (RelativeLayout) findViewById(R.id.person_concerned_container);
        adminContainer = (RelativeLayout) findViewById(R.id.admin_container);
        sharerContainer.setOnClickListener(this);
        personConcernedContainer.setOnClickListener(this);
        adminContainer.setOnClickListener(this);
        Intent intent = getIntent();
        ddayId = intent.getLongExtra(Cons.KEY_DDAY_ID, 9999999);
        CanHide = intent.getBooleanExtra(Cons.KEY_CAN_HIDE, false);
        if (!CanHide) cb_sharer.setChecked(true);
        remainingDays = intent.getIntExtra(Cons.KEY_DDAY_REMAINING_DAYS, 9999999);
        ddayDate = intent.getStringExtra(Cons.KEY_DDAY_DATE);
        groupId = intent.getLongExtra(Cons.KEY_DDAY_GROUP_ID, -1);

        IsHidden = intent.getBooleanExtra(Cons.KEY_IS_HIDDEN, false);
        IsNew = intent.getBooleanExtra(Cons.KEY_IS_NEW, false);
        CanManage = intent.getBooleanExtra(Cons.KEY_MANAGER, false);
        Log.d(TAG, "intent CanManage:" + CanManage);
        if (!CanManage) ln_checkbox.setVisibility(View.GONE);
        HiddenDataNo = intent.getLongExtra(Cons.KEY_IS_HIDDEN_NO, 0);
        IsCompleted = intent.getBooleanExtra(Cons.KEY_IS_COMPLETED, false);
        CompletedRecordNo = intent.getLongExtra(Cons.KEY_IS_COMPLETED_RECORD, 0);

        prefUtils = _Application.getInstance().getPreferenceUtilities();
        id = prefUtils.getId();

        arr_temp = new ArrayList<>(arr_lv_special);
        specialAdapter = new DT_SpecialAdapter(this, arr_lv_special);

        Calendar calendar = Calendar.getInstance();
        sa_y = calendar.get(Calendar.YEAR);
        sa_m = calendar.get(Calendar.MONTH);
        sa_d = calendar.get(Calendar.DAY_OF_MONTH);
    }

    public static ArrayList<NotifyTime> arr_adapter_notify;
    public static DT_AdapterNotifi adapterNotify;
    public static ListView lv_notify;
    LinearLayout notify;

    public void create_lv_notify(ArrayList<NotifyTime> arr, boolean canEdit) {
        arr_adapter_notify = arr;
        if (arr_adapter_notify.size() > 0) {
            ArrayList<Integer> integerArrayList = new ArrayList<>();
            for (int i = 0; i < arr_adapter_notify.size(); i++) {
                NotifyTime ob = arr_adapter_notify.get(i);
                if (ob.getNotificationType() != 2) {
                    String time = ob.getNotificationTime();
                    time = time.split(":")[0] + time.split(":")[1];
                    ob.setNotificationTime(time);
                    ob.setNotificationType(ob.getNotificationType() + 1);
                    arr_adapter_notify.set(i, ob);
                } else {
                    integerArrayList.add(i);
                }
            }
            for (int i = 0; i < integerArrayList.size(); i++) {
                for (int j = 0; j <= i; j++) {
                    if (integerArrayList.get(i) > integerArrayList.get(j)) {
                        int temp = integerArrayList.get(i);
                        integerArrayList.set(i, integerArrayList.get(j));
                        integerArrayList.set(j, temp);
                    }
                }
            }
            for (int i : integerArrayList) {
                arr_adapter_notify.remove(i);
            }
            if (arr_adapter_notify.size() == 0) {
                NotifyTime ob = new NotifyTime();
                ob.setNotificationType(0);
                ob.setNotificationTime("0900");
                arr_adapter_notify.add(ob);
            }
//            for(int i=0;i<arr_adapter_notify.size();i++){
//                Log.e(TAG,arr_adapter_notify.get(i).getNotificationType()+"-----"+arr_adapter_notify.get(i).getNotificationTime());
//            }
        } else {
            NotifyTime ob = new NotifyTime();
            ob.setNotificationType(0);
            ob.setNotificationTime("0900");
            arr_adapter_notify.add(ob);
        }

        int temp = arr_adapter_notify.size();
        if (temp > 1) {
            HomeActivity.checkAlarm = 1;
            for (int i = 0; i < arr_adapter_notify.size(); i++) {
                arr_adapter_notify.get(i).setNotificationType(arr_adapter_notify.get(i).getNotificationType() - 1);
            }
        } else {
            HomeActivity.checkAlarm = 0;
        }
        adapterNotify = new DT_AdapterNotifi(this, arr_adapter_notify, temp, canEdit);
        lv_notify.setAdapter(adapterNotify);
        setheight_lv_noti(lv_notify);
    }

    public void setheight_lv_noti(ListView lv_repeat) {
        int totalHeight = lv_repeat.getPaddingTop() + lv_repeat.getPaddingBottom();
        int desiredWidth = View.MeasureSpec.makeMeasureSpec(lv_repeat.getWidth(), View.MeasureSpec.AT_MOST);
        for (int i = 0; i < DetailViewActivity.adapterNotify.getCount(); i++) {
            View listItem = DetailViewActivity.adapterNotify.getView(i, null, lv_repeat);
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

    @Override
    protected void onResume() {
        super.onResume();
        if (!isPageLoaded) {
            DownloadDDayDetail downloadDataAsync = new DownloadDDayDetail(this, ddayId, this);
            downloadDataAsync.execute();
            isPageLoaded = true;
        }
    }

    @Override
    public void intializeDdayDetail(DDayDetailModel dDayDetailModel) {
//        Log.d(TAG,"dDayDetailModel:"+new Gson().toJson(dDayDetailModel));
        this.ddayDetailModel = dDayDetailModel;

//        String json = dDayDetailModel.getStringRepeatOptions();
//        Log.e(TAG,dDayDetailModel.getGroupName());

        HomeActivity.instance.refreshCountOfUnreadDays();
    }

    boolean canEdit = false;

    @Override
    public void onError() {
        if (Utils.isNetworkAvailable()) {
            Toast.makeText(this, R.string.message_network_unstable, Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, R.string.message_network_checking, Toast.LENGTH_LONG).show();
        }

        finish();
    }

    @Override
    public void updateUI() {
//        Log.d(TAG,"updateUI:"+new Gson().toJson(ddayDetailModel));
        canEdit = ddayDetailModel.isCanEdit();
        CanManage = ddayDetailModel.isCanManage();
        CanHide = ddayDetailModel.isCanHide();
        Log.d(TAG, "updateUI canEdit:" + canEdit);
        Log.d(TAG, "updateUI CanManage:" + CanManage);
        Log.d(TAG, "updateUI CanHide:" + CanHide);
        if (!canEdit) {
            MenuItem item1 = menu.findItem(R.id.menu_edit);
            MenuItem item2 = menu.findItem(R.id.menu_delete);
            item1.setVisible(false);
            item2.setVisible(false);
            sp_dday_type.setBackgroundColor(Color.WHITE);
            sp_dday_type.setClickable(false);
            sp_dday_type.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    if (event.getAction() == MotionEvent.ACTION_DOWN) {
                        cannot_edit();
                    }
                    return true;
                }
            });
        } else {
            img_plus.setVisibility(View.VISIBLE);
        }
        ConnectionUtils.getInstance().GetNotification(ddayId, new GetNotifyTime() {
            @Override
            public void onGetNotifyTimeSuccess(ArrayList<NotifyTime> a) {
//                Log.d(TAG,"onGetNotifyTimeSuccess:"+new Gson().toJson(a));
                if (a.size() > 0)
                    jsonCars = new Gson().toJson(a);
                create_lv_notify(a, canEdit);

            }
        });
        //Update top date view
        String newDayFormat = TimeUtils.convertToDayOfWeek(ddayDate);
        tvDate.setText(newDayFormat);

        //Update title view
        String title = ddayDetailModel.getTitle();
        if (title.equals(Cons.NULL_RESPONSE)) {
            hideLayout(titleContainer);
        } else {
            tvTitle.setText(title);
        }

        //Update content view
        String content = ddayDetailModel.getContent();
        if (content.equals(Cons.NULL_RESPONSE)) {
            hideLayout(commentContainer);

        } else {
            img_plus.setImageResource(R.drawable.ic_arrow_drop_up);
            tvContent.setText(content);
            flag_title = true;
        }

        //Update writer view////////////////////////
        tvWriter.setText(ddayDetailModel.getWriter());

        // Update sharer view////////////////////
        ArrayList<Employee> shareList = ddayDetailModel.getSharersList();
        if (shareList.size() == 0) {
//            hideLayout(sharerContainer);
            ln_checkbox.setVisibility(View.GONE);
        } else {
            String title_2 = "";
            resultList = new ArrayList<>();
            ArrayList<String> arrayList = new ArrayList<>();
            for (int i = 0; i < shareList.size(); i++) {
                String name = shareList.get(i).getUserName();
                if (!arrayList.contains(name)) {
                    arrayList.add(name);
                    PersonData personData = new PersonData();
                    personData.setFullName(shareList.get(i).getUserName());
                    personData.setDepartNo(shareList.get(i).getDepartNo());
                    personData.setUserNo(shareList.get(i).getUserNo());
                    personData.setDepartName(shareList.get(i).getDepartName());
                    resultList.add(personData);
                }
            }
            String serverLink = HttpRequest.getInstance().getServiceDomain();
            ArrayList<PersonData> result = OrganizationUserDBHelper.getAllOfOrganization(serverLink);

            if (resultList != null && resultList.size() != 0) {
                for (int i = 0; i < result.size(); i++) {
                    PersonData data = result.get(i);
                    int UserNo = data.getUserNo();
                    if (UserNo == 0) {
                        if (ListUtils.check_root_folder(resultList, data)) {
                            resultList.add(data);
                        }
                    } else {
                        if (ListUtils.check_root_user(resultList, data)) {
                            resultList.add(data);
                        }
                    }
                }
            }

            JSON_SHARER_TYPE = new Gson().toJson(resultList);
            for (int i = 0; i < arrayList.size(); i++) {
                String name = arrayList.get(i);
                if (i == arrayList.size() - 1) {
                    title_2 += name;
                } else {
                    title_2 += name + ", ";
                }
            }
            tvSharer.setText(ListUtils.getListName(resultList));
//            tvSharer.setText(title_2);
            tvSharer.setSelected(true);
        }
        // Update director view/////////////////
        int directorNo = ddayDetailModel.getDirectorNo();
        if (directorNo == id || !CanManage)
            tv_myself.setVisibility(View.GONE);
        if (directorNo == 0) {
//            hideLayout(personConcernedContainer);
        } else {
            int DepartNoOfDirector = ddayDetailModel.getDirectorDepartNo();
            String directorName = ddayDetailModel.getDirectorUsername();
            tvPersonConcerned.setText(directorName);
            resultList2 = new ArrayList<>();
            PersonData personData = new PersonData();
            personData.setFullName(directorName);
            personData.setDepartNo(DepartNoOfDirector);
            personData.setUserNo(directorNo);
            resultList2.add(personData);
            JSON_PERSON_CONCERNED_TYPE = new Gson().toJson(resultList2);
        }

        //Update manager view (co-admin)/////////////////////
        ArrayList<Employee> managerList = ddayDetailModel.getManagersList();
        if (managerList.size() == 0) {
//            hideLayout(adminContainer);
        } else {
            String title_managerList = "";
            resultList3 = new ArrayList<>();
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

        //Update make completion view
        final String ddayType = ddayDetailModel.getDdayType();
        int userid = ddayDetailModel.getRegUserNo();

        if (ddayType.equals("D-Day")) {
//            hideLayout(cbMakeCompletion);

            sp_ddaytype_int = 0;
        } else {
            sp_ddaytype_int = 1;
            //if (id != userid && Cons.check_admin(id, resultList3) == false || IsCompleted) {
            //      hideLayout(cbMakeCompletion);
            //} else {
            //}
        }

        //if (id == userid || Cons.check_admin(id, resultList3) == true || IsHidden) {
        //}

        if (ddayType.equalsIgnoreCase("Completion D-Day")) {
            DetailViewActivity.sp_ddaytype_int = 0;
        } else if (ddayType.equalsIgnoreCase("D+Day")) {
            DetailViewActivity.sp_ddaytype_int = 1;
            notify.setVisibility(View.GONE);
        } else {
            DetailViewActivity.sp_ddaytype_int = 0;
        }

        if (remainingDays > 0) {
            tvRemainingDays.setText("D+" + remainingDays);
            if (CanHide)
                tvRemainingDays.setTextColor(Color.parseColor(Cons.COLOR_CAN_HIDDEN));
            else
                tvRemainingDays.setTextColor(Color.parseColor(Cons.COLOR_RED));
        } else if (remainingDays < 0) {
            tvRemainingDays.setText("D" + remainingDays);
            tvRemainingDays.setTextColor(Color.parseColor(Cons.COLOR_DAY_PLUS));
        } else {
            tvRemainingDays.setText("D-Day");
            tvRemainingDays.setTextColor(Color.parseColor(Cons.COLOR_DDAY));
        }
        if (HomeActivity.currentFragmentPos == 1) {
            if (remainingDays == 1)
                tvRemainingDays.setText(getResources().getString(R.string.startPlus));
            tvRemainingDays.setTextColor(Color.parseColor(Cons.COLOR_DAY_PLUS));
        }
        detailViewContainer.setVisibility(View.VISIBLE);
        ConnectionUtils.getInstance().getInstance().getgroup(this);

        String json = ddayDetailModel.getStringRepeatOptions();
        json = json.replaceAll("(^\\s+|\\s+$)", "");
        String str[] = json.substring(1, ddayDetailModel.getStringRepeatOptions().length() - 1).split("\\},\\{");
        ArrayList<String> arrayList = new ArrayList<>();
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
        if (repeatOption_daily.length() > 0
                || repeatOption_week.length() > 0
                || repeatOption_month.length() > 0
                || repeatOption_year.length() > 0) {
            sp_dday_type.setBackgroundColor(Color.WHITE);
            sp_dday_type.setClickable(false);
            sp_dday_type.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
//                    if (event.getAction() == MotionEvent.ACTION_DOWN) {
//                        cannot_edit();
//                    }
                    return true;
                }
            });
        } else {
        }

        if (repeatOption_special.length() > 0)
            repeatOption_special = repeatOption_special.substring(0, repeatOption_special.length() - 1);
        if (repeatOption_daily.length() > 0)
            repeatOption_daily = repeatOption_daily.substring(0, repeatOption_daily.length() - 1);
        if (repeatOption_week.length() > 0)
            repeatOption_week = repeatOption_week.substring(0, repeatOption_week.length() - 1);
        if (repeatOption_month.length() > 0)
            repeatOption_month = repeatOption_month.substring(0, repeatOption_month.length() - 1);
        if (repeatOption_year.length() > 0)
            repeatOption_year = repeatOption_year.substring(0, repeatOption_year.length() - 1);
        str1 = repeatOption_special;
        str2 = repeatOption_daily;
        str3 = repeatOption_week;
        str4 = repeatOption_month;
        str5 = repeatOption_year;
//        Log.e(TAG, repeatOption_special);
        if (repeatOption_special.contains("CheckDPlusDay")) {
            Object_CheckDPlusDay ob = new Gson().fromJson(repeatOption_special, Object_CheckDPlusDay.class);
            String StartDate = ob.getStartDate();
            Lunar_sp = ob.getLunar();
            sa_y = Integer.parseInt(StartDate.substring(0, 4));
            sa_m = Integer.parseInt(StartDate.substring(4, 6));
            sa_d = Integer.parseInt(StartDate.substring(6, 8));

        } else if (repeatOption_special.contains("CheckSpecificDday")) {
            String _json = "[" + repeatOption_special + "]";
            Type type = new TypeToken<ArrayList<Object_CheckSpecificDday>>() {
            }.getType();
            ArrayList<Object_CheckSpecificDday> arrayList1 = new Gson().fromJson(_json, type);
            Object_CheckSpecificDday ob = arrayList1.get(arrayList1.size() - 1);
            String SpecificDay = ob.getSpecificDay();
            sa_y = Integer.parseInt(SpecificDay.substring(0, 4));
            sa_m = Integer.parseInt(SpecificDay.substring(4, 6));
            sa_d = Integer.parseInt(SpecificDay.substring(6, 8));
        }
        //Update repeat view
        String repeatString = ddayDetailModel.getRepeatString().trim();
        if (repeatString != Cons.NULL_RESPONSE) {
//            Log.e(TAG,"repeatString:"+repeatString);
            tvRepeat.setText(repeatString);
        } else {
            hideLayout(repeatContainer);
        }
//        Log.e(TAG, repeatOption_special);
        sp_dday_type.setSelection(DetailViewActivity.sp_ddaytype_int);
        sp_dday_type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                sp_ddaytype_int = position;
                if (position == 0) {
                    dday_minus();
                } else {
                    dday_plus();
                }
//                if (temp_text > 0)
                setTitleRepeat(tvRepeat);

//                temp_text++;
                if (ddayType.equalsIgnoreCase("D+Day") && temp_pl == 0) {
                    temp_pl++;
                } else {
                    _display_item();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        if (CanManage) {
            if (IsHidden || IsCompleted) {
                if (IsHidden) {
                    if (menu_un_hidden != null) menu_un_hidden.setVisible(true);
                } else {
                    if (HomeActivity.currentFragmentPos != 1) {
                        if (menu_un_comp != null) menu_un_comp.setVisible(true);
                    }
                }
            } else {
                if (CanHide) {
                    if (mn_hide != null) mn_hide.setVisible(true);
                } else {
                    if (HomeActivity.currentFragmentPos != 1) {
                        if (menu_completed != null) menu_completed.setVisible(true);
                    }
                }
            }
            if (menu_delete != null) menu_delete.setVisible(true);
        } else {
            if (CanHide) {
                if (!IsHidden) {
                    if (mn_hide != null) mn_hide.setVisible(true);
                } else {
                    if (menu_un_hidden != null) menu_un_hidden.setVisible(true);
                }
            }
        }
    }

    int temp_pl = 0;

    private void hideLayout(View view) {
        view.setVisibility(View.GONE);
    }

    private void showDialog() {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_make_completion);

        final TextView tvCancel = (TextView) dialog.findViewById(R.id.txt_cancel);
        final TextView tvConfirm = (TextView) dialog.findViewById(R.id.txt_confirm);
        final EditText edtComment = (EditText) dialog.findViewById(R.id.edt_comment);

        tvConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String content = edtComment.getText().toString().trim();

                if (TextUtils.isEmpty(content)) {
                    edtComment.setError("Please type something");
                } else {
                    ConnectionUtils.getInstance().insertCompletedRecord(ddayId, ddayDate, content, new MakeComplete() {
                        @Override
                        public void onSuccessMakeComplete(long dataNo) {
                            dialog.dismiss();
                            goToHome();
                        }
                    });
                }
            }
        });

        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        dialog.show();
        dialog.setOnKeyListener(new Dialog.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    dialog.dismiss();
//                    Log.e(TAG,"KEYCODE_BACK");
                }
                return true;
            }
        });

    }

    MenuItem item;

    public void _display_item() {
        boolean canEdit = ddayDetailModel.isCanEdit();
        if (canEdit) {
            if (!item.isVisible())
                item.setVisible(true);
        }
    }

    MenuItem mn_hide, menu_un_comp, menu_un_hidden, menu_completed, menu_delete;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        this.menu = menu;
        getMenuInflater().inflate(R.menu.menu_detail_view, menu);
        if (menu != null) {
            menu_completed = menu.findItem(R.id.menu_complete);
            menu_un_comp = menu.findItem(R.id.menu_un_comp);
            mn_hide = menu.findItem(R.id.menu_hide);
            menu_un_hidden = menu.findItem(R.id.menu_un_hidden);
            menu_delete = menu.findItem(R.id.menu_delete);
            menu_completed.setVisible(false);
            menu_un_comp.setVisible(false);
            mn_hide.setVisible(false);
            menu_un_hidden.setVisible(false);
            menu_delete.setVisible(false);

            item = menu.findItem(R.id.menu_edit);
            item.setVisible(false);
        }
        return true;
    }

    void actionEdit() {
        if (sp_ddaytype_int == 0) {
            typeNo = 2;
        } else {
            typeNo = 3;
        }
        updateDday(groupNo, tvTitle.getText().toString(), tvContent.getText().toString(), ddayId);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.menu_delete) {
            AlertDialog.Builder adb = new AlertDialog.Builder(this);
            adb.setTitle(getResources().getString(R.string.Dday));
            adb.setMessage(getResources().getString(R.string.sure_delete));
            adb.setPositiveButton(getResources().getString(R.string.ok), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    ConnectionUtils.getInstance().delete_v2(ddayId, new DeleteSuccess() {
                        @Override
                        public void onDeleteSuccess() {
                            HomeDDayBoardAdapter adapter;

                            if (HomeActivity.currentFragmentPos == 0) {
                                adapter = FragmentHomeAll.instance.mDDayListAdapter;
                            } else {
                                adapter = FragmentHomePlus.instance.mDDayListAdapter;
                            }

                            ArrayList<DDayModel> list = adapter.list;
                            int length = list.size();

                            DDayModel dDayModel = null;

                            for (int i = 0; i < length; i++) {
                                if (list.get(i).getDdayId() == ddayId) {
                                    dDayModel = list.get(i);
                                }
                            }

                            ArrayList<DDayModel> listOfModels = new ArrayList<>();

                            long ddayNo = dDayModel.getDdayId();

                            for (DDayModel model : list) {
                                if (model.getDdayId() == ddayNo)
                                    listOfModels.add(model);
                            }

                            if (listOfModels.size() == list.size()) {
                                if (HomeActivity.currentFragmentPos == 0) {
                                    FragmentHomeAll.instance.refreshList();
                                } else if (HomeActivity.currentFragmentPos == 1) {
                                    FragmentHomePlus.instance.refreshList();
                                }
                            }
                            if (list.size() - listOfModels.size() < 30) {
                                int index;
                                for (DDayModel model : listOfModels) {
                                    index = list.indexOf(model);
                                    list.remove(model);

                                    adapter.notifyItemRemoved(index);
                                }

                                if (HomeActivity.currentFragmentPos == 0) {
                                    FragmentHomeAll.instance.showMoreProgressBar(false);
                                }
                            } else {
                                int index;
                                for (DDayModel model : listOfModels) {
                                    index = list.indexOf(model);
                                    list.remove(model);

                                    adapter.notifyItemRemoved(index);
                                }
                            }

                            goToHome();
                        }
                    });
                }
            });

            adb.setNegativeButton(getResources().getString(R.string.cancel), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });

            adb.create().show();

            return true;
        } else if (id == R.id.menu_edit) {
            Log.d(TAG, "menu_edit");
//            if (sp_ddaytype_int == 0) {
//                typeNo = 2;
//            } else {
//                typeNo = 3;
//            }
//            updateDday(groupNo, tvTitle.getText().toString(), tvContent.getText().toString(), ddayId);
            actionEdit();
            return true;
        } else if (id == R.id.menu_complete) {
            showDialog();
        } else if (id == R.id.menu_hide) {
            if (CanManage) {
                insertCompletedRecord();
            } else {
                insertCoveredDay();
            }
        } else if (id == R.id.menu_un_comp) {
            ConnectionUtils.getInstance().DeleteCompletedRecord(CompletedRecordNo, new DeleteCompletedRecordSuccess() {
                @Override
                public void onDeleteCompletedRecordSuccess() {
                    HomeDDayBoardAdapter adapter;

                    if (HomeActivity.currentFragmentPos == 0) {
                        adapter = FragmentHomeAll.instance.mDDayListAdapter;
                    } else {
                        adapter = FragmentHomePlus.instance.mDDayListAdapter;
                    }

                    ArrayList<DDayModel> listOfModels = adapter.list;
                    int length = listOfModels.size();

                    DDayModel dDayModel = null;
                    int pos = 0;

                    for (int i = 0; i < length; i++) {
                        if (listOfModels.get(i).getDdayId() == ddayId) {
                            dDayModel = listOfModels.get(i);
                            pos = i;
                        }
                    }

                    if (HomeActivity.filterTypeForList == HomeActivity.InProgress) {
                        listOfModels.remove(dDayModel);
                        adapter.notifyItemRemoved(pos);
                    } else if (HomeActivity.filterTypeForList == (HomeActivity.InProgress | HomeActivity.Completed | HomeActivity.Hiden)) {
                        dDayModel.setIsCompleted(false);
                        adapter.notifyItemChanged(pos);
                    }

                    goToHome();
                }
            });
        } else if (id == R.id.menu_un_hidden) {
            ConnectionUtils.getInstance().DeleteCoveredDay(HiddenDataNo, new DeleteCoveredDaySuccess() {
                @Override
                public void onDeleteCoveredDaySuccess() {
                    HomeDDayBoardAdapter adapter;

                    if (HomeActivity.currentFragmentPos == 0) {
                        adapter = FragmentHomeAll.instance.mDDayListAdapter;
                    } else {
                        adapter = FragmentHomePlus.instance.mDDayListAdapter;
                    }

                    ArrayList<DDayModel> listOfModels = adapter.list;
                    int length = listOfModels.size();

                    DDayModel dDayModel = null;
                    int pos = 0;

                    for (int i = 0; i < length; i++) {
                        if (listOfModels.get(i).getDdayId() == ddayId) {
                            dDayModel = listOfModels.get(i);
                            pos = i;
                        }
                    }

                    if (HomeActivity.filterTypeForList == HomeActivity.InProgress) {
                        listOfModels.remove(dDayModel);
                        adapter.notifyItemRemoved(pos);
                    } else if (HomeActivity.filterTypeForList == (HomeActivity.InProgress | HomeActivity.Completed | HomeActivity.Hiden)) {
                        dDayModel.setIsHidden(false);
                        adapter.notifyItemChanged(pos);
                    }

                    goToHome();
                }
            });
        }

        return super.onOptionsItemSelected(item);
    }

    String jsonCars = "";

    @Override
    public void onStop() {
        super.onStop();
        isPageLoaded = true;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        isPageLoaded = false;
    }

    ArrayList<Integer> sp_GroupNo = new ArrayList<>();
    ArrayList<String> sp_Name = new ArrayList<>();
    public int groupNo = 0;
    TextView tv_group;

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
            for (int i = 0; i < listDtos.size(); i++) {
                if (ddayDetailModel.getGroupName().equalsIgnoreCase(listDtos.get(i).getName())) {
                    groupNo = listDtos.get(i).getGroupNo();
                    tv_group.setText(sp_Name.get(i));

                }
            }
        }
    }

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
                if (groupNo != sp_GroupNo.get(position)) {
                    _display_item();
                    if (!canEdit)
                        Toast.makeText(getApplicationContext(), getResources().getString(R.string.updatesuccess), Toast.LENGTH_SHORT).show();
                }
                groupNo = sp_GroupNo.get(position);
                tv_group.setText(sp_Name.get(position));
                dialog.dismiss();


            }
        });
        dialog.show();
    }

    public void dialog_repeat() {
        final Dialog dialog = new Dialog(this);
        dialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setContentView(R.layout.layout_dialog_repeat);
        loadTabs(dialog);
//        Log.e("dialog_repea:", repeatOption_special);
        init_view_tab(dialog);
        TextView btnCancel = (TextView) dialog.findViewById(R.id.btnCancel);
        TextView btnOk = (TextView) dialog.findViewById(R.id.btnOk);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (special_rb1.isChecked()) Lunar_sp = 1;
                else if (special_rb2.isChecked()) Lunar_sp = 0;
                else Lunar_sp = -1;
                repeatOption_special = "";
                repeatOption_daily = "";
                repeatOption_week = "";
                repeatOption_month = "";
                repeatOption_year = "";
                if (v1_cb1.isChecked())
                    getJsonSpecial();
                if (v2_cb1.isChecked())
                    getJsonDaily();
                if (v3_cb1.isChecked())
                    getJsonWeek();
                if (v4_cb1.isChecked())
                    getJsonMonth();
                if (v5_cb1.isChecked())
                    getJsonAnnnually();
                setTitleRepeat(tvRepeat);

                if (!str1.equals(repeatOption_special) || !str2.equals(repeatOption_daily) || !str3.equals(repeatOption_week) ||
                        !str4.equals(repeatOption_month) || !str5.equals(repeatOption_year))
                    _display_item();
                dialog.dismiss();
            }
        });
        dialog.show();
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

    public void _DisableCheckBos() {
        v2_cb1.setEnabled(false);
        v3_cb1.setEnabled(false);
        v4_cb1.setEnabled(false);
        v5_cb1.setEnabled(false);
    }

    public void _EnableCheckBos() {
        v2_cb1.setEnabled(true);
        v3_cb1.setEnabled(true);
        v4_cb1.setEnabled(true);
        v5_cb1.setEnabled(true);
    }

    public void loadTabs(Dialog dialog) {
        stackList = new ArrayList<>();
        tabsHorizontalScrollView = (HorizontalScrollView) dialog.findViewById(R.id.tabsHorizontalScrollView);
        tab = (TabHost) dialog.findViewById(R.id.tabhost_day);
        tab.setup();
        TabHost.TabSpec spec;

        View v1 = LayoutInflater.from(this).inflate(R.layout.title_tabhost, null);
        v1_cb1 = (CheckBox) v1.findViewById(R.id.checkBox);
        TextView v1_tv1 = (TextView) v1.findViewById(R.id.tv_cb);
        v1_tv1.setText(getResources().getString(R.string.special_day));

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
                tab.setCurrentTab(Cons.INDEX_SPECIAL);
                if (sp_ddaytype_int == 1) {
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
                if (sp_ddaytype_int == 1) {
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
                if (sp_ddaytype_int == 1) {
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
                if (sp_ddaytype_int == 1) {
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
                if (sp_ddaytype_int == 1) {
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
                if (sp_ddaytype_int == 1) {
                    tab.setCurrentTab(0);
                } else {
                    for (int i = 0; i < tab.getTabWidget().getChildCount(); i++) {
                        tab.getTabWidget().getChildAt(i).setBackgroundResource(R.drawable.tab_not_choose); // unselected
                    }
                    tab.getTabWidget().getChildAt(tab.getCurrentTab()).setBackgroundResource(R.drawable.tab_choose); // selected
                }
            }
        });
        if (repeatOption_special.length() > 0) {
            v1_cb1.setChecked(true);
            tab.setCurrentTab(Cons.INDEX_SPECIAL);
            Log.d(TAG, "v1_cb1 setChecked");
            stackList.add(Cons.INDEX_SPECIAL);
        }
        if (repeatOption_daily.length() > 0) {
            v2_cb1.setChecked(true);
            tab.setCurrentTab(Cons.INDEX_DAILY);
            Log.d(TAG, "v2_cb1 setChecked");
            stackList.add(Cons.INDEX_DAILY);
        }
        if (repeatOption_week.length() > 0) {
            v3_cb1.setChecked(true);
            tab.setCurrentTab(Cons.INDEX_WEEK);
            Log.d(TAG, "v3_cb1 setChecked");
            stackList.add(Cons.INDEX_WEEK);
        }
        if (repeatOption_month.length() > 0) {
            v4_cb1.setChecked(true);
            tab.setCurrentTab(Cons.INDEX_MONTH);
            Log.d(TAG, "v4_cb1 setChecked");
            stackList.add(Cons.INDEX_MONTH);
        }
        if (repeatOption_year.length() > 0) {
            v5_cb1.setChecked(true);
            tab.setCurrentTab(Cons.INDEX_YEAR);
            Log.d(TAG, "v5_cb1 setChecked");
            stackList.add(Cons.INDEX_YEAR);
        }
        if (sp_ddaytype_int == 0) {
            _EnableCheckBos();
        } else {
            _DisableCheckBos();
        }
    }

    public void setMarginBottomListNotify(int dimens) {
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) notify.getLayoutParams();
        params.bottomMargin = dimens;
        notify.setLayoutParams(params);
    }

    public void setTabWhenPLusCheck() {
        tab.setCurrentTab(0);
        for (int i = 0; i < tab.getTabWidget().getChildCount(); i++) {
            tab.getTabWidget().getChildAt(i).setBackgroundResource(R.drawable.tab_not_choose); // unselected
        }
        tab.getTabWidget().getChildAt(0).setBackgroundResource(R.drawable.tab_choose); // selected
    }

    public String repeatOption_special = "", repeatOption_daily = "", repeatOption_week = "", repeatOption_month = "", repeatOption_year = "";
    private String str1 = "", str2 = "", str3 = "", str4 = "", str5 = "";

    public void init_view_tab(Dialog dialog) {
        dialog_special(dialog);
        dialog_day(dialog);
        dialog_week(dialog);
        dialog_month(dialog);
        dialog_annually(dialog);
    }

    public static ArrayList<String> arr_lv_special = new ArrayList<>();
    public static ArrayList<String> arr_temp = new ArrayList<>();
    public static int year, month_special, date_special;
    RadioButton special_rb1, special_rb2, special_rb3;
    public static DT_SpecialAdapter specialAdapter;
    public static ListView special_lv_day;
    int day_of_week;
    public static int sa_y, sa_m, sa_d;

    public void dialog_special(Dialog dialog) {
        arr_lv_special.clear();
        special_lv_day = (ListView) dialog.findViewById(R.id.special_lv_day);
        special_rb1 = (RadioButton) dialog.findViewById(R.id.special_rb1);
        special_rb2 = (RadioButton) dialog.findViewById(R.id.special_rb2);
        special_rb3 = (RadioButton) dialog.findViewById(R.id.special_rb3);
        Calendar calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month_special = calendar.get(Calendar.MONTH) + 1;
        date_special = calendar.get(Calendar.DAY_OF_MONTH);
        day_of_week = calendar.get(Calendar.DAY_OF_WEEK);
//        Log.e("dialog_special", repeatOption_special);
        if (repeatOption_special.length() == 0) {
            arr_lv_special.add(year + "-" + Cons.getfulldate(month_special) + "-" + Cons.getfulldate(date_special));
            special_rb1.setChecked(true);
        } else {
            if (repeatOption_special.contains(Cons.CheckDPlusDay)) {
//                Log.e("Contain CheckDPlusDay", repeatOption_special);
                Object_CheckDPlusDay ob = new Gson().fromJson(repeatOption_special, Object_CheckDPlusDay.class);
                int Lunar = ob.getLunar();
//                Log.e(TAG, "Contain CheckDPlusDay:" + Lunar);
                if (Lunar == 0) special_rb2.setChecked(true);
                else if (Lunar == -1) special_rb3.setChecked(true);
                else special_rb1.setChecked(true);
                arr_lv_special.add(Cons.formmat_date(ob.getStartDate()));
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
                    arr_lv_special.add(Cons.formmat_date(arrayList_s.get(i).getSpecificDay()));
                }
            }
        }

        special_lv_day.setAdapter(specialAdapter);
        View item = DetailViewActivity.specialAdapter.getView(0, null, DetailViewActivity.special_lv_day);
        item.measure(0, 0);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, (arr_lv_special.size() * item.getMeasuredHeight()));
        DetailViewActivity.special_lv_day.setLayoutParams(params);
    }

    int HolidayCondition = 1, HolidayCondition_w = 1, HolidayCondition_m = 1, HolidayCondition_y = 1;
    String StartOption = "D";
    TextView dl_start, dl_end, w_start, w_end, m_start, m_end, y_start, y_end;
    CheckBox dl_loop, w_loop, m_loop, y_loop;
    int hld[] = {1, 2, 3, 4};
    CheckBox cb_mon, cb_tue, cb_wed, cb_thu, cb_fri, cb_sat, cb_sun;
    int Interval = 1, Interval_w = 1, Interval_m = 1, Interval_y = 1;

    public void dialog_day(Dialog dialog) {
        int p = 0, so = 0, hd = 0;
        View view_start = (View) dialog.findViewById(R.id.view_start);
        View view_end = (View) dialog.findViewById(R.id.view_end);
        dl_start = (TextView) view_start.findViewById(R.id.txt_start_day_chose);
        dl_end = (TextView) view_end.findViewById(R.id.txt_start_day_chose);
        dl_loop = (CheckBox) dialog.findViewById(R.id.cb_infiniteloop);
        final String ngay = "" + year + "-" + Cons.getfulldate(month_special) + "-" + Cons.getfulldate(date_special);
        dl_end.setText(Cons.date_finish);
        dl_end.setTextColor(Color.parseColor("#FFFFFF"));
        dl_loop.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    dl_end.setText(Cons.date_finish);
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
            if (inter == 1000) p = _Application.getInstance().arrayList_frequency().length - 1;
            else if (inter == 100) p = _Application.getInstance().arrayList_frequency().length - 2;
            else p = ob.getInterval() - 1;
            String StartOption = ob.getStartOption();
            if (StartOption.equalsIgnoreCase("Y")) so = 1;
            else if (StartOption.equalsIgnoreCase("M")) so = 2;
            else if (StartOption.equalsIgnoreCase("W")) so = 3;
            hd = ob.getHolidayCondition() - 1;
            dl_start.setText(Cons.formmat_date(ob.getStartDate()));
            if (ob.getEndDate().length() > 0) {
                dl_loop.setChecked(false);
                dl_end.setText(Cons.formmat_date(ob.getEndDate()));
                dl_end.setTextColor(Color.parseColor("#000000"));
            }
        } else {
            dl_start.setText(ngay);
        }
        View view1 = (View) dialog.findViewById(R.id.view1);
        TextView tv_frequency = (TextView) view1.findViewById(R.id.tv_item);
        Spinner sp_frequency = (Spinner) view1.findViewById(R.id.sp_item);
        tv_frequency.setText(getResources().getString(R.string.frequency));
        ArrayAdapter adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, _Application.getInstance().arrayList_frequency());
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_frequency.setAdapter(adapter);
        sp_frequency.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == _Application.getInstance().arrayList_frequency().length - 1)
                    Interval = 1000;
                else if (position == _Application.getInstance().arrayList_frequency().length - 2)
                    Interval = 100;
                else Interval = position + 1;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        sp_frequency.setSelection(p);
        View view2 = (View) dialog.findViewById(R.id.view2);
        TextView tv_option = (TextView) view2.findViewById(R.id.tv_item);
        Spinner sp_option = (Spinner) view2.findViewById(R.id.sp_item);
        tv_option.setText(getResources().getString(R.string.option));

        ArrayAdapter adapter_option = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, _Application.getInstance().arrayList_option());
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
        View view3 = (View) dialog.findViewById(R.id.view3);
        TextView tv_holiday = (TextView) view3.findViewById(R.id.tv_item);
        Spinner sp_holiday = (Spinner) view3.findViewById(R.id.sp_item);
        tv_holiday.setText(getResources().getString(R.string.holiday));

        ArrayAdapter adapter_holiday = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, _Application.getInstance().arrayList_holiday());
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
                year = year;
                month_special = monthOfYear;
                date_special = dayOfMonth;
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
        }, year, month_special - 1, date_special);
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
                        year = year;
                        month_special = monthOfYear;
                        date_special = dayOfMonth;
                    } else {
                        tv.setText("" + tv2.getText().toString());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, year, month_special - 1, date_special);
        dpd.show();
    }

    public void dialog_week(Dialog dialog) {
        View time_week = (View) dialog.findViewById(R.id.time_week);
        View view_start = (View) time_week.findViewById(R.id.view_start);
        View view_end = (View) time_week.findViewById(R.id.view_end);
        w_start = (TextView) view_start.findViewById(R.id.txt_start_day_chose);
        w_end = (TextView) view_end.findViewById(R.id.txt_start_day_chose);
        w_loop = (CheckBox) time_week.findViewById(R.id.cb_infiniteloop);
        final String ngay = "" + Cons.getfulldate(year) + "-" + Cons.getfulldate(month_special) + "-" + Cons.getfulldate(date_special);
        w_end.setText(Cons.date_finish);
        w_end.setTextColor(Color.parseColor("#FFFFFF"));
        w_loop.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    w_end.setText(Cons.date_finish);
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
        cb_mon = (CheckBox) dialog.findViewById(R.id.cb_mon);
        cb_tue = (CheckBox) dialog.findViewById(R.id.cb_tue);
        cb_wed = (CheckBox) dialog.findViewById(R.id.cb_wed);
        cb_thu = (CheckBox) dialog.findViewById(R.id.cb_thu);
        cb_fri = (CheckBox) dialog.findViewById(R.id.cb_fri);
        cb_sat = (CheckBox) dialog.findViewById(R.id.cb_sat);
        cb_sun = (CheckBox) dialog.findViewById(R.id.cb_sun);
        int p = 0, hd = 0;
        if (repeatOption_week.length() > 0) {
            Object_CheckEveryDdayOfWeek ob = new Gson().fromJson(repeatOption_week, Object_CheckEveryDdayOfWeek.class);
            p = ob.getInterval() - 1;
            hd = ob.getHolidayCondition() - 1;
            w_start.setText(Cons.formmat_date(ob.getStartDate()));
            if (ob.getSpecificDayOfWeek().contains("0")) cb_sun.setChecked(true);
            if (ob.getSpecificDayOfWeek().contains("1")) cb_mon.setChecked(true);
            if (ob.getSpecificDayOfWeek().contains("2")) cb_tue.setChecked(true);
            if (ob.getSpecificDayOfWeek().contains("3")) cb_wed.setChecked(true);
            if (ob.getSpecificDayOfWeek().contains("4")) cb_thu.setChecked(true);
            if (ob.getSpecificDayOfWeek().contains("5")) cb_fri.setChecked(true);
            if (ob.getSpecificDayOfWeek().contains("6")) cb_sat.setChecked(true);
            if (ob.getEndDate().length() > 0) {
                w_loop.setChecked(false);
                w_end.setText(Cons.formmat_date(ob.getEndDate()));
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

        View view1_week = (View) dialog.findViewById(R.id.view1_week);
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
        View view3_week = (View) dialog.findViewById(R.id.view2_week);
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

    public static DT_MonthAdapter monthAdapter;
    public static int week_of_month;
    public static int year_month, month_month, date_month, date_of_week_month;
    public static ArrayList<ObjectAddMonth> arrayList_month;

    public void dialog_month(Dialog dialog) {
        ListView lv_month = (ListView) dialog.findViewById(R.id.lv_month);
        arrayList_month = new ArrayList<ObjectAddMonth>();
        Calendar calendar = Calendar.getInstance();
        year_month = calendar.get(Calendar.YEAR);
        month_month = calendar.get(Calendar.MONTH);
        date_month = calendar.get(Calendar.DAY_OF_MONTH) - 1;
        week_of_month = calendar.get(Calendar.WEEK_OF_MONTH);
        date_of_week_month = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        View time_month = (View) dialog.findViewById(R.id.time_month);
        View view_start = (View) time_month.findViewById(R.id.view_start);
        View view_end = (View) time_month.findViewById(R.id.view_end);
        m_start = (TextView) view_start.findViewById(R.id.txt_start_day_chose);
        m_end = (TextView) view_end.findViewById(R.id.txt_start_day_chose);
        m_loop = (CheckBox) time_month.findViewById(R.id.cb_infiniteloop);
        final String ngay = "" + year + "-" + Cons.getfulldate(month_special) + "-" + Cons.getfulldate(date_special);
        m_end.setText(Cons.date_finish);
        m_end.setTextColor(Color.parseColor("#FFFFFF"));
        m_loop.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    m_end.setText(Cons.date_finish);
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
            m_start.setText(Cons.formmat_date(arrayList.get(0).StartDate));
            if (arrayList.get(0).EndDate.length() > 0) {
                m_loop.setChecked(false);
                m_end.setText(Cons.formmat_date(arrayList.get(0).EndDate));
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
        monthAdapter = new DT_MonthAdapter(this, arrayList_month, lv_month);
        lv_month.setAdapter(monthAdapter);

        int totalHeight = lv_month.getPaddingTop() + lv_month.getPaddingBottom();
        int desiredWidth = View.MeasureSpec.makeMeasureSpec(lv_month.getWidth(), View.MeasureSpec.AT_MOST);

        for (int i = 0; i < DetailViewActivity.monthAdapter.getCount(); i++) {
            View listItem = DetailViewActivity.monthAdapter.getView(i, null, lv_month);
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

        View view1_month = (View) dialog.findViewById(R.id.view1_month);
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
        View view3_month = (View) dialog.findViewById(R.id.view2_month);
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

    public static int month_year, dayOfWeek_year, day_year;
    public static ObjectAddYear objectAddYear;
    public static ArrayList<ObjectAddYear> arrayList_year;
    public static DT_YearAdapter yearAdapter;

    public void dialog_annually(Dialog dialog) {
        View time_y = (View) dialog.findViewById(R.id.time_y);
        View view_start = (View) time_y.findViewById(R.id.view_start);
        View view_end = (View) time_y.findViewById(R.id.view_end);
        y_start = (TextView) view_start.findViewById(R.id.txt_start_day_chose);
        y_end = (TextView) view_end.findViewById(R.id.txt_start_day_chose);
        y_loop = (CheckBox) time_y.findViewById(R.id.cb_infiniteloop);
        final String ngay = "" + year + "-" + Cons.getfulldate(month_special) + "-" + Cons.getfulldate(date_special);

        y_end.setText(Cons.date_finish);
        y_end.setTextColor(Color.parseColor("#FFFFFF"));
        y_loop.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    y_end.setText(Cons.date_finish);
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
        dayOfWeek_year = calendar.get(Calendar.DAY_OF_WEEK) - 1;
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
            y_start.setText(Cons.formmat_date(arrayList.get(0).StartDate));
            if (arrayList.get(0).EndDate.length() > 0) {
                y_loop.setChecked(false);
                y_end.setText(Cons.formmat_date(arrayList.get(0).EndDate));
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
                    objectAddYear.setDayofweek(dayOfWeek_year);
                    if (date == 0)
                        objectAddYear.setDayofmonth(_Application.getInstance().month_date().length - 1);
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
            objectAddYear.setDayofweek(dayOfWeek_year);
            objectAddYear.setDayofmonth(day_year);
            objectAddYear.setCalendar(0);
            objectAddYear.setWeek(week_of_month);
            arrayList_year.add(objectAddYear);
            y_start.setText(ngay);
        }
        ListView lv_month_y = (ListView) dialog.findViewById(R.id.lv_month_y);
        yearAdapter = new DT_YearAdapter(this, arrayList_year, lv_month_y);
        lv_month_y.setAdapter(yearAdapter);

        int totalHeight = lv_month_y.getPaddingTop() + lv_month_y.getPaddingBottom();
        int desiredWidth = View.MeasureSpec.makeMeasureSpec(lv_month_y.getWidth(), View.MeasureSpec.AT_MOST);

        for (int i = 0; i < DetailViewActivity.yearAdapter.getCount(); i++) {
            View listItem = DetailViewActivity.yearAdapter.getView(i, null, lv_month_y);
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

        View view1_y = (View) dialog.findViewById(R.id.view1_y);
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
        View view3_y = (View) dialog.findViewById(R.id.view2_y);
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

    public void restartjson() {
        DetailViewActivity.sp_ddaytype_int = 0;
        repeatOption_special = "";
        repeatOption_daily = "";
        repeatOption_week = "";
        repeatOption_month = "";
        repeatOption_year = "";
    }

    String TypeName = "CheckSpecificDday";
    String SpecificDay = "SpecificDay";
    int Lunar_sp = 1;

    public String getRepeatOption() {
        if (sp_ddaytype_int == 0)
            typeNo = 2;
        else
            typeNo = 3;
        String repeatOption = "[";
        ArrayList<String> arrayList = new ArrayList<String>();
        if (repeatOption_special.length() > 0)
            arrayList.add(repeatOption_special);
        if (repeatOption_daily.length() > 0) {
            arrayList.add(repeatOption_daily);
        }
        if (repeatOption_week.length() > 0) {
            arrayList.add(repeatOption_week);
        }
        if (repeatOption_month.length() > 0) {
            arrayList.add(repeatOption_month);
        }
        if (repeatOption_year.length() > 0) {
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
        return repeatOption;
    }

    private void updateDday(int groupNo, String title, String content, long ddayId) {
        String repeatOption = getRepeatOption();
        Log.e(TAG, "repeatOption:" + repeatOption);
        if (repeatOption.length() < 10) {
            show_notify(getString(R.string.select));
        } else {
            if (cb_sharer.isChecked())
                nodelete = false;
            else
                nodelete = true;
            if (HomeActivity.checkAlarm == 0) {
            } else {
                for (int i = 0; i < arr_adapter_notify.size(); i++) {
                    arr_adapter_notify.get(i).setNotificationType(arr_adapter_notify.get(i).getNotificationType() + 1);
                }
            }
            if (sp_ddaytype_int == 1)
                arr_adapter_notify.clear();
            ConnectionUtils.getInstance().update_v2(groupNo, ddayId, typeNo, repeatOption, title, content, this, JSON_SHARER_TYPE,
                    JSON_PERSON_CONCERNED_TYPE, JSON_ADMIN_TYPE, notiNo, arr_adapter_notify, nodelete);
        }
    }

    boolean nodelete = false;

    public void getJsonSpecial() {
        repeatOption_special = "";
        if (arr_lv_special.size() == 1) {
            repeatOption_special = "{TypeName:\"" + TypeName + "\"," + SpecificDay + ":\"" +
                    arr_lv_special.get(0).replace("-", "") + "\",Lunar:" + Lunar_sp + "}";
        } else {
            for (int i = 0; i < arr_lv_special.size(); i++) {
                repeatOption_special += "{TypeName:\"" + TypeName + "\"," + SpecificDay + ":\"" +
                        arr_lv_special.get(i).replace("-", "") + "\",Lunar:" + Lunar_sp + "},";
            }
            repeatOption_special = repeatOption_special.substring(0, repeatOption_special.length() - 1);
        }
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
                int Lunar = 1;
                if (arrayList_year.get(i).getCalendar() == 0) Lunar = 1;
                else if (arrayList_year.get(i).getCalendar() == 1) Lunar = 0;
                else if (arrayList_year.get(i).getCalendar() == 2) Lunar = -1;
                repeatOption_year += "{TypeName:\"CheckEveryYearDday\",StartDate:\"" + y_start.getText().toString().replace("-", "") +
                        "\",EndDate:\"" + EndDate + "\",Interval:" + Interval_y +
                        ",SpecificMMDD:\"" + SpecificMMDD + "\",Lunar:" + Lunar + ",HolidayCondition:" + HolidayCondition_y + "}" + ",";
            } else {
                String SpecificMMnthWeek = "";
                int a = arrayList_year.get(i).getMonth2() + 1;
                if (a < 10) SpecificMMnthWeek += "0" + a;
                else SpecificMMnthWeek += a;
                int b = arrayList_year.get(i).getWeek();
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

    public void goToHome() {
        SharedPreferences pref = getSharedPreferences(Cons.prefname, MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putBoolean(Cons.UPDATE_SUCCESS, true);
        editor.commit();

        finish();
    }

    @Override
    public void onUpdateSuccess() {
        goToHome();
    }

    public void setTitleRepeat(final TextView tv) {
        ConnectionUtils.getInstance().GetRepetitionString(getRepeatOption(), new IFRepetitionString() {
            @Override
            public void onGetRepetitionStringSuccess(String str) {
                tv.setText(str.trim());
            }
        });
//        arrayList_repeat.clear();
//        temp_list.clear();
//        title_repeat = "";
//        String str_temp = "";
////        Log.e(TAG,repeatOption_special);
//        if (repeatOption_special.length() > 0) {
//            if (repeatOption_special.contains("CheckSpecificDday")) {
//                String jsonString = "[" + repeatOption_special + "]";
//                Gson gson = new Gson();
//                Type type = new TypeToken<ArrayList<Object_CheckSpecificDday>>() {
//                }.getType();
//                ArrayList<Object_CheckSpecificDday> object_checkSpecificDday = gson.fromJson(jsonString, type);
//                for (int i = 0; i < object_checkSpecificDday.size(); i++) {
////                    Log.e(TAG,"time:"+object_checkSpecificDday.get(i));
//                    title_repeat += get_date(object_checkSpecificDday.get(i));
//                }
//            } else {
//                Object_CheckDPlusDay ob = new Gson().fromJson(repeatOption_special, Object_CheckDPlusDay.class);
//                String date = ob.getStartDate();
//                int year = Integer.parseInt(date.substring(0, 4));
//                int month = Integer.parseInt(date.substring(4, 6));
//                int ngay = Integer.parseInt(date.substring(6, 8));
//                int a = ob.getLunar();
//                if (a == 1) a = 0;
//                else if (a == 0) a = 1;
//                else a = 2;
//                Calendar calendar = Calendar.getInstance();
//                calendar.set(year, month - 1, ngay);
//                get_dayofweek(calendar.get(Calendar.DAY_OF_WEEK));
////                title_repeat = "D+Day" + " " + year + "-" + getfulldate(month) + "-" + getfulldate(ngay) +
////                        get_dayofweek(calendar.get(Calendar.DAY_OF_WEEK)) + " [" + arr_calendar[a] + "]" + "\n";
//                title_repeat = year + "-" + Cons.getfulldate(month) + "-" + Cons.getfulldate(ngay) +
//                        get_dayofweek(calendar.get(Calendar.DAY_OF_WEEK)) + "\n";
//            }
//        }
//        if (repeatOption_daily.length() > 0) {
//            Object_CheckEveryDayDday ob = new Gson().fromJson(repeatOption_daily, Object_CheckEveryDayDday.class);
//            int Interval = ob.getInterval();
//            String inter = "";
//            if (Interval == 1) inter = "";
//            else if (Interval == 1000) inter = "1000";
//            else if (Interval == 100) inter = "100";
//            else {
//                inter = "" + Interval;
//            }
//            String options = "";
//            String holiday = "";
//            if (!ob.StartOption.equalsIgnoreCase("D")) {
//                if (ob.StartOption.equalsIgnoreCase("Y"))
//                    options = "Options: " + _Application.getInstance().arrayList_option()[1];
//                else if (ob.StartOption.equalsIgnoreCase("M"))
//                    options = "Options: " + _Application.getInstance().arrayList_option()[2];
//                else options = "Options: " + _Application.getInstance().arrayList_option()[3];
//            }
//            holiday = "If holiday: " + _Application.getInstance().arrayList_holiday()[ob.HolidayCondition - 1];
//            String str = "";
//            if (options.length() > 0)
//                str = "Repeat every " + inter + " day" + "\n" + "Period: From " + Cons.formmat_date(ob.getStartDate()) + "\n" + options + "\n" + holiday;
//            else
//                str = "Repeat every " + inter + " day" + "\n" + "Period: From " + Cons.formmat_date(ob.getStartDate()) + "\n" + holiday;
//            str = str.replaceAll("(^\\s+|\\s+$)", "");
//            title_repeat += "\n" + str + "\n\n";
//            arrayList_repeat.add(str.trim());
//            temp_list.add("repeatOption_daily");
//        }
//        if (repeatOption_week.length() > 0) {
//            String str = "", line1 = "", line2 = "", line3 = "";
//            Object_CheckEveryDdayOfWeek ob = new Gson().fromJson(repeatOption_week, Object_CheckEveryDdayOfWeek.class);
//            String get_dayofweek = ob.getSpecificDayOfWeek();
//            if (get_dayofweek.contains("1"))
//                get_dayofweek = get_dayofweek.replace("1", " " + getResources().getString(R.string.monday));
//            if (get_dayofweek.contains("2"))
//                get_dayofweek = get_dayofweek.replace("2", " " + getResources().getString(R.string.tuesday));
//            if (get_dayofweek.contains("3"))
//                get_dayofweek = get_dayofweek.replace("3", " " + getResources().getString(R.string.wednesday));
//            if (get_dayofweek.contains("4"))
//                get_dayofweek = get_dayofweek.replace("4", " " + getResources().getString(R.string.thursday));
//            if (get_dayofweek.contains("5"))
//                get_dayofweek = get_dayofweek.replace("5", " " + getResources().getString(R.string.friday));
//            if (get_dayofweek.contains("6"))
//                get_dayofweek = get_dayofweek.replace("6", " " + getResources().getString(R.string.saturday));
//            if (get_dayofweek.contains("0"))
//                get_dayofweek = get_dayofweek.replace("0", " " + getResources().getString(R.string.sunday));
//            if (ob.getInterval() == 1) line1 = "Repeat:" + get_dayofweek;
//            else line1 = "Repeat: Every " + ob.getInterval() + " weeks" + get_dayofweek;
//            line2 = "Period: From " + Cons.formmat_date(ob.getStartDate());
//            line3 = "If holiday: " + _Application.getInstance().arrayList_holiday()[ob.HolidayCondition - 1];
//            str = "Repeat every week" + "\n" + line1 + "\n" + line2 + "\n" + line3;
//            title_repeat += str + "\n\n";
//            arrayList_repeat.add(str.trim());
//            temp_list.add("repeatOption_week");
//        }
//        if (repeatOption_month.length() > 0) {
//            String str[] = repeatOption_month.split("\\},\\{");
//            String repeat = "", title = "", Period = "", holiday = "", inter = "", sum = "";
//            for (int i = 0; i < str.length; i++) {
//                if (i == 0) str_temp = str[i] + "}";
//                else if (i == str.length - 1) str_temp = "{" + str[i];
//                else str_temp = "{" + str[i] + "}";
//                if (str_temp.substring(str_temp.length() - 2, str_temp.length() - 1).equalsIgnoreCase("}"))
//                    str_temp = str_temp.substring(0, str_temp.length() - 1);
//
//                Object_CheckEveryMonthSpecificDday ob = new Gson().fromJson(str_temp, Object_CheckEveryMonthSpecificDday.class);
//                Period = "Period: From " + Cons.formmat_date(ob.StartDate);
//                holiday = "If holiday: " + _Application.getInstance().arrayList_holiday()[ob.HolidayCondition - 1];
//                if (ob.Interval == 1) inter = "";
//                else inter = "" + ob.Interval;
//                if (str_temp.contains("CheckEveryMonthSpecificDday")) {
//                    String a = "";
//                    String abc[] = ob.SpecificDD.split(",");
//                    for (int j = 0; j < abc.length; j++) {
//                        if (abc[j].equalsIgnoreCase("1") || abc[j].equalsIgnoreCase("31"))
//                            a += abc[j] + "st, ";
//                        else if (abc[j].equalsIgnoreCase("2")) a += abc[j] + "nd, ";
//                        else if (abc[j].equalsIgnoreCase("3")) a += abc[j] + "rd, ";
//                        else if (abc[j].equalsIgnoreCase("00")) a += "Last";
//                        else a += abc[j] + "th, ";
//                    }
//                    String SpecificDD = a + " day of every " + inter + " month";
//                    SpecificDD = SpecificDD.replaceAll("(^\\s+|\\s+$)", "");
//                    repeat = "Repeat: " + SpecificDD;
//                    title = "Repeat the specific days every month";
//                } else {
//                    String a = "";
//                    String abc[] = ob.SpecificWeek.split(",");
//                    for (int j = 0; j < abc.length; j++) {
//                        String content = "", content2 = "";
//                        String str1 = abc[j].trim().split("")[1];
//                        String str2 = abc[j].trim().split("")[2];
//                        if (str1.equalsIgnoreCase("A"))
//                            content = _Application.getInstance().month_week()[0];
//                        else if (str1.equalsIgnoreCase("0"))
//                            content = _Application.getInstance().month_week()[_Application.getInstance().month_week().length - 1];
//                        else
//                            content = _Application.getInstance().month_week()[Integer.parseInt(str1)];
//                        content2 = _Application.getInstance().month_day_of_week()[Integer.parseInt(str2)];
//                        a += " " + content + " " + content2 + ",";
//                    }
//                    if (a.substring(a.length() - 1, a.length()).equalsIgnoreCase(","))
//                        a = a.substring(0, a.length() - 1);
//                    repeat = "Repeat:" + a;
//                    title = "Repeat the specific weeks every month";
//                }
//                sum += title + "\n" + repeat + "\n" + Period + "\n" + holiday + "\n";
//            }
//            title_repeat += sum + "\n";
//            arrayList_repeat.add(sum.trim());
//            temp_list.add("repeatOption_month");
//        }
//        if (repeatOption_year.length() > 0) {
//            String str[] = repeatOption_year.split("\\},\\{");
//            String title = "", sum = "", repeat = "", Period = "", holiday = "";
//            for (int i = 0; i < str.length; i++) {
//                if (i == 0) str_temp = str[i] + "}";
//                else if (i == str.length - 1) str_temp = "{" + str[i];
//                else str_temp = "{" + str[i] + "}";
//                if (str_temp.substring(str_temp.length() - 2, str_temp.length() - 1).equalsIgnoreCase("}"))
//                    str_temp = str_temp.substring(0, str_temp.length() - 1);
//                Object_CheckEveryYearDday ob = new Gson().fromJson(str_temp, Object_CheckEveryYearDday.class);
//                Period = "Period: From " + Cons.formmat_date(ob.StartDate);
//                holiday = "If holiday: " + _Application.getInstance().arrayList_holiday()[ob.HolidayCondition - 1];
//                if (str_temp.contains("CheckEveryYearDday")) {
//                    String month = _Application.getInstance().arr_month()[Integer.parseInt(ob.SpecificMMDD.substring(0, 2)) - 1];
//                    String date = "" + Integer.parseInt(ob.SpecificMMDD.substring(2, 4));
//                    title = "Repeat the specific days every year";
//                    String Solar = "";
//                    if (ob.Lunar == 1) Solar = _Application.getInstance().arr_calendar()[0];
//                    else if (ob.Lunar == 0) Solar = _Application.getInstance().arr_calendar()[1];
//                    else Solar = _Application.getInstance().arr_calendar()[2];
//                    repeat = "Repeat: " + _Application.getInstance().arrayList_annually()[ob.Interval - 1] + " " + month + " Every " + date + " days" + "(" + Solar + ")";
//                } else {
//                    title = "Repeat the specific weeks every year";
//                    String month = _Application.getInstance().arr_month()[Integer.parseInt(ob.SpecificMMnthWeek.substring(0, 2)) - 1];
//                    String date = "" + ob.SpecificMMnthWeek.substring(2, 4);
//                    String str1 = date.split("")[1];
//                    String str2 = date.split("")[2];
//                    String w = "", d = "";
//                    if (str1.equalsIgnoreCase("A")) w = _Application.getInstance().month_week()[0];
//                    else if (str1.equalsIgnoreCase("0"))
//                        w = _Application.getInstance().month_week()[_Application.getInstance().month_week().length - 1];
//                    else w = _Application.getInstance().month_week()[Integer.parseInt(str1)];
//                    d = _Application.getInstance().month_day_of_week()[Integer.parseInt(str2)];
//                    repeat = "Repeat: " + _Application.getInstance().arrayList_annually()[ob.Interval - 1] + " " + month + " " + w + " " + d;
//                }
//                sum += title + "\n" + repeat + "\n" + Period + "\n" + holiday + "\n";
//            }
//
//            title_repeat += sum + "\n";
//            arrayList_repeat.add(sum.trim());
//            temp_list.add("repeatOption_year");
//        }
//        if (arrayList_repeat.size() == 0) {
//            temp_list.clear();
//            arrayList_repeat.add(getResources().getString(R.string.norepeat));
//        }
//        if (title_repeat.length() == 0) title_repeat = getResources().getString(R.string.norepeat);
//        tv.setText(title_repeat.trim());
    }

    public void Start_First() {
        repeatOption_daily = "";
        repeatOption_week = "";
        repeatOption_month = "";
        repeatOption_year = "";
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        arr_lv_special.clear();
        String date = "";
        if (sa_y > 0 && sa_m > 0 && sa_d > 0)
            date = sa_y + Cons.getfulldate(sa_m) + Cons.getfulldate(sa_d);
        else
            date = year + Cons.getfulldate(month) + Cons.getfulldate(day);
        repeatOption_special = "{TypeName:\"CheckSpecificDday\",SpecificDay:\"" + date + "\",Lunar:1}";
    }

    public void dday_minus() {
        if (temp == 1) {
            Start_First();
            temp = 0;
        } else {

        }

        typeNo = 2;
        TypeName = "CheckSpecificDday";
        SpecificDay = "SpecificDay";
        notify.setVisibility(View.VISIBLE);
    }

    int temp = 0;

    public void dday_plus() {
        temp = 1;
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        String date = "";
        if (sa_y > 0 && sa_m > 0 && sa_d > 0)
            date = sa_y + Cons.getfulldate(sa_m) + Cons.getfulldate(sa_d);
        else
            date = year + Cons.getfulldate(month) + Cons.getfulldate(day);
        repeatOption_special = "{TypeName:\"CheckDPlusDay\",StartDate:\"" + date + "\",Lunar:" + Lunar_sp + "}";
        typeNo = 3;
        TypeName = "CheckDPlusDay";
        SpecificDay = "StartDate";
        repeatOption_daily = "";
        repeatOption_week = "";
        repeatOption_month = "";
        repeatOption_year = "";
        notify.setVisibility(View.GONE);
    }

    private int mType;
    ArrayList<PersonData> resultList;
    ArrayList<PersonData> resultList2;
    ArrayList<PersonData> resultList3;

    private void handleSelectedOrganizationResult() {
        Gson gson = new Gson();
        _display_item();
        String json = "";
        switch (mType) {
            case Cons.SHARER_TYPE:
                tvSharer.setText(ListUtils.getListName(resultList));
                tvSharer.setTextColor(Color.parseColor("#000000"));
                tvSharer.setSelected(true);
                json = gson.toJson(resultList);
                if (json.equals("[]")) json = "";
                JSON_SHARER_TYPE = json;

                if (resultList.size() == 0) {
                    tvSharer.setText(getResources().getString(R.string.nosharer));
                    tvSharer.setTextColor(Color.parseColor("#9d9d9d"));
                    ln_checkbox.setVisibility(View.GONE);
                } else {
                    ln_checkbox.setVisibility(View.VISIBLE);
                }
                break;
            case Cons.PERSON_CONCERNED_TYPE:
                if (resultList2.size() > 1) {
                    show_notify(getResources().getString(R.string.invalid));
                    tvPersonConcerned.setText(getResources().getString(R.string.noconcerned));
                    tvPersonConcerned.setTextColor(Color.parseColor("#9d9d9d"));
                } else {
                    tvPersonConcerned.setText(ListUtils.getListName(resultList2));
                    tvPersonConcerned.setTextColor(Color.parseColor("#000000"));
                    tvPersonConcerned.setSelected(true);
                    json = gson.toJson(resultList2);
                    if (json.equals("[]")) json = "";
                    JSON_PERSON_CONCERNED_TYPE = json;
                    if (resultList2.size() == 0) {
                        tv_myself.setVisibility(View.VISIBLE);
                        tvPersonConcerned.setText(getResources().getString(R.string.noconcerned));
                        tvPersonConcerned.setTextColor(Color.parseColor("#9d9d9d"));
                    } else {
                        if (resultList2.get(0).getUserNo() == id)
                            tv_myself.setVisibility(View.GONE);
                        else tv_myself.setVisibility(View.VISIBLE);
                    }
                }
                break;
            case Cons.ADMIN_TYPE:
                tvAdmin.setText(ListUtils.getListName(resultList3));
                tvAdmin.setTextColor(Color.parseColor("#000000"));
                tvAdmin.setSelected(true);
                json = gson.toJson(resultList3);
                if (json.equals("[]")) json = "";
                JSON_ADMIN_TYPE = json;
                if (resultList3.size() == 0) {
                    tvAdmin.setText(getResources().getString(R.string.noadmin));
                    tvAdmin.setTextColor(Color.parseColor("#9d9d9d"));
                }
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        try {
            if (resultCode == Activity.RESULT_OK) {
                switch (requestCode) {
                    case Cons.ORGANIZATION_TO_ACTIVITY:
                        resultList = data.getExtras().getParcelableArrayList(Cons.BUNDLE_LIST_PERSON);
                        mType = data.getExtras().getInt(Cons.BUNDLE_TYPE);
                        handleSelectedOrganizationResult();
                        break;
                    case Cons.ORGANIZATION_TO_ACTIVITY_2:
                        resultList2 = data.getExtras().getParcelableArrayList(Cons.BUNDLE_LIST_PERSON);
                        mType = data.getExtras().getInt(Cons.BUNDLE_TYPE);
                        handleSelectedOrganizationResult();
                        break;
                    case Cons.ORGANIZATION_TO_ACTIVITY_3:
                        resultList3 = data.getExtras().getParcelableArrayList(Cons.BUNDLE_LIST_PERSON);
                        mType = data.getExtras().getInt(Cons.BUNDLE_TYPE);
                        handleSelectedOrganizationResult();
                        break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void startOrganizationActivity(ArrayList<PersonData> selectedList, int type) {
        HomeActivity.intShare = 1;
//        Intent i = new Intent(this, OrganizationActivity.class);
        Intent i = new Intent(this, NewOrganizationChart.class);
        i.putParcelableArrayListExtra(Cons.BUNDLE_LIST_PERSON, selectedList);
        i.putExtra(Cons.BUNDLE_TYPE, type);
        i.putExtra(Cons.BUNDLE_ORG_DISPLAY_SELECTED_ONLY, false);
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
        startActivityForResult(i, Cons.ORGANIZATION_TO_ACTIVITY_2);
    }

    private void startOrganizationActivity3(ArrayList<PersonData> selectedList, int type) {
        HomeActivity.intShare = 3;
//        Intent i = new Intent(this, OrganizationActivity.class);
        Intent i = new Intent(this, NewOrganizationChart.class);
        i.putParcelableArrayListExtra(Cons.BUNDLE_LIST_PERSON, selectedList);
        i.putExtra(Cons.BUNDLE_TYPE, type);
        i.putExtra(Cons.BUNDLE_ORG_DISPLAY_SELECTED_ONLY, false);
        startActivityForResult(i, Cons.ORGANIZATION_TO_ACTIVITY_3);
    }

    public void startOrganization() {
        startOrganizationActivity(resultList, Cons.SHARER_TYPE);
    }

    public void startOrganization2() {
        startOrganizationActivity2(resultList2, Cons.PERSON_CONCERNED_TYPE);
    }

    public void startOrganization3() {
        startOrganizationActivity3(resultList3, Cons.ADMIN_TYPE);
    }

    boolean flag_title = false;

    public void cannot_edit() {
        show_notify(getResources().getString(R.string.permiss));
    }

    @Override
    public void onClick(View v) {
        if (v == ln_gr) {
            dialog_group();
        } else if (v == tvRepeat || v == repeatContainer) {
            if (!canEdit)
                cannot_edit();
            else
                dialog_repeat();
        } else if (v == tvPersonConcerned || v == personConcernedContainer) {
            if (!canEdit)
                cannot_edit();
            else
                startOrganization2();
        } else if (v == tvAdmin || v == adminContainer) {
            if (!canEdit)
                cannot_edit();
            else
                startOrganization3();
        } else if (v == tvSharer || v == sharerContainer) {
            if (!canEdit)
                cannot_edit();
            else
                startOrganization();
        } else if (v == img_plus) {
            if (!canEdit) {
                cannot_edit();
            } else {
                if (flag_title) {
                    img_plus.setImageResource(R.drawable.ic_arrow_drop_down);
                    commentContainer.setVisibility(View.GONE);
                    flag_title = false;
                } else {
                    img_plus.setImageResource(R.drawable.ic_arrow_drop_up);
                    commentContainer.setVisibility(View.VISIBLE);
                    flag_title = true;
                }
            }
        } else if (v == tvTitle) {
            if (!canEdit) {
                tvTitle.setCursorVisible(false);
                cannot_edit();
            }
        } else if (v == tvContent) {
            if (!canEdit) {
                tvContent.setCursorVisible(false);
                cannot_edit();
            }
        } else if (v == detail_ic_ask) {
            show_notify(getResources().getString(R.string.detail_ic_ask));
        } else if (v == cb_sharer) {
            _display_item();
        } else if (v == tvDate) {
            if (!canEdit)
                cannot_edit();
            else
                dialog_repeat();
        } else if (v == tv_myself) {
            if (!canEdit) {
                cannot_edit();
            } else {
                tv_myself.setVisibility(View.GONE);
                _display_item();
                resultList2 = new ArrayList<>();
                PersonData ob = new PersonData();
                String name = prefUtils.getUserName().replace("\"", "");
                ob.setDepartNo(prefUtils.getDepartNo());
                ob.setUserNo(id);
                ob.setFullName(name);
                ob.setNameEn(name);
                resultList2.add(ob);
                JSON_PERSON_CONCERNED_TYPE = new Gson().toJson(resultList2);
                tvPersonConcerned.setTextColor(Color.BLACK);
                tvPersonConcerned.setText(name);
            }
        }
    }

    public void insertCompletedRecord() {
        AlertDialog.Builder adb = new AlertDialog.Builder(this);
        adb.setTitle(getResources().getString(R.string.Dday));
        adb.setMessage(getResources().getString(R.string.wanthide));
        adb.setPositiveButton(getResources().getString(R.string.ok), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ConnectionUtils.getInstance().insertCompletedRecord(ddayId, ddayDate, "", new MakeComplete() {
                    @Override
                    public void onSuccessMakeComplete(long dataNo) {
                        HomeDDayBoardAdapter adapter;

                        if (HomeActivity.currentFragmentPos == 0) {
                            adapter = FragmentHomeAll.instance.mDDayListAdapter;
                        } else {
                            adapter = FragmentHomePlus.instance.mDDayListAdapter;
                        }

                        ArrayList<DDayModel> listOfModels = adapter.list;
                        int length = listOfModels.size();

                        DDayModel dDayModel = null;
                        int pos = 0;

                        for (int i = 0; i < length; i++) {
                            if (listOfModels.get(i).getDdayId() == ddayId) {
                                dDayModel = listOfModels.get(i);
                                pos = i;
                            }
                        }

                        dDayModel.setCompletedRecordNo(dataNo);

                        if (HomeActivity.filterTypeForList == HomeActivity.InProgress) {
                            adapter.list.remove(dDayModel);
                            adapter.notifyItemRemoved(pos);
                        } else if (HomeActivity.filterTypeForList == (HomeActivity.InProgress | HomeActivity.Completed | HomeActivity.Hiden)) {
                            dDayModel.setIsCompleted(true);
                            adapter.notifyItemChanged(pos);
                        }

                        goToHome();
                    }
                });
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

    public void insertCoveredDay() {
        AlertDialog.Builder adb = new AlertDialog.Builder(this);
        adb.setTitle(getResources().getString(R.string.Dday));
        adb.setMessage(getResources().getString(R.string.wanthide));
        adb.setPositiveButton(getResources().getString(R.string.ok), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ConnectionUtils.getInstance().InsertCoveredDay(ddayId, ddayDate, new InsertCoveredDaySuccess() {
                    @Override
                    public void onInsertCoveredDaySuccess(long dataNo) {
                        HomeDDayBoardAdapter adapter;

                        if (HomeActivity.currentFragmentPos == 0) {
                            adapter = FragmentHomeAll.instance.mDDayListAdapter;
                        } else {
                            adapter = FragmentHomePlus.instance.mDDayListAdapter;
                        }

                        ArrayList<DDayModel> listOfModels = adapter.list;
                        int length = listOfModels.size();

                        DDayModel dDayModel = null;
                        int pos = 0;

                        for (int i = 0; i < length; i++) {
                            if (listOfModels.get(i).getDdayId() == ddayId) {
                                dDayModel = listOfModels.get(i);
                                pos = i;
                            }
                        }

                        dDayModel.setHiddenDataNo(dataNo);

                        if (HomeActivity.filterTypeForList == HomeActivity.InProgress) {
                            adapter.list.remove(dDayModel);
                            adapter.notifyItemRemoved(pos);
                        } else if (HomeActivity.filterTypeForList == (HomeActivity.InProgress | HomeActivity.Completed | HomeActivity.Hiden)) {
                            dDayModel.setIsHidden(true);
                            adapter.notifyItemChanged(pos);
                        }

                        goToHome();
                    }
                });
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

    @Override
    protected void onPause() {
        super.onPause();
        if (!canEdit)
            ConnectionUtils.getInstance().UpdateGroupNoOfDay(ddayId, groupNo, new UpdateGroupCalllback() {
                @Override
                public void onUpdateSuccess() {

                }

                @Override
                public void onUpdateFail() {

                }
            });
    }
}