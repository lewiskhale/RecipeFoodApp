package com.skl.foodrecipeapp.common.di

import com.skl.foodrecipeapp.common.data.datasource.api.mapper.ApiRecipeMapper
import com.skl.foodrecipeapp.common.data.datasource.api.webServices.RecipeNetworkDataSource
import com.skl.foodrecipeapp.common.data.datasource.cache.RecipeAppDatabase
import com.skl.foodrecipeapp.common.data.repository.RecipesRepositoryImpl
import com.skl.foodrecipeapp.common.domain.mapper.RecipeMapper
import com.skl.foodrecipeapp.common.domain.repository.RecipeRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@InstallIn(SingletonComponent::class)
@Module
object AppModule {

    @Provides
    fun provideRecipeRepository(
        api: RecipeNetworkDataSource,
        db: RecipeAppDatabase,
        apiMapper: ApiRecipeMapper,
        domainMapper: RecipeMapper
    ): RecipeRepository =
        RecipesRepositoryImpl(api, db, apiMapper, domainMapper)

}