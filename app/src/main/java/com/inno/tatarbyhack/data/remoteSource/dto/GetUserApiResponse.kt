package com.inno.tatarbyhack.data.remoteSource.dto

import com.google.gson.annotations.SerializedName
import com.inno.tatarbyhack.domain.models.User

data class GetUserApiResponse(
    @SerializedName("status")
    val status: String,

    @SerializedName("user")
    val user: User,
)