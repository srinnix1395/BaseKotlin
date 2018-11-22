package io.srinnix.basekotlin.common.di.module

import dagger.Module
import dagger.Provides
import io.srinnix.basekotlin.BuildConfig
import io.srinnix.basekotlin.common.constant.Constant
import io.srinnix.basekotlin.common.interceptor.ApiExceptionInterceptor
import io.srinnix.basekotlin.common.interceptor.HttpHeaderInterceptor
import io.srinnix.basekotlin.data.api.LoginApi
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
class ApiModule {

    @Provides
    @Singleton
    fun provideLoginApi(retrofit: Retrofit): LoginApi {
        return retrofit.create<LoginApi>(LoginApi::class.java)
    }

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        val httpClient = OkHttpClient.Builder()
        httpClient.connectTimeout(1, TimeUnit.MINUTES)
        httpClient.readTimeout(30, TimeUnit.SECONDS)
        httpClient.writeTimeout(20, TimeUnit.SECONDS)
        httpClient.addInterceptor(HttpHeaderInterceptor())
        httpClient.addInterceptor(ApiExceptionInterceptor())
        httpClient.connectTimeout(60, TimeUnit.SECONDS)
        if (BuildConfig.DEBUG) {
            val logging = HttpLoggingInterceptor()
            logging.level = HttpLoggingInterceptor.Level.BODY
            httpClient.addInterceptor(logging)
        }

        // TODO: 一度生成されると設定が固定されてしまうので、実装を検討する (特にfirebase remote config の設定が反映されない)
        return Retrofit.Builder()
                .baseUrl(Constant.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(httpClient.build())
                .build()
    }
}