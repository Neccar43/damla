package com.novacodestudios.network.api

import com.novacodestudios.model.DonationCenter
import retrofit2.http.GET
import retrofit2.http.Path

interface DonationCenterApi {

    @GET("donation-center")
    suspend fun getDonationCenters(): List<DonationCenter>

    @GET("donation-center/{id}")
    suspend fun getDonationCenter(@Path("id")id: Int): DonationCenter
}