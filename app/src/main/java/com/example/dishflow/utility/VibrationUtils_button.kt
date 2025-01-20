package com.example.dishflow.utility
import android.content.Context
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.widget.Button
import androidx.cardview.widget.CardView

object VibrationUtils_button {
    fun vibrationSound(context: Context, button: Button) {
        // Access the Vibrator service
        val vibrator = context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator

        // Set click listener on the passed CardView
        button.setOnClickListener {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                vibrator.vibrate(VibrationEffect.createOneShot(50, VibrationEffect.DEFAULT_AMPLITUDE))
            } else {
                @Suppress("DEPRECATION")
                vibrator.vibrate(5000) // Deprecated in API 26+
            }
        }
    }
}


object VibrationUtils_card {
    fun vibrationSound(context: Context, cardView: CardView) {
        // Access the Vibrator service
        val vibrator = context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator

        // Set click listener on the passed CardView
        cardView.setOnClickListener {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                vibrator.vibrate(VibrationEffect.createOneShot(50, VibrationEffect.DEFAULT_AMPLITUDE))
            } else {
                @Suppress("DEPRECATION")
                vibrator.vibrate(5000) // Deprecated in API 26+
            }
        }
    }
}