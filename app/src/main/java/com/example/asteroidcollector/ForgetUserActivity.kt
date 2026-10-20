package com.example.asteroidcollector

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.asteroidcollector.databinding.ActivityForgetUserBinding

class ForgetUserActivity : AppCompatActivity() {

    private lateinit var binding : ActivityForgetUserBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityForgetUserBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initlistener()
    }

    private fun initlistener() {
        binding.bEnterF.setOnClickListener {
            val emailBody = (R.string.s_email_body.toString()+binding.etUserF.toString())

            val intent = Intent(Intent.ACTION_SEND)

            intent.putExtra(Intent.EXTRA_EMAIL, R.string.s_email_send)
            intent.putExtra(Intent.EXTRA_SUBJECT, R.string.s_email_subject)
            intent.putExtra(Intent.EXTRA_TEXT, emailBody)

            intent.type = "message/rfc6854"

            startActivity(Intent.createChooser(intent, "Choose an Email client: "))

        }
    }
}