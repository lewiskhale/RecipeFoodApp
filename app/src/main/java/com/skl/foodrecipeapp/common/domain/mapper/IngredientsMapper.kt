package com.skl.foodrecipeapp.common.domain.mapper

import com.skl.foodrecipeapp.common.data.datasource.cache.model.CacheRecipe.*
import com.skl.foodrecipeapp.common.domain.model.Recipe.*
import javax.inject.Inject

class IngredientsMapper @Inject constructor(): Mapper<CacheIngredient, Ingredient> {
    override fun toDomain(cacheModel: CacheIngredient): Ingredient =
        parseIngredient(cacheModel)


    private fun parseIngredient(ingredient: CacheIngredient): Ingredient {
        ingredient.apply {
            return Ingredient(
                name,
                originalName,
                originalString,
                parseMeasure(measures)
            )
        }
    }

    private fun parseMeasure(measures: CacheIngredient.CacheMeasures): Ingredient.Measures {
        measures.apply {
            return Ingredient.Measures(
                metric_amount,
                metric_unitLong,
                imperial_amount,
                imperial_unitLong
            )
        }
    }

    override fun fromDomain(domainModel: Ingredient): CacheIngredient =
        parseToCacheIngredient(domainModel)

    private fun parseToCacheIngredient(ingredient: Ingredient): CacheIngredient {
        ingredient.apply {
            return CacheIngredient(
                name,
                originalName,
                originalString,
                parseToCacheMeasure(measures)
            )
        }
    }

    private fun parseToCacheMeasure(measures: Ingredient.Measures): CacheIngredient.CacheMeasures {
        measures.apply {
            return CacheIngredient.CacheMeasures(
                metric_amount,
                metric_unitLong,
                imperial_amount,
                imperial_unitLong
            )
        }
    }
}