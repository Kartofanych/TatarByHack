package com.inno.tatarbyhack.data.localSource.vebinarSource

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.inno.tatarbyhack.data.localSource.myCoursesSource.Converters

@Database(entities = [VebinarEntity::class], version = 3, exportSchema = false)
@TypeConverters(Converters::class)
abstract class VebinarsDatabase : RoomDatabase() {
    abstract val listDao: VebinarsDao
}