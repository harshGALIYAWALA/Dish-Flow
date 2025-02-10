package com.example.dishflow.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.interfaces.ItemClickListener
import com.denzcoskun.imageslider.models.SlideModel
import com.example.dishflow.R
import com.example.dishflow.adaptar.MenuAdapter
import com.example.dishflow.adaptar.PopularAdapter
import com.example.dishflow.databinding.FragmentHomeBinding
import com.example.dishflow.models.MenuItemUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var database: FirebaseDatabase
    private lateinit var menuItems: MutableList<MenuItemUser>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
       binding = FragmentHomeBinding.inflate(inflater, container, false)

        // onclicklisten for menu button to show bottomSheet
        binding.viewAllMenu.setOnClickListener{
            val bottomSheetDialog = MenuBottomSheetFragment()
            bottomSheetDialog.show(parentFragmentManager, "Test")
        }


        // retrieving data from firebase
        retrieveAndDisplayPolularItem()



        return binding.root
    }



    private fun retrieveAndDisplayPolularItem() {
        database = FirebaseDatabase.getInstance()
        val foodRef : DatabaseReference = database.reference.child("menu")
        menuItems = mutableListOf()

        foodRef.addListenerForSingleValueEvent(object: ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                for (foodSnapShot in snapshot.children) {
                    val menuItem = foodSnapShot.getValue(MenuItemUser::class.java)
                    menuItem?.let { menuItems.add(it) }
                }
                //display a random popular item
                randomPopularItem()
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }


        })

    }

    private fun randomPopularItem() {
        //create as shuffled list of menu items
        val index = menuItems.indices.toList().shuffled()
        val numItemToShow = 5
        val subsetMenuItems = index.take(numItemToShow).map { menuItems[it] }

        //set-Up adapter for popular item as a shuffled list
        setPopularItemAdapter(subsetMenuItems)
    }

    private fun setPopularItemAdapter(subsetMenuItems: List<MenuItemUser>) {
        val adapter = MenuAdapter(subsetMenuItems, requireContext())
        binding.popularItemRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.popularItemRecyclerView.adapter = adapter
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //image list for slider
        val imageList = ArrayList<SlideModel>()
        imageList.add(SlideModel(R.drawable.menu_item_food02, ScaleTypes.FIT))
        imageList.add(SlideModel(R.drawable.menu_item_food, ScaleTypes.FIT))
        imageList.add(SlideModel(R.drawable.menu_item_food02, ScaleTypes.FIT))

        val imageSlider = binding.imageSlider
        imageSlider.setImageList(imageList)
        imageSlider.setImageList(imageList, ScaleTypes.FIT)

        // image click listener for counting position(index)
        imageSlider.setItemClickListener(object: ItemClickListener{
            override fun doubleClick(position: Int) {
                TODO("Not yet implemented")
            }

            override fun onItemSelected(position: Int) {
                val itemPosition = imageList[position]
                val itemMessage = "selected image $position"
                Toast.makeText(requireContext(), itemMessage, Toast.LENGTH_LONG).show()

                // nothing
            }
        })




    }

}