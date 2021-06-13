package se.umu.chho0126.thirty.model

import android.widget.TextView

class Dice {
    var value: Int
    var isSelected: Boolean = false

    init {
        value = IntRange(1, 6).random()
    }

    fun throwDice() {
        value = IntRange(1, 6).random()
    }

    fun toggleSelection() {
        isSelected = !isSelected
    }

}

