package com.example.myapplication.di

import android.app.Application
import android.content.Context
import com.example.myapplication.model.AnimalApi
import com.example.myapplication.model.AnimalApiService
import com.example.myapplication.util.SharedPreferencesHelper
import dagger.Module
import dagger.Provides

import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

@Module
class ApiModule {

    private val BASE_URL = "https://us-central1-apis-4674e.cloudfunctions.net"

    @Provides
    fun api() : AnimalApi {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
            .create(AnimalApi::class.java)
    }

    @Provides
    fun apiService() : AnimalApiService {
        return AnimalApiService()
    }


}