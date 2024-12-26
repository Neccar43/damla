package com.novacodestudios.data.repository

import com.novacodestudios.network.api.NotificationApi
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class NotificationRepository @Inject constructor(
    private val api: NotificationApi,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) {
    suspend fun getActiveNotifications() = withContext(dispatcher) {
        runCatching {
            api.getActiveNotifications()
        }
    }

    suspend fun getNotification(id: Int) = withContext(dispatcher) {
        runCatching {
            api.getNotification(id)
        }
    }
}