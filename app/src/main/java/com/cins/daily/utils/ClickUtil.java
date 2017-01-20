package com.cins.daily.utils;

import android.os.SystemClock;

/**
 * Created by Eric on 2017/1/20.
 */

public class ClickUtil {
    private static long mLastClickTime = 0;
    private static final int SPACE_TIME = 500;

    public static boolean isFastDoubleClick() {
        long time = SystemClock.elapsedRealtime();
        if (time - mLastClickTime <= SPACE_TIME) {
            return true;
        } else {
            mLastClickTime = time;
            return false;
        }
    }
}
