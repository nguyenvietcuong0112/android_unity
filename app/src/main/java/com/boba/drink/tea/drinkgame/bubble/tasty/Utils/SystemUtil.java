package com.boba.drink.tea.drinkgame.bubble.tasty.Utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.net.ConnectivityManager;

import java.util.Locale;

public class SystemUtil {
    private static Locale myLocale;

    public static void saveLocale(Context context, String lang) {
        setPreLanguage(context, lang);
    }

    public static void setLocale(Context context) {
        String language = getPreLanguage(context);
        if (language.equals("")) {
            Configuration config = new Configuration();
            Locale locale = Locale.getDefault();
            Locale.setDefault(locale);
            config.locale = locale;

            context.getResources()
                    .updateConfiguration(config, context.getResources().getDisplayMetrics());
        } else {
            changeLang(language, context);
        }
    }

    public static void changeLang(String lang, Context context) {
        if (lang.equalsIgnoreCase(""))
            return;
        myLocale = new Locale(lang);
        saveLocale(context, lang);
        Locale.setDefault(myLocale);
        Configuration config = new Configuration();
        config.locale = myLocale;
        context.getResources().updateConfiguration(config, context.getResources().getDisplayMetrics());
    }

    public static String getPreLanguage(Context mContext) {
        SharedPreferences preferences = mContext.getSharedPreferences("data", Context.MODE_PRIVATE);
        return preferences.getString("KEY_LANGUAGE", "en");
    }

    public static void setPreLanguage(Context context, String language) {
        if (language == null || language == "") {
            return;
        } else {
            SharedPreferences preferences = context.getSharedPreferences("data", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString("KEY_LANGUAGE", language);
            editor.apply();
        }
    }


    public static String getIsoSound(Context mContext) {
        SharedPreferences preferences = mContext.getSharedPreferences("data", Context.MODE_PRIVATE);
        return preferences.getString("isoSound", "bird");
    }

    public static void setIsoSound(Context context, String isoSound) {
        SharedPreferences preferences = context.getSharedPreferences("data", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("isoSound", isoSound);
        editor.apply();
    }

    public static boolean isNetworkConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
    }


}