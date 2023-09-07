package com.inno.tatarbyhack.data.localSource.myCoursesSource

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface MyCoursesDao {

    @Query("SELECT * FROM myCoursesList")
    fun getAllFlow(): Flow<List<CourseEntity>>
    @Query("SELECT * FROM myCoursesList")
    fun getAll(): List<CourseEntity>
//

    @Query("SELECT * FROM myCoursesList WHERE id=:itemId")
    fun getItem(itemId: String): CourseEntity
    @Query("SELECT * FROM myCoursesList WHERE id=:itemId")
    fun getItemFlow(itemId: String): Flow<CourseEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun add(course: CourseEntity)
//
//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    suspend fun addList(newItems: List<ToDoItemEntity>)
//
    @Update
    suspend fun updateCourseItem(course: CourseEntity)

    @Query("DELETE FROM myCoursesList WHERE id = :id")
    fun delete(id: String)
//
//    @Query("DELETE FROM todoList")
//    suspend fun deleteAll()
//
    @Query("UPDATE myCoursesList SET image= :uri WHERE id = :id")
    suspend fun updateImage(uri: String, id: String)
}