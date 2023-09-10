package com.inno.tatarbyhack.ui.login

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
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
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
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.view.WindowCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.viewmodel.compose.viewModel
import com.inno.tatarbyhack.App
import com.inno.tatarbyhack.R
import com.inno.tatarbyhack.ui.theme.TatarByHackTheme
import com.inno.tatarbyhack.ui.theme.TatarTheme
import com.inno.tatarbyhack.ui.theme.bold
import com.inno.tatarbyhack.ui.theme.semibold
import com.inno.tatarbyhack.utils.findTopNavController
import com.inno.tatarbyhack.utils.viewModelFactory


class LoginFragment : Fragment() {


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
                    val viewModel = viewModel<LoginViewModel>(
                        factory = viewModelFactory {
                            LoginViewModel(App.appModule.loginRepository)
                        }
                    )

                    val window = (context as Activity).window
                    WindowCompat.getInsetsController(window, this).isAppearanceLightStatusBars =
                        true
                    //if in account
                    LoginPage(viewModel) {
                        val action = LoginFragmentDirections.actionStart()
                        findTopNavController().navigate(action)
                    }
                }
            }
        }
    }

}

@Composable
fun LoginPage(viewModel: LoginViewModel, start: () -> Unit) {
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

            BottomLoginPart(start, viewModel)
        }
    }
}

@Composable
fun BottomLoginPart(start: () -> Unit, viewModel: LoginViewModel) {
    Box(
        Modifier.fillMaxWidth()
    ) {

        Box(
            modifier = Modifier
                .height(440.dp)
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

            Box(
                modifier = Modifier
                    .align(Alignment.Center),
            ) {
                LoginPart(start, viewModel)
            }

        }



        Card(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .clip(RoundedCornerShape(30.dp)),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 12.dp,
            ),
            colors = CardDefaults.cardColors(
                containerColor = Color(0xFF9D80D7)
            )
        ) {
            Row(
                modifier = Modifier
                    .width(200.dp)
                    .height(60.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_nav_main),
                    contentDescription = null,
                    modifier = Modifier
                        .padding(end = 8.dp)
                        .size(24.dp),
                    tint = TatarTheme.colors.backSecondary
                )
                Text(
                    modifier = Modifier,
                    text = "edu.tatar.ru",
                    style = TextStyle(
                        fontFamily = semibold,
                        fontSize = 17.sp,
                        color = TatarTheme.colors.backSecondary
                    ),
                    textAlign = TextAlign.Start
                )
            }
        }


    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginPart(start: () -> Unit, viewModel: LoginViewModel) {

    var textMail by rememberSaveable { mutableStateOf("") }
    var textPassword by rememberSaveable { mutableStateOf("") }

    val state = viewModel.loginState.collectAsState()


    Column(
        Modifier.padding(horizontal = 40.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (state.value == 0) {
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
                    painter = painterResource(id = R.drawable.ic_nav_account),
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
                            text = "логин",
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
                    visualTransformation = PasswordVisualTransformation(),
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
                    //reg
                    viewModel.login(textMail, textPassword)
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
        } else if (state.value == 1) {
            CircularProgressIndicator(
                color = TatarTheme.colors.colorWhite,
                strokeWidth = 4.dp,
                modifier = Modifier.size(50.dp)
            )
        } else {
            start()
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
                text = "Керү",
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


@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    TatarByHackTheme {
        Greeting("Android")
    }
}