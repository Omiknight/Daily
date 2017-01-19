package com.cins.daily.di.module;

import android.content.Context;

import com.cins.daily.App;
import com.cins.daily.di.scope.ContextLife;
import com.cins.daily.di.scope.PerApp;

import dagger.Provides;

/**
 * Created by Eric on 2017/1/19.
 */

public class ApplicationModule {
    private App mApplication;

    public ApplicationModule(App application) {
        mApplication = application;
    }

    @Provides
    @PerApp
    @ContextLife("Application")
    public Context provideApplicationContext() {
        return mApplication.getApplicationContext();
    }
}
