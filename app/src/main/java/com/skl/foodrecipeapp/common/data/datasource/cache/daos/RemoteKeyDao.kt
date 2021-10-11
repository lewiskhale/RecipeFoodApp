package com.skl.foodrecipeapp.common.data.datasource.cache.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.skl.foodrecipeapp.common.data.datasource.cache.model.RemoteKey

@Dao
interface RemoteKeyDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrReplace(remoteKey: RemoteKey)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrReplace(remoteKeys: List<RemoteKey>)

    @Query("select * from remote_keys where id = :id")
    suspend fun remoteKeyByQuery(id: Int): RemoteKey

    @Query("delete from remote_keys where id = :id")
    suspend fun deleteKeyByQuery(id: Int)

    @Query("delete from remote_keys")
    suspend fun deleteKeys()
}