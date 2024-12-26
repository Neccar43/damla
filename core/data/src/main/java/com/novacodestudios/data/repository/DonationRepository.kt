package com.novacodestudios.data.repository

import com.novacodestudios.network.api.DonationApi
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class DonationRepository @Inject constructor(
    private val api: DonationApi,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) {
    suspend fun getDonations(donorId: Int) = withContext(dispatcher) {
        runCatching {
            api.getDonations(donorId)
        }
    }

    suspend fun getDonation(id: Int) = withContext(dispatcher) {
        runCatching {
            api.getDonation(id)
        }
    }

}