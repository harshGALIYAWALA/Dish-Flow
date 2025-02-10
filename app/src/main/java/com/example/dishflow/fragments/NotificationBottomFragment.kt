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
    private var newMessage: String?= null
    private var newImage: Int?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            newMessage = it.getString("notification_message")
            newImage = it.getInt("notification_image")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNotificationBottomBinding.inflate(layoutInflater, container, false)


        // Default notifications
        val notifications = mutableListOf(
            "Congrats, Your Order Placed"
        )
        val notificationImages = mutableListOf(
            R.drawable.correct_icon
        )

        // Add new notification if available
        newMessage?.let { notifications.add(0, it) }
        newImage?.let { notificationImages.add(0, it) }

        // Set up RecyclerView
        val adapter = NotificationAdapter(ArrayList(notifications), ArrayList(notificationImages))
        binding.notificationRecyclerView.adapter = adapter
        binding.notificationRecyclerView.layoutManager = LinearLayoutManager(requireContext())

        return binding.root
    }

    companion object {
        fun newInstance(message: String, imageRes: Int) = NotificationBottomFragment().apply {
            arguments = Bundle().apply {
                putString("notification_message", message)
                putInt("notification_image", imageRes)
            }
        }
    }
}