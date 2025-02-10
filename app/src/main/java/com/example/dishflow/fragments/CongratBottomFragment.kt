package com.example.dishflow.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.dishflow.R
import com.example.dishflow.activities.MainActivity
import com.example.dishflow.databinding.FragmentCongratBottomBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase


class CongratBottomFragment : BottomSheetDialogFragment() {

    private lateinit var binding: FragmentCongratBottomBinding
    private var _binding: FragmentCongratBottomBinding? = null
    private lateinit var auth: FirebaseAuth
    private lateinit var database: FirebaseDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentCongratBottomBinding.inflate(inflater, container, false)

        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()

        binding.goHome.setOnClickListener{
            startActivity(Intent(requireContext(), MainActivity::class.java))
            Toast.makeText(context, "Order Placed", Toast.LENGTH_SHORT).show()

            clearCartItem()

            //Show the notification bottom sheet with new order status
            val notificationBottomSheet = NotificationBottomFragment.newInstance("Order Confirmed!", R.drawable.correct_icon)
            notificationBottomSheet.show(parentFragmentManager, notificationBottomSheet.tag)
        }
        return binding.root
    }

    private fun clearCartItem() {
        val auth = FirebaseAuth.getInstance()
        val userId = auth.currentUser?.uid?:""
        val cartRef = database.getReference("user").child(userId).child("CartItems")

        cartRef.removeValue().addOnSuccessListener {
            Toast.makeText(context, "Order Placed", Toast.LENGTH_SHORT).show()
        }
            .addOnFailureListener {
                Toast.makeText(context, "Order failed", Toast.LENGTH_SHORT).show()
        }
    }


}