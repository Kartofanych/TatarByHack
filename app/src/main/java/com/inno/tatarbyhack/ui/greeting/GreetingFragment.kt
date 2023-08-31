package com.inno.tatarbyhack.ui.greeting

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.inno.tatarbyhack.R
import com.inno.tatarbyhack.ui.theme.TatarByHackTheme
import com.inno.tatarbyhack.ui.theme.TatarTheme
import com.inno.tatarbyhack.ui.theme.bold
import com.inno.tatarbyhack.ui.theme.medium

class GreetingFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        return ComposeView(requireContext()).apply {
            setContent {
                TatarByHackTheme {
                    // A surface container using the 'background' color from the theme
                    Surface(
                        modifier = Modifier.fillMaxSize(),
                        color = MaterialTheme.colorScheme.background
                    ) {
                        GreetingPage {
                            val action = GreetingFragmentDirections.actionStart()
                            findNavController().navigate(action)
                        }
                    }
                }
            }
        }
    }

}

@Composable
fun GreetingPage(start: () -> Unit) {

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(TatarTheme.colors.colorWhite)
            .padding(top = 24.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_logo_greeting),
                contentDescription = null,
                modifier = Modifier
                    .padding(start = 20.dp)
            )

            Image(
                painter = painterResource(id = R.drawable.ic_back_greeting),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1.49f)
            )

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 30.dp)
            ) {
                Column {
                    Text(
                        text = "Без белем алырга ярдәм итәбез",
                        fontFamily = bold,
                        fontSize = 32.sp,
                        color = TatarTheme.colors.labelPrimary,
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally)
                    )
                    Spacer(
                        modifier = Modifier
                            .padding(vertical = 20.dp)
                            .width(150.dp)
                            .height(2.dp)
                            .background(TatarTheme.colors.backPrimary)
                    )
                    Text(
                        text = "Дәресләрне өйрәнегез һәм үзегез төзегез!",
                        fontFamily = medium,
                        fontSize = 20.sp,
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally)
                    )
                }
            }

            OutlinedButton(
                modifier = Modifier
                    .padding(bottom = 100.dp)
                    .align(Alignment.CenterHorizontally)
                    .width(200.dp)
                    .aspectRatio(3.724f),
                onClick = {
                    start()
                },
                shape = RoundedCornerShape(18.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = TatarTheme.colors.backPrimary,
                ),
                border = BorderStroke(1.dp, TatarTheme.colors.backSecondary),
            ) {
                Text(
                    text = "Башлыйбыз",
                    fontFamily = bold,
                    fontSize = 20.sp,
                    )
            }


        }
    }

}


@Preview
@Composable
fun GreetingPreview() {
    TatarByHackTheme(
        darkTheme = false
    ) {
        // A surface container using the 'background' color from the theme
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            GreetingPage {

            }
        }
    }
}
