package ru.kvf.multitool.feature.navigation

sealed class Screen(val name: String) {
    object SelectFeature : Screen("SelectFeature")
    object Duck : Screen("Duck")
    object Cat : Screen("Cat")
    object PickPhoto : Screen("Pick photo")
}