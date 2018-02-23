package com.dazone.crewdday.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;

import com.dazone.crewdday.Cons;
import com.dazone.crewdday.R;
import com.dazone.crewdday.adapter.AdapterOrganizationChart;
import com.dazone.crewdday.other.ErrorDto;
import com.dazone.crewdday.other.OnGetAllOfUser;
import com.dazone.crewdday.other.PersonData;
import com.dazone.crewdday.other.Statics;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by maidinh on 5/19/2017.
 */

public class NewOrganizationChart extends AppCompatActivity {
    private String TAG = "NewOrganizationChart";
    private RecyclerView recyclerView;
    private AdapterOrganizationChart mAdapter;
    private LinearLayoutManager mLayoutManager;
    private List<PersonData> personDatas = new ArrayList<>();

    private int BUNDLE_TYPE = 1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_organizationchart_layout);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.colorPrimaryDrark));
        }
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // add back arrow to toolbar
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setTitle("");
        }
        getDataIntent();
        initView();
        initDB();

    }

    void initView() {
        recyclerView = (RecyclerView) findViewById(R.id.rv);
        NewOrganizationChart instance = this;

        mAdapter = new AdapterOrganizationChart(this, personDatas, instance, selectedPersonList, BUNDLE_TYPE);
        mLayoutManager = new LinearLayoutManager(getApplicationContext());
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(mAdapter);
    }

    void getDataIntent() {
        Intent intent = getIntent();
        try {
            selectedPersonList = intent.getParcelableArrayListExtra(Cons.BUNDLE_LIST_PERSON);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (selectedPersonList == null) selectedPersonList = new ArrayList<>();
        try {
            BUNDLE_TYPE = intent.getIntExtra(Cons.BUNDLE_TYPE, 1);
        } catch (Exception e) {
            e.printStackTrace();
        }

        Log.d(TAG, "BUNDLE_TYPE:" + BUNDLE_TYPE);
        Log.d(TAG, "selectedPersonList:" + selectedPersonList.size());

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(getTitle(BUNDLE_TYPE));
        }

    }

    String getTitle(int type) {
        if (type == 2)
            return getResources().getString(R.string.personal_concerned);
        else if (type == 3)
            return getResources().getString(R.string.co_administrator);
        else return getResources().getString(R.string.sharer);
    }

    void initDB() {
        this.mSelectedPersonList = selectedPersonList;
        initWholeOrganization();
    }


    private ArrayList<PersonData> selectedPersonList = new ArrayList<>();
    private ArrayList<PersonData> mSelectedPersonList = new ArrayList<>();
    private ArrayList<PersonData> mPersonList = new ArrayList<>();

    private void initWholeOrganization() {
        PersonData.getDepartmentAndUser(new OnGetAllOfUser() {
            @Override
            public void onGetAllOfUserSuccess(ArrayList<PersonData> list) {
                mPersonList = new ArrayList<>(list);
                // set selected for list before create recursive list
                for (PersonData selectedPerson : mSelectedPersonList) {

                    int indexOf = mPersonList.indexOf(selectedPerson);
                    if (indexOf != -1) {
                        mPersonList.get(indexOf).setIsCheck(true);
                    }
                }
                createRecursiveList(list, mPersonList);
                personDatas.clear();
                for (PersonData personData : mPersonList) {
                    if (personData.getPersonList() != null && personData.getType() != 2 && personData.getDepartmentParentNo() == 0) {
                        personDatas.add(personData);
                    }
                }
                mAdapter.updateList(personDatas);


            }

            @Override
            public void onGetAllOfUserFail(ErrorDto errorData) {

            }
        });
    }

    private void createRecursiveList(ArrayList<PersonData> list, ArrayList<PersonData> parentList) {

        //create recursive list
        Iterator<PersonData> iter = list.iterator();
        while (iter.hasNext()) {

            PersonData tempPerson = iter.next();
//            Log.e(TAG,tempPerson.toString());
            for (PersonData person : parentList) {
//                Log.e(TAG,"aaaaa "+ person.toString());
                if (person.getType() == 1) {
                    if (tempPerson.getType() == 1 && person.getDepartNo() == tempPerson.getDepartmentParentNo()) {
                        // department compare by departNo and parentNo
                        person.addChild(tempPerson);
                        iter.remove();
                        parentList.remove(tempPerson);
                        break;
                    } else if (tempPerson.getType() == 2 && person.getDepartNo() == tempPerson.getDepartNo()) {
                        // member , compare by departNo and departNo
                        person.addChild(tempPerson);
                        iter.remove();
                        parentList.remove(tempPerson);
                        break;
                    }
                    if (person.getPersonList() != null && person.getPersonList().size() > 0) {
                        // not in root list , search in child list
                        ArrayList<PersonData> test = new ArrayList<>();
                        test.add(tempPerson);
                        createRecursiveList(test, person.getPersonList());
                    }
                }
            }
        }
    }

    public void scrollToEndList(int size) {
        recyclerView.smoothScrollToPosition(size);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBack();
                break;
        }
        return false;
    }

    @Override
    public void onBackPressed() {
        onBack();
    }

    ArrayList<PersonData> getListDTO(List<PersonData> lst) {
        ArrayList<PersonData> dtoList = new ArrayList<>();
        for (PersonData obj : lst) {
            if (obj.isCheck())
                dtoList.add(obj);
        }
        return dtoList;
    }

    void onBack() {
        ArrayList<PersonData> personDatas = getListDTO(mAdapter.getList());
        if (personDatas == null) personDatas = new ArrayList<>();


        Intent resultIntent = new Intent();
        resultIntent.putParcelableArrayListExtra(Statics.BUNDLE_LIST_PERSON, personDatas);
        resultIntent.putExtra(Cons.BUNDLE_TYPE, BUNDLE_TYPE);
        setResult(Activity.RESULT_OK, resultIntent);
        finish();
    }
}
