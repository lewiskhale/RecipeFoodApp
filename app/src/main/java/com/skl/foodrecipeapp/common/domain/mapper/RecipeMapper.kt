package com.skl.foodrecipeapp.common.domain.mapper

import com.skl.foodrecipeapp.common.data.datasource.cache.model.CacheRecipe
import com.skl.foodrecipeapp.common.domain.model.Recipe
import javax.inject.Inject

class RecipeMapper @Inject constructor(
    private val instructionMapper: InstructionMapper,
    private val ingredientMapper: IngredientsMapper
) : Mapper<CacheRecipe, Recipe> {

    override fun toDomain(cacheModel: CacheRecipe): Recipe {
        cacheModel.apply {
            return Recipe(
                id,
                title,
                summary,
                image,
                servings,
                readyInMinutes,
                instructions,
                likes,
                score,
                isCheap,
                isSaved,
                liked,
                cuisines,
                diets,
                dishTypes,
                sourceName,
                sourceUrl,
                license,
                creditsText,
                isVegan,
                isDairyFree,
                isVegetarian,
                isGlutenFree,
                detailedIngredients = detailedIngredients.map { ingredientMapper.toDomain(it) },
                detailedInstructions = detailedInstructions.map { instructionMapper.toDomain(it) }
            )
        }
    }

    fun mapListToDomain(list: List<CacheRecipe>) =
        list.map { toDomain(it) }


    override fun fromDomain(domainModel: Recipe): CacheRecipe {
        domainModel.apply {
            return CacheRecipe(
                id,
                title,
                summary,
                image,
                servings,
                readyInMinutes,
                instructions,
                likes,
                score,
                isCheap,
                isSaved,
                liked,
                cuisines,
                diets,
                dishTypes,
                sourceName,
                sourceUrl,
                license,
                creditsText,
                isVegan,
                isDairyFree,
                isVegetarian,
                isGlutenFree,
                detailedIngredients = detailedIngredients.map { ingredientMapper.fromDomain(it) },
                detailedInstructions = detailedInstructions.map { instructionMapper.fromDomain(it) }
            )
        }
    }
}