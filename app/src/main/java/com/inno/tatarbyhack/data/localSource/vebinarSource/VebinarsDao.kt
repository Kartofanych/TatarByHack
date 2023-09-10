package com.inno.tatarbyhack.data.localSource.vebinarSource

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface VebinarsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addList(list: List<VebinarEntity>)
    @Query("SELECT * FROM vebinarsList")
    fun getAllFlow(): Flow<List<VebinarEntity>>

    @Query("DELETE FROM vebinarsList")
    fun deleteAll()

}