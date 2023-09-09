package com.inno.tatarbyhack.ui.course_page

import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.view.WindowCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import coil.compose.AsyncImage
import com.inno.tatarbyhack.App
import com.inno.tatarbyhack.R
import com.inno.tatarbyhack.domain.models.Course
import com.inno.tatarbyhack.domain.models.Lesson
import com.inno.tatarbyhack.domain.models.Module
import com.inno.tatarbyhack.ui.constructor.MyCourseFragmentArgs
import com.inno.tatarbyhack.ui.navigation_fragment.compose_elements.MultiSelector
import com.inno.tatarbyhack.ui.theme.TatarByHackTheme
import com.inno.tatarbyhack.ui.theme.TatarTheme
import com.inno.tatarbyhack.ui.theme.bold
import com.inno.tatarbyhack.ui.theme.medium
import com.inno.tatarbyhack.ui.theme.regular
import com.inno.tatarbyhack.ui.theme.semibold
import com.inno.tatarbyhack.utils.viewModelFactory

class CoursePageFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    private val args: CoursePageFragmentArgs by navArgs()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        return ComposeView(requireContext()).apply {
            setContent {
                TatarByHackTheme {
                    val viewModel = viewModel<CoursePageViewModel>(
                        factory = viewModelFactory {
                            CoursePageViewModel(
                                App.appModule.coursesRepository,
                                args.id,
                            )
                        }
                    )
                    val window = (context as Activity).window
                    WindowCompat.getInsetsController(window, this).isAppearanceLightStatusBars =
                        true
                    Surface(
                        modifier = Modifier.fillMaxSize(),
                        color = MaterialTheme.colorScheme.background
                    ) {
                        CoursePage(
                            viewModel,
                            back = {
                                findNavController().popBackStack()
                            },
                            openLesson = { moduleId: String, lessonId: String ->
                                val action = CoursePageFragmentDirections.lessonAction()
                                action.courseId = args.id
                                action.moduleId = moduleId
                                action.lessonId = lessonId
                                findNavController().navigate(action)
                            }
                        )
                    }
                }
            }
        }
    }

}

@Composable
fun CoursePage(
    viewModel: CoursePageViewModel,
    back: () -> Unit,
    openLesson: (String, String) -> Unit
) {

    val course = viewModel.myCourse.collectAsState()

    val currentLoginState = rememberSaveable { mutableStateOf("Xәбәр") }

    val scrollState = rememberScrollState()

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Image(
            modifier = Modifier
                .fillMaxSize(),
            painter = painterResource(id = R.drawable.pattern_white_background),
            contentDescription = null,
            contentScale = ContentScale.Crop
        )
        Column(
            Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
                .padding(top = 28.dp),
        ) {

            CourseInformation(course.value)

            BottomCoursePart(currentLoginState, course.value, openLesson)
        }
        ToolbarCourse(back, scrollState, course.value)
    }

}

@Composable
fun CourseInformation(
    course: Course
) {

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .padding(top = 85.dp, bottom = 35.dp),
    ) {
        AsyncImage(
            course.photoLink,
            contentDescription = null,
            contentScale = ContentScale.Inside,
            modifier = Modifier
                .padding(top = 10.dp)
                .width(150.dp)
                .height(150.dp)
                .clip(RoundedCornerShape(8.dp))
        )
        BoxWithConstraints(
            Modifier
                .padding(top = 10.dp)
                .padding(horizontal = 25.dp)
                .fillMaxWidth()

        ) {
            Column(
                Modifier
                    .width(maxWidth - 60.dp)
            ) {

                Text(
                    text = course.courseName,
                    fontFamily = bold,
                    fontSize = 27.sp,
                    color = TatarTheme.colors.colorBlue,
                    modifier = Modifier
                        .padding(top = 15.dp)
                )

                Text(
                    text = "${course.lessonsCounter} дәрес",
                    fontFamily = medium,
                    fontSize = 14.sp,
                    color = TatarTheme.colors.labelPrimary,
                    modifier = Modifier
                        .padding(top = 5.dp)
                )
            }
            Image(
                painter = painterResource(id = R.drawable.ic_favourite),
                contentDescription = null,
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(top = 10.dp)
                    .size(50.dp)
                    .clip(CircleShape)
                    .clickable {

                    }
            )
        }


    }

}

@Composable
fun ToolbarCourse(back: () -> Unit, scrollState: ScrollState, course: Course) {
    val animatedGray by animateColorAsState(
        targetValue = if (scrollState.value > 100) TatarTheme.colors.colorWhite else Color(
            0x80A7A7A7
        )
    )

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(85.dp)
    ) {

        AnimatedVisibility(
            visible = scrollState.value > 100,
            enter = fadeIn(animationSpec = tween(200)),
            exit = fadeOut(animationSpec = tween(200))
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White)
                    .padding(top = 25.dp)
            ) {
                Box(
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .fillMaxWidth()
                        .height(2.dp)
                        .background(
                            brush = Brush.verticalGradient(
                                colors = listOf(
                                    Color.Black.copy(alpha = 0.1f),
                                    Color.Transparent,
                                )
                            )
                        )
                )
            }
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 25.dp)
        ) {
            Box(
                modifier = Modifier
                    .align(Alignment.CenterStart)
                    .padding(start = 20.dp)
                    .padding(top = 10.dp)
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(animatedGray)
                    .clickable {
                        back()
                    }
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_arr_back),
                    contentDescription = null,
                    modifier = Modifier
                        .align(Alignment.Center)
                        .size(30.dp),
                    tint = TatarTheme.colors.labelPrimary
                )
            }

            Text(
                text = if (scrollState.value > 700) "ОГЭ/ЕГЭ, ${course.courseName}" else "ОГЭ/ЕГЭ",
                fontFamily = semibold,
                fontSize = 18.sp,
                color = TatarTheme.colors.labelPrimary,
                modifier = Modifier
                    .align(Alignment.Center)
                    .padding(top = 10.dp)
            )
        }
    }
}


@Composable
fun BottomCoursePart(
    currentLoginState: MutableState<String>,
    course: Course,
    openLesson: (String, String) -> Unit
) {

    Box(
        modifier = Modifier
            .heightIn(min = 500.dp)
    ) {

        Box(
            modifier = Modifier
                .heightIn(min = 500.dp)
                .padding(top = 24.dp)
                .clip(RoundedCornerShape(topStart = 25.dp, topEnd = 25.dp))
                .background(TatarTheme.colors.backPrimary)
        ) {

            AnimatedVisibility(
                currentLoginState.value == "Xәбәр",
                enter = slideInHorizontally(
                    animationSpec = tween(durationMillis = 250),
                    initialOffsetX = { -it / 3 - it },
                ),
                exit = slideOutHorizontally(
                    animationSpec = tween(durationMillis = 250),
                    targetOffsetX = { -it / 3 - it }),
                modifier = Modifier,
            ) {
                InformationPart(course)
            }

            AnimatedVisibility(
                currentLoginState.value == "Модульлар",
                enter = slideInHorizontally(
                    animationSpec = tween(durationMillis = 250),
                    initialOffsetX = { it / 3 + it },
                ),
                exit = slideOutHorizontally(
                    animationSpec = tween(durationMillis = 250),
                    targetOffsetX = { it / 3 + it }),
                modifier = Modifier,
            ) {
                Modules(course, openLesson)
            }


        }


        Box(
            modifier = Modifier
                .align(Alignment.TopCenter)
        ) {
            Box(
                modifier = Modifier
                    .height(54.dp)
                    .width(300.dp)
                    .clip(
                        shape = RoundedCornerShape(
                            topEnd = 20.dp,
                            topStart = 20.dp,
                            bottomStart = 22.dp,
                            bottomEnd = 22.dp
                        )
                    )
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                Color.Transparent,
                                Color.Black.copy(alpha = 0.3f),
                                Color.Transparent,
                            )
                        )
                    )
            )
            MultiSelector(
                options = listOf("Xәбәр", "Модульлар"),
                selectedOption = currentLoginState.value,
                onOptionSelect = {
                    when (it) {
                        "Xәбәр" -> currentLoginState.value = "Xәбәр"
                        "Модульлар" -> currentLoginState.value = "Модульлар"
                    }
                },
                containerColor = TatarTheme.colors.backPrimary,
                selectionColor = TatarTheme.colors.backSecondary,
                unselectedColor = TatarTheme.colors.colorWhite,
                selectedColor = TatarTheme.colors.colorWhite,
                textStyle = TextStyle(
                    color = TatarTheme.colors.colorWhite,
                    fontFamily = semibold,
                    fontSize = 14.sp
                ),
                modifier = Modifier
                    .width(300.dp)
            )

        }


    }
}

@Composable
fun InformationPart(course: Course) {
    Column(
        Modifier
            .fillMaxSize()
            .padding(horizontal = 30.dp, vertical = 45.dp)

    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                course.photoLink,
                contentDescription = null,
                modifier = Modifier
                    .padding(end = 10.dp)
                    .size(35.dp)
                    .clip(CircleShape)
                    .border(BorderStroke(2.dp, color = TatarTheme.colors.colorWhite)),
                contentScale = ContentScale.Crop
            )
            Text(
                text = course.authorName,
                fontFamily = semibold,
                fontSize = 17.sp,
                color = TatarTheme.colors.colorWhite,
                modifier = Modifier
            )
        }

        Text(
            text = course.desc,
            fontFamily = regular,
            fontSize = 18.sp,
            color = TatarTheme.colors.colorWhite,
            modifier = Modifier
                .padding(top = 15.dp)
        )

        Card(
            elevation = CardDefaults.cardElevation(
                defaultElevation = 10.dp
            ),
            modifier = Modifier
                .padding(top = 20.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color(0xFF9D80D7)
            )
        ) {
            Column(
                Modifier
                    .padding(vertical = 10.dp, horizontal = 20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Курсны бәяләгез!",
                    fontFamily = semibold,
                    fontSize = 18.sp,
                    color = TatarTheme.colors.colorWhite,
                    modifier = Modifier
                        .padding(bottom = 10.dp)
                )
                Row(
                    Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    for (i in 0..4) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_star),
                            contentDescription = null,
                            tint = TatarTheme.colors.colorWhite,
                            modifier = Modifier
                                .size(40.dp)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun Modules(course: Course, openLesson: (String, String) -> Unit) {
    val currentOpened = rememberSaveable { mutableStateOf(-1) }
    Column(
        Modifier
            .fillMaxSize()
            .padding(horizontal = 30.dp, vertical = 45.dp)

    ) {
        for (index in course.modules.indices) {
            ModuleItem(
                module = course.modules[index],
                currentOpened = currentOpened,
                index = index,
                openModule = {
                    currentOpened.value = index
                },
                closeModule = {
                    currentOpened.value = -1
                },
                openLesson = openLesson
            )
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

@Composable
fun ModuleItem(
    module: Module,
    currentOpened: MutableState<Int>,
    index: Int,
    openModule: () -> Unit,
    closeModule: () -> Unit,
    openLesson: (String, String) -> Unit
) {
    Column(
        Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(TatarTheme.colors.backElevated)
    ) {
        BoxWithConstraints(
            Modifier
                .fillMaxWidth()
                .heightIn(min = 65.dp, max = 115.dp)
                .clip(RoundedCornerShape(12.dp))
                .clickable {
                    if (currentOpened.value == index) {
                        closeModule()
                    } else {
                        openModule()
                    }
                }
                .padding(horizontal = 15.dp, vertical = 10.dp),
        ) {
            Column(
                Modifier.width(maxWidth-80.dp)
            ) {
                Text(
                    text = module.name,
                    fontFamily = semibold,
                    fontSize = 17.sp,
                    color = TatarTheme.colors.colorWhite,
                    modifier = Modifier,
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = "${module.lessons.size} дәрес",
                    fontFamily = regular,
                    fontSize = 13.sp,
                    color = TatarTheme.colors.colorWhite,
                    modifier = Modifier
                )
            }

            Icon(
                painter = painterResource(id = if (currentOpened.value == index) R.drawable.ic_arr_up else R.drawable.ic_open_module),
                contentDescription = null,
                tint = TatarTheme.colors.colorWhite,
                modifier = Modifier
                    .align(Alignment.CenterEnd)

            )
        }

        AnimatedVisibility(
            visible = currentOpened.value == index
        ) {
            Column(
                Modifier
                    .padding(horizontal = 20.dp)
                    .fillMaxWidth()
            ) {
                for (lesson in module.lessons) {
                    LessonRow(lesson, module.id, openLesson)
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        }
    }
}

@Composable
fun LessonRow(
    lesson: Lesson,
    moduleId: String,
    openLesson: (String, String) -> Unit,
) {

    Row(
        Modifier
            .fillMaxWidth()
            .height(40.dp)
            .clip(RoundedCornerShape(10.dp))
            .background(TatarTheme.colors.backPrimary)
            .clickable {
                openLesson(moduleId, lesson.id)
            }
            .padding(start = 20.dp, end = 10.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically

    ) {
        Text(
            text = lesson.name,
            fontFamily = regular,
            fontSize = 16.sp,
            color = TatarTheme.colors.colorWhite,
            style = TextStyle(textDecoration = if (lesson.done) TextDecoration.LineThrough else TextDecoration.None),
            modifier = Modifier
        )

        if (lesson.done) {
            Icon(
                painter = painterResource(id = R.drawable.ic_ready_lesson),
                contentDescription = null,
                modifier = Modifier.size(24.dp),
                tint = TatarTheme.colors.colorWhite
            )
        }
    }

}

