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


class CongratBottomFragment : BottomSheetDialogFragment() {

    private lateinit var binding: FragmentCongratBottomBinding
    private var _binding: FragmentCongratBottomBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCongratBottomBinding.inflate(inflater, container, false)

        binding.goHome.setOnClickListener{
            startActivity(Intent(requireContext(), MainActivity::class.java))
            Toast.makeText(context, "Order Placed", Toast.LENGTH_SHORT).show()

        }
        return binding.root
    }






}