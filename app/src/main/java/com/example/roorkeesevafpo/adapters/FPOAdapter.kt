package com.example.roorkeesevafpo.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.roorkeesevafpo.R
import com.example.roorkeesevafpo.databinding.FpoItemBinding

class FPOAdapter(val list: ArrayList<String>): RecyclerView.Adapter<FPOAdapter.ViewHolder>() {

    inner class ViewHolder(private val binding: FpoItemBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(item: String){
            binding.tvCategoryTitle.text = item
            when(item){
                "Pulses" -> binding.ivCategory.setImageResource(R.drawable.soy)
                "Fruits" -> binding.ivCategory.setImageResource(R.drawable.fruits)
                "Vegetables" -> binding.ivCategory.setImageResource(R.drawable.vegetable)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(FpoItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(list[position])
        holder.itemView.setOnClickListener{ view ->
            view.findNavController().navigate(R.id.action_fpoFragment_to_listFragment, bundleOf("list" to list))
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }
}