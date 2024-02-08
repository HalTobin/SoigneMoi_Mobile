package com.example.soignemoi.di

import com.example.soignemoi.data.repository.MedicineRepository
import com.example.soignemoi.data.repository.MedicineRepositoryTest
import com.example.soignemoi.data.repository.PatientRepository
import com.example.soignemoi.data.repository.PatientRepositoryTest
import com.example.soignemoi.data.repository.PrescriptionRepository
import com.example.soignemoi.data.repository.PrescriptionRepositoryTest
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object TestRepositoryModule {

    @Provides
    @Singleton
    fun providePatientRepository(): PatientRepository =
        PatientRepositoryTest()

    @Provides
    @Singleton
    fun provideMedicineRepository(): MedicineRepository =
        MedicineRepositoryTest()

    @Provides
    @Singleton
    fun providePrescriptionRepository(): PrescriptionRepository =
        PrescriptionRepositoryTest()

}