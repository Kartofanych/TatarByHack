package com.inno.tatarbyhack.data.remoteSource.entities

import com.google.gson.annotations.SerializedName
import com.inno.tatarbyhack.domain.models.Module

data class ModuleDto(
    @SerializedName("module_id") val id: String,
    @SerializedName("module_name") val name: String,
    @SerializedName("lessons") val lessons: List<LessonDto>
)

fun Module.toModuleDto(): ModuleDto {
    return ModuleDto(
        id = id,
        name = name,
        lessons = lessons.map { LessonDto(it.id, it.videoLink, it.name, it.tasks.map { TaskDto(it.text, it.answer) }, it.text) }
    )
}