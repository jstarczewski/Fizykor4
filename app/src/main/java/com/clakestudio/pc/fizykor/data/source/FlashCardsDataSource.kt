package com.clakestudio.pc.fizykor.data.source

import com.clakestudio.pc.fizykor.data.FlashCard
import io.reactivex.Flowable

interface FlashCardsDataSource {

    fun getAllFlashCards() : Flowable<List<FlashCard>>

    fun saveFlashCard(flashCard: FlashCard)

}