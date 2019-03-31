package com.clakestudio.pc.fizykor.data.source

import com.clakestudio.pc.fizykor.data.Equation
import com.clakestudio.pc.fizykor.data.source.remote.EquationsRemoteDataSource
import com.clakestudio.pc.fizykor.data.source.remote.firebase.FirebaseService
import com.clakestudio.pc.fizykor.util.AppSchedulersProvider
import io.reactivex.Flowable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

class EquationsRepository(private val equationsLocalDataSource: EquationsDataSource,
                          private val equationsRemoteDataSource: EquationsRemoteDataSource,
                          private val firebaseService: FirebaseService
) : EquationsDataSource {

    override fun getAllEquations(): Flowable<List<Equation>> = equationsLocalDataSource.getAllEquations()

    override fun getAllEquationsFromSection(section: String): Flowable<List<Equation>> = equationsLocalDataSource.getAllEquationsFromSection(section)

    override fun saveEquation(equation: Equation) = equationsLocalDataSource.saveEquation(equation)

    fun performUpdateIfNeeded(compositeDisposable: CompositeDisposable) {
        return firebaseService.isUpdateNeeded { if (it) compositeDisposable.add(performUpdate()) }
    }

    private fun performUpdate(): Disposable =
            equationsRemoteDataSource.getAllEquations()
                    .subscribeOn(AppSchedulersProvider.ioScheduler())
                    .observeOn(AppSchedulersProvider.uiScheduler())
                    .forEach {
                        it.forEach { equations -> equationsLocalDataSource.saveEquation(equations) }
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