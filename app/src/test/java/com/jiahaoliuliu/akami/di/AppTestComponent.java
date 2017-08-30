package com.jiahaoliuliu.akami.di;

import com.jiahaoliuliu.akami.test.transactionslist.MainActivityTest;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by jiahaoliuliu on 7/15/17.
 */

@Singleton
@Component(modules = {
        MvpTestModule.class})
public interface AppTestComponent extends AppComponent {

    // Activities
    void inject(MainActivityTest mainActivityTest);

}
