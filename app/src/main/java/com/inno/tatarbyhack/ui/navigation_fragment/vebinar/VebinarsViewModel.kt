package com.inno.tatarbyhack.ui.navigation_fragment.vebinar

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.inno.tatarbyhack.domain.models.Vebinar
import com.inno.tatarbyhack.domain.repository.VebinarsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.launch

class VebinarsViewModel(
    private val repository: VebinarsRepository
) : ViewModel() {

    private val futureVebinarsFlow = MutableStateFlow(listOf<Vebinar>())
    val futureVebinars = futureVebinarsFlow.asStateFlow()

    private val pastVebinarsFlow = MutableStateFlow(listOf<Vebinar>())
    val pastVebinars = pastVebinarsFlow.asStateFlow()

    init {
        start()

        viewModelScope.launch(Dispatchers.IO) {
            launch { repository.deleteAll() }.join()
            repository.getRemoteFutureVebinars()
            repository.getRemotePastVebinars()
        }
    }

    fun start() {
        getFutureVebinars()
        getPastVebinars()
    }

    private fun getFutureVebinars() {
        viewModelScope.launch(Dispatchers.IO) {
            futureVebinarsFlow.emitAll(repository.getFutureVebinars())
        }
    }

    private fun getPastVebinars() {
        viewModelScope.launch(Dispatchers.IO) {
            pastVebinarsFlow.emitAll(repository.getPastVebinars())
        }
    }

}