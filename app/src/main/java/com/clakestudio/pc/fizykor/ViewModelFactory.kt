package com.clakestudio.pc.fizykor

import android.app.Application
import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.clakestudio.pc.fizykor.data.source.EquationsRepository
import com.clakestudio.pc.fizykor.equations.EquationsViewModel
import com.clakestudio.pc.fizykor.flashcards.FlashCardsViewModel
import com.clakestudio.pc.fizykor.util.Injection

class ViewModelFactory private constructor(
        private val equationsRepository: EquationsRepository
) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel> create(modelClass: Class<T>) =
            with(modelClass) {
                when {
                    isAssignableFrom(EquationsViewModel::class.java) ->
                        EquationsViewModel(equationsRepository)
                    isAssignableFrom(FlashCardsViewModel::class.java) ->
                        FlashCardsViewModel(equationsRepository)

                    else -> throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
                }
                // Unchecked cast
            } as T


    companion object {

        @Volatile
        private var INSTANCE: ViewModelFactory? = null

        fun getInstance(application: Application) = INSTANCE
                ?: synchronized(ViewModelFactory::class.java) {

                    INSTANCE
                            ?: ViewModelFactory(Injection.provideEquationsRepository(application.applicationContext)).also { INSTANCE = it }

                }

    }
}