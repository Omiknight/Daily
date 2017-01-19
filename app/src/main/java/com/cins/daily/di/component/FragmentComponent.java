package com.cins.daily.di.component;

import android.app.Activity;
import android.content.Context;

import com.cins.daily.di.module.FragmentModule;
import com.cins.daily.di.scope.ContextLife;
import com.cins.daily.di.scope.PerFragment;
import com.cins.daily.mvp.ui.fragment.NewsListFragment;

import dagger.Component;

/**
 * Created by Eric on 2017/1/19.
 */
@PerFragment
@Component(dependencies = ApplicationComponent.class, modules = FragmentModule.class)
public interface FragmentComponent {
    @ContextLife("Activity")
    Context getActivityContext();

    @ContextLife("Application")
    Context getApplicationContext();

    Activity getActivity();

    void inject(NewsListFragment newsListFragment);
}
