package com.example.dishflow.activities_Admin

import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.dishflow.R
import com.example.dishflow.databinding.ActivityAdminAddItemBinding
import com.google.android.gms.auth.api.signin.internal.Storage
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase


class AdminAddItemActivity : AppCompatActivity() {

    //food items details
    private lateinit var foodName : String
    private lateinit var foodPrice : String
    private lateinit var foodDescription : String
    private var foodImageUri : Uri ?= null
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


        // add item button
        binding.addItemButton.setOnClickListener{
            // get data from details
            foodName = binding.foodName.text.toString().trim()
            foodPrice = binding.foodPrice.text.toString().trim()
            foodDescription = binding.description.text.toString().trim()
            foodIngredients = binding.ingredients.text.toString().trim()
            binding.selectedImage.setOnClickListener{
                pickImage.launch("image/*")
            }

            // validation of all field
            if ( !(foodName.isBlank() || foodPrice.isBlank() || foodDescription.isBlank() || foodIngredients.isBlank()) ){
                uploadData()
                Toast.makeText(this, "item added successfully", Toast.LENGTH_SHORT).show()
                finish()
            } else {
                Toast.makeText(this, "fill all the item", Toast.LENGTH_SHORT).show()
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
        val menuRef = database.getReference("menu")
        val newItemId = Storage


    }

    // image selector from photo
    private val pickImage =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
            if(uri != null) {
                binding.selectedImage.setImageURI(uri)
            }

        }


}