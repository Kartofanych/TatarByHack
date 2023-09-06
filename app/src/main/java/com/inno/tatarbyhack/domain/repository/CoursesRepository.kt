package com.inno.tatarbyhack.domain.repository

import com.inno.tatarbyhack.domain.models.Course

interface CoursesRepository {
    fun getAllCourses(): List<Course>
    suspend fun getLocalPopular(): List<Course>
    suspend fun getPopularCourses()
    suspend fun getLocalRecommended(): Array<List<Course>>
    suspend fun getRecommendedCourses()
}