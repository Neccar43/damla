package com.novacodestudios.network.api

import com.novacodestudios.model.AddDonor
import com.novacodestudios.model.Donor
import com.novacodestudios.model.DonorLoginRequest
import retrofit2.http.Body
import retrofit2.http.POST

interface DonorApi {

    @POST("donor/signup")
    suspend fun signUp(@Body addDonor: AddDonor): Donor

    @POST("donor/login")
    suspend fun login(@Body request: DonorLoginRequest): Donor

    @POST("donor/{id}")
    suspend fun getDonor(id: Int): Donor
}