package com.example.roorkeesevafpo.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.roorkeesevafpo.R
import com.example.roorkeesevafpo.databinding.SellerListItemBinding
import com.example.roorkeesevafpo.model.remote.buyer.DataBuyer

class SellerUserAdpater(private var list: List<DataBuyer>): RecyclerView.Adapter<SellerUserAdpater.ViewHolder>() {

    inner class ViewHolder(private val binding: SellerListItemBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(item: DataBuyer){
            binding.tvItem.text = item.name
            binding.tvAmount.text = item.orders.size.toString()
//            if (item.price != 0){
//                "Rs. ${item.price}".also { binding.tvPrice.text = it }
//            }
            "Rs. 70".also { binding.tvPrice.text = it }
            Glide.with(binding.root.context).load(item.photo).into(binding.image)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            SellerListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val user = list[position]
        holder.bind(user)
        holder.itemView.setOnClickListener {
            it.findNavController().navigate(R.id.action_FPOSellerFragment_to_profileFragment,
                bundleOf("name" to user.name, "num" to user.phone_no, "address" to user.address.city, "cart" to user.cart, "photo" to user.photo))
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

}