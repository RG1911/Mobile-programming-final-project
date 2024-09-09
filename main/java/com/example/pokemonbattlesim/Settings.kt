package com.example.pokemonbattlesim

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import com.google.android.material.bottomnavigation.BottomNavigationView

const val EXTRA_COLOR = "com.example.pokemonbattlesim.color"

class Settings : AppCompatActivity() {
    private lateinit var settings: View
    private lateinit var bottomNavigation: BottomNavigationView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        settings = findViewById(R.id.settingsActivity)
        val backButton = findViewById<Button>(R.id.BackToMainButton)
        backButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
            startActivity(intent)
        }
        findViewById<View>(R.id.RedButton).setOnClickListener { onColorSelected(it) }
        findViewById<View>(R.id.GreenButton).setOnClickListener { onColorSelected(it) }
        findViewById<View>(R.id.BlueButton).setOnClickListener { onColorSelected(it) }

        val clearMatchHistoryButton = findViewById<Button>(R.id.ClearHistoryButton)
        clearMatchHistoryButton.setOnClickListener {
            clearMatchHistory() }

        val squadGif: ImageView = findViewById(R.id.squadGif)
        Glide.with(this)
            .asGif()
            .load(R.drawable.squad)
            .into(squadGif)

            initBottomNavigation()

    }

    private fun onColorSelected(view: View) {
        val colorId = when (view.id) {
            R.id.RedButton -> R.color.Red
            R.id.GreenButton -> R.color.Green
            R.id.BlueButton -> R.color.Blue
            else -> R.color.Blue
        }
        val sharedPreferences = getSharedPreferences("AppSettingsPrefs", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putInt("backgroundColor", ContextCompat.getColor(this, colorId))
        editor.apply()

        // Update the current settings activity background as well
        settings.setBackgroundColor(ContextCompat.getColor(this, colorId))
    }

    private fun clearMatchHistory() {
        val sharedPreferences = getSharedPreferences("AppSettingsPrefs", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.remove("battleHistory")
        editor.apply()
    }

    private fun initBottomNavigation() {
        bottomNavigation = findViewById(R.id.bottom_navigation)
        bottomNavigation.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home -> {
                    // Navigate to MainActivity
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    true
                }
                R.id.nav_match_history -> {
                    // Navigate to MatchHistory
                    val intent = Intent(this, MatchHistory::class.java)
                    startActivity(intent)
                    true
                }
                R.id.nav_settings -> {
                    // Already on Settings, do nothing
                    true
                }

                R.id.nav_pokedex -> {
                    // Navigate to MatchHistory
                    val intent = Intent(this, Pokedex::class.java)
                    startActivity(intent)
                    true
                }

                else -> false
            }
        }
    }

}