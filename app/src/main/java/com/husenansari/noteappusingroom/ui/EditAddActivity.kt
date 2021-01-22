package com.husenansari.noteappusingroom.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.husenansari.noteappusingroom.R
import com.husenansari.noteappusingroom.databinding.ActivityEditAddBinding
import com.husenansari.noteappusingroom.model.NoteModel
import com.husenansari.noteappusingroom.util.AppConstants
import com.husenansari.noteappusingroom.viewmodel.NoteViewModel

class EditAddActivity : AppCompatActivity() {

    lateinit var viewModel: NoteViewModel
    private lateinit var binding: ActivityEditAddBinding
    private var type: String? = null
    private var noteId: Int? = null
    private var noteModel: NoteModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_edit_add)
        binding.lifecycleOwner = this

        type = intent.getStringExtra(AppConstants.TYPE);

        if (type == AppConstants.INSERT) {
            binding.btnUpdateAdd.text = "Save"
        } else {
            binding.btnUpdateAdd.text = "Update"
            noteModel = intent.getSerializableExtra(AppConstants.DATA) as NoteModel?
            setData(noteModel)
        }


        viewModel = ViewModelProvider(this).get(NoteViewModel::class.java)

        binding.btnUpdateAdd.setOnClickListener {
            checkValidation()
        }

    }

    private fun setData(noteModel: NoteModel?) {
        noteId = noteModel!!.noteId
        binding.edtTitle.setText(noteModel!!.title)
        binding.edtDesc.setText(noteModel.description)
    }

    private fun checkValidation() {

        val noteTitle: String = binding.edtTitle.text.toString().trim()
        val noteDesc: String = binding.edtDesc.text.toString().trim()
        val noteDate = "01/01/0001"


        if (TextUtils.isEmpty(noteTitle)) {
            Toast.makeText(this, "Please enter title", Toast.LENGTH_LONG).show()
            return
        }

        if (TextUtils.isEmpty(noteDesc)) {
            Toast.makeText(this, "Please enter desc", Toast.LENGTH_LONG).show()
            return
        }


        if (type == AppConstants.INSERT) {
            viewModel.insertData(this, noteTitle, noteDesc, noteDate)
            Toast.makeText(this, "Note Saved successfully", Toast.LENGTH_LONG).show()
        } else {
            viewModel.updateData(this, noteTitle, noteDesc, noteDate, noteId)
            Toast.makeText(this, "Note Updated successfully", Toast.LENGTH_LONG).show()
        }
        finish()

        /* if (noteModel != null) {
             val noteData = viewModel.findNoteById(this, noteModel!!.noteId!!)
             if (noteData != null) {
                 viewModel.updateData(this, noteTitle, noteDesc, noteDate)
                 Toast.makeText(this, "Note Updated successfully", Toast.LENGTH_LONG).show()
             }
         } else {
             val noteDataByTitle = viewModel.findNoteByTitle(this, noteTitle)
             if (noteDataByTitle != null) {
                 Toast.makeText(this, "Note already saves with title $noteTitle", Toast.LENGTH_LONG).show()
                 return
             }else{
                 viewModel.insertData(this, noteTitle, noteDesc, noteDate)
                 Toast.makeText(this, "Note Saved successfully", Toast.LENGTH_LONG).show()
             }
         }
         finish()*/
    }
}