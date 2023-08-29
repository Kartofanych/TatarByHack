package com.inno.tatarbyhack.ui.navigation_fragment.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.expandIn
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleOut
import androidx.compose.animation.shrinkOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
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
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.inno.tatarbyhack.R
import com.inno.tatarbyhack.ui.theme.TatarByHackTheme
import com.inno.tatarbyhack.ui.theme.medium
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

                )
            }
        }
    }

}


@OptIn(ExperimentalMaterialApi::class)
@Preview
@Composable
fun CoursesPage() {
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

    val popularCourses = listOf("Телләрне өйрәнү", "IT", "ОГЭ/ЕГЭ", "Табигый фәннәр")
    val currPopularCourses = rememberSaveable {
        mutableStateOf(0)
    }

    ModalBottomSheetLayout(
        sheetState = sheetState,
        sheetContent = {
            BottomSheetContent(
                { i: Int ->
                    showModalSheet.value = !showModalSheet.value
                    scope.launch {
                        sheetState.hide()
                    }
                    currPopularCourses.value = i
                },
                items = popularCourses,
                current = currPopularCourses.value
            )
        },
        sheetBackgroundColor = Color.Transparent
    ) {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = Color(0xFF7C52C6)
            //MaterialTheme.colorScheme.background
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize(),
                verticalArrangement = Arrangement.SpaceBetween
            ) {

                AnimatedVisibility(
                    !searchState.value,
                    enter = expandIn(),
                    exit = shrinkOut()
                ) {
                    Toolbar(title = "Дәресләр")
                }

                SearchField {
                    searchState.value = true
                }

                AnimatedVisibility(
                    searchState.value,
                    enter = fadeIn(),
                    exit = fadeOut()
                ) {
                    SearchItems()
                }

                AnimatedVisibility(
                    !searchState.value,
                    enter = fadeIn()+ slideInVertically(),
                    exit = fadeOut()+ slideOutVertically()
                ) {
                    val items = (1..10).map { "Item $it" }

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
                        // LazyRow to display your items horizontally
                        LazyRow(
                            modifier = Modifier
                                .fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(10.dp),
                            contentPadding = PaddingValues(horizontal = 20.dp, vertical = 10.dp),
                            state = rememberLazyListState(),

                            ) {
                            itemsIndexed(items) { index, item ->
                                CourseItem()
                            }
                        }
                    }
                }
                AnimatedVisibility(
                    !searchState.value,
                    enter = fadeIn()+ slideInVertically(),
                    exit = fadeOut()+ slideOutVertically()
                ) {
                    Spacer(
                        modifier = Modifier
                            .height(0.7.dp)
                            .fillMaxWidth()
                            .background(Color(0x33FFFFFF))
                    )
                }
                AnimatedVisibility(
                    !searchState.value,
                    enter = fadeIn()+ slideInVertically(),
                    exit = fadeOut()+ slideOutVertically()
                ) {
                    BottomPart(
                        openSheet = {
                            showModalSheet.value = !showModalSheet.value
                            scope.launch {
                                sheetState.show()
                            }
                        },
                        popularCourses[currPopularCourses.value]
                    )
                }
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

        ) {
        itemsIndexed(items) { index, item ->
            SmallCourseItem()
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
                state = rememberLazyListState(),

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
                                    Color(0xFF7C52C7)
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
            .height(60.dp)
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
                    color = Color.White
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
fun SearchField(openSearch: () -> Unit) {
    var text by rememberSaveable { mutableStateOf("") }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
            .padding(horizontal = 20.dp)
            .clip(RoundedCornerShape(10.dp))
            .background(Color.White)
            .padding(horizontal = 10.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_search),
            contentDescription = null,
            Modifier.size(24.dp)
        )
        TextField(
            modifier = Modifier
                .fillMaxWidth(),
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
                                openSearch()
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
                        color = Color(0xFFC5C5C5)
                    )
                )
            },
            colors = TextFieldDefaults.textFieldColors(
                containerColor = Color.White,
                focusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
            )

        )
    }

}

@Composable
fun CourseItem(
    //courseItem
) {
    Box(
        modifier = Modifier
            .height(122.dp)
            .aspectRatio(2.016f)
            .clip(RoundedCornerShape(20.dp))
            .background(Color.White)
            .clickable {

            }
            .padding(start = 20.dp, top = 10.dp, bottom = 10.dp, end = 10.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .width(152.dp),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Python",
                    style = TextStyle(
                        fontSize = 17.sp,
                        fontFamily = semibold,
                        color = Color.Black
                    )
                )
                Image(
                    painter = painterResource(id = R.drawable.ic_line_purple),
                    contentDescription = null,
                    modifier = Modifier.padding(top = 3.dp, bottom = 5.dp),
                )
                Text(
                    modifier = Modifier
                        .heightIn(min = 51.dp),
                    text = "Python да веб-кушымталар" +
                            "һәм нейросетилар ясыйлар," +
                            "фәнни исәпләүләр үткәрәләр.",
                    style = TextStyle(
                        fontSize = 12.sp,
                        fontFamily = semibold,
                        color = Color(0xFFA7A7A7)
                    ),
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis

                )
                Row(
                    verticalAlignment = Alignment.CenterVertically,

                    ) {
                    Text(
                        text = "Айнур Г.",
                        style = TextStyle(
                            fontSize = 11.sp,
                            fontFamily = semibold,
                            color = Color(0xFF0E0025)
                        )
                    )
                    Box(
                        modifier = Modifier
                            .padding(horizontal = 5.dp)
                            .width(0.7.dp)
                            .height(15.dp)
                            .background(Color(0x334F0E77))
                    )
                    Text(
                        text = "12 дәрес",
                        style = TextStyle(
                            fontSize = 11.sp,
                            fontFamily = semibold,
                            color = Color(0xFF0E0025)
                        )
                    )
                }
            }
            Box(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_course_ex),
                    contentDescription = null,
                    modifier = Modifier
                        .size(50.dp)
                        .align(Alignment.TopEnd),
                )

                Image(
                    painter = painterResource(id = R.drawable.ic_favourite),
                    contentDescription = null,
                    modifier = Modifier
                        .size(30.dp)
                        .align(Alignment.BottomEnd),
                )
            }
        }
    }
}

@Preview
@Composable
fun SmallCourseItem(
    //courseItem
) {
    Box(
        modifier = Modifier
            .height(72.dp)
            .aspectRatio(2.208f)
            .clip(RoundedCornerShape(14.dp))
            .background(Color.White)
            .clickable {

            }
            .padding(horizontal = 10.dp, vertical = 10.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .width(92.dp),
                verticalArrangement = Arrangement.SpaceEvenly
            ) {
                Box {
                    Text(
                        modifier = Modifier
                            .padding(bottom = 4.dp),
                        text = "Python",
                        style = TextStyle(
                            fontSize = 14.sp,
                            fontFamily = semibold,
                            color = Color.Black
                        ),

                        )
                    Spacer(
                        modifier = Modifier
                            .height(1.dp)
                            .width(20.dp)
                            .background(Color(0xFF4F0E77))
                            .align(Alignment.BottomStart),

                        )
                }

//                Text(
//                    text = "Айнур Г.",
//                    style = TextStyle(
//                        fontSize = 11.sp,
//                        fontFamily = medium,
//                        color = Color(0xFF0E0025)
//                    )
//                )
                Text(
                    text = "12 дәрес",
                    style = TextStyle(
                        fontSize = 11.sp,
                        fontFamily = semibold,
                        color = Color(0xFF0E0025)
                    )
                )
            }
            Box(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_course_ex),
                    contentDescription = null,
                    modifier = Modifier
                        .size(37.dp)
                        .align(Alignment.Center),
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
                        color = Color.White
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

