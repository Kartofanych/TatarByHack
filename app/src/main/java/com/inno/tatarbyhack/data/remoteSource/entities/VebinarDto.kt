package com.inno.tatarbyhack.data.remoteSource.entities

import com.google.gson.annotations.SerializedName
import com.inno.tatarbyhack.data.localSource.vebinarSource.VebinarEntity
import com.inno.tatarbyhack.domain.models.Vebinar

data class VebinarDto(
    @SerializedName("id") val id:String,
    @SerializedName("name") val name:String,
    @SerializedName("time_starts") val time: String,
    @SerializedName("preview_image_url") val previewImageUrl: String,
    @SerializedName("video_url") val videoUrl: String,
    @SerializedName("author_name") val authorName: String
)

fun VebinarDto.toVebinar():Vebinar{
    return Vebinar(
        name = name,
        time = time.toLong(),
        previewImageUrl = previewImageUrl,
        videoUrl = videoUrl,
        authorName = authorName
    )
}
fun VebinarDto.toVebinarEntity():VebinarEntity{
    return VebinarEntity(
        id = id,
        name = name,
        time = time,
        previewImageUrl = previewImageUrl,
        videoUrl = videoUrl.ifEmpty { null },
        authorName = authorName
    )
}