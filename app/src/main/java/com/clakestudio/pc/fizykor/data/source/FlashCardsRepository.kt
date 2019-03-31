package com.clakestudio.pc.fizykor.data.source

import com.clakestudio.pc.fizykor.data.FlashCard
import com.clakestudio.pc.fizykor.data.source.remote.FlashCardsRemoteDataSource
import com.clakestudio.pc.fizykor.data.source.remote.firebase.FirebaseService
import com.clakestudio.pc.fizykor.util.AppSchedulersProvider
import io.reactivex.Flowable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

class FlashCardsRepository(private val flashCardsLocalDataSource: FlashCardsDataSource,
                           private val flashCardsRemoteDataSource: FlashCardsRemoteDataSource,
                           private val firebaseService: FirebaseService
) : FlashCardsDataSource {


    override fun getAllFlashCards(): Flowable<List<FlashCard>> = flashCardsLocalDataSource.getAllFlashCards()

    override fun getFlashCardsWithSectionAs(section: String): Flowable<List<FlashCard>> = flashCardsLocalDataSource.getFlashCardsWithSectionAs(section)

    override fun saveFlashCard(flashCard: FlashCard) = flashCardsLocalDataSource.saveFlashCard(flashCard)

    fun performUpdateIfNeeded(compositeDisposable: CompositeDisposable) {
        return firebaseService.isUpdateNeeded { if (it) compositeDisposable.add(performUpdate()) }
    }

    private fun performUpdate(): Disposable =
            flashCardsRemoteDataSource.getAllFlashCards()
                    .subscribeOn(AppSchedulersProvider.ioScheduler())
                    .forEach {
                        it.forEach { flashCard -> flashCardsRemoteDataSource.saveFlashCard(flashCard) }
                    }


    companion object {

        private var INSTANCE: FlashCardsRepository? = null

        @JvmStatic
        fun getInstance(flashCardsDataSource: FlashCardsDataSource, flashCardsRemoteDataSource: FlashCardsRemoteDataSource, firebaseService: FirebaseService): FlashCardsRepository {
            if (INSTANCE == null) {
                synchronized(FlashCardsRepository::class.java) {
                    INSTANCE = FlashCardsRepository(flashCardsDataSource, flashCardsRemoteDataSource, firebaseService).also {
                        INSTANCE = it
                    }
                }
            }
            return INSTANCE!!
        }


    }

}