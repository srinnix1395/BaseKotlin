package io.srinnix.basekotlin.ui.base

import javax.inject.Inject

open class BasePresenter<V : BaseView> @Inject constructor() : Presenter<V> {

    var view: V? = null

    override fun attachView(view: V) {
        this.view = view
    }

    override fun detachView() {
        this.view = null
    }
}