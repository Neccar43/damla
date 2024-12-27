package com.novacodestudios.damla

import android.app.Application
import com.novacodestudios.appointment.appointmentModule
import com.novacodestudios.auth.authModule
import com.novacodestudios.data.di.dataModule
import com.novacodestudios.datastore.di.dataStoreModule
import com.novacodestudios.donation.donationModule
import com.novacodestudios.donationcenter.donationCenterModule
import com.novacodestudios.home.homeModule
import com.novacodestudios.network.di.networkModule
import com.novacodestudios.notification.notificationModule
import com.novacodestudios.profileModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class DamlaApp : Application() {

    override fun onCreate() {
        super.onCreate()

        // sırası önemli
        startKoin {
            androidContext(this@DamlaApp)
            modules(
                networkModule,
                dataModule,
                dataStoreModule,
                authModule,
                appointmentModule,
                donationModule,
                donationCenterModule,
                homeModule,
                notificationModule,
                profileModule
            )
        }
    }
}