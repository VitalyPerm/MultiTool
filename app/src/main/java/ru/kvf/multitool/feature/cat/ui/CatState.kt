package ru.kvf.multitool.feature.cat.ui

import ru.kvf.multitool.feature.cat.domain.Cat

data class CatState(
    val cats: List<Cat>,
    val isLoading: Boolean
) {
    companion object {
        val initial = CatState(
            cats = emptyList(),
            isLoading = false
        )
    }
}
