package com.example.dishflow.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.dishflow.R
import com.example.dishflow.activities_Admin.AdminLogInActivity
import com.example.dishflow.databinding.ActivitySelectRoleBinding
import com.example.dishflow.utility.VibrationUtils_button

class selectRoleActivity : AppCompatActivity() {

    private val binding : ActivitySelectRoleBinding by lazy {
        ActivitySelectRoleBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)


        binding.selectUser.setOnClickListener{
            Log.d("login", "login page is opened")
           intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            VibrationUtils_button.vibrationSound(this, binding.selectUser)
        }

        binding.selectAdmin.setOnClickListener{
            intent = Intent(this, AdminLogInActivity::class.java)
            startActivity(intent)
            VibrationUtils_button.vibrationSound(this, binding.selectAdmin)
        }




        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

}