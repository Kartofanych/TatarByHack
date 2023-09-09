package com.inno.tatarbyhack.utils

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import com.google.gson.Gson
import com.inno.tatarbyhack.App
import com.inno.tatarbyhack.domain.models.Role
import com.inno.tatarbyhack.domain.models.User

object SharedPreferencesHelper {

    private val sharedPreferences: SharedPreferences
    private val editor: SharedPreferences.Editor

    private val gson = Gson()

    var a = 0
    init {
        val context = App.appModule.context
        sharedPreferences = context.getSharedPreferences("states", Context.MODE_PRIVATE)
        editor = sharedPreferences.edit()

        if(!sharedPreferences.contains("user")){
            editor.putString("user", gson.toJson(User("Насыбуллин Карим", "some login", Role.Student, listOf()), User::class.java))
            editor.apply()
        }

    }

    fun getUser(): User {
        return gson.fromJson(sharedPreferences.getString("user", "user"), User::class.java)
    }

}