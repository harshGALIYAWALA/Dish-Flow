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



        binding.pickImageadmin.setOnClickListener{
            pickImage.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
            Log.d("imagePick", "onCreate:")
        }

        // add item button
        binding.addItemButton.setOnClickListener{
            // get data from details
            foodName = binding.foodName.text.toString().trim()
            foodPrice = binding.foodPrice.text.toString().trim()
            foodDescription = binding.description.text.toString().trim()
            foodIngredients = binding.ingredients.text.toString().trim()

            binding.pickImageadmin.setOnClickListener{
                pickImage.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
                Log.d("imagePick", "onCreate:")
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



    }

    // image selector from photo
   val pickImage = registerForActivityResult(ActivityResultContracts.PickVisualMedia()) {uri ->
       if(uri != null) {
           binding.selectedImage.setImageURI(uri)
       }


    }


}