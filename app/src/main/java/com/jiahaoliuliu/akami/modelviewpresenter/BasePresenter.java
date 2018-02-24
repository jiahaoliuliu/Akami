package com.jiahaoliuliu.akami.modelviewpresenter;

/**
 * The base for all the presenters
 *
 * Created by jiahaoliuliu on 7/7/17.
 */
public interface BasePresenter {

    /**
     * Set the base view
     * @param view
     */
    void setView(BaseView view);

    /**
     * Linked with the lifecycle of the view. This is called
     * on onCreate from the view
     */
    void onViewCreated();

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
