package se.umu.chho0126.thirty.model

import se.umu.chho0126.thirty.Util

class Round(val dices: List<Dice> = List(6) { Dice() }, var tossesRemaining: Int = 2) {
    var score: Int = 0

    fun throwAllDices() {
        if (isRoundFinished())
            return

        dices.forEach {
            if (!it.isSelected) {
                it.throwDice()
            } else {
                it.toggleSelection()
            }
        }
        Util.debugLog("round: $tossesRemaining")
        tossesRemaining--
    }

    fun isRoundFinished(): Boolean {
        return tossesRemaining <= 0
    }


}