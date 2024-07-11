package com.example.tictactoegame

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity(), View.OnClickListener {

    private var pl1Active = true
    private lateinit var pl1Score: TextView
    private lateinit var pl2Score: TextView
    private lateinit var playerStatus: TextView
    private lateinit var buttons: Array<Button>
    private lateinit var reset: Button
    private lateinit var playAgain: Button

    private val gameState = IntArray(9) { 2 }
    private val winningPositions = arrayOf(
        intArrayOf(0, 1, 2), intArrayOf(3, 4, 5), intArrayOf(6, 7, 8),
        intArrayOf(0, 3, 6), intArrayOf(1, 4, 7), intArrayOf(2, 5, 8),
        intArrayOf(0, 4, 8), intArrayOf(2, 4, 6)
    )

    private var rounds = 0
    private var pl1ScoreCount = 0
    private var pl2ScoreCount = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        pl1Score = findViewById(R.id.score_Player1)
        pl2Score = findViewById(R.id.score_Player2)
        playerStatus = findViewById(R.id.textStatus)
        reset = findViewById(R.id.btn_reset)
        playAgain = findViewById(R.id.btn_play_again)

        buttons = arrayOf(
            findViewById(R.id.btn0), findViewById(R.id.btn1), findViewById(R.id.btn2),
            findViewById(R.id.btn3), findViewById(R.id.btn4), findViewById(R.id.btn5),
            findViewById(R.id.btn6), findViewById(R.id.btn7), findViewById(R.id.btn8)
        )

        buttons.forEach { it.setOnClickListener(this) }

        reset.setOnClickListener {
            playAgain()
            pl1ScoreCount = 0
            pl2ScoreCount = 0
            updatePlayerScore()
        }

        playAgain.setOnClickListener { playAgain() }
    }

    @SuppressLint("SetTextI18n")
    override fun onClick(view: View) {
        if ((view as Button).text.isNotEmpty() || checkWinner()) return

        val buttonID = view.resources.getResourceEntryName(view.id)
        val gameStatePointer = buttonID.substring(buttonID.length - 1).toInt()

        if (pl1Active) {
            view.text = "X"
            view.setTextColor(Color.parseColor("#ffc34a"))
            gameState[gameStatePointer] = 0
        } else {
            view.text = "O"
            view.setTextColor(Color.parseColor("#70fc3a"))
            gameState[gameStatePointer] = 1
        }

        rounds++

        when {
            checkWinner() -> {
                if (pl1Active) {
                    pl1ScoreCount++
                    playerStatus.text = "Player-1 has won"
                } else {
                    pl2ScoreCount++
                    playerStatus.text = "Player-2 has won"
                }
                updatePlayerScore()
            }
            rounds == 9 -> playerStatus.text = "Match Draw"
            else -> pl1Active = !pl1Active
        }
    }

    private fun checkWinner(): Boolean {
        return winningPositions.any { positions ->
            gameState[positions[0]] == gameState[positions[1]] &&
                    gameState[positions[1]] == gameState[positions[2]] &&
                    gameState[positions[0]] != 2
        }
    }

    @SuppressLint("SetTextI18n")
    private fun playAgain() {
        rounds = 0
        pl1Active = true
        gameState.fill(2)
        buttons.forEach { it.text = "" }
        playerStatus.text = "Status"
    }

    private fun updatePlayerScore() {
        pl1Score.text = pl1ScoreCount.toString()
        pl2Score.text = pl2ScoreCount.toString()
    }
}