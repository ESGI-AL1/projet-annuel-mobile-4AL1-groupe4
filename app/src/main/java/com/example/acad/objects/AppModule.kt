package com.example.acad.objects

import android.content.Context
import com.example.acad.data.ProgramData
import com.example.acad.data.UserData
import com.example.acad.repositories.AuthRepository
import com.example.acad.repositories.DataStoreRepository
import com.example.acad.repositories.FriendRepository
import com.example.acad.repositories.GroupRepository
import com.example.acad.repositories.NotificationRepository
import com.example.acad.repositories.ProgramRepository
import com.example.acad.repositories.QuestionRepository
import com.example.acad.services.AuthService
import com.example.acad.services.FriendService
import com.example.acad.services.GroupService
import com.example.acad.services.NotificationService
import com.example.acad.services.ProgramService
import com.example.acad.services.QuestionService
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

    @Provides
    @Singleton
    fun provideUserData(): UserData = UserData()

    @Provides
    @Singleton
    fun provideGroupRepository(service: GroupService) = GroupRepository(service)

    @Provides
    @Singleton
    fun provideGroupService(): GroupService = Retrofit.getRetrofit().create(GroupService::class.java)

    @Provides
    @Singleton
    fun provideQuestionRepository(service: QuestionService) = QuestionRepository(service)

    @Provides
    @Singleton
    fun provideQuestionService(): QuestionService = Retrofit.getRetrofit().create(QuestionService::class.java)

    @Provides
    @Singleton
    fun provideFriendService(): FriendService = Retrofit.getRetrofit().create(FriendService::class.java)

    @Provides
    @Singleton
    fun provideFriendRepository(service: FriendService) = FriendRepository(service)

    @Provides
    @Singleton
    fun provideNotificationService(): NotificationService = Retrofit.getRetrofit()
        .create(NotificationService::class.java)

    @Provides
    @Singleton
    fun provideNotificationRepository(service: NotificationService) = NotificationRepository(service)
}