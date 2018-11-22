package io.srinnix.basekotlin.common.di.component

import dagger.Component
import io.srinnix.basekotlin.common.di.module.ApiModule
import io.srinnix.basekotlin.common.di.module.CommonModule
import io.srinnix.basekotlin.common.di.module.DaoModule
import io.srinnix.basekotlin.common.di.module.RepositoryModule
import io.srinnix.basekotlin.ui.login.LoginFragment
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(CommonModule::class, RepositoryModule::class, ApiModule::class, DaoModule::class))
interface AppComponent {
    // 起動・その他
    fun inject(fragment: LoginFragment)
}