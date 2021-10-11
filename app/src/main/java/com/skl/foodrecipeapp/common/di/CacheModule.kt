package com.skl.foodrecipeapp.common.di

import android.content.Context
import androidx.room.Room
import com.skl.foodrecipeapp.common.data.datasource.cache.CacheConstants.DATABASE_NAME
import com.skl.foodrecipeapp.common.data.datasource.cache.RecipeAppDatabase
import com.skl.foodrecipeapp.common.data.datasource.cache.RecipeAppDatabase_Impl
import com.skl.foodrecipeapp.common.data.datasource.cache.daos.RecipeCacheDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object CacheModule {

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context): RecipeAppDatabase =
        Room.databaseBuilder(
            context,
            RecipeAppDatabase::class.java,
            DATABASE_NAME
        )
            .fallbackToDestructiveMigration()
            .build()

    @Singleton
    @Provides
    fun provideCacheDatasource(db: RecipeAppDatabase): RecipeCacheDataSource =
        db.recipeDao

}