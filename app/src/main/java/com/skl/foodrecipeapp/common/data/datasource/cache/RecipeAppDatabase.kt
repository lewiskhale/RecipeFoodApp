package com.skl.foodrecipeapp.common.data.datasource.cache

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.skl.foodrecipeapp.common.data.datasource.api.webServices.RecipeNetworkDataSource
import com.skl.foodrecipeapp.common.data.datasource.cache.daos.RecipeCacheDataSource
import com.skl.foodrecipeapp.common.data.datasource.cache.daos.RemoteKeyDao
import com.skl.foodrecipeapp.common.data.datasource.cache.model.CacheRecipe
import com.skl.foodrecipeapp.common.data.datasource.cache.model.RemoteKey
import com.skl.foodrecipeapp.common.data.datasource.cache.util.Converters

@Database(entities = [CacheRecipe::class, RemoteKey::class], version = 1)
@TypeConverters(Converters::class)
abstract class RecipeAppDatabase: RoomDatabase() {

    abstract val recipeDao: RecipeCacheDataSource
    abstract val remoteKeyDao: RemoteKeyDao

}