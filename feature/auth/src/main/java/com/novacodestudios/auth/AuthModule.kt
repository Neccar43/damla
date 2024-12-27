package com.novacodestudios.auth

import com.novacodestudios.auth.login.LoginViewModel
import com.novacodestudios.auth.signup.SignupViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module


val authModule = module {
    viewModelOf(::LoginViewModel)
    viewModelOf(::SignupViewModel)

}