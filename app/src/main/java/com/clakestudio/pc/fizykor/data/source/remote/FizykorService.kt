package com.clakestudio.pc.fizykor.data.source.remote

import com.clakestudio.pc.fizykor.data.Equation
import com.clakestudio.pc.fizykor.data.FlashCard
import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.GET


interface FizykorService {

    @GET(URLManager.flashcards)
    fun getFlashCards(): Single<Response<List<FlashCard>>>

    @GET(URLManager.equations)
    fun getEquations(): Single<Response<List<Equation>>>

}