package com.inno.tatarbyhack.data.localSource.cousesSource

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.inno.tatarbyhack.data.localSource.myCoursesSource.Converters

@Database(entities = [PopularCoursesEntity::class], version = 2, exportSchema = false)
@TypeConverters(Converters::class)
abstract class PopularCoursesDatabase : RoomDatabase() {
    abstract val listDao: PopularCoursesDao
}