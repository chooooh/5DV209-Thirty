package se.umu.chho0126.thirty.controller

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.ViewModelProviders
import se.umu.chho0126.thirty.R
import se.umu.chho0126.thirty.model.Dice
import se.umu.chho0126.thirty.model.Game
import se.umu.chho0126.thirty.viewModels.DiceViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var throwButton: Button
    private lateinit var calculateButton: Button
    private lateinit var resultTest: TextView
    private lateinit var diceViews: List<DiceView>

    private var game: Game = Game()

    /*
    private val diceViewModel: DiceViewModel by lazy {
        ViewModelProviders.of(this).get(DiceViewModel::class.java)
    }
    */

    data class DiceView(var view: ImageView, var dice: Dice = Dice())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        throwButton = findViewById(R.id.throw_button)
        calculateButton = findViewById(R.id.calculate_button)
        resultTest = findViewById(R.id.result_test)

        diceViews = listOf(
            DiceView(findViewById(R.id.dice_one)),
            DiceView(findViewById(R.id.dice_two)),
            DiceView(findViewById(R.id.dice_three)),
            DiceView(findViewById(R.id.dice_four)),
            DiceView(findViewById(R.id.dice_five)),
            DiceView(findViewById(R.id.dice_six))
        )

        diceViews[0].dice.toggleSelection()

        diceViews.forEach {
            updateDiceView(it)
        }

        throwButton.setOnClickListener {
            throwAllDices()
        }

        calculateButton.setOnClickListener {
            val dices: List<Dice> = getDices()
            val score: Int = game.determineScore(dices)
            resultTest.text = "$score"
        }

    }

    private fun throwDice(diceView: DiceView) {
        diceView.dice.throwDice()
        updateDiceView(diceView)
    }

    private fun throwAllDices() {
        diceViews.forEach {
            throwDice(it)
        }
    }

    private fun getDices(): List<Dice> {
        return diceViews.map { it.dice }
    }

    private fun updateDiceView(diceView: DiceView) {
        if (diceView.dice.isSelected) {
            when (diceView.dice.value) {
                1 -> diceView.view.setImageResource(R.drawable.grey1)
                2 -> diceView.view.setImageResource(R.drawable.grey2)
                3 -> diceView.view.setImageResource(R.drawable.grey3)
                4 -> diceView.view.setImageResource(R.drawable.grey4)
                5 -> diceView.view.setImageResource(R.drawable.grey5)
                6 -> diceView.view.setImageResource(R.drawable.grey6)
            }
        } else {
            when (diceView.dice.value) {
                1 -> diceView.view.setImageResource(R.drawable.white1)
                2 -> diceView.view.setImageResource(R.drawable.white2)
                3 -> diceView.view.setImageResource(R.drawable.white3)
                4 -> diceView.view.setImageResource(R.drawable.white4)
                5 -> diceView.view.setImageResource(R.drawable.white5)
                6 -> diceView.view.setImageResource(R.drawable.white6)
            }
        }
    }
}
