package com.example.dishflow.adaptar

import android.content.res.ColorStateList
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.dishflow.R
import com.example.dishflow.databinding.AdminDeliveryItemBinding

class AdminDeliveryAdapter(private val CustomerName: ArrayList<String>, private val MoneyStatus: ArrayList<String>): RecyclerView.Adapter<AdminDeliveryAdapter.DeliveryViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): AdminDeliveryAdapter.DeliveryViewHolder {
        val binding = AdminDeliveryItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DeliveryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AdminDeliveryAdapter.DeliveryViewHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount(): Int = CustomerName.size

    inner class DeliveryViewHolder(private val binding: AdminDeliveryItemBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(position: Int) {
            binding.apply {
                customerName.text = CustomerName[position]
                moneyStatus.text = MoneyStatus[position]
                val colorMap = mapOf(
                    "received" to Color.GREEN,
                    "not received" to Color.RED,
                    "pending" to Color.GRAY
                )
                moneyStatus.setTextColor(colorMap[MoneyStatus[position]] ?: Color.BLACK)
                statusColor.backgroundTintList = ColorStateList.valueOf(colorMap[MoneyStatus[position]]?: Color.BLACK)
            }
        }

    }

}