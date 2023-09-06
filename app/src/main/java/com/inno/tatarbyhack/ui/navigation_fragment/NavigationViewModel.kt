package com.inno.tatarbyhack.ui.navigation_fragment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.inno.tatarbyhack.domain.repository.CoursesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NavigationViewModel(
    private val coursesRepository: CoursesRepository
) : ViewModel() {

    init {
        viewModelScope.launch(Dispatchers.IO) {
            coursesRepository.getRecommendedCourses()
            coursesRepository.getRecommendedCourses()
        }
    }


}