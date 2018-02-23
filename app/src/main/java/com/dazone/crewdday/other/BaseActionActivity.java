package com.dazone.crewdday.other;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dazone.crewdday.*;
import com.dazone.crewdday.activity.AddUser;
import com.dazone.crewdday.activity.HomeActivity;
import com.dazone.crewdday.mInterface.IF_GetDepartmentsAlsoDisabled;
import com.dazone.crewdday.mInterface.InsertDepartmentCallback;
import com.dazone.crewdday.util.WebClient;

public abstract class BaseActionActivity extends BaseActivity {
    protected Toolbar toolbar;
    protected FloatingActionButton fab;
    protected RelativeLayout content_main;
    public static BaseActionActivity instance;
    String TAG = "BaseActionActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
        addFragment(savedInstanceState);
        setupFab();

    }

    public void addUserLayout() {
        Intent intent = new Intent(BaseActionActivity.this, AddUser.class);
        intent.putExtra(com.dazone.crewdday.Cons.ADD_USER, 1);
        startActivity(intent);
    }

    public void addChildLayout() {
//        Intent intent = new Intent(BaseActionActivity.this, AddUser.class);
//        startActivity(intent);
    }

    public void showDialog() {
        LayoutInflater inflater = getLayoutInflater();
        View alertLayout = inflater.inflate(R.layout.add_department_layout, null);
        final EditText nameDepartment = (EditText) alertLayout.findViewById(R.id.editText);
        AlertDialog.Builder adb = new AlertDialog.Builder(this);
        adb.setTitle(getResources().getString(R.string.addDepartment));
        adb.setView(alertLayout);
        adb.setPositiveButton(getResources().getString(R.string.Confirm), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                WebClient.InsertDepartment(1, nameDepartment.getText().toString().trim(), "_domain", new InsertDepartmentCallback() {
                    @Override
                    public void onInsertDepartmentSuccess() {

                    }

                    @Override
                    public void Fail() {

                    }
                });

            }
        });
        adb.setNegativeButton(getResources().getString(R.string.cancel), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        adb.create().show();

    }

    ProgressBar progress_bar;

    public void showProgressbar() {
        if (progress_bar != null) {
            if (!progress_bar.isShown()) {
                progress_bar.setVisibility(View.VISIBLE);
            }
        }
    }

    public void dismissProgressbar() {
        if (progress_bar != null) {
            if (progress_bar.isShown()) {
                progress_bar.setVisibility(View.GONE);
            }
        }
    }

    private void init() {
        setContentView(R.layout.activity_base_action);
        instance = this;
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        displayToolBarBackButton(true);
        content_main = (RelativeLayout) findViewById(R.id.content_base_action);
        progress_bar = (ProgressBar) findViewById(R.id.progress_bar);
        fab = (FloatingActionButton) findViewById(R.id.fabManager);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog();
            }
        });
        if (HomeActivity.gotoOrganizationActivity != 0)
            fab.hide();
    }

    protected abstract void addFragment(Bundle bundle);

    protected abstract void setupFab();

    /*****
     * tool bar function
     ****/
    public void displayToolBarBackButton(boolean enableHome) {
        ActionBar actionBar = getSupportActionBar();
        if (enableHome && actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
        }
    }

    @Override
    public void onBackPressed() {
        OrganizationFragment.instance.getListChoose();
//        Log.e(TAG, "onBackPressed");
//        super.onBackPressed();
    }

    MenuItem stopDepts, stopUser, menu_filter;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_organization, menu);
        if (menu != null) {
            menu_filter = menu.findItem(R.id.menu_filter);
            stopDepts = menu.findItem(R.id.stopDepts);
            stopUser = menu.findItem(R.id.stopUser);
            if (HomeActivity.isManagerTool && HomeActivity.gotoOrganizationActivity == 0) {

            } else {
                menu_filter.setVisible(false);
            }
        }
        return true;
    }

    private void onStopDeptsCheck() {
        String _domain = _Application.getInstance().getPreferenceUtilities().getCurrentCompanyDomain();
        HttpRequest.getInstance().GetDepartmentsAlsoDisabled(_domain, new IF_GetDepartmentsAlsoDisabled() {
            @Override
            public void onSuccess() {

//                _Application.getInstance().dismissDialog();
            }

            @Override
            public void onFail() {

            }
        });
    }

    private void onStopDeptsNoCheck() {
        OrganizationUserDBHelper.clearData();
        HttpRequest.getInstance().getDepartment(null);
    }

    private void onStopUser() {
    }


    public void updateListAfterNewDepartment() {
        if (stopDepts.isChecked()) {
            onStopDeptsCheck();
        } else {
            onStopDeptsNoCheck();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.stopDepts) {
            if (!stopDepts.isChecked()) {
                onStopDeptsCheck();
                stopDepts.setChecked(true);
            } else {
                onStopDeptsNoCheck();
                stopDepts.setChecked(false);
            }
            showProgressbar();
            new PrefManager(this).setRestoreList(true);
        } else if (id == R.id.stopUser) {
            if (!stopUser.isChecked()) {
                onStopUser();
                stopUser.setChecked(true);
            } else {
                stopUser.setChecked(false);
            }
            new PrefManager(this).setRestoreList(true);
        }
        switch (id) {
            case android.R.id.home:
                if (getSupportFragmentManager().getBackStackEntryCount() != 0) {
                    getSupportFragmentManager().popBackStackImmediate();
                } else {
//                    finish();
                    OrganizationFragment.instance.getListChoose();
//                    Log.e(TAG, "onOptionsItemSelected");
                }
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /*****
     * end tool bar function
     ****/


    public void hideToolBarBackButton() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(false);
            actionBar.setDisplayShowHomeEnabled(false);
        }
    }

}
