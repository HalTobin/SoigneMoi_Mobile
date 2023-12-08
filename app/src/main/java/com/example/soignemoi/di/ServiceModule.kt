package com.example.soignemoi.di

import android.app.Application
import android.content.SharedPreferences
import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import androidx.preference.PreferenceManager
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import androidx.security.crypto.MasterKeys
import com.example.soignemoi.data.repository.AuthRepository
import com.example.soignemoi.data.service.AuthService
import com.example.soignemoi.data.service.AuthServiceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Named
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object ServiceModule {

    private const val PREFS_FILE_NAME = "secure_prefs"

    @Provides
    @Singleton
    @Named("shared_preferences")
    fun provideSharedPreference(
        app: Application
    ): SharedPreferences =
        PreferenceManager.getDefaultSharedPreferences(app)

    @Provides
    @Singleton
    @Named("secured_shared_preferences")
    fun provideSecureSharedPreference(
        app: Application
    ): SharedPreferences {
        val masterKey = MasterKey.Builder(app)
            .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
            .build()

        return EncryptedSharedPreferences.create(app,
            "secret_shared_prefs",
            masterKey,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
    }

    @Provides
    @Singleton
    fun provideAuthService(
        authRepository: AuthRepository,
        @Named("shared_preferences") sp: SharedPreferences,
        @Named("secured_shared_preferences") securedSp: SharedPreferences
    ): AuthService =
        AuthServiceImpl(
            authRepository = authRepository,
            sp = sp,
            securedSp = securedSp
        )

}