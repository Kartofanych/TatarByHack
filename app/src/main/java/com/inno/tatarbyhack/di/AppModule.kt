package com.inno.tatarbyhack.di

import android.content.Context
import androidx.room.Room
import com.inno.tatarbyhack.data.localSource.myCoursesSource.MyCoursesDao
import com.inno.tatarbyhack.data.localSource.myCoursesSource.MyCoursesDatabase
import com.inno.tatarbyhack.data.repositories.CoursesRepositoryImpl
import com.inno.tatarbyhack.data.repositories.MyCoursesRepositoryImpl
import com.inno.tatarbyhack.domain.repository.CoursesRepository
import com.inno.tatarbyhack.domain.repository.MyCoursesRepository


interface AppModule {
    val context: Context
    val coursesRepository: CoursesRepository
    val myCoursesRepository: MyCoursesRepository
    val myCoursesRoom: MyCoursesDatabase
    val myCoursesDao: MyCoursesDao
}

class AppModuleImpl(
    private val appContext: Context
) : AppModule {
    override val context: Context
        get() = appContext
    override val coursesRepository: CoursesRepository
        get() = CoursesRepositoryImpl()

    override val myCoursesRoom = Room.databaseBuilder(
        context,
        MyCoursesDatabase::class.java,
        "myCoursesList"
    ).fallbackToDestructiveMigration().build()

    override val myCoursesDao = myCoursesRoom.listDao
    override val myCoursesRepository: MyCoursesRepository
        get() = MyCoursesRepositoryImpl(myCoursesDao)


}