package com.skl.foodrecipeapp.common.data.datasource.cache.daos

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.skl.foodrecipeapp.common.data.datasource.cache.model.CacheRecipe
import kotlinx.coroutines.flow.Flow

@Dao
interface RecipeCacheDataSource {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRecipe(recipe: CacheRecipe)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRecipes(recipes: List<CacheRecipe>)

    @Query("select * from recipe")
    fun getRecipes(): Flow<List<CacheRecipe>>

    @Query("""
        select * from recipe
        where (title like '%' || :query || '%' or summary like '%' || :query || '%') 
        and (cuisines in (:cuisine)) and (diets in (:diet))
    """)
    fun getAllSearchedRecipes(
        query: String,
        cuisine: String,
        diet: String
    ): PagingSource<Int, CacheRecipe>

    @Query("delete from recipe")
    suspend fun deleteAllRecipes()

}