package com.cins.daily;

import android.app.Application;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatDelegate;

import com.cins.daily.common.Constants;
import com.cins.daily.greendao.DaoMaster;
import com.cins.daily.greendao.DaoSession;
import com.cins.daily.greendao.NewsChannelTableDao;
import com.cins.daily.utils.MyUtils;
import com.squareup.leakcanary.RefWatcher;

import de.greenrobot.dao.query.QueryBuilder;

/**
 * Created by Eric on 2017/1/16.
 */

public class App extends Application {
    private RefWatcher mRefWatcher;
    private static Context sAppContext;
    private static DaoSession mDaoSession;

    public static RefWatcher getRefWatcher(Context context) {
        App application = (App) context.getApplicationContext();
        return application.mRefWatcher;
    }

    public static Context getAppContext() {
        return sAppContext;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sAppContext = this;
        if (MyUtils.isNightMode()) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }
        // 官方推荐将获取 DaoMaster 对象的方法放到 Application 层，这样将避免多次创建生成 Session 对象
        setupDatabase();
    }

    public static NewsChannelTableDao getNewsChannelTableDao() {
        return mDaoSession.getNewsChannelTableDao();
    }

    private void setupDatabase() {
        // 通过 DaoMaster 的内部类 DevOpenHelper，你可以得到一个便利的 SQLiteOpenHelper 对象。
        // 可能你已经注意到了，你并不需要去编写「CREATE TABLE」这样的 SQL 语句，因为 greenDAO 已经帮你做了。
        // 注意：默认的 DaoMaster.DevOpenHelper 会在数据库升级时，删除所有的表，意味着这将导致数据的丢失。
        // 所以，在正式的项目中，你还应该做一层封装，来实现数据库的安全升级。
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, Constants.DB_NAME, null);
        SQLiteDatabase db = helper.getWritableDatabase();
        // 注意：该数据库连接属于 DaoMaster，所以多个 Session 指的是相同的数据库连接。
        DaoMaster daoMaster = new DaoMaster(db);
        mDaoSession = daoMaster.newSession();
        // 在 QueryBuilder 类中内置两个 Flag 用于方便输出执行的 SQL 语句与传递参数的值
        QueryBuilder.LOG_SQL = BuildConfig.DEBUG;
        QueryBuilder.LOG_VALUES = BuildConfig.DEBUG;
    }
}
