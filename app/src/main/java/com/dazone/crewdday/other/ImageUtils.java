package com.dazone.crewdday.other;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Build;
import android.text.TextUtils;
import android.util.Log;
import android.widget.ImageView;

import com.dazone.crewdday._Application;
import com.dazone.crewdday.util.PreferenceUtilities;
import com.nostra13.universalimageloader.core.ImageLoader;


import java.io.File;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by david on 12/25/15.
 */
public class ImageUtils {

    private static PreferenceUtilities mPref = _Application.getInstance().getPreferenceUtilities();
    public static InputStream input;

    @TargetApi(Build.VERSION_CODES.M)
    public static final int getColor(Context context, int id) {
        final int version = Build.VERSION.SDK_INT;
        if (version >= 23) {
            return context.getColor(id);
        } else {
            return context.getResources().getColor(id);
        }
    }

    public static void showImage_custom(DrawImageItem dto, final ImageView view) {
        ImageLoader imageLoader = ImageLoader.getInstance();
        PreferenceUtilities prefUtils = _Application.getInstance().getPreferenceUtilities();
        String serviceDomain = prefUtils.getCurrentServiceDomain();

        if (TextUtils.isEmpty(dto.getImageLink())) {
            String url = serviceDomain + "/Images/avatar.jpg";
            imageLoader.displayImage(url, view);
        } else {
            String url = serviceDomain + dto.getImageLink();
            imageLoader.displayImage(url, view);
        }
    }

}
