package com.example.pokemonbattlesim

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.bottomnavigation.BottomNavigationView


class MatchHistory : AppCompatActivity() {
    private lateinit var HistoryLayout:View
    private lateinit var bottomNavigation: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_match_history)
        HistoryLayout = findViewById(R.id.HistoryActivity)
        ViewCompat.setOnApplyWindowInsetsListener(HistoryLayout) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val sharedPreferences = getSharedPreferences("AppSettingsPrefs", Context.MODE_PRIVATE)
        val savedColor = sharedPreferences.getInt("backgroundColor", ContextCompat.getColor(this, R.color.Blue))
        HistoryLayout.setBackgroundColor(savedColor)

        val battleHistory = sharedPreferences.getString("battleHistory", "")
        val battleHistoryTextView = findViewById<TextView>(R.id.battleHistory)
        battleHistoryTextView.text = battleHistory

        initBottomNavigation()

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
                    // Already on MatchHistory, do nothing
                    true
                }

                R.id.nav_settings -> {
                    // Navigate to MatchHistory
                    val intent = Intent(this, Settings::class.java)
                    startActivity(intent)
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