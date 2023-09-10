package com.inno.tatarbyhack.data.remoteSource.dto

import com.google.gson.annotations.SerializedName

data class GetUserApiRequest(

    @SerializedName("email")
    val login: String,

    @SerializedName("password")
    val password: String
)