package com.inno.tatarbyhack.data.remoteSource.dto

import com.inno.tatarbyhack.data.remoteSource.entities.VebinarDto

data class GetVebinarsResponse(
    val vebinars: List<VebinarDto>,
)