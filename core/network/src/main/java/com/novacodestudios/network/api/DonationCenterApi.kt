package com.novacodestudios.network.api

import com.novacodestudios.model.DonationCenter
import retrofit2.http.GET

interface DonationCenterApi {

    @GET("donation-center")
    suspend fun getDonationCenters(): List<DonationCenter>

    @GET("donation-center/{id}")
    suspend fun getDonationCenter(id: Int): DonationCenter
}