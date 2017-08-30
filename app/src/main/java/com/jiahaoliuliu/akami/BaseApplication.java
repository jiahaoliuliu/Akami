package com.jiahaoliuliu.akami;

import android.app.Application;

import com.jiahaoliuliu.akami.di.AppComponent;

/**
 * Created by jiahaoliuliu on 7/16/17.
 */

public abstract class BaseApplication extends Application {

    private static BaseApplication sInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;
    }

    public static BaseApplication getInstance() {
        return sInstance;
    }

    public abstract AppComponent getComponent();
}
