package com.example.dishflow.activities_Admin

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.dishflow.R
import com.example.dishflow.databinding.ActivityAdminLogInBinding
import com.example.dishflow.utility.VibrationUtils_card
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.firebase.auth.GoogleAuthProvider



class AdminLogInActivity : AppCompatActivity() {

    private lateinit var email:String
    private lateinit var password : String
    private lateinit var auth: FirebaseAuth
    private lateinit var database: DatabaseReference
    private lateinit var googleSignInClient : GoogleSignInClient


    private val binding : ActivityAdminLogInBinding by lazy {
        ActivityAdminLogInBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)


        // initialize FirebaseAuth and FirebaseDatabase
        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance().reference


        //initialize google signIn
//        @Suppress("DEPRECATION")
        val googleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()


        googleSignInClient = GoogleSignIn.getClient(this, googleSignInOptions)


        // Handle Google Sign-In button click
        binding.adminGoogleBtn.setOnClickListener{
            val signIntent = googleSignInClient.signInIntent
            VibrationUtils_card.vibrationSound(this, binding.adminGoogleBtn)
            launcher.launch(signIntent)
        }


        // Handle redirect to Sign-Up activity
        binding.adminRidirectToSignUp.setOnClickListener{
            intent = Intent(this, AdminSignUpActivity::class.java)
            startActivity(intent)
        }

        // Handle Login button click for email/password authentication
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



    // Method to handle user login using email and password
    private fun createUserAccount(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener{ task ->
            if(task.isSuccessful) {
                val user = auth.currentUser
                startActivity(Intent(this, AdminMainActivity::class.java))
                Log.d("Account", "createUser: Success", task.exception)
                finish() 
            } else {
                Toast.makeText(this, "please first create account", Toast.LENGTH_LONG).show()
                Log.d("Account", "createUser: Failure", task.exception)
            }
        }
    }


    // Launcher for Google Sign-In
    private val launcher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        // Check if the result code is OK (indicating successful sign-in attempt)
        if (result.resultCode == Activity.RESULT_OK) {
            // Get the Google Sign-In account from the result data
            val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)

            // If the sign-in task is successful
            if (task.isSuccessful) {
                // Extract the signed-in account information
                val account: GoogleSignInAccount = task.result
                // Get the credential for Firebase authentication
                val credential = GoogleAuthProvider.getCredential(account.idToken, null)

                // Log the successful sign-in attempt
                Log.d("googleSignIn", "signIn: Successful", task.exception)

                // Sign in with Firebase using the Google credentials
                auth.signInWithCredential(credential).addOnCompleteListener { authTask ->
                    // If the Firebase sign-in is successful
                    if (authTask.isSuccessful) {
                        // Show a success message
                        Toast.makeText(this, "Successfully Signed In", Toast.LENGTH_SHORT).show()

                        // Log the successful Firebase sign-in
                        Log.d("googleSignIn", "signIn: Successful", authTask.exception)

                        // Navigate to the main activity of the admin panel
                        startActivity(Intent(this, AdminMainActivity::class.java))
                        finish() // Close the login activity
                    } else {
                        // Show an error message if Firebase sign-in failed
                        Toast.makeText(this, "Sign In Failed", Toast.LENGTH_SHORT).show()

                        // Log the failure reason
                        Log.d("googleSignIn", "signIn: Failed", authTask.exception)
                    }
                }
            } else {
                // Show an error message if Google Sign-In itself failed
                Toast.makeText(this, "Sign In Failed", Toast.LENGTH_SHORT).show()

                // Log the failure reason for Google Sign-In
                Log.d("googleSignIn", "signIn: failure", task.exception)
            }
        }
    }

    override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser
        if (currentUser != null) {
            // If user is already signed in, navigate directly to the main activity
            startActivity(Intent(this, AdminMainActivity::class.java))
            finish() // Close the login activity
        }
    }

}