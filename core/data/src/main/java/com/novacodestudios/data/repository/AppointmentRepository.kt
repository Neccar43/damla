package com.novacodestudios.data.repository

import com.novacodestudios.network.api.AppointmentApi
import com.novacodestudios.model.AddAppointmentRequest
import com.novacodestudios.model.UpdateAppointment
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class AppointmentRepository @Inject constructor(
    private val api: AppointmentApi,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) {
    suspend fun getActiveAppointments(donorId:Int) = withContext(dispatcher) {
        runCatching {
            api.getActiveAppointmentsByDonorId(donorId)
        }
    }

    suspend fun getAppointments(donorId:Int) = withContext(dispatcher) {
        runCatching {
            api.getAppointmentsByDonorId(donorId)
        }
    }

    suspend fun getAppointmentById(id:Int) = withContext(dispatcher) {
        runCatching {
            api.getAppointmentById(id)
        }
    }

    suspend fun addAppointment(request: AddAppointmentRequest)= withContext(dispatcher) {
        runCatching {
            api.addAppointment(request)
        }
    }

    suspend fun updateAppointment(request: UpdateAppointment)= withContext(dispatcher) {
        runCatching {
            api.updateAppointment(request)
        }
    }


}