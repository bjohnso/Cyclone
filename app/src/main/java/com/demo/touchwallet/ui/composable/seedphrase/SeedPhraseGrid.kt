package com.demo.touchwallet.ui.composable.seedphrase

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SeedPhraseItemGrid(gridItemColor: Int) {
    LazyVerticalGrid(
        cells = GridCells.Adaptive(minSize = 128.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp),
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        items(12) {
            SeedPhraseItem(it, gridItemColor)
        }
    }
}

@Composable
fun SeedPhraseItem(index: Int, itemColor: Int) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .aspectRatio(4f),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ){
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .width(50.dp)
                .fillMaxHeight()
                .clip(
                    shape = RoundedCornerShape(
                        topStart = 30.dp,
                        bottomStart = 30.dp,
                        topEnd = 0.dp,
                        bottomEnd = 0.dp,
                    )
                )
                .border(
                    width = 1.dp,
                    color = Color.White,
                    shape = RoundedCornerShape(
                        topStart = 30.dp,
                        bottomStart = 30.dp,
                        topEnd = 0.dp,
                        bottomEnd = 0.dp,
                    )
                )
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            Color(itemColor),
                            Color(itemColor)
                        )
                    )
                ),
        ) {
            Text(
                text = "${index + 1}",
                textAlign = TextAlign.Start,
                style = TextStyle(fontSize = 18.sp, color = Color.White),
            )
        }

        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .clip(
                    shape = RoundedCornerShape(
                        topStart = 0.dp,
                        bottomStart = 0.dp,
                        topEnd = 30.dp,
                        bottomEnd = 30.dp,
                    )
                )
                .border(
                    width = 1.dp,
                    color = Color.White,
                    shape = RoundedCornerShape(
                        topStart = 0.dp,
                        bottomStart = 0.dp,
                        topEnd = 30.dp,
                        bottomEnd = 30.dp,
                    )
                )
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            Color(itemColor),
                            Color(itemColor)
                        )
                    )
                ),
        ) {
            Text(
                text = "Secret",
                textAlign = TextAlign.Start,
                style = TextStyle(fontSize = 18.sp, color = Color.White),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 10.dp)
            )
        }
    }
}