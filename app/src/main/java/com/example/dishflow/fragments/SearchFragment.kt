package com.example.dishflow.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView

import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dishflow.R
import com.example.dishflow.adaptar.MenuAdapter
import com.example.dishflow.databinding.FragmentSearchBinding
import com.example.dishflow.models.MenuItem

class SearchFragment : Fragment() {

    private lateinit var binding: FragmentSearchBinding
//    private lateinit var adapter: MenuAdapter
//    private val originalMenuItems = listOf(
//        MenuItem("Burger", "$2", R.drawable.menu_item_food),
//        MenuItem("Sandwich", "$5", R.drawable.menu_item_food02),
//        MenuItem("Pizza", "$2", R.drawable.menu_item_food),
//        MenuItem("Cake", "$5", R.drawable.menu_item_food02),
//        MenuItem("Burger", "$4", R.drawable.menu_item_food),
//        MenuItem("Sandwich", "$5", R.drawable.menu_item_food02),
//        MenuItem("Pizza", "$7", R.drawable.menu_item_food),
//        MenuItem("Cake", "$5", R.drawable.menu_item_food02)
//    )



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSearchBinding.inflate(inflater, container, false)

//        adapter = MenuAdapter(originalMenuItems)
//        binding.menuRecyclerView.layoutManager = LinearLayoutManager(requireContext())
//        binding.menuRecyclerView.adapter = adapter
//
//        setUpSearchView()

        return binding.root
    }

//    private fun setUpSearchView() {
//        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
//            override fun onQueryTextSubmit(query: String?): Boolean {
//                filterMenuList(query)
//                return true
//            }
//
//            override fun onQueryTextChange(newText: String?): Boolean {
//                filterMenuList(newText)
//                return true
//            }
//        })
//    }

//    private fun filterMenuList(query: String?) {
//        if (query.isNullOrEmpty()) {
//            adapter.updateList(originalMenuItems)
//        } else {
//            val filteredList = originalMenuItems.filter {
//                it.name.contains(query, ignoreCase = true)
//            }
//            adapter.updateList(filteredList)
//        }
//    }
}
