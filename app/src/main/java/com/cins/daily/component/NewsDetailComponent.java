package com.cins.daily.component;

import com.cins.daily.module.NewsDetailModule;

import dagger.Component;

/**
 * Created by Eric on 2017/1/18.
 */
@Component(modules = {NewsDetailModule.class})
public interface NewsDetailComponent {
    void Inject(NewsDetailModule newsDetailModule);
}
