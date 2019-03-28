package com.clakestudio.pc.fizykor.data.source.remote

import com.google.firebase.database.DatabaseReference


class FirebaseDatabase(val equationsReference: DatabaseReference, flashCardsReference: DatabaseReference) {

    companion object {

        private var INSTANCE : FirebaseDatabase? = null

        fun getInstance() : FirebaseDatabase {
            if (INSTANCE == null) {
                INSTANCE = FirebaseDatabase(com.google.firebase.database.FirebaseDatabase.getInstance().getReference("equations"),
                        com.google.firebase.database.FirebaseDatabase.getInstance().getReference("flashcards"))

            }
            return INSTANCE!!
        }
    }


}