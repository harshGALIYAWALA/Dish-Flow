package com.example.dishflow.adaptar

import android.view.LayoutInflater
import com.example.dishflow.models.MenuItem
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.dishflow.databinding.MenuItemBinding


class MenuAdapter(
    private val menuItems: List<MenuItem>
    ) : RecyclerView.Adapter<MenuAdapter.MenuViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuAdapter.MenuViewHolder {
        val binding = MenuItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MenuViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MenuAdapter.MenuViewHolder, position: Int) {
        holder.bind(menuItems[position])
    }

    override fun getItemCount(): Int = menuItems.size

    inner class MenuViewHolder(private val binding: MenuItemBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(item: MenuItem) {
            binding.apply {
                menuFoodName.text = item.name
                menuPrice.text = item.price
                menuImage.setImageResource(item.imageRes)
            }
        }

    }


}