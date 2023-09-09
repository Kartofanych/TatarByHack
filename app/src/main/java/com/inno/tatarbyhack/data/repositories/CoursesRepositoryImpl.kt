package com.inno.tatarbyhack.data.repositories

import android.util.Log
import com.inno.tatarbyhack.data.localSource.cousesSource.PopularCoursesDao
import com.inno.tatarbyhack.data.localSource.cousesSource.toCourse
import com.inno.tatarbyhack.data.localSource.cousesSource.toPopularCoursesEntity
import com.inno.tatarbyhack.data.remoteSource.api.RetrofitService
import com.inno.tatarbyhack.data.remoteSource.entities.toCourse
import com.inno.tatarbyhack.domain.models.Course
import com.inno.tatarbyhack.domain.models.Lesson
import com.inno.tatarbyhack.domain.models.Module
import com.inno.tatarbyhack.domain.repository.CoursesRepository
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
        val result = service.searchCourse(prefix)
        return result.map { it.toCourse() }
    }


    override suspend fun getPopularCourses(): Flow<List<Course>> = flow {
        dao.getAllFlow().collect { list ->
            emit(list.map { it.toCourse() })
        }
    }


    override suspend fun getRecommendedCourses(): Flow<List<Course>> = flow {

    }

    override suspend fun increaseWatches(id: String) {
        try {
            service.addViewToCourse(id = id)
        } catch (exception: Exception) {

        }
    }

    override suspend fun loadPopularCourses() : List<Course>? {
        try {
            val result = service.getPopularCourses()
            dao.addList(result.map { it.toPopularCoursesEntity() })
            Log.d("121212", result.size.toString())
            return result.map { it.toCourse() }
        } catch (exception: Exception) {
            Log.d("121212", exception.toString())
        }
        Log.d("121212", "zero")
        return null
    }
}