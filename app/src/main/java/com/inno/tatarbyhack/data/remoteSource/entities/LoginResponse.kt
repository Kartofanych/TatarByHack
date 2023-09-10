package com.inno.tatarbyhack.data.remoteSource.entities

import com.google.gson.annotations.SerializedName

data class LoginResponse(
    @SerializedName("access_token") val accessToken:String
)