package com.clakestudio.pc.fizykor.data

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import java.util.*


@Entity (tableName = "flashcard")
data class FlashCard @JvmOverloads constructor (
        @ColumnInfo(name = "section") var section: String = "",
        @ColumnInfo(name = "ismatura") var isMatura: Boolean = false,
        @ColumnInfo(name = "title") var title: String = "",
        @ColumnInfo(name = "equation") var equation: String = "",
        @PrimaryKey @ColumnInfo(name = "entryid") var entryid: String = UUID.randomUUID().toString()
)