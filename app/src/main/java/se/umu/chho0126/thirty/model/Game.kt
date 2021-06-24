package se.umu.chho0126.thirty.model

import android.os.Parcel
import android.os.Parcelable
import java.lang.IllegalArgumentException

/**
 * Represents a Game of Thirty. Implements the Parcelable interface.
 * This class contains various properties.
 * @property previousRounds An ArrayList containing previous game rounds
 * @property isGameFinished Boolean indicating if game is finished
 * @property currentRound Total score
 * @property roundsLeft Number of rounds remaining until game is finished
 * @property currentRound Current game round instance
 */
class Game() : Parcelable {
    var previousRounds: ArrayList<Round> = ArrayList()
    var isGameFinished: Boolean = false
    var currentScore: Int = 0
    var roundsLeft: Int = 9
    var currentRound: Round = Round()

    constructor(parcel: Parcel) : this() {
        previousRounds = parcel.createTypedArrayList(Round.CREATOR) as ArrayList<Round>
        isGameFinished = parcel.readByte() != 0.toByte()
        currentScore = parcel.readInt()
        roundsLeft = parcel.readInt()
        currentRound = parcel.readParcelable(Round::class.java.classLoader)!!
    }

    /**
     * Retrieve previous scores as an IntArray.
     * @return an IntArray containing previous scores.
     */
    fun getPreviousScores(): IntArray {
        return previousRounds.map { it.score }.toIntArray()
    }

    /**
     * Establishes a new round by instantiating and assigning a new round
     */
    fun newRound() {
        currentRound = Round()
    }

    /**
     * Ends an ongoing round and calculates the score based on the specified scoring mode selection.
     * @param choice the scoring selection
     * @return The score
     */
    fun endRound(choice: String): Int {
        if (roundsLeft <= 0) {
            isGameFinished = true
        }
        val roundScore = determineScore(choice)
        currentScore += roundScore
        currentRound.score = roundScore
        previousRounds.add(currentRound)
        roundsLeft--
        newRound()
        return currentScore
    }

    /**
     * Retrieves the dices of the current round
     * @return An ArrayList containing the dices of the current round.
     */
    fun getDices(): ArrayList<Dice> {
        return currentRound.dices
    }

    /**
     * Throws selected dices.
     */
    fun throwDices() {
        currentRound.throwAllDices()
    }

    private fun determineScore(choice: String): Int {
        val dices = getDices()
        return when (choice) {
            "Low" -> scoreLow(dices)
            "4" -> scoreNumber(dices,4)
            "5" -> scoreNumber(dices, 5)
            "6" -> scoreNumber(dices, 6)
            "7" -> scoreNumber(dices, 7)
            "8" -> scoreNumber(dices, 8)
            "9" -> scoreNumber(dices, 9)
            "10" -> scoreNumber(dices, 10)
            "11" -> scoreNumber(dices, 11)
            "12" -> scoreNumber(dices, 12)
            else -> throw IllegalArgumentException("dices passed are not valid")
        }
    }

    private fun scoreNumber(dices: List<Dice>, target: Int): Int {
        val selectedDices = dices.filter { it.isSelected }
        val selected = selectedDices.size
        val sum = selectedDices.sumOf { it.value }
        if (selected <= 0) throw IllegalArgumentException("you must select dices!")
        if (sum % target != 0) throw IllegalArgumentException("invalid choices")
        return sum
    }

    private fun scoreLow(dices: List<Dice>): Int {
        return dices.filter { it.value <= 3 }.sumOf { it.value }
    }


    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeTypedList(previousRounds)
        parcel.writeByte(if (isGameFinished) 1 else 0)
        parcel.writeInt(currentScore)
        parcel.writeInt(roundsLeft)
        parcel.writeParcelable(currentRound, flags)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Game> {
        override fun createFromParcel(parcel: Parcel): Game {
            return Game(parcel)
        }

        override fun newArray(size: Int): Array<Game?> {
            return arrayOfNulls(size)
        }
    }

}