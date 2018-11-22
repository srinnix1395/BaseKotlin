package io.srinnix.basekotlin.data.repository

import io.reactivex.Single
import io.srinnix.basekotlin.data.api.LoginApi
import io.srinnix.basekotlin.ui.login.LoginRepos
import javax.inject.Inject

class LoginReposImpl @Inject constructor() : LoginRepos {

    @Inject
    lateinit var api: LoginApi

    override fun login(user: String, password: String): Single<Void> {

    }
}