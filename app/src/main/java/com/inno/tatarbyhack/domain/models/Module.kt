package com.inno.tatarbyhack.domain.models

data class Module(
    val id: String,
    val name : String,
    val lessons: List<Lesson>
)
