package com.example.roorkeesevafpo.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.roorkeesevafpo.databinding.ListTwoItemBinding
import com.example.roorkeesevafpo.model.local.Item

class ListTwoAdapter(private var list: List<Item>): RecyclerView.Adapter<ListTwoAdapter.ViewHolder>() {

    inner class ViewHolder(private val binding: ListTwoItemBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Item){
            "${item.name} : ${item.amount} pcs".also { binding.tvItem.text = it }
            if (item.price != 0){
                "Price(INR): ${item.price}".also { binding.tvPrice.text = it }
            }
            binding.image.setImageResource(item.image)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ListTwoItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }

}