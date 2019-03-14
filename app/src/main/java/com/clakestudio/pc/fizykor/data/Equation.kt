package com.clakestudio.pc.fizykor.data

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import java.util.*

@Entity(tableName = "equation")
data class Equation @JvmOverloads constructor(
        @ColumnInfo(name = "section") var section: String = "",
        @ColumnInfo(name = "title") var title: String = "",
        @ColumnInfo(name = "equation") var equation: String = "",
        @PrimaryKey @ColumnInfo(name = "entryid") var id: String = UUID.randomUUID().toString()
)
