package io.srinnix.basekotlin.common.exception

import com.google.gson.GsonBuilder
import okhttp3.Response


/**
 * Created by dr.joyno054 on 2017/07/28.
 */
class ApiException : Exception {

    val error: ApiError
    val url: String
    val method: String
    val status: Int

    constructor(response: Response) {
        val errJson = response.body()?.string()
        if(errJson != null) {
            this.error = GsonBuilder().create().fromJson(errJson, ApiError::class.java)
        }else{
            this.error = ApiError()
        }

        this.url = response.request().url().toString()
        this.method = response.request().method()
        this.status = response.code()
    }

}