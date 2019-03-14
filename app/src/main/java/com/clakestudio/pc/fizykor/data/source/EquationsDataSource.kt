package com.clakestudio.pc.fizykor.data.source

import com.clakestudio.pc.fizykor.data.Equation
import io.reactivex.Flowable

interface EquationsDataSource {

    fun getAllEquations(): Flowable<List<Equation>>

    fun getAllEquationsFromSection(section: String): Flowable<List<Equation>>

    fun saveEquation(equation: Equation)

}
