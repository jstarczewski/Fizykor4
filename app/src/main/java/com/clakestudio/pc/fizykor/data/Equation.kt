package com.clakestudio.pc.fizykor.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "equation")
data class Equation @JvmOverloads constructor(
        @ColumnInfo(name = "section") var section: String = "",
        @ColumnInfo(name = "title") var title: String = "",
        @ColumnInfo(name = "equation") var equation: String = "",
        @PrimaryKey @ColumnInfo(name = "id") var id: String = UUID.randomUUID().toString()
)
