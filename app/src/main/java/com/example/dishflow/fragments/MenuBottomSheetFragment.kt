package com.example.dishflow.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dishflow.adaptar.MenuAdapter
import com.example.dishflow.databinding.FragmentMenuBottomSheetBinding
import com.example.dishflow.models.MenuItemUser
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class MenuBottomSheetFragment : BottomSheetDialogFragment() {
    private lateinit var binding: FragmentMenuBottomSheetBinding
    private lateinit var database: FirebaseDatabase
    private lateinit var menuItems: MutableList<MenuItemUser>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentMenuBottomSheetBinding.inflate(inflater, container, false)


        retrieveData()


        return binding.root
    }

    private fun retrieveData() {
        database = FirebaseDatabase.getInstance()
        val foodRef : DatabaseReference = database.reference.child("menu")
        menuItems = mutableListOf()

        //snapShot
        foodRef.addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                for(foodSnapshot in snapshot.children) {
                    val menuItem = foodSnapshot.getValue(MenuItemUser::class.java)
                    menuItem?.let{
                        menuItems.add(it)
                    }
                }
                Log.d("ITEM", "data received")

                if (menuItems.isNotEmpty()) {
                    val adapter = MenuAdapter(menuItems, requireContext())
                    binding.menuRecyclerView.layoutManager = LinearLayoutManager(requireContext())
                    binding.menuRecyclerView.adapter = adapter
                    Log.d("ITEMS", "items found")
                } else {
                    Log.d("ITEMS", "No items found")
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("ITEMS", "Error: ${error.message}")
                Toast.makeText(requireContext(), "Failed to fetch data: ${error.message}", Toast.LENGTH_SHORT).show()
            }

        })
    }


}