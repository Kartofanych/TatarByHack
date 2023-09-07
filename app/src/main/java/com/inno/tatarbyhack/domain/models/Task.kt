package com.inno.tatarbyhack.domain.models

data class Task(
    val isTest: Boolean,
    val text: String,
    val answerOptions: List<String>,
    val answer: String
)
