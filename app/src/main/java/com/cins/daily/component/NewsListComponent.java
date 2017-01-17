package com.cins.daily.component;

import dagger.Component;

/**
 * Created by Eric on 2017/1/17.
 */
@Component(modules = {NewsListModule.class})
public interface NewsListComponent {
    void inject(NewsListFragment newsFragment);
}
