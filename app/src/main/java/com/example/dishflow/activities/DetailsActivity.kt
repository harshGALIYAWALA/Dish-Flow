package com.example.dishflow.activities

import android.net.Uri
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import com.example.dishflow.R
import com.example.dishflow.databinding.ActivityDetailsBinding

class DetailsActivity : AppCompatActivity() {

        private lateinit var binding: ActivityDetailsBinding
        private val foodName: String ?= null
        private val foodImage: String ?= null
        private val foodDescription: String ?= null
        private val foodIngredient: String ?= null
        private val foodPrice: String ?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailsBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)

        val foodName = intent.getStringExtra("MenuItemName")
        val foodDescription = intent.getStringExtra("MenuItemDescription")
        val foodIngredient = intent.getStringExtra("MenuItemIntredient")
        val foodPrice = intent.getStringExtra("MenuItemPrice")
        val foodImage = intent.getStringExtra("MenuItemImage")

        with(binding) {
            detailFoodName.text = foodName
            detailFoodDescription.text = foodDescription
            detailFoodIngredients.text = foodIngredient
            Glide.with(this@DetailsActivity).load(Uri.parse(foodImage)).into(detailFoodImage)
            
        }





        binding.imageButton.setOnClickListener{
            finish()
        }



        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}