package com.sabil.diarytale

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.google.android.material.navigation.NavigationView
import com.sabil.diarytale.alarm.AlarmActivity
import com.sabil.diarytale.drink.DrinkActivity
import com.sabil.diarytale.note.NoteActivity
import com.sabil.diarytale.list.ListActivity
import com.sabil.diarytale.nutrition.DataNutrisi
import com.sabil.diarytale.nutrition.NutritionActivity
import com.sabil.diarytale.room.nutrition.NutritionEntity
import com.sabil.diarytale.room.nutrition.NutritionViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var nutrisiViewModel: NutritionViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        nutrisiViewModel = ViewModelProviders.of(this).get(NutritionViewModel::class.java)
        val listNutrisi = DataNutrisi.dataNutrisi()
        nutrisiViewModel.upsertNutrition(listNutrisi)

        setSupportActionBar(toolbar_main)
        supportActionBar?.setTitle("Home")

        val header = navview_home.getHeaderView(0)
        val textviewVersion = header?.findViewById<TextView>(R.id.vertion_app_navdraw)
        textviewVersion?.text = BuildConfig.VERSION_NAME

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
            startActivity(Intent(this, ListActivity::class.java))
        }
        btn_drinkMenu_main.setOnClickListener {
            startActivity(Intent(this,DrinkActivity::class.java))
        }
        btn_nutrisiMenu_main.setOnClickListener {
            startActivity(Intent(this,
                NutritionActivity::class.java))
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_colortheme -> {
                Toast.makeText(this, "Color clicked", Toast.LENGTH_SHORT).show()
            }
            R.id.nav_rateus -> {
                val appName = "com.sabil.diarytale"
                startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=$appName")))
            }
            R.id.nav_share -> {
                val shareIntent = Intent(Intent.ACTION_SEND)
                shareIntent.type = "text/plain"
                shareIntent.putExtra(Intent.EXTRA_SUBJECT,"Diary Tale")
                var message = "\n Let me recommend you this application\n\n"
                message = message + "https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID +"\n\n"
                shareIntent.putExtra(Intent.EXTRA_TEXT, message)
                startActivity(Intent.createChooser(shareIntent,"Choose one"))
            }
        }
        drawerLayout_home.closeDrawer(GravityCompat.START)
        return true
    }
}
