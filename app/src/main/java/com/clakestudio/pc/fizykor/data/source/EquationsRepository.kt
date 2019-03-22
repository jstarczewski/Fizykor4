package com.clakestudio.pc.fizykor.data.source

import com.clakestudio.pc.fizykor.data.Equation
import com.clakestudio.pc.fizykor.data.FlashCard
import io.reactivex.Flowable

class EquationsRepository(private var equationsLocalDataSource: EquationsDataSource
) : EquationsDataSource {


    /**
     * Cacheing and the rest of the work will be done after I decide whether I will query the data from internet
     * or not
     *
     * */

    override fun getAllEquations(): Flowable<List<Equation>> = equationsLocalDataSource.getAllEquations()

    override fun getAllEquationsFromSection(section: String): Flowable<List<Equation>> = equationsLocalDataSource.getAllEquationsFromSection(section)

    override fun saveEquation(equation: Equation) = equationsLocalDataSource.saveEquation(equation)

    companion object {

        private var INSTANCE: EquationsRepository? = null

        @JvmStatic
        fun getInstance(equationsDataSource: EquationsDataSource): EquationsRepository {
            if (INSTANCE == null) {
                synchronized(EquationsRepository::class.java) {
                    INSTANCE = EquationsRepository(equationsDataSource).also {
                        INSTANCE = it
                    }
                }
            }
            return INSTANCE!!
        }


    }


}