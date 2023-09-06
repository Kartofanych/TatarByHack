package com.inno.tatarbyhack.data.repositories

import android.util.Log
import com.inno.tatarbyhack.data.localSource.myCoursesSource.CourseEntity
import com.inno.tatarbyhack.data.localSource.myCoursesSource.MyCoursesDao
import com.inno.tatarbyhack.data.localSource.myCoursesSource.toCourse
import com.inno.tatarbyhack.domain.models.Course
import com.inno.tatarbyhack.domain.repository.MyCoursesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import java.util.UUID

class MyCoursesRepositoryImpl(
    private val dao: MyCoursesDao
) : MyCoursesRepository {


    override suspend fun createCourse(name: String) {
        val entity = CourseEntity(UUID.randomUUID().toString(), name, "","","Галиев Ирек", listOf())
        dao.add(entity)
    }

    override suspend fun getCourses(): Flow<List<Course>> = flow{
        dao.getAllFlow().collect{ list->
            emit(list.map { it.toCourse() })
        }
    }


}