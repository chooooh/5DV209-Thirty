package se.umu.chho0126.thirty.viewModels

import android.util.Log
import android.widget.ImageView
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders
import se.umu.chho0126.thirty.R
import se.umu.chho0126.thirty.controller.MainActivity
import se.umu.chho0126.thirty.model.Dice

private const val TAG = "DiceViewModel"

class DiceViewModel : ViewModel() {

    var dices: List<Dice> = List(6) { Dice() }
    var score: Int = 0

    fun throwAllDices() {
        dices.forEach {
            if (!it.isSelected) {
                it.throwDice()
            } else {
                toggleSelection(it)
            }
        }
    }

    fun toggleSelection(dice: Dice) {
        dice.toggleSelection()
        Log.d(TAG, "------------")
        dices.forEach { Log.d(TAG, "${it.value}") }
    }

    init {
        Log.d(TAG, "diceviewmodel initialized")
    }

    override fun onCleared() {
        super.onCleared()
        Log.d(TAG, "ViewModel instace about to be destroyed")
    }
}