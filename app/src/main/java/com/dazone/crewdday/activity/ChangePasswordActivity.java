package com.dazone.crewdday.activity;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.dazone.crewdday.R;
import com.dazone.crewdday._Application;
import com.dazone.crewdday.mInterface.IF_UpdatePass;
import com.dazone.crewdday.util.ConnectionUtils;
import com.dazone.crewdday.util.PreferenceUtilities;
import com.dazone.crewdday.util.WebClient;

/**
 * Created by maidinh on 5/22/2017.
 */

public class ChangePasswordActivity extends AppCompatActivity {
    private EditText edOldPass, edNewPass, edConfirmPass;
    private Button btnChange;

    void initView() {
        edOldPass = (EditText) findViewById(R.id.edOldPass);
        edNewPass = (EditText) findViewById(R.id.edNewPass);
        edConfirmPass = (EditText) findViewById(R.id.edConfirmPass);
        btnChange = (Button) findViewById(R.id.btnChange);

        btnChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                actionChange();
            }
        });
    }

    void showMsg(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    void actionChange() {
        String oldPass = edOldPass.getText().toString().trim();
        final String newPass = edNewPass.getText().toString().trim();
        String cfPass = edConfirmPass.getText().toString().trim();
        if (oldPass.length() == 0 || newPass.length() == 0 || cfPass.length() == 0) {
            if (oldPass.length() == 0) showMsg("missing input Old password");
            else if (newPass.length() == 0) showMsg("missing input New password");
            else showMsg("missing input Confirm password");
        } else {
            final PreferenceUtilities preferenceUtilities = _Application.getInstance().getPreferenceUtilities();
            String pass = preferenceUtilities.getPASSWORD_LOGIN();

            if (!oldPass.equals(pass)) {
                showMsg("Old pass incorrect");
            } else if (!newPass.equals(cfPass)) {
                showMsg("Confirm pass incorrect");
            } else {

                String _domain = preferenceUtilities.getCurrentServiceDomain();

                ConnectionUtils.getInstance().updatePassword(_domain, oldPass, newPass, new IF_UpdatePass() {
                    @Override
                    public void onSuccess(String newSessionID) {
                        showMsg("Success");
                        preferenceUtilities.setCurrentMobileSessionId(newSessionID);
                        preferenceUtilities.setPASSWORD_LOGIN(newPass);
                        finish();
                    }

                    @Override
                    public void onFail() {
                        showMsg("There was an error changing your password, try again");
                    }
                });
            }
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.change_password_layout);
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
            getSupportActionBar().setTitle(getResources().getString(R.string.change_password));
        }

        initView();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;

        }
        return false;
    }
}
