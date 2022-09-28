package com.example.myapplication.di

import android.app.Application
import com.example.myapplication.util.SharedPreferencesHelper
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class PrefsModule {
    @Provides
    @Singleton
    fun sharedPreferences(app: Application) : SharedPreferencesHelper {
        return SharedPreferencesHelper(app)
    }
}