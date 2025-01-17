package com.example.dishflow.activities_Admin

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.dishflow.R
import com.example.dishflow.databinding.ActivityAdminLogInBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class AdminLogInActivity : AppCompatActivity() {

    private lateinit var email:String
    private lateinit var password : String
    private lateinit var auth: FirebaseAuth
    private lateinit var database: DatabaseReference


    private val binding : ActivityAdminLogInBinding by lazy {
        ActivityAdminLogInBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance().reference


        binding.adminRidirectToSignUp.setOnClickListener{
            intent = Intent(this, AdminSignUpActivity::class.java)
            startActivity(intent)
        }

        binding.adminNextBtn.setOnClickListener{
            email = binding.email.text.toString().trim()
            password = binding.password.text.toString().trim()

            // Validation
            if(email.isBlank() || password.isBlank()) {
                Toast.makeText(this, "please fill all detail", Toast.LENGTH_SHORT).show()
            } else{
                createUserAccount(email, password)
            }

        }


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
    
    private fun createUserAccount(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener{ task ->
            if(task.isSuccessful) {
                val user = auth.currentUser
                updateUI(user)
                Log.d("Account", "createUser: Success", task.exception)
            } else {
                Toast.makeText(this, "please first create account", Toast.LENGTH_LONG).show()
                Log.d("Account", "createUser: Failure", task.exception)
            }
        }
    }

    private fun updateUI(user: FirebaseUser?) {
        startActivity(Intent(this, AdminMainActivity::class.java))
        finish()
    }
}