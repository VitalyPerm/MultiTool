package ru.kvf.multitool.feature.select_feature

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.kvf.multitool.feature.navigation.Screen
import kotlin.random.Random

@Composable
fun SelectFeatureUi(
    onClick: (Screen) -> Unit
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalArrangement = Arrangement.Top,
        modifier = Modifier
            .fillMaxSize()
    ) {
        item {
            Cell(name = Screen.Cat.name) {
                onClick(Screen.Cat)
            }
        }

        item {
            Cell(name = Screen.Duck.name) {
                onClick(Screen.Duck)
            }
        }

        item {
            Cell(name = Screen.PickPhoto.name) {
                onClick(Screen.PickPhoto)
            }
        }
    }
}

@Composable
private fun Cell(
    name: String,
    onClick: () -> Unit
) {
    val color = remember {
        Color(
            Random.nextInt(256),
            Random.nextInt(256),
            Random.nextInt(256)
        )
    }
    Box(
        modifier = Modifier
            .background(color)
            .clickable(onClick = onClick)
            .aspectRatio(1f),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = name,
            fontSize = 32.sp,
            color = Color.Black,
            modifier = Modifier
                .padding(32.dp)
        )
    }
}