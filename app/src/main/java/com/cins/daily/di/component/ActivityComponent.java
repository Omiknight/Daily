package com.cins.daily.di.component;

import android.app.Activity;
import android.content.Context;

import com.cins.daily.di.module.ActivityModule;
import com.cins.daily.di.scope.ContextLife;
import com.cins.daily.di.scope.PerActivity;
import com.cins.daily.mvp.ui.activities.NewsActivity;
import com.cins.daily.mvp.ui.activities.NewsChannelActivity;
import com.cins.daily.mvp.ui.activities.NewsDetailActivity;
import com.cins.daily.mvp.ui.activities.NewsPhotoDetailActivity;

import dagger.Component;

/**
 * Created by Eric on 2017/1/19.
 */
@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = ActivityModule.class)
public interface ActivityComponent {

    @ContextLife("Activity")
    Context getActivityContext();

    @ContextLife("Application")
    Context getApplicationContext();

    Activity getActivity();

    void inject(NewsActivity newsActivity);

    void inject(NewsDetailActivity newsDetailActivity);

    void inject(NewsChannelActivity newsChannelActivity);

    void inject(NewsPhotoDetailActivity newsPhotoDetailActivity);

    //void inject(PhotoActivity photoActivity);

    //void inject(PhotoDetailActivity photoDetailActivity);
}
