package se.umu.chho0126.thirty.model

import android.content.Intent
import se.umu.chho0126.thirty.Util
import java.lang.IllegalStateException


class Game {
    var isGameFinished: Boolean = false
    var currentScore: Int = 0
    var roundsLeft: Int = 2
    var round: Round = Round()

    fun newRound() {
        round = Round()
    }

    fun endRound(): Int {
        if (roundsLeft <= 0)
            isGameFinished = true
        currentScore += determineScore()
        roundsLeft--
        newRound()
        return currentScore
    }

    private fun determineScore(): Int {
        val dices = getDices()
        return dices.sumOf { it.value }
    }

    /**
     * retrieve a reference to the running round's dices
     */
    fun getDices(): List<Dice> {
        return round.dices
    }

    /**
     * check if round is finished
     * @return is round finished
     */
    fun isRoundFinished(): Boolean {
        return round.isRoundFinished()
    }

    fun throwDices() {
        Util.debugLog("Game: $currentScore")
        round.throwAllDices()
    }


}

