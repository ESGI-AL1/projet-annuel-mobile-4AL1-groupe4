package com.example.acad.objects

import android.content.Context
import com.example.acad.repositories.AuthRepository
import com.example.acad.repositories.DataStoreRepository
import com.example.acad.repositories.ProgramRepository
import com.example.acad.services.AuthService
import com.example.acad.services.ProgramService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun providerAuthRepository(service: AuthService) = AuthRepository(service)

    @Provides
    @Singleton
    fun provideAuthService(): AuthService = Retrofit.getRetrofit().create(AuthService::class.java)

    @Provides
    @Singleton
    fun providerDataStoreRepository(@ApplicationContext context: Context) =
        DataStoreRepository(context)

    @Provides
    @Singleton
    fun provideProgramService(): ProgramService =
        Retrofit.getRetrofit().create(ProgramService::class.java)

    @Provides
    @Singleton
    fun provideProgramRepository(service: ProgramService) = ProgramRepository(service)

}