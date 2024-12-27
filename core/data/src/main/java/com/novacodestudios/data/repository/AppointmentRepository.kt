package com.novacodestudios.data.repository

import com.novacodestudios.model.AddAppointmentRequest
import com.novacodestudios.model.Appointment
import com.novacodestudios.model.UpdateAppointment
import com.novacodestudios.network.api.AppointmentApi
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext

class AppointmentRepository(
    private val api: AppointmentApi,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) {
    suspend fun getActiveAppointments(donorId: Int): Result<List<Appointment>> = withContext(dispatcher) {
        runCatching {
            api.getActiveAppointmentsByDonorId(donorId)
        }
    }

    suspend fun getAppointments(donorId: Int) = withContext(dispatcher) {
        runCatching {
            api.getAppointmentsByDonorId(donorId)
        }
    }

    fun getAppointmentsPollingFlow(donorId: Int): Flow<List<Appointment>> = flow {
        while (true) {
            emit(
                api.getAppointmentsByDonorId(donorId)

            )
            delay(5_000)
        }
    }.flowOn(dispatcher)


    suspend fun getAppointmentById(id: Int) = withContext(dispatcher) {
        runCatching {
            api.getAppointmentById(id)
        }
    }

    suspend fun addAppointment(request: AddAppointmentRequest) = withContext(dispatcher) {
        runCatching {
            api.addAppointment(request)
        }
    }

    suspend fun updateAppointment(request: UpdateAppointment) = withContext(dispatcher) {
        runCatching {
            api.updateAppointment(request)
        }
    }


}