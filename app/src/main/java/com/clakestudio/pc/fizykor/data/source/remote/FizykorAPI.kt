package com.clakestudio.pc.fizykor.data.source.remote

import com.clakestudio.pc.fizykor.data.Equation
import com.clakestudio.pc.fizykor.data.FlashCard
import io.reactivex.Flowable
import retrofit2.Retrofit
import retrofit2.http.GET

class FizykorAPI(retrofit: Retrofit) {

    private val fizykorService: FizykorService

    private interface FizykorService {

        @GET(URLManager.flashcards)
        fun getFlashCards(): Flowable<List<FlashCard>>

        @GET(URLManager.equations)
        fun getEquations(): Flowable<List<Equation>>

    }

    init {
        fizykorService = retrofit.create(FizykorService::class.java)
    }

    fun getAllFlashCards(): Flowable<List<FlashCard>> = fizykorService.getFlashCards()

    fun getAllEquations(): Flowable<List<Equation>> = fizykorService.getEquations()

}