package com.inno.tatarbyhack.domain.repository

import com.inno.tatarbyhack.domain.models.User


interface LoginRepository {
    suspend fun login(login: String, password: String): Boolean
    suspend fun getUser():User?
}