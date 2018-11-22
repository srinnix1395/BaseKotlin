package io.srinnix.basekotlin.ui.base

interface BaseView {

    fun isNetworkAvailable(): Boolean

    fun showDialogNoInternet()

    fun backScreen()

    fun showProgressDialog(isShow: Boolean)
}