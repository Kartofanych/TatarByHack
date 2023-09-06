package com.inno.tatarbyhack.domain.repository

import com.inno.tatarbyhack.data.localSource.myCoursesSource.CourseEntity
import com.inno.tatarbyhack.domain.models.Course
import kotlinx.coroutines.flow.Flow

interface MyCoursesRepository {

    suspend fun createCourse(name: String)
    suspend fun getCourses(): Flow<List<Course>>

}