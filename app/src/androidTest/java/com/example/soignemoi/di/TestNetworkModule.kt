package com.example.soignemoi.di

import com.example.soignemoi.data.api.AuthInterceptor
import com.example.soignemoi.data.api.SessionManager
import com.example.soignemoi.data.api.SessionManagerTest
import com.example.soignemoi.data.api.SoigneMoiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object TestNetworkModule {

    const val MOCK_WEBSERVER_URL = "https://10.0.0.2:8888"
    const val MOCK_WEBSERVER_PORT = 8888

    @Provides
    @Singleton
    fun provideSessionManager(): SessionManager = SessionManagerTest()

    @Provides
    @Singleton
    fun provideSoigneMoiService(
        authInterceptor: AuthInterceptor
    ): SoigneMoiService = Retrofit.Builder()
        .baseUrl(MOCK_WEBSERVER_URL)
        .client(
            OkHttpClient.Builder()
            .addInterceptor(authInterceptor)
            .build())
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(SoigneMoiService::class.java)

}