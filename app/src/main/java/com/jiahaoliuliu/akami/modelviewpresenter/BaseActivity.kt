package com.jiahaoliuliu.akami.modelviewpresenter

import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity

open class BaseActivity : AppCompatActivity() {

    var basePresenter: BasePresenter? = null
    var context: Context? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        context = this
        basePresenter?.onViewCreated()
    }

    override fun onResume() {
        super.onResume()
        basePresenter?.onViewShown()
    }

    override fun onPause() {
        basePresenter?.onViewHidden()
        super.onPause()
    }

    override fun onDestroy() {
        basePresenter?.onViewDestroyed()
        super.onDestroy()
    }
}