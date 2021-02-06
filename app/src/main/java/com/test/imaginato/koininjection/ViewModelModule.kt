package com.test.imaginato.koininjection


import com.test.imaginato.ui.signin.viewmodel.SignInViewModel
import org.koin.androidx.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module

val viewModelModule = module {


    viewModel {
        SignInViewModel(get(),get())
    }
}