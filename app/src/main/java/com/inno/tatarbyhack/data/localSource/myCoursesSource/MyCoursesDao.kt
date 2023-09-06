package com.inno.tatarbyhack.data.localSource.myCoursesSource

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
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

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun add(course: CourseEntity)
//
//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    suspend fun addList(newItems: List<ToDoItemEntity>)
//
//    @Update
//    suspend fun updateItem(toDoItemEntity: ToDoItemEntity)
//
//    @Delete
//    suspend fun delete(entity: ToDoItemEntity)
//
//    @Query("DELETE FROM todoList")
//    suspend fun deleteAll()
//
//    @Query("UPDATE todolist SET done= :done, changedAt=:time WHERE id = :id")
//    suspend fun updateDone(id: String, done: Boolean, time: Long)
}