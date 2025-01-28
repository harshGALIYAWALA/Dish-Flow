package com.example.dishflow.adaptar

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View.OnClickListener
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.dishflow.activities.DetailsActivity
import com.example.dishflow.databinding.MenuItemBinding
import com.example.dishflow.models.MenuItemUser


class MenuAdapter(private val menuItems: List<MenuItemUser>,
                  private val requireContext: Context) : RecyclerView.Adapter<MenuAdapter.MenuViewHolder>()  {
                      

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuViewHolder {
        val binding = MenuItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MenuViewHolder(binding)
    }


    override fun onBindViewHolder(holder: MenuViewHolder, position: Int) {
        holder.bind(position)
    }


    override fun getItemCount(): Int = menuItems.size


    inner class MenuViewHolder(private val binding: MenuItemBinding): RecyclerView.ViewHolder(binding.root){

        init {
            binding.root.setOnClickListener{
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    openDetailActivity(position)
                }
            }
        }

        private fun openDetailActivity(position: Int) {
            val menuItem = menuItems[position]
            // Create an Intent to start the DetailsActivity
            val intent = Intent(requireContext, DetailsActivity::class.java).apply {
                putExtra("MenuItemName", menuItem.foodName)
                putExtra("MenuItemImage", menuItem.foodImage)
                putExtra("MenuItemDescription", menuItem.foodDescription)
                putExtra("MenuItemIntredient", menuItem.foodIngredients)
                putExtra("MenuItemPrice", menuItem.foodPrice)
            }
            requireContext.startActivity(intent)
        }

        fun bind(position: Int) {
            val menuItems = menuItems[position]
            binding.apply {
                menuFoodName.text = menuItems.foodName
                menuPrice.text = menuItems.foodPrice
                val uri = Uri.parse(menuItems.foodImage)
                Glide.with(requireContext).load(uri).into(menuImage)


            }
        }

    }



}









