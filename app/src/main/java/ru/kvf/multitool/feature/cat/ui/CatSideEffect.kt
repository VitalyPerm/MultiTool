package ru.kvf.multitool.feature.cat.ui

sealed interface CatSideEffect {
    data class Toast(val text: String): CatSideEffect
}