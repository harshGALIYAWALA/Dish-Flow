package com.example.dishflow.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.dishflow.R
import com.example.dishflow.databinding.ActivitySignUpBinding
import com.example.dishflow.models.UserUserModel
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.database
import kotlin.math.log

class SignUpActivity : AppCompatActivity() {

    private lateinit var username: String
    private lateinit var email: String
    private lateinit var password: String
    private lateinit var auth : FirebaseAuth
    private lateinit var database: DatabaseReference
    private lateinit var googleSignInClient : GoogleSignInClient

    private val binding: ActivitySignUpBinding by lazy {
        ActivitySignUpBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)

        // initialize firebase auth and database
        auth = Firebase.auth
        database = Firebase.database.reference

        binding.nextBtn.setOnClickListener{
            username = binding.usernameSP.text.toString().trim()
            email = binding.emailSP.text.toString().trim()
            password = binding.passwordSP.text.toString().trim()

            if(username.isBlank() || email.isBlank() || password.isBlank()) {
                Toast.makeText(this, "please enter all the fields", Toast.LENGTH_SHORT).show()
            } else {
                createAccount(email, password)
                Log.d("userAccount", "in validation ?")
            }

        }

        binding.ridirectToLogin.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun createAccount(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener{
            task ->
            if(task.isSuccessful) {
                Toast.makeText(this, "account is successfully created", Toast.LENGTH_SHORT).show()
                saveUserData()
                Log.d("userAccount", "success", task.exception)
                startActivity(Intent(this, LoginActivity::class.java))
                finish()
            } else {
                Toast.makeText(this, "account is successfully failed!", Toast.LENGTH_SHORT).show()
                Log.d("userAccount", "failed", task.exception)
            }
        }
    }

    private fun saveUserData() {
        //retreive data from input
        username = binding.usernameSP.text.toString().trim()
        email = binding.emailSP.text.toString().trim()
        password = binding.passwordSP.text.toString().trim()

        val user = UserUserModel(username, email, password)
        val userId = FirebaseAuth.getInstance().currentUser!!.uid
        database.child("user").child(userId).setValue(user)
    }
}