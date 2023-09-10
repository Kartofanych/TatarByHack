package com.inno.tatarbyhack.ui.navigation_fragment.account

import androidx.compose.runtime.MutableState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.inno.tatarbyhack.domain.models.Role
import com.inno.tatarbyhack.domain.models.User
import com.inno.tatarbyhack.domain.repository.LoginRepository
import com.inno.tatarbyhack.utils.SharedPreferencesHelper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AccountViewModel(
    private val repository:LoginRepository
):ViewModel() {

    private val userFlow = MutableStateFlow(SharedPreferencesHelper.getUser())
    val user = userFlow.asStateFlow()
    fun getUserInfo(){
        viewModelScope.launch(Dispatchers.IO){
            userFlow.emit(repository.getUser()?:SharedPreferencesHelper.getUser())
        }
    }
}