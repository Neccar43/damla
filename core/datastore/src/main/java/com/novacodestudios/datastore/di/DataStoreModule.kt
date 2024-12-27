package com.novacodestudios.datastore.di

import android.app.Application
import android.content.Context
import com.novacodestudios.datastore.DonorPreferences
import com.novacodestudios.datastore.dataStore
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module


val dataStoreModule = module {
    single { androidContext().dataStore }
    single { DonorPreferences(get()) }
}