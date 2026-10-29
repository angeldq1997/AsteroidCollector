package com.example.loginangelgarciaruiz

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isGone
import com.example.loginangelgarciaruiz.databinding.ActivityMainBinding
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException

class MainActivity : AppCompatActivity() {
    val user : String = "Manolo"
    val password : String = "8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92"
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
                Toast.makeText(this, getText(R.string.t_fill_repeat_password), Toast.LENGTH_LONG).show()
            } else{
                resetErrorTexts()
                checkSignIn()
            }
        }

        binding.tvForgetPassword.setOnClickListener {
            val i = Intent(this, ForgetUserActivity::class.java)
            startActivity(i)
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
                .setMessage(R.string.t_correct_login)
                .setTitle(R.string.d_success_login)
                .setNegativeButton(R.string.d_cancel_login) { dialog, which ->
                    Toast.makeText(this, getText(R.string.t_canceled_login), Toast.LENGTH_LONG).show()
                }
                .setPositiveButton(R.string.d_confirm_login) { dialog, which ->
                    Toast.makeText(this, getText(R.string.t_correct_login), Toast.LENGTH_LONG).show()
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
            newUserPassword = sha256(binding.etPassword.text.toString())

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
        return binding.etUser.text.toString() == user && sha256(binding.etPassword.text.toString()) == password
    }

    private fun correctNewUser() : Boolean{
        return  ( binding.etUser.text.toString() == newUser && sha256(binding.etPassword.text.toString()) == newUserPassword )
    }

    @Throws(NoSuchAlgorithmException::class)
    fun sha256(password: String): String {
        var sha: MessageDigest? = null
        sha = MessageDigest.getInstance("SHA-256")
        val digest = sha.digest(password.toByteArray())

        val hexString = StringBuilder()

        for (b in digest) {
            hexString.append(String.format("%02x", b))
        }
        return hexString.toString()
    }
}