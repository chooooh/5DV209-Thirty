package se.umu.chho0126.thirty.viewModels

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import se.umu.chho0126.thirty.Util
import se.umu.chho0126.thirty.model.Dice
import se.umu.chho0126.thirty.model.Game

private const val TAG = "GameViewModel"
private const val DICES = "state_dices"
private const val GAME = "state_game"

class GameViewModel(val state: SavedStateHandle) : ViewModel() {
    var game: Game
    var dices: List<Dice>

    init {
        Log.d(TAG, "diceviewmodel initialized")
        game = state.get<Game>(GAME) ?: Game()
        dices = game.getDices()
    }

    fun throwAllDices() {
        game.throwDices()
        state.set(GAME, game)
    }

    fun createNewRound(choice: String) {
        game.endRound(choice)
        game.newRound()
        dices = game.getDices()
    }

    fun newGame() {
        game = Game()
        dices = game.getDices()
    }

    override fun onCleared() {
        super.onCleared()
        Log.d(TAG, "ViewModel instace about to be destroyed")
    }
}