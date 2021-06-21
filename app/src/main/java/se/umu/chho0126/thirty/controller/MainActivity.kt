package se.umu.chho0126.thirty.controller

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import se.umu.chho0126.thirty.databinding.ActivityMainBinding
import se.umu.chho0126.thirty.R
import se.umu.chho0126.thirty.viewModels.GameViewModel
import java.lang.IllegalArgumentException

private const val TAG = "MainActivity"
class MainActivity : AppCompatActivity() {

    private lateinit var diceImages: List<ImageView>
    private lateinit var binding: ActivityMainBinding
    private lateinit var currentChoice: String
    private lateinit var toast: Toast
    private lateinit var gameViewModel: GameViewModel


    /*
    private val gameViewModel: GameViewModel by lazy {
        ViewModelProviders.of(this).get(GameViewModel::class.java)
    }
     */

    private val startForResult: ActivityResultLauncher<Intent> = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        if (it.resultCode == Activity.RESULT_OK){
            showToast("new game started!")
        }
        gameViewModel.newGame()
        updateAllViews()
        updateScore()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        gameViewModel = ViewModelProvider(this).get(GameViewModel::class.java)

        diceImages = listOf(
            binding.diceOne,
            binding.diceTwo,
            binding.diceThree,
            binding.diceFour,
            binding.diceFive,
            binding.diceSix,
        )

        toast = Toast.makeText(this, "new game started!", Toast.LENGTH_SHORT)
        binding.resultTest.text = "${gameViewModel.game.currentScore}"

        setupDiceImageListeners()
        setupSpinner()

        binding.throwButton.setOnClickListener {
            if (gameViewModel.game.isGameFinished) {
                startForResult.launch(ScoreActivity.newIntent(this, gameViewModel.game.getPreviousScores()))
            }

            if (gameViewModel.game.currentRound.tossesRemaining <= 1) {
                binding.throwButton.isEnabled = false
            }

            gameViewModel.throwAllDices()

            updateAllViews()
        }

        binding.calculateButton.setOnClickListener {
            try {
                if (gameViewModel.game.isGameFinished) {
                    startForResult.launch(ScoreActivity.newIntent(this, gameViewModel.game.getPreviousScores()))
                } else {
                    showToast("rounds remaining: ${gameViewModel.game.roundsLeft}")
                    newRound()
                }
            } catch(e: IllegalArgumentException) {
                showToast(e.message.toString())
            }
        }

    }

    private fun showToast(msg: String) {
        toast.setText(msg)
        toast.show()
    }

    private fun newRound() {
        gameViewModel.createNewRound(currentChoice)
        updateAllViews()
        updateScore()
        binding.throwButton.isEnabled = true
    }

    private fun setupSpinner() {
        ArrayAdapter.createFromResource(this, R.array.choices_array, android.R.layout.simple_spinner_item).also {
            it.setDropDownViewResource(android.R.layout.simple_spinner_item)
            binding.choicesSpinner.adapter = it
        }

        binding.choicesSpinner.onItemSelectedListener = object: AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                Log.d(TAG, "${parent?.getItemAtPosition(position)}")
                currentChoice = parent?.getItemAtPosition(position) as String
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {
                Log.d(TAG, "Nothing selected")
            }
        }
    }

    private fun updateScore() {
        binding.resultTest.text = gameViewModel.game.currentScore.toString()
    }

    private fun updateAllViews() {
        for (i in diceImages.indices) {
            diceImages[i].setImageResource(gameViewModel.dices[i].view)
        }
    }

    private fun updateView(imageView: ImageView, resourceId: Int) {
        imageView.setImageResource(resourceId)
    }

    private fun setupDiceImageListeners() {
        for (i in diceImages.indices) {
            diceImages[i].setOnClickListener {
                gameViewModel.dices[i].toggleSelection()
                updateView(diceImages[i], gameViewModel.dices[i].view)
            }
        }
        updateAllViews()
    }


}
