package com.novacodestudios.data.repository

import com.novacodestudios.model.AddDonor
import com.novacodestudios.model.DonorLoginRequest
import com.novacodestudios.network.api.DonorApi
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class DonorRepository(
    private val api: DonorApi,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) {
    suspend fun signUp(addDonor: AddDonor) = withContext(dispatcher) {
        runCatching {
            api.signUp(addDonor)
        }
    }

    suspend fun login(request: DonorLoginRequest) = withContext(dispatcher) {
        runCatching {
            api.login(request)
        }
    }

    suspend fun getDonor(id: Int) = withContext(dispatcher) {
        runCatching {
            api.getDonor(id)
        }
    }
}