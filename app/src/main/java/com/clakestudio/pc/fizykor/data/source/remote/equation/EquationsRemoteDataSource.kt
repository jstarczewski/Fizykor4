package com.clakestudio.pc.fizykor.data.source.remote.equation

import com.clakestudio.pc.fizykor.data.Equation
import com.clakestudio.pc.fizykor.data.source.EquationsDataSource
import com.google.firebase.database.DatabaseReference
import io.reactivex.Flowable

class EquationsRemoteDataSource(var equationsReference: DatabaseReference) : EquationsDataSource {

    override fun getAllEquations(): Flowable<List<Equation>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getAllEquationsFromSection(section: String): Flowable<List<Equation>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun saveEquation(equation: Equation) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


}