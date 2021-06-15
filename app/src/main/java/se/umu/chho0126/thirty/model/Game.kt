package se.umu.chho0126.thirty.model

class Game {
    private var thrown: Int = 0
    private var round: Int = 0
    private var score: Int = 0
    private var currentRound: Round = Round()

    fun finishRound() {
        round++
    }

    fun determineScore(dices: List<Dice>): Int {
        if (thrown++ >= 3)
            finishRound()
        return dices.sumOf { dice -> dice.value }
    }
}