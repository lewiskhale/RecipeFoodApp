package com.skl.foodrecipeapp.common.data.datasource.api.model

data class ApiSearchResponse(

    val offset:Int?,
    val number:Int?,
    val totalResults: Int?,
    val results: List<ApiRecipe>?
)
