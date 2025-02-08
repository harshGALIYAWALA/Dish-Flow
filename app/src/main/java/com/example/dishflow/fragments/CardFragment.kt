package com.example.dishflow.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dishflow.R
import com.example.dishflow.activities.PayOutActivity
import com.example.dishflow.adaptar.CartAdapter
import com.example.dishflow.databinding.FragmentCardBinding
import com.example.dishflow.models.CartItem
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class CardFragment : Fragment() {

    private lateinit var binding: FragmentCardBinding
    private lateinit var foodName: MutableList<String>
    private lateinit var CartItemPrice: MutableList<String>
    private lateinit var CartImages: MutableList<String>
    private lateinit var CartDescription: MutableList<String>
    private lateinit var CartIngredients: MutableList<String>
    private lateinit var quantity: MutableList<Int>

    private lateinit var adapter: CartAdapter
    private lateinit var auth: FirebaseAuth
    private lateinit var database: FirebaseDatabase
    private lateinit var UserID :String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCardBinding.inflate(inflater, container, false)

        auth = FirebaseAuth.getInstance()
        retrievedCartItems()






        //Proceed set on click listen to PayOut Activity
        binding.proceesBTN.setOnClickListener{
            //get all the item quantity
            getOrderItemDetail()
            val intent = Intent(requireContext(), PayOutActivity::class.java)
            startActivity(intent)
        }





        return binding.root
    }

    private fun getOrderItemDetail() {
        val orderIdReference: DatabaseReference = database.reference.child("user").child(UserID).child("CartItems")
        val foodName = mutableListOf<String>()
        val foodPrice = mutableListOf<String>()
        val foodImage = mutableListOf<String>()
        val foodDescription = mutableListOf<String>()
        val foodIngredients = mutableListOf<String>()
        val foodQuantities = adapter.getUpdatedQuantities()

        orderIdReference.addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                    for (foodSnapshot in snapshot.children) {
                        //get the item detail
                        val orderItem = foodSnapshot.getValue(CartItem::class.java)
                        //add the item detail to the list
                        orderItem?.foodName?.let { foodName.add(it) }
                        orderItem?.foodPrice?.let { foodPrice.add(it) }
                        orderItem?.foodImage?.let { foodImage.add(it) }
                        orderItem?.foodDescription?.let { foodDescription.add(it) }
                        orderItem?.foodIngredients?.let { foodIngredients.add(it) }
                        orderNow(foodName, foodPrice, foodImage, foodDescription, foodIngredients, foodQuantities)
                    }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(context, "order making failed, please try again", Toast.LENGTH_SHORT).show()
            }

        })
    }

    private fun orderNow(
        foodName: MutableList<String>,
        foodPrice: MutableList<String>,
        foodImage: MutableList<String>,
        foodDescription: MutableList<String>,
        foodIngredients: MutableList<String>,
        foodQuantities: MutableList<Int>
    ) {
         if(isAdded && context != null) {
             val intent = Intent(requireContext(), PayOutActivity::class.java)
             intent.putStringArrayListExtra("foodItemName", foodName as ArrayList<String>)
             intent.putStringArrayListExtra("foodItemPrice", foodPrice as ArrayList<String>)
             intent.putStringArrayListExtra("foodItemImage", foodImage as ArrayList<String>)
             intent.putStringArrayListExtra("foodItemDescription", foodDescription as ArrayList<String>)
             intent.putStringArrayListExtra("foodItemIngredients", foodIngredients as ArrayList<String>)
             intent.putIntegerArrayListExtra("foodItemQuantity", foodQuantities as ArrayList<Int>)
             startActivity(intent)
         }
    }

    private fun retrievedCartItems() {
        //database reference
        database = FirebaseDatabase.getInstance()
        val userId = auth.currentUser?.uid?:""
        val foodRef :DatabaseReference = database.getReference("user").child(userId).child("CartItems")

        foodName = mutableListOf()
        CartItemPrice = mutableListOf()
        CartDescription = mutableListOf()
        CartImages = mutableListOf()
        CartIngredients = mutableListOf()
        quantity = mutableListOf()

        //fetch data from database
        foodRef.addListenerForSingleValueEvent(object: ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                for (foodSnapshot in snapshot.children) {
                     val cartItems =foodSnapshot.getValue(CartItem::class.java)

                    cartItems?.foodName?.let { foodName.add(it) }
                    cartItems?.foodPrice?.let { CartItemPrice.add(it) }
                    cartItems?.foodDescription?.let { CartDescription.add(it) }
                    cartItems?.foodImage?.let { CartImages.add(it) }
                    cartItems?.foodIngredients?.let { CartIngredients.add(it) }
                    cartItems?.foodQuantity?.let { quantity.add(it) }


                }

                val adapter = CartAdapter(foodName, CartItemPrice, CartImages, CartDescription, quantity, CartIngredients, requireContext())
                binding.cartrecyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
                binding.cartrecyclerView.adapter = adapter
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(context, "data didn't fetched", Toast.LENGTH_SHORT).show()
            }

        })
    }

}