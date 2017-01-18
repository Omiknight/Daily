package com.cins.daily.utils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;


import com.cins.daily.App;
import com.cins.daily.common.Constants;
import com.socks.library.KLog;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Eric on 2017/1/17.
 */

public class MyUtils {
    public static boolean isNightMode() {
        SharedPreferences preferences = App.getAppContext().getSharedPreferences(
                Constants.SHARES_COLOURFUL_NEWS, Activity.MODE_PRIVATE
        );
        return preferences.getBoolean(Constants.NIGHT_THEME_MODE,false);
    }

    public static void saveTheme(boolean isNight) {
        SharedPreferences preferences = App.getAppContext().getSharedPreferences(
                Constants.SHARES_COLOURFUL_NEWS, Activity.MODE_PRIVATE
        );
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(Constants.NIGHT_THEME_MODE, isNight);
        editor.apply();
    }

    public static SharedPreferences getSharedPreferences() {
        return App.getAppContext()
                .getSharedPreferences(Constants.SHARES_COLOURFUL_NEWS, Context.MODE_PRIVATE);
    }

    /**
     * from yyyy-MM-dd HH:mm:ss to MM-dd HH:mm
     */
    public static String formatDate(String before) {
        String after;
        try {
            Date date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
                    .parse(before);
            after = new SimpleDateFormat("MM-dd HH:mm", Locale.getDefault()).format(date);
        } catch (ParseException e) {
            KLog.e("转换新闻日期格式异常：" + e.toString());
            return before;
        }
        return after;
    }
}
