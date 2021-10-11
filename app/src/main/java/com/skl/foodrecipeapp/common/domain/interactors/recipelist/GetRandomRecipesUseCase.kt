package com.skl.foodrecipeapp.common.domain.interactors.recipelist

import android.util.Log
import com.skl.foodrecipeapp.common.domain.model.Recipe
import com.skl.foodrecipeapp.common.domain.repository.RecipeRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

const val TAG = "App debug"

class GetRandomRecipes @Inject constructor(
    private val recipeRepository: RecipeRepository
) {

    suspend operator fun invoke(
        cuisine: String,
        diet: List<String>,
        intolerance: List<String>
    ): Flow<List<Recipe>> {
//        val string = DomainUtils().listToString(tags)
        Log.d(TAG, "invoke: calling recipeRepo.getRecipes")
        return recipeRepository.getRecipes(cuisine = cuisine, diet = diet, intolerance = intolerance)
    }
}