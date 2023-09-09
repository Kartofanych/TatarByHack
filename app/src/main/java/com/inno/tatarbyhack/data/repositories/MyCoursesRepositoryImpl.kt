package com.inno.tatarbyhack.data.repositories

import com.inno.tatarbyhack.data.localSource.myCoursesSource.MyCourseEntity
import com.inno.tatarbyhack.data.localSource.myCoursesSource.MyCoursesDao
import com.inno.tatarbyhack.data.localSource.myCoursesSource.toCourse
import com.inno.tatarbyhack.domain.models.Course
import com.inno.tatarbyhack.domain.models.Module
import com.inno.tatarbyhack.domain.repository.MyCoursesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.util.UUID

class MyCoursesRepositoryImpl(
    private val dao: MyCoursesDao
) : MyCoursesRepository {


    override suspend fun createCourse(name: String) {
        val entity = MyCourseEntity(UUID.randomUUID().toString(), name, "","","Галиев Ирек", listOf())
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

    override suspend fun updateCourseModules(id: String, newModule: String) {
        val list = dao.getItem(id).modules.toMutableList()
        list.add(Module(UUID.randomUUID().toString(), newModule, listOf()))
        dao.updateCourseModule(list, id)
    }

    override suspend fun updateCourseModules(id: String, i1:Int, i2:Int) {
        val list = dao.getItem(id).modules.toMutableList()
        val temp = list[i1]
        list[i1] = list[i2]
        list[i2] = temp
        dao.updateCourseModule(list, id)
    }


}