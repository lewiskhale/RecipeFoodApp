package com.skl.foodrecipeapp.common.data.datasource.cache.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "userId")
data class CachedUserId (

    @PrimaryKey(autoGenerate = false) val userId: Int

        ){
}