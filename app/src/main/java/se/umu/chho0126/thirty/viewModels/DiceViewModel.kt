package se.umu.chho0126.thirty.viewModels

import android.widget.ImageView
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders
import se.umu.chho0126.thirty.R
import se.umu.chho0126.thirty.controller.MainActivity
import se.umu.chho0126.thirty.model.Dice

class DiceViewModel : ViewModel() {

    var dices: List<Dice> = List(6) { Dice() }

    fun throwDice(dice: Dice) {
        dice.throwDice()
    }

    fun throwAllDices() {
        dices.forEach {
            throwDice(it)
        }
    }

    fun toggleSelection(dice: Dice) {
        dice.toggleSelection()
    }

}