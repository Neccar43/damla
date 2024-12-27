package com.novacodestudios.network.api

import com.novacodestudios.model.AddAppointmentRequest
import com.novacodestudios.model.Appointment
import com.novacodestudios.model.UpdateAppointment
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface AppointmentApi {

    @GET("appointment/active/{donorId}")
    suspend fun getActiveAppointmentsByDonorId(@Path("donorId") donorId: Int): List<Appointment>

    @GET("appointment/donor/{id}")
    suspend fun getAppointmentsByDonorId(@Path("id") donorId: Int): List<Appointment>

    @GET("appointment/{id}")
    suspend fun getAppointmentById(@Path("id") id: Int): Appointment

    @POST("appointment")
    suspend fun addAppointment(@Body request: AddAppointmentRequest): Appointment

    @PUT("appointment")
    suspend fun updateAppointment(@Body appointment: UpdateAppointment): Appointment
}