package com.skl.foodrecipeapp.common.data.datasource.api.model

import com.google.gson.annotations.SerializedName


data class ApiRecipe(
    val id: Int?,
    val title: String?,
    val summary: String?,
    val image: String?,
    val servings: Int?,
    val readyInMinutes: Int?,
    val instructions: String?,
    @SerializedName("analyzedInstructions") val detailedInstructions: List<ApiAnalyzedInstruction>?,
    @SerializedName("extendedIngredients") val detailedIngredients: List<ApiExtendedIngredient>?,
    @SerializedName("aggregateLikes") val likes: Int?,
    @SerializedName("spoonacularScore") val score: Double?,
    @SerializedName("cheap") val isCheap: Boolean?,

    //Tags
    val cuisines: List<String>?,
    val diets: List<String>?,
    val dishTypes: List<String>?,

    //Licenses and Sources
    val sourceName: String?,
    val sourceUrl: String?,
    val license: String?,
    val creditsText: String?,

    //Dietary Restrictions
    @SerializedName("vegan") val isVegan: Boolean?,
    @SerializedName("dairyFree") val isDairyFree: Boolean?,
    @SerializedName("vegetarian") val isVegetarian: Boolean?,
    @SerializedName("glutenFree") val isGlutenFree: Boolean?,

    ){


    data class ApiAnalyzedInstruction(
        val name: String?,
        val steps: List<ApiStep>?
    ){

        data class ApiStep(
            val number: Int?,
            val equipment: List<ApiEquipment>?,
            @SerializedName("step") val instruction: String?
        ){

            data class ApiEquipment(
                val localizedName: String?,
                val name: String?
            )
        }
    }


    data class ApiExtendedIngredient(
        val name: String?,
        val originalName: String?,
        val originalString: String?,
        val measures: ApiMeasures?
    ){
        data class ApiMeasures(
            val metric: ApiMetric?,
            @SerializedName("us") val imperial: ApiImperial?
        ){

            data class ApiImperial(
                val amount: Double?,
                val unitLong: String?,
                val unitShort: String?
            )

            data class ApiMetric(
                val amount: Double?,
                val unitLong: String?,
                val unitShort: String?
            )
        }
    }
}