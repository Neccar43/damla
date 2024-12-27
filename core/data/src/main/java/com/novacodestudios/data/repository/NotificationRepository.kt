package com.novacodestudios.data.repository

import com.novacodestudios.model.Notification
import com.novacodestudios.network.api.NotificationApi
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext

class NotificationRepository(
    private val api: NotificationApi,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) {
    suspend fun getActiveNotifications() = withContext(dispatcher) {
        runCatching {
            api.getActiveNotifications()
        }
    }

    fun getActiveNotificationsFlow(): Flow<List<Notification>> = flow {
        while (true) {
            emit(api.getActiveNotifications())
            delay(5000)
        }
    }.flowOn(dispatcher)


    suspend fun getNotification(id: Int) = withContext(dispatcher) {
        runCatching {
            api.getNotification(id)
        }
    }
}