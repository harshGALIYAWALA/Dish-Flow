package com.example.dishflow.adaptar

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View.OnClickListener
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.dishflow.activities.DetailsActivity
import com.example.dishflow.databinding.MenuItemBinding


class MenuAdapter(private val menuItemName: MutableList<String>,
                  private val menuItemPrice: MutableList<String>,
                  private val menuItemImage: MutableList<Int>,
                  private val requireContext: Context) : RecyclerView.Adapter<MenuAdapter.MenuViewHolder>()  {


    private val itemclickListener: OnClickListener ?= null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuViewHolder {
        val binding = MenuItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MenuViewHolder(binding)
    }


    override fun onBindViewHolder(holder: MenuViewHolder, position: Int) {
        holder.bind(position)
    }


    override fun getItemCount(): Int = menuItemName.size


    inner class MenuViewHolder(private val binding: MenuItemBinding): RecyclerView.ViewHolder(binding.root){

        init {
            binding.root.setOnClickListener{
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    itemclickListener.onItemClick(position)
                }
                // SET ON CLICK LISTEN TO OPEN DETAILS
                val intent = Intent(requireContext, DetailsActivity::class.java)
                intent.putExtra("MenuItemName", menuItemName[position])
                intent.putExtra("foodImage", menuItemImage[position])
                requireContext.startActivity(intent)
            }
        }

        fun bind(position: Int) {
            binding.apply {
                menuFoodName.text = menuItemName[position]
                menuPrice.text = menuItemPrice[position]
                menuImage.setImageResource(menuItemImage[position])


            }
        }

    }

}

private fun OnClickListener?.onItemClick(position: Int) {

}








