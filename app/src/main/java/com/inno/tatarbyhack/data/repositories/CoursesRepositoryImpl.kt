package com.inno.tatarbyhack.data.repositories

import android.util.Log
import com.inno.tatarbyhack.data.localSource.cousesSource.PopularCoursesDao
import com.inno.tatarbyhack.data.localSource.cousesSource.toCourse
import com.inno.tatarbyhack.data.localSource.cousesSource.toPopularCoursesEntity
import com.inno.tatarbyhack.data.remoteSource.api.RetrofitService
import com.inno.tatarbyhack.data.remoteSource.entities.toCourse
import com.inno.tatarbyhack.domain.models.Course
import com.inno.tatarbyhack.domain.repository.CoursesRepository
import com.inno.tatarbyhack.utils.SharedPreferencesHelper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class CoursesRepositoryImpl(
    private val dao: PopularCoursesDao,
    private val service: RetrofitService
) : CoursesRepository {


    override fun getCourse(id: String): Course {
        return dao.getCourse(id).toCourse()
    }

    override suspend fun searchWithPrefix(prefix: String): List<Course> {
        try {
            val result =
                service.searchCourse("Bearer ${SharedPreferencesHelper.getToken()}", prefix)
            dao.addList(result.map { it.toPopularCoursesEntity() })
            return result.map { it.toCourse() }
        } catch (exception: Exception) {
        }
        return listOf()
    }


    override suspend fun getPopularCoursesFlow(): Flow<List<Course>> = flow {
        dao.getAllFlow().collect { list ->
            emit(list.map { it.toCourse() })
        }
    }

    override suspend fun getPopularCourses(): List<Course> {
        return dao.getAll().map { it.toCourse() }
    }

    override suspend fun increaseWatches(id: String) {
        try {
            service.addViewToCourse(
                "Bearer ${SharedPreferencesHelper.getToken()}",
                id = id
            )
        } catch (exception: Exception) {
        }
    }

    override suspend fun loadPopularCourses(): List<Course>? {
        try {
            val result = service.getPopularCourses()
            dao.addList(result.map { it.toPopularCoursesEntity() })
            return result.map { it.toCourse() }
        } catch (exception: Exception) {

        }
        return null
    }

    override suspend fun getMatching(userAnswer: String, rightAnswer: String):String {
        try {
            val res = service.matchingAnswer(rightAnswer, userAnswer).matching
            return res
        } catch (exception: Exception) {
        }
        return "0.59"
    }
}