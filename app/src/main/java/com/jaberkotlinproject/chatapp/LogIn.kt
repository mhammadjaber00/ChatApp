package com.jaberkotlinproject.chatapp

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

import com.jaberkotlinproject.chatapp.databinding.ActivityLogInBinding

class LogIn : AppCompatActivity() {

    private lateinit var binding: ActivityLogInBinding
    private lateinit var mAuth: FirebaseAuth

    private lateinit var edtEmail: EditText
    private lateinit var edtPassword: EditText
    private lateinit var btnLogIn: Button
    private lateinit var btnSignUp: Button

    private lateinit var sharedPref: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLogInBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        supportActionBar?.hide()

        mAuth = FirebaseAuth.getInstance()

        edtEmail = binding.edtEmail
        edtPassword = binding.edtPassword
        btnLogIn = binding.btnLogin
        btnSignUp = binding.btnSignUp

        sharedPref = this.getSharedPreferences("UserStatus", Context.MODE_PRIVATE)

        if (sharedPref.getBoolean("loggedIn", false)) {
            goToMainActivity()
        }


        btnSignUp.setOnClickListener {
            val intent = Intent(this, SignUp::class.java)
            startActivity(intent)
        }


        btnLogIn.setOnClickListener {
            val email = edtEmail.text.toString()
            val password = edtPassword.text.toString()

            login(email, password)
        }

    }

    private fun login(email: String, password: String) {

        mAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    sharedPref.edit().putBoolean("loggedIn", true).apply()
                    goToMainActivity()
                } else {
                    Toast.makeText(this@LogIn, "User does not exist", Toast.LENGTH_SHORT).show()
                }
            }

    }

    private fun goToMainActivity() {
        val intent = Intent(this@LogIn, MainActivity::class.java)
        finish()
        startActivity(intent)
    }
}