package com.sabil.diarytale

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProviders
import com.sabil.diarytale.room.notes.NoteEntity
import com.sabil.diarytale.room.notes.NoteViewModel
import kotlinx.android.synthetic.main.activity_view_note.*
import java.util.*

class ViewNoteActivity : AppCompatActivity() {

    private var noteID_Extra: String? = ""
    private var noteTitle_Extra: String? = ""
    private var noteIsi_Extra: String? = ""
    private lateinit var noteViewModel: NoteViewModel
    private var fromButtonSave: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_note)

        setSupportActionBar(toolbar_viewNote)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_baseline_close_24)

        noteViewModel = ViewModelProviders.of(this).get(NoteViewModel::class.java)

        getInterntAndSetEditTex()

        btn_delete_viewNote.setOnClickListener {
            showDialogDelete()
        }
        btn_save_viewNote.setOnClickListener {
            fromButtonSave = true
            validasiEditText()
        }
    }

    private fun getInterntAndSetEditTex(){
        // Get string extra
        noteID_Extra = intent.getStringExtra("NOTE_ID")
        noteTitle_Extra = intent.getStringExtra("NOTE_TITLE")
        noteIsi_Extra = intent.getStringExtra("NOTE_ISI")

        // Set Edittext
        et_isi_viewNote.setText(noteIsi_Extra)
        et_title_viewNote.setText(noteTitle_Extra)
    }

    private fun validasiEditText(){
        val et_title = et_title_viewNote.text
        val et_isi = et_isi_viewNote.text
        if(et_title.isNullOrEmpty()){
            etLayout_title_viewNote.isErrorEnabled = true
            etLayout_title_viewNote.error = "Title tidak boleh kosong"
            return
        }
        etLayout_title_viewNote.isErrorEnabled = false
        if(et_isi.isNullOrEmpty()){
            etLayout_isi_viewNote.isErrorEnabled = true
            etLayout_isi_viewNote.error = "Note tidak boleh kosong"
            return
        }
        etLayout_isi_viewNote.isErrorEnabled = false

        if(fromButtonSave == true){
            saveNewData(et_title.toString(),et_isi.toString())
        }
        else checkPerubahan(et_title.toString(),et_isi.toString())
    }

    private fun saveNewData(newTitle: String, newIsi: String){
        val newTimestamp = Calendar.getInstance().timeInMillis / 1000L
        val newNote = NoteEntity(noteID_Extra!!,newTitle,newIsi,newTimestamp)
        noteViewModel.upset(newNote)
        startActivity(Intent(this@ViewNoteActivity,NoteActivity::class.java))
        finish()
    }

    private fun showDialogDelete(){
        val alertDialog = AlertDialog.Builder(this, R.style.MyDialogTheme)
        alertDialog.apply {
            this.setTitle("Delete Note")
            this.setMessage("Apakah anda yakin akan mengahapus note ini ?")
            this.setPositiveButton("Delete",object : DialogInterface.OnClickListener{
                override fun onClick(p0: DialogInterface?, p1: Int) {
                    noteViewModel.deleteByID(noteID_Extra!!)
                    startActivity(Intent(this@ViewNoteActivity,NoteActivity::class.java))
                    finish()
                }
            })
            this.setNegativeButton("Cancel",object : DialogInterface.OnClickListener{
                override fun onClick(p0: DialogInterface?, p1: Int) {
                    p0?.cancel()
                }
            })
        }
        alertDialog.create().show()
    }

    private fun showDialogBack(){
        val alertDialog = AlertDialog.Builder(this,R.style.MyDialogTheme)
        alertDialog.apply {
            this.setTitle("Not Saved")
            this.setMessage("Anda belum menyimpan perubahan, simpan atau buang?")
            setPositiveButton("Save",object : DialogInterface.OnClickListener{
                override fun onClick(p0: DialogInterface?, p1: Int) {
                    fromButtonSave = true
                    validasiEditText()
                }
            })
            this.setNegativeButton("Discard",object : DialogInterface.OnClickListener{
                override fun onClick(p0: DialogInterface?, p1: Int) {
                    back()
                }
            })
            this.setNeutralButton("Cancel",object :DialogInterface.OnClickListener{
                override fun onClick(p0: DialogInterface?, p1: Int) {
                    p0?.cancel()
                }
            })
        }
        alertDialog.create().show()
    }

    private fun back(){
        super.onBackPressed()
    }

    private fun checkPerubahan(newTitle: String, newIsi: String){
        if(newIsi == noteIsi_Extra && newTitle == noteTitle_Extra){
            back()
        }
        else{
            showDialogBack()
        }
    }

    override fun onBackPressed() {
        validasiEditText()
    }
}