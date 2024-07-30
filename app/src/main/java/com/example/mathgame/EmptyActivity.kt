package com.example.mathgame

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class EmptyActivity : AppCompatActivity() {
    private lateinit var result:TextView
    private lateinit var playAgain:Button
    private lateinit var exit:Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_empty)
        result=findViewById(R.id.textViewResult)
        playAgain=findViewById(R.id.button_again)
        exit=findViewById(R.id.exit)
        val score=intent.getIntExtra("score",0)
        result.text= "Your score is: $score"
        playAgain.setOnClickListener {
            val intent=Intent(this@EmptyActivity,MainActivity::class.java)
            startActivity(intent)
            finish()
        }
        exit.setOnClickListener {
            finishAffinity()
        }
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}