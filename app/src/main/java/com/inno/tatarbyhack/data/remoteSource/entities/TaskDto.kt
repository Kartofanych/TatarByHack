package com.inno.tatarbyhack.data.remoteSource.entities

import com.google.gson.annotations.SerializedName

data class TaskDto(
    @SerializedName("question")
    val text: String,
    @SerializedName("answer")
    val answer: String
)
