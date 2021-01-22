package com.husenansari.noteappusingroom.ui

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.husenansari.noteappusingroom.R
import com.husenansari.noteappusingroom.adapter.NoteAdapter
import com.husenansari.noteappusingroom.databinding.ActivityMainBinding
import com.husenansari.noteappusingroom.model.NoteModel
import com.husenansari.noteappusingroom.util.AppConstants
import com.husenansari.noteappusingroom.viewmodel.NoteViewModel
import java.util.*


class MainActivity : AppCompatActivity(), NoteAdapter.NoteAdaptorClickListener {

    lateinit var viewModel: NoteViewModel
    lateinit var userAdapter: NoteAdapter
    var linearLayoutManager: LinearLayoutManager? = null
    var searchQuery: String? = null
    private lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.lifecycleOwner = this

        viewModel = ViewModelProvider(this).get(NoteViewModel::class.java)

        linearLayoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.rvNote.layoutManager = linearLayoutManager
        userAdapter = NoteAdapter(this)
        binding.rvNote.adapter = userAdapter


        binding.fab.setOnClickListener {
            val intent = Intent(this, EditAddActivity::class.java)
            intent.putExtra(AppConstants.TYPE, AppConstants.INSERT)
            startActivity(intent)
        }


        binding.fabDelete.setOnClickListener {
            viewModel.deleteAllData(this)
            Toast.makeText(this, "All Notes are deleted successfully", Toast.LENGTH_LONG).show()
        }


        binding.btnSearch.setOnClickListener {
            if (searchQuery != null) {
                setSearchObserver(searchQuery!!)
            }else{
                Toast.makeText(this, "Please insert title for search", Toast.LENGTH_LONG).show()
            }
        }

        binding.edtSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
            override fun afterTextChanged(editable: Editable) {
                if (editable.toString().isNotEmpty()) {
                    searchQuery = editable.toString()
                    binding.btnSearch.visibility = View.VISIBLE
                } else {
                    binding.btnSearch.visibility = View.GONE
                    setupObserver()
                }
            }
        })

        setupObserver()
    }

    private fun setSearchObserver(searchQuery: String) {
        viewModel.getNoteDetailByTitleCharacterMatch(this, searchQuery)!!.observe(this, {searchList ->
            if (searchList.isNotEmpty()) {
                binding.txtEmptyText.visibility = View.GONE
                binding.rvNote.visibility = View.VISIBLE
                userAdapter.setUserData(searchList as ArrayList<NoteModel>)

            } else {
                binding.txtEmptyText.visibility = View.VISIBLE
                binding.rvNote.visibility = View.GONE
            }
        })
    }

    private fun setupObserver() {
        viewModel.getAllNotes(this)!!.observe(this, { notesList ->

            if (notesList.isNotEmpty()) {
                binding.txtEmptyText.visibility = View.GONE
                binding.rvNote.visibility = View.VISIBLE
                binding.edtSearch.visibility = View.VISIBLE
                userAdapter.setUserData(notesList as ArrayList<NoteModel>)

            } else {
                binding.txtEmptyText.visibility = View.VISIBLE
                binding.rvNote.visibility = View.GONE
                binding.edtSearch.visibility = View.GONE
            }
        })

    }

    override fun buttonUpdateClick(notesModel: NoteModel) {
        val intent = Intent(this, EditAddActivity::class.java)
        intent.putExtra(AppConstants.TYPE, AppConstants.UPDATE)
        intent.putExtra(AppConstants.DATA, notesModel)
        startActivity(intent)
    }

    override fun buttonDeleteClick(notesModel: NoteModel) {
        viewModel.deleteData(this, notesModel)
        Toast.makeText(this, "Note Deleted successfully", Toast.LENGTH_LONG).show()
    }
}