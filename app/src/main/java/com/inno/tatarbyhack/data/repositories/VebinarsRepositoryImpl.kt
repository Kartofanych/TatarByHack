package com.inno.tatarbyhack.data.repositories

import android.util.Log
import com.inno.tatarbyhack.data.localSource.vebinarSource.VebinarsDao
import com.inno.tatarbyhack.data.localSource.vebinarSource.toVebinar
import com.inno.tatarbyhack.data.remoteSource.api.RetrofitService
import com.inno.tatarbyhack.data.remoteSource.entities.toVebinarEntity
import com.inno.tatarbyhack.domain.models.Vebinar
import com.inno.tatarbyhack.domain.repository.VebinarsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class VebinarsRepositoryImpl(
    private val service: RetrofitService,
    private val dao: VebinarsDao
) : VebinarsRepository {


    override fun getFutureVebinars(): Flow<List<Vebinar>> = flow {
        dao.getAllFlow().collect { list ->
            emit(list.filter { it.videoUrl.isNullOrEmpty() }.map { it.toVebinar() })
        }
    }

    override fun getPastVebinars(): Flow<List<Vebinar>> = flow {
        dao.getAllFlow().collect { list ->
            emit(list.filter { !it.videoUrl.isNullOrEmpty() }.map { it.toVebinar() })
        }
    }


    override suspend fun getRemoteFutureVebinars() {
        try {
            val result = service.getUpcomingVebinars()
            dao.addList(result.map { it.toVebinarEntity() })
        } catch (exception: Exception) {
            Log.d("121212", exception.toString())
            //getRemoteFutureVebinars()
        }
        //add flag
    }

    override suspend fun getRemotePastVebinars() {
        try {
            val result = service.getPastVebinars()
            dao.addList(result.map { it.toVebinarEntity() })
        } catch (exception: Exception) {
            Log.d("121212", exception.toString())

        }
        //add flag
    }

    override suspend fun deleteAll() {
        dao.deleteAll()
    }


}