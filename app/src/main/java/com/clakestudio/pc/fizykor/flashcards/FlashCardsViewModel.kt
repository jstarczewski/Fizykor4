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



class FlashCardsViewModel(private val equationsRepository: EquationsRepository) : ViewModel() {


    private val minDistance = 200

    var title: ObservableField<String> = ObservableField()
    var equation: ObservableField<String> = ObservableField()
    var visibility: ObservableField<Boolean> = ObservableField(true)

    var animateNewFlashCardEvent: SingleLiveEvent<Unit> = SingleLiveEvent()
    var animatePreviousFlashCardEvent: SingleLiveEvent<Unit> = SingleLiveEvent()

    private val compositeDisposable = CompositeDisposable()

    private var flashCards: ArrayList<FlashCard> = ArrayList()
    lateinit var filtering: String

    private var isLastOperationPush = false
    var isMaturaMode = false

    private val flashCardsBackStack = Stack<FlashCard>()

    fun start() {
        if (flashCards.isEmpty()) filter()
    }

    fun load() {

    }

    fun filter() {
        val disposable = equationsRepository.selectFlashCardsWhereTitleIs(filtering)
                .subscribeOn(AppSchedulersProvider.ioScheduler())
                .subscribe {
                    flashCards.addAll(it)
                }
        compositeDisposable.add(disposable)

    }

    private fun loadFlashCards(flashCards: List<FlashCard>) {
        this.flashCards.addAll(flashCards)
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

    private fun addToFlashCardsBackStack(index: Int) =
            if (!isMaturaMode) flashCardsBackStack.push(flashCards[index])
            else flashCardsBackStack.push(flashCards.filter { it.isMatura }[index])

    private fun setPreviousFlashCard() {
        if (!flashCardsBackStack.isEmpty()) {
            flashCardsBackStack.pop()
            if (isLastOperationPush && !flashCardsBackStack.isEmpty())
                setData(flashCardsBackStack.pop())
            isLastOperationPush = false
        } else setNewFlashCard()
    }

    fun determineAnimation(x1: Float, x2: Float) {
        val delta = x2 - x1
        if (x2 > x1 && delta > minDistance) {
            setNewFlashCard()
            animateNewFlashCardEvent.call()
        } else if (x2 < x1 && abs(delta) > minDistance) {
            setPreviousFlashCard()
            animatePreviousFlashCardEvent.call()
        }
        visibility.set(true)
    }


}
