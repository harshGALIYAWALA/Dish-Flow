package com.example.dishflow.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import com.example.dishflow.models.MenuItem

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dishflow.R
import com.example.dishflow.adaptar.MenuAdapter
import com.example.dishflow.databinding.FragmentMenuBottomSheetBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


class MenuBottomSheetFragment : BottomSheetDialogFragment() {
    private lateinit var binding: FragmentMenuBottomSheetBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentMenuBottomSheetBinding.inflate(inflater, container, false)

        val menuItems = listOf(
            MenuItem("Burger", "$10", R.drawable.menu_item_food),
            MenuItem("Pizza", "$15", R.drawable.menu_item_food02),
            MenuItem("Pasta", "$20", R.drawable.menu_item_food),
            MenuItem("Cake", "$25", R.drawable.menu_item_food02),
            MenuItem("Burger", "$10", R.drawable.menu_item_food),
            MenuItem("Pizza", "$15", R.drawable.menu_item_food02),
            MenuItem("Pasta", "$20", R.drawable.menu_item_food),
            MenuItem("Cake", "$25", R.drawable.menu_item_food02)
        )

        //setup adapter
        val adapter = MenuAdapter(menuItems)
        binding.menuRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.menuRecyclerView.adapter = adapter


        return binding.root
    }

    companion object {

    }
}