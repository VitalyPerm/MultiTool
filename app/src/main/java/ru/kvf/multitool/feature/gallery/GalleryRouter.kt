package ru.kvf.multitool.feature.gallery

import android.net.Uri
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.google.gson.Gson
import ru.kvf.multitool.core.App
import ru.kvf.multitool.feature.gallery.photo_details.PhotoDetailsUi
import ru.kvf.multitool.feature.gallery.photos_list.ui.PhotosListUi
import ru.kvf.multitool.feature.navigation.RootScreen

fun NavGraphBuilder.galleryRouter(
    navController: NavHostController
) {
    navigation(GalleryScreen.List.route, RootScreen.Gallery.route) {
        composable(GalleryScreen.List.route) {
            PhotosListUi(
                onClickPhoto = {
                    App.print("uri_str - ${it.toString()}")
                    navController.navigate(GalleryScreen.Details.createRoute(it.toString().replace('/','.')))
                }
            )
        }
        composable(
            GalleryScreen.Details.route,
            arguments = listOf(navArgument("photo_uri") { type = NavType.StringType })
        ) {
            val arg = it.arguments?.getString("photo_uri")?.replace('.','/')
            App.print("arg - $arg")
            PhotoDetailsUi(uri = Uri.parse(arg))
        }
    }
}