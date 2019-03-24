package com.clakestudio.pc.fizykor.flashcards

import android.arch.lifecycle.ViewModel
import android.databinding.ObservableField
import com.clakestudio.pc.fizykor.SingleLiveEvent
import com.clakestudio.pc.fizykor.data.FlashCard
import com.clakestudio.pc.fizykor.data.source.FlashCardsRepository
import com.clakestudio.pc.fizykor.util.AppSchedulersProvider
import io.reactivex.disposables.CompositeDisposable
import java.util.*
import kotlin.collections.ArrayList
import kotlin.math.abs
import kotlin.random.Random


class FlashCardsViewModel(private val flashCardsRepository: FlashCardsRepository) : ViewModel() {


    private val minDistance = 250

    val title: ObservableField<String> = ObservableField()
    val equation: ObservableField<String> = ObservableField()
    val visibility: ObservableField<Boolean> = ObservableField(true)

    val animateNewFlashCardEvent: SingleLiveEvent<Unit> = SingleLiveEvent()
    val animatePreviousFlashCardEvent: SingleLiveEvent<Unit> = SingleLiveEvent()

    private val compositeDisposable = CompositeDisposable()

    private val flashCards: ArrayList<FlashCard> = ArrayList()
    private val allFlashCards: ArrayList<FlashCard> = ArrayList()
    lateinit var filtering: String

    private var isLastOperationPush = false
    var isMaturaMode = false

    private val flashCardsBackStack = Stack<FlashCard>()

    fun start() {
        if (flashCards.isEmpty()) load()
    }

    private fun load() = compositeDisposable.add(flashCardsRepository.getAllFlashCards()
            .subscribeOn(AppSchedulersProvider.ioScheduler())
            .observeOn(AppSchedulersProvider.uiScheduler())
            .subscribe {
                loadFlashCards(it)
            })

    private fun loadFlashCards(allFlashCards: List<FlashCard>) {
        this.allFlashCards.addAll(allFlashCards)
        setDefaultSection()
        setNewFlashCard()
    }

    private fun setDefaultSection() = flashCards.addAll(allFlashCards.filter { it.section == filtering })

    fun setSection(section: String) = flashCards.apply {
        clear()
        addAll(allFlashCards.filter { it.section == section })
    }.also {
        switchMathViewVisibility()
        setNewFlashCard()
    }


    private fun getRandomFlashCardIndex(): Int =
            if (isMaturaMode) Random.nextInt(flashCards.filter { it.isMatura }.size)
            else Random.nextInt(flashCards.size)

    private fun setFlashCardBasedOnIndex(index: Int) {
        if (isMaturaMode) {
            this.title.set(flashCards.filter { it.isMatura }[index].title)
            this.equation.set(flashCards.filter { it.isMatura }[index].equation)
        } else {
            this.title.set(flashCards[index].title)
            this.equation.set(flashCards[index].equation)
        }
    }

    private fun setFlashCard(flashCard: FlashCard) {
        this.title.set(flashCard.title)
        this.equation.set(flashCard.equation)
    }

    private fun setNewFlashCard() {
        val index = getRandomFlashCardIndex()
        setFlashCardBasedOnIndex(index)
        addToFlashCardsBackStack(index)
        isLastOperationPush = true
    }

    private fun setPreviousFlashCard() {
        if (!flashCardsBackStack.isEmpty()) {
            flashCardsBackStack.pop()
            if (isLastOperationPush && !flashCardsBackStack.isEmpty())
                setFlashCard(flashCardsBackStack.pop())
            isLastOperationPush = false
        } else setNewFlashCard()
    }

    private fun addToFlashCardsBackStack(index: Int) =
            if (!isMaturaMode) flashCardsBackStack.push(flashCards[index])
            else flashCardsBackStack.push(flashCards.filter { it.isMatura }[index])

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

    fun switchMathViewVisibility() {
        if (visibility.get()!!) visibility.set(false) else visibility.set(true)
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}
