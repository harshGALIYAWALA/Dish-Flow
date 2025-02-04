package com.example.dishflow.activities

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import com.example.dishflow.R
import com.example.dishflow.databinding.ActivityDetailsBinding
import com.example.dishflow.models.CartItem
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.database

class DetailsActivity : AppCompatActivity() {

        private lateinit var binding: ActivityDetailsBinding
        private var foodName: String ?= null
        private var foodImage: String ?= null
        private var foodDescription: String ?= null
        private var foodIngredient: String ?= null
        private var foodPrice: String ?= null
        private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailsBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()

         foodName = intent.getStringExtra("MenuItemName")
         foodDescription = intent.getStringExtra("MenuItemDescription")
         foodIngredient = intent.getStringExtra("MenuItemIntredient")
         foodPrice = intent.getStringExtra("MenuItemPrice")
         foodImage = intent.getStringExtra("MenuItemImage")

        with(binding) {
            detailFoodName.text = foodName
            detailFoodDescription.text = foodDescription
            detailFoodIngredients.text = foodIngredient
            Glide.with(this@DetailsActivity).load(Uri.parse(foodImage)).into(detailFoodImage)
            
        }

        binding.imageButton.setOnClickListener{
            finish()
        }

        // add to card button
        binding.addToCardButton.setOnClickListener{
            addItemToCard()
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun addItemToCard() {
        val database = Firebase.database
        val userId = auth.currentUser?.uid?:""

        //create a cardItem object
        val cartItem = CartItem(foodName.toString(),
            foodPrice.toString(),
            foodDescription.toString(),
            foodImage.toString(),
            foodIngredient.toString(),
            1
        )
        // save the cartItem to the database
        database.getReference("user").child(userId).child("CartItems").push().setValue(cartItem).addOnSuccessListener {
            Toast.makeText(this, "item added into Cart successfully", Toast.LENGTH_SHORT).show()
        }.addOnFailureListener{
            Toast.makeText(this, "failed to add item into Cart", Toast.LENGTH_SHORT).show()
            Log.d("Cart", "failed to add item into Cart")
        }

    }
}