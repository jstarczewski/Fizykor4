package com.clakestudio.pc.fizykor.data.source.local.flashcard

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.clakestudio.pc.fizykor.data.FlashCard
import io.reactivex.Flowable

@Dao
interface FlashCardDao {

    @Query("SELECT * FROM flashcard")
    fun getAllFlashCards(): Flowable<List<FlashCard>>

    @Query("SELECT * FROM flashcard WHERE section = :section")
    fun getFlashCardsWithSectionAs(section: String) : Flowable<List<FlashCard>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveFlashCard(flashCard: FlashCard)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveAllFlashCards(vararg flashCard: FlashCard)


}