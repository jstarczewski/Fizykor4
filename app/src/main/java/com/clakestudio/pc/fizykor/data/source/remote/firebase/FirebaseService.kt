package com.clakestudio.pc.fizykor.data.source.remote.firebase

import com.google.firebase.database.*

class FirebaseService(private val reference: DatabaseReference) {

    fun isUpdateNeeded(isNeeded: (Boolean) -> (Unit)) {
        reference.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                isNeeded(false)
            }

            override fun onDataChange(p0: DataSnapshot) {
                isNeeded(p0.value as Boolean)
            }
        })
    }


    companion object {

        private var INSTANCE: FirebaseService? = null

        fun getInstance(): FirebaseService {
            if (INSTANCE == null) {
                synchronized(FirebaseService::class.java) {
                    INSTANCE = FirebaseService(FirebaseDatabase.getInstance().apply {
                        setPersistenceEnabled(true)
                    }.getReference("update")).also {
                        INSTANCE = it
                    }
                }
            }
            return INSTANCE!!

        }


    }


}

