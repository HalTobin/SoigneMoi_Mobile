package com.example.soignemoi.di

import android.content.SharedPreferences
import com.example.soignemoi.data.api.AuthInterceptor
import com.example.soignemoi.data.api.AuthService
import com.example.soignemoi.data.api.SessionManager
import com.example.soignemoi.data.api.SoigneMoiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    const val BASE_URL = "http://10.0.4.105:9091/"

    @Provides
    @Singleton
    fun provideAuthInterceptor(sessionManager: SessionManager): AuthInterceptor =
        AuthInterceptor(sessionManager)

    @Provides
    @Singleton
    fun provideAuthService(): AuthService = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(AuthService::class.java)

    @Provides
    @Singleton
    fun provideSoigneMoiService(
        authInterceptor: AuthInterceptor
    ): SoigneMoiService = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(OkHttpClient.Builder().addInterceptor(authInterceptor).build())
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(SoigneMoiService::class.java)

    @Provides
    @Singleton
    fun provideSessionManager(
        api: AuthService,
        @Named("shared_preferences") sp: SharedPreferences,
        @Named("secured_shared_preferences") securedSp: SharedPreferences
    ): SessionManager =
        SessionManager(
            api = api,
            sp = sp,
            securedSp = securedSp
        )

}