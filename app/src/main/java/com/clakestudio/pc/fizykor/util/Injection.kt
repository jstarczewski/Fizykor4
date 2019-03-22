package com.clakestudio.pc.fizykor.util

import android.content.Context
import com.clakestudio.pc.fizykor.data.source.EquationsRepository
import com.clakestudio.pc.fizykor.data.source.FlashCardsRepository
import com.clakestudio.pc.fizykor.data.source.local.PhysicsDatabase
import com.clakestudio.pc.fizykor.data.source.local.equation.EquationsLocalDataSource
import com.clakestudio.pc.fizykor.data.source.local.flashcard.FlashCardsLocalDataSource

object Injection {

    fun provideEquationsRepository(context: Context): EquationsRepository = EquationsRepository
            .getInstance(EquationsLocalDataSource.getInstance(PhysicsDatabase.getInstance(context).equationDao()))

    fun provideFlashCardsRepository(context: Context): FlashCardsRepository = FlashCardsRepository
            .getInstance(FlashCardsLocalDataSource.getInstance(PhysicsDatabase.getInstance(context).flashCardDao()))


}