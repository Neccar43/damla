package com.novacodestudios.notification

import com.novacodestudios.notification.detail.NotificationDetailViewModel
import com.novacodestudios.notification.list.NotificationListViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val notificationModule= module {
    viewModelOf(::NotificationDetailViewModel)
    viewModelOf(::NotificationListViewModel)
}