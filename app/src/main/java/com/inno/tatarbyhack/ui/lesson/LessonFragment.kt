package com.inno.tatarbyhack.ui.lesson

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.with
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.inno.tatarbyhack.R
import com.inno.tatarbyhack.ui.theme.TatarByHackTheme
import com.inno.tatarbyhack.ui.theme.TatarTheme
import com.inno.tatarbyhack.ui.theme.semibold

class LessonFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    val args: LessonFragmentArgs by navArgs()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        // Inflate the layout for this fragment
        return ComposeView(requireContext()).apply {

            setContent {
                TatarByHackTheme {
                    val scaffoldState = rememberScaffoldState()
                    val scope = rememberCoroutineScope()
                    val drawerState = remember { mutableStateOf(false) }
                    val animatedDrawerSize by animateDpAsState(targetValue = if (!drawerState.value) 0.dp else 200.dp)

                    val lessonId = remember { mutableStateOf(0) }

                    Scaffold(
                        scaffoldState = scaffoldState,
                        modifier = Modifier.fillMaxSize(),
                        backgroundColor = MaterialTheme.colorScheme.background,
                    ) { paddingValues ->
                        Box(
                            modifier = Modifier.fillMaxSize()
                        ) {
                            LessonContent(paddingValues, lessonId = lessonId.value, drawerState.value, animatedDrawerSize) {
                                drawerState.value = !drawerState.value
                            }
                            Box(modifier = Modifier
                                .align(Alignment.CenterEnd)) {
                                DrawerContent(animatedDrawerSize, lessonId.value) { newId ->
                                    lessonId.value = newId
                                    drawerState.value = false
                                }
                            }
                        }
                    }
                }
            }
        }
    }


}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun LessonContent(
    paddingValues: PaddingValues,
    lessonId: Int,
    drawerState: Boolean,
    animatedDrawerSize: Dp,
    openDrawer: () -> Unit
) {

//    val localContext = LocalContext.current
//    val lifecycleOwner = LocalLifecycleOwner.current
//    var lifecycle by remember { mutableStateOf(Lifecycle.Event.ON_CREATE) }
//
//    var isVideoReady by remember { mutableStateOf(false) }
//
//
//    val exoPlayer = remember {
//        ExoPlayer.Builder(localContext).build().apply {
//            addListener(object : Player.Listener {
//                override fun onRenderedFirstFrame() {
//                    super.onRenderedFirstFrame()
//                    isVideoReady = true
//                }
//            })
//        }
//    }
//
//    LaunchedEffect(key1 = Unit){
//        exoPlayer.setMediaItem(
//            MediaItem.fromUri("some uri")
//        )
//        exoPlayer.prepare()
//        exoPlayer.repeatMode = Player.REPEAT_MODE_ONE
//    }


    Box(
        modifier = Modifier
            .fillMaxSize()

    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 24.dp)
        ) {

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 5.dp)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_arr_back),
                    contentDescription = null,
                    Modifier
                        .size(40.dp)
                        .clickable(
//                    interactionSource = MutableInteractionSource(),
//                    indication =  null
                        ) {
                        }
                        .padding(8.dp)
                )

                Text(
                    modifier = Modifier
                        .align(Alignment.Center),
                    text = "IT course, $lessonId",
                    fontFamily = semibold,
                    color = TatarTheme.colors.labelPrimary
                )
            }

            Column {
                Box(
                    modifier = Modifier
                        .padding(20.dp)
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(20.dp))
                        .aspectRatio(1.89f),
                ) {
                    Image(
                        modifier = Modifier
                            .fillMaxSize(),
                        painter = painterResource(id = R.drawable.ic_lesson_preview),
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                    )
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(
                                brush = Brush.verticalGradient(
                                    colors = listOf(
                                        Color.Transparent,
                                        Color.Transparent,
                                        TatarTheme.colors.labelPrimary
                                    )
                                )
                            )
                    )
                    Image(
                        modifier = Modifier
                            .align(Alignment.Center)
                            .size(50.dp)
                            .clip(RoundedCornerShape(25.dp))
                            .clickable {

                            },
                        painter = painterResource(id = R.drawable.ic_play),
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                    )
                }
            }
        }

        Box(
            modifier = Modifier
                .padding(end = animatedDrawerSize)
                .padding(top = 80.dp)
                .height(56.dp)
                .width(40.dp)
                .align(Alignment.TopEnd)
                .clip(RoundedCornerShape(topStart = 18.dp, bottomStart = 18.dp))
                .background(TatarTheme.colors.supportOverlay)
                .clickable {
                    openDrawer()
                }
        ) {
            AnimatedContent(
                drawerState,
                transitionSpec = {
                    scaleIn(animationSpec = tween(durationMillis = 250)) with
                            scaleOut(animationSpec = tween(durationMillis = 250))
                }, label = ""
            ) { target ->
                Icon(
                    modifier = Modifier
                        .height(56.dp)
                        .width(40.dp)
                        .padding(if (target) 8.dp else 3.dp),
                    painter = painterResource(id = if (!target) R.drawable.ic_menu else R.drawable.ic_close),
                    contentDescription = "menu",
                    tint = TatarTheme.colors.colorWhite
                )
            }
        }

    }

}

@Composable
fun DrawerContent(animatedDrawerSize: Dp, lessonId: Int, navigateToLesson: (Int) -> Unit) {

    Box(
        modifier = Modifier
            .fillMaxHeight()
            .width(animatedDrawerSize)
            .padding(bottom = 10.dp)
            .clip(RoundedCornerShape(topStart = 18.dp, bottomStart = 18.dp))
            .background(TatarTheme.colors.supportOverlay)
    ) {
        LazyColumn(
            modifier = Modifier
                .padding(top = 72.dp)
                .fillMaxSize(),
            //contentPadding = PaddingValues(vertical = 10.dp),
            state = rememberLazyListState(),
        ) {
            itemsIndexed(arrayOf("Introduction", "Primitives", "If")) { index, item ->
                LessonsListItem(title = "${index + 1}. $item", index == lessonId) {
                    navigateToLesson(index)
                }
            }
        }
    }

}

@Composable
fun LessonsListItem(title: String, curr: Boolean, startLessonAction: () -> Unit) {
    Text(
        modifier = Modifier
            .fillMaxWidth()
            .background(if (curr) TatarTheme.colors.backPrimary else Color.Transparent)
            .clickable {
                startLessonAction()
            }
            .padding(start = 20.dp)
            .padding(vertical = 5.dp),
        text = title,
        fontFamily = semibold,
        color = TatarTheme.colors.colorWhite,
        fontSize = 18.sp,
        maxLines = 1,
    )
}

@Preview
@Composable
fun preview() {
    TatarByHackTheme(
        darkTheme = false
    ) {
        // A surface container using the 'background' color from the theme
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            LessonContent(
                PaddingValues(1.dp), 1, true, 2.dp
            ) {

            }
        }
    }
}