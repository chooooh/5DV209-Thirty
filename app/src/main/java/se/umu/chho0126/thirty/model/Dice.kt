package se.umu.chho0126.thirty.model

import android.os.Parcel
import android.os.Parcelable
import se.umu.chho0126.thirty.R
import java.lang.IllegalArgumentException

/**
 * Represents a dice. Implements the Parcelable interface.
 */
class Dice(var value: Int = IntRange(1,6).random()) : Parcelable {
    var view: Int
    var isSelected: Boolean

    /**
     * Required by the Parcelable interface.
     */
    constructor(parcel: Parcel) : this(parcel.readInt()) {
        view = parcel.readInt()
        isSelected = parcel.readByte() != 0.toByte()
    }

    /**
     * Initializes properties. Dices are initially not selected.
     */
    init {
        isSelected = false
        view = updateDiceView()
    }

    /**
     * Tosses this dice by assigning a random number and the updating the view
     * with the corresponding value.
     */
    fun throwDice() {
        value = IntRange(1, 6).random()
        view = updateDiceView()
    }

    /**
     * Toggles the dice selection
     */
    fun toggleSelection() {
        isSelected = !isSelected
        view = updateDiceView()
    }

    private fun updateDiceView(): Int {
        return if (isSelected) {
            when (value) {
                1 -> R.drawable.grey1
                2 -> R.drawable.grey2
                3 -> R.drawable.grey3
                4 -> R.drawable.grey4
                5 -> R.drawable.grey5
                6 -> R.drawable.grey6
                else -> throw IllegalArgumentException("Invalid dice value")
            }
        } else {
            when (value) {
                1 -> R.drawable.white1
                2 -> R.drawable.white2
                3 -> R.drawable.white3
                4 -> R.drawable.white4
                5 -> R.drawable.white5
                6 -> R.drawable.white6
                else -> throw IllegalArgumentException("Invalid dice value")
            }
        }
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(value)
        parcel.writeInt(view)
        parcel.writeByte(if (isSelected) 1 else 0)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Dice> {
        override fun createFromParcel(parcel: Parcel): Dice {
            return Dice(parcel)
        }

        override fun newArray(size: Int): Array<Dice?> {
            return arrayOfNulls(size)
        }
    }
}

