package com.inno.tatarbyhack.ui.navigation_fragment.vebinar

import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.view.WindowCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.inno.tatarbyhack.App
import com.inno.tatarbyhack.R
import com.inno.tatarbyhack.domain.models.Vebinar
import com.inno.tatarbyhack.ui.navigation_fragment.NavigationFragmentDirections
import com.inno.tatarbyhack.ui.theme.TatarByHackTheme
import com.inno.tatarbyhack.ui.theme.TatarTheme
import com.inno.tatarbyhack.ui.theme.bold
import com.inno.tatarbyhack.ui.theme.medium
import com.inno.tatarbyhack.ui.theme.semibold
import com.inno.tatarbyhack.utils.findTopNavController
import com.inno.tatarbyhack.utils.viewModelFactory

class VebinarFragment : Fragment() {

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

                    val viewModel = viewModel<VebinarsViewModel>(
                        factory = viewModelFactory {
                            VebinarsViewModel(
                                App.appModule.vebinarsRepository
                            )
                        }
                    )
                    viewModel.start()
                    val window = (context as Activity).window
                    WindowCompat.getInsetsController(window, this).isAppearanceLightStatusBars =
                        true

                    VebinarPage(viewModel) { url: String, title: String, subTitle:String ->
                        val action = NavigationFragmentDirections.actionPlayer()
                        action.url = url
                        action.title = title
                        action.subtitle = subTitle
                        findTopNavController().navigate(action)
                    }

                }
            }
        }
    }

}

@Composable
fun VebinarPage(viewModel: VebinarsViewModel, watchVebinar: (String, String, String) -> Unit) {


    val scrollState = rememberScrollState()

    val futureVebinars = viewModel.futureVebinars.collectAsState()
    val pastVebinars = viewModel.pastVebinars.collectAsState()


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
                .padding(top = 24.dp)
                //.padding(bottom = 100.dp)
            ,
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            ToolbarVebinar()

            FutureVebinars(futureVebinars)

            PreviousVebinars(watchVebinar, pastVebinars)

        }
    }
}

@Composable
fun FutureVebinars(futureVebinars: State<List<Vebinar>>) {

    Column(
        Modifier
    ) {

        Text(
            text = "Булачак вебинарлар",
            style = TextStyle(
                fontSize = 19.sp,
                fontFamily = bold,
                color = TatarTheme.colors.labelPrimary
            ),
            modifier = Modifier
                .padding(start = 20.dp)
        )

        LazyRow(
            Modifier
                .padding(top = 20.dp)
                .height(274.dp)
                .fillMaxWidth()
        ) {
            item {
                Spacer(modifier = Modifier.width(20.dp))
            }
            itemsIndexed(futureVebinars.value) { index, item ->
                FutureVebinar(item)
                Spacer(modifier = Modifier.width(20.dp))
            }
        }
    }
}

@Composable
fun FutureVebinar(item: Vebinar) {
    Box(
        modifier = Modifier
            .height(274.dp)
            .aspectRatio(1.2f)
            .border(
                width = 2.dp,
                color = TatarTheme.colors.backPrimary,
                shape = RoundedCornerShape(15.dp)
            )
            .clip(
                shape = RoundedCornerShape(15.dp)
            )
            .clickable {

            }
    ) {
        Box(
            modifier = Modifier
                .height(274.dp)
                .width(224.dp)
                .clip(
                    RoundedCornerShape(
                        topStart = 15.dp,
                        bottomStart = 15.dp
                    )
                )
        )
        {
            AsyncImage(
                model = item.previewImageUrl,
                contentDescription = null,
                modifier = Modifier
                    .fillMaxSize(),
                contentScale = ContentScale.Crop
            )
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                Color(0x00D9D9D9),
                                Color(0xB00E0025)
                            )
                        )
                    )
            )
            Row(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
                    .padding(bottom = 20.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "21.09",
                    style = TextStyle(
                        fontSize = 22.sp,
                        fontFamily = semibold,
                        color = TatarTheme.colors.colorWhite
                    )
                )

                Text(
                    text = "18:00",
                    style = TextStyle(
                        fontSize = 22.sp,
                        fontFamily = semibold,
                        color = TatarTheme.colors.colorWhite
                    )
                )
            }
        }
        Box(
            modifier = Modifier
                .padding(start = 224.dp)
        ) {
            Column(
                Modifier
                    .fillMaxSize()
                    .padding(horizontal = 8.dp, vertical = 15.dp)
            ) {
                Text(
                    text = item.name,
                    style = TextStyle(
                        fontSize = 14.sp,
                        fontFamily = semibold,
                        color = TatarTheme.colors.labelPrimary
                    ),
                    modifier = Modifier
                )

                Spacer(
                    modifier = Modifier
                        .padding(vertical = 10.dp)
                        .height(1.5.dp)
                        .width(20.dp)
                        .background(TatarTheme.colors.colorOrange)
                )

                Text(
                    text = item.authorName,
                    style = TextStyle(
                        fontSize = 14.sp,
                        fontFamily = medium,
                        color = TatarTheme.colors.labelPrimary
                    ),
                    modifier = Modifier
                )
            }
            Image(
                painter = painterResource(id = R.drawable.ic_reminder),
                contentDescription = null,
                Modifier
                    .padding(12.dp)
                    .size(40.dp)
                    .align(Alignment.BottomEnd)
                    .clip(CircleShape)
                    .clickable {

                    }
            )
        }

    }
}


@Composable
fun PreviousVebinars(watchVebinar: (String, String, String) -> Unit, pastVebinars: State<List<Vebinar>>) {
    Column(
        Modifier
            .padding(top = 30.dp)
            .height(250.dp)
    ) {

        Text(
            text = "Үткән вебинарлар",
            style = TextStyle(
                fontSize = 19.sp,
                fontFamily = bold,
                color = TatarTheme.colors.labelPrimary
            ),
            modifier = Modifier
                .padding(start = 20.dp)
        )

        LazyRow(
            Modifier
                .padding(top = 20.dp)
                .fillMaxWidth()
        ) {
            item {
                Spacer(modifier = Modifier.width(20.dp))
            }
            itemsIndexed(pastVebinars.value) { index, item ->
                PreviousVebinar(watchVebinar, item)
                Spacer(modifier = Modifier.width(20.dp))
            }
        }
    }
}

@Composable
fun PreviousVebinar(watchVebinar: (String, String, String) -> Unit, item: Vebinar) {
    Box(
        modifier = Modifier
            .width(250.dp)
            .aspectRatio(2.5f)
            .border(
                width = 2.dp,
                color = TatarTheme.colors.backPrimary,
                shape = RoundedCornerShape(12.dp)
            )
            .clip(
                RoundedCornerShape(12.dp)
            )
            .clickable {
                watchVebinar(item.videoUrl, item.name, item.authorName)
            }
    ) {
        Box(
            modifier = Modifier
                .width(125.dp)
                .fillMaxHeight()
                .clip(
                    RoundedCornerShape(
                        topStart = 12.dp,
                        bottomStart = 12.dp
                    )
                )
        ) {
            AsyncImage(
                model = item.previewImageUrl,
                contentDescription = null,
                modifier = Modifier
                    .fillMaxSize(),
                contentScale = ContentScale.Crop
            )

        }
        Box(
            modifier = Modifier
                .padding(start = 125.dp)
        ) {
            Column(
                Modifier
                    .fillMaxSize()
                    .padding(horizontal = 10.dp, vertical = 15.dp)
            ) {
                Text(
                    text = item.name,
                    style = TextStyle(
                        fontSize = 12.sp,
                        fontFamily = semibold,
                        color = TatarTheme.colors.labelPrimary
                    ),
                    modifier = Modifier,
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(
                    modifier = Modifier
                        .padding(vertical = 4.dp)
                        .height(1.5.dp)
                        .width(20.dp)
                        .background(TatarTheme.colors.colorOrange)
                )

                Text(
                    text = item.authorName,
                    style = TextStyle(
                        fontSize = 10.sp,
                        fontFamily = medium,
                        color = TatarTheme.colors.labelPrimary
                    ),
                    modifier = Modifier,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }

    }
}

@Composable
fun ToolbarVebinar() {
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
                text = "Вебинар",
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


