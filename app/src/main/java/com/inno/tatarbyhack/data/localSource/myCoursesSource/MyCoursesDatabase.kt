package com.inno.tatarbyhack.data.localSource.myCoursesSource

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters

@Database(entities = [CourseEntity::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class MyCoursesDatabase : RoomDatabase() {
    abstract val listDao: MyCoursesDao
}