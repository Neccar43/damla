package com.novacodestudios.donation

import com.novacodestudios.donation.detail.DonationDetailViewModel
import com.novacodestudios.donation.list.DonationListViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module


val donationModule = module {
    viewModelOf(::DonationDetailViewModel)
    viewModelOf(::DonationListViewModel)

}