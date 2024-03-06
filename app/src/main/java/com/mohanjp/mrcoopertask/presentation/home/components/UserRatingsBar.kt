package com.mohanjp.mrcoopertask.presentation.home.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.mohanjp.mrcoopertask.R

@Composable
fun UserRatingsBar(
    modifier: Modifier,
    size: Dp = 64.dp,
    ratingState: MutableState<Int> = remember { mutableIntStateOf(0) },
    ratingIconPainter: Painter = painterResource(id = R.drawable.ic_star),
    selectedColor: Color = Color(0xFFFFD700),
    unselectedColor: Color = Color(0xFFA2ADB1)
) {

    Row(
        modifier = modifier.wrapContentSize()
    ) {
        (1..5).forEach { value ->
            StarIcon(
                size = size,
                ratingState = ratingState,
                painter = ratingIconPainter,
                ratingValue = value,
                selectedColor = selectedColor,
                unselectedColor = unselectedColor
            )
        }
    }
}