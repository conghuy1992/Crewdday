package com.dazone.crewdday.util;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;

import java.lang.reflect.Field;

/**
 * Created by DAZONE on 16/03/16.
 */
public class ImageUtils {

    public static int getDrawableIdByName(String resourceName, Class<?> c) {
        try {
            Field idField = c.getDeclaredField(resourceName);
            return idField.getInt(idField);
        } catch (Exception e) {
            throw new RuntimeException("No resource ID found for: " + resourceName + " / " + c, e);
        }
    }

    public static Drawable getDrawable(Context context, int id) {
        Drawable drawable;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            drawable = context.getResources().getDrawable(id, context.getTheme());
        } else {
            drawable = context.getResources().getDrawable(id);
        }
        return drawable;
    }


}
