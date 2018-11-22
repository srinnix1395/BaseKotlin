package io.srinnix.basekotlin.common.di.module

import dagger.Module
import dagger.Provides
import io.srinnix.basekotlin.data.dao.LoginDao
import javax.inject.Singleton

@Module
class DaoModule {

    @Provides
    @Singleton
    fun provideLoginDao(): LoginDao {
        return LoginDao()
    }

}