package com.example.dishflow.activities_Admin

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dishflow.R
import com.example.dishflow.adaptar.AdminDeliveryAdapter
import com.example.dishflow.adaptar.PendingOderAdapter
import com.example.dishflow.databinding.ActivityAdminPendingOrderBinding

class AdminPendingOrderActivity : AppCompatActivity() {
    private val binding: ActivityAdminPendingOrderBinding by lazy {
        ActivityAdminPendingOrderBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)

        val customerName = arrayListOf(
            "John Doe",
            "Hashr",
            "Mike"
        )

        val customerquantities = arrayListOf(
            "received",
            "not received",
            "pending"
        )
        val customerImages = arrayListOf(
            R.drawable.logo,
            R.drawable.logo,
            R.drawable.logo
        )

//        val adapter = pendingOderAdapter(customerName, customerQuantities, customerImages)
        val adapter = PendingOderAdapter(customerName, customerquantities, customerImages)
        binding.pendingOrderRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.pendingOrderRecyclerView.adapter = adapter


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}