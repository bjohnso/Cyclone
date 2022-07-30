package com.demo.touchwallet.ui.composable.seedphrase

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import com.demo.touchwallet.extensions.ConfigurationExtensions.heightPercentageDP

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SeedPhraseItemGrid(seedWords: List<String>, gridItemColor: Int) {
    val configuration = LocalConfiguration.current

    LazyVerticalGrid(
        columns = GridCells.Adaptive(minSize = 128.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp),
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        items(seedWords.size) {
            SeedPhraseItem(
                it,
                seedWords[it],
                gridItemColor,
                configuration.heightPercentageDP(5f)
            )
        }
    }
}