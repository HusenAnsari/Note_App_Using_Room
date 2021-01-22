package com.husenansari.noteappusingroom.adapter


import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.husenansari.noteappusingroom.R
import com.husenansari.noteappusingroom.model.NoteModel
import java.util.*

class NoteAdapter(private val context: Context) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var noteList = ArrayList<NoteModel>()
    var onNoteAdaptorClickListener: NoteAdaptorClickListener? = null


    override fun onCreateViewHolder(viewGroup: ViewGroup, position: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_note, viewGroup, false)
        onNoteAdaptorClickListener = context as NoteAdaptorClickListener
        return UserViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, position: Int) {
        val searchViewHolder = viewHolder as UserViewHolder
        searchViewHolder.setValues(noteList[position])
    }


    override fun getItemCount(): Int {
        return noteList.size
    }

    fun setUserData(noteArrayList: ArrayList<NoteModel>) {
        noteList = noteArrayList
        notifyDataSetChanged()
    }


    private inner class UserViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val txtTitle: TextView = view.findViewById(R.id.txtTitle)
        private val txtDesc: TextView = view.findViewById(R.id.txtDesc)
        private val btnUpdate: TextView = view.findViewById(R.id.btnUpdate)
        private val btnDelete: TextView = view.findViewById(R.id.btnDelete)

        @SuppressLint("SetTextI18n")
        fun setValues(items: NoteModel) {

            txtTitle.text = "Title : ${items.noteId} -- ${items.title}"
            txtDesc.text = "Description : ${items.description}"

            btnUpdate.setOnClickListener {
                onNoteAdaptorClickListener!!.buttonUpdateClick(items)
            }

            btnDelete.setOnClickListener {
                onNoteAdaptorClickListener!!.buttonDeleteClick(items)
            }
        }

    }

    interface NoteAdaptorClickListener {
        fun buttonUpdateClick(notesModel: NoteModel)
        fun buttonDeleteClick(notesModel: NoteModel)
    }
}