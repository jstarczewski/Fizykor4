package com.clakestudio.pc.fizykor.util

import android.content.Context
import com.clakestudio.pc.fizykor.data.source.EquationsRepository
import com.clakestudio.pc.fizykor.data.source.FlashCardsRepository
import com.clakestudio.pc.fizykor.data.source.local.PhysicsDatabase
import com.clakestudio.pc.fizykor.data.source.local.flashcard.FlashCardsLocalDataSource
import com.clakestudio.pc.fizykor.data.source.remote.EquationsRemoteDataSource
import com.clakestudio.pc.fizykor.data.source.remote.api.FizykorAPI
import com.clakestudio.pc.fizykor.data.source.remote.URLManager
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object Injection {

    //  fun provideEquationsRepository(context: Context): EquationsRepository = EquationsRepository
    //        .getInstance(EquationsLocalDataSource.getInstance(PhysicsDatabase.getInstance(context).equationDao()))

    fun provideEquationsRepository(context: Context): EquationsRepository = EquationsRepository(EquationsRemoteDataSource.getInstance(FizykorAPI(provideRetrofit())))


    fun provideFlashCardsRepository(context: Context): FlashCardsRepository = FlashCardsRepository
            .getInstance(FlashCardsLocalDataSource.getInstance(PhysicsDatabase.getInstance(context).flashCardDao()))


    fun provideRetrofit() =
            Retrofit.Builder()
                    .baseUrl(URLManager.base)
                    .addConverterFactory(GsonConverterFactory.create())
                    //.addConverterFactory(MoshiConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build()

}