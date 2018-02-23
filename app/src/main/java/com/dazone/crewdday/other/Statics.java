package com.dazone.crewdday.other;

import android.graphics.Bitmap;

import com.dazone.crewdday.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

/**
 * Created by david on 12/18/15.
 */
public interface Statics {

    String GOOGLE_SENDER_ID = "731703049967";
    String GCM_KEY = "AIzaSyCejK9axRostAjn_sTiiOdg8qZ1hHgzxSg";
//    preft key

    String ORGANIZATION_TREE = "organization_tree";

//    end preft key

    //    bundle key
    String BUNDLE_LIST_PERSON = "listPerson";
    String BUNDLE_ORG_DISPLAY_SELECTED_ONLY = "isDisplaySelectedOnly";
//    end bundle key

    int REQUEST_TIMEOUT_MS = 15000;

    String TAG = "CrewScheduleLogs";
    String TAG_DAVID = "David_tags";
    String PREFS_KEY_SESSION_ERROR = "session_error";

    //    database
    int DATABASE_VERSION = 1;
    String DATABASE_NAME = "crewchat.db";


    int ORGANIZATION_DISPLAY_SELECTED_ACTIVITY = 303;
    DisplayImageOptions options2 = new DisplayImageOptions.Builder()
            .cacheOnDisk(true)
            .cacheInMemory(true)
            .showImageForEmptyUri(R.drawable.avatar_l)
            .showImageOnLoading(R.drawable.avatar_l)
            .showImageOnFail(R.drawable.avatar_l)
            .imageScaleType(ImageScaleType.NONE_SAFE)
            .bitmapConfig(Bitmap.Config.RGB_565)
            .considerExifParams(false)
            .displayer(new RoundedBitmapDisplayer(90))
            .build();
}
