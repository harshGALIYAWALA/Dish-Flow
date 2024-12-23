package com.example.dishflow.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dishflow.R
import com.example.dishflow.adaptar.CartAdapter
import com.example.dishflow.databinding.FragmentCardBinding


class CardFragment : Fragment() {

    private lateinit var binding: FragmentCardBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCardBinding.inflate(inflater, container, false)

        val cartFoodName = listOf("burger", "pizza", "pasta", "cake")
        val CartItemPrice = listOf("$10", "$15", "$20", "$25")
        val CartImages = listOf(R.drawable.menu_item_food, R.drawable.menu_item_food02, R.drawable.menu_item_food, R.drawable.menu_item_food02)
        val adapter = CartAdapter(ArrayList(cartFoodName), ArrayList(CartItemPrice), ArrayList(CartImages))
        binding.cartrecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.cartrecyclerView.adapter = adapter


        //Proceed set on clicl listen to PayOut Activity
        


        return binding.root
    }

}