package com.clakestudio.pc.fizykor.util.sharedprefs

interface FirebaseSharedPreferences {

    fun isUpdateNeeded(dataVersion : Int) : Boolean

    fun saveDataVersion(dataVersion: Int)

}