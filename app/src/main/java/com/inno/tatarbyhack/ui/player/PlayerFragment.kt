package com.inno.tatarbyhack.ui.player

import android.content.Context
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.with
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.media3.ui.PlayerView
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.accompanist.systemuicontroller.SystemUiController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.inno.tatarbyhack.App
import com.inno.tatarbyhack.R
import com.inno.tatarbyhack.ui.theme.TatarByHackTheme
import com.inno.tatarbyhack.ui.theme.TatarTheme
import com.inno.tatarbyhack.ui.theme.medium
import com.inno.tatarbyhack.ui.theme.semibold
import com.inno.tatarbyhack.utils.viewModelFactory
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class PlayerFragment : Fragment() {

    override fun onAttach(context: Context) {
        super.onAttach(context)
        requireActivity().requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        hideSystemUI()

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    private val args:PlayerFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        // Inflate the layout for this fragment
        return ComposeView(requireContext()).apply {

            setContent {
                TatarByHackTheme {
                    val viewModel = viewModel<PlayerViewModel>(
                        factory = viewModelFactory {
                            PlayerViewModel(App.appModule.context)
                        }
                    )


                    val lifecycleOwner = LocalLifecycleOwner.current
                    var lifecycle by remember { mutableStateOf(Lifecycle.Event.ON_CREATE) }

                    val systemUiController = rememberSystemUiController()
                    //systemUiController.isStatusBarVisible = false
                    //systemUiController.isSystemBarsVisible = false

                    DisposableEffect(lifecycleOwner) {
                        val observer = LifecycleEventObserver { _, event ->
                            lifecycle = event
                        }
                        lifecycleOwner.lifecycle.addObserver(observer)


                        onDispose {
                            lifecycleOwner.lifecycle.removeObserver(observer)
                        }
                    }

                    PlayerContent(
                        args.url,
                        args.title,
                        args.subtitle,
                        //"https://sample-videos.com/video123/mp4/720/big_buck_bunny_720p_30mb.mp4",
                        //"https://download.samplelib.com/mp4/sample-30s.mp4",
                        lifecycle,
                        viewModel
                    ) {
                        findNavController().popBackStack()
                    }
                }
            }
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
    }

    override fun onDetach() {
        super.onDetach()
        requireActivity().requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        showSystemUI()
    }

    private fun hideSystemUI() {
        WindowCompat.setDecorFitsSystemWindows(requireActivity().window, false)
        WindowInsetsControllerCompat(requireActivity().window, requireActivity().findViewById(R.id.main_host)).let { controller ->
            controller.hide(WindowInsetsCompat.Type.systemBars())
            controller.systemBarsBehavior = WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        }
    }

    private fun showSystemUI() {
        WindowCompat.setDecorFitsSystemWindows(requireActivity().window, true)
        WindowInsetsControllerCompat(requireActivity().window, requireActivity().findViewById(R.id.main_host)).show(WindowInsetsCompat.Type.systemBars())
    }


}




@OptIn(ExperimentalAnimationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun PlayerContent(
    videoUrl: String,
    title: String,
    subTitle: String,
    lifecycle: Lifecycle.Event,
    viewModel: PlayerViewModel,
    popBack: () -> Boolean
) {


    val coroutineScope = rememberCoroutineScope()

    val videoLoading = remember { mutableStateOf(true) }

    var paused by remember { mutableStateOf(false) }

    var openMenu by remember { mutableStateOf(true) }
    var isVideoReady by remember { mutableStateOf(false) }

    val currentTime = remember { mutableStateOf(viewModel.exoPlayer.currentPosition) }


    LaunchedEffect(key1 = Unit) {
        viewModel.playVideo(videoUrl, {
            isVideoReady = true
            openMenu = false

        },
            { isLoading ->
                if (videoLoading.value != isLoading) {
                    videoLoading.value = isLoading
                    if (videoLoading.value) {
                        if (!paused) {
                            viewModel.exoPlayer.pause()
                        }
                    } else {
                        if (!paused) {
                            viewModel.exoPlayer.play()
                        }
                    }
                }
            },
            { isPlaying ->
                if (!paused) {
                    videoLoading.value = !isPlaying
                }
            }
        )

    }

    LaunchedEffect(Unit) {
        coroutineScope.launch {
            while (true) {
                delay(150)
                if (viewModel.exoPlayer.isPlaying && isVideoReady) {
                    currentTime.value = viewModel.exoPlayer.currentPosition
                }
            }
        }
    }


    val animatedTimeButtonsSize by animateDpAsState(targetValue = if (openMenu) 50.dp else 0.dp)
    val animatedPlayButtonSize by animateDpAsState(targetValue = if (openMenu) 50.dp else 0.dp)
    val animatedBottomRowSize by animateDpAsState(targetValue = if (openMenu) 50.dp else 0.dp)
    val animatedBlackColor by animateColorAsState(targetValue = if (openMenu) Color(0x5D000000) else Color.Transparent)


    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        AndroidView(
            factory = { context ->
                PlayerView(context).apply {
                    player = viewModel.exoPlayer
                    useController = false
                }
            },
            update = {
                when (lifecycle) {
                    Lifecycle.Event.ON_PAUSE -> {
                        it.onPause()
                        it.player?.pause()
                        paused = true
                        Log.d("121212", lifecycle.name)
                    }

                    Lifecycle.Event.ON_RESUME -> {
                        it.onResume()
                        paused = false
                        Log.d("121212", lifecycle.name)
                    }

                    Lifecycle.Event.ON_DESTROY -> {
                        it.player?.release()
                        Log.d("121212", lifecycle.name)
                    }


                    else -> {
                        Log.d("121212", lifecycle.name)
                    }
                }
            },
            modifier = Modifier
                .fillMaxSize()
                .clickable {
                    if (!videoLoading.value) {
                        openMenu = !openMenu
                    }
                }
        )

        if (openMenu) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(animatedBlackColor)
            )


            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(90.dp)
            ) {
                Icon(
                    modifier = Modifier
                        .align(Alignment.CenterStart)
                        .size(40.dp)
                        .clickable {
                            popBack()
                        },
                    painter = painterResource(id = R.drawable.ic_arr_back),
                    contentDescription = null,
                    tint = TatarTheme.colors.colorWhite,

                    )
                Column(
                    modifier = Modifier
                        .align(Alignment.Center),
                    verticalArrangement = Arrangement.SpaceAround,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        modifier = Modifier,
                        text = title,
                        fontFamily = semibold,
                        fontSize = 22.sp,
                        color = TatarTheme.colors.colorWhite
                    )
                    Text(
                        modifier = Modifier
                            .padding(top = 5.dp),
                        text = subTitle,
                        fontFamily = medium,
                        fontSize = 13.sp,
                        color = TatarTheme.colors.colorWhite
                    )
                }
            }
        }


        if (videoLoading.value) {
            CircularProgressIndicator(
                modifier = Modifier
                    .align(Alignment.Center)
                    .size(50.dp)
            )
        } else {

            Row(
                modifier = Modifier
                    .align(Alignment.Center)
                    .fillMaxWidth()
                    .padding(horizontal = 50.dp),
                horizontalArrangement = Arrangement.SpaceEvenly,
            ) {
                Icon(
                    modifier = Modifier
                        .padding(5.dp)
                        .size(animatedTimeButtonsSize)
                        .clip(RoundedCornerShape(25.dp))
                        .clickable {
                            viewModel.exoPlayer.seekTo(viewModel.exoPlayer.currentPosition - 5000)
                        },
                    painter = painterResource(id = R.drawable.ic_time_back),
                    contentDescription = "time back",
                    tint = TatarTheme.colors.colorWhite
                )

                AnimatedContent(
                    paused,
                    transitionSpec = {
                        scaleIn(animationSpec = tween(durationMillis = 250)) with
                                scaleOut(animationSpec = tween(durationMillis = 250))
                    }, label = ""
                ) { target ->
                    Icon(
                        modifier = Modifier
                            .padding(10.dp)
                            .size(animatedPlayButtonSize)
                            .clip(RoundedCornerShape(25.dp))
                            .padding(if (paused) 5.dp else 0.dp)
                            .clickable {
                                if (paused) {
                                    paused = false
                                    viewModel.exoPlayer.play()
                                    openMenu = false
                                } else {
                                    paused = true
                                    viewModel.exoPlayer.pause()
                                }
                            },
                        painter = painterResource(id = if (target) R.drawable.ic_play else R.drawable.ic_pause),
                        contentDescription = "time back",
                        tint = TatarTheme.colors.colorWhite
                    )
                }

                Icon(
                    modifier = Modifier
                        .padding(5.dp)
                        .size(animatedTimeButtonsSize)
                        .clip(RoundedCornerShape(25.dp))
                        .clickable {
                            viewModel.exoPlayer.seekTo(viewModel.exoPlayer.currentPosition + 5000)
                        },
                    painter = painterResource(id = R.drawable.ic_time_forward),
                    contentDescription = "time back",
                    tint = TatarTheme.colors.colorWhite

                )
            }
        }

        if (isVideoReady) {
            AnimatedVisibility(
                openMenu,
                enter = scaleIn(animationSpec = tween(durationMillis = 250)),
                exit = scaleOut(animationSpec = tween(durationMillis = 250)),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .align(Alignment.BottomStart)
            ) {
                Box(
                    modifier = Modifier,
                ) {
                    Slider(
                        value = currentTime.value.toFloat(),
                        valueRange = 0f..Math.max(viewModel.exoPlayer.duration.toFloat(), 1f),
                        onValueChange = {
                            currentTime.value = it.toLong()
                            viewModel.exoPlayer.seekTo(it.toLong())
                        },
                        modifier = Modifier
                            .padding(end = 150.dp)
                            .height(50.dp)
                            .fillMaxWidth()
                            .align(Alignment.CenterStart),
                        colors = SliderDefaults.colors(
                            activeTrackColor = TatarTheme.colors.backPrimary,
                            inactiveTrackColor = Color(0x33FFFFFF),
                            thumbColor = TatarTheme.colors.backSecondary
                        ),
                        interactionSource = MutableInteractionSource(),
                        thumb = {
                            SliderDefaults.Thumb(
                                modifier = Modifier
                                    .padding(5.dp),
                                interactionSource = MutableInteractionSource(),
                                thumbSize = DpSize(17.dp, 17.dp),
                                colors = SliderDefaults.colors(
                                    thumbColor = TatarTheme.colors.backSecondary
                                ),
                            )
                        },
                        track = remember(SliderDefaults.colors(), true) {
                            { sliderPositions ->
                                SliderDefaults.Track(
                                    colors = SliderDefaults.colors(),
                                    enabled = true,
                                    sliderPositions = sliderPositions,
                                    modifier = Modifier
                                        .padding(5.dp),
                                )
                            }
                        }
                    )
                    Text(
                        modifier = Modifier
                            .padding(top = 10.dp)
                            .width(140.dp)
                            .height(30.dp)
                            .align(Alignment.CenterEnd),
                        text = currentTime.value.toTimeString() +
                                " / " + viewModel.exoPlayer.duration.toTimeString(),
                        fontFamily = medium,
                        fontSize = 12.sp,
                        color = TatarTheme.colors.colorWhite,
                    )

                }

            }
        }
    }
}


fun Long.toTimeString(): String {
    val milliseconds = this
    val seconds = (milliseconds / 1000) % 60
    val minutes = ((milliseconds / 1000) / 60) % 60
    val hours = ((milliseconds / 1000) / 60) / 60

    var result = ""

    if (hours != 0L) {
        result += "0$hours:"
    }
    result += if (minutes < 10L) {
        "0$minutes:"
    } else {
        "$minutes:"
    }

    result += if (seconds < 10L) {
        "0$seconds"
    } else {
        "$seconds"
    }

    return result

}