package se.umu.chho0126.thirty.model

class Round(var tossesRemaining: Int = 3) {
    fun decrementTosses() {
        tossesRemaining--
    }
}