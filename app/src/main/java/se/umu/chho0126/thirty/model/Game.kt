package se.umu.chho0126.thirty.model

import android.os.Parcel
import android.os.Parcelable
import se.umu.chho0126.thirty.Util
import java.lang.IllegalArgumentException


class Game() : Parcelable {
    var previousRounds: ArrayList<Round> = ArrayList()
    var isGameFinished: Boolean = false
    var currentScore: Int = 0
    var roundsLeft: Int = 9
    var currentRound: Round = Round()

    constructor(parcel: Parcel) : this() {
        isGameFinished = parcel.readByte() != 0.toByte()
        currentScore = parcel.readInt()
        roundsLeft = parcel.readInt()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeByte(if (isGameFinished) 1 else 0)
        parcel.writeInt(currentScore)
        parcel.writeInt(roundsLeft)
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

    fun getPreviousScores(): IntArray {
        return previousRounds.map {
            it.score
        }.toIntArray()
    }

    fun newRound() {
        currentRound = Round()
    }

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
            else -> -1
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

    private fun determineScore(result: IntArray, target: Int) {
        val subsetList = subsets(result)
        val output = arrayListOf<ArrayList<Int>>()
        for (subset in subsetList) {
            var sum = 0
            for (num in subset) {
                sum += num
            }
            if (sum == target)
                output.add(subset)
        }
        println(output)
    }

    private fun subsets(arr: IntArray): ArrayList<ArrayList<Int>> {
        val output = arrayListOf<ArrayList<Int>>()
        output.add(arrayListOf<Int>())
        for (num in arr) {
            val newSubset = arrayListOf<ArrayList<Int>>()
            for (curr in output) {
                val sub = ArrayList(curr)
                sub.add(num)
                newSubset.add(sub)
            }
            for (prev in newSubset) {
                output.add(prev)
            }
        }
        return output
    }

    /**
     * retrieve a reference to the running round's dices
     */
    fun getDices(): List<Dice> {
        return currentRound.dices
    }

    /**
     * check if round is finished
     * @return is round finished
     */
    fun isRoundFinished(): Boolean {
        return currentRound.isRoundFinished()
    }

    fun lastToss(): Boolean {
        return currentRound.tossesRemaining <= 1
    }

    fun throwDices() {
        currentRound.throwAllDices()
    }


}

