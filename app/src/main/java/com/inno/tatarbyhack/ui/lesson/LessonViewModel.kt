package com.inno.tatarbyhack.ui.lesson

import android.util.Log
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



    private val matchingFlow = MutableStateFlow("")
    val matching = matchingFlow.asStateFlow()


    init {
        getCourse()
    }

    private fun getCourse() {
        viewModelScope.launch(Dispatchers.IO) {
            courseFlow.emit(coursesRepository.getCourse(id))
        }
    }

    fun getMatching(userAnswer:String, rightAnswer:String){
        matchingFlow.value = "-1f"
        viewModelScope.launch(Dispatchers.IO) {
            matchingFlow.emit(coursesRepository.getMatching(userAnswer, rightAnswer))
            Log.d("121212",matching.value.toString() )
        }
    }

}