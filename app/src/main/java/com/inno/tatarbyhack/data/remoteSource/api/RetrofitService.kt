package com.inno.tatarbyhack.data.remoteSource.api

import com.inno.tatarbyhack.data.remoteSource.dto.GetUserApiResponse
import com.inno.tatarbyhack.data.remoteSource.entities.CourseDto
import com.inno.tatarbyhack.data.remoteSource.entities.ModuleDto
import com.inno.tatarbyhack.data.remoteSource.entities.VebinarDto
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Path

interface RetrofitService {
    @GET("list")
    suspend fun getUser(
        @Body id: String,
    ): GetUserApiResponse

    @GET("get-past-webinars/")
    suspend fun getPastVebinars(): List<VebinarDto>

    @GET("get-upcoming-webinars/")
    suspend fun getUpcomingVebinars(): List<VebinarDto>


    @GET("get-popular-courses")
    suspend fun getPopularCourses(): List<CourseDto>


    @GET("add-view-to-course/{course_id}")
    suspend fun addViewToCourse(
        @Path("course_id") id: String,
    )

    @GET("get-modules/{course_id}")
    suspend fun getModules(
        @Path("course_id") id: String,
    ): List<ModuleDto>

    @GET("search/{keyword}")
    suspend fun searchCourse(
        @Path("keyword") keyWord: String,
    ): List<CourseDto>
}