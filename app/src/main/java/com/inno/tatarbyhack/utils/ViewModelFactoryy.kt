package com.inno.tatarbyhack.utils

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.inno.tatarbyhack.domain.repository.CoursesRepository

class ViewModelFactory (
    private val coursesRepository: CoursesRepository
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val viewModel = when (modelClass) {


            else -> {
                error("Unknown view model class")
            }
        }
        return viewModel as T
    }

}