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
import androidx.lifecycle.ViewModelProviders
import se.umu.chho0126.thirty.databinding.ActivityMainBinding
import se.umu.chho0126.thirty.R
import se.umu.chho0126.thirty.viewModels.DiceViewModel

private const val TAG = "MainActivity"
class MainActivity : AppCompatActivity() {

    private lateinit var diceImages: List<ImageView>
    private lateinit var binding: ActivityMainBinding
    private lateinit var currentChoice: String


    private val diceViewModel: DiceViewModel by lazy {
        ViewModelProviders.of(this).get(DiceViewModel::class.java)
    }

    private val startForResult: ActivityResultLauncher<Intent> = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        if (it.resultCode == Activity.RESULT_OK){
            val score = it.data?.getIntExtra(ScoreActivity.EXTRA_SCORE, 0)
            Toast.makeText(this, "you have exited the activity! The score was $score", Toast.LENGTH_SHORT).show()
        }
        diceViewModel.newGame()
        updateAllViews()
        updateScore()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        diceImages = listOf(
            binding.diceOne,
            binding.diceTwo,
            binding.diceThree,
            binding.diceFour,
            binding.diceFive,
            binding.diceSix,
        )

        binding.resultTest.text = "${diceViewModel.game.currentScore}"

        setupDiceImageListeners()
        setupSpinner()

        binding.throwButton.setOnClickListener {
            if (!diceViewModel.game.isRoundFinished()) {
                diceViewModel.throwAllDices()
            } else {
                diceViewModel.createNewRound()
                Toast.makeText(this, R.string.new_round, Toast.LENGTH_SHORT).show()
                updateScore()
            }
            if (diceViewModel.game.isGameFinished)
                startForResult.launch(ScoreActivity.newIntent(this, diceViewModel.game.currentScore))
            updateAllViews()
        }

        binding.calculateButton.isEnabled = false
        binding.calculateButton.setOnClickListener {
            newRound()
        }

/*
        throwButton.setOnClickListener {
            startForResult.launch(Intent(this....))
        }
 */
    }

    private fun newRound() {
        diceViewModel.createNewRound()
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
        binding.resultTest.text = diceViewModel.game.currentScore.toString()
    }

    private fun updateAllViews() {
        for (i in diceImages.indices) {
            diceImages[i].setImageResource(diceViewModel.dices[i].view)
        }
    }

    private fun updateView(imageView: ImageView, resourceId: Int) {
        imageView.setImageResource(resourceId)
    }

    private fun setupDiceImageListeners() {
        for (i in diceImages.indices) {
            diceImages[i].setOnClickListener {
                diceViewModel.dices[i].toggleSelection()
                updateView(diceImages[i], diceViewModel.dices[i].view)
            }
        }
        updateAllViews()
    }


}
