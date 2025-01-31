package com.example.dishflow.activities

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
import com.example.dishflow.activities_Admin.AdminMainActivity
import com.example.dishflow.databinding.ActivityLoginBinding
import com.example.dishflow.models.UserUserModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.database

class LoginActivity : AppCompatActivity() {

    private lateinit var email: String
    private lateinit var password: String
    private lateinit var auth: FirebaseAuth
    private lateinit var database: DatabaseReference
    private lateinit var googleSignInClient: GoogleSignInClient

    private val binding:ActivityLoginBinding by lazy {
        ActivityLoginBinding.inflate(layoutInflater)
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)

        //initialize auth and database
        auth = Firebase.auth
        database = Firebase.database.reference


        //initialize google signIn
        @Suppress("DEPRECATION")
        val googleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(this, googleSignInOptions)

        binding.googleBtn.setOnClickListener{
            val signIntent = googleSignInClient.signInIntent
            launcher.launch(signIntent)
        }

        binding.ridirectToSignUp.setOnClickListener({
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        })

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
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

                        // save data to firebase
                        saveUserDataToFirebaseDB()

                        // Navigate to the main activity of the admin panel
                        startActivity(Intent(this, MainActivity::class.java))
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

    private fun saveUserDataToFirebaseDB() {
        val user = auth.currentUser // Get the authenticated user
        user?.let {
            val userId = it.uid  // Get the user's unique ID
            val userData = UserUserModel(
                name = it.displayName,
                email = it.email,
                password = null
            )

            // Store user data in Realtime Database
            database.child("user").child(userId).setValue(userData)
                .addOnSuccessListener {
                    Log.d("FirebaseDB", "User data stored successfully")
                    Toast.makeText(this, "User data saved", Toast.LENGTH_SHORT).show()
                }
                .addOnFailureListener { e ->
                    Log.e("FirebaseDB", "Failed to store user data", e)
                    Toast.makeText(this, "Failed to save user data", Toast.LENGTH_SHORT).show()
                }
        }
    }

}