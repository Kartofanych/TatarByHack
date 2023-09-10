package com.inno.tatarbyhack.data.remoteSource.entities

import com.google.gson.annotations.SerializedName

data class AuthorDto(
    @SerializedName("name") val name: String,
    @SerializedName("photo_url") val photoUrl: String
)
