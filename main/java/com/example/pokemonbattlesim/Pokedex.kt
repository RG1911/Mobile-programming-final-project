package com.example.pokemonbattlesim

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.android.material.bottomnavigation.BottomNavigationView

class Pokedex : AppCompatActivity() {

    private lateinit var bottomNavigation: BottomNavigationView
    private lateinit var pokedex: View
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pokedex)
        pokedex = findViewById(R.id.pokedex)
        val sharedPreferences = getSharedPreferences("AppSettingsPrefs", Context.MODE_PRIVATE)
        val savedColor = sharedPreferences.getInt("backgroundColor", ContextCompat.getColor(this, R.color.Blue))
        pokedex.setBackgroundColor(savedColor)

        val squirtleInfoTextView: TextView = findViewById(R.id.squirtle_info)
        val squirtleBio = getString(R.string.squirtlebio)
        var squirtleMoves = getString(R.string.squirtlemoves)
        squirtleMoves = squirtleMoves.replace(". ", ".\n")
        squirtleInfoTextView.text = "$squirtleBio\n\n$squirtleMoves"


        // Bulbasaur
        val bulbasaurInfoTextView: TextView = findViewById(R.id.bulbasaur_info)
        val bulbasaurBio = getString(R.string.bulbasaurbio)
        var bulbasaurMoves = getString(R.string.bulbasaurmoves)
        bulbasaurMoves = bulbasaurMoves.replace(". ", ".\n")
        bulbasaurInfoTextView.text = "$bulbasaurBio\n\n$bulbasaurMoves"

        // Charmander
        val charmanderInfoTextView: TextView = findViewById(R.id.charmander_info)
        val charmanderBio = getString(R.string.charmanderbio)
        var charmanderMoves = getString(R.string.charmandermoves)
        charmanderMoves = charmanderMoves.replace(". ", ".\n")
        charmanderInfoTextView.text = "$charmanderBio\n\n$charmanderMoves"

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
                    // Navigate to MatchHistory
                    val intent = Intent(this, MatchHistory::class.java)
                    startActivity(intent)
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