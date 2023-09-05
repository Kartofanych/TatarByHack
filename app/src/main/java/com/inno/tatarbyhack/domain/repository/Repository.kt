package com.inno.tatarbyhack.domain.repository

import com.inno.tatarbyhack.domain.models.Course

interface Repository {
    suspend fun getLocalPopular(): List<Course>
    suspend fun getLocalRecommended(): Array<List<Course>>
}