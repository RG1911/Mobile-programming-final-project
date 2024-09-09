package com.example.pokemonbattlesim

import BattleLogic
import android.content.Context
import android.content.Intent
import android.icu.text.DecimalFormat
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat


class Battle : AppCompatActivity() {
    private lateinit var BattleScreen: View
    private lateinit var logic: BattleLogic
    private lateinit var currentPlayer: Pokemon
    private lateinit var opponentPlayer: Pokemon
    private var lastMoveUsed: String = ""
    private var turnNumber: Int = 1
    private var isPlayerOneTurn: Boolean = true
    var dform: DecimalFormat = DecimalFormat("#.#")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_battle)
        BattleScreen = findViewById(R.id.Battle)
        val sharedPreferences = getSharedPreferences("AppSettingsPrefs", Context.MODE_PRIVATE)
        val savedColor = sharedPreferences.getInt("backgroundColor", ContextCompat.getColor(this, R.color.Blue))
        BattleScreen.setBackgroundColor(savedColor)

        logic = BattleLogic()
        initializeGame()

        val returnToTitleButton = findViewById<Button>(R.id.ReturnToTitleButtion)
        returnToTitleButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        val rematchButton = findViewById<Button>(R.id.rematchButton)
        rematchButton.setOnClickListener {
            logic.resetPokemon(currentPlayer, opponentPlayer)
            updateHealthDisplays()
            turnNumber = 1
            returnToTitleButton.visibility = View.INVISIBLE
            rematchButton.visibility = View.INVISIBLE
        }
    }

    private fun initializeGame() {
        currentPlayer = logic.getPokemonByName(intent.getStringExtra("FIRST_POKEMON") ?: "missingno")!!
        opponentPlayer = logic.getPokemonByName(intent.getStringExtra("SECOND_POKEMON") ?: "missingno")!!

        setupViews()
        updateMoveButtons(currentPlayer)
    }

    private fun setupViews() {
        findViewById<ImageView>(R.id.pokemon1).setImageResource(currentPlayer.imageResId)
        findViewById<ImageView>(R.id.pokemon2).setImageResource(opponentPlayer.imageResId)
        findViewById<TextView>(R.id.pokemon1health).text = "HP: ${currentPlayer.health}/50.0"
        findViewById<TextView>(R.id.pokemon2health).text = "HP: ${opponentPlayer.health}/50.0"

        findViewById<Button>(R.id.SelectMoveButton).setOnClickListener {
            performMove()
        }
    }

    private fun updateMoveButtons(pokemon: Pokemon) {
        val moves = pokemon.moves
        val moveButtons = listOf(
            findViewById<RadioButton>(R.id.move1button),
            findViewById<RadioButton>(R.id.move2button),
            findViewById<RadioButton>(R.id.move3button),
            findViewById<RadioButton>(R.id.move4button)
        )
        moves.forEachIndexed { index, move ->
            moveButtons[index].text = move.name
        }
    }

    private fun performMove() {
        val moveRadioGroup = findViewById<RadioGroup>(R.id.MoveRadioGroup)
        val selectedMoveId = moveRadioGroup.checkedRadioButtonId
        val selectedMove = findViewById<RadioButton>(selectedMoveId).text.toString()

        logic.performAttack(currentTurnPlayer(), opponent(), selectedMove)
        updateHealthDisplays()
        checkForWin()
        swapTurns()
        lastMoveUsed = selectedMove
    }

    private fun updateHealthDisplays() {
        val healthPlayer1 = findViewById<TextView>(R.id.pokemon1health)
        val healthPlayer2 = findViewById<TextView>(R.id.pokemon2health)

        healthPlayer1.text = "HP: ${dform.format(logic.getPokemonByName(intent.getStringExtra("FIRST_POKEMON") ?: "missingno")!!.health)}/50.0"
        healthPlayer2.text = "HP: ${dform.format(logic.getPokemonByName(intent.getStringExtra("SECOND_POKEMON") ?: "missingno")!!.health)}/50.0"
    }

    private fun checkForWin() {
        if (currentPlayer.health <= 0) {
            finishBattle("${opponentPlayer.name} wins!")
        } else if (opponentPlayer.health <= 0) {
            finishBattle("${currentPlayer.name} wins!")
        }
    }

    private fun saveBattleHistory(winner: String) {
        val sharedPreferences = getSharedPreferences("AppSettingsPrefs", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        val history = sharedPreferences.getString("battleHistory", "") ?: ""
        val newHistory = "$history\n$winner Won the battle in $turnNumber turns"
        editor.putString("battleHistory", newHistory)
        editor.apply()
    }

    private fun finishBattle(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
        saveBattleHistory(message)
        // Show rematch and return to title buttons
        findViewById<Button>(R.id.rematchButton).visibility = View.VISIBLE
        findViewById<Button>(R.id.ReturnToTitleButtion).visibility = View.VISIBLE
    }
    private fun currentTurnPlayer(): Pokemon = if (isPlayerOneTurn) currentPlayer else opponentPlayer
    private fun opponent(): Pokemon = if (isPlayerOneTurn) opponentPlayer else currentPlayer

    private fun swapTurns() {
        isPlayerOneTurn = !isPlayerOneTurn
        updateMoveButtons(currentTurnPlayer())
        findViewById<TextView>(R.id.TurnAndPlayerText).text = "Player ${if (isPlayerOneTurn) "1" else "2"}'s Turn"
        if(isPlayerOneTurn){
            turnNumber++
        }
    }

}
