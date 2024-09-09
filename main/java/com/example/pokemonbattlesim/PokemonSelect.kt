package com.example.pokemonbattlesim

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.RadioButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.example.pokemonbattlesim.MainActivity
import com.example.pokemonbattlesim.MatchHistory

class PokemonSelect : AppCompatActivity() {
    private var firstPokemon: String? = null
    private var secondPokemon: String? = null
    private lateinit var pokemonSelectActivity: View
    private lateinit var bulbasaurButton: RadioButton
    private lateinit var charmanderButton: RadioButton
    private lateinit var squirtleButton: RadioButton
    private lateinit var bottomNavigation: BottomNavigationView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pokemon_select)
        pokemonSelectActivity = findViewById(R.id.PokemonSelect)
        val sharedPreferences = getSharedPreferences("AppSettingsPrefs", Context.MODE_PRIVATE)
        val savedColor = sharedPreferences.getInt("backgroundColor", ContextCompat.getColor(this, R.color.Blue))
        pokemonSelectActivity.setBackgroundColor(savedColor)

        initRadioButtons()
        setupListeners()
        initBottomNavigation()

        val squirtleGif: ImageView = findViewById(R.id.squirtleGif)
        Glide.with(this)
            .asGif()
            .load(R.drawable.squirtlesquad)
            .into(squirtleGif)
    }


    private fun initRadioButtons() {
        bulbasaurButton = findViewById(R.id.BulbasaurButton)
        charmanderButton = findViewById(R.id.CharmanderButton)
        squirtleButton = findViewById(R.id.SquirtleButton)
    }

    private fun setupListeners() {
        val selectButton = findViewById<Button>(R.id.selectButton)
        val startGameButton = findViewById<Button>(R.id.startGameButton)
        val playerSelectTitle = findViewById<TextView>(R.id.pokemonselecttitle)

        val radioButtons = listOf(bulbasaurButton, charmanderButton, squirtleButton)
        radioButtons.forEach { radioButton ->
            radioButton.setOnClickListener { handleRadioButtonSelection(radioButton, radioButtons) }
        }

        selectButton.setOnClickListener {
            radioButtons.firstOrNull { it.isChecked }?.let { radioButton ->
                val currentSelection = radioButton.text.toString()
                if (firstPokemon == null) {
                    firstPokemon = currentSelection
                    playerSelectTitle.text = "Player 2 select your pokemon"
                } else if (secondPokemon == null) {
                    secondPokemon = currentSelection
                    startGameButton.visibility = View.VISIBLE
                    Toast.makeText(this, "Both Pokemon are locked in. Are you ready to battle?", Toast.LENGTH_LONG).show()
                } else {
                    Toast.makeText(this, "Both selections are already made", Toast.LENGTH_SHORT).show()
                }
            }
        }

        startGameButton.setOnClickListener {
            val intent = Intent(this, Battle::class.java).apply {
                putExtra("FIRST_POKEMON", firstPokemon)
                putExtra("SECOND_POKEMON", secondPokemon)
            }
            startActivity(intent)
        }
    }

    private fun handleRadioButtonSelection(selectedButton: RadioButton, buttons: List<RadioButton>) {
        buttons.forEach { it.isChecked = false }
        selectedButton.isChecked = true
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