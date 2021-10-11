package com.skl.foodrecipeapp.common.data.datasource.cache.util

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.skl.foodrecipeapp.common.data.datasource.cache.model.CacheRecipe.*
import com.skl.foodrecipeapp.common.data.datasource.cache.model.CacheRecipe.CacheInstruction.*

class Converters {

    private val mapper = Gson()
//    @TypeConverter
//    fun fromStepsString(string: String): List<CachedSteps> {
//        val listType = object : TypeToken<CachedSteps>() {}.type
//        return mapper.fromJson(string, listType)
//    }
//
//    @TypeConverter
//    fun fromStepsList(list: List<CachedSteps>): String {
//        return mapper.toJson(list)
//    }


    @TypeConverter
    fun fromIngredientList(list: List<CacheIngredient>): String {
        return mapper.toJson(list)
    }

    @TypeConverter
    fun fromIngredientsString(string: String): List<CacheIngredient> {
        val listType = object : TypeToken<List<CacheIngredient>>() {}.type
        return mapper.fromJson(string, listType)
    }


    @TypeConverter
    fun fromInstructionList(list: List<CacheInstruction>): String {
        return mapper.toJson(list)
    }

    @TypeConverter
    fun fromInstructionString(string: String): List<CacheInstruction> {
        val listType = object : TypeToken<List<CacheInstruction>>() {}.type
        return mapper.fromJson(string, listType)
    }


    @TypeConverter
    fun fromEquipmentList(list: List<CacheEquipment>): String {
        return mapper.toJson(list)
    }

    @TypeConverter
    fun fromEquipmentString(string: String): List<CacheEquipment> {
        val listType = object : TypeToken<List<CacheEquipment>>() {}.type
        return mapper.fromJson(string, listType)
    }

    @TypeConverter
    fun fromList(list: List<String>): String {
        return mapper.toJson(list)
    }

    @TypeConverter
    fun toList(string: String): List<String> {
        val listType = object : TypeToken<List<String>>() {}.type
        return mapper.fromJson(string, listType)
    }


}