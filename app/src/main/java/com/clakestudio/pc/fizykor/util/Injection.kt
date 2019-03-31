package com.clakestudio.pc.fizykor.util

import android.content.Context
import com.clakestudio.pc.fizykor.data.source.EquationsRepository
import com.clakestudio.pc.fizykor.data.source.FlashCardsRepository
import com.clakestudio.pc.fizykor.data.source.local.PhysicsDatabase
import com.clakestudio.pc.fizykor.data.source.local.equation.EquationsLocalDataSource
import com.clakestudio.pc.fizykor.data.source.local.flashcard.FlashCardsLocalDataSource
import com.clakestudio.pc.fizykor.data.source.remote.EquationsRemoteDataSource
import com.clakestudio.pc.fizykor.data.source.remote.FlashCardsRemoteDataSource
import com.clakestudio.pc.fizykor.data.source.remote.api.FizykorAPI
import com.clakestudio.pc.fizykor.data.source.remote.URLManager
import com.clakestudio.pc.fizykor.data.source.remote.firebase.FirebaseService
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object Injection {

    //  fun provideEquationsRepository(context: Context): EquationsRepository = EquationsRepository
    //        .getInstance(EquationsLocalDataSource.getInstance(PhysicsDatabase.getInstance(context).equationDao()))

    fun provideEquationsRepository(context: Context): EquationsRepository = EquationsRepository
            .getInstance(
                    EquationsLocalDataSource.getInstance(PhysicsDatabase.getInstance(context).equationDao()),
                    EquationsRemoteDataSource.getInstance(FizykorAPI(provideRetrofit())),
                    FirebaseService.getInstance(context)
            )


    fun provideFlashCardsRepository(context: Context): FlashCardsRepository = FlashCardsRepository
            .getInstance(
                    FlashCardsLocalDataSource.getInstance(PhysicsDatabase.getInstance(context).flashCardDao()),
                    FlashCardsRemoteDataSource.getInstance(FizykorAPI(provideRetrofit())),
                    FirebaseService.getInstance(context)
            )


    private fun provideRetrofit() =
            Retrofit.Builder()
                    .baseUrl(URLManager.base)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build()

}