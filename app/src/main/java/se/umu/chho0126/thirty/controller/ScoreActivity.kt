package se.umu.chho0126.thirty.controller

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import se.umu.chho0126.thirty.R
import se.umu.chho0126.thirty.databinding.ActivityScoreBinding


class ScoreActivity : AppCompatActivity() {
    private lateinit var binding: ActivityScoreBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityScoreBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val score = intent.getIntExtra(EXTRA_SCORE, 0)
        binding.scoreResult.text = score.toString()

        setIntentResult(score)
    }

    private fun setIntentResult(score: Int) {
        val data = Intent().apply {
            putExtra(EXTRA_SCORE, score)
        }
        setResult(Activity.RESULT_OK, data)
    }

    companion object {
        const val EXTRA_SCORE = "se.umu.chho0126.thirty.score"
        const val EXTRA_TEST= "se.umu.chho0126.thirty.TEST"

        fun newIntent(packageContext: Context, score: Int): Intent {
            return Intent(packageContext, ScoreActivity::class.java).apply { putExtra(EXTRA_SCORE, score) }
        }
    }
}