package se.umu.chho0126.thirty.controller

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import se.umu.chho0126.thirty.databinding.ActivityScoreBinding
import se.umu.chho0126.thirty.model.Game


/**
 * Score activity class that represents the score screen. Retrieves the total game score and
 * initializes relevant views.
 */
class ScoreActivity : AppCompatActivity() {
    private lateinit var binding: ActivityScoreBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityScoreBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val scores = intent.getIntArrayExtra(EXTRA_SCORE)?.toMutableList()

        val adapter = scores?.let {
            ArrayAdapter(this, android.R.layout.simple_list_item_1, it.mapIndexed { index, i ->
                "Round ${index+1}\t\t $i"
            })
        }

        binding.scoreResult.text = scores?.sum().toString()
        binding.scoreResults.adapter = adapter

        setIntentResult(null)
    }

    private fun setIntentResult(game: Game?) {
        setResult(Activity.RESULT_OK, Intent().putExtra(EXTRA_SCORE, 0))
    }

    /**
     * This companion object provides a public constant and a helper function.
     */
    companion object {
        const val EXTRA_SCORE = "se.umu.chho0126.thirty.score"

        /**
         * Instantiates and returns an intent that this score activity requires.
         * @param packageContext the context that calls this function
         * @param previousScores previous game round scores
         */
        fun newIntent(packageContext: Context, previousScores: IntArray): Intent {
            return Intent(packageContext, ScoreActivity::class.java).apply { putExtra(EXTRA_SCORE, previousScores) }
        }
    }
}