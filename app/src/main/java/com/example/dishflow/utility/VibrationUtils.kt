package com.example.dishflow.utility
import android.content.Context
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.widget.Button
import android.widget.Toast
import androidx.cardview.widget.CardView

object VibrationUtils {
    fun vibrationSound(context: Context, button: Button) {
        // Access the Vibrator service
        val vibrator = context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator

        // Set click listener on the passed CardView
        button.setOnClickListener {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                vibrator.vibrate(VibrationEffect.createOneShot(50, VibrationEffect.DEFAULT_AMPLITUDE))
            } else {
                @Suppress("DEPRECATION")
                vibrator.vibrate(50) // Deprecated in API 26+
            }
            Toast.makeText(context, "Card Clicked!", Toast.LENGTH_SHORT).show()
        }
    }
}