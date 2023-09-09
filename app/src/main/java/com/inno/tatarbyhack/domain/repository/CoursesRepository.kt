package com.inno.tatarbyhack.domain.repository

import android.net.IpPrefix
import com.inno.tatarbyhack.domain.models.Course
import kotlinx.coroutines.flow.Flow

interface CoursesRepository {

    fun getCourse(id:String): Course

    suspend fun searchWithPrefix(prefix: String):List<Course>
    suspend fun getPopularCourses(): Flow<List<Course>>
    suspend fun getRecommendedCourses(): Flow<List<Course>>
    suspend fun increaseWatches(id: String)
    suspend fun loadPopularCourses(): List<Course>?
}