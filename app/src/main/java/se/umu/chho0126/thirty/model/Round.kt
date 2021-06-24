package se.umu.chho0126.thirty.model

import android.os.Parcel
import android.os.Parcelable

/**
 * Represents a game Round. Implements the Parcelable interface.
 * This class track the score for the current round.
 */
class Round(var dices: ArrayList<Dice> = arrayListOf(Dice(), Dice(), Dice(), Dice(), Dice(), Dice()), var tossesRemaining: Int = 2) : Parcelable {
    var score: Int = 0

    constructor(parcel: Parcel) : this(
        parcel.createTypedArrayList(Dice.CREATOR) as ArrayList<Dice>,
        parcel.readInt()
    ) {
        score = parcel.readInt()
    }


    /**
     * Throws all non selected dices if round is not finished, otherwise do nothing.
     */
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
        tossesRemaining--
    }

    private fun isRoundFinished(): Boolean {
        return tossesRemaining <= 0
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeTypedList(dices)
        parcel.writeInt(tossesRemaining)
        parcel.writeInt(score)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Round> {
        override fun createFromParcel(parcel: Parcel): Round {
            return Round(parcel)
        }

        override fun newArray(size: Int): Array<Round?> {
            return arrayOfNulls(size)
        }
    }

}