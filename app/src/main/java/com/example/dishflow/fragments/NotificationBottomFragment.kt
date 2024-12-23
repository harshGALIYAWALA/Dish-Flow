package com.example.dishflow.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dishflow.R
import com.example.dishflow.adaptar.NotificationAdapter
import com.example.dishflow.databinding.FragmentNotificationBottomBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


class NotificationBottomFragment : BottomSheetDialogFragment() {

    private lateinit var binding: FragmentNotificationBottomBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNotificationBottomBinding.inflate(layoutInflater, container, false)

        val notification = listOf("Your order has been Canceled Successfully",
                                    "Order has been taken by the driver",
                                    "Congrats Your Order Placed")

        val notificationImage = listOf(R.drawable.sademoji,
                                        R.drawable.truck_icon,
                                        R.drawable.correct_icon)

        val adapter = NotificationAdapter(
            ArrayList(notification),
            ArrayList(notificationImage)
        )
        binding.notificationRecyclerView.adapter = adapter
        binding.notificationRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.notificationRecyclerView.adapter = adapter

        return binding.root
    }

    companion object {

    }
}