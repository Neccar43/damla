package com.novacodestudios.network.di

import com.novacodestudios.network.api.*
import org.koin.dsl.module
import retrofit2.Retrofit

val networkModule = module {

    // Retrofit instance
    single { RetrofitInstance.retrofit }

    // API interfaces
    single { get<Retrofit>().create(AnswerApi::class.java) }
    single { get<Retrofit>().create(AppointmentApi::class.java) }
    single { get<Retrofit>().create(DonationApi::class.java) }
    single { get<Retrofit>().create(DonationCenterApi::class.java) }
    single { get<Retrofit>().create(DonorApi::class.java) }
    single { get<Retrofit>().create(NotificationApi::class.java) }
    single { get<Retrofit>().create(QuestionApi::class.java) }
}
