package com.novacodestudios.donationcenter

import com.novacodestudios.donationcenter.detail.DonationCenterDetailViewModel
import com.novacodestudios.donationcenter.list.DonationCenterListViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val donationCenterModule = module {
    viewModelOf(::DonationCenterDetailViewModel)
    viewModelOf(::DonationCenterListViewModel)
}