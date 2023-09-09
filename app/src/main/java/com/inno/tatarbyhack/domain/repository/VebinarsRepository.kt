package com.inno.tatarbyhack.domain.repository

import com.inno.tatarbyhack.domain.models.Vebinar
import kotlinx.coroutines.flow.Flow

interface VebinarsRepository {

    fun getFutureVebinars(): Flow<List<Vebinar>>
    fun getPastVebinars(): Flow<List<Vebinar>>
    suspend fun getRemoteFutureVebinars()
    suspend fun getRemotePastVebinars()
    suspend fun deleteAll()
}