package com.clakestudio.pc.fizykor.data.source.remote

import com.clakestudio.pc.fizykor.data.Equation
import com.clakestudio.pc.fizykor.data.source.EquationsDataSource
import com.clakestudio.pc.fizykor.data.source.remote.api.FizykorAPI
import io.reactivex.Flowable

class EquationsRemoteDataSource(private val fizykorAPI: FizykorAPI) : EquationsDataSource {

    override fun getAllEquations(): Flowable<List<Equation>> = fizykorAPI.getAllEquations()
            .flatMapPublisher { response ->
                if (response.isSuccessful)
                    return@flatMapPublisher Flowable.just(response.body())
                else return@flatMapPublisher Flowable.just(listOf(Equation("Error", "Nie udało się uzyskać danych", response.errorBody().toString() )))
            }

    override fun getAllEquationsFromSection(section: String): Flowable<List<Equation>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun saveEquation(equation: Equation) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    companion object {

        private var INSTANCE: EquationsRemoteDataSource? = null

        fun getInstance(fizykorAPI: FizykorAPI): EquationsRemoteDataSource {
            if (INSTANCE == null) {
                synchronized(EquationsRemoteDataSource::class.java) {
                    INSTANCE = EquationsRemoteDataSource(fizykorAPI)
                }
            }
            return INSTANCE!!

        }

    }
}