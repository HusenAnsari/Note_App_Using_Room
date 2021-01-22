package com.husenansari.noteappusingroom.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.husenansari.noteappusingroom.model.NoteModel

@Dao
interface NoteModelDao {

    @Query("SELECT * FROM Note_table")
    fun getAllNote(): LiveData<List<NoteModel>>


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNote(note: NoteModel)

    // return if character match found. return all data who contain same character in column
    @Query("SELECT * FROM Note_table WHERE noteTitle LIKE :title")
    fun getNoteDetailByTitle(title: String?) : LiveData<List<NoteModel>>?

    // return if exact word match found. return only same word value in column
    @Query("SELECT * FROM Note_table WHERE noteTitle  =:title")
    fun getNoteDetailByTitleExactWord(title: String?) : LiveData<List<NoteModel>>?

    @Query("SELECT * FROM Note_table WHERE noteId = :id LIMIT 1")
    suspend fun findNoteById(id: Int): NoteModel?

    @Query("SELECT * FROM Note_table WHERE noteTitle = :title")
    suspend fun findNoteByTitle(title: String): NoteModel?

    @Update(entity  = NoteModel::class)
    suspend fun updateNote(repos: NoteModel)

    @Delete
    fun deleteNote(note: NoteModel)

    @Query("DELETE FROM Note_table")
    fun deleteAllNotes()

}