package com.skl.foodrecipeapp.common.data.datasource.api.mapper

import com.skl.foodrecipeapp.common.data.datasource.api.model.ApiRecipe
import com.skl.foodrecipeapp.common.data.datasource.api.model.ApiRecipe.ApiAnalyzedInstruction.ApiStep.*
import com.skl.foodrecipeapp.common.data.datasource.api.model.ApiRecipe.ApiExtendedIngredient
import com.skl.foodrecipeapp.common.data.datasource.api.model.ApiRecipe.ApiExtendedIngredient.*
import com.skl.foodrecipeapp.common.data.datasource.cache.model.CacheRecipe
import com.skl.foodrecipeapp.common.data.datasource.cache.model.CacheRecipe.*
import com.skl.foodrecipeapp.common.data.datasource.cache.model.CacheRecipe.CacheIngredient.*
import com.skl.foodrecipeapp.common.data.datasource.cache.model.CacheRecipe.CacheInstruction.*
import javax.inject.Inject

class ApiRecipeMapper @Inject constructor(): ApiMapper<ApiRecipe, CacheRecipe> {

    override fun toCacheModel(apiModel: ApiRecipe): CacheRecipe {
        apiModel.apply {
            return CacheRecipe(
                id = id ?: throw Exception("The recipe doesn't exist"),
                title = title.orEmpty(),
                summary = summary.orEmpty(),
                image = image.orEmpty(),
                servings = servings ?: -1,
                readyInMinutes = readyInMinutes ?: -1,
                instructions = instructions.orEmpty(),
                likes = likes ?: 0,
                score = score ?: -1.0,
                isCheap = isCheap ?: false,
                detailedIngredients = parseIngredients(detailedIngredients),
                detailedInstructions = parseInstruction(detailedInstructions),
                sourceName = sourceName.orEmpty(),
                sourceUrl = sourceUrl.orEmpty(),
                license = license.orEmpty(),
                creditsText = creditsText.orEmpty(),
                isVegan = isVegan ?: false,
                isDairyFree = isDairyFree ?: false,
                isVegetarian = isVegetarian ?: false,
                isGlutenFree = isGlutenFree ?: false,
                cuisines = cuisines ?: emptyList(),
                diets = diets ?: emptyList(),
                dishTypes = dishTypes ?: emptyList(),
                isSaved = false,
                liked = false
            )
        }
    }

    fun mapList(recipes: List<ApiRecipe>): List<CacheRecipe>{
        return recipes.map { toCacheModel(it) }
    }

    private fun parseInstruction
                (detailedInstructions: List<ApiRecipe.ApiAnalyzedInstruction>?)
    : List<CacheInstruction> {
        detailedInstructions?.map { instruction ->
                return instruction.steps?.map { step -> parseSteps(step) }?: emptyList()
        }
        return emptyList()
    }

    private fun parseSteps(step: ApiRecipe.ApiAnalyzedInstruction.ApiStep)=
        CacheInstruction(
            number = step.number ?: -1,
            instruction = step.instruction.orEmpty(),
            equipment = parseEquipmentList(step.equipment)
        )


    private fun parseEquipmentList(equipment: List<ApiEquipment>?): List<CacheEquipment> {
        return equipment?.map { parseItem(it) } ?: emptyList()
    }

    private fun parseItem(it: ApiEquipment) =
        CacheEquipment(
            localizedName = it.localizedName.orEmpty(),
            name = it.name.orEmpty()
        )


    private fun parseIngredients
                (detailedIngredients: List<ApiExtendedIngredient>?)
    : List<CacheIngredient> {
        return detailedIngredients?.map { parseIngredient(it) } ?: emptyList()
    }

    private fun parseIngredient(ingredient: ApiExtendedIngredient): CacheIngredient {
        ingredient.apply {
            return CacheIngredient(
                name = name.orEmpty(),
                originalName = originalName.orEmpty(),
                originalString = originalString.orEmpty(),
                measures = parseMeasure(measures)
            )
        }
    }

    private fun parseMeasure
                (measures: ApiMeasures?)
    : CacheMeasures {
        return CacheMeasures(
            metric_amount = measures?.metric?.amount ?: -1.0,
            metric_unitLong = measures?.metric?.unitLong.orEmpty(),
            imperial_amount = measures?.imperial?.amount ?: -1.0,
            imperial_unitLong = measures?.imperial?.unitLong.orEmpty()
        )
    }
}