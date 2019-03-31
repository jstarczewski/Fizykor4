package com.clakestudio.pc.fizykor.data.source.remote

import com.clakestudio.pc.fizykor.data.FlashCard
import com.clakestudio.pc.fizykor.data.source.FlashCardsDataSource
import com.clakestudio.pc.fizykor.data.source.remote.api.FizykorAPI
import io.reactivex.Flowable

class FlashCardsRemoteDataSource(private val fizykorAPI: FizykorAPI) : FlashCardsDataSource {

    private val flashCards = ArrayList<FlashCard>()

    override fun getAllFlashCards(): Flowable<List<FlashCard>> = fizykorAPI.getAllFlashCards()
            .flatMapPublisher { response ->
                if (response.isSuccessful) return@flatMapPublisher Flowable.just(response.body())
                else return@flatMapPublisher Flowable.just(listOf(FlashCard("Error", false, "Nie udało się uzyskać danych", "")))
            }

    override fun saveFlashCard(flashCard: FlashCard) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getFlashCardsWithSectionAs(section: String): Flowable<List<FlashCard>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    companion object {

        private var INSTANCE: FlashCardsRemoteDataSource? = null

        fun getInstance(fizykorAPI: FizykorAPI): FlashCardsRemoteDataSource {
            if (INSTANCE == null) {
                synchronized(FlashCardsRemoteDataSource::class.java) {
                    INSTANCE = FlashCardsRemoteDataSource(fizykorAPI)
                }
            }
            return INSTANCE!!

        }

    }

}