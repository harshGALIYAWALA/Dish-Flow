package com.example.dishflow.activities

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.dishflow.R
import com.example.dishflow.databinding.ActivityPayOutBinding
import com.example.dishflow.fragments.CongratBottomFragment

class PayOutActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPayOutBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding  = ActivityPayOutBinding.inflate(layoutInflater)
        setContentView(binding.root)

       binding.placeMyOderBtn.setOnClickListener{
           val congraBottomFragment = CongratBottomFragment()
           congraBottomFragment.show(supportFragmentManager, "CongratBottomFragment")
       }


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}