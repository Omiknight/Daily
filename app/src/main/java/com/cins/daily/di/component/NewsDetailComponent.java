package com.cins.daily.di.component;

import com.cins.daily.di.module.NewsDetailModule;

import dagger.Component;

/**
 * Created by Eric on 2017/1/18.
 */
@Component(modules = {NewsDetailModule.class})
public interface NewsDetailComponent {
    void Inject(NewsDetailModule newsDetailModule);
}
