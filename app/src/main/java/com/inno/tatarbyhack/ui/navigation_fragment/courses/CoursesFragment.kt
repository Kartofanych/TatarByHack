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
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandIn
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkOut
import androidx.compose.animation.with
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
import com.inno.tatarbyhack.R
import com.inno.tatarbyhack.ui.navigation_fragment.compose_elements.CourseItem
import com.inno.tatarbyhack.ui.navigation_fragment.compose_elements.SearchCourseItem
import com.inno.tatarbyhack.ui.navigation_fragment.compose_elements.SmallCourseItem
import com.inno.tatarbyhack.ui.theme.TatarByHackTheme
import com.inno.tatarbyhack.ui.theme.TatarTheme
import com.inno.tatarbyhack.ui.theme.semibold
import kotlinx.coroutines.launch

class HomeFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = ComposeView(requireContext()).apply {
        setContent {
            TatarByHackTheme {

                // A surface container using the 'background' color from the theme
                CoursesPage(
                    this
                )
            }
        }
    }

}


@OptIn(ExperimentalMaterialApi::class, ExperimentalAnimationApi::class)
@Composable
fun CoursesPage(composeView: ComposeView) {
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


    val localContext = LocalContext.current
    val scope = rememberCoroutineScope()
    val window = (localContext as Activity).window


    val popularCourses = listOf("Телләрне өйрәнү", "IT", "ОГЭ/ЕГЭ", "Табигый фәннәр")
    val currPopularCourse = rememberSaveable { mutableStateOf(0) }

    val animatedBackground by animateColorAsState(targetValue = if(searchState.value) Color.White else Color(0xFF7C52C6))

    WindowCompat.getInsetsController(window, composeView).isAppearanceLightStatusBars = searchState.value


    ModalBottomSheetLayout(
        sheetState = sheetState,
        sheetContent = {
            BottomSheetContent(
                { i: Int ->
                    showModalSheet.value = !showModalSheet.value
                    scope.launch {
                        sheetState.hide()
                    }
                    currPopularCourse.value = i
                },
                items = popularCourses,
                current = currPopularCourse.value
            )
        },
        sheetBackgroundColor = Color.Transparent
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(animatedBackground)
            //MaterialTheme.colorScheme.background
        ) {
            AnimatedContent(targetState = searchState.value) {
                Image(
                    modifier = Modifier
                        .fillMaxSize(),
                    painter = painterResource(id = if(it) R.drawable.pattern_white_background else R.drawable.pattern_dark_background),
                    contentDescription = null,
                    contentScale = ContentScale.Crop
                )
            }
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 24.dp),
                verticalArrangement = Arrangement.SpaceBetween
            ) {

                AnimatedVisibility(
                    !searchState.value,
                    enter = expandIn(),
                    exit = shrinkOut()
                ) {
                    Toolbar(title = "Дәресләр")
                }

                SearchField(searchState)

                if(searchState.value) {
                    SearchItems()
                }


                if (!searchState.value) {

                    TopPart()

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
                        popularCourses[currPopularCourse.value]
                    )
                }
            }
        }

    }
}

@Composable
fun TopPart() {

    val items = listOf("1", "2", "3", "4")
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
            itemsIndexed(items) { index, item ->
                CourseItem()
            }
        }
    }
}

@Composable
fun SearchItems() {
    val items = listOf("1", "2", "3", "4")
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp),
        contentPadding = PaddingValues(vertical = 10.dp),
        state = rememberLazyListState(),
        verticalArrangement = Arrangement.spacedBy(10.dp)


    ) {
        itemsIndexed(items) { index, item ->
            SearchCourseItem()
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
        color = Color(0xFF1A1A47)
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

@OptIn(ExperimentalMaterial3Api::class, ExperimentalAnimationApi::class)
@Composable
fun SearchField(searchState: MutableState<Boolean>) {
    val focusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current

    var text by rememberSaveable { mutableStateOf("") }

    val backButtonSize
            by animateDpAsState(targetValue = if (searchState.value) 30.dp else 0.dp)

    val paddingTopSize
            by animateDpAsState(targetValue = if (searchState.value) 8.dp else 0.dp)
    val searchSize
            by animateDpAsState(targetValue = if (searchState.value) 0.dp else 24.dp)
    Card(
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = paddingTopSize)
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
                AnimatedContent(
                    searchState.value,
                    transitionSpec = {
                        fadeIn(animationSpec = tween(durationMillis = 500)) with
                                fadeOut(animationSpec = tween(durationMillis = 500))
                    }, label = ""
                ) { target ->
                    Icon(
                        painter = painterResource(id = if(!target) R.drawable.ic_search else R.drawable.ic_arr_back),
                        contentDescription = null,
                        Modifier
                            .size(40.dp)
                            .clickable(
                                enabled = target,
                                interactionSource = MutableInteractionSource(),
                                indication = null
                            ) {
                                searchState.value = false
                                focusManager.clearFocus()
                            }
                            .padding(8.dp)
                    )
                }
                TextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.Transparent)
                        .offset(x = (-8).dp)
                        .focusRequester(focusRequester),
                    value = text,
                    onValueChange = {
                        text = it
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
fun BottomPart(openSheet: () -> Unit, title: String) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(0.9f)
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_courses_bottom_back),
            contentDescription = null,
            modifier = Modifier.align(Alignment.BottomEnd)
        )

        Column {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        bottom = 10.dp
                    )
                    .clickable {
                        openSheet()
                    }
                    .padding(
                        horizontal = 20.dp,
                        vertical = 20.dp
                    )

            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_burger_menu),
                    contentDescription = null,
                    modifier = Modifier
                        .padding(end = 10.dp)
                        .size(30.dp)
                )
                Text(
                    text = title,
                    style = TextStyle(
                        fontSize = 20.sp,
                        fontFamily = semibold,
                        color = TatarTheme.colors.colorWhite
                    )
                )
            }

            val numbers = (0..4).toList()

            LazyVerticalGrid(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
                columns = GridCells.Fixed(2),
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                verticalArrangement = Arrangement.spacedBy(20.dp),
                contentPadding = PaddingValues(vertical = 2.dp)

            ) {
                items(numbers.size) {
                    SmallCourseItem()
                }
            }

        }

    }
}

