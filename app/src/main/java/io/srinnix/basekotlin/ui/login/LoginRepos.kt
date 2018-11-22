package io.srinnix.basekotlin.ui.login

import io.reactivex.Single

interface LoginRepos {

    fun login(user: String, password: String) : Single<Void>
}