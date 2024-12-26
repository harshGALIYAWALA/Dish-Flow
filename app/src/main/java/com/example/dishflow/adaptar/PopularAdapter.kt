package com.example.dishflow.adaptar

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.dishflow.activities.DetailsActivity
import com.example.dishflow.databinding.PopularItemBinding

class PopularAdapter(private val items:List<String>,
                     private val image:List<Int>, private val price:List<String>,
                    private val requireContext: Context): RecyclerView.Adapter<PopularAdapter.PopularViewHolder>() {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PopularViewHolder {
        return PopularViewHolder(PopularItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    }

    override fun onBindViewHolder(holder: PopularViewHolder, position: Int) {
        val item = items[position]
        val image = image[position]
        val price = price[position]
        holder.bind(item, image, price)

        holder.itemView.setOnClickListener{
            // SET ON CLICK LISTEN TO OPEN DETAILS
            val intent = Intent(requireContext, DetailsActivity::class.java)
            intent.putExtra("MenuItemName", item)
            intent.putExtra("foodImage", image)
            requireContext.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    class PopularViewHolder(private val binding: PopularItemBinding): RecyclerView.ViewHolder(binding.root) {
        private val imagesView = binding.imagePopular
        fun bind(item: String, image: Int, price: String) {
            binding.foodnamePopular.text = item
            binding.pricePopular.text = price
            imagesView.setImageResource(image)
        }

    }

}