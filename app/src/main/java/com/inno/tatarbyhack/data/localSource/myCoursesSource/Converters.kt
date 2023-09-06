package com.inno.tatarbyhack.data.localSource.myCoursesSource

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.inno.tatarbyhack.domain.models.Course
import com.inno.tatarbyhack.domain.models.Module

class Converters {
    @TypeConverter
    fun moduleListToJsonString(value: List<Module>): String = Gson().toJson(value)

    @TypeConverter
    fun jsonStringToModuleList(value: String) = Gson().fromJson(value, Array<Module>::class.java).toList()
}

fun CourseEntity.toCourse():Course{
    return Course(
        id = this.id,
        courseName = this.name,
        desc = description,
        authorName = authorName,
        modules = modules,
        photoLink = image
    )
}