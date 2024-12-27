package com.novacodestudios

import com.novacodestudios.profile.ProfileViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val profileModule= module {
    viewModelOf(::ProfileViewModel)
}