package com.inno.tatarbyhack.domain.models

data class User(
    val name: String,
    val login: String,
    val photoUrl: String,
    val role: Role,
    val listHomework: List<Lesson>
)
