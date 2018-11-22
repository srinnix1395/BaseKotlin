package io.srinnix.basekotlin.common.interceptor

import io.srinnix.basekotlin.common.exception.ApiException
import okhttp3.Interceptor
import okhttp3.Response

/**
 * Created by ChinhDV on 8/15/2017.
 */
class ApiExceptionInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain?): Response? {
        val request = chain?.request()
        val response: Response? = chain?.proceed(request)
        if (!response?.isSuccessful!!) {
            throw ApiException(response)
        }
        return response
    }
}