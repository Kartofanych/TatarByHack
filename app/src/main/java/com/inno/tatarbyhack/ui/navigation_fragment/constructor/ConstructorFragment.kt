package com.inno.tatarbyhack.ui.navigation_fragment.constructor

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
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
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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
import com.inno.tatarbyhack.App
import com.inno.tatarbyhack.R
import com.inno.tatarbyhack.domain.models.Course
import com.inno.tatarbyhack.ui.navigation_fragment.compose_elements.SearchCourseItem
import com.inno.tatarbyhack.ui.theme.TatarByHackTheme
import com.inno.tatarbyhack.ui.theme.TatarTheme
import com.inno.tatarbyhack.ui.theme.medium
import com.inno.tatarbyhack.ui.theme.semibold
import com.inno.tatarbyhack.utils.viewModelFactory
import kotlinx.coroutines.launch

class ConstructorFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = ComposeView(requireContext()).apply {
        setContent {
            TatarByHackTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val viewModel = viewModel<ConstructorViewModel>(
                        factory = viewModelFactory {
                            ConstructorViewModel(App.appModule.myCoursesRepository)
                        }
                    )
                    val window = (context as Activity).window
                    WindowCompat.getInsetsController(window, this).isAppearanceLightStatusBars =
                        true

                    ConstructorPage(
                        viewModel
                    )


                }
            }
        }
    }

}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ConstructorPage(viewModel: ConstructorViewModel) {

    val sheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
    )
    //Our flag variable
    val courses = viewModel.myCourses.collectAsState()

    val scope = rememberCoroutineScope()

    ModalBottomSheetLayout(
        sheetState = sheetState,
        sheetContent = {
            BottomSheetConstructorContent { name ->
                viewModel.createCourse(name)
                scope.launch {
                    sheetState.hide()
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
                    .padding(top = 24.dp)
            ) {
                ToolbarConstructor()
                AddCourse {
                    //
                    scope.launch {
                        sheetState.show()
                    }
                }
                ListCourses(courses.value)
            }

        }
    }


}

@Composable
fun ListCourses(courses: List<Course>) {
    LazyColumn(
        modifier = Modifier
            .padding(horizontal = 10.dp)
            .fillMaxWidth(),
        contentPadding = PaddingValues(top = 2.dp, bottom = 10.dp),
        state = rememberLazyListState()

    ) {
        itemsIndexed(courses) { index, item ->
            SearchCourseItem(course = item)
        }
    }
}

@Composable
fun AddCourse(openSheet: () -> Unit) {
    Row(
        Modifier
            .fillMaxWidth()
            .padding(vertical = 10.dp)
            .height(50.dp)
            .clickable {
                openSheet()
            },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_add_course),
            contentDescription = null,
            tint = TatarTheme.colors.labelPrimary,
            modifier = Modifier
                .padding(horizontal = 13.dp)
                .size(20.dp)
        )

        Text(
            text = "Курс өстәргә",
            style = TextStyle(
                fontSize = 21.sp,
                fontFamily = semibold,
                color = TatarTheme.colors.labelPrimary
            )
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomSheetConstructorContent(createCourse: (String) -> Unit) {


    var courseName by remember { mutableStateOf("") }


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
                text = "Яңа курс",
                style = TextStyle(
                    fontSize = 20.sp,
                    fontFamily = semibold,
                    color = TatarTheme.colors.labelPrimary
                ),
                modifier = Modifier
                    .fillMaxWidth()
            )
            Text(
                text = "Курс исеме",
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
                value = courseName,
                onValueChange = {
                    courseName = it
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
                        text = "Например, Python",
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
                        createCourse(courseName)
                    }
            ){

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
fun ToolbarConstructor() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 10.dp)
            .padding(horizontal = 30.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column {
            Text(
                text = "Сезнең курслар",
                style = TextStyle(
                    fontSize = 28.sp,
                    fontFamily = semibold,
                    color = TatarTheme.colors.labelPrimary
                )
            )
            Icon(
                painter = painterResource(id = R.drawable.ic_toolbar_line),
                contentDescription = null,
                modifier = Modifier
                    .padding(top = 5.dp)
                    .height(2.dp),
                tint = TatarTheme.colors.backPrimary
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