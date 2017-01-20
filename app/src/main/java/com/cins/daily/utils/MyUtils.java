package com.cins.daily.utils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.design.widget.TabLayout;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;

import com.cins.daily.App;
import com.cins.daily.R;
import com.cins.daily.common.Constants;
import com.socks.library.KLog;

import java.lang.reflect.Field;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import retrofit2.adapter.rxjava.HttpException;
import rx.Subscription;

/**
 * Created by Eric on 2017/1/17.
 */

public class MyUtils {
    public static boolean isNightMode() {
        SharedPreferences preferences = App.getAppContext().getSharedPreferences(
                Constants.SHARES_COLOURFUL_NEWS, Activity.MODE_PRIVATE
        );
        return preferences.getBoolean(Constants.NIGHT_THEME_MODE, false);
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

    public static int getStatusBarHeight(Activity activity) {
        int height = 0;
        int resourceId = activity.getResources().getIdentifier("status_bar_height", "dimen",
                "android");
        if (resourceId > 0) {
            height = activity.getResources().getDimensionPixelSize(resourceId);
        }
        return height;
    }

    public static void dynamicSetTabLayoutMode(TabLayout tabLayout) {
        int tabWidth = calculateTabWidth(tabLayout);
        int screenWidth = getScreenWith();

        if (tabWidth <= screenWidth) {
            tabLayout.setTabMode(TabLayout.MODE_FIXED);
        } else {
            tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        }
    }

    private static int calculateTabWidth(TabLayout tabLayout) {
        int tabWidth = 0;
        for (int i = 0; i < tabLayout.getChildCount(); i++) {
            final View view = tabLayout.getChildAt(i);
            view.measure(0, 0); // 通知父view测量，以便于能够保证获取到宽高
            tabWidth += view.getMeasuredWidth();
        }
        return tabWidth;
    }

    public static int getScreenWith() {
        return App.getAppContext().getResources().getDisplayMetrics().widthPixels;
    }

    public static int getColor(int nightColor, int dayColor) {
        int color;
        if (!MyUtils.isNightMode()) {
            color = nightColor;
        } else {
            color = dayColor;
        }
        return color;
    }

    public static String analyzeNetworkError(Throwable e) {
        String errMsg = App.getAppContext().getString(R.string.load_error);
        if (e instanceof HttpException) {
            int state = ((HttpException) e).code();
            if (state == 403) {
                errMsg = App.getAppContext().getString(R.string.retry_after);
            }
        }
        return errMsg;
    }

    public static void cancelSubscription(Subscription subscription) {
        if (subscription != null && !subscription.isUnsubscribed()) {
            subscription.unsubscribe();
        }
    }

    /**
     * 解决InputMethodManager内存泄露现象
     */
    public static void fixInputMethodManagerLeak(Context destContext) {
        if (destContext == null) {
            return;
        }

        InputMethodManager imm = (InputMethodManager) destContext
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm == null) {
            return;
        }

        String[] arr = new String[]{"mCurRootView", "mServedView", "mNextServedView"};
        Field f;
        Object obj_get;
        for (String param : arr) {
            try {
                f = imm.getClass().getDeclaredField(param);
                if (!f.isAccessible()) {
                    f.setAccessible(true);
                } // author: sodino mail:sodino@qq.com
                obj_get = f.get(imm);
                if (obj_get != null && obj_get instanceof View) {
                    View v_get = (View) obj_get;
                    if (v_get
                            .getContext() == destContext) { // 被InputMethodManager持有引用的context是想要目标销毁的
                        f.set(imm, null); // 置空，破坏掉path to gc节点
                    } else {
                        // 不是想要目标销毁的，即为又进了另一层界面了，不要处理，避免影响原逻辑,也就不用继续for循环了
                        /*if (QLog.isColorLevel()) {
                            QLog.d(ReflecterHelper.class.getSimpleName(), QLog.CLR, "fixInputMethodManagerLeak break, context is not suitable, get_context=" + v_get.getContext()+" dest_context=" + destContext);
                        }*/
                        break;
                    }
                }
            } catch (Throwable t) {
                t.printStackTrace();
            }
        }
    }

    public static View getRootView(Activity context) {
        return ((ViewGroup) context.findViewById(android.R.id.content)).getChildAt(0);
    }
}
