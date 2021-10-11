package com.skl.foodrecipeapp.common.data.datasource.api.webServices

import com.skl.foodrecipeapp.BuildConfig.API_KEY
import com.skl.foodrecipeapp.common.data.datasource.api.ApiConstants.GET_RECIPE_ENDPOINT
import com.skl.foodrecipeapp.common.data.datasource.api.ApiConstants.RANDOM_ENDPOINT
import com.skl.foodrecipeapp.common.data.datasource.api.ApiConstants.SEARCH_ENDPOINT
import com.skl.foodrecipeapp.common.data.datasource.api.ApiConstants.SIMILAR_RECIPES_ENDPOINT
import com.skl.foodrecipeapp.common.data.datasource.api.model.ApiRandomResponse
import com.skl.foodrecipeapp.common.data.datasource.api.model.ApiRecipe
import com.skl.foodrecipeapp.common.data.datasource.api.model.ApiSearchResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RecipeNetworkDataSource {

    //random
    @GET(RANDOM_ENDPOINT)
    suspend fun getRandomRecipes(
        @Query("apiKey") apiKey: String? = API_KEY,
        @Query("number") number: Int = 5,
        @Query("tags") tags: String = ""
    ): Response<ApiRandomResponse>

    //search
    @GET(SEARCH_ENDPOINT)
    suspend fun searchRecipe(
        @Query("apiKey") apiKey: String? = API_KEY,
        @Query("query") query: String = "",
        @Query("cuisine") cuisine: String = "",
        @Query("diet") diet: String = "",
        @Query("intolerance") intolerance: String = "",
        @Query("number") number: Int = 3
    ): Response<ApiSearchResponse>

    //Similar recipes
    @GET(SIMILAR_RECIPES_ENDPOINT)
    suspend fun getSimilarRecipes(
        @Query("apiKey") apiKey: String? = API_KEY,
        @Path("id") id: Int,
        @Query("number") number: Int = 3
    )

    //get specific recipe
    @GET(GET_RECIPE_ENDPOINT)
    suspend fun getRecipe(
        @Query("apiKey") apiKey: String? = API_KEY,
        @Path("id") id: Int
    ):Response<ApiRecipe>
}