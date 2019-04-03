package com.clakestudio.pc.fizykor.util

import android.content.SharedPreferences

class SharedPreferencesProvider(private val sharedPreferences: SharedPreferences) {


    fun wasNavigationToastShowed(): Boolean = sharedPreferences.contains("toast")

    companion object {

        private var INSTANCE: SharedPreferencesProvider? = null

        @JvmStatic
        fun getInstance(sharedPreferences: SharedPreferences): SharedPreferencesProvider {
            if (INSTANCE == null)
                synchronized(SharedPreferencesProvider::class.java) {
                    INSTANCE = SharedPreferencesProvider(sharedPreferences)
                }
            return INSTANCE!!
        }


    }


}