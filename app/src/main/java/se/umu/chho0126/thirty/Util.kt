package se.umu.chho0126.thirty

import android.util.Log
import se.umu.chho0126.thirty.model.Dice

private const val TAG = "debugging"

class Util {
    companion object {
        fun debugLog(toLog: String) {
            Log.d(TAG, toLog)
        }

        fun currentDices(dices: List<Dice>) {
            dices.forEach { debugLog(it.value.toString()) }

        }
    }
}