package com.example.dishflow.activities_Admin

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.dishflow.R
import com.example.dishflow.activities.StartActivity
import com.example.dishflow.databinding.ActivityAdminMainBinding

class AdminMainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAdminMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAdminMainBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)

        // vibrating sound effects
        vibrationSound(binding.cardView1) // Pass the specific CardView
        vibrationSound(binding.cardView2)
        vibrationSound(binding.cardView3)
        vibrationSound(binding.cardView4)
        vibrationSound(binding.cardView5)
        vibrationSound(binding.cardView6)


        binding.cardView1.setOnClickListener{
           intent = Intent(this, AdminAddItemActivity::class.java)
            startActivity(intent)
        }



        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun vibrationSound(cardView: CardView) {
        // Access the Vibrator service
        val vibrator = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator

        // Set click listener on the passed CardView
        cardView.setOnClickListener {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                vibrator.vibrate(VibrationEffect.createOneShot(50, VibrationEffect.DEFAULT_AMPLITUDE))
            } else {
                @Suppress("DEPRECATION")
                vibrator.vibrate(50) // Deprecated in API 26+
            }
            Toast.makeText(this, "Card Clicked!", Toast.LENGTH_SHORT).show()
        }
    }

}

