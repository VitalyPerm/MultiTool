package ru.kvf.multitool.feature.navigation

sealed class RootScreen(val route: String) {
   data object SelectFeature : RootScreen("SelectFeature")
   data object Duck : RootScreen("Duck")
    data object Cat : RootScreen("Cat")
    data object Gallery : RootScreen("Gallery")
    data object Test: RootScreen("Test")
}