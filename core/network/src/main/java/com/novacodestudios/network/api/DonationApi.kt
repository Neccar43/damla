package com.novacodestudios.network.api

import com.novacodestudios.model.Donation
import retrofit2.http.GET
import retrofit2.http.Path

interface DonationApi {

    @GET("donation/donor/{id}")
    suspend fun getDonations(@Path("id") donorId:Int): List<Donation>

    @GET("donation/{id}")
    suspend fun getDonation(@Path("id") id:Int): Donation
}