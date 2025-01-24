package com.example.dishflow.adaptar

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.dishflow.databinding.AdminItemItemBinding
import com.example.dishflow.models.AllMenu
import com.google.firebase.database.DatabaseReference

class MenuItemAdapter_Admin(
    private val context: Context,
    private val menuList: ArrayList<AllMenu>,
    databaseRefrence: DatabaseReference
): RecyclerView.Adapter<MenuItemAdapter_Admin.AllItemViewHolder>() {

    private val itemQuantities = IntArray(menuList.size){1}

    override fun onCreateViewHolder(
        parent: ViewGroup,
         viewType: Int
    ): MenuItemAdapter_Admin.AllItemViewHolder {
        val binding = AdminItemItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AllItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MenuItemAdapter_Admin.AllItemViewHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount(): Int = menuList.size


    inner class AllItemViewHolder(private val binding: AdminItemItemBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(position: Int) {
            binding.apply {
                val quantity = itemQuantities[position]
                val menuItem = menuList[position]
                val uriString = menuItem.foodImage
                val uri = Uri.parse(uriString)
                AdminFoodName.text = menuItem.foodName
                AdminItemPrice.text = menuItem.foodPrice
                Glide.with(context).load(uri).into(AdminFoodImage)




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
                binding.ItemQuantity.text = itemQuantities[position].toString()
            }
        }
        private fun incerementQuantity(position: Int){
            if (itemQuantities[position] < 9){
                itemQuantities[position]++
                binding.ItemQuantity.text = itemQuantities[position].toString()
            }
        }
        private fun deleteItem(position: Int){
            menuList.removeAt(position)
            menuList.removeAt(position)
            menuList.removeAt(position)
            notifyItemRemoved(position)
            notifyItemRangeChanged(position, menuList.size)
        }

    }


}