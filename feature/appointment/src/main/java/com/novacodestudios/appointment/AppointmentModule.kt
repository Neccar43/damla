package com.novacodestudios.appointment

import com.novacodestudios.appointment.appointment.AppointmentViewModel
import com.novacodestudios.appointment.detail.AppointmentDetailViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val appointmentModule = module {
    viewModelOf(::AppointmentViewModel)
    viewModelOf(::AppointmentDetailViewModel)
}