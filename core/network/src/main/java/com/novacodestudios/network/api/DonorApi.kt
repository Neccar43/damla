package com.novacodestudios.network.api

import com.novacodestudios.model.AddDonor
import com.novacodestudios.model.Donor
import com.novacodestudios.model.DonorLoginRequest
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface DonorApi {

    @POST("donor/signup")
    suspend fun signUp(@Body addDonor: AddDonor): Donor

    @POST("donor/login")
    suspend fun login(@Body request: DonorLoginRequest): Donor

    @GET("donor/{id}")
    suspend fun getDonor(@Path("id") id: Int): Donor
}