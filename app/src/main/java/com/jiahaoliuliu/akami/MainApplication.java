package com.jiahaoliuliu.akami;

import android.app.Application;

import com.jiahaoliuliu.akami.di.AppComponent;
import com.jiahaoliuliu.akami.di.DaggerAppComponent;

/**
 * Created by jiahaoliuliu on 7/7/17.
 */

public class MainApplication extends Application {

    private AppComponent mAppComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        mAppComponent = DaggerAppComponent
                .builder()
                .build();
    }

    public AppComponent getAppComponent() {
        return mAppComponent;
    }
}
