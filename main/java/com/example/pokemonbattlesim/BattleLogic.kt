import com.example.pokemonbattlesim.Move
import com.example.pokemonbattlesim.Pokemon
import com.example.pokemonbattlesim.R

class BattleLogic {
    private val pokemons = initPokemon()

    fun getPokemonImage(pokemonName: String): Int = pokemons[pokemonName]?.imageResId ?: R.drawable.missingno

    fun getPokemonByName(name: String): Pokemon? = pokemons[name]

    fun getPokemons(): List<Pokemon> = pokemons.values.toList()

    fun performAttack(attacker: Pokemon, defender: Pokemon, moveName: String): Double {
        val move = attacker.moves.find { it.name == moveName }
        move?.effect?.invoke(attacker, defender)

        if(attacker.leeched){
            attacker.health -= 3
            defender.health += 3
        }
        return defender.health
    }

    fun resetPokemon(attacker: Pokemon, defender: Pokemon){
        attacker.health = 50.0
        attacker.attack = 1.0
        attacker.defense = 1.0
        attacker.leeched = false
        defender.health = 50.0
        defender.attack = 1.0
        defender.defense = 1.0
        defender.leeched = false
    }

    private fun initPokemon(): Map<String, Pokemon> {
        val rageEffect = { attacker: Pokemon, defender: Pokemon ->
            attacker.attack += 0.2 // increases attack power
            if(attacker.attack > 1.5){
                attacker.attack = 1.5 //attack doesn't increase over 50%
            }
        }
        val leerEffect = { attacker: Pokemon, defender: Pokemon ->
            defender.defense -= 0.1 // Reduces the defense by 10%
            if (defender.defense < 0.5) { //defense doesn't reduce below 50%
                defender.defense = 0.5
            }
        }
        val growlEffect = { attacker: Pokemon, defender: Pokemon ->
            defender.attack -= 0.1 // Reduces the attack multiplier by 10%
            if (defender.attack < 0.5) { //attack doesn't reduce below 50%
                defender.attack = 0.5
            }
        }
        val tailWhipEffect = { attacker: Pokemon, defender: Pokemon ->
            defender.defense -= 0.1 // Reduces the defense by 10%
            if (defender.defense < 0.5) { //defense doesn't reduce below 50%
                defender.defense = 0.5
            }
        }
        val withdrawEffect = { attacker: Pokemon, defender: Pokemon ->
            defender.defense += 0.2 // increases the defense by 10%
            if (defender.defense > 1.5) { //defense doesn't increase above 50%
                defender.defense = 1.5
            }
        }
        val leechSeedEffect= { attacker: Pokemon, defender: Pokemon ->
            defender.leeched = true
        }
        val vineWhipEffect= { attacker: Pokemon, defender: Pokemon ->
            val Base = 10
            val Bonus = if(defender.name == "Squirtle") 5 else 0
            defender.health -=((Base + Bonus) * attacker.attack)/defender.defense
        }
        val emberEffect= { attacker: Pokemon, defender: Pokemon ->
            val Base = 10
            val Bonus = if(defender.name == "Bulbasaur") 5 else 0
            defender.health -=((Base + Bonus) * attacker.attack)/defender.defense
        }
        val waterGunEffect= { attacker: Pokemon, defender: Pokemon ->
            val Base = 10
            val Bonus = if(defender.name == "Charmander") 5 else 0
            defender.health -=((Base + Bonus) * attacker.attack)/defender.defense
        }

        return mapOf(
            "Bulbasaur" to Pokemon(
                name = "Bulbasaur",
                imageResId = R.drawable.bulbasaur,
                moves = listOf(
                    Move("Tackle", 10, {a, d -> d.health -= (10 * a.attack)/d.defense}), //deals light damage
                    Move("Vine Whip", 10, vineWhipEffect), //deals moderate damage, bonus damage against squritle
                    Move("Growl", 0, growlEffect), //reduces opponent's attacking power
                    Move("Leech Seed", 0, leechSeedEffect) //deal a small amount of damage each turn, restore same amount of health to self
                )
            ),
            "Charmander" to Pokemon(
                name = "Charmander",
                imageResId = R.drawable.charmander,
                moves = listOf(
                    Move("Scratch", 10, {a, d -> d.health -= (10 * a.attack)/d.defense}),//deals light damage
                    Move("Rage", 0, rageEffect), //increase attacking power every time damage is taken
                    Move("Ember", 10, emberEffect), //deals moderate damage, bonus damage against bulbasaur
                    Move("Leer", 0, leerEffect) //reduces defense of opponent
                )
            ),
            "Squirtle" to Pokemon(
                name = "Squirtle",
                imageResId = R.drawable.squirtle,
                moves = listOf(
                    Move("Tackle", 10, {a, d -> d.health -= (10 * a.attack)/d.defense}), //deals light damage
                    Move("Water Gun", 10, waterGunEffect), //deals moderate damage, bonus damage against charmander
                    Move("Tail Whip", 0,tailWhipEffect), //reduces defense of opponent
                    Move("Withdraw", 0, withdrawEffect) // increases defense of self
                )
            )
        )
    }
}