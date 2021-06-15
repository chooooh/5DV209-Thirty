package se.umu.chho0126.thirty.controller

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.ViewModelProviders
import se.umu.chho0126.thirty.databinding.ActivityMainBinding
import se.umu.chho0126.thirty.model.Dice
import se.umu.chho0126.thirty.model.Game
import se.umu.chho0126.thirty.viewModels.DiceViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var throwButton: Button
    private lateinit var calculateButton: Button
    private lateinit var resultTest: TextView
    private lateinit var diceImages: List<ImageView>

    private lateinit var binding: ActivityMainBinding

    private var game: Game = Game()

    private val diceViewModel: DiceViewModel by lazy {
        ViewModelProviders.of(this).get(DiceViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        throwButton = binding.throwButton
        calculateButton = binding.calculateButton
        resultTest = binding.resultTest
        diceImages = listOf(
            binding.diceOne,
            binding.diceTwo,
            binding.diceThree,
            binding.diceFour,
            binding.diceFive,
            binding.diceSix,
        )

        resultTest.text = "${diceViewModel.score}"

        updateAllViews()
        setupDiceImages()

        throwButton.setOnClickListener {
            diceViewModel.throwAllDices()
            updateAllViews()
            calculateScore()
        }

    }

    private fun calculateScore() {
        val dices: List<Dice> = diceViewModel.dices
        val score: Int = game.determineScore(dices)
        diceViewModel.score += score // undersök om detta ska in i game klassen
        resultTest.text = "${diceViewModel.score}"
    }

    private fun setupDiceImages() {
        for (i in diceImages.indices) {
            diceImages[i].setOnClickListener {
                diceViewModel.dices[i].toggleSelection()
                updateView(diceImages[i], diceViewModel.dices[i].view)
            }
        }
    }

    private fun updateAllViews() {
        for (i in diceImages.indices) {
            diceImages[i].setImageResource(diceViewModel.dices[i].view)
        }
    }

    private fun updateView(imageView: ImageView, resourceId: Int) {
        imageView.setImageResource(resourceId)
    }
}
