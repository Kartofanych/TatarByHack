package com.inno.tatarbyhack.domain.repository

import com.inno.tatarbyhack.domain.models.Course
import kotlinx.coroutines.flow.Flow

interface CoursesRepository {

    fun getCourse(id:String): Course
    suspend fun searchWithPrefix(prefix: String):List<Course>
    suspend fun getPopularCoursesFlow(): Flow<List<Course>>
    suspend fun getPopularCourses(): List<Course>
    suspend fun increaseWatches(id: String)
    suspend fun loadPopularCourses(): List<Course>?
    suspend fun getMatching(userAnswer:String, rightAnswer:String):String
}