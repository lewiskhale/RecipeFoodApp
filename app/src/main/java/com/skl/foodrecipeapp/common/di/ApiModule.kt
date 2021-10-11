package com.skl.foodrecipeapp.common.di

import com.skl.foodrecipeapp.common.data.datasource.api.ApiConstants.BASE_URL
import com.skl.foodrecipeapp.common.data.datasource.api.webServices.RecipeNetworkDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


@InstallIn(SingletonComponent::class)
@Module
object ApiModule {

    @Singleton
    @Provides
    fun provideOkhttpclient() =
        OkHttpClient.Builder()
            .connectTimeout(3, TimeUnit.SECONDS)
            .readTimeout(3, TimeUnit.SECONDS)
            .writeTimeout(3, TimeUnit.SECONDS)
            .build()

    @Singleton
    @Provides
    fun provideRetrofitApi(okHttpClient: OkHttpClient): RecipeNetworkDataSource =
        Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(RecipeNetworkDataSource::class.java)

}