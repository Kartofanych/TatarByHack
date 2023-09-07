package com.inno.tatarbyhack.data.repositories

import com.inno.tatarbyhack.data.localSource.myCoursesSource.CourseEntity
import com.inno.tatarbyhack.data.localSource.myCoursesSource.MyCoursesDao
import com.inno.tatarbyhack.data.localSource.myCoursesSource.toCourse
import com.inno.tatarbyhack.domain.models.Course
import com.inno.tatarbyhack.domain.repository.MyCoursesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.util.UUID

class MyCoursesRepositoryImpl(
    private val dao: MyCoursesDao
) : MyCoursesRepository {


    override suspend fun createCourse(name: String) {
        val entity = CourseEntity(UUID.randomUUID().toString(), name, "","","Галиев Ирек", listOf())
        dao.add(entity)
    }

    override suspend fun getCourse(id: String): Flow<Course>  = flow {
        dao.getItemFlow(id).collect{
            if(it != null) {
                emit(it.toCourse())
            }
        }
    }

    override fun deleteCourse(id: String) {
        dao.delete(id)
    }

    override suspend fun changeCourseImage(uri: String, id:String) {
        dao.updateImage(uri, id)
    }

    override suspend fun getCourses(): Flow<List<Course>> = flow{
        dao.getAllFlow().collect{ list->
            emit(list.map { it.toCourse() })
        }
    }

    override suspend fun updateCourseDesc(id: String, desc: String) {
        dao.updateCourseItem(dao.getItem(id).copy(description = desc))
    }


}