package io.srinnix.basekotlin.common.exception

import com.google.gson.annotations.SerializedName

/**
 * Created by dr.joyno054 on 2017/07/31.
 */
class ApiError {

    @SerializedName("message")
    var message: String? = null

    @SerializedName("error_codes")
    var errorCodes: List<String>? = null

}