package com.jiahaoliuliu.akami;

import com.jiahaoliuliu.akami.di.AppTestComponent;

/**
 * Created by jiahaoliuliu on 7/15/17.
 */

public class MainTestApplication extends BaseApplication {

    private AppTestComponent mAppTestComponent;

    @Override
    public void onCreate() {
        super.onCreate();
//        DaggerAppTestComponent.builder().build();
//        mAppTestComponent = DaggerAppTestComponent.builder()
//                .build;
    }

    @Override
    public AppTestComponent getComponent() {
        return mAppTestComponent;
    }
}
