package com.inno.tatarbyhack.domain.repository

import com.inno.tatarbyhack.data.localSource.myCoursesSource.CourseEntity
import com.inno.tatarbyhack.domain.models.Course
import kotlinx.coroutines.flow.Flow

interface MyCoursesRepository {

    suspend fun createCourse(name: String)
    suspend fun getCourse(id : String): Flow<Course>
    fun deleteCourse(id: String)
    suspend fun changeCourseImage(uri: String, id:String)
    suspend fun getCourses(): Flow<List<Course>>
    suspend fun updateCourseDesc(id: String, desc: String)

}