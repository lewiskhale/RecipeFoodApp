package com.skl.foodrecipeapp.common.data.datasource.api.mapper

interface ApiMapper<ApiModel, CacheModel> {

    fun toCacheModel(apiModel: ApiModel): CacheModel
}