package com.inno.tatarbyhack.data.repositories

import android.util.Log
import com.inno.tatarbyhack.data.remoteSource.api.RetrofitService
import com.inno.tatarbyhack.data.remoteSource.dto.GetUserApiRequest
import com.inno.tatarbyhack.domain.models.Role
import com.inno.tatarbyhack.domain.models.User
import com.inno.tatarbyhack.domain.repository.LoginRepository
import com.inno.tatarbyhack.utils.SharedPreferencesHelper


class LoginRepositoryImpl(
    private val service: RetrofitService
) : LoginRepository {
    override suspend fun login(login: String, password: String): Boolean {
        return try {
            val result = service.loginUser(GetUserApiRequest(login, password))
            SharedPreferencesHelper.putToken(result.accessToken)
            true
        } catch (exception: Exception) {
            Log.d("121212", exception.toString())
            false
        }
    }

    override suspend fun getUser(): User? {
        return try {
            val result = service.getUser("Bearer ${SharedPreferencesHelper.getToken()}")
            User(result.name, "", result.photoUrl, Role.Student, listOf())
        } catch (exception: Exception) {
            Log.d("121212", exception.toString())
            null
        }
    }
}