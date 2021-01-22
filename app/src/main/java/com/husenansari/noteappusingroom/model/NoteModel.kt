package com.husenansari.noteappusingroom.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "Note_table")

data class NoteModel(
        @ColumnInfo(name = "noteTitle")
        var title: String,

        @ColumnInfo(name = "noteDescription")
        var description: String,

        @ColumnInfo(name = "noteDate")
        var date: String

) : Serializable {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "noteId")
    var noteId: Int? = null

}