package com.novacodestudios.data.repository

import com.novacodestudios.network.api.DonorApi
import com.novacodestudios.model.AddDonor
import com.novacodestudios.model.DonorLoginRequest
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class DonorRepository @Inject constructor(
    private val api: DonorApi,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) {
    suspend fun signUp(addDonor: AddDonor) = withContext(dispatcher) {
        runCatching {
            api.signUp(addDonor)
        }
    }

    suspend fun login(request: com.novacodestudios.model.DonorLoginRequest) = withContext(dispatcher) {
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