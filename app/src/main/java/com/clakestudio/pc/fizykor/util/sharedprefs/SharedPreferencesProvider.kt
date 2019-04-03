package com.clakestudio.pc.fizykor.util.sharedprefs

import android.content.SharedPreferences

class SharedPreferencesProvider(private val sharedPreferences: SharedPreferences) : ViewSharedPreferences, FirebaseSharedPreferences {


    override fun wasNavigationToastShowed(): Boolean = !sharedPreferences.contains("toast")

    override fun isUpdateNeeded(dataVersion: Int): Boolean = sharedPreferences.getInt("dataVersion", 0) < dataVersion

    override fun saveDataVersion(dataVersion: Int) = sharedPreferences.edit().putInt("dataVersion", dataVersion).apply()

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