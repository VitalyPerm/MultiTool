package ru.kvf.multitool.feature.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import ru.kvf.multitool.feature.cat.ui.CatUi
import ru.kvf.multitool.feature.gallery.galleryRouter
import ru.kvf.multitool.feature.select_feature.SelectFeatureUi

@Composable
fun Navigation(
    navController: NavHostController
) {
    NavHost(navController = navController, startDestination = RootScreen.SelectFeature.route) {
        composable(RootScreen.SelectFeature.route) {
            SelectFeatureUi(
                onClick = { screen ->
                    navController.navigate(screen.route)
                }
            )
        }

        composable(RootScreen.Duck.route) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Blue)
            )
        }

        composable(RootScreen.Cat.route) {
            CatUi()
        }

        galleryRouter(navController)
    }
}