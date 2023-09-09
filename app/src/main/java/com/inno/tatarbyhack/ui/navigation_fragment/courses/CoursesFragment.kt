package com.inno.tatarbyhack.ui.navigation_fragment.courses

import android.app.Activity
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.expandIn
import androidx.compose.animation.shrinkOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
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
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.view.WindowCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import com.inno.tatarbyhack.App
import com.inno.tatarbyhack.R
import com.inno.tatarbyhack.domain.models.Course
import com.inno.tatarbyhack.ui.navigation_fragment.NavigationFragmentDirections
import com.inno.tatarbyhack.ui.navigation_fragment.compose_elements.CourseItem
import com.inno.tatarbyhack.ui.navigation_fragment.compose_elements.SearchCourseItem
import com.inno.tatarbyhack.ui.navigation_fragment.compose_elements.SmallCourseItem
import com.inno.tatarbyhack.ui.theme.TatarByHackTheme
import com.inno.tatarbyhack.ui.theme.TatarTheme
import com.inno.tatarbyhack.ui.theme.semibold
import com.inno.tatarbyhack.utils.findTopNavController
import com.inno.tatarbyhack.utils.viewModelFactory
import kotlinx.coroutines.launch

class CoursesFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = ComposeView(requireContext()).apply {
        setContent {
            TatarByHackTheme {
                //download
                val viewModel = viewModel<CoursesViewModel>(
                    factory = viewModelFactory {
                        CoursesViewModel(App.appModule.coursesRepository)
                    }
                )
                viewModel.start()
                CoursesPage(
                    viewModel,
                    this
                ) { id ->
                    val action = NavigationFragmentDirections.actionCourseFragment()
                    action.id = id
                    findTopNavController().navigate(action)
                }
            }
        }
    }

}


@OptIn(ExperimentalMaterialApi::class, ExperimentalAnimationApi::class)
@Composable
fun CoursesPage(viewModel: CoursesViewModel, composeView: ComposeView, moveToCourse: (String) -> Unit) {

    val localContext = LocalContext.current
    val window = (localContext as Activity).window

    val sheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
    )
    //Our flag variable
    val showModalSheet = rememberSaveable {
        mutableStateOf(false)
    }

    val searchState = rememberSaveable {
        mutableStateOf(false)
    }


    val scope = rememberCoroutineScope()

    val searchList = rememberSaveable { mutableStateOf(listOf<Course>()) }

    val recommendedCoursesTitles = listOf("Телләрне өйрәнү", "IT", "Табигый фәннәр")
    val currRecommendedCourse = rememberSaveable { mutableStateOf(0) }
    val recommendedCourses = viewModel.recommendedCourses.collectAsState()

    val animatedBackground by animateColorAsState(
        targetValue = if (searchState.value) Color.White else Color(
            0xFF7C52C6
        )
    )

    WindowCompat.getInsetsController(window, composeView).isAppearanceLightStatusBars =
        searchState.value



    ModalBottomSheetLayout(
        sheetState = sheetState,
        sheetContent = {
            BottomSheetContent(
                { i: Int ->
                    showModalSheet.value = !showModalSheet.value
                    scope.launch {
                        sheetState.hide()
                    }
                    currRecommendedCourse.value = i
                },
                items = recommendedCoursesTitles,
                current = currRecommendedCourse.value
            )
        },
        sheetBackgroundColor = Color.Transparent
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(animatedBackground)
        ) {
            AnimatedContent(targetState = searchState.value) {
                Image(
                    modifier = Modifier
                        .fillMaxSize(),
                    painter = painterResource(id = if (it) R.drawable.pattern_white_background else R.drawable.pattern_dark_background),
                    contentDescription = null,
                    contentScale = ContentScale.Crop
                )
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 24.dp),
                verticalArrangement = Arrangement.SpaceBetween
            ) {

                AnimatedVisibility(
                    !searchState.value,
                    enter = expandIn(),
                    exit = shrinkOut(),
                ) {
                    Toolbar(title = "Дәресләр")
                }

                SearchField(searchState, viewModel, searchList)


                Box(modifier = Modifier.fillMaxSize()) {
                    androidx.compose.animation.AnimatedVisibility(
                        searchState.value,
                        enter = slideInHorizontally(initialOffsetX = { it + it / 2 }),
                        exit = slideOutHorizontally(targetOffsetX = { it + it / 2 })
                    ) {
                        SearchItems(searchList, moveToCourse)
                    }


                    androidx.compose.animation.AnimatedVisibility(
                        !searchState.value,
                        enter = slideInHorizontally(initialOffsetX = { -it / 2 - it }),
                        exit = slideOutHorizontally(targetOffsetX = { -it / 2 - it }),
                        modifier = Modifier.fillMaxSize()
                    ) {
                        Column(
                            Modifier.fillMaxSize()
                        ) {

                            TopPart(viewModel, moveToCourse)

                            Spacer(
                                modifier = Modifier
                                    .height(0.7.dp)
                                    .fillMaxWidth()
                                    .background(Color(0x33FFFFFF))
                            )
                            BottomPart(
                                openSheet = {
                                    showModalSheet.value = !showModalSheet.value
                                    scope.launch {
                                        sheetState.show()
                                    }
                                },
                                title = recommendedCoursesTitles[currRecommendedCourse.value],
                                recommendedCourses = recommendedCourses.value[currRecommendedCourse.value],
                                moveToCourse = moveToCourse
                            )
                        }
                    }
                }
            }
        }

    }
}

@Composable
fun TopPart(viewModel: CoursesViewModel, moveToCourse: (String) -> Unit) {

    val items = viewModel.popularCourses.collectAsState()
    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Text(
            modifier = Modifier
                .padding(start = 20.dp),
            text = "Танылган",
            style = TextStyle(
                fontSize = 20.sp,
                fontFamily = semibold,
                color = Color.White
            )
        )

        LazyRow(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            contentPadding = PaddingValues(
                horizontal = 20.dp,
                vertical = 10.dp
            ),
            state = rememberLazyListState(),

            ) {
            itemsIndexed(items.value) { index, item ->
                CourseItem(item, moveToCourse)
            }
        }
    }
}

@Composable
fun SearchItems(searchList: MutableState<List<Course>>, moveToCourse: (String) -> Unit) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp),
        contentPadding = PaddingValues(vertical = 10.dp),
        state = rememberLazyListState(),
        verticalArrangement = Arrangement.spacedBy(10.dp)


    ) {
        itemsIndexed(searchList.value) { index, item ->
            SearchCourseItem(item, moveToCourse)
        }
    }
}


@Preview
@Composable
fun BottomSheetContent(
    closeSheet: (Int) -> Unit = {},
    items: List<String> = listOf(""),
    current: Int = 0
) {
    Surface(
        modifier = Modifier
            .clip(shape = RoundedCornerShape(15.dp, 15.dp, 0.dp, 0.dp)),
        color = Color(0xFF9D80D7)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 30.dp),
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth(),
                contentPadding = PaddingValues(vertical = 10.dp),
                state = rememberLazyListState()

            ) {
                itemsIndexed(items) { index, item ->
                    Text(
                        text = item,
                        fontSize = 16.sp,
                        fontFamily = semibold,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                closeSheet(index)
                            }
                            .background(
                                if (index == current) {
                                    TatarTheme.colors.backPrimary
                                } else {
                                    Color.Transparent
                                }
                            )
                            .padding(vertical = 7.dp)
                            .padding(horizontal = 30.dp),
                        color = Color.White
                    )
                }
            }
        }
    }
}

@Composable
fun Toolbar(title: String = "Дәресләр") {
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
                text = title,
                style = TextStyle(
                    fontSize = 24.sp,
                    fontFamily = semibold,
                    color = TatarTheme.colors.colorWhite
                )
            )
            Image(
                painter = painterResource(id = R.drawable.ic_toolbar_line),
                contentDescription = null,
                modifier = Modifier.padding(top = 5.dp)
            )
        }
        Image(
            painter = painterResource(id = R.drawable.ic_logo),
            contentDescription = null,
            modifier = Modifier
                .size(70.dp)
        )

    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchField(
    searchState: MutableState<Boolean>,
    viewModel: CoursesViewModel,
    searchList: MutableState<List<Course>>
) {
    val focusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current

    var text by rememberSaveable { mutableStateOf("") }

    Card(
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 10.dp)
            .padding(horizontal = 20.dp),
        colors = CardDefaults.cardColors(
            containerColor = TatarTheme.colors.colorWhite
        )
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(Color.White)
                    .padding(horizontal = 10.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Icon(
                    painter = painterResource(id = if (!searchState.value) R.drawable.ic_search else R.drawable.ic_arr_back),
                    contentDescription = null,
                    Modifier
                        .size(40.dp)
                        .clickable(
                            enabled = searchState.value,
                            interactionSource = MutableInteractionSource(),
                            indication = null
                        ) {
                            searchState.value = false
                            focusManager.clearFocus()
                        }
                        .padding(8.dp)
                )
                TextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.Transparent)
                        .offset(x = (-8).dp)
                        .focusRequester(focusRequester),
                    value = text,
                    onValueChange = {
                        text = it
                        searchList.value = listOf()
                        viewModel.findWithPrefix(text)
                    },
                    maxLines = 1,
                    textStyle = TextStyle(
                        fontFamily = semibold,
                        fontSize = 14.sp,
                    ),
                    interactionSource = remember { MutableInteractionSource() }
                        .also { interactionSource ->
                            LaunchedEffect(interactionSource) {
                                interactionSource.interactions.collect {
                                    if (it is PressInteraction.Release) {
                                        searchState.value = true
                                    }
                                }
                            }
                        },
                    placeholder = {
                        Text(
                            modifier = Modifier.fillMaxWidth(),
                            text = "Дәреснең исеме, авторы яки бүлеге",
                            style = TextStyle(
                                fontFamily = semibold,
                                fontSize = 13.sp,
                                color = TatarTheme.colors.colorGray
                            ),
                            textAlign = TextAlign.Start
                        )
                    },
                    colors = TextFieldDefaults.textFieldColors(
                        containerColor = TatarTheme.colors.colorWhite,
                        focusedIndicatorColor = Color.Transparent,
                        disabledIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                    ),
                )
            }
        }
    }

}

@Composable
fun BottomPart(openSheet: () -> Unit, title: String, recommendedCourses: List<Course>, moveToCourse: (String) -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(0.91f)
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_courses_bottom_back),
            contentDescription = null,
            modifier = Modifier.align(Alignment.BottomEnd)
        )

        Column {
            Box(modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    openSheet()
                }) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier

                        .padding(
                            horizontal = 20.dp,
                            vertical = 25.dp
                        ),

                    ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_burger_menu),
                        contentDescription = null,
                        modifier = Modifier
                            .padding(end = 10.dp)
                            .size(30.dp),
                        tint = TatarTheme.colors.backSecondary
                    )
                    Text(
                        text = title,
                        style = TextStyle(
                            fontSize = 20.sp,
                            fontFamily = semibold,
                            color = TatarTheme.colors.colorWhite
                        ),
                        modifier = Modifier,
                    )

                }
                Icon(
                    painter = painterResource(id = R.drawable.ic_arr_menu),
                    contentDescription = null,
                    modifier = Modifier
                        .align(Alignment.CenterEnd)
                        .padding(end = 10.dp)
                        .size(24.dp),
                    tint = TatarTheme.colors.backSecondary,

                    )
            }

            LazyVerticalGrid(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
                columns = GridCells.Fixed(2),
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                verticalArrangement = Arrangement.spacedBy(20.dp),
                contentPadding = PaddingValues(vertical = 2.dp)

            ) {
                items(recommendedCourses.size) {
                    SmallCourseItem(recommendedCourses[it], moveToCourse)
                }
            }

        }

    }
}

