package com.dazone.crewdday.activity;

import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;

import com.dazone.crewdday.Cons;
import com.dazone.crewdday.R;
import com.dazone.crewdday._Application;
import com.dazone.crewdday.adapter.DdeptAdapter;
import com.dazone.crewdday.other.PersonData;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.TimeZone;

public class AddUser extends AppCompatActivity {
    private String TAG = "AddUser";
    private Spinner spSex, spTimeZone;
    private ArrayList<String> arrayListTimeZone = new ArrayList<>();
    private ListView listDdepts;
    private DdeptAdapter adapter;
    private ArrayList<String> listDDep = new ArrayList<>();
    private ScrollView scrollView;
    private TextView tvShow;
    private EditText etId, etMail, etName, etNameEn;
    public static AddUser instance = null;
    int kind = 1; //0: view detail, 1: add user

    public void gotoEndScroll() {
        scrollView.fullScroll(View.FOCUS_DOWN);
    }

    public void showText() {
//        tvShow.setVisibility(View.VISIBLE);
    }

    public void hideText() {
//        tvShow.setVisibility(View.GONE);
    }

    private void initView() {
        instance = this;
        listDDep.add("a");
        etId = (EditText) findViewById(R.id.etId);
        etName = (EditText) findViewById(R.id.etName);
        etNameEn = (EditText) findViewById(R.id.etNameEn);
        etMail = (EditText) findViewById(R.id.etMail);
        tvShow = (TextView) findViewById(R.id.tvShow);
        scrollView = (ScrollView) findViewById(R.id.scrollView);
        listDdepts = (ListView) findViewById(R.id.listDdepts);
        adapter = new DdeptAdapter(this, listDDep, listDdepts);
        spSex = (Spinner) findViewById(R.id.spSex);
        spTimeZone = (Spinner) findViewById(R.id.spTimeZone);
        listDdepts = (ListView) findViewById(R.id.listDdepts);
        String[] ids = TimeZone.getAvailableIDs();
        for (int i = 0; i < ids.length; i++) {
            TimeZone d = TimeZone.getTimeZone(ids[i]);

            if (!ids[i].matches(".*/.*")) {
                continue;
            }

            String region = ids[i].replaceAll(".*/", "").replaceAll("_", " ");
            int hours = Math.abs(d.getRawOffset()) / 3600000;
            int minutes = Math.abs(d.getRawOffset() / 60000) % 60;
            String sign = d.getRawOffset() >= 0 ? "+" : "-";

            String timeZonePretty = String.format("(UTC %s %02d:%02d) %s", sign, hours, minutes, region);
            arrayListTimeZone.add(timeZonePretty);
        }
    }

    PersonData personData;

    private void doWork() {
        listDdepts.setAdapter(adapter);

        ArrayAdapter adapterTimeZone = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, arrayListTimeZone);
        adapterTimeZone.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spTimeZone.setAdapter(adapterTimeZone);
        spTimeZone.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int args, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        ArrayAdapter adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, _Application.getInstance().sex_array());
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spSex.setAdapter(adapter);
        spSex.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int args, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        kind = getIntent().getIntExtra(Cons.ADD_USER, 0); //0: view detail, 1: add user
        if (kind == 0) {
            setTitle(getResources().getString(R.string.update_user));
            personData = new Gson().fromJson(getIntent().getStringExtra(Cons.OBJECT_PERSON), PersonData.class);
            if (personData != null) {
                etId.setText("" + personData.getUserNo());
                etName.setText("" + personData.getFullName());
                etNameEn.setText("" + personData.getNameEn());
                etMail.setText("" + personData.getmEmail());
            }

        } else {
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_user_layout);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.colorPrimaryDrark));
        }
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.mnubar_ic_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        initView();
        doWork();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add_user, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.menu_ok) {

        }

        return super.onOptionsItemSelected(item);
    }
}