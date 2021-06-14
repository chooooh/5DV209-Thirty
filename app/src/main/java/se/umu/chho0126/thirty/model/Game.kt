package se.umu.chho0126.thirty.model

class Game {
    private var round: Int = 0
    private var score: Int = 0
    private var currentRound: Round = Round()

    fun finishRound(dices: List<Dice>) {
         score += determineScore(dices)
    }

    fun determineScore(dices: List<Dice>): Int {
        return dices.sumOf { dice -> dice.value }
    }


}