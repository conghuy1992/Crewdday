package com.dazone.crewdday.activity;

import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.dazone.crewdday.R;
import com.dazone.crewdday.mInterface.BaseHTTPCallBackWithString;
import com.dazone.crewdday.other.BaseActivity;
import com.dazone.crewdday.other.ErrorDto;
import com.dazone.crewdday.other.HttpRequest;


public class SignUpActivity extends AppCompatActivity {
    private EditText mEtEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        initToolBar();

        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.colorPrimaryDrark));
        }

        mEtEmail = (EditText) findViewById(R.id.sign_up_edt_email);
        RelativeLayout mBtnSignUp = (RelativeLayout) findViewById(R.id.login_btn_register);

        if (mEtEmail != null) {
            mEtEmail.setSelection(0);
        }

        if (mBtnSignUp != null) {
            mBtnSignUp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String email = mEtEmail.getText().toString().trim();
                    signUp(email);
                }
            });
        }
    }

    private void signUp(String email) {
        HttpRequest.getInstance().signUp(new BaseHTTPCallBackWithString() {
            @Override
            public void onHTTPSuccess(String message) {
                Toast.makeText(SignUpActivity.this, message, Toast.LENGTH_LONG).show();
                finish();
            }

            @Override
            public void onHTTPFail(ErrorDto errorDto) {
                Toast.makeText(SignUpActivity.this, errorDto.message, Toast.LENGTH_LONG).show();
                mEtEmail.requestFocus();
            }
        }, email);
    }

    public void initToolBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.tbSignUpToolbar);

        if (toolbar == null) {
            return;
        }

        toolbar.setTitle(R.string.title_sign_up_screen);
        setSupportActionBar(toolbar);

        toolbar.setNavigationIcon(R.drawable.mnubar_ic_back);
        toolbar.setNavigationOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finish();
                    }
                }
        );
    }
}