package com.clakestudio.pc.fizykor.data.source.remote.equation

import com.clakestudio.pc.fizykor.data.Equation
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener

class EquationEventListener(var equations: ArrayList<Equation>) : ValueEventListener {

    override fun onCancelled(p0: DatabaseError) {

    }

    override fun onDataChange(p0: DataSnapshot) {
        for (dataSnapshot: DataSnapshot in p0.children) {
            equations.add(dataSnapshot.getValue(Equation::class.java)!!)
        }
    }
}