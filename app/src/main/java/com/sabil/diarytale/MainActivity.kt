package com.sabil.diarytale

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.*
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
            startActivity(Intent(this,NoteActivity::class.java))
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
