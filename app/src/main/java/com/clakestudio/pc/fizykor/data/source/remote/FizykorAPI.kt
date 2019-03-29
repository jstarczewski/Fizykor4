package com.clakestudio.pc.fizykor.data.source.remote

import com.clakestudio.pc.fizykor.data.Equation
import com.clakestudio.pc.fizykor.data.FlashCard
import io.reactivex.Single
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.http.GET

class FizykorAPI(retrofit: Retrofit) {

    private val fizykorService: FizykorService = retrofit.create(FizykorService::class.java)

    fun getAllFlashCards(): Single<Response<List<FlashCard>>> = fizykorService.getFlashCards()

    fun getAllEquations(): Single<Response<List<Equation>>> = fizykorService.getEquations()

}