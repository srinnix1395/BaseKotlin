package io.srinnix.basekotlin.common.di.module

import android.app.Application
import android.content.Context
import dagger.Module
import dagger.Provides

@Module
class CommonModule(private val application: Application) {

    @Provides
    fun provideApplicationContext(): Context {
        return application.applicationContext
    }
}
