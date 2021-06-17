package se.umu.chho0126.thirty.viewModels

import android.util.Log
import android.widget.ImageView
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders
import se.umu.chho0126.thirty.R
import se.umu.chho0126.thirty.Util
import se.umu.chho0126.thirty.controller.MainActivity
import se.umu.chho0126.thirty.model.Dice
import se.umu.chho0126.thirty.model.Game

private const val TAG = "DiceViewModel"

class DiceViewModel : ViewModel() {
    var game: Game = Game()
    var dices: List<Dice> = game.getDices()

    fun throwAllDices() {
        game.throwDices()
    }

    fun createNewRound() {
        endGameRound()
        newGameRound()
    }

    fun newGame() {
        game = Game()
        dices = game.getDices()
        print()
    }

    private fun endGameRound() {
        game.endRound()
    }

    private fun newGameRound() {
        game.newRound()
        dices = game.getDices()
    }

    private fun print() {
        dices.forEach {
            Util.debugLog(it.value.toString())
        }

    }
    init {
        Log.d(TAG, "diceviewmodel initialized")
    }

    override fun onCleared() {
        super.onCleared()
        Log.d(TAG, "ViewModel instace about to be destroyed")
    }
}