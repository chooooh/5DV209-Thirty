package se.umu.chho0126.thirty

import android.util.Log

private const val TAG = "debugging"

class Util {
    companion object {
        fun debugLog(toLog: String) {
            Log.d(TAG, toLog)
        }
    }
}