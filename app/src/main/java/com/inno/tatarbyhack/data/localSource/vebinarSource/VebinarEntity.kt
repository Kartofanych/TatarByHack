package com.inno.tatarbyhack.data.localSource.vebinarSource

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.inno.tatarbyhack.data.remoteSource.entities.VebinarDto
import com.inno.tatarbyhack.domain.models.Vebinar

@Entity(tableName = "vebinarsList")
data class VebinarEntity(
    @PrimaryKey val id: String,
    val name: String,
    val time: String,
    val previewImageUrl: String,
    val videoUrl: String?,
    val authorName: String,
)

fun VebinarEntity.toVebinar() : Vebinar{
    return Vebinar(
        name = name,
        time = time.toLong(),
        previewImageUrl = previewImageUrl,
        videoUrl = videoUrl?:"",
        authorName = authorName
    )
}