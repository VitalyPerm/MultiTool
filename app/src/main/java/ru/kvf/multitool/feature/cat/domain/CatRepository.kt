package ru.kvf.multitool.feature.cat.domain

interface CatRepository {

    suspend fun getCats(count: Int): List<Cat>
}