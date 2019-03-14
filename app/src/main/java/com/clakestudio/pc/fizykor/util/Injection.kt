package com.clakestudio.pc.fizykor.util

import android.content.Context
import com.clakestudio.pc.fizykor.data.source.EquationsRepository
import com.clakestudio.pc.fizykor.data.source.local.EquationDatabase
import com.clakestudio.pc.fizykor.data.source.local.equation.EquationsLocalDataSource
import com.clakestudio.pc.fizykor.data.source.local.flashcard.FlashCardsLocalDataSource

object Injection {

    fun provideEquationsRepository(context: Context) : EquationsRepository = EquationsRepository.
            getInstance(EquationsLocalDataSource.getInstance(EquationDatabase.getInstance(context).equationDao()),
                    FlashCardsLocalDataSource.getInstance(EquationDatabase.getInstance(context).flashCardDao()))


}