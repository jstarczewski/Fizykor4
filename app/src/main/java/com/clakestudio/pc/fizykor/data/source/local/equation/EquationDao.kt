package com.clakestudio.pc.fizykor.data.source.local.equation

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.clakestudio.pc.fizykor.data.Equation
import io.reactivex.Flowable

@Dao interface EquationDao {

    /**
     * Books Dao if a place for all the querries needed
     * */

    @Query("SELECT * FROM equation")
    fun getAllEquations() : Flowable<List<Equation>>

    @Query("SELECT * FROM equation WHERE section = :section")
    fun getAllEquationsFromSection(section: String) : Flowable<List<Equation>>

    @Insert(onConflict =  OnConflictStrategy.REPLACE)
    fun insertEquation(equation: Equation)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllEquations(vararg equation: Equation)


}