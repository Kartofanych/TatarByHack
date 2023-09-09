package com.inno.tatarbyhack.ui.navigation_fragment.homework

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Space
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.view.WindowCompat
import androidx.fragment.app.Fragment
import com.inno.tatarbyhack.R
import com.inno.tatarbyhack.domain.models.Course
import com.inno.tatarbyhack.ui.navigation_fragment.account.AccountPage
import com.inno.tatarbyhack.ui.navigation_fragment.account.ToolbarProfile
import com.inno.tatarbyhack.ui.navigation_fragment.compose_elements.SearchCourseItem
import com.inno.tatarbyhack.ui.theme.TatarByHackTheme
import com.inno.tatarbyhack.ui.theme.TatarTheme
import com.inno.tatarbyhack.ui.theme.semibold

class HomeworkFragment:Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = ComposeView(requireContext()).apply {
        setContent {
            TatarByHackTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {

                    val window = (context as Activity).window
                    WindowCompat.getInsetsController(window, this).isAppearanceLightStatusBars =
                        true

                    HomeworkPage()

                }
            }
        }
    }

}

@Composable
fun HomeworkPage() {

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
            ToolbarHomework()

            ListHomeworks()
        }
    }
}

@Composable
fun ToolbarHomework() {
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
                text = "Биремнәр",
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
fun ListHomeworks() {
    val items = arrayOf(1,2,3,4,5)
    Column(
        Modifier
            .fillMaxWidth()
            .padding(top = 20.dp)
            .padding(horizontal = 20.dp)
    ) {
        for (i in items){
            SearchCourseItem(course = Course(), moveToCourse = {})
            Spacer(modifier = Modifier.height(10.dp))
        }
    }
}

@Composable
fun HomeworkElement(){

}


