package com.inno.tatarbyhack.ui.navigation_fragment

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.inno.tatarbyhack.domain.models.Course
import com.inno.tatarbyhack.domain.repository.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class NavigationViewModel(
    private val repository: Repository
) : ViewModel() {

    //courses page
    private val popularCoursesFlow = MutableStateFlow(listOf<Course>())
    val popularCourses = popularCoursesFlow.asStateFlow()

    private val recommendedCoursesFlow = MutableStateFlow(listOf<Course>())
    val recommendedCourses = popularCoursesFlow.asStateFlow()


    init {
        Log.d("121212", "init")
        getPopularCourses()
        getRecommendedCourses()
    }

    fun start(){
        Log.d("121212", "start")
    }


    private fun getPopularCourses(){
        viewModelScope.launch(Dispatchers.IO) {
            popularCoursesFlow.emit(repository.getLocalPopular())
        }
    }

    private fun getRecommendedCourses(){
        viewModelScope.launch(Dispatchers.IO) {
            recommendedCoursesFlow.emit(repository.getLocalPopular())
        }
    }



}