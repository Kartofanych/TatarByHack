package com.inno.tatarbyhack.ui.navigation_fragment.account

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.view.WindowCompat
import androidx.fragment.app.Fragment
import com.inno.tatarbyhack.R
import com.inno.tatarbyhack.ui.navigation_fragment.compose_elements.MultiSelector
import com.inno.tatarbyhack.ui.theme.TatarByHackTheme
import com.inno.tatarbyhack.ui.theme.TatarTheme
import com.inno.tatarbyhack.ui.theme.bold
import com.inno.tatarbyhack.ui.theme.semibold

class AccountFragment : Fragment() {


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
                    val window = (context as Activity).window
                    WindowCompat.getInsetsController(window, this).isAppearanceLightStatusBars =
                        true
                    val currentLoginState = rememberSaveable { mutableStateOf("Керү") }
                    //if in account
                    AccountPage(currentLoginState)
                }
            }
        }
    }

}

@Composable
fun AccountPage(currentLoginState: MutableState<String>) {
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
                .padding(top = 24.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            ToolbarLogin()

            Image(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1.46f)
                    .padding(vertical = 20.dp),
                painter = painterResource(id = R.drawable.ic_login_back),
                contentDescription = null,
            )

            BottomLoginPart(currentLoginState)
        }
    }
}

@Composable
fun BottomLoginPart(currentLoginState: MutableState<String>) {
    val animatedLoginPartSize by animateDpAsState(targetValue = if (currentLoginState.value == "Керү") 320.dp else 440.dp)

    Box(
        Modifier.fillMaxWidth()
    ) {

        Box(
            modifier = Modifier
                .height(animatedLoginPartSize)
                .fillMaxWidth()
                .padding(top = 24.dp)
                .clip(RoundedCornerShape(topStart = 25.dp, topEnd = 25.dp))
                .background(TatarTheme.colors.backPrimary)
        ) {
            Image(
                modifier = Modifier
                    .fillMaxSize(),
                painter = painterResource(id = R.drawable.pattern_dark_background),
                contentDescription = null,
                contentScale = ContentScale.Crop
            )

            AnimatedVisibility(
                currentLoginState.value == "Керү",
                enter = slideInHorizontally(
                    animationSpec = tween(durationMillis = 250),
                    initialOffsetX = { -it / 3 - it },
                ),
                exit = slideOutHorizontally(
                    animationSpec = tween(durationMillis = 250),
                    targetOffsetX = { -it / 3 - it }),
                modifier = Modifier
                    .align(Alignment.Center),
            ) {
                LoginPart()
            }

            AnimatedVisibility(
                currentLoginState.value == "Теркәлү",
                enter = slideInHorizontally(
                    animationSpec = tween(durationMillis = 250),
                    initialOffsetX = { it / 3 + it },
                ),
                exit = slideOutHorizontally(
                    animationSpec = tween(durationMillis = 250),
                    targetOffsetX = { it / 3 + it }),
                modifier = Modifier
                    .align(Alignment.Center),
            ) {
                RegistrationPart()
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
                options = listOf("Керү", "Теркәлү"),
                selectedOption = currentLoginState.value,
                onOptionSelect = {
                    when (it) {
                        "Керү" -> currentLoginState.value = "Керү"
                        "Теркәлү" -> currentLoginState.value = "Теркәлү"
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


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginPart() {

    var textMail by rememberSaveable { mutableStateOf("") }
    var textPassword by rememberSaveable { mutableStateOf("") }


    Column(
        Modifier.padding(horizontal = 40.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
                .clip(RoundedCornerShape(14.dp))
                .background(TatarTheme.colors.backElevated)
                .padding(horizontal = 10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_mail),
                contentDescription = null,
                tint = TatarTheme.colors.colorWhite,
                modifier = Modifier
                    .size(25.dp)
            )
            TextField(
                modifier = Modifier
                    .fillMaxWidth(),
                value = textMail,
                onValueChange = {
                    textMail = it
                },
                maxLines = 1,
                textStyle = TextStyle(
                    fontFamily = semibold,
                    fontSize = 14.sp,
                    color = TatarTheme.colors.colorWhite
                ),
                interactionSource = remember { MutableInteractionSource() },
                placeholder = {
                    Text(
                        modifier = Modifier
                            .fillMaxWidth(),
                        text = "e-mail",
                        style = TextStyle(
                            fontFamily = semibold,
                            fontSize = 13.sp,
                            color = TatarTheme.colors.colorWhite
                        ),
                        textAlign = TextAlign.Start
                    )
                },
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                ),
            )
        }
        Spacer(modifier = Modifier.height(20.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
                .clip(RoundedCornerShape(14.dp))
                .background(TatarTheme.colors.backElevated)
                .padding(horizontal = 10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_password),
                contentDescription = null,
                tint = TatarTheme.colors.colorWhite,
                modifier = Modifier
                    .size(25.dp)
            )
            TextField(
                modifier = Modifier
                    .fillMaxWidth(),
                value = textPassword,
                onValueChange = {
                    textPassword = it
                },
                maxLines = 1,
                textStyle = TextStyle(
                    fontFamily = semibold,
                    fontSize = 14.sp,
                    color = TatarTheme.colors.colorWhite
                ),
                interactionSource = remember { MutableInteractionSource() },
                placeholder = {
                    Text(
                        modifier = Modifier
                            .fillMaxWidth(),
                        text = "пароль",
                        style = TextStyle(
                            fontFamily = semibold,
                            fontSize = 13.sp,
                            color = TatarTheme.colors.colorWhite
                        ),
                        textAlign = TextAlign.Start
                    )
                },
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                ),
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        Box(modifier = Modifier
            .height(35.dp)
            .width(100.dp)
            .clip(RoundedCornerShape(10.dp))
            .background(TatarTheme.colors.colorOrange)
            .clickable {

            }) {
            Text(
                modifier = Modifier
                    .align(Alignment.Center),
                text = "Керү",
                style = TextStyle(
                    fontFamily = bold,
                    fontSize = 13.sp,
                    color = TatarTheme.colors.colorWhite,
                    textAlign = TextAlign.Center
                ),
            )
        }

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegistrationPart() {

    var textMail by rememberSaveable { mutableStateOf("") }
    var textPassword by rememberSaveable { mutableStateOf("") }


    Column(
        Modifier
            .padding(horizontal = 40.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
                .clip(RoundedCornerShape(14.dp))
                .background(TatarTheme.colors.backElevated)
                .padding(horizontal = 10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_login),
                contentDescription = null,
                tint = TatarTheme.colors.colorWhite,
                modifier = Modifier
                    .size(30.dp)
            )
            TextField(
                modifier = Modifier
                    .fillMaxWidth(),
                value = textMail,
                onValueChange = {
                    textMail = it
                },
                maxLines = 1,
                textStyle = TextStyle(
                    fontFamily = semibold,
                    fontSize = 14.sp,
                    color = TatarTheme.colors.colorWhite
                ),
                interactionSource = remember { MutableInteractionSource() },
                placeholder = {
                    Text(
                        modifier = Modifier
                            .fillMaxWidth(),
                        text = "Фамилия Имя",
                        style = TextStyle(
                            fontFamily = semibold,
                            fontSize = 13.sp,
                            color = TatarTheme.colors.colorWhite
                        ),
                        textAlign = TextAlign.Start
                    )
                },
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                ),
            )
        }
        Spacer(modifier = Modifier.height(20.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
                .clip(RoundedCornerShape(14.dp))
                .background(TatarTheme.colors.backElevated)
                .padding(horizontal = 10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_login),
                contentDescription = null,
                tint = TatarTheme.colors.colorWhite,
                modifier = Modifier
                    .size(30.dp)
            )
            TextField(
                modifier = Modifier
                    .fillMaxWidth(),
                value = textMail,
                onValueChange = {
                    textMail = it
                },
                maxLines = 1,
                textStyle = TextStyle(
                    fontFamily = semibold,
                    fontSize = 14.sp,
                    color = TatarTheme.colors.colorWhite
                ),
                interactionSource = remember { MutableInteractionSource() },
                placeholder = {
                    Text(
                        modifier = Modifier
                            .fillMaxWidth(),
                        text = "Логин",
                        style = TextStyle(
                            fontFamily = semibold,
                            fontSize = 13.sp,
                            color = TatarTheme.colors.colorWhite
                        ),
                        textAlign = TextAlign.Start
                    )
                },
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                ),
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
                .clip(RoundedCornerShape(14.dp))
                .background(TatarTheme.colors.backElevated)
                .padding(horizontal = 10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_mail),
                contentDescription = null,
                tint = TatarTheme.colors.colorWhite,
                modifier = Modifier
                    .size(25.dp)
            )
            TextField(
                modifier = Modifier
                    .fillMaxWidth(),
                value = textMail,
                onValueChange = {
                    textMail = it
                },
                maxLines = 1,
                textStyle = TextStyle(
                    fontFamily = semibold,
                    fontSize = 14.sp,
                    color = TatarTheme.colors.colorWhite
                ),
                interactionSource = remember { MutableInteractionSource() },
                placeholder = {
                    Text(
                        modifier = Modifier
                            .fillMaxWidth(),
                        text = "e-mail",
                        style = TextStyle(
                            fontFamily = semibold,
                            fontSize = 13.sp,
                            color = TatarTheme.colors.colorWhite
                        ),
                        textAlign = TextAlign.Start
                    )
                },
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                ),
            )
        }
        Spacer(modifier = Modifier.height(20.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
                .clip(RoundedCornerShape(14.dp))
                .background(TatarTheme.colors.backElevated)
                .padding(horizontal = 10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_password),
                contentDescription = null,
                tint = TatarTheme.colors.colorWhite,
                modifier = Modifier
                    .size(25.dp)
            )
            TextField(
                modifier = Modifier
                    .fillMaxWidth(),
                value = textPassword,
                onValueChange = {
                    textPassword = it
                },
                maxLines = 1,
                textStyle = TextStyle(
                    fontFamily = semibold,
                    fontSize = 14.sp,
                    color = TatarTheme.colors.colorWhite
                ),
                interactionSource = remember { MutableInteractionSource() },
                placeholder = {
                    Text(
                        modifier = Modifier
                            .fillMaxWidth(),
                        text = "пароль",
                        style = TextStyle(
                            fontFamily = semibold,
                            fontSize = 13.sp,
                            color = TatarTheme.colors.colorWhite
                        ),
                        textAlign = TextAlign.Start
                    )
                },
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                ),
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        Box(modifier = Modifier
            .height(35.dp)
            .width(120.dp)
            .clip(RoundedCornerShape(10.dp))
            .background(TatarTheme.colors.colorOrange)
            .clickable {

            }) {
            Text(
                modifier = Modifier
                    .align(Alignment.Center),
                text = "Теркәлү",
                style = TextStyle(
                    fontFamily = bold,
                    fontSize = 13.sp,
                    color = TatarTheme.colors.colorWhite,
                    textAlign = TextAlign.Center
                ),
            )
        }
    }

}

@Composable
fun ToolbarLogin() {
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
                text = "Профиль",
                style = TextStyle(
                    fontSize = 24.sp,
                    fontFamily = semibold,
                    color = TatarTheme.colors.labelPrimary
                )
            )
            Image(
                painter = painterResource(id = R.drawable.ic_toolbar_line),
                contentDescription = null,
                modifier = Modifier.padding(top = 5.dp)
            )
        }
        Icon(
            painter = painterResource(id = R.drawable.ic_logo),
            contentDescription = null,
            modifier = Modifier
                .size(70.dp),
            tint = TatarTheme.colors.backSecondary
        )

    }
}

@Composable
fun BottomShadow(alpha: Float = 0.1f, height: Dp = 8.dp) {
    Box(
        modifier = Modifier
            .width(270.dp)

            .height(height)
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color.Black.copy(alpha = alpha),
                        Color.Transparent,
                    )
                )
            )
    )
}

@Preview
@Composable
fun AccountPagePreview() {
    TatarByHackTheme(
        darkTheme = false,
    ) {
        AccountPage(remember {
            mutableStateOf("Керү")
        }
        )

    }
}
