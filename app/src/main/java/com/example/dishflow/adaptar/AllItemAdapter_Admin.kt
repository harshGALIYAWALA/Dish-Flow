package com.example.dishflow.adaptar

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.dishflow.databinding.AdminItemItemBinding

class AllItemAdapter_Admin(private val MenuItemNameAdmin:ArrayList<String>,
                           private val MenuItemPriceAdmin:ArrayList<String>,
                           private val MenuItemImageAdmin:ArrayList<Int>): RecyclerView.Adapter<AllItemAdapter_Admin.AllItemViewHolder>() {

    private val itemQuantities = IntArray(MenuItemNameAdmin.size){1}

    override fun onCreateViewHolder(
        parent: ViewGroup,
         viewType: Int
    ): AllItemAdapter_Admin.AllItemViewHolder {
        val binding = AdminItemItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AllItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AllItemAdapter_Admin.AllItemViewHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount(): Int = MenuItemNameAdmin.size


    inner class AllItemViewHolder(private val binding: AdminItemItemBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(position: Int) {
            binding.apply {
                val quantity = itemQuantities[position]
                AdminFoodName.text = MenuItemNameAdmin[position]
                AdminItemPrice.text = MenuItemPriceAdmin[position]
                AdminFoodImage.setImageResource(MenuItemImageAdmin[position])



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
            MenuItemNameAdmin.removeAt(position)
            MenuItemImageAdmin.removeAt(position)
            MenuItemPriceAdmin.removeAt(position)
            notifyItemRemoved(position)
            notifyItemRangeChanged(position, MenuItemNameAdmin.size)
        }

    }


}