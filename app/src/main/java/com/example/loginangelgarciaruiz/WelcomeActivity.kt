package com.example.loginangelgarciaruiz

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.loginangelgarciaruiz.databinding.ActivityWelcomeBinding

class WelcomeActivity : AppCompatActivity() {
    companion object {
        const val PARAMETER_USER = "parameter_user"
    }
    private lateinit var binding : ActivityWelcomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityWelcomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val welcomeMessage : String = "Welcome " + intent.getStringExtra("parameter_user")
        binding.tvWelcome.text = welcomeMessage
        setListeners()
    }

    private fun setListeners() {
        binding.bExitWelcome.setOnClickListener {
            Toast.makeText(this, getText(R.string.h_exit), Toast.LENGTH_LONG).show()
            finish()
        }
    }
}