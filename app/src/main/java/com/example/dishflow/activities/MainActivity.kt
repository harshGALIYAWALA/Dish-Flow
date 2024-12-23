package com.example.dishflow.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.dishflow.R
import com.example.dishflow.databinding.ActivityMainBinding
import com.example.dishflow.fragments.NotificationBottomFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        // Wait until the FragmentContainerView is fully initialized
            val navController = findNavController(R.id.fragmentContainerView)
            val bottomNav = binding.bottomNavigationView
            bottomNav.setupWithNavController(navController)


        binding.bellIcon.setOnClickListener{
            val bottomSheetFragment = NotificationBottomFragment()
            bottomSheetFragment.show(supportFragmentManager, "test")
        }



        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            // Apply top, left, and right padding only to root view
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, 0)

            // BottomNavigationView adjustment for gesture vs button navigation
            if (systemBars.bottom == 0) {
                // Gesture Navigation: Ensure BottomNavigationView extends fully
                binding.bottomNavigationView.setPadding(0, 0, 0, 0)
            } else {
                // Button Navigation: Add bottom padding dynamically
                binding.bottomNavigationView.setPadding(0, 0, 0, systemBars.bottom)
            }

            insets
        }
    }
}