package com.skl.foodrecipeapp.common.domain.repository

import com.skl.foodrecipeapp.common.domain.model.Recipe
import kotlinx.coroutines.flow.Flow

interface RecipeRepository {

    suspend fun getRecipe(id: Int): Recipe

    suspend fun getRecipes(cuisine: String, diet: List<String>, intolerance: List<String>): Flow<List<Recipe>>

    suspend fun searchRecipes(query:String, cuisine: List<String>, diet: List<String>, intolerance: List<String>): Flow<List<Recipe>>

}