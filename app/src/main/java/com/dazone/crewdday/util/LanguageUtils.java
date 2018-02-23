package com.dazone.crewdday.util;

import java.util.Locale;

/**
 * Created by DAZONE on 04/03/16.
 */

public class LanguageUtils {

    public static String getPhoneLanguage()
    {
        return Locale.getDefault().getLanguage();
    }

    public static boolean isPhoneLanguageEN()
    {
        return Locale.getDefault().getLanguage().equalsIgnoreCase("EN");
    }
    public static boolean isPhoneLanguageVN()
    {
        return Locale.getDefault().getLanguage().equalsIgnoreCase("VN");
    }
    public static boolean isPhoneLanguageKO()
    {
        return Locale.getDefault().getLanguage().equalsIgnoreCase("KO");
    }
}
