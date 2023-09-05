package com.inno.tatarbyhack.domain.models

data class Course(
    val id:Int,
    val courseName:String,
    val photoLink:String,
    val desc: String,
    val authorName: String,
    val modules: List<Module>
)