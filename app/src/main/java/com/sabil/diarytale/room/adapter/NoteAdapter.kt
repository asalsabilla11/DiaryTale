package com.sabil.diarytale.room.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sabil.diarytale.R
import com.sabil.diarytale.room.notes.NoteEntity
import kotlinx.android.synthetic.main.note_layout_rv.view.*

class NoteAdapter: RecyclerView.Adapter<NoteAdapter.ViewHolder>() {

    private lateinit var listNote: List<NoteEntity>

    class ViewHolder(v: View): RecyclerView.ViewHolder(v){
        val tvTitle = v.tv_titleNote_noteLayout
        val tvIsi = v.tv_isiNote_noteLayout

        fun bindViewHolder(noteEntity: NoteEntity){
            tvTitle.text = noteEntity.noteTitle
            tvIsi.text = noteEntity.noteIsi
        }
    }

    fun noteAdapter(listNote: List<NoteEntity>){
        this.listNote = listNote
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.note_layout_rv,parent,false)
        )
    }

    override fun getItemCount(): Int {
        return listNote.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindViewHolder(listNote.get(position))
    }
}