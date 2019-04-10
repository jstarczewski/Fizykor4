package com.clakestudio.pc.fizykor.equations

import androidx.lifecycle.ViewModel
import androidx.databinding.ObservableArrayList
import com.clakestudio.pc.fizykor.R
import com.clakestudio.pc.fizykor.SingleLiveEvent
import com.clakestudio.pc.fizykor.data.Equation
import com.clakestudio.pc.fizykor.data.source.EquationsRepository
import com.clakestudio.pc.fizykor.util.AppSchedulersProvider
import io.reactivex.disposables.CompositeDisposable
import java.util.*

class EquationsViewModel(
        private val equationsRepository: EquationsRepository
) : ViewModel() {

    private var compositeDisposable: CompositeDisposable = CompositeDisposable()

    val equations: ObservableArrayList<Equation> = ObservableArrayList()
    private val allEquations: ArrayList<Equation> = ArrayList()

    var flashCardsEvent: SingleLiveEvent<Void> = SingleLiveEvent()

    private var isDataLoaded = false
    lateinit var filtering: String


    fun start() {
        if (!isDataLoaded) {
            performUpdateIfNeeded()
            loadData()
        }
    }

    private fun loadData() = compositeDisposable.add(
            equationsRepository.getAllEquations()
                    .subscribeOn(AppSchedulersProvider.ioScheduler())
                    .observeOn(AppSchedulersProvider.uiScheduler())
                    .subscribe({ addEquations(it) },
                            { addEquations(listOf(Equation(filtering, "Error loading data occurred" + it.localizedMessage, ""))) }
                    ))

    private fun performUpdateIfNeeded() = equationsRepository.performUpdateIfNeeded(compositeDisposable)

    private fun addEquations(equations: List<Equation>) {
        allEquations.clear()
        allEquations.addAll(equations)
        isDataLoaded = true
        filterEquations(filtering)
    }

    fun filterEquations(filtering: String) {
        this.equations.clear()
        this.equations.addAll(allEquations.filter { equation -> equation.section == filtering })
        this.filtering = filtering

    }

    fun openFlashCards() {
        flashCardsEvent.call()
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}
