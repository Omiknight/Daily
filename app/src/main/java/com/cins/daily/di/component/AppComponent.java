package com.cins.daily.di.component;

import com.cins.daily.App;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by Eric on 2017/1/19.
 */

@Singleton
@Component(modules = AppModule.clss)
public interface AppComponent {
    void inject(App app);
}
