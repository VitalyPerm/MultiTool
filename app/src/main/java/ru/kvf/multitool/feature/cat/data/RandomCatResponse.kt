package ru.kvf.multitool.feature.cat.data

import ru.kvf.multitool.feature.cat.domain.Cat

data class RandomCatResponse(
    val height: Int,
    val id: String,
    val url: String,
    val width: Int
) {
    fun toDomain() = Cat(url)
}