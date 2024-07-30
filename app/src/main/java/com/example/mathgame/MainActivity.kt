package com.example.mathgame

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    private lateinit var addition:Button
    private lateinit var subtraction:Button
    private lateinit var multiplication:Button
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        addition=findViewById(R.id.addition)
        subtraction=findViewById(R.id.substraction)
        multiplication=findViewById(R.id.multiplication)
        addition.setOnClickListener {
            val intent=Intent(this@MainActivity,GameActivity::class.java)
            intent.putExtra("operation","addition")
            startActivity(intent)
        }
        subtraction.setOnClickListener {
            val intent=Intent(this@MainActivity,GameActivity::class.java)
            intent.putExtra("operation","subtraction")
            startActivity(intent)
        }
        multiplication.setOnClickListener {
            val intent=Intent(this@MainActivity,GameActivity::class.java)
            intent.putExtra("operation","multiplication")
            startActivity(intent)
        }
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}