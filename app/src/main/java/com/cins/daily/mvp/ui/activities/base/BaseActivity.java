package com.cins.daily.mvp.ui.activities.base;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.SwitchCompat;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.cins.daily.App;
import com.cins.daily.R;
import com.cins.daily.annotation.BindValues;
import com.cins.daily.common.Constants;
import com.cins.daily.di.component.ActivityComponent;
import com.cins.daily.di.component.DaggerActivityComponent;
import com.cins.daily.di.module.ActivityModule;
import com.cins.daily.mvp.entity.NewsChannelTable;
import com.cins.daily.mvp.presenter.base.BasePresenter;
import com.cins.daily.mvp.ui.activities.AboutActivity;
import com.cins.daily.mvp.ui.activities.NewsActivity;
import com.cins.daily.mvp.ui.activities.NewsDetailActivity;
import com.cins.daily.utils.MyUtils;
import com.cins.daily.utils.NetUtil;
import com.cins.daily.utils.SharedPreferencesUtil;
import com.readystatesoftware.systembartint.SystemBarTintManager;
import com.socks.library.KLog;
import com.squareup.leakcanary.RefWatcher;

import java.util.List;

import butterknife.ButterKnife;
import rx.Subscription;

/**
 * Created by Eric on 2017/1/16.
 */
public abstract class BaseActivity<T extends BasePresenter> extends AppCompatActivity {
    protected ActivityComponent mActivityComponent;
    private boolean mIsChangeTheme;
    public Activity mActivity;
    public ActivityComponent getActivityComponent() {
        return mActivityComponent;
    }

    private WindowManager mWindowManager = null;
    private View mNightView = null;
    private boolean mIsAddedView;
    protected T mPresenter;
    protected boolean mIsHasNavigationView;
    private DrawerLayout mDrawerLayout;
    private Class mClass;

    public abstract int getLayoutId();

    public abstract void initInjector();

    public abstract void initViews();

    protected Subscription mSubscription;
    protected NavigationView mBaseNavView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = this;
        KLog.i(getClass().getSimpleName());
        initAnnotation();
        NetUtil.isNetworkErrThenShowMsg();
        initActivityComponent();
        setStatusBarTranslucent();

        int layoutId = getLayoutId();
        setContentView(layoutId);
        initInjector();
        ButterKnife.bind(this);
        initToolBar();
        initViews();
        if (mIsHasNavigationView) {
            initDrawerLayout();
        }
        if (mPresenter != null) {
            mPresenter.onCreate();
        }

        initNightModeSwitch();
    }

    private void initAnnotation() {
        if (getClass().isAnnotationPresent(BindValues.class)) {
            BindValues annotation = getClass().getAnnotation(BindValues.class);
            mIsHasNavigationView = annotation.mIsHasNavigationView();
        }
    }



    private void initActivityComponent() {
        mActivityComponent = DaggerActivityComponent.builder()
                .applicationComponent(((App) getApplication()).getApplicationComponent())
                .activityModule(new ActivityModule(this))
                .build();
    }


    private void initToolBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }
    private void initNightModeSwitch() {
        if (this instanceof NewsActivity) {
            MenuItem menuNightMode = mBaseNavView.getMenu().findItem(R.id.nav_night_mode);
            SwitchCompat dayNightSwitch = (SwitchCompat) MenuItemCompat
                    .getActionView(menuNightMode);
            setCheckedState(dayNightSwitch);
            setCheckedEvent(dayNightSwitch);
        }
    }

    private void setCheckedState(SwitchCompat dayNightSwitch) {
        boolean isNight = SharedPreferencesUtil.getBoolean(this, Constants.ISNIGHT, false);
        if (isNight) {
            dayNightSwitch.setChecked(true);
        } else {
            dayNightSwitch.setChecked(false);
        }
    }

    private void setCheckedEvent(SwitchCompat dayNightSwitch) {
        dayNightSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    SharedPreferencesUtil.setBoolean(mActivity, Constants.ISNIGHT, true);
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                } else {
                    SharedPreferencesUtil.setBoolean(mActivity, Constants.ISNIGHT, false);
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                }
                mIsChangeTheme = true;
                mDrawerLayout.closeDrawer(GravityCompat.START);
            }
        });
    }
    private void initDrawerLayout() {

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        NavigationView navView = (NavigationView) findViewById(R.id.nav_view);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, mDrawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        if (navView != null) {
            navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(MenuItem item) {
                    switch (item.getItemId()) {
                        case R.id.nav_news:
                            mClass = NewsActivity.class;
                            break;
                        /*case R.id.nav_photo:
                            //mClass = PhotoActivity.class;
                            break;
                        case R.id.nav_video:
                            Toast.makeText(BaseActivity.this, "施工准备中...", Toast.LENGTH_SHORT).show();
                            break;*/
                        case R.id.nav_about:
                            mClass = AboutActivity.class;
                            break;
                        case R.id.nav_night_mode:
                            break;
                    }
                    mDrawerLayout.closeDrawer(GravityCompat.START);
                    return false;
                }
            });
        }

        mDrawerLayout.addDrawerListener(new DrawerLayout.SimpleDrawerListener() {
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                if (mClass != null) {
                    Intent intent = new Intent(BaseActivity.this, mClass);
                    // 此标志用于启动一个Activity的时候，若栈中存在此Activity实例，则把它调到栈顶。不创建多一个
//                    intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                    startActivity(intent);
                    overridePendingTransition(0, 0);
                    mClass = null;
                }

                if (mIsChangeTheme) {
                    mIsChangeTheme = false;
                    getWindow().setWindowAnimations(R.style.WindowAnimationFadeInOut);
                    recreate();
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (mIsHasNavigationView && mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }



    //colorPrimaryDark
    @TargetApi(Build.VERSION_CODES.KITKAT)
    protected void setStatusBarTranslucent() {
        if ((Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT &&
                !(this instanceof NewsDetailActivity /*|| this instanceof PhotoActivity
                        || this instanceof PhotoDetailActivity*/))
                || (Build.VERSION.SDK_INT == Build.VERSION_CODES.KITKAT
                && this instanceof NewsDetailActivity)) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            SystemBarTintManager tintManager = new SystemBarTintManager(this);
            tintManager.setStatusBarTintEnabled(true);
            tintManager.setStatusBarTintResource(R.color.colorPrimaryDark);
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        if (mIsHasNavigationView) {
            overridePendingTransition(0, 0);
        }
//        getWindow().getDecorView().invalidate();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                finishAfterTransition();
            } else {
                finish();
            }
        } else if (id == R.id.action_day_night_yes) {
            int currentNightMode = getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
            if (currentNightMode == Configuration.UI_MODE_NIGHT_YES) {
                SharedPreferencesUtil.setBoolean(mActivity, Constants.ISNIGHT, false);
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            } else {
                SharedPreferencesUtil.setBoolean(mActivity, Constants.ISNIGHT, true);
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            }
            //调用 recreate(); 使设置生效
            getWindow().setWindowAnimations(R.style.WindowAnimationFadeInOut);
            recreate();
            return true;
        } else if (id == R.id.action_settings) {

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (mIsHasNavigationView) {
            getMenuInflater().inflate(R.menu.main, menu);
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        RefWatcher refWatcher = App.getRefWatcher(this);
        refWatcher.watch(this);

        if (mPresenter != null) {
            mPresenter.onDestroy();
        }
        //removeNightModeMask();
        MyUtils.cancelSubscription(mSubscription);
        MyUtils.fixInputMethodManagerLeak(this);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }
}
