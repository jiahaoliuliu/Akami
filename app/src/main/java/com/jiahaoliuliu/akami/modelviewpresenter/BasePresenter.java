package com.jiahaoliuliu.akami.modelviewpresenter;

import android.content.Context;

/**
 * The base for all the presenters
 *
 * Created by jiahaoliuliu on 7/7/17.
 */
public interface BasePresenter {

    /**
     * Linked with the lifecycle of the view. This is called
     * on onCreate from the view
     * @param context The context of the application
     */
    void onViewCreated(Context context);

    /**
     * Linked with the lifecycle of the view. This is called
     * on onResume from the view
     */
    void onViewShown();

    /**
     * Linked with the lifecycle of the view. This is called
     * on onPause from the view
     */
    void onViewHidden();

    /**
     * Linked with the lifecycle of the view. This is called
     * on onDestroy from the view
     */
    void onViewDestroyed();
}
