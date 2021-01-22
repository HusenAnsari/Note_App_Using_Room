package com.husenansari.noteappusingroom.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.husenansari.noteappusingroom.model.NoteModel
import com.husenansari.noteappusingroom.repository.NoteRepository


class NoteViewModel : ViewModel() {

    fun insertData(context: Context, noteTitle: String, noteDesc: String, noteDate: String) {
        NoteRepository.insertData(context, noteTitle, noteDesc, noteDate)
    }

    fun getNoteDetailByTitleCharacterMatch(context: Context, noteTitle: String) : LiveData<List<NoteModel>>? {
        return NoteRepository.getNoteDetailByTitleCharacterMatch(context, noteTitle)
    }

    fun getAllNotes(context: Context): LiveData<List<NoteModel>>? {
        return NoteRepository.getAllNotes(context)
    }

    fun updateData(context: Context, noteTitle: String, noteDesc: String, noteDate: String, noteId: Int?) {
        NoteRepository.updateNote(context, noteTitle, noteDesc, noteDate, noteId)
    }

    fun deleteData(context: Context, note: NoteModel) {
        NoteRepository.deleteNote(context, note)
    }

    fun deleteAllData(context: Context) {
        NoteRepository.deleteAllNote(context)
    }

    fun findNoteById(context: Context, id: Int): NoteModel? {
        return NoteRepository.findNoteById(context, id)
    }

    fun findNoteByTitle(context: Context, noteTitle: String): NoteModel? {
        return NoteRepository.findNoteByTitle(context, noteTitle)
    }

   /*fun getDirectorFullName(movie: Movie): String? {
        return MoviesDatabase.getDatabase(requireContext()).directorDao().findDirectorById(movie.directorId)?.fullName
    }*/
}