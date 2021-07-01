package se.umu.chho0126.thirty.model

import junit.framework.TestCase
import org.junit.Test
import java.lang.IllegalArgumentException

class GameTest : TestCase() {

    private fun generateDices(vararg nums: Int): ArrayList<Dice> {
        val arr = ArrayList<Dice>()
        nums.forEach { arr.add(Dice(it))}
        return arr
    }

    private fun getNewGame(dices: ArrayList<Dice>): Game {
        val game = Game()
        game.currentRound.dices = dices
        return game
    }

    private fun setTrue(pos: IntArray, game: Game) {
        pos.forEach { game.currentRound.dices[it].isSelected = true }
    }

    private fun setAllTrue(game: Game) {
        game.currentRound.dices.forEach { it.isSelected = true }
    }

    fun testInvalidDetermineScore() {
        try {
            val game = getNewGame(generateDices(1, 1, 6, 4, 4, 6))
            game.currentRound.dices.forEach { it.isSelected = true }
            game.determineScore("4")
            fail("Exception did not occur")
        } catch (e: IllegalArgumentException) {
        }
    }

    fun testValidDetermineScore() {
        val game = getNewGame(generateDices(1,1,1,2,4,4))
        setTrue(intArrayOf(0,1,4,5), game)
        assertEquals(game.determineScore("5"), 10)
    }

    fun testValidDetermineScore2() {
        val game = getNewGame(generateDices(1,1,6,4,4,6))
        setTrue(intArrayOf(3,4), game)
        assertEquals(game.determineScore("4"), 8)
    }

    fun testValidDetermineScore3() {
        val game = getNewGame(generateDices(1,1,6,4,4,6))
        assertEquals(game.determineScore("4"), 0)
    }

    fun testInvalidDetermineScore2() {
        try {
            val game = getNewGame(generateDices(6,6,2,1,1,1))
            setTrue(intArrayOf(0,1,2), game)
            game.determineScore("7")
            fail("Exception did not occur")
        } catch (e: IllegalArgumentException) {
            println(e.message.toString())
        }
    }

    fun testInvalidDetermineScore3() {
        try {
            val game = getNewGame(generateDices(6,6,6,1,1,1))
            setTrue(intArrayOf(0,1,2), game)
            game.determineScore("9")
            fail("Exception did not occur")
        } catch (e: IllegalArgumentException) {
            println(e.message.toString())
        }
    }

    fun testInvalidDetermineScore4() {
        try {
            val game = getNewGame(generateDices(6,1,6,1,6,2))
            setAllTrue(game)
            game.determineScore("11")
            fail("Exception did not occur")
        } catch (e: IllegalArgumentException) {
            println(e.message.toString())
        }
    }

    fun testInvalidDetermineScore5() {
        try {
            val game = getNewGame(generateDices(6,5,6,4,1,1))
            setTrue(intArrayOf(0,1,2,3), game)
            game.determineScore("11")
            fail("Exception did not occur")
        } catch (e: IllegalArgumentException) {
            println(e.message.toString())
        }
    }
}