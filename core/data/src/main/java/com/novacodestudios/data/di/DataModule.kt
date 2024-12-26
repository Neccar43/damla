package com.novacodestudios.data.di

import com.novacodestudios.data.repository.*
import com.novacodestudios.network.api.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Provides
    @Singleton
    fun provideAnswerRepository(
        api: AnswerApi,
        dispatcher: CoroutineDispatcher
    ): AnswerRepository = AnswerRepository(api, dispatcher)

    @Provides
    @Singleton
    fun provideAppointmentRepository(
        api: AppointmentApi,
        dispatcher: CoroutineDispatcher
    ): AppointmentRepository = AppointmentRepository(api, dispatcher)

    @Provides
    @Singleton
    fun provideDonationRepository(
        api: DonationApi,
        dispatcher: CoroutineDispatcher
    ): DonationRepository = DonationRepository(api, dispatcher)

    @Provides
    @Singleton
    fun provideDonationCenterRepository(
        api: DonationCenterApi,
        dispatcher: CoroutineDispatcher
    ): DonationCenterRepository = DonationCenterRepository(api, dispatcher)

    @Provides
    @Singleton
    fun provideDonorRepository(
        api: DonorApi,
        dispatcher: CoroutineDispatcher
    ): DonorRepository = DonorRepository(api, dispatcher)

    @Provides
    @Singleton
    fun provideNotificationRepository(
        api: NotificationApi,
        dispatcher: CoroutineDispatcher
    ): NotificationRepository = NotificationRepository(api, dispatcher)

    @Provides
    @Singleton
    fun provideQuestionRepository(
        api: QuestionApi,
        dispatcher: CoroutineDispatcher
    ): QuestionRepository = QuestionRepository(api, dispatcher)

    @Provides
    fun provideDefaultDispatcher(): CoroutineDispatcher = Dispatchers.IO
}
