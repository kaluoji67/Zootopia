package com.example.myapplication.di

import com.example.myapplication.model.AnimalApiService
import com.example.myapplication.viewmodel.ListViewModel
import dagger.Component

//@Component(modules = [ApiModule::class])
interface ApiServiceComponent {
    fun inject(viewModel: ListViewModel)
}