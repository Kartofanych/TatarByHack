package com.inno.tatarbyhack.ui.navigation_fragment.constructor

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.inno.tatarbyhack.domain.models.Course
import com.inno.tatarbyhack.domain.repository.MyCoursesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class ConstructorViewModel(
    private val repository: MyCoursesRepository
) : ViewModel() {

    private val myCoursesFlow = MutableStateFlow(listOf<Course>())
    val myCourses = myCoursesFlow.asStateFlow()

    init {
        getCourses()
    }

    private fun getCourses() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getCourses().collect { list ->
                myCoursesFlow.emit(list)
            }
        }
    }

    fun createCourse(name: String) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.createCourse(name)
        }
    }


}