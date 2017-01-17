package com.cins.daily.component;

import com.cins.daily.module.NewsModule;
import com.cins.daily.mvp.ui.activities.NewsActivity;

import dagger.Component;

/**
 * Created by Eric on 2017/1/16.
 */

@Component(modules = {NewsModule.class})
public interface NewsComponent {

    void inject(NewsActivity newsActivity);

}
