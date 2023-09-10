package com.inno.tatarbyhack.ui.navigation_fragment.courses

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.inno.tatarbyhack.domain.models.Course
import com.inno.tatarbyhack.domain.repository.CoursesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
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

    private var currentPrefix = MutableStateFlow("")

    init {
        searchFlow()
        fetchPopularCourses()
    }


    private fun fetchPopularCourses() {
        viewModelScope.launch(Dispatchers.IO) {
            coursesRepository.loadPopularCourses()?.let {
                if (it.size > 24) {
                    popularCoursesFlow.emit(it.subList(0, 9))
                    recommendedCoursesFlow.emit(
                        arrayOf(
                            it.subList(9, 14),
                            it.subList(14, 19),
                            it.subList(19, 24),
                        )
                    )
                }
            }
        }
    }

    private fun searchFlow() {
        viewModelScope.launch(Dispatchers.IO) {
            currentPrefix.collect { prefix ->
                val list = coursesRepository.getPopularCourses()
                searchCoursesFlow.emit(list.filter {
                    it.authorName.lowercase()
                        .contains(prefix.lowercase()) || it.courseName.lowercase()
                        .contains(prefix.lowercase())
                })
            }
        }
    }


    fun findWithPrefix(prefix: String) {
        Log.d("121212", prefix)
        Log.d("121212", currentPrefix.value)
        if(prefix!=currentPrefix.value) {
            if (prefix.isNotEmpty()) {
                viewModelScope.launch(Dispatchers.IO) {
                    currentPrefix.value = (prefix)
                    coursesRepository.searchWithPrefix(prefix)
                }
            }
        }
    }

}