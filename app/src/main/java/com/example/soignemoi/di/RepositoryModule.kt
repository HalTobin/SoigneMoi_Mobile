package com.example.soignemoi.di

import com.example.soignemoi.data.api.SoigneMoiService
import com.example.soignemoi.data.repository.MedicineRepository
import com.example.soignemoi.data.repository.MedicineRepositoryImpl
import com.example.soignemoi.data.repository.PatientRepository
import com.example.soignemoi.data.repository.PatientRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun providePatientRepository(
        api: SoigneMoiService
    ): PatientRepository = PatientRepositoryImpl(api)

    @Provides
    @Singleton
    fun provideMedicineRepository(
        api: SoigneMoiService
    ): MedicineRepository = MedicineRepositoryImpl(api)

}