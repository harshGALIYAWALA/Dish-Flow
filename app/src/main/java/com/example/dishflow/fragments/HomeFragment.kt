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
import com.example.dishflow.adaptar.PopularAdapter
import com.example.dishflow.databinding.FragmentHomeBinding


class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding

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

        return binding.root
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
            }
        })

        val foodName = listOf("burger", "sandwich", "pizza", "cake")
        val image = listOf(R.drawable.menu_item_food, R.drawable.menu_item_food02, R.drawable.menu_item_food, R.drawable.menu_item_food02)
        val price = listOf("$15", "$15", "$15", "$15")

        val adapter = PopularAdapter(foodName, image, price)
        binding.popularItemRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.popularItemRecyclerView.adapter = adapter
    }

}