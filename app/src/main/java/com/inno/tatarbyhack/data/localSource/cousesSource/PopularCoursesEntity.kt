package com.inno.tatarbyhack.data.localSource.cousesSource

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import com.inno.tatarbyhack.data.remoteSource.entities.AuthorDto
import com.inno.tatarbyhack.data.remoteSource.entities.CourseDto
import com.inno.tatarbyhack.data.remoteSource.entities.ModuleDto
import com.inno.tatarbyhack.domain.models.Course
import com.inno.tatarbyhack.domain.models.Lesson
import com.inno.tatarbyhack.domain.models.Module
import com.inno.tatarbyhack.domain.models.Task

@Entity(tableName = "popularCoursesList")
data class PopularCoursesEntity(
    @PrimaryKey val id: String,
    val courseName: String,
    val description: String,
    val lessonsCounter: Int,
    val photoUrl: String,
    val modules: List<ModuleDto>,
    val authorName: AuthorDto,
)

fun CourseDto.toPopularCoursesEntity(): PopularCoursesEntity {
    return PopularCoursesEntity(
        id = id,
        courseName = courseName,
        photoUrl = photoUrl,
        lessonsCounter = lessonsCounter,
        description = description,
        modules = modules,
        authorName = author,
    )
}

fun PopularCoursesEntity.toCourse(): Course {
    return Course(
        id = id,
        courseName = courseName,
        photoLink = photoUrl,
        lessonsCounter = lessonsCounter,
        desc = description,
        authorName = authorName.name,
        authorImage = authorName.photoUrl,
        modules = modules.map { moduleDto ->
            Module(moduleDto.id, moduleDto.name, moduleDto.lessons.map { lessonDto ->
                Lesson(
                    lessonDto.id,
                    lessonDto.name,
                    lessonDto.videoLink,
                    lessonDto.text,
                    lessonDto.tasks?.map {
                        Task(it.text, it.answer)
                    }?: listOf(),
                    false
                )
            })
        }
    )
}