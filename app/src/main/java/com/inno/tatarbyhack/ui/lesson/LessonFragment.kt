package com.inno.tatarbyhack.ui.lesson

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.Fragment
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.inno.tatarbyhack.App
import com.inno.tatarbyhack.R
import com.inno.tatarbyhack.domain.models.Course
import com.inno.tatarbyhack.domain.models.Lesson
import com.inno.tatarbyhack.domain.models.Task
import com.inno.tatarbyhack.ui.theme.TatarByHackTheme
import com.inno.tatarbyhack.ui.theme.TatarTheme
import com.inno.tatarbyhack.ui.theme.bold
import com.inno.tatarbyhack.ui.theme.medium
import com.inno.tatarbyhack.ui.theme.semibold
import com.inno.tatarbyhack.utils.viewModelFactory

class LessonFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    private val args: LessonFragmentArgs by navArgs()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {


        return ComposeView(requireContext()).apply {

            setContent {
                TatarByHackTheme {
                    val viewModel = viewModel<LessonViewModel>(
                        factory = viewModelFactory {
                            LessonViewModel(
                                App.appModule.coursesRepository,
                                args.courseId,
                            )
                        }
                    )
                    val course = viewModel.myCourse.collectAsState()
//
//                    Log.d("121212", "${args.courseId} ${args.moduleId} ${args.lessonId}")
//                    Log.d("121212", course.value.modules.toString())


                    LessonContent(
                        args,
                        course.value,
                        openPlayer = {
                            val action = LessonFragmentDirections.actionPlayer()
                            action.url =
                                course.value.modules.first { it.id == args.moduleId }.lessons.first { it.id == args.lessonId }.videoLink
                            findNavController().navigate(action)
                        },
                        back = {
                            findNavController().popBackStack()
                        },
                        viewModel = viewModel
                    )

                }
            }
        }
    }


}

@Composable
fun LessonContent(
    args: LessonFragmentArgs,
    course: Course,
    back: () -> Unit,
    openPlayer: () -> Unit,
    viewModel: LessonViewModel
) {
    val lesson =
        course.modules.firstOrNull { it.id == args.moduleId }?.lessons?.firstOrNull { it.id == args.lessonId }
            ?: Lesson(
                "1", "some name", "somelink", "safniwrb0rw",
                listOf(),
                false
            )

    var solving by rememberSaveable { mutableStateOf(false) }

    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            modifier = Modifier
                .fillMaxSize(),
            painter = painterResource(id = if (!solving) R.drawable.pattern_white_background else R.drawable.pattern_dark_background),
            contentDescription = null,
            contentScale = ContentScale.Crop
        )
        if (!solving) {
            ReadingPart(args, course, back, openPlayer, lesson) { solving = true }
        } else {
            SolvingPart(lesson.tasks, back, viewModel)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SolvingPart(tasks: List<Task>, back: () -> Unit, viewModel: LessonViewModel) {
    var taskIndex by rememberSaveable { mutableStateOf(0) }
    var taskAccuracy by rememberSaveable { mutableStateOf(0) }
    var textField by remember { mutableStateOf("") }
    var curState by remember { mutableStateOf(0) }
    val buttonColor by animateColorAsState(
        targetValue = if (curState == 0) Color(0xFFF49A11) else Color(
            0xFF9D80D7
        )
    )

    val matching = viewModel.matching.collectAsState()

    Column(
        modifier = Modifier
            .padding(top = 28.dp)
            .padding(bottom = 10.dp)
            .fillMaxSize(),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        ToolbarSolving()

        Card(
            modifier = Modifier
                .padding(horizontal = 40.dp)
                .fillMaxWidth(),
            shape = RoundedCornerShape(15.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color(0xFF9D80D7)
            )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
                    .padding(top = 15.dp, bottom = 30.dp)
            ) {

                Text(
                    text = "Сорау  ${taskIndex + 1} / ${tasks.size}",
                    style = TextStyle(
                        fontSize = 20.sp,
                        fontFamily = bold,
                        color = TatarTheme.colors.colorWhite
                    )
                )
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 15.dp)
                        .heightIn(min = 200.dp)
                ) {
                    Text(
                        modifier = Modifier
                            .align(Alignment.Center),
                        text = if (curState == 0) tasks[taskIndex].text else tasks[taskIndex].answer,
                        style = TextStyle(
                            fontSize = if (curState == 0) 26.sp else 20.sp,
                            fontFamily = semibold,
                            color = TatarTheme.colors.colorWhite,
                            textAlign = TextAlign.Center
                        ),
                    )
                }

                TextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(min = 120.dp)
                        .background(Color.Transparent)
                        .border(1.dp, color = Color(0xFF4F0E77), RoundedCornerShape(14.dp)),
                    value = textField,
                    onValueChange = {
                        textField = it
                    },
                    textStyle = TextStyle(
                        fontFamily = semibold,
                        fontSize = 18.sp,
                        color = TatarTheme.colors.colorWhite
                    ),
                    placeholder = {
                        Text(
                            modifier = Modifier.fillMaxWidth(),
                            text = "Жәвап...",
                            style = TextStyle(
                                fontFamily = medium,
                                fontSize = 18.sp,
                                color = TatarTheme.colors.colorWhite
                            ),
                            textAlign = TextAlign.Start
                        )
                    },
                    colors = TextFieldDefaults.textFieldColors(
                        containerColor = Color(0xFF7C52C7),
                        focusedIndicatorColor = Color.Transparent,
                        disabledIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                    ),
                    shape = RoundedCornerShape(14.dp)
                )

                if (curState > 0) {
                    Text(
                        modifier = Modifier
                            .padding(top = 5.dp)
                            .fillMaxWidth(),
                        text = if (matching.value == "-1f") {
                            "йөкләнеш..."
                        } else {
                            curState++
                            "${matching.value}% туры килү"
                        },
                        style = TextStyle(
                            fontFamily = medium,
                            fontSize = 18.sp,
                            color = TatarTheme.colors.colorWhite
                        ),
                        textAlign = TextAlign.Start
                    )
                }
            }
        }

        Box(
            modifier = Modifier
                .width(240.dp)
                .height(60.dp)
                .clip(RoundedCornerShape(30.dp))
                .background(buttonColor)
                .clickable(curState != 1) {
                    if (curState == 0) {
                        curState++
                        viewModel.getMatching(textField, tasks[taskIndex].answer)
                    } else {
                        curState = 0


                        textField = ""
                        if (taskIndex < tasks.size - 1) {
                            taskIndex++
                        } else {
                            //report
                            back()
                        }
                    }
                }
        ) {
            Text(
                modifier = Modifier
                    .align(Alignment.Center),
                text = if (curState == 0) "Тикшерү" else "Киләсе сорау",
                style = TextStyle(
                    fontFamily = semibold,
                    fontSize = 17.sp,
                    color = TatarTheme.colors.colorWhite
                ),
                textAlign = TextAlign.Center
            )
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Image(
                modifier = Modifier
                    .align(Alignment.BottomStart),
                painter = painterResource(id = R.drawable.ic_courses_bottom_back),
                contentDescription = null,
            )
        }

    }
}

@Composable
fun ToolbarSolving() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(70.dp)
            .padding(horizontal = 20.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column {
            Text(
                text = "Биремнәр",
                style = TextStyle(
                    fontSize = 24.sp,
                    fontFamily = semibold,
                    color = TatarTheme.colors.labelPrimary
                ),
                color = TatarTheme.colors.colorWhite
            )
            Image(
                painter = painterResource(id = R.drawable.ic_toolbar_line),
                contentDescription = null,
                modifier = Modifier.padding(top = 5.dp),
            )
        }
//        Icon(
//            painter = painterResource(id = R.drawable.ic_logo),
//            contentDescription = null,
//            modifier = Modifier
//                .size(70.dp),
//            tint = TatarTheme.colors.backSecondary
//        )

    }
}


@Composable
fun ReadingPart(
    args: LessonFragmentArgs,
    course: Course,
    back: () -> Unit,
    openPlayer: () -> Unit,
    lesson: Lesson,
    startSolving: () -> Unit,
) {
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
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(top = 28.dp)
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
                        .align(Alignment.CenterStart)
                        .padding(start = 10.dp)
                        .size(40.dp)
                        .clip(CircleShape)
                        .clickable {
                            back()
                        }
                        .padding(8.dp)
                )

                if (course.modules.isNotEmpty()) {
                    Text(
                        modifier = Modifier
                            .padding(horizontal = 60.dp)
                            .align(Alignment.Center),
                        text = course.modules.first { it.id == args.moduleId }.name,
                        fontFamily = semibold,
                        color = TatarTheme.colors.labelPrimary,
                        textAlign = TextAlign.Center,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }

            Column(
                Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    modifier = Modifier
                        .padding(20.dp)
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(20.dp))
                        .aspectRatio(1.89f)
                        .clickable {
                            openPlayer()
                        },
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
                                openPlayer()
                            },
                        painter = painterResource(id = R.drawable.ic_play_video),
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                    )
                }

                BoxWithConstraints(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp)
                        .padding(bottom = 20.dp),
                ) {

                    Text(
                        modifier = Modifier
                            .width(maxWidth - 100.dp),
                        text = lesson.name,
                        fontFamily = semibold,
                        color = TatarTheme.colors.labelPrimary,
                        fontSize = 27.sp
                    )
                    Row(
                        modifier = Modifier
                            .align(Alignment.CenterEnd)
                            .height(35.dp)
                            .width(120.dp)
                            .clip(RoundedCornerShape(10.dp))
                            .background(TatarTheme.colors.colorOrange)
                            .clickable {

                            },
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_nav_vebinar),
                            contentDescription = null,
                            Modifier
                                .padding(end = 8.dp)
                                .size(23.dp),
                            tint = TatarTheme.colors.colorWhite
                        )
                        Text(
                            modifier = Modifier,
                            text = "Өй эше",
                            fontFamily = semibold,
                            color = TatarTheme.colors.colorWhite,
                            fontSize = 12.sp,
                            maxLines = 1
                        )
                    }
                }


                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp)
                        .clip(RoundedCornerShape(20.dp)),
                    elevation = CardDefaults.cardElevation(
                        defaultElevation = 8.dp
                    ),
                    colors = CardDefaults.cardColors(
                        containerColor = Color(0xFFF0F6FE)
                    )
                ) {
                    Box(
                        modifier =
                        Modifier
                            .fillMaxSize()
                            .padding(horizontal = 20.dp, vertical = 20.dp)
                    ) {
                        Text(
                            text = lesson.text,
                            fontFamily = medium,
                            color = TatarTheme.colors.labelPrimary,
                            fontSize = 14.sp,
                        )
                    }
                }

                Row(
                    modifier = Modifier
                        .padding(top = 20.dp, bottom = 50.dp)
                        .height(50.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .clickable {
                            startSolving()
                        }
                        .background(TatarTheme.colors.colorOrange)
                        .padding(horizontal = 20.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_play),
                        contentDescription = null,
                        Modifier
                            .size(30.dp)
                            .padding(5.dp),
                        tint = TatarTheme.colors.colorWhite
                    )
                    Text(
                        modifier = Modifier,
                        text = "Биремне чишегез!",
                        fontFamily = semibold,
                        color = TatarTheme.colors.colorWhite,
                        fontSize = 14.sp,
                    )
                }
            }
        }
    }
}