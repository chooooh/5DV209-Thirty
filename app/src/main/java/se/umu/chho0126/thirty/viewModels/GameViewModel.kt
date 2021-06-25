package se.umu.chho0126.thirty.viewModels

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import se.umu.chho0126.thirty.model.Dice
import se.umu.chho0126.thirty.model.Game

private const val TAG = "GameViewModel"
private const val DICES = "state_dices"
private const val GAME = "state_game"

/**
 * ViewModel that persists the Game state on configuration changes and process death.
 */
class GameViewModel(private val state: SavedStateHandle) : ViewModel() {
    var game: Game
    var dices: ArrayList<Dice>

    /**
     * Retrieve saved state if it exists, otherwise create new instances.
     */
    init {
        Log.d(TAG, "diceviewmodel initialized")
        game = state.get<Game>(GAME) ?: Game()
        dices = state.get<ArrayList<Dice>>(DICES) ?: game.getDices()
        game.currentRound.dices = dices
    }

    /**
     * Throws selected dices and saves state.
     */
    fun throwAllDices() {
        game.throwDices()
        setState()
    }

    /**
     * Creates a new round and saves state.
     * @param choice The string that determines the scoring mode selection.
     */
    fun createNewRound(choice: String) {
        game.endRound(choice)
        game.newRound()
        dices = game.getDices()
        setState()
    }

    /**
     * Creates a new game.
     */
    fun newGame() {
        game = Game()
        dices = game.getDices()
        setState()
    }

    private fun setState() {
        state.set(GAME, game)
        state.set(DICES, dices)
    }
}