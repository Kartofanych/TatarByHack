package com.inno.tatarbyhack.data.localSource.myCoursesSource

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.inno.tatarbyhack.data.remoteSource.entities.AuthorDto
import com.inno.tatarbyhack.data.remoteSource.entities.ModuleDto
import com.inno.tatarbyhack.domain.models.Course
import com.inno.tatarbyhack.domain.models.Module

class Converters {
    @TypeConverter
    fun moduleListToJsonString(value: List<Module>): String = Gson().toJson(value)

    @TypeConverter
    fun jsonStringToModuleList(value: String) =
        Gson().fromJson(value, Array<Module>::class.java).toList()
    @TypeConverter
    fun moduleDtoListToJsonString(value: List<ModuleDto>): String = Gson().toJson(value)

    @TypeConverter
    fun jsonStringToModuleDtoList(value: String) =
        Gson().fromJson(value, Array<ModuleDto>::class.java).toList()

    @TypeConverter
    fun authorDtoListToJsonString(value: AuthorDto): String = Gson().toJson(value)

    @TypeConverter
    fun jsonStringToAuthorDtoList(value: String) =
        Gson().fromJson(value, AuthorDto::class.java)
}

fun MyCourseEntity.toCourse(): Course {
    return Course(
        id = this.id,
        courseName = this.name,
        desc = description,
        lessonsCounter = 0,
        authorName = authorName,
        modules = modules,
        photoLink = image,
        authorImage = image
    )
}