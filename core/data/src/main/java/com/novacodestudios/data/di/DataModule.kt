package com.novacodestudios.data.di

import com.novacodestudios.data.repository.*
import com.novacodestudios.network.api.*
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import org.koin.dsl.module

val dataModule = module {

    single<CoroutineDispatcher> { Dispatchers.IO }

    single { AnswerRepository(get(), get()) }
    single { AppointmentRepository(get(), get()) }
    single { DonationRepository(get(), get()) }
    single { DonationCenterRepository(get(), get()) }
    single { DonorRepository(get(), get()) }
    single { NotificationRepository(get(), get()) }
    single { QuestionRepository(get(), get()) }
}
