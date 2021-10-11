package com.skl.foodrecipeapp.common.data.repository

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.skl.foodrecipeapp.common.data.datasource.api.mapper.ApiMapper
import com.skl.foodrecipeapp.common.data.datasource.api.mapper.ApiRecipeMapper
import com.skl.foodrecipeapp.common.data.datasource.api.model.ApiRecipe
import com.skl.foodrecipeapp.common.data.datasource.api.webServices.RecipeNetworkDataSource
import com.skl.foodrecipeapp.common.data.datasource.cache.RecipeAppDatabase
import com.skl.foodrecipeapp.common.data.repository.utils.getTag
import com.skl.foodrecipeapp.common.data.repository.utils.listToString
import com.skl.foodrecipeapp.common.domain.mapper.RecipeMapper
import com.skl.foodrecipeapp.common.domain.model.Recipe
import com.skl.foodrecipeapp.common.domain.repository.RecipeRepository
import com.skl.foodrecipeapp.common.paging.RecipeRemoteMediator
import kotlinx.coroutines.flow.*
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

const val TAG = "App Debug"

class RecipesRepositoryImpl @Inject constructor(
    private val api: RecipeNetworkDataSource,
    private val db: RecipeAppDatabase,
    private val apiMapper: ApiRecipeMapper,
    private val domainMapper: RecipeMapper
): RecipeRepository {

    private val recipeDao = db.recipeDao

    override suspend fun getRecipe(id: Int): Recipe {
        TODO("Not yet implemented")
    }

    @ExperimentalPagingApi
    override suspend fun getRecipes(
        cuisine: String,
        diet: List<String>,
        intolerance: List<String>
    ): Flow<List<Recipe>> {
        val tags = getTag(cuisine, diet, intolerance)
        Log.d(TAG, "getRecipes: the tag is: $tags")
        try {
            val call = api.getRandomRecipes(tags = tags)
            Log.d(TAG, "getRecipes: Made the api call ")
            if(call.isSuccessful){
                call.body()?.let {
                    Log.d(TAG, "getRecipes: null check: ${call.body()!!.results == null}")
                    it.results?.let { api_recipes ->
                        Log.d(TAG, "getRecipes: the api call was successful")
                        val result = apiMapper.mapList(api_recipes)
                        recipeDao.deleteAllRecipes()
                        Log.d(TAG, "getRecipes: inserting the recipes in db")
                        recipeDao.insertRecipes(result)
                        Log.d(TAG, "getRecipes: recipes have been inserted,now returning data")
                        return recipeDao.getRecipes().map { cache_recipe ->
                            domainMapper.mapListToDomain(cache_recipe)
                        }
                    }
                }
            }
        }
        catch(e: Exception){
            Log.d(TAG, "getRecipes: there was an error: $e")
            throw e
        }
        return flowOf()
    }

    override suspend fun searchRecipes(
        query: String,
        _cuisine: List<String>,
        _diet: List<String>,
        _intolerance: List<String>
    ): Flow<List<Recipe>> {
        val cuisine = listToString(_cuisine)
        val diet = listToString(_diet)
        val intolerance = listToString(_intolerance)
        //TODO
        return flowOf()
    }
}

//        val pagingSourceFactory = {db.recipeDao.getRecipes()}
//        return Pager(
//            config = PagingConfig(
//                pageSize = 5,
//                maxSize = 5 * (5 * 2),
//                enablePlaceholders = false
//            ),
//            remoteMediator = RecipeRemoteMediator(
//                isSearched = false,
//                query = "",
//                db = db,
//                webservice = api,
//                cuisine = cuisine,
//                diet = diet,
//                intolerance = intolerance,
//                apiMapper = apiMapper,
//                domainMapper = domainMapper
//            ),
//            pagingSourceFactory = pagingSourceFactory
//        ).flow()