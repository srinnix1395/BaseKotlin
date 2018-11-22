package io.srinnix.basekotlin.ui.base

interface Presenter<V: BaseView> {

    fun attachView(view: V)

    fun detachView()
}