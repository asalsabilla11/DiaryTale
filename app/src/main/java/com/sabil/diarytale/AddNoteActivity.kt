package com.sabil.diarytale

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProviders
import com.sabil.diarytale.room.notes.NoteEntity
import com.sabil.diarytale.room.notes.NoteViewModel
import kotlinx.android.synthetic.main.activity_add_note.*
import java.util.*

class AddNoteActivity : AppCompatActivity() {

    private lateinit var mNoteViewModel: NoteViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_note)

        setSupportActionBar(toolbar_addNote)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_baseline_close_24)

        mNoteViewModel = ViewModelProviders.of(this).get(NoteViewModel::class.java)

        btn_create_addNote.setOnClickListener {
            validasiForm()
        }
    }

    private fun validasiForm(){
        val etTitleNote = et_title_note.text
        val etIsiNote = et_isi_note.text

        if(etTitleNote.isNullOrEmpty()){
            etLayout_title_note.isErrorEnabled = true
            etLayout_title_note.error = "Title must not be null"
            return
        }
        etLayout_title_note.isErrorEnabled = false

        if(etIsiNote.isNullOrEmpty()){
            etLayout_isi_note.isErrorEnabled = true
            etLayout_isi_note.error = "Note must not be null"
            return
        }
        etLayout_isi_note.isErrorEnabled = false

        val titleNote = etTitleNote.toString()
        val isiNote = etIsiNote.toString()
        addNoteToDatabase(titleNote, isiNote)
    }

    private fun addNoteToDatabase(titleNote: String, isiNote: String){
        val noteID = UUID.randomUUID().toString()
        val timestamp = Calendar.getInstance().timeInMillis / 1000L
        val notes = NoteEntity(noteID,titleNote,isiNote,timestamp)
        mNoteViewModel.upset(notes)
        finish()
        startActivity(Intent(this,NoteActivity::class.java))
    }
}