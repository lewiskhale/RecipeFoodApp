package com.skl.foodrecipeapp.common.domain.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Recipe(

    val id: Int,
    val title: String,
    val summary: String,
    val image: String,
    val servings: Int,
    val readyInMinutes: Int,
    val instructions: String,
    val likes: Int,
    val score: Double,

    //might delete
    val isCheap: Boolean,
    val isSaved: Boolean,
    val liked: Boolean,

    //Tags
    val cuisines: List<String>,
    val diets: List<String>,
    val dishTypes: List<String>,

    //Licenses and Sources
    val sourceName: String,
    val sourceUrl: String,
    val license: String,
    val creditsText: String,

    //Dietary Restrictions
    val isVegan: Boolean,
    val isDairyFree: Boolean,
    val isVegetarian: Boolean,
    val isGlutenFree: Boolean,


    val detailedInstructions: List<Instruction>,
    val detailedIngredients: List<Ingredient>,

    ): Parcelable {

    @Parcelize
    data class Instruction(
        val number: Int,
        val equipment: List<Equipment>,
        val instruction: String
    ): Parcelable{
        @Parcelize
        data class Equipment(
            val localizedName: String,
            val name: String
        ):Parcelable
    }

    @Parcelize
    data class Ingredient(
        val name: String,
        val originalName: String,
        val originalString: String,
        val measures: Measures
    ):Parcelable{
        @Parcelize
        data class Measures(

            //Metric
            val metric_amount: Double,
            val metric_unitLong: String,

            //Imperial
            val imperial_amount: Double,
            val imperial_unitLong: String,
        ):Parcelable
    }
}