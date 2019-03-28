package com.clakestudio.pc.fizykor.data.source

import com.clakestudio.pc.fizykor.data.Equation

interface EquationsDataSourceLambda {

    fun getAllEquations(): (Unit) -> List<Equation>

    fun getAllEquationsFromSection(): (String) -> List<Equation>

    fun saveEquation(): (Equation) -> Unit

}