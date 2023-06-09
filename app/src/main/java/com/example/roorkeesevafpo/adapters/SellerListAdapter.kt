package com.example.roorkeesevafpo.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.roorkeesevafpo.R
import com.example.roorkeesevafpo.databinding.SellerListItemBinding
import com.example.roorkeesevafpo.model.local.Item

class SellerListAdapter(private var list: List<Item>): RecyclerView.Adapter<SellerListAdapter.ViewHolder>() {

    inner class ViewHolder(private val binding: SellerListItemBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Item){
            binding.tvItem.text = item.name
            "Total units left: ${item.amount}".also { binding.tvAmount.text = it }
            if (item.price != 0){
                "Rs. ${item.price}".also { binding.tvPrice.text = it }
            }
            binding.image.setImageResource(item.image)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            SellerListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(list[position])
        holder.itemView.setOnClickListener {
            it.findNavController().navigate(R.id.action_listOneFragment_to_listTwoFragment)
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

}