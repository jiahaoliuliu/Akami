package com.jiahaoliuliu.akami.modelviewpresenter;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by jiahaoliuliu on 7/7/17.
 */

public class BaseActivity extends AppCompatActivity {

    protected BasePresenter mBasePresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBasePresenter.onViewCreated();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mBasePresenter.onViewShown();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mBasePresenter.onViewHidden();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mBasePresenter.onViewDestroyed();
    }

    /**
     * Set the presenter for this activity.
     *
     * @param basePresenter
     */
    public void setPresenter(BasePresenter basePresenter) {
        this.mBasePresenter = basePresenter;
    }
}
