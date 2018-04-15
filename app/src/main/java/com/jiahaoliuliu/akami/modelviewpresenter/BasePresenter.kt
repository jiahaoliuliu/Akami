package com.jiahaoliuliu.akami.modelviewpresenter

interface BasePresenter {

    fun setView(baseView: BaseView)

    /**
     * Linked with the lifecycle of the view. This is called
     * on onCreate from the view
     */
    fun onViewCreated()

    /**
     * Linked with the lifecycle of the view. This is called
     * on onResume from the view
     */
    fun onViewShown()

    /**
     * Linked with the lifecycle of the view. This is called
     * on onPause from the view
     */
    fun onViewHidden()

    /**
     * Linked with the lifecycle of the view. This is called
     * on onDestroy from the view
     */
    fun onViewDestroyed()
}