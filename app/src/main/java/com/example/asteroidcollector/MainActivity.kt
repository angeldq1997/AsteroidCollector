package com.example.asteroidcollector

import android.app.AlertDialog
import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.asteroidcollector.databinding.ActivityMainBinding
import androidx.core.view.isGone

class MainActivity : AppCompatActivity() {
    val user : String = "Manolo"
    val password : String = "123456"
    var newUser : String = ""
    var newUserPassword : String = ""
    private lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initListeners()
    }

    private fun initListeners() {
        binding.bEnter.setOnClickListener {

            resetErrorTexts()
            checkLogIn()
        }

        binding.bRegister.setOnClickListener {
            if (binding.etRepeatPassword.isGone){
                binding.etRepeatPassword.visibility = View.VISIBLE
                Toast.makeText(this, "To sign in please fill out repeat password", Toast.LENGTH_LONG).show()
            } else{
                resetErrorTexts()
                checkSignIn()
            }
        }

        binding.bRegister.setOnHoverListener { view, event -> true

        }
    }

    private fun resetErrorTexts() {
        binding.tvEUser.setText(R.string.e_user)
        binding.tvEPassword.setText(R.string.e_password)
        binding.tvEEnd.setText(R.string.e_passwords_matching)

        binding.tvEUser.visibility = View.GONE
        binding.tvEPassword.visibility = View.GONE
        binding.tvEEnd.visibility = View.GONE
    }

    private fun checkLogIn(){
        //USER_EMPTY
        if (binding.etUser.text.isBlank()){
            binding.tvEUser.visibility = View.VISIBLE
            binding.tvEUser.setText(R.string.e_user_empty)

        //PASSWORD_EMPTY
        } else if (binding.etPassword.text.isBlank()){
            binding.tvEUser.visibility = View.VISIBLE
            binding.tvEUser.setText(R.string.e_password_empty)

        //CORRECT_LOGIN
        } else if (userOnDB()){
            val i = Intent(this, WelcomeActivity::class.java)
            if (binding.etUser.text.toString() == "Manolo") {
                i.putExtra(WelcomeActivity.PARAMETER_USER, user)
            } else {
                i.putExtra(WelcomeActivity.PARAMETER_USER, newUser)
            }

            val builder: AlertDialog.Builder = AlertDialog.Builder(this)
            builder
                .setMessage(R.string.t_correct_sign_in)
                .setTitle(R.string.d_success_sign_in)
                .setNegativeButton(R.string.d_cancel_login) { dialog, which ->
                    Toast.makeText(this, R.string.t_canceled_login, Toast.LENGTH_LONG).show()
                }
                .setPositiveButton(R.string.d_confirm_login) { dialog, which ->
                    startActivity(i)
                }
            val dialog: AlertDialog = builder.create()
            dialog.show()

        //NOT_MATCHING_USER_PASSWORD
        } else {
            binding.tvEPassword.visibility = View.VISIBLE
            binding.tvEPassword.setText(R.string.e_incorrect_user_password)
        }
    }

    private fun checkSignIn() {
        //USER_EMPTY
        if (binding.etUser.text.isBlank()){
            binding.tvEUser.visibility = View.VISIBLE
            binding.tvEUser.setText(R.string.e_user_empty)

        //PASSWORD_EMPTY
        } else if (binding.etPassword.text.isBlank()){
            binding.tvEUser.visibility = View.VISIBLE
            binding.tvEUser.setText(R.string.e_password_empty)

        //REPEAT_PASSWORD_EMPTY
        }else if (binding.etRepeatPassword.text.isBlank()) {
            binding.tvEEnd.visibility = View.VISIBLE
            binding.tvEEnd.setText(R.string.e_rep_password_empty)

        //REPEAT_PASSWORDS_NOT_MATCHING
        }else if (!matchingPasswords()) {
            binding.tvEEnd.visibility = View.VISIBLE
            binding.tvEEnd.setText(R.string.e_passwords_matching)

        //USER_ALREADY_ON_DB
        } else if (userOnDB()){
            binding.tvEEnd.visibility = View.VISIBLE
            binding.tvEEnd.setText(R.string.e_user_already_exists)

        //CORRECT_SIGN_IN
        } else if (matchingPasswords()) {
            newUser = binding.etUser.text.toString()
            newUserPassword = binding.etPassword.text.toString()

            val builder: AlertDialog.Builder = AlertDialog.Builder(this)
            builder
                .setMessage(R.string.t_correct_sign_in)
                .setTitle(R.string.d_success_sign_in)
                .setNegativeButton(R.string.d_cancel_sign_in) { dialog, which ->
                    Toast.makeText(this, R.string.t_canceled_sign_in, Toast.LENGTH_LONG).show()
                }
                .setPositiveButton(R.string.d_confirm_sign_in) { dialog, which ->
                    Toast.makeText(this, R.string.t_correct_sign_in, Toast.LENGTH_LONG).show()
                }
            val dialog: AlertDialog = builder.create()
            dialog.show()

        //ERROR SIGNING IN
        } else {
            binding.tvEEnd.visibility = View.VISIBLE
            binding.tvEEnd.setText(R.string.e_sign_in)
        }
    }

    private fun matchingPasswords(): Boolean = ( binding.etPassword.text.toString() == binding.etRepeatPassword.text.toString() )

    private fun userOnDB() : Boolean{
        return correctUser() || correctNewUser()
    }

    private fun correctUser() : Boolean{
        return binding.etUser.text.toString() == user && binding.etPassword.text.toString() == password
    }

    private fun correctNewUser() : Boolean{
        return  ( binding.etUser.text.toString() == newUser && binding.etPassword.text.toString() == newUserPassword )
    }
}