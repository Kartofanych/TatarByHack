package com.inno.tatarbyhack.data.remoteSource.api

import com.inno.tatarbyhack.data.remoteSource.dto.GetUserApiRequest
import com.inno.tatarbyhack.data.remoteSource.dto.MatchingDto
import com.inno.tatarbyhack.data.remoteSource.entities.CourseDto
import com.inno.tatarbyhack.data.remoteSource.entities.LessonDto
import com.inno.tatarbyhack.data.remoteSource.entities.LoginResponse
import com.inno.tatarbyhack.data.remoteSource.entities.ModuleDto
import com.inno.tatarbyhack.data.remoteSource.entities.UserDto
import com.inno.tatarbyhack.data.remoteSource.entities.VebinarDto
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Path

interface RetrofitService {
    @GET("get-user/")
    suspend fun getUser(
        @Header("Authorization") token: String,
    ): UserDto

    @GET("get-past-webinars/")
    suspend fun getPastVebinars(): List<VebinarDto>

    @GET("get-upcoming-webinars/")
    suspend fun getUpcomingVebinars(): List<VebinarDto>


    @GET("get-popular-courses")
    suspend fun getPopularCourses(): List<CourseDto>


    @GET("add-view-to-course/{course_id}")
    suspend fun addViewToCourse(
        @Header("Authorization") token: String,
        @Path("course_id") id: String,
    )

    @GET("get-modules/{course_id}")
    suspend fun getModules(
        @Path("course_id") id: String,
    ): List<ModuleDto>

    @GET("search/{keyword}")
    suspend fun searchCourse(
        @Header("Authorization") token: String,
        @Path("keyword") keyWord: String,
    ): List<CourseDto>


    @POST("login-user/")
    suspend fun loginUser(
        @Body getUserApiRequest: GetUserApiRequest,
    ): LoginResponse

    @GET("login-user/{lesson_id}")
    suspend fun getLesson(
        @Path("lesson_id") lessonId: String,
    ): LessonDto

    @GET("get-matching/{right_answer}/{given_answer}")
    suspend fun matchingAnswer(
        @Path("right_answer") answer: String,
        @Path("given_answer") userAnswer: String,
    ): MatchingDto
}