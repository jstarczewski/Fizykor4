package com.clakestudio.pc.fizykor.flashcards

import android.arch.lifecycle.ViewModel
import android.databinding.ObservableField
import com.clakestudio.pc.fizykor.SingleLiveEvent
import com.clakestudio.pc.fizykor.data.FlashCard
import com.clakestudio.pc.fizykor.data.source.EquationsRepository
import com.clakestudio.pc.fizykor.util.AppSchedulersProvider
import io.reactivex.disposables.CompositeDisposable
import java.util.*
import kotlin.collections.ArrayList
import kotlin.math.abs
import kotlin.random.Random


private const val MIN_DISTANCE = 200

class FlashCardsViewModel(private val equationsRepository: EquationsRepository) : ViewModel() {


    var title: ObservableField<String> = ObservableField()
    var equation: ObservableField<String> = ObservableField()

    var animateNewFlashCardEvent: SingleLiveEvent<Unit> = SingleLiveEvent()
    var animatePreviousFlashCardEvent: SingleLiveEvent<Unit> = SingleLiveEvent()

    private val compositeDisposable = CompositeDisposable()

    private var flashCards: ArrayList<FlashCard> = ArrayList()
    private var rawFlashCards: ArrayList<FlashCard> = ArrayList()

    private var isLastOperationPush = false
    
    var isMaturaMode = false

    var visibility: ObservableField<Boolean> = ObservableField(true)

    var filtering = "Kinematyka"

    private val flashCardsBackStack = Stack<FlashCard>()

    fun start() {
        if (!rawFlashCards.isEmpty()) loadData()
    }

    fun filterFlashCards(filtering: String) {

        flashCards.clear()
        flashCards.addAll(rawFlashCards.filter { flashCard -> flashCard.section == filtering })

    }

    private fun loadData() {

        val disposable = equationsRepository.getAllFlashCards()
                .subscribeOn(AppSchedulersProvider.ioScheduler())
                .subscribe {
                    loadFlashCards(it)
                }
        compositeDisposable.add(disposable)

    }

    private fun loadFlashCards(flashCards: List<FlashCard>) {
        this.rawFlashCards.clear()
        this.rawFlashCards.addAll(flashCards)
        filterFlashCards(filtering)
        setNewFlashCard()
        compositeDisposable.clear()
    }

    fun switchMathViewVisibility() {
        if (visibility.get()!!) visibility.set(false) else visibility.set(true)
    }

    private fun getRandomFlashCardIndex(): Int =
            if (isMaturaMode) Random.nextInt(flashCards.filter { it.isMatura }.size)
            else Random.nextInt(flashCards.size)

    private fun setData(index: Int) {
        if (isMaturaMode) {
            this.title.set(flashCards.filter { it.isMatura }[index].title)
            this.equation.set(flashCards.filter { it.isMatura }[index].equation)
        } else {
            this.title.set(flashCards[index].title)
            this.equation.set(flashCards[index].equation)
        }
    }

    private fun setData(flashCard: FlashCard) {
        this.title.set(flashCard.title)
        this.equation.set(flashCard.equation)
    }

    private fun setNewFlashCard() {
        val index = getRandomFlashCardIndex()
        setData(index)
        addToFlashCardsBackStack(index)
        isLastOperationPush = true
    }

    private fun addToFlashCardsBackStack(index: Int) = if (!isMaturaMode) flashCardsBackStack.push(flashCards[index]) else flashCardsBackStack.push(flashCards.filter { it.isMatura }[index])

    private fun setPreviousFlashCard() {
        if (!flashCardsBackStack.isEmpty()) {
            var previousFlashCard = flashCardsBackStack.pop()
            if (isLastOperationPush && !flashCardsBackStack.isEmpty())
                previousFlashCard = flashCardsBackStack.pop()
            setData(previousFlashCard)
            isLastOperationPush = false
        } else setNewFlashCard()
    }

    fun determineAnimation(x1: Float, x2: Float) {
        val delta = x2 - x1
        if (x2 > x1 && delta > MIN_DISTANCE) {
            setNewFlashCard()
            animateNewFlashCardEvent.call()
        } else if (x1 > x2 && abs(delta) > MIN_DISTANCE) {
            setPreviousFlashCard()
            animatePreviousFlashCardEvent.call()
        }
        // despite being true hides the equation
        visibility.set(true)
    }


}
