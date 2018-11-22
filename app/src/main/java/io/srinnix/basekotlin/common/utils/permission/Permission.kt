package io.srinnix.basekotlin.common.utils.permission


data class Permission constructor(val permission: String, val granted: Boolean, val preventAskAgain: Boolean)