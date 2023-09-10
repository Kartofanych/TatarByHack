package com.inno.tatarbyhack.data.remoteSource.entities

import com.google.gson.annotations.SerializedName

data class LessonDto(
    @SerializedName("id") val id: String,
    @SerializedName("video_link") val videoLink: String,
    @SerializedName("name") val name: String,
    @SerializedName("tasks_content") val tasks: List<TaskDto>,
    @SerializedName("text") val text: String,
)