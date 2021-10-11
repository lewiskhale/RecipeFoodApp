package com.skl.foodrecipeapp.common.data.datasource.cache.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "remote_keys")
data class RemoteKey(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val prevKey: Int?,
    val nextKey: Int?
)
