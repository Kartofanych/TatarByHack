package com.inno.tatarbyhack.ui.player

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer

class PlayerViewModel(context: Context) : ViewModel() {

    val exoPlayer = ExoPlayer.Builder(context).build()
    private var count = 0
    init {
        exoPlayer.prepare()
    }

    fun playVideo(
        url: String,
        onRender: () -> Unit,
        onLoadingChanged: (Boolean) -> Unit,
        onPlayingChanged: (Boolean) -> Unit,
    ) {
        if (count == 1) {
            exoPlayer.setMediaItem(
                MediaItem.fromUri(url)
            )
            exoPlayer.playWhenReady = true
            exoPlayer.addListener(
                MyListener(
                    onRender, onLoadingChanged, onPlayingChanged
                )
            )
            Log.d("121212", "added listener")
        }
        count++
    }

    override fun onCleared() {
        super.onCleared()
        exoPlayer.release()
    }


}

class MyListener(
    private val onRender: () -> Unit,
    private val onLoadChanged: (Boolean) -> Unit,
    private val onPlayingChanged: (Boolean) -> Unit,

    ) : Player.Listener {
    override fun onRenderedFirstFrame() {
        super.onRenderedFirstFrame()
        onRender()
    }

    override fun onIsLoadingChanged(isLoading: Boolean) {
        super.onIsLoadingChanged(isLoading)
        onLoadChanged(isLoading)
    }

    override fun onIsPlayingChanged(isPlaying: Boolean) {
        super.onIsPlayingChanged(isPlaying)
        onPlayingChanged(isPlaying)

    }
}