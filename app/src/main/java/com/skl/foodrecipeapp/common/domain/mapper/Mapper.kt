package com.skl.foodrecipeapp.common.domain.mapper

interface Mapper<CacheModel, DomainModel> {

    fun toDomain(cacheModel: CacheModel): DomainModel

    fun fromDomain(domainModel: DomainModel): CacheModel

}