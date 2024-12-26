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

    // View binding for this fragment's layout
    private lateinit var binding: FragmentSearchBinding

    // Adapter for the RecyclerView that displays menu items
    private lateinit var adapter: MenuAdapter

    // Original lists representing the full menu (unfiltered)
    private val originalMenuFoodName = listOf("burger", "sandwich", "pizza", "cake", "burger", "sandwich", "pizza", "cake")
    private val originalMenuPrice = listOf("$15", "$15", "$15", "$15", "$15", "$15", "$15", "$15")
    private val originalMenuImage = listOf(R.drawable.menu_item_food,
        R.drawable.menu_item_food02,
        R.drawable.menu_item_food,
        R.drawable.menu_item_food02,
        R.drawable.menu_item_food,
        R.drawable.menu_item_food02,
        R.drawable.menu_item_food,
        R.drawable.menu_item_food02)

    // Lists used to store the currently displayed items (filtered based on search input)
    private val filteredMenuFoodName = mutableListOf<String>()
    private val filteredMenuFoodPrice = mutableListOf<String>()
    private val filteredMenuFoodImage = mutableListOf<Int>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout using view binding
        binding = FragmentSearchBinding.inflate(inflater, container, false)

        // Initialize the adapter with the filtered lists (currently empty)
        adapter = MenuAdapter(filteredMenuFoodName, filteredMenuFoodPrice, filteredMenuFoodImage, requireContext())
        // Set a LinearLayoutManager to display items in a vertical list
        binding.menuRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        // Attach the adapter to the RecyclerView
        binding.menuRecyclerView.adapter = adapter

        // Set up the search view listener to handle search queries
        setUpSearchView()

        // Initially display all menu items before any search input
        showAllMenu()

        // Return the root view of the inflated layout
        return binding.root
    }

    private fun showAllMenu() {
        // Clear the filtered lists to avoid duplicates before adding all items
        filteredMenuFoodName.clear()
        filteredMenuFoodPrice.clear()
        filteredMenuFoodImage.clear()

        // Add all original items to the filtered lists (showing full menu)
        filteredMenuFoodName.addAll(originalMenuFoodName)
        filteredMenuFoodPrice.addAll(originalMenuPrice)
        filteredMenuFoodImage.addAll(originalMenuImage)

        // Notify the adapter that the dataset changed so the UI updates
        adapter.notifyDataSetChanged()
    }

    private fun setUpSearchView() {
        // Set a listener that responds to search query text changes
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String?): Boolean {
                // Filter the menu items based on the submitted query
                filterMenuItems(p0)
                return true
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                // Filter the menu items as the query text changes (live search)
                filterMenuItems(p0)
                return true
            }
        })
    }

    private fun filterMenuItems(p0: String?) {
        // Clear previously filtered results to start fresh
        filteredMenuFoodName.clear()
        filteredMenuFoodPrice.clear()
        filteredMenuFoodImage.clear()

        // If the query is empty or null, just show all menu items again
        if (p0.isNullOrEmpty()) {
            showAllMenu()
            return
        }

        // Otherwise, iterate over the original lists and check if each item matches the query
        originalMenuFoodName.forEachIndexed { index, foodName ->
            if (foodName.contains(p0.toString(), ignoreCase = true)) {
                // If it matches, add the item and its corresponding details to the filtered lists
                filteredMenuFoodName.add(foodName)
                filteredMenuFoodPrice.add(originalMenuPrice[index])
                filteredMenuFoodImage.add(originalMenuImage[index])
            }
        }

        // Notify the adapter that the dataset changed after filtering
        adapter.notifyDataSetChanged()
    }

}
