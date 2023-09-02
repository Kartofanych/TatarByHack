package com.inno.tatarbyhack.di

import android.content.Context
import androidx.media3.exoplayer.ExoPlayer


interface AppModule {
    val player: ExoPlayer
}

class AppModuleImpl(
    private val appContext: Context
):AppModule {
    override val player: ExoPlayer
        get() = ExoPlayer.Builder(appContext).build()


}