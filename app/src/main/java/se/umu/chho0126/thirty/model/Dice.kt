package se.umu.chho0126.thirty.model

import android.widget.TextView
import se.umu.chho0126.thirty.R
import se.umu.chho0126.thirty.controller.MainActivity
import java.lang.IllegalArgumentException

class Dice(var value: Int = IntRange(1,6).random()) {
    var view: Int
    var isSelected: Boolean

    init {
        isSelected = false
        view = updateDiceView()
    }

    fun throwDice() {
        value = IntRange(1, 6).random()
        view = updateDiceView()
    }

    fun toggleSelection() {
        isSelected = !isSelected
        view = updateDiceView()
    }

    private fun updateDiceView(): Int {
        return if (isSelected) {
            when (value) {
                1 -> R.drawable.grey1
                2 -> R.drawable.grey2
                3 -> R.drawable.grey3
                4 -> R.drawable.grey4
                5 -> R.drawable.grey5
                6 -> R.drawable.grey6
                else -> throw IllegalArgumentException("Invalid dice value")
            }
        } else {
            when (value) {
                1 -> R.drawable.white1
                2 -> R.drawable.white2
                3 -> R.drawable.white3
                4 -> R.drawable.white4
                5 -> R.drawable.white5
                6 -> R.drawable.white6
                else -> throw IllegalArgumentException("Invalid dice value")
            }
        }
    }
}

