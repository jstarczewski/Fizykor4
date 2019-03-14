package com.clakestudio.pc.fizykor.data.source.local.flashcard

import com.clakestudio.pc.fizykor.data.FlashCard
import com.clakestudio.pc.fizykor.data.source.FlashCardsDataSource
import com.clakestudio.pc.fizykor.util.AppSchedulersProvider
import io.reactivex.Flowable

class FlashCardsLocalDataSource(private val flashCardDao: FlashCardDao) : FlashCardsDataSource {

    override fun getAllFlashCards(): Flowable<List<FlashCard>> = flashCardDao.getAllFlashCards()

    override fun saveFlashCard(flashCard: FlashCard) {

        Flowable.fromCallable {
            flashCardDao.saveFlashCard(flashCard)
        }.subscribeOn(AppSchedulersProvider.ioScheduler())
                .observeOn(AppSchedulersProvider.uiScheduler())
                .subscribe()
    }

    companion object {

        private var INSTANCE: FlashCardsLocalDataSource? = null

        fun getInstance(flashCardDao: FlashCardDao): FlashCardsLocalDataSource {
            if (INSTANCE == null) {
                synchronized(FlashCardsLocalDataSource::class.java) {
                    INSTANCE = FlashCardsLocalDataSource(flashCardDao)
                }
            }
            return INSTANCE!!

        }

    }
}