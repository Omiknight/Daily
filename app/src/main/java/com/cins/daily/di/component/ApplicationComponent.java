package com.cins.daily.di.component;

import android.content.Context;

import com.cins.daily.di.module.ApplicationModule;
import com.cins.daily.di.scope.ContextLife;
import com.cins.daily.di.scope.PerApp;

import dagger.Component;

/**
 * Created by Eric on 2017/1/19.
 */
@PerApp
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {
    @ContextLife("Application")
    Context getApplication();
}