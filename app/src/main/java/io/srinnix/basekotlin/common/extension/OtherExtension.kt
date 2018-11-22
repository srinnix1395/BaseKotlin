package io.srinnix.basekotlin.common.extension

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import org.greenrobot.eventbus.EventBus


fun Context.isConnected(): Boolean {
    val cm: ConnectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val netInfo: NetworkInfo? = cm.activeNetworkInfo
    return netInfo != null && netInfo.isConnected
}

//region eventbus
inline fun <reified T> Any.postEvent(t: T) {
    if (EventBus.getDefault().hasSubscriberForEvent(T::class.java)) {
        EventBus.getDefault().post(t)
    }
}

inline fun <reified T> Any.postStickyEvent(t: T) {
    EventBus.getDefault().postSticky(t)
}

fun <T> Any.getStickyEvent(clazz: Class<T>): T? {
    return EventBus.getDefault().getStickyEvent(clazz)
}

fun Any.registerEventBus() {
    if (!EventBus.getDefault().isRegistered(this)) {
        EventBus.getDefault().register(this)
    }
}

fun Any.unregisterEventBus() {
    if (EventBus.getDefault().isRegistered(this)) {
        EventBus.getDefault().unregister(this)
    }
}
//endregion eventbus