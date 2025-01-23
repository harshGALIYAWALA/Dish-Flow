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
        val currentUserId = FirebaseAuth.getInstance().currentUser?.uid

        if (currentUserId != null) {
            val menuRef = database.reference.child("menu").child(currentUserId) // Menu branch specific to the user
            val newItemKey = menuRef.push().key // Generate a unique key for the new item

            if (newItemKey != null) {
                // Create a FoodItemModel
                val foodItem = AllMenu(foodName, foodPrice, foodDescription, foodImageUri, foodIngredients)

                // Save the new food item under the generated key
                menuRef.setValue(foodItem)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            Log.d("Menu", "uploadData: Item added successfully")
                        } else {
                            Log.e("Menu", "uploadData: Failed to add item", task.exception)
                        }
                    }
            } else {
                Log.e("Menu", "uploadData: Failed to generate a new key")
            }
        } else {
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