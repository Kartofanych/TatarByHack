package com.inno.tatarbyhack.data.localSource.cousesSource

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.inno.tatarbyhack.domain.models.Course
import kotlinx.coroutines.flow.Flow

@Dao
interface PopularCoursesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addList(list: List<PopularCoursesEntity>)
    @Query("SELECT * FROM popularCoursesList")
    fun getAllFlow(): Flow<List<PopularCoursesEntity>>
    @Query("SELECT * FROM popularCoursesList")
    fun getAll(): List<PopularCoursesEntity>
    @Query("SELECT * FROM popularCoursesList WHERE id=:id")
    fun getCourse(id:String): PopularCoursesEntity
}