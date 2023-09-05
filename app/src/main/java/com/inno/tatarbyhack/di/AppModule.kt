package com.inno.tatarbyhack.di

import android.content.Context
import androidx.media3.exoplayer.ExoPlayer
import com.inno.tatarbyhack.data.RepositoryImpl
import com.inno.tatarbyhack.domain.repository.Repository


interface AppModule {
    val repository: Repository
    val context: Context
}

class AppModuleImpl(
    private val appContext: Context
) : AppModule {
    override val repository: Repository
        get() = RepositoryImpl()

    override val context: Context
        get() = appContext


}