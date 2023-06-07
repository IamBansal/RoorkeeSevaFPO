package com.example.roorkeesevafpo.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.roorkeesevafpo.model.local.Item
import com.example.roorkeesevafpo.databinding.CartListItemBinding

class CartAdapter(private var list: List<Item>): RecyclerView.Adapter<CartAdapter.ViewHolder>() {

    inner class ViewHolder(private val binding: CartListItemBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Item){
            binding.tvItem.text = item.name
            binding.tvAmount.text = item.amount.toString()
            "Rs. ${item.price}".also { binding.tvPrice.text = it }
            binding.image.setImageResource(item.image)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            CartListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }

}