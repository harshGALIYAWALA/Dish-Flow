package com.example.dishflow.activities_Admin


import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.dishflow.R
import com.example.dishflow.databinding.ActivityAdminAddItemBinding
import com.example.dishflow.models.AllMenu
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class AdminAddItemActivity : AppCompatActivity() {

    //food items details
    private lateinit var foodName : String
    private lateinit var foodPrice : String
    private lateinit var foodDescription : String
    private lateinit var foodImageUri : String
    private lateinit var foodIngredients : String
    private lateinit var auth: FirebaseAuth
    private lateinit var database: FirebaseDatabase



    private val binding : ActivityAdminAddItemBinding by lazy {
        ActivityAdminAddItemBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)

        // initialization auth and database
        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()



//        binding.pickImageadmin.setOnClickListener{
//            pickImage.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
//            Log.d("imagePick", "onCreate:")
//        }

        // add item button
        binding.addItemButton.setOnClickListener{
            //get data from field
            foodName = binding.foodName.text.toString().trim()
            foodPrice = binding.foodPrice.text.toString().trim()
            foodDescription = binding.description.text.toString().trim()
            foodIngredients = binding.ingredients.text.toString().trim()
            foodImageUri = binding.foodImage.text.toString().trim()

            if(foodName.isNotEmpty() || foodPrice.isNotEmpty() || foodDescription.isNotEmpty() || foodIngredients.isNotEmpty()) {
                uploadData()
                Toast.makeText(this, "item added Successfully", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Please fill all the fields", Toast.LENGTH_SHORT).show()
            }
        }


        // backPress btn
        binding.backBtn.setOnClickListener{
            finish()
        }


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun uploadData() {
        // Get the current user's unique ID (UID) from Firebase Authentication
        val currentUserId = FirebaseAuth.getInstance().currentUser?.uid

        // Check if the user is authenticated
        if (currentUserId != null) {
            // Reference the "menu" node in the Firebase Realtime Database
            val menuRef = database.reference.child("menu") // Menu branch in the database
            // Generate a unique key for the new item
            val newItemKey = menuRef.push().key

            // Check if a unique key was successfully generated
            if (newItemKey != null) {
                // Create a FoodItemModel object with the food details
                val foodItem = AllMenu(foodName, foodPrice, foodDescription, foodImageUri, foodIngredients)

                // Save the new food item under the generated unique key
                menuRef.child(newItemKey).setValue(foodItem)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            // Log success if the item was added successfull
                            Toast.makeText(this, "item added Successfully", Toast.LENGTH_SHORT).show()
                            Log.d("Menu", "uploadData: Item added successfully")
                        } else {
                            // Log an error if the item could not be added
                            Toast.makeText(this, "item added failure", Toast.LENGTH_SHORT).show()
                            Log.e("Menu", "uploadData: Failed to add item", task.exception)
                        }
                    }
            } else {
                // Log an error if the unique key could not be generated
                Toast.makeText(this, "unique key could not be generated", Toast.LENGTH_SHORT).show()
                Log.e("Menu", "uploadData: Failed to generate a new key")
            }
        } else {
            // Log an error and show a message if the user is not authenticated
            Log.e("Menu", "uploadData: User not authenticated")
            Toast.makeText(this, "User not authenticated. Please log in.", Toast.LENGTH_SHORT).show()
        }
    }


    // image selector from photo
//    val pickImage = registerForActivityResult(ActivityResultContracts.PickVisualMedia()) {uri ->
//        if(uri != null) {
//            binding.selectedImage.setImageURI(uri)
//        }
//
//
//    }


}