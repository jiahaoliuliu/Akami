package com.jiahaoliuliu.akami.di;

import com.jiahaoliuliu.akami.transactionslist.MainActivity;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by jiahaoliuliu on 7/7/17.
 */

@Singleton
@Component(modules = MvpModule.class)
public interface AppComponent {

    // Views -> To inject the views, the activity should be used because the variables to be
    // injected are in the activities, not in the views itself
    void inject(MainActivity mainActivity);
}
