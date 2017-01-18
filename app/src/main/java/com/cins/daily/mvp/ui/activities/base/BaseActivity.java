package com.cins.daily.mvp.ui.activities.base;

import android.content.Context;
import android.graphics.PixelFormat;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NotificationCompatSideChannelService;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;

import com.cins.daily.App;
import com.cins.daily.R;
import com.cins.daily.mvp.presenter.base.BasePresenter;
import com.cins.daily.utils.MyUtils;
import com.squareup.leakcanary.RefWatcher;


/**
 * Created by Eric on 2017/1/16.
 */

public class BaseActivity<T extends BasePresenter> extends AppCompatActivity {
    private static final String LOG_TAG = "BaseActivity";

    private WindowManager mWindowManager = null;
    private View mNightView = null;
    private boolean mIsAddedView;
    protected T mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setNightOrDayMode();
    }

    private void setNightOrDayMode() {
        if (MyUtils.isNightMode()) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);

            initNightView();
            mNightView.setBackgroundResource(R.color.night_mask);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }
    }

    public void initNightView() {
        if (mIsAddedView) {
            return;
        }
        //增加夜间模式蒙版
        WindowManager.LayoutParams nightViewParam = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.TYPE_APPLICATION,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                PixelFormat.TRANSPARENT
        );
        mWindowManager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        mNightView = new View(this);
        mWindowManager.addView(mNightView, nightViewParam);
        mIsAddedView = true;
    }

    public void changeToDay() {
        getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        mNightView.setBackgroundResource(R.color.transparent);
    }

    public void changeToNight() {
        getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        initNightView();
        mNightView.setBackgroundResource(R.color.night_mask);
    }

    // TODO:适配4.4
    protected void setStatusBarTranslucent() {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            SystemBarTintManager tintManager = new SystemBarTintManager(this);
            tintManager.setStatusBarTintEnabled(true);
            tintManager.setStatusBarTintResource(R.color.colorPrimary);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.home:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    finishAfterTransition();
                } else {
                    finish();
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        RefWatcher refWatcher = App.getRefWatcher(this);
        refWatcher.watch(this);

        if (mPresenter != null) {
            mPresenter.onDestroy();
        }
        if (mIsAddedView) {
            //移除夜间蒙版
            mWindowManager.removeViewImmediate(mNightView);
            mWindowManager = null;
            mNightView = null;
        }
    }
}
