package com.inno.tatarbyhack.ui.navigation_fragment.courses

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.inno.tatarbyhack.domain.models.Course
import com.inno.tatarbyhack.domain.repository.CoursesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.launch

class CoursesViewModel(
    private val coursesRepository: CoursesRepository
) : ViewModel() {
    private val popularCoursesFlow = MutableStateFlow(listOf<Course>())
    val popularCourses = popularCoursesFlow.asStateFlow()

    private val recommendedCoursesFlow = MutableStateFlow(arrayOf(listOf<Course>()))
    val recommendedCourses = recommendedCoursesFlow.asStateFlow()

    private val searchCoursesFlow = MutableStateFlow(listOf<Course>())
    val searchCourses = searchCoursesFlow.asStateFlow()


    fun start() {
        fetchPopularCourses()
    }


    private fun fetchPopularCourses() {
        viewModelScope.launch(Dispatchers.IO) {
            coursesRepository.loadPopularCourses()?.let { popularCoursesFlow.emit(it) }
        }
    }


    private lateinit var findWork:Job
    fun findWithPrefix(prefix: String) {
        if(findWork!=null){
            findWork.cancel()
        }
        if(prefix.isEmpty()) {
            viewModelScope.launch(Dispatchers.IO) {
                searchCoursesFlow.emit(popularCourses.value)
            }
        }
        findWork = viewModelScope.launch(Dispatchers.IO) {
            searchCoursesFlow.emit(coursesRepository.searchWithPrefix(prefix))
        }
    }


    private fun getRecommendedCourses() {
        viewModelScope.launch(Dispatchers.IO) {
            //recommendedCoursesFlow.emit(coursesRepository.getLocalRecommended())
        }
    }
}