package com.example.myapplication.util

import android.content.Context
import android.preference.PreferenceManager
import java.util.prefs.Preferences

class SharedPreferencesHelper(context: Context) {
    private val API_KEY= "Api Key"
    val prefs = PreferenceManager.getDefaultSharedPreferences(context)

    fun saveKey(key:String?){
        prefs.edit().putString(API_KEY,key).apply()
    }

    fun getKey() : String?{
        return prefs.getString(API_KEY,null)
    }
}