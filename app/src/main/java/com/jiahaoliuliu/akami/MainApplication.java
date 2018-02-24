package com.jiahaoliuliu.akami;

import android.app.Application;
import android.content.Context;

import com.jiahaoliuliu.akami.dependencyinjection.AppComponent;
import com.jiahaoliuliu.akami.dependencyinjection.DaggerAppComponent;

/**
 * Created by jiahaoliuliu on 7/7/17.
 */

public class MainApplication extends Application {

    private static Context application;
    private AppComponent mAppComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        application = this;

        mAppComponent = DaggerAppComponent
                .builder()
                .build();
    }

    public AppComponent getAppComponent() {
        return mAppComponent;
    }

    public static Context getApplication() {
        return application;
    }
}
