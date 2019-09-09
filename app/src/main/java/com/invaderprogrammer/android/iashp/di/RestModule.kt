package com.invaderprogrammer.android.iashp.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.invaderprogrammer.android.iashp.rest.SmisApi
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

@Module
class RestModule {

    @Provides
    @Singleton
    fun provideGson(): Gson =
        GsonBuilder()
            .setLenient()
            .create()

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        return OkHttpClient.Builder()
            .addInterceptor(httpLoggingInterceptor.apply {
                httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
            })
            .connectTimeout(60, TimeUnit.SECONDS)
            .build()
    }


    @Provides
    @Singleton
    @Named("SMIS_API")
    fun provideIASHPRetrofit(gson: Gson, okHttpClient: OkHttpClient): Retrofit =
        Retrofit.Builder()
            .baseUrl("https://gisp.gov.ru/iashp/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(okHttpClient)
            .build()


    @Provides
    @Singleton
    fun provideIASHPApiService(@Named("SMIS_API") retrofit: Retrofit): SmisApi =
        retrofit.create(SmisApi::class.java)
}