package com.skl.foodrecipeapp.common.data.datasource.cache.model

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "recipe")
data class CacheRecipe(

    @PrimaryKey(autoGenerate = false)
    val id: Int,

    val title: String,
    val summary: String,
    val image: String,
    val servings: Int,
    val readyInMinutes: Int,
    val instructions: String,
    val likes: Int,
    val score: Double,
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

    val detailedInstructions: List<CacheInstruction>,
    val detailedIngredients: List<CacheIngredient>,


    ){
    data class CacheIngredient(
        val name: String,
        val originalName: String,
        val originalString: String,
        @Embedded val measures: CacheMeasures
    ){
        data class CacheMeasures(

            //Metric
            val metric_amount: Double,
            val metric_unitLong: String,

            //Imperial
            val imperial_amount: Double,
            val imperial_unitLong: String,
        )
    }

    data class CacheInstruction(
        val number: Int,
        val equipment: List<CacheEquipment>,
        val instruction: String
    ){
        data class CacheEquipment(
            val localizedName: String,
            val name: String
        )
    }

//    data class CacheSteps(
//        val steps: List<CacheInstruction>
//    ){
//    }

}


