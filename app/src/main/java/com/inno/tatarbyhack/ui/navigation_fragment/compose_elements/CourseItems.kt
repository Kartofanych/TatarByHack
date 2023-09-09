package com.inno.tatarbyhack.ui.navigation_fragment.compose_elements

import android.net.Uri
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
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import com.inno.tatarbyhack.R
import com.inno.tatarbyhack.domain.models.Course
import com.inno.tatarbyhack.ui.theme.TatarTheme
import com.inno.tatarbyhack.ui.theme.medium
import com.inno.tatarbyhack.ui.theme.semibold


@Composable
fun CourseItem(course: Course, moveToCourse: (String) -> Unit) {
    Box(
        modifier = Modifier
            .height(122.dp)
            .aspectRatio(2.016f)
            .clip(RoundedCornerShape(20.dp))
            .background(TatarTheme.colors.colorWhite)
            .clickable {
                moveToCourse(course.id)
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
                    text = course.courseName,
                    style = TextStyle(
                        fontSize = 17.sp,
                        fontFamily = semibold,
                        color = TatarTheme.colors.labelPrimary
                    ),
                    maxLines = 2
                )
                Box(
                    modifier = Modifier
                        .width(10.dp)
                        .height(1.dp),
                )
                Text(
                    modifier = Modifier,
                    text = course.desc,
                    style = TextStyle(
                        fontSize = 12.sp,
                        fontFamily = semibold,
                        color = TatarTheme.colors.colorGray
                    ),
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis

                )
                Row(
                    verticalAlignment = Alignment.CenterVertically,

                    ) {
                    Text(
                        text = course.authorName.split(" ")[0] + " " + course.authorName.split(" ")[0].toCharArray()[0] + ".",
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
                        text = "${course.lessonsCounter} дәрес",
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
                    painter = rememberAsyncImagePainter(course.photoLink),
                    contentDescription = null,
                    modifier = Modifier
                        .clip(RoundedCornerShape(20.dp))
                        .size(50.dp)
                        .align(Alignment.TopEnd),
                    contentScale = ContentScale.Crop
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

@Composable
fun SmallCourseItem(course: Course, moveToCourse: (String) -> Unit) {
    Box(
        modifier = Modifier
            .height(72.dp)
            .aspectRatio(2.208f)
            .clip(RoundedCornerShape(14.dp))
            .background(TatarTheme.colors.colorWhite)
            .clickable {
                moveToCourse(course.id)
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
                        text = course.courseName,
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

                Text(
                    text = course.authorName.split(" ")[0] + " " + course.authorName.split(" ")[0].toCharArray()[0] + ".",
                    style = TextStyle(
                        fontSize = 11.sp,
                        fontFamily = medium,
                        color = Color(0xFF0E0025)
                    ),
                    maxLines = 1
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
            Box(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                Image(
                    painter = rememberAsyncImagePainter(course.photoLink),
                    contentDescription = null,
                    modifier = Modifier
                        .size(40.dp)
                        .align(Alignment.Center),
                )
            }
        }
    }
}


@Composable
fun SearchCourseItem(course: Course, moveToCourse: (String) -> Unit) {
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
                    moveToCourse(course.id)
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
                            text = course.courseName,
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
                        text = course.authorName,
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
                    painter = rememberAsyncImagePainter(course.photoLink),
                    contentDescription = null,
                    modifier = Modifier
                        .size(50.dp)
                )
            }
        }
    }
}

@Composable
fun MyCourseItem(course: Course, onClick: (String) -> Unit) {
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
                    onClick(course.id)
                }
                .padding(horizontal = 10.dp, vertical = 20.dp)
        ) {
            Row(
                modifier = Modifier,
                verticalAlignment = Alignment.CenterVertically
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
                        .padding(end = 20.dp)
                        .size(50.dp)
                        .clip(RoundedCornerShape(5.dp))
                )

                Column(
                    modifier = Modifier,
                    verticalArrangement = Arrangement.SpaceEvenly
                ) {
                    Box {
                        Text(
                            modifier = Modifier
                                .padding(bottom = 4.dp),
                            text = course.courseName,
                            style = TextStyle(
                                fontSize = 19.sp,
                                fontFamily = semibold,
                                color = TatarTheme.colors.labelPrimary
                            ),
                        )
                        Spacer(
                            modifier = Modifier
                                .height(2.dp)
                                .width(30.dp)
                                .background(TatarTheme.colors.backSecondary)
                                .align(Alignment.BottomStart),

                            )
                    }

                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "12 дәрес",
                            style = TextStyle(
                                fontSize = 13.sp,
                                fontFamily = medium,
                                color = Color(0xFFA7A7A7)
                            ),
                            modifier = Modifier
                                .padding(vertical = 5.dp)

                        )
                        Spacer(
                            modifier = Modifier
                                .padding(horizontal = 5.dp)
                                .height(20.dp)
                                .width(1.dp)
                                .background(TatarTheme.colors.colorRed),

                            )
                        Text(
                            text = "эштә",
                            style = TextStyle(
                                fontSize = 11.sp,
                                fontFamily = medium,
                                color = TatarTheme.colors.colorRed
                            )
                        )

                    }

                }

            }
            Image(
                painter = painterResource(id = R.drawable.ic_course_settings),
                contentDescription = null,
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .size(35.dp)
            )
        }
    }
}
