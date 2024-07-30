package com.example.mathgame

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
//import androidx.activity.OnBackPressedDispatcher
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.util.Locale
import kotlin.random.Random
class GameActivity : AppCompatActivity() {
    private lateinit var textScore:TextView
    private lateinit var textLife:TextView
    private lateinit var textTime:TextView
    private lateinit var textQuestion:TextView
    private lateinit var editTextAnswer:EditText
    private lateinit var buttonOk:Button
    private lateinit var textLifeValue:TextView
    private lateinit var buttonNext:Button
    private var correctAnswer=0
    private var userScore=0
    private var userLife=3
    private lateinit var timer:CountDownTimer
    private val startTimerInMllis:Long=60000
    private var timeLeftInMillis:Long= startTimerInMllis
    private lateinit var operation :String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_game)
        operation = intent.getStringExtra("operation")?: "addition"
        supportActionBar?.title ="Addition"
        textScore=findViewById(R.id.textviewScore)
        textLife=findViewById(R.id.textViewLife)
        textTime=findViewById(R.id.textView60)
        textLifeValue=findViewById(R.id.textView3)
        textQuestion=findViewById(R.id.textViewQuestion)
        editTextAnswer=findViewById(R.id.Answer)
        buttonOk=findViewById(R.id.buttonOk)
        buttonNext=findViewById(R.id.buttonNext)
        gameContinue()
        buttonOk.setOnClickListener {
            val input=editTextAnswer.text.toString()
            pauseTimer()
            if(input=="")
            {
               Toast.makeText(applicationContext,"Please Enter a answer",Toast.LENGTH_LONG).show()
            }
            else
            {
                val userAnswer=input.toInt()
                if(userAnswer == correctAnswer)
                {
                    userScore += 10
                    textQuestion.text= getString(R.string.congratulations_your_answer_is_correct)
                    textScore.text=userScore.toString()

                }
                else
                {
                    userLife--
                    textQuestion.text= getString(R.string.sorry_your_answer_is_wrong)
                    textLifeValue.text=userLife.toString()
                }


            }
        }
        buttonNext.setOnClickListener {
            pauseTimer()
            resetTimer()
            gameContinue()
            editTextAnswer.setText("")
            if(userLife==0)
            {
                Toast.makeText(applicationContext,"Game Over",Toast.LENGTH_LONG).show()
                val intent=Intent(this@GameActivity,EmptyActivity::class.java)
                intent.putExtra("score",userScore)
                startActivity(intent)
                finish()
            }
            else
            {
                gameContinue()
            }
        }
        // Setup toolbar
        val toolbar = findViewById<Toolbar>(R.id.materialToolbar2)
        setSupportActionBar(toolbar)

        // Enable back button
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        // Handle back button click
        toolbar.setNavigationOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
    private fun gameContinue()
    {
       val number1= Random.nextInt(0,100)
        val number2=Random.nextInt(0,100)
        correctAnswer= when(operation)
        {
            "subtraction"->number1-number2
            "multiplication"->number1*number2
            else->number1+number2
        }
        textQuestion.text= when(operation)
        {
            "subtraction" -> "$number1 - $number2"
            "multiplication" -> "$number1 * $number2"
            else -> "$number1 + $number2"
        }
        startTimer()

    }
    private fun startTimer()
    {
        timer=object :CountDownTimer(timeLeftInMillis,1000)
        {
            override fun onTick(millisUntilFinished: Long) {
               timeLeftInMillis=millisUntilFinished
                updateText()
            }

            override fun onFinish() {
                pauseTimer()
                resetTimer()
                updateText()
                userLife--
                textLifeValue.text=userLife.toString()
                textQuestion.text= getString(R.string.sorry_your_time_is_up)
            }

        }.start()

    }
    fun updateText()
    {
        val remainingTime = (timeLeftInMillis/1000).toInt()
        textTime.text=String.format(Locale.getDefault(),"%02d",remainingTime)

    }
    fun pauseTimer()
    {
        timer.cancel()
    }
    fun resetTimer()
    {
        timeLeftInMillis=startTimerInMllis
        updateText()
    }
    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return true
    }
}
