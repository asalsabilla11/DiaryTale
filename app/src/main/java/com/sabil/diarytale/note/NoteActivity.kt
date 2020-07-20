package com.sabil.diarytale.note

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import com.sabil.diarytale.MainActivity
import com.sabil.diarytale.R
import com.sabil.diarytale.adapter.NoteAdapter
import com.sabil.diarytale.room.notes.NoteEntity
import com.sabil.diarytale.room.notes.NoteViewModel
import kotlinx.android.synthetic.main.activity_note.*

class NoteActivity : AppCompatActivity(), NoteAdapter.ItemClickListener {

    private lateinit var mNoteViewModel: NoteViewModel
    private lateinit var mNoteAdapter: NoteAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_note)

        setSupportActionBar(toolbar_note)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = null

        mNoteViewModel = ViewModelProviders.of(this).get(NoteViewModel::class.java)
        mNoteAdapter = NoteAdapter()

        btn_addNote_note.setOnClickListener {
            startActivity(Intent(this, AddNoteActivity::class.java))
        }

        isiRecyclerView()
    }

    private fun isiRecyclerView(){
        mNoteViewModel.getAllNote()?.observe(this,Observer<List<NoteEntity>>{
            rv_notes_note.apply {
                layoutManager = GridLayoutManager(this@NoteActivity,2)
                mNoteAdapter.noteAdapter(it,this@NoteActivity)
                adapter = mNoteAdapter
            }
        })
    }

    override fun onBackPressed() {
        val intent = Intent(this, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
        startActivity(intent)
    }

    override fun itemClickListener(noteEntity: NoteEntity, position: Int) {
        val intent = Intent(this, ViewNoteActivity::class.java)
        intent.putExtra("NOTE_ID",noteEntity.noteID)
        intent.putExtra("NOTE_TITLE",noteEntity.noteTitle)
        intent.putExtra("NOTE_ISI",noteEntity.noteIsi)
        startActivity(intent)
    }
}