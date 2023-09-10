package com.inno.tatarbyhack.ui.constructor

import android.app.Activity
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
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
import com.inno.tatarbyhack.domain.models.Module
import com.inno.tatarbyhack.ui.navigation_fragment.compose_elements.DragDropColumn
import com.inno.tatarbyhack.ui.theme.TatarByHackTheme
import com.inno.tatarbyhack.ui.theme.TatarTheme
import com.inno.tatarbyhack.ui.theme.bold
import com.inno.tatarbyhack.ui.theme.medium
import com.inno.tatarbyhack.ui.theme.regular
import com.inno.tatarbyhack.ui.theme.semibold
import com.inno.tatarbyhack.utils.viewModelFactory
import kotlinx.coroutines.launch


class MyCourseFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    private val args: MyCourseFragmentArgs by navArgs()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        return ComposeView(requireContext()).apply {
            setContent {
                TatarByHackTheme {
                    val viewModel = viewModel<MyCourseViewModel>(
                        factory = viewModelFactory {
                            MyCourseViewModel(
                                App.appModule.myCoursesRepository,
                                args.id,
                                App.appModule.context
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
                        MyCoursePage(
                            viewModel,
                            back = {
                                findNavController().popBackStack()
                            },
                            delete = {
                                viewModel.deleteCourse()
                                findNavController().popBackStack()
                            }
                        )
                    }
                }
            }
        }
    }

}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MyCoursePage(viewModel: MyCourseViewModel, back: () -> Unit, delete: () -> Unit) {

    val course = viewModel.myCourse.collectAsState()

    val singlePhotoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = { uri ->
            if (uri != null) {
                viewModel.updateCourseImage(uri.toString())
            }
        }
    )


    val sheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
    )

    val scope = rememberCoroutineScope()

    val scrollState = rememberScrollState()


    val sheetInformation = remember {
        mutableStateOf(true)
    }


    ModalBottomSheetLayout(
        sheetState = sheetState,
        sheetContent = {
            if (sheetInformation.value) {
                BottomSheetInformationContent(course.value) { desc ->
                    viewModel.updateCourseDescription(desc)
                    scope.launch {
                        sheetState.hide()
                    }
                }
            } else {
                BottomSheetNewModuleContent { newModule ->
                    viewModel.addCourseModules(newModule)
                    scope.launch {
                        sheetState.hide()
                    }
                }
            }
        },
        sheetBackgroundColor = Color.Transparent
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
                    .verticalScroll(scrollState),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceBetween
            ) {

                CourseInfo(course.value, singlePhotoPickerLauncher)

                CourseDescription(course.value) {
                    sheetInformation.value = true
                    scope.launch {
                        sheetState.show()
                    }
                }
                ModulesPart(viewModel) {
                    sheetInformation.value = false
                    scope.launch {
                        sheetState.show()
                    }
                }
            }

            ToolbarMyCourse(back, delete, scrollState, course.value.courseName)
        }

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomSheetNewModuleContent(createModule: (String) -> Unit) {

    var moduleName by remember { mutableStateOf("") }


    Surface(
        modifier = Modifier
            .clip(shape = RoundedCornerShape(20.dp, 20.dp, 0.dp, 0.dp)),
        color = TatarTheme.colors.colorWhite
    ) {
        Column(
            modifier = Modifier
                .padding(horizontal = 30.dp)
                .fillMaxWidth()
                .padding(bottom = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Box(
                modifier = Modifier
                    .padding(top = 10.dp, bottom = 20.dp)
                    .height(2.dp)
                    .width(50.dp)
                    .background(Color(0xFFA7A7A7))
            )


            Text(
                text = "Яңа модуль",
                style = TextStyle(
                    fontSize = 20.sp,
                    fontFamily = semibold,
                    color = TatarTheme.colors.labelPrimary
                ),
                modifier = Modifier
                    .fillMaxWidth()
            )
            Text(
                text = "Модуль исеме",
                style = TextStyle(
                    fontSize = 17.sp,
                    fontFamily = medium,
                    color = TatarTheme.colors.labelPrimary
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 20.dp, bottom = 10.dp)
            )

            TextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(shape = RoundedCornerShape(14.dp)),
                value = moduleName,
                onValueChange = {
                    moduleName = it
                },
                maxLines = 1,
                textStyle = TextStyle(
                    fontFamily = medium,
                    fontSize = 14.sp,
                    color = TatarTheme.colors.labelPrimary
                ),
                interactionSource = remember { MutableInteractionSource() },
                placeholder = {
                    Text(
                        modifier = Modifier
                            .fillMaxWidth(),
                        text = "Например, Модуль 1",
                        style = TextStyle(
                            fontFamily = medium,
                            fontSize = 14.sp,
                            color = Color(0xFF898999)
                        ),
                        textAlign = TextAlign.Start
                    )
                },
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = Color(0xFFD9D9D9),
                    focusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                ),
            )

            Box(
                modifier = Modifier
                    .padding(top = 30.dp)
                    .fillMaxWidth()
                    .height(40.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(TatarTheme.colors.backPrimary)
                    .clickable {
                        createModule(moduleName)
                    }
            ) {

                Text(
                    modifier = Modifier
                        .align(Alignment.Center),
                    text = "Өстәргә",
                    style = TextStyle(
                        fontFamily = semibold,
                        fontSize = 17.sp,
                        color = TatarTheme.colors.colorWhite
                    ),
                    textAlign = TextAlign.Start
                )
            }

        }
    }

}

@Composable
fun ModulesPart(viewModel: MyCourseViewModel, openSheet: () -> Unit) {

    val items = viewModel.myCourse.collectAsState().value.modules

    Column(
        modifier = Modifier
            .heightIn(min = 400.dp)
            .clip(RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp))
            .background(TatarTheme.colors.backPrimary)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(70.dp)
                .padding(top = 10.dp)
                .padding(horizontal = 30.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = "Модульлар",
                    style = TextStyle(
                        fontSize = 22.sp,
                        fontFamily = semibold,
                        color = TatarTheme.colors.colorWhite
                    )
                )
                Icon(
                    painter = painterResource(id = R.drawable.ic_toolbar_line),
                    contentDescription = null,
                    tint = TatarTheme.colors.colorOrange,
                    modifier = Modifier
                        .padding(top = 5.dp)
                        .height(2.dp),

                    )
            }
            Icon(
                painter = painterResource(id = R.drawable.ic_add_module),
                contentDescription = null,
                modifier = Modifier
                    .clip(CircleShape)
                    .clickable {
                        openSheet()
                    }
                    .padding(4.dp)
                    .size(22.dp),
                tint = TatarTheme.colors.colorWhite
            )

        }
        Box(modifier = Modifier.fillMaxSize()) {

            DragDropColumn(items = items, onSwap = viewModel::updateModulesPosition) { item ->
                MyModuleItem(item)
            }
        }
//        Column(
//            Modifier
//                .fillMaxWidth()
//                .padding(horizontal = 20.dp)
//                .padding(bottom = 40.dp)
//        ) {
//            Spacer(
//                modifier = Modifier
//                    .height(15.dp)
//            )
//            items.forEach {
//                ModuleItem(it)
//                Spacer(
//                    modifier = Modifier
//                        .height(10.dp)
//                )
//            }
//        }


    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomSheetInformationContent(course: Course, changeDescriptionOfCourse: (String) -> Unit) {


    var courseDesc by remember { mutableStateOf(course.desc) }


    Surface(
        modifier = Modifier
            .clip(shape = RoundedCornerShape(20.dp, 20.dp, 0.dp, 0.dp)),
        color = TatarTheme.colors.colorWhite
    ) {
        Column(
            modifier = Modifier
                .padding(horizontal = 30.dp)
                .fillMaxWidth()
                .padding(bottom = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Box(
                modifier = Modifier
                    .padding(top = 10.dp, bottom = 20.dp)
                    .height(2.dp)
                    .width(50.dp)
                    .background(Color(0xFFA7A7A7))
            )


            Text(
                text = "Xәбәр",
                style = TextStyle(
                    fontSize = 20.sp,
                    fontFamily = bold,
                    color = TatarTheme.colors.labelPrimary
                ),
                modifier = Modifier
                    .fillMaxWidth()
            )

            TextField(
                modifier = Modifier
                    .padding(top = 15.dp)
                    .fillMaxWidth()
                    .heightIn(min = 70.dp)
                    .clip(shape = RoundedCornerShape(14.dp)),
                value = courseDesc,
                onValueChange = {
                    courseDesc = it
                },
                textStyle = TextStyle(
                    fontFamily = medium,
                    fontSize = 14.sp,
                    color = TatarTheme.colors.labelPrimary
                ),
                interactionSource = remember { MutableInteractionSource() },
                placeholder = {
                    Text(
                        modifier = Modifier
                            .fillMaxWidth(),
                        text = "Xәбәр курс буенча",
                        style = TextStyle(
                            fontFamily = medium,
                            fontSize = 14.sp,
                            color = Color(0xFF898999)
                        ),
                        textAlign = TextAlign.Start
                    )
                },
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = Color(0xFFD9D9D9),
                    focusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                ),
            )

            Box(
                modifier = Modifier
                    .padding(top = 30.dp, bottom = 15.dp)
                    .fillMaxWidth()
                    .height(40.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(TatarTheme.colors.backPrimary)
                    .clickable {
                        changeDescriptionOfCourse(courseDesc)
                    }
            ) {

                Text(
                    modifier = Modifier
                        .align(Alignment.Center),
                    text = "Өстәргә",
                    style = TextStyle(
                        fontFamily = semibold,
                        fontSize = 17.sp,
                        color = TatarTheme.colors.colorWhite
                    ),
                    textAlign = TextAlign.Start
                )
            }

        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CourseDescription(course: Course, openDescEditor: () -> Unit) {

    Card(
        modifier = Modifier
            .padding(horizontal = 20.dp, vertical = 35.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp,
        ),
        onClick = {
            openDescEditor()
        }
    ) {
        BoxWithConstraints(
            Modifier
                .heightIn(min = 90.dp)
                .fillMaxWidth()
                .clip(RoundedCornerShape(10.dp))
                .background(TatarTheme.colors.colorWhite)
                .padding(horizontal = 15.dp, vertical = 15.dp),
        ) {
            Column(
                modifier = Modifier
                    .width(
                        width = maxWidth - 50.dp,
                    )
            ) {
                Text(
                    text = "Xәбәр",
                    fontFamily = bold,
                    fontSize = 18.sp,
                    color = Color(0xFF0E0025),
                    modifier = Modifier
                )
                Text(
                    text = if (course.desc.isEmpty()) "Xәбәр курс буенча" else course.desc,
                    fontFamily = medium,
                    fontSize = 15.sp,
                    color = if (course.desc.isEmpty()) Color(0xFF898999) else TatarTheme.colors.labelPrimary,
                    modifier = Modifier
                        .padding(top = 5.dp, bottom = 5.dp)
                )
            }
            Image(
                painter = painterResource(id = R.drawable.ic_edit_pencil),
                contentDescription = null,
                Modifier
                    .align(Alignment.CenterEnd)
                    .size(40.dp)
            )
        }
    }
}

@Composable
fun CourseInfo(
    course: Course,
    singlePhotoPickerLauncher: ManagedActivityResultLauncher<PickVisualMediaRequest, Uri?>,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .padding(top = 70.dp)
    ) {
        AsyncImage(
            model = if (course.photoLink.isEmpty()) {
                R.drawable.ic_add_image
            } else {
                Uri.parse(course.photoLink)
            },
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .padding(top = 10.dp)
                .width(maxOf(150, 0).dp)
                .height(maxOf(150, 0).dp)
                .clip(RoundedCornerShape(8.dp))
                .clickable {
                    singlePhotoPickerLauncher.launch(
                        PickVisualMediaRequest(mediaType = ActivityResultContracts.PickVisualMedia.ImageOnly)
                    )
                }
        )
        Text(
            text = course.courseName,
            fontFamily = bold,
            fontSize = 27.sp,
            color = TatarTheme.colors.colorBlue,
            modifier = Modifier
                .padding(top = 15.dp)
        )

        Text(
            text = course.authorName.ifEmpty { "Галиев Ирек" },
            fontFamily = medium,
            fontSize = 14.sp,
            color = TatarTheme.colors.labelPrimary,
            modifier = Modifier
                .padding(top = 5.dp)
        )

        Box(
            modifier = Modifier
                .padding(top = 20.dp)
                .width(150.dp)
                .height(40.dp)
                .clip(RoundedCornerShape(10.dp))
                .background(TatarTheme.colors.colorBlue)
                .clickable {

                }
        ) {
            Text(
                text = "Выложить",
                fontFamily = semibold,
                fontSize = 14.sp,
                color = TatarTheme.colors.colorWhite,
                modifier = Modifier
                    .align(Alignment.Center)
            )
        }


    }
}

@Composable
fun ToolbarMyCourse(
    back: () -> Unit,
    delete: () -> Unit,
    scrollState: ScrollState,
    courseName: String
) {

    val toolbarSize by animateDpAsState(targetValue = if (scrollState.value > 550) 85.dp else 98.dp)
    val animatedGray by animateColorAsState(
        targetValue = if (scrollState.value > 550) TatarTheme.colors.colorWhite else Color(
            0x80A7A7A7
        )
    )

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(toolbarSize)
    ) {

        AnimatedVisibility(
            visible = scrollState.value > 550,
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

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 28.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .padding(start = 20.dp)
                    .size(50.dp)
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
            AnimatedVisibility(
                visible = scrollState.value > 550,
                enter = fadeIn(animationSpec = tween(200)),
                exit = fadeOut(animationSpec = tween(200))
            ) {
                Text(
                    text = courseName,
                    fontFamily = bold,
                    fontSize = 27.sp,
                    color = TatarTheme.colors.colorBlue,
                    modifier = Modifier

                )
            }

            Box(
                modifier = Modifier
                    .padding(end = 20.dp)
                    .size(50.dp)
                    .clip(CircleShape)
                    .background(animatedGray)
                    .clickable {
                        delete()
                    }
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_delete),
                    contentDescription = null,
                    modifier = Modifier
                        .align(Alignment.Center)
                        .size(30.dp),
                    tint = TatarTheme.colors.labelPrimary
                )
            }
        }
    }
}

@Composable
fun MyModuleItem(module: Module) {
    Row(
        Modifier
            .fillMaxWidth()
            .height(65.dp)
            //.clip(RoundedCornerShape(12.dp))
            .background(TatarTheme.colors.backElevated)
            .padding(horizontal = 15.dp, vertical = 10.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column {
            Text(
                text = module.name,
                fontFamily = semibold,
                fontSize = 17.sp,
                color = TatarTheme.colors.colorWhite,
                modifier = Modifier
            )
            Text(
                text = "5 дәрес",
                fontFamily = regular,
                fontSize = 13.sp,
                color = TatarTheme.colors.colorWhite,
                modifier = Modifier
            )
        }

        Icon(
            painter = painterResource(id = R.drawable.ic_delete),
            contentDescription = null,
            tint = TatarTheme.colors.colorWhite
        )
    }
}
