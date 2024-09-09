package com.example.pokemonbattlesim

data class Pokemon(
    val name: String,
    val imageResId: Int,
    var health: Double = 50.0,
    var attack: Double = 1.0,
    var defense: Double = 1.0,
    val moves: List<Move>,
    var leeched: Boolean = false
)

class Move(
    val name: String,
    val damage: Int,
    val effect: (Pokemon, Pokemon) -> Unit
)