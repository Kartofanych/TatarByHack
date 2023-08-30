package com.inno.tatarbyhack.ui.navigation_fragment.compose_elements

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.AppBarDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import com.inno.tatarbyhack.R
import com.inno.tatarbyhack.ui.theme.TatarTheme
import com.inno.tatarbyhack.ui.theme.medium
import com.inno.tatarbyhack.ui.theme.semibold


@Composable
fun CourseItem(
    //courseItem
) {
    Box(
        modifier = Modifier
            .height(122.dp)
            .aspectRatio(2.016f)
            .clip(RoundedCornerShape(20.dp))
            .background(TatarTheme.colors.colorWhite)
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
                        color = TatarTheme.colors.labelPrimary
                    )
                )
                Box(
                    modifier = Modifier
                        .width(10.dp)
                        .height(1.dp),
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
                        color = TatarTheme.colors.colorGray
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
                            color = TatarTheme.colors.labelPrimary
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
                            color = TatarTheme.colors.labelPrimary
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
            .background(TatarTheme.colors.colorWhite)
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
                            color = TatarTheme.colors.labelPrimary
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
                        color = TatarTheme.colors.labelPrimary
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


@Preview
@Composable
fun SearchCourseItem(
    //courseItem
) {
    Card(
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp
        )
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(14.dp))
                .background(Color.White)
                .clickable {

                }
                .padding(horizontal = 10.dp, vertical = 10.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(
                    modifier = Modifier,
                    verticalArrangement = Arrangement.SpaceEvenly
                ) {
                    Box {
                        Text(
                            modifier = Modifier
                                .padding(bottom = 4.dp),
                            text = "Python",
                            style = TextStyle(
                                fontSize = 15.sp,
                                fontFamily = semibold,
                                color = TatarTheme.colors.labelPrimary
                            ),

                            )
                        Spacer(
                            modifier = Modifier
                                .height(1.dp)
                                .width(20.dp)
                                .background(TatarTheme.colors.backSecondary)
                                .align(Alignment.BottomStart),

                            )
                    }

                    Text(
                        text = "Галиев Ирек",
                        style = TextStyle(
                            fontSize = 13.sp,
                            fontFamily = medium,
                            color = TatarTheme.colors.labelPrimary
                        ),
                        modifier = Modifier
                            .padding(vertical = 5.dp)

                    )
                    Text(
                        text = "12 дәрес",
                        style = TextStyle(
                            fontSize = 11.sp,
                            fontFamily = semibold,
                            color = TatarTheme.colors.labelPrimary
                        )
                    )
                }
                Image(
                    painter = painterResource(id = R.drawable.ic_course_ex),
                    contentDescription = null,
                    modifier = Modifier
                        .size(50.dp)
                )
            }
        }
    }
}
