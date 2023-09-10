package com.inno.tatarbyhack.di

import android.content.Context
import androidx.room.Room
import com.inno.tatarbyhack.data.localSource.cousesSource.PopularCoursesDao
import com.inno.tatarbyhack.data.localSource.cousesSource.PopularCoursesDatabase
import com.inno.tatarbyhack.data.localSource.myCoursesSource.MyCoursesDao
import com.inno.tatarbyhack.data.localSource.myCoursesSource.MyCoursesDatabase
import com.inno.tatarbyhack.data.localSource.vebinarSource.VebinarsDao
import com.inno.tatarbyhack.data.localSource.vebinarSource.VebinarsDatabase
import com.inno.tatarbyhack.data.remoteSource.api.RetrofitService
import com.inno.tatarbyhack.data.repositories.CoursesRepositoryImpl
import com.inno.tatarbyhack.data.repositories.LoginRepositoryImpl
import com.inno.tatarbyhack.data.repositories.MyCoursesRepositoryImpl
import com.inno.tatarbyhack.data.repositories.VebinarsRepositoryImpl
import com.inno.tatarbyhack.domain.repository.CoursesRepository
import com.inno.tatarbyhack.domain.repository.LoginRepository
import com.inno.tatarbyhack.domain.repository.MyCoursesRepository
import com.inno.tatarbyhack.domain.repository.VebinarsRepository
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


interface AppModule {
    val context: Context
    val coursesRepository: CoursesRepository
    val myCoursesRepository: MyCoursesRepository
    val vebinarsRepository: VebinarsRepository
    val loginRepository: LoginRepository
    val myCoursesRoom: MyCoursesDatabase
    val myCoursesDao: MyCoursesDao
    val popularRoom: PopularCoursesDatabase
    val popularDao: PopularCoursesDao
    val vebinarsRoom: VebinarsDatabase
    val vebinarsDao: VebinarsDao
}

class AppModuleImpl(
    private val appContext: Context
) : AppModule {
    override val context: Context
        get() = appContext
    override val coursesRepository: CoursesRepository
        get() = CoursesRepositoryImpl(popularDao, service)

    override val vebinarsRoom = Room.databaseBuilder(
        context,
        VebinarsDatabase::class.java,
        "vebinarsList"
    ).fallbackToDestructiveMigration().build()
    override val vebinarsDao: VebinarsDao
        get() = vebinarsRoom.listDao


    override val myCoursesRoom = Room.databaseBuilder(
        context,
        MyCoursesDatabase::class.java,
        "myCoursesList"
    ).fallbackToDestructiveMigration().build()


    override val myCoursesDao = myCoursesRoom.listDao
    override val popularRoom: PopularCoursesDatabase
        get() = Room.databaseBuilder(
            context,
            PopularCoursesDatabase::class.java,
            "popularCoursesList"
        ).fallbackToDestructiveMigration().build()
    override val popularDao: PopularCoursesDao
        get() = popularRoom.listDao

    private val interceptor = HttpLoggingInterceptor().apply {
        setLevel(HttpLoggingInterceptor.Level.BODY)
    }
    private val client =
        OkHttpClient.Builder().connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .retryOnConnectionFailure(true)
            .addInterceptor(interceptor).build()
    private val retrofit: Retrofit
        get() = Retrofit.Builder()
            .client(client)
            .baseUrl("http://bishplus.ru/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    private val service = retrofit.create(RetrofitService::class.java)
    override val myCoursesRepository: MyCoursesRepository
        get() = MyCoursesRepositoryImpl(myCoursesDao)
    override val vebinarsRepository: VebinarsRepository
        get() = VebinarsRepositoryImpl(service, vebinarsDao)
    override val loginRepository: LoginRepository
        get() = LoginRepositoryImpl(service)


}