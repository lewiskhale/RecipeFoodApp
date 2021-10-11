package com.skl.foodrecipeapp.common.data.datasource.api.model

import com.google.gson.annotations.SerializedName

data class ApiRandomResponse(
    @SerializedName(value = "recipes") val results: List<ApiRecipe>?
)
