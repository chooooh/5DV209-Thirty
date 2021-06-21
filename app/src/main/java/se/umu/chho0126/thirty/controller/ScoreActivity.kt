package se.umu.chho0126.thirty.controller

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import se.umu.chho0126.thirty.Util
import se.umu.chho0126.thirty.databinding.ActivityScoreBinding
import se.umu.chho0126.thirty.model.Game


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


    companion object {
        const val EXTRA_SCORE = "se.umu.chho0126.thirty.score"
        const val EXTRA_GAME = "se.umu.chho0126.thirty.game"

        fun newIntent(packageContext: Context, game: IntArray): Intent {
            return Intent(packageContext, ScoreActivity::class.java).apply { putExtra(EXTRA_SCORE, game) }
        }
    }
}