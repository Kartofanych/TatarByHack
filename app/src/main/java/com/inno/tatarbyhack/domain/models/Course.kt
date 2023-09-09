package com.inno.tatarbyhack.domain.models

data class Course(
    val id:String,
    val courseName:String,
    val photoLink:String,
    val lessonsCounter:Int,
    val desc: String,
    val authorName: String,
    val authorImage: String,
    val modules: List<Module>,
){
    constructor(): this("", "", "",0, "","", "", listOf())
}