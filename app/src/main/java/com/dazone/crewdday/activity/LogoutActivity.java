package com.dazone.crewdday.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.dazone.crewdday.Cons;
import com.dazone.crewdday.R;
import com.dazone.crewdday._Application;
import com.dazone.crewdday.mInterface.OnGetUserCallback;
import com.dazone.crewdday.mInterface.onDeleteDeviceSuccess;
import com.dazone.crewdday.model.ProfileUserDTO;
import com.dazone.crewdday.other.ErrorDto;
import com.dazone.crewdday.other.HttpRequest;
import com.dazone.crewdday.util.ConnectionUtils;
import com.dazone.crewdday.util.PreferenceUtilities;
import com.dazone.crewdday.util.WebClient;
import com.fasterxml.jackson.databind.JsonNode;
import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import org.w3c.dom.Text;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class LogoutActivity extends AppCompatActivity {
    public static String TAG = "LogoutActivity";
    private int temp = 0;
    private ImageView ivProfileIcon, img_full, ivTheme;
    private TextView tvProfileName, tvCompany, tvPhone, tvExtensionNumber;
    private ScrollView lnabove;
    private Button btnChangePass;
    private RelativeLayout layoutCellPhone, layoutExtensionNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logout);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // add back arrow to toolbar
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setTitle("");
        }


        initImageLoader();


        layoutCellPhone = (RelativeLayout) findViewById(R.id.layoutCellPhone);
        layoutExtensionNumber = (RelativeLayout) findViewById(R.id.layoutExtensionNumber);

        btnChangePass = (Button) findViewById(R.id.btnChangePass);
        btnChangePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LogoutActivity.this, ChangePasswordActivity.class);
                startActivity(intent);
            }
        });

        lnabove = (ScrollView) findViewById(R.id.lnabove);
        PreferenceUtilities prefUtils = _Application.getInstance().getPreferenceUtilities();
        String name = prefUtils.getUserName();
        String email = prefUtils.getEmail();
        String avatar = prefUtils.getAvatar();
        String userId = prefUtils.getUserId();
        String companyName = prefUtils.getCompanyName();
        String serviceDomain = prefUtils.getCurrentServiceDomain();


        //Remove brackets from the string
        String newName = name.replaceAll("\\p{P}", "");
        String newEmail = email.replaceAll("\"", "");
        String newAvatar = avatar.replaceAll("\"", "");
        String newUserId = userId.replaceAll("\"", "");
        String newCompanyName = companyName.replaceAll("\"", "");


        String mUrl = serviceDomain + newAvatar;


        tvProfileName = (TextView) findViewById(R.id.txt_name);
        TextView tvPersonId = (TextView) findViewById(R.id.txt_person_id);
        TextView tvEmail = (TextView) findViewById(R.id.txt_email);
        TextView tvCompanyName = (TextView) findViewById(R.id.txt_company_name);
        ivProfileIcon = (ImageView) findViewById(R.id.img_profile);
        img_full = (ImageView) findViewById(R.id.img_full);
        ivTheme = (ImageView) findViewById(R.id.img_theme);
        tvCompany = (TextView) findViewById(R.id.txt_company);
        tvPhone = (TextView) findViewById(R.id.txt_phone);
        tvExtensionNumber = (TextView) findViewById(R.id.txt_phone_2);
        ivProfileIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                temp = 1;
                lnabove.setVisibility(View.GONE);
                img_full.setVisibility(View.VISIBLE);
            }
        });
        tvProfileName.setText(newName);
        tvCompany.setText(newCompanyName);
        tvPersonId.setText(newUserId);
        tvEmail.setText(newEmail);
        tvCompanyName.setText(newCompanyName);

        ImageLoader imageLoader = ImageLoader.getInstance();
        imageLoader.displayImage(mUrl, ivProfileIcon);
        imageLoader.displayImage(mUrl, img_full);
        imageLoader.displayImage(mUrl, ivTheme);

//        new DownloadImageTask(img_full).execute(mUrl);
//        imageLoader.loadImage(mUrl, new SimpleImageLoadingListener() {
//            @Override
//            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
//                BitmapDrawable bitmapDrawable = new BitmapDrawable(getResources(), loadedImage);
//                ivTheme.setBackground(bitmapDrawable);
//            }
//        });

        getDataFromServer();
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

    void setData(ProfileUserDTO profile) {
        String CellPhone = "";
        String ExtensionNumber = "";
        try {
            CellPhone = profile.getCellPhone();
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            ExtensionNumber = profile.getExtensionNumber();
        } catch (Exception e) {
            e.printStackTrace();
        }
        tvPhone.setText(CellPhone);
        tvExtensionNumber.setText(ExtensionNumber);
        if (CellPhone.length() > 0) layoutCellPhone.setVisibility(View.VISIBLE);
        else layoutCellPhone.setVisibility(View.GONE);
        if (ExtensionNumber.length() > 0) layoutExtensionNumber.setVisibility(View.VISIBLE);
        else layoutExtensionNumber.setVisibility(View.GONE);
    }

    private void getDataFromServer() {
        int id = _Application.getInstance().getPreferenceUtilities().getId();
        HttpRequest.getInstance().GetUser(id, new OnGetUserCallback() {
            @Override
            public void onHTTPSuccess(ProfileUserDTO user) {
                setData(user);
            }

            @Override
            public void onHTTPFail(ErrorDto errorDto) {
                Toast.makeText(getApplicationContext(), "Get data fail, try again", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void backFunction(View v) {
        finish();
    }

    public void logoutFunction(View v) {
//        new WebClientAsync_Logout_v2().execute();
        ConnectionUtils.getInstance().DeleteDevice(new onDeleteDeviceSuccess() {
            @Override
            public void DeleteSuccess() {
                new WebClientAsync_Logout_v2().execute();
            }
        });
    }


    private class WebClientAsync_Logout_v2 extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... params) {
            PreferenceUtilities preferenceUtilities = _Application.getInstance().getPreferenceUtilities();

            WebClient.Logout_v2(preferenceUtilities.getCurrentMobileSessionId(),
                    "http://" + preferenceUtilities.getCurrentCompanyDomain(), new WebClient.OnWebClientListener() {
                        @Override
                        public void onSuccess(JsonNode jsonNode) {
                        }

                        @Override
                        public void onFailure() {
                        }
                    });

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            final PreferenceUtilities preferenceUtilities = _Application.getInstance().getPreferenceUtilities();
            preferenceUtilities.setCurrentMobileSessionId("");
            preferenceUtilities.setCurrentCompanyNo(0);
            preferenceUtilities.setCurrentServiceDomain("");
            preferenceUtilities.setCurrentCompanyDomain("");
            preferenceUtilities.setCurrentUserID("");
            preferenceUtilities.setSectionFragmentPlus("");
            preferenceUtilities.setSectionFragmentComplete("");
            preferenceUtilities.setSectionFragmentMinus("");
            preferenceUtilities.setSectionFragmentAll("");
            preferenceUtilities.setUserName("");
            preferenceUtilities.setEmail("");
            preferenceUtilities.setUserId("");
            preferenceUtilities.setAvatar("");
            preferenceUtilities.setCompanyName("");
            preferenceUtilities.setGCMregistrationid("");
            Intent intent = new Intent(LogoutActivity.this, LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);

        }
    }


    @Override
    public void onBackPressed() {
        onBack();
    }

    void onBack() {
        if (temp == 1) {
            temp = 0;
            lnabove.setVisibility(View.VISIBLE);
            img_full.setVisibility(View.GONE);
        } else {
            finish();
        }
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

    public static class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;
        Bitmap bitmapOrg;
        InputStream input;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            try {
                bitmapOrg = BitmapFactory.decodeStream((InputStream) new URL(urls[0]).getContent());
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return bitmapOrg;
        }

        protected void onPostExecute(final Bitmap result) {
            Log.e(TAG, bitmapOrg.getWidth() + "-" + bitmapOrg.getHeight());
            bmImage.setImageBitmap(bitmapOrg);
        }
    }
}
