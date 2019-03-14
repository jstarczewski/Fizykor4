package com.clakestudio.pc.fizykor.data.source

import com.clakestudio.pc.fizykor.data.Equation
import com.clakestudio.pc.fizykor.data.FlashCard
import com.clakestudio.pc.fizykor.data.source.local.flashcard.FlashCardsLocalDataSource
import io.reactivex.Flowable

class EquationsRepository(private var equationsLocalDataSource: EquationsDataSource,
                          private var flashCardsLocalDataSource: FlashCardsDataSource
) : EquationsDataSource, FlashCardsDataSource {


    /**
     * Cacheing and the rest of the work will be done after I decide whether I will query the data from internet
     * or not
     *
     * */
    override fun getAllEquations(): Flowable<List<Equation>> = equationsLocalDataSource.getAllEquations()

    override fun getAllEquationsFromSection(section: String): Flowable<List<Equation>> = equationsLocalDataSource.getAllEquationsFromSection(section)

    override fun saveEquation(equation: Equation) = equationsLocalDataSource.saveEquation(equation)

    /**
     * Separate Repository for FlashCards is a better option but I as long as there are only 2 methods and no more funcionality
     * I decided to add it to this repository now EquationsRepository means -> Equations as database repository
     * I plan to add Firebase module in future so we can query updates from Internet
     * */


    override fun getAllFlashCards(): Flowable<List<FlashCard>> = flashCardsLocalDataSource.getAllFlashCards()

    override fun saveFlashCard(flashCard: FlashCard) = flashCardsLocalDataSource.saveFlashCard(flashCard)

    companion object {

        private var INSTANCE: EquationsRepository? = null

        @JvmStatic
        fun getInstance(equationsDataSource: EquationsDataSource,
                        flashCardsDataSource: FlashCardsDataSource
        ): EquationsRepository {
            if (INSTANCE == null) {
                synchronized(EquationsRepository::class.java) {
                    INSTANCE = EquationsRepository(equationsDataSource, flashCardsDataSource).also {
                        INSTANCE = it
                    }
                }
            }
            return INSTANCE!!
        }


    }


}