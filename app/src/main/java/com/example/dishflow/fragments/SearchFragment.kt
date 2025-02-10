package com.example.dishflow.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dishflow.adaptar.MenuAdapter
import com.example.dishflow.databinding.FragmentSearchBinding
import com.example.dishflow.models.MenuItemUser
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class SearchFragment : Fragment() {

    // View binding for this fragment's layout
    private lateinit var binding: FragmentSearchBinding

    // Adapter for the RecyclerView that displays menu items
    private lateinit var adapter: MenuAdapter
    private lateinit var auth: FirebaseAuth
    private lateinit var database: FirebaseDatabase
    private lateinit var menuItems: MutableList<MenuItemUser>



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout using view binding
        binding = FragmentSearchBinding.inflate(inflater, container, false)

        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()


        Log.d("SearchFragment", "adapter is seted")
        fetchDataFromDatabase()


        // Return the root view of the inflated layout
        return binding.root
    }

    private fun fetchDataFromDatabase() {

        val userRef : DatabaseReference = database.reference.child("menu")
        menuItems = mutableListOf()
        userRef.addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                for(menuSnapshot in snapshot.children) {
                    val menuItem = menuSnapshot.getValue(MenuItemUser::class.java)
                    menuItem?.let { menuItems.add(it) }
                }

                Log.d("SearchFragment", "data received")

                if (menuItems.isNotEmpty()) {
                    val adapter = MenuAdapter(menuItems, requireContext())
                    binding.searchRecyclerView.layoutManager = LinearLayoutManager(requireContext())
                    binding.searchRecyclerView.adapter = adapter
                    Log.d("SearchFragment", "items found")
                } else {
                    Log.d("SearchFragment", "No items found")
                }
            }


            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(context, "didnt fetch the data", Toast.LENGTH_SHORT).show()
            }

        })

    }


}








//private fun showAllMenu() {
//        // Clear the filtered lists to avoid duplicates before adding all items
//        filteredMenuFoodName.clear()
//        filteredMenuFoodPrice.clear()
//        filteredMenuFoodImage.clear()
//
//        // Add all original items to the filtered lists (showing full menu)
//        filteredMenuFoodName.addAll(originalMenuFoodName)
//        filteredMenuFoodPrice.addAll(originalMenuPrice)
//        filteredMenuFoodImage.addAll(originalMenuImage)
//
//        // Notify the adapter that the dataset changed so the UI updates
//        adapter.notifyDataSetChanged()
//    }
//
//    private fun setUpSearchView() {
//        // Set a listener that responds to search query text changes
//        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
//            override fun onQueryTextSubmit(p0: String?): Boolean {
//                // Filter the menu items based on the submitted query
//                filterMenuItems(p0)
//                return true
//            }
//
//            override fun onQueryTextChange(p0: String?): Boolean {
//                // Filter the menu items as the query text changes (live search)
//                filterMenuItems(p0)
//                return true
//            }
//        })
//    }
//
//    private fun filterMenuItems(p0: String?) {
//        // Clear previously filtered results to start fresh
//        filteredMenuFoodName.clear()
//        filteredMenuFoodPrice.clear()
//        filteredMenuFoodImage.clear()
//
//        // If the query is empty or null, just show all menu items again
//        if (p0.isNullOrEmpty()) {
//            showAllMenu()
//            return
//        }
//
//        // Otherwise, iterate over the original lists and check if each item matches the query
//        originalMenuFoodName.forEachIndexed { index, foodName ->
//            if (foodName.contains(p0.toString(), ignoreCase = true)) {
//                // If it matches, add the item and its corresponding details to the filtered lists
//                filteredMenuFoodName.add(foodName)
//                filteredMenuFoodPrice.add(originalMenuPrice[index])
//                filteredMenuFoodImage.add(originalMenuImage[index])
//            }
//        }
//
//        // Notify the adapter that the dataset changed after filtering
//        adapter.notifyDataSetChanged()
//    }
