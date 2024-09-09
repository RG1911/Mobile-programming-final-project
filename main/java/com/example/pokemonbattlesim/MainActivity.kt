package com.example.pokemonbattlesim
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide

class MainActivity : AppCompatActivity() {
    private lateinit var mainLayout: View
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        mainLayout = findViewById(R.id.main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val sharedPreferences = getSharedPreferences("AppSettingsPrefs", Context.MODE_PRIVATE)
        val savedColor = sharedPreferences.getInt("backgroundColor", ContextCompat.getColor(this, R.color.Blue))
        mainLayout.setBackgroundColor(savedColor)

        // val squadGif: ImageView = findViewById(R.id.squadGif)
       //  Glide.with(this)
        //    .asGif()
          //  .load(R.drawable.squad)
            //.into(squadGif)
    }
    fun startClick(view: View) {
        val intent = Intent(this, PokemonSelect::class.java)
        startActivity(intent)
    }

    fun historyClick(view: View) {
        val intent = Intent(this, MatchHistory::class.java)
        startActivity(intent)
    }

    fun settingsClick(view: View) {
        val intent = Intent(this, Settings::class.java)
        startActivity(intent)
    }

    fun pokedexClick(view: View) {
        val intent = Intent(this, Pokedex::class.java)
        startActivity(intent)
    }

}