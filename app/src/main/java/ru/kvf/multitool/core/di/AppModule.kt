package ru.kvf.multitool.core.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.kvf.multitool.feature.cat.data.CatApi
import ru.kvf.multitool.feature.cat.data.CatRepositoryImpl
import ru.kvf.multitool.feature.cat.domain.CatRepository

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun provideCatApi(): CatApi = Retrofit.Builder()
        .baseUrl("https://api.thecatapi.com/v1/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(CatApi::class.java)

    @Provides
    fun provideCatRepository(api: CatApi): CatRepository = CatRepositoryImpl(api)
}