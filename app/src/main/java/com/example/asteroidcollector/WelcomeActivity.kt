package com.example.asteroidcollector

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.asteroidcollector.databinding.ActivityWelcomeBinding

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
    }
}