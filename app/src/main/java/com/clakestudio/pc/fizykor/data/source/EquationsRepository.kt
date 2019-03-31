package com.clakestudio.pc.fizykor.data.source

import com.clakestudio.pc.fizykor.data.Equation
import com.clakestudio.pc.fizykor.data.FlashCard
import com.clakestudio.pc.fizykor.data.source.remote.EquationsRemoteDataSource
import com.clakestudio.pc.fizykor.data.source.remote.firebase.FirebaseService
import io.reactivex.Flowable

class EquationsRepository(private var equationsLocalDataSource: EquationsDataSource,
                          private var equationsRemoteDataSource: EquationsRemoteDataSource,
                          private var firebaseService: FirebaseService
) : EquationsDataSource {

    override fun getAllEquations(): Flowable<List<Equation>> = equationsLocalDataSource.getAllEquations()

    override fun getAllEquationsFromSection(section: String): Flowable<List<Equation>> = equationsLocalDataSource.getAllEquationsFromSection(section)

    override fun saveEquation(equation: Equation) = equationsLocalDataSource.saveEquation(equation)

    fun performUpdateIfNeeded() = firebaseService.isUpdateNeeded { if (it) performUpdate() }

    private fun performUpdate() {
        equationsRemoteDataSource.getAllEquations().forEach {
            it.forEach { equationsLocalDataSource.saveEquation(it) }
        }.dispose()
    }

    companion object {

        private var INSTANCE: EquationsRepository? = null

        @JvmStatic
        fun getInstance(equationsDataSource: EquationsDataSource, equationsRemoteDataSource: EquationsRemoteDataSource, firebaseService: FirebaseService): EquationsRepository {
            if (INSTANCE == null) {
                synchronized(EquationsRepository::class.java) {
                    INSTANCE = EquationsRepository(equationsDataSource, equationsRemoteDataSource, firebaseService).also {
                        INSTANCE = it
                    }
                }
            }
            return INSTANCE!!
        }


    }


}