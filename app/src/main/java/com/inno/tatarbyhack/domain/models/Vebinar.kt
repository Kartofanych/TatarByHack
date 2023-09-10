package com.inno.tatarbyhack.domain.models

import com.google.common.math.LongMath

data class Vebinar(
    val name: String,
    val time: Long,
    val previewImageUrl: String,
    val videoUrl: String,
    val authorName: String
)