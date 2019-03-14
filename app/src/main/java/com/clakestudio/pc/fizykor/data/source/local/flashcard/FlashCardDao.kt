package com.clakestudio.pc.fizykor.data.source.local.flashcard

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.clakestudio.pc.fizykor.data.FlashCard
import io.reactivex.Flowable

@Dao
interface FlashCardDao {

    @Query("SELECT * FROM flashcard")
    fun getAllFlashCards(): Flowable<List<FlashCard>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveFlashCard(flashCard: FlashCard)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveAllFlashCards(vararg flashCard: FlashCard)


}