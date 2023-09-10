package com.inno.tatarbyhack.data.remoteSource.entities

import com.google.gson.annotations.SerializedName
import com.inno.tatarbyhack.domain.models.Course
import com.inno.tatarbyhack.domain.models.Lesson
import com.inno.tatarbyhack.domain.models.Module
import com.inno.tatarbyhack.domain.models.Task

data class CourseDto(
    @SerializedName("id") val id: String,
    @SerializedName("name") val courseName: String,
    @SerializedName("module_names") val modules: List<ModuleDto>,
    @SerializedName("photo_url") val photoUrl: String,
    @SerializedName("lessons_counter") val lessonsCounter: Int,
    @SerializedName("description") val description: String,
    @SerializedName("author") val author: AuthorDto,
)

fun CourseDto.toCourse(): Course {
    return Course(
        id = id,
        courseName = courseName,
        photoLink = photoUrl,
        lessonsCounter = lessonsCounter,
        desc = description,
        authorName = author.name,
        authorImage = author.photoUrl,
        modules = modules.map { moduleDto -> Module(moduleDto.id,moduleDto.name, moduleDto.lessons.map { Lesson(it.id, it.name, it.videoLink, it.text, it.tasks.map { Task("hah","") }, false) }) }
    )
}