package ru.kvf.multitool.feature.gallery

sealed class GalleryScreen(val route: String) {
    data object List : GalleryScreen("gallery_list")
    data object Details: GalleryScreen("gallery_details/{photo_uri}") {
        fun createRoute(uri: String) = "gallery_details/$uri"
    }
}
