package com.novacodestudios.network.di

import com.novacodestudios.network.api.AnswerApi
import com.novacodestudios.network.api.AppointmentApi
import com.novacodestudios.network.api.DonationApi
import com.novacodestudios.network.api.DonationCenterApi
import com.novacodestudios.network.api.DonorApi
import com.novacodestudios.network.api.NotificationApi
import com.novacodestudios.network.api.QuestionApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit = RetrofitInstance.retrofit

    @Provides
    @Singleton
    fun provideAnswerApi(retrofit: Retrofit): AnswerApi =
        retrofit.create(AnswerApi::class.java)

    @Provides
    @Singleton
    fun provideAppointmentApi(retrofit: Retrofit): AppointmentApi =
        retrofit.create(AppointmentApi::class.java)

    @Provides
    @Singleton
    fun provideDonationApi(retrofit: Retrofit): DonationApi =
        retrofit.create(DonationApi::class.java)

    @Provides
    @Singleton
    fun provideDonationCenterApi(retrofit: Retrofit): DonationCenterApi =
        retrofit.create(DonationCenterApi::class.java)

    @Provides
    @Singleton
    fun provideDonorApi(retrofit: Retrofit): DonorApi =
        retrofit.create(DonorApi::class.java)

    @Provides
    @Singleton
    fun provideNotificationApi(retrofit: Retrofit): NotificationApi =
        retrofit.create(NotificationApi::class.java)

    @Provides
    @Singleton
    fun provideQuestionApi(retrofit: Retrofit): QuestionApi =
        retrofit.create(QuestionApi::class.java)
}