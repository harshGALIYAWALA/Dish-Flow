package com.example.dishflow.activities_Admin

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dishflow.R
import com.example.dishflow.adaptar.AllItemAdapter_Admin
import com.example.dishflow.databinding.ActivityAdminAllItemBinding

class AdminAllItemActivity : AppCompatActivity() {
    private val binding: ActivityAdminAllItemBinding by lazy {
        ActivityAdminAllItemBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)

        val menuFoodName = listOf("Herbal Pancake", "Herbal Pancake", "Herbal Pancake", "Herbal Pancake")
        val menuFoodPrice = listOf("$15", "$15", "$15", "$15")
        val menuFoodImage = listOf(R.drawable.menu_item_food02, R.drawable.menu_item_food02, R.drawable.menu_item_food02, R.drawable.menu_item_food02)

        val adapter = AllItemAdapter_Admin(ArrayList(menuFoodName), ArrayList(menuFoodPrice), ArrayList(menuFoodImage))
        binding.MenuRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.MenuRecyclerView.adapter = adapter



        binding.backBtn.setOnClickListener{
            finish()
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}