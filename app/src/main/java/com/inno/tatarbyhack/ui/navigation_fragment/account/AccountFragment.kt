package com.inno.tatarbyhack.ui.navigation_fragment.account

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
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
import coil.compose.AsyncImage
import com.inno.tatarbyhack.App
import com.inno.tatarbyhack.R
import com.inno.tatarbyhack.domain.models.User
import com.inno.tatarbyhack.ui.theme.TatarByHackTheme
import com.inno.tatarbyhack.ui.theme.TatarTheme
import com.inno.tatarbyhack.ui.theme.bold
import com.inno.tatarbyhack.ui.theme.medium
import com.inno.tatarbyhack.ui.theme.semibold
import com.inno.tatarbyhack.utils.viewModelFactory

class AccountFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = ComposeView(requireContext()).apply {
        setContent {
            TatarByHackTheme {
                val viewModel = viewModel<AccountViewModel>(
                    factory = viewModelFactory {
                        AccountViewModel(
                            App.appModule.loginRepository
                        )
                    }
                )
                viewModel.getUserInfo()
                val user = viewModel.user.collectAsState()
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {

                    val window = (context as Activity).window
                    WindowCompat.getInsetsController(window, this).isAppearanceLightStatusBars =
                        true

                    //if in account
                    AccountPage(user)

                }
            }
        }
    }

}

@Composable
fun AccountPage(user: State<User>) {

    val scrollState = rememberScrollState()
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
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            ToolbarProfile()

            ProfileInformation(user)

            TableInfo()


        }
    }
}

@Composable
fun TableInfo() {
    Column(
        Modifier
            .fillMaxWidth()
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .background(Color(0x547C52C7))
        )
        BoxWithConstraints(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Box(
                modifier = Modifier
                    .width(maxWidth * 3 / 8)
                    .height(50.dp)
                    .padding(start = 20.dp)
            ) {
                Text(
                    text = "Мәктәп",
                    fontFamily = semibold,
                    fontSize = 18.sp,
                    color = TatarTheme.colors.colorBlue,
                    modifier = Modifier
                        .align(Alignment.CenterStart)
                )
            }
            Spacer(
                modifier = Modifier
                    .padding(start = maxWidth * 3 / 8)
                    .height(50.dp)
                    .width(1.dp)
                    .background(Color(0x547C52C7))
            )
            Box(
                modifier = Modifier
                    .padding(start = maxWidth * 3 / 8)
                    .width(maxWidth * 5 / 8)
                    .height(50.dp)
                    .padding(start = 30.dp)
            ) {
                Text(
                    text = "ОАО “IT - лицей”",
                    fontFamily = medium,
                    fontSize = 15.sp,
                    color = TatarTheme.colors.colorBlue,
                    modifier = Modifier
                        .align(Alignment.CenterStart)
                )
            }
        }
        ////////////////////////////////////////////////////////////////
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .background(Color(0x547C52C7))
        )
        BoxWithConstraints(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Box(
                modifier = Modifier
                    .width(maxWidth * 3 / 8)
                    .height(50.dp)
            ) {
                Text(
                    text = "Шәһәр",
                    fontFamily = semibold,
                    fontSize = 18.sp,
                    color = TatarTheme.colors.colorBlue,
                    modifier = Modifier
                        .align(Alignment.CenterStart)
                        .padding(start = 20.dp)
                )
            }
            Spacer(
                modifier = Modifier
                    .padding(start = maxWidth * 3 / 8)
                    .height(50.dp)
                    .width(1.dp)
                    .background(Color(0x547C52C7))
            )
            Box(
                modifier = Modifier
                    .padding(start = maxWidth * 3 / 8)
                    .width(maxWidth * 5 / 8)
                    .height(50.dp)
            ) {
                Text(
                    text = "Казан",
                    fontFamily = medium,
                    fontSize = 15.sp,
                    color = TatarTheme.colors.colorBlue,
                    modifier = Modifier
                        .align(Alignment.CenterStart)
                        .padding(start = 30.dp)
                )
            }
        }
        /////////////////////////////////////////////////////////////
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .background(Color(0x547C52C7))
        )
        BoxWithConstraints(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Box(
                modifier = Modifier
                    .width(maxWidth * 3 / 8)
                    .height(50.dp)
            ) {
                Text(
                    text = "Билге",
                    fontFamily = semibold,
                    fontSize = 18.sp,
                    color = TatarTheme.colors.colorBlue,
                    modifier = Modifier
                        .align(Alignment.CenterStart)
                        .padding(start = 20.dp)
                )
            }
            Spacer(
                modifier = Modifier
                    .padding(start = maxWidth * 3 / 8)
                    .height(50.dp)
                    .width(1.dp)
                    .background(Color(0x547C52C7))
            )
            Box(
                modifier = Modifier
                    .padding(start = maxWidth * 3 / 8)
                    .width(maxWidth * 5 / 8)
                    .height(50.dp)
            ) {
                Text(
                    text = "4.5",
                    fontFamily = medium,
                    fontSize = 15.sp,
                    color = TatarTheme.colors.colorBlue,
                    modifier = Modifier
                        .align(Alignment.CenterStart)
                        .padding(start = 30.dp)
                )
            }
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .background(Color(0x547C52C7))
        )
    }
}

@Composable
fun ProfileInformation(user: State<User>) {

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .padding(top = 25.dp, bottom = 35.dp),
    ) {
        AsyncImage(
            model = user.value.photoUrl,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .width(200.dp)
                .height(200.dp)
                .clip(CircleShape)
        )
        Column(
            Modifier
                .padding(top = 10.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = user.value.name,
                fontFamily = bold,
                fontSize = 27.sp,
                color = TatarTheme.colors.colorBlue,
                modifier = Modifier
                    .padding(top = 15.dp),
                textAlign = TextAlign.Center
            )

            Text(
                text = "11А сыйныф",
                fontFamily = medium,
                fontSize = 14.sp,
                color = TatarTheme.colors.labelPrimary,
                modifier = Modifier
                    .padding(top = 5.dp)
            )
        }

    }

}

@Composable
fun ToolbarProfile() {
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

