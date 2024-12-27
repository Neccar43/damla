package com.novacodestudios.util

import com.novacodestudios.model.AppointmentStatus
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

fun AppointmentStatus.toUiText(): String = when (this) {
    AppointmentStatus.SCHEDULED -> "Randevu Alındı"
    AppointmentStatus.COMPLETED -> "Tamamlandı"
    AppointmentStatus.CANCELED -> "İptal Edildi"
}

fun formatDateTime(dateTime: String?): String {
    if (dateTime == null) return "-"
    return LocalDateTime.parse(dateTime).format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"))
}