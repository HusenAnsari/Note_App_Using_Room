package com.husenansari.noteappusingroom.repository

import android.content.Context
import androidx.lifecycle.LiveData
import com.husenansari.noteappusingroom.db.NoteDatabase
import com.husenansari.noteappusingroom.model.NoteModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class NoteRepository() {

    companion object {

        var loginDatabase: NoteDatabase? = null

        private var note: LiveData<List<NoteModel>>? = null
        private var allNoteList: LiveData<List<NoteModel>>? = null
        private var findByIdNote: NoteModel? = null

        private fun initializeDB(context: Context): NoteDatabase {
            return NoteDatabase.invoke(context)
        }

        fun insertData(context: Context, title: String, description: String, date: String) {
            loginDatabase = initializeDB(context)
            CoroutineScope(Dispatchers.IO).launch {
                val noteDetail = NoteModel(title, description, date)
                loginDatabase!!.getNoteDao().insertNote(noteDetail)
            }
        }

        // return if character match found. return all data who contain same character
        fun getNoteDetailByTitleCharacterMatch(
                context: Context,
                noteTitle: String
        ): LiveData<List<NoteModel>>? {
            loginDatabase = initializeDB(context)
            note = loginDatabase!!.getNoteDao().getNoteDetailByTitle("$noteTitle%")
            return note
        }

        // return if exact word match found. return only same word value
        fun getNoteDetailByTitleExactMatch(
                context: Context,
                noteTitle: String
        ): LiveData<List<NoteModel>>? {
            loginDatabase = initializeDB(context)
            note = loginDatabase!!.getNoteDao().getNoteDetailByTitleExactWord(noteTitle)
            return note
        }

        fun getAllNotes(context: Context): LiveData<List<NoteModel>>? {
            loginDatabase = initializeDB(context)
            allNoteList = loginDatabase!!.getNoteDao().getAllNote()
            return allNoteList
        }

        fun updateNote(context: Context, title: String, description: String, date: String, noteId: Int?) {
            loginDatabase = initializeDB(context)
            CoroutineScope(Dispatchers.IO).launch {
                val noteDetail = NoteModel(title, description, date)
                noteDetail.noteId = noteId
                loginDatabase!!.getNoteDao().updateNote(noteDetail)
            }
        }

        fun deleteNote(context: Context, note: NoteModel) {
            loginDatabase = initializeDB(context)
            CoroutineScope(Dispatchers.IO).launch {
                loginDatabase!!.getNoteDao().deleteNote(note)
            }
        }

        fun deleteAllNote(context: Context) {
            loginDatabase = initializeDB(context)
            CoroutineScope(Dispatchers.IO).launch {
                loginDatabase!!.getNoteDao().deleteAllNotes()
            }
        }

        fun findNoteById(context: Context, id: Int): NoteModel? {
            loginDatabase = initializeDB(context)
            CoroutineScope(Dispatchers.IO).launch {
                findByIdNote = loginDatabase!!.getNoteDao().findNoteById(id)
            }

            return findByIdNote
        }

        fun findNoteByTitle(context: Context, noteTitle: String): NoteModel? {
            loginDatabase = initializeDB(context)
            CoroutineScope(Dispatchers.IO).launch {
                findByIdNote = loginDatabase!!.getNoteDao().findNoteByTitle(noteTitle)
            }
            return findByIdNote
        }
    }

}