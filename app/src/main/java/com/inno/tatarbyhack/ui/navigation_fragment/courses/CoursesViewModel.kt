package com.inno.tatarbyhack.ui.navigation_fragment.courses

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.inno.tatarbyhack.domain.models.Course
import com.inno.tatarbyhack.domain.repository.CoursesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class CoursesViewModel(
    private val coursesRepository: CoursesRepository
) : ViewModel() {
    private val popularCoursesFlow = MutableStateFlow(listOf<Course>())
    val popularCourses = popularCoursesFlow.asStateFlow()

    private val recommendedCoursesFlow = MutableStateFlow(arrayOf(listOf<Course>()))
    val recommendedCourses = recommendedCoursesFlow.asStateFlow()


    private var allCourses = listOf<Course>()


    init {
        getPopularCourses()
        getRecommendedCourses()
        if (allCourses.isEmpty()) {
            getAllCourses()
        }
    }

    fun start() {

    }


    private fun getAllCourses() {
        viewModelScope.launch(Dispatchers.IO) {
            allCourses = coursesRepository.getAllCourses()
        }
    }


    fun findWithPrefix(prefix: String): List<Course> {
        if(prefix.isEmpty()) return listOf()
        return allCourses.filter {
            it.courseName.lowercase().contains(prefix.lowercase()) || it.authorName.lowercase().contains(prefix.lowercase()) }
    }


    private fun getPopularCourses() {
        viewModelScope.launch(Dispatchers.IO) {
            popularCoursesFlow.emit(coursesRepository.getLocalPopular())
        }
    }

    private fun getRecommendedCourses() {
        viewModelScope.launch(Dispatchers.IO) {
            recommendedCoursesFlow.emit(coursesRepository.getLocalRecommended())
        }
    }
}