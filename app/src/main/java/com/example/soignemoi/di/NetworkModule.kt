package com.example.soignemoi.di

import com.example.soignemoi.data.network.SoigneMoiApiService
import com.example.soignemoi.data.repository.AuthRepository
import com.example.soignemoi.data.repository.AuthRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    const val BASE_URL = "http://10.0.4.105:9091/"

    @Provides
    @Singleton
    fun provideApi() = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(SoigneMoiApiService::class.java)

    @Provides
    @Singleton
    fun provideAuthRepository(
        api: SoigneMoiApiService
    ): AuthRepository =
        AuthRepositoryImpl(api = api)

}