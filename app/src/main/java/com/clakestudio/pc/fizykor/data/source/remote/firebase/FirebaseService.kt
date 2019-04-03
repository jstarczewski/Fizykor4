package com.clakestudio.pc.fizykor.data.source.remote.firebase

import android.util.Log
import com.clakestudio.pc.fizykor.util.SharedPreferencesProvider
import com.google.firebase.database.*

class FirebaseService(private val reference: DatabaseReference, private val sharedPreferencesProvider: SharedPreferencesProvider) {

    fun isUpdateNeeded(isNeeded: (Boolean) -> (Unit)) {
        reference.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                isNeeded(false)
            }

            override fun onDataChange(p0: DataSnapshot) {
                val value = p0.value.toString().toInt()
                if (sharedPreferencesProvider.isUpdateNeeded(value)) {
                    sharedPreferencesProvider.saveDataVersion(value)
                    isNeeded(true)
                    Log.e("Value", (value).toString())
                }
                isNeeded(false)
            }
        })
    }


    companion object {

        private var INSTANCE: FirebaseService? = null

        fun getInstance(sharedPreferencesProvider: SharedPreferencesProvider): FirebaseService {
            if (INSTANCE == null) {
                synchronized(FirebaseService::class.java) {
                    INSTANCE = FirebaseService(FirebaseDatabase.getInstance().getReference("update"),
                            sharedPreferencesProvider
                    ).also {
                        INSTANCE = it
                    }
                }
            }
            return INSTANCE!!

        }


    }


}

