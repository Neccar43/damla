package com.novacodestudios.data.repository

import com.novacodestudios.network.api.DonationCenterApi
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class DonationCenterRepository @Inject constructor(
    private val api: DonationCenterApi,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) {
    suspend fun getDonationCenters() = withContext(dispatcher) {
        runCatching {
            api.getDonationCenters()
        }
    }

    suspend fun getDonationCenter(id: Int) = withContext(dispatcher) {
        runCatching {
            api.getDonationCenter(id)
        }
    }
}