package com.inno.tatarbyhack.data.localSource.myCoursesSource

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.inno.tatarbyhack.domain.models.Module

@Entity(tableName = "myCoursesList")
data class MyCourseEntity(
    @PrimaryKey val id: String,
    val name: String,
    val description: String,
    val image: String,
    val authorName: String,
    val modules: List<Module>,
)

