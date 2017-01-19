package com.cins.daily.di.component;

import android.app.Activity;

import dagger.Component;

/**
 * Created by Eric on 2017/1/19.
 */

@Component(dependencies = AppComponent.class, modules = ActivityModule.class)
public interface ActivityComponent {
    Activity activity();
}
