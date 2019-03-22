package com.clakestudio.pc.fizykor.data.source

import com.clakestudio.pc.fizykor.data.FlashCard
import io.reactivex.Flowable

class FlashCardsRepository(private var flashCardsLocalDataSource: FlashCardsDataSource) : FlashCardsDataSource {


    override fun getAllFlashCards(): Flowable<List<FlashCard>> = flashCardsLocalDataSource.getAllFlashCards()

    override fun getFlashCardsWithSectionAs(title: String): Flowable<List<FlashCard>> = flashCardsLocalDataSource.getFlashCardsWithSectionAs(title)

    override fun saveFlashCard(flashCard: FlashCard) = flashCardsLocalDataSource.saveFlashCard(flashCard)

    companion object {

        private var INSTANCE: FlashCardsRepository? = null

        @JvmStatic
        fun getInstance(flashCardsDataSource: FlashCardsDataSource): FlashCardsRepository {
            if (INSTANCE == null) {
                synchronized(FlashCardsRepository::class.java) {
                    INSTANCE = FlashCardsRepository(flashCardsDataSource).also {
                        INSTANCE = it
                    }
                }
            }
            return INSTANCE!!
        }


    }

}