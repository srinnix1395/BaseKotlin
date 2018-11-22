package io.srinnix.basekotlin.common.interceptor

import okhttp3.Interceptor
import okhttp3.Response

class HttpHeaderInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        return chain.proceed(request)
    }
}