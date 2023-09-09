package com.inno.tatarbyhack.ui.lesson

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.inno.tatarbyhack.domain.models.Course
import com.inno.tatarbyhack.domain.repository.CoursesRepository
import com.inno.tatarbyhack.domain.repository.MyCoursesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class LessonViewModel(
    private val coursesRepository: CoursesRepository,
    private val id:String
) : ViewModel() {
    private val courseFlow = MutableStateFlow(Course())
    val myCourse = courseFlow.asStateFlow()

    init {
        getCourse()
    }

    private fun getCourse() {
        viewModelScope.launch(Dispatchers.IO) {
            courseFlow.emit(coursesRepository.getCourse(id))
        }
    }
}