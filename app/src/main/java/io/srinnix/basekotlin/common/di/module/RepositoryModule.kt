package io.srinnix.basekotlin.common.di.module

import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RepositoryModule {

    @Provides
    @Singleton
    fun providesLoginRepository(repository: LoginReposImpl): LoginRepos {
        return repository
    }
}