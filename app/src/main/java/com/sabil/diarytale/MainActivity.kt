package com.sabil.diarytale

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import com.google.android.material.navigation.NavigationView
import com.sabil.diarytale.alarm.AlarmActivity
import com.sabil.diarytale.note.NoteActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(toolbar_main)
        supportActionBar?.setTitle("Home")

        navview_home.bringToFront()
        navview_home.setNavigationItemSelectedListener(this)
        val toggle = ActionBarDrawerToggle(this,drawerLayout_home,toolbar_main,
            R.string.openDrawerNavigation, R.string.closeDrawerNavigation)
        drawerLayout_home.addDrawerListener(toggle)
        toggle.syncState()

        btn_noteMenu_main.setOnClickListener {
            startActivity(Intent(this, NoteActivity::class.java))
        }
        btn_moneyMenu_main.setOnClickListener {
            startActivity(Intent(this, MoneyActivity::class.java))
        }
        btn_sleepMenu_main.setOnClickListener {
            startActivity(Intent(this, AlarmActivity::class.java))
        }
        btn_listMenu_main.setOnClickListener {
            startActivity(Intent(this, com.sabil.diarytale.ListActivity::class.java))
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_colortheme -> {
                Toast.makeText(this, "Color clicked", Toast.LENGTH_SHORT).show()
            }
            R.id.nav_rateus -> {
                Toast.makeText(this, "Messages clicked", Toast.LENGTH_SHORT).show()
            }
            R.id.nav_share -> {
                Toast.makeText(this, "Friends clicked", Toast.LENGTH_SHORT).show()
            }
        }
        drawerLayout_home.closeDrawer(GravityCompat.START)
        return true
    }
}
