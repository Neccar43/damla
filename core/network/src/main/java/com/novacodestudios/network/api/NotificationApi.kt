package com.novacodestudios.network.api

import com.novacodestudios.model.Notification
import retrofit2.http.GET

interface NotificationApi {

    @GET("notification/active")
    suspend fun getActiveNotifications(): List<Notification>

    @GET("notification/{id}")
    suspend fun getNotification(id: Int): Notification
}