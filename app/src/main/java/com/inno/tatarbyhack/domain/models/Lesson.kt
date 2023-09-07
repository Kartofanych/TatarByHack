package com.inno.tatarbyhack.domain.models

data class Lesson(
    val name:String,
    val videoLink:String,
    val text:String,
    val tasks:List<Task>
)