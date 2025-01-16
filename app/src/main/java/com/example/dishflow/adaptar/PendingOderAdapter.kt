package com.example.dishflow.adaptar

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.dishflow.databinding.PendingOrderItemBinding

class PendingOderAdapter(private val PendingOderItems:ArrayList<String>,
                          private val Quantites:ArrayList<String>,
                         private val PendingOderItemImage:ArrayList<Int>): RecyclerView.Adapter<PendingOderAdapter.PendingOderViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PendingOderAdapter.PendingOderViewHolder {
        val binding = PendingOrderItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PendingOderViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PendingOderAdapter.PendingOderViewHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount(): Int = PendingOderItems.size

    inner class PendingOderViewHolder(private val binding: PendingOrderItemBinding): RecyclerView.ViewHolder(binding.root){
        private val isAccepted = false
        fun bind(position: Int) {
            binding.apply {
                CustomerName.text = PendingOderItems[position]
                quantity.text = Quantites[position]
                pendingOderImage.setImageResource(PendingOderItemImage[position])
                
            }
        }

    }

}