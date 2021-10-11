package com.skl.foodrecipeapp.common.domain.interactors.recipelist

import com.skl.foodrecipeapp.common.domain.model.Recipe
import com.skl.foodrecipeapp.common.domain.repository.RecipeRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SearchRecipesUseCase @Inject constructor(
    private val recipeRepository: RecipeRepository
) {
    suspend operator fun invoke(query: String,
                                cuisine: List<String>,
                                diet: List<String>,
                                intolerance: List<String>)
    : Flow<List<Recipe>> {
        return recipeRepository.searchRecipes(query,
            cuisine,
            diet,
            intolerance
        )
    }

}