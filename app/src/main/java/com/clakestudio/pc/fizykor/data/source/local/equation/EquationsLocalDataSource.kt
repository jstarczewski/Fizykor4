package com.clakestudio.pc.fizykor.data.source.local.equation

import com.clakestudio.pc.fizykor.data.Equation
import com.clakestudio.pc.fizykor.data.source.EquationsDataSource
import com.clakestudio.pc.fizykor.util.AppSchedulersProvider
import io.reactivex.Flowable

class EquationsLocalDataSource(private val equationDao: EquationDao) : EquationsDataSource {


    override fun getAllEquations(): Flowable<List<Equation>> {
        return equationDao.getAllEquations()
    }

    override fun getAllEquationsFromSection(section: String): Flowable<List<Equation>> {
        return equationDao.getAllEquationsFromSection(section)
    }

    override fun saveEquation(equation: Equation) {


        // TODO: Check if this approach is correct

        val disposable = Flowable.fromCallable { equationDao.insertEquation(equation) }
                .subscribeOn(AppSchedulersProvider.ioScheduler())
                .observeOn(AppSchedulersProvider.uiScheduler())
                .subscribe()
    }

    companion object {

        private var INSTANCE: EquationsLocalDataSource? = null


        fun getInstance(equationDao: EquationDao): EquationsLocalDataSource {

            if (INSTANCE == null) {
                synchronized(EquationsLocalDataSource::class.java) {
                    INSTANCE = EquationsLocalDataSource(equationDao)
                }
            }
            return INSTANCE!!
        }

    }

}