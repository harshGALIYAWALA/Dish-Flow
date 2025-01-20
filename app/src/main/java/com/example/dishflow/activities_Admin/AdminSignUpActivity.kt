package com.example.dishflow.activities_Admin

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.dishflow.R
import com.example.dishflow.databinding.ActivityAdminSignUpBinding
import com.example.dishflow.models.UserModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.database

class AdminSignUpActivity : AppCompatActivity() {

    private lateinit var email: String
    private lateinit var password: String
    private lateinit var username: String
    private lateinit var nameOfRestaurant: String
    private lateinit var auth: FirebaseAuth
    private lateinit var database: DatabaseReference
    private lateinit var googleSignInClient: GoogleSignInClient



    private val binding : ActivityAdminSignUpBinding by lazy {
        ActivityAdminSignUpBinding.inflate(layoutInflater)
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

        // Handle Google Sign-In button click
        binding.adminGoogleBtn2.setOnClickListener{
            val signIntent = googleSignInClient.signInIntent
            launcher.launch(signIntent)
        }



        // creating user
        binding.createUserButton.setOnClickListener{

            username = binding.name.text.toString().trim()
            nameOfRestaurant = binding.restaurantName.text.toString().trim()
            email = binding.email.text.toString().trim()
            password = binding.password.text.toString().trim()

            // Validation
            if (username.isBlank() || nameOfRestaurant.isBlank() || email.isBlank() || password.isBlank()) {
                Toast.makeText(this, "please fill all detail", Toast.LENGTH_SHORT).show()
            } else{
                createUser(email, password)
            }

        }

        //redirect to login screen
        binding.adminRidirectToLogin.setOnClickListener{
            intent = Intent(this, AdminLogInActivity::class.java)
            startActivity(intent)
        }


        // option for region
        val locationList = arrayOf(
            "Andhra Pradesh",
            "Arunachal Pradesh",
            "Assam",
            "Bihar",
            "Chhattisgarh",
            "Goa",
            "Gujarat",
            "Haryana",
            "Himachal Pradesh",
            "Jharkhand",
            "Karnataka",
            "Kerala",
            "Madhya Pradesh",
            "Maharashtra",
            "Manipur",
            "Meghalaya",
            "Mizoram",
            "Nagaland",
            "Odisha",
            "Punjab",
            "Rajasthan",
            "Sikkim",
            "Tamil Nadu",
            "Telangana",
            "Tripura",
            "Uttar Pradesh",
            "Uttarakhand",
            "West Bengal"
        )
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, locationList)
        val autoCompleteTextView = binding.listOfLocation
        autoCompleteTextView.setAdapter(adapter)


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    // create user in firebase
    private fun createUser(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener{ task ->
            if(task.isSuccessful){
                Toast.makeText(this, "account is created", Toast.LENGTH_SHORT).show()
                saveUserData()
                intent = Intent(this, AdminLogInActivity::class.java)
                startActivity(intent)
            } else{
                Toast.makeText(this, "account is not created", Toast.LENGTH_SHORT).show()
                Log.d("Account", "createUser: Failure", task.exception)
            }
        }
    }

    // user data saved in real time DataBase
    private fun saveUserData() {
        username = binding.name.text.toString().trim()
        nameOfRestaurant = binding.restaurantName.text.toString().trim()
        email = binding.email.text.toString().trim()
        password = binding.password.text.toString().trim()

        val user  = UserModel(username, nameOfRestaurant, email, password)
        val userId = FirebaseAuth.getInstance().currentUser!!.uid
        database.child("user").child(userId).setValue(user)

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


}