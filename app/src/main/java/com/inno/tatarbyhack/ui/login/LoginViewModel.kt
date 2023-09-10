package com.inno.tatarbyhack.ui.login

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.inno.tatarbyhack.domain.repository.LoginRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class LoginViewModel(
    private val loginRepository: LoginRepository
) : ViewModel() {

    private val loginStateFlow = MutableStateFlow(0)
    val loginState = loginStateFlow.asStateFlow()

    fun login(login: String, password: String) {
        viewModelScope.launch(Dispatchers.IO) {
            loginStateFlow.emit(1)
            Log.d("121212", "loading")
            if(loginRepository.login(login, password)){
                loginStateFlow.emit(2)
                Log.d("121212", "logged")
            }else{
                loginStateFlow.emit(0)
                Log.d("121212", "err")
            }
        }
    }
}