package com.cins.daily.di.component;

import com.cins.daily.di.module.NewsListModule;
import com.cins.daily.mvp.ui.fragment.NewsListFragment;

import dagger.Component;

/**
 * Created by Eric on 2017/1/17.
 */
@Component(modules = {NewsListModule.class})
public interface NewsListComponent {
    void inject(NewsListFragment newsFragment);
}
