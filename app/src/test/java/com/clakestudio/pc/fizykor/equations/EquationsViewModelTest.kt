package com.clakestudio.pc.fizykor.equations

import com.clakestudio.pc.fizykor.data.source.EquationsRepository
import org.junit.Before
import org.junit.Test

import org.junit.Assert.*
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock

class EquationsViewModelTest {

    private val equationsRepository = mock(EquationsRepository::class.java)
    private lateinit var equationsViewModel: EquationsViewModel

    @Before
    fun setUp() {
        equationsViewModel = EquationsViewModel(equationsRepository)
    }

    @Test
    fun getEquations() {
    }

    @Test
    fun getFlashCardsEvent() {
    }

    @Test
    fun setFlashCardsEvent() {
    }

    @Test
    fun start() {
        //`when`(equationsRepository.getAllEquations()).thenReturn("")

    }

    @Test
    fun filterEquations() {
    }

    @Test
    fun openFlashCards() {
    }
}