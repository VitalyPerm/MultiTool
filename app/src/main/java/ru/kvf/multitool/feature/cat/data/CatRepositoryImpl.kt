package ru.kvf.multitool.feature.cat.data

import ru.kvf.multitool.feature.cat.domain.CatRepository
import javax.inject.Inject

class CatRepositoryImpl @Inject constructor(
    private val api: CatApi
) : CatRepository {

    override suspend fun getCats(count: Int) = api.getCats(count).map { it.toDomain() }
}