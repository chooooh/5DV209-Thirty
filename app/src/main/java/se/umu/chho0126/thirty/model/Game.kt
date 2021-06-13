package se.umu.chho0126.thirty.model

class Game {
    private var score: Int = 0

    fun determineScore(dices: List<Dice>): Int {
        return dices.sumOf { dice -> dice.value }
    }

}