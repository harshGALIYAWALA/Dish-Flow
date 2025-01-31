package com.example.dishflow.adaptar

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.dishflow.databinding.CartItemBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class CartAdapter(private val CartItems:MutableList<String>,
                  private val CartItemPrice:MutableList<String>,
                  private val CartImages:MutableList<String>,
                  private val CartDescription:MutableList<String>,
                  private val CartQuantity:MutableList<Int>,
                  private val context: Context,
                    ):RecyclerView.Adapter<CartAdapter.CartViewHolder>() {

    // auth and database
    private val auth = FirebaseAuth.getInstance()
    init {
        val database = FirebaseDatabase.getInstance()
        val userId = auth.currentUser?.uid?:""
        val cartItemNumber = CartItems.size

        itemQuantities = IntArray(cartItemNumber){1}
        cartItemsRef = database.getReference("user").child(userId).child("CartItems")
    }

    companion object {
        private var itemQuantities : IntArray = intArrayOf()
        private lateinit var cartItemsRef: DatabaseReference
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        return CartViewHolder(CartItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount(): Int = CartItems.size

    inner class CartViewHolder(private val binding: CartItemBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(position: Int) {
            binding.apply {
                val quantity = itemQuantities[position]
                cartFoodName.text = CartItems[position]
                cartItemPrice.text = CartItemPrice[position]
                // load image through Glide
                val uriString = CartImages[position]
                val uri = Uri.parse(uriString)
                Glide.with(context).load(uri).into(cartImage)

                cartItemQuantity.text = itemQuantities[position].toString()

                minusButton.setOnClickListener{
                    decerementQuantity(position )
                }

                plusButton.setOnClickListener{
                    incerementQuantity(position)
                }

                deleteButton.setOnClickListener{
                    val itemPosition = adapterPosition
                    if(itemPosition != RecyclerView.NO_POSITION){
                        deleteItem(itemPosition)
                    }
                }

            }

        }


        private fun decerementQuantity(position: Int){
            if (itemQuantities[position] > 1){
                itemQuantities[position]--
                binding.cartItemQuantity.text = itemQuantities[position].toString()
            }
        }
        private fun incerementQuantity(position: Int){
            if (itemQuantities[position] < 9){
                itemQuantities[position]++
                binding.cartItemQuantity.text = itemQuantities[position].toString()
            }
        }
        private fun deleteItem(position: Int){
            val positionRetrieved = position
            getUniqueKeyAtPosition(positionRetrieved){
                uniqueKey ->
                uniqueKey?.let {
                    removeItem(position, uniqueKey)
                }

            }
        }

        private fun removeItem(position: Int, uniqueKey: String) {
            if (uniqueKey != null) {
                cartItemsRef.child(uniqueKey).removeValue().addOnSuccessListener {
                    CartItems.removeAt(position)
                    CartItemPrice.removeAt(position)
                    CartImages.removeAt(position)
                    CartDescription.removeAt(position)
                    CartQuantity.removeAt(position)
                    Toast.makeText(context,  "item deleted", Toast.LENGTH_SHORT).show()

                    //update qunatities
                    itemQuantities = itemQuantities.filterIndexed { index, i -> index!= position }.toIntArray()
                    notifyItemRemoved(position)
                    notifyItemRangeChanged(position, CartItems.size)

                }.addOnFailureListener{
                    Toast.makeText(context, "failed to delete", Toast.LENGTH_SHORT).show()
                }

            }
        }

        private fun getUniqueKeyAtPosition(positionRetrieved: Int, onComplete: (String?) -> Unit) {
            cartItemsRef.addListenerForSingleValueEvent(object : ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    var uniqueKey: String ?= null
                    snapshot.children.forEachIndexed { index, dataSnapshot ->
                        if (index == positionRetrieved){
                            uniqueKey = dataSnapshot.key
                            return@forEachIndexed
                        }
                    }
                    onComplete(uniqueKey)

                }

                override fun onCancelled(error: DatabaseError) {

                }

            })
        }
    }

}