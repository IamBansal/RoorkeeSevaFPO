package com.example.roorkeesevafpo.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.example.roorkeesevafpo.viewmodel.ShoppingViewModel
import com.example.roorkeesevafpo.databinding.ListItemBinding
import com.example.roorkeesevafpo.model.remote.DataProduct
import com.bumptech.glide.Glide
import com.example.roorkeesevafpo.R

class ListAdapter(
    private var list: List<DataProduct>,
    private var viewModel: ShoppingViewModel,
    private var viewLifecycleOwner: LifecycleOwner
): RecyclerView.Adapter<ListAdapter.ViewHolder>() {

    var addList = ArrayList<DataProduct>()

    inner class ViewHolder(private val binding: ListItemBinding): RecyclerView.ViewHolder(binding.root) {

        private val amountAdded: MutableLiveData<Int> = MutableLiveData()

        fun bind(item: DataProduct){

//            var added = item.quantity_added.toInt()
            var added = 0
            amountAdded.postValue(added)

            binding.tvItem.text = item.product

            binding.tvAmount.text = item.current_quantity

            "Rs. ${item.selling_price}".also { binding.tvPrice.text = it }

            amountAdded.observeForever {
                binding.tvSelectAmount.text = "${ amountAdded.value }"
            }

            binding.btnAdd.setOnClickListener {
//                val value = binding.tvSelectAmount.text.toString().toInt()
//                amountAdded.postValue(value + 1)

//                item.amountAdded++
//                viewModel.upsert(item)
//                Toast.makeText(binding.root.context, "Added", Toast.LENGTH_SHORT).show()
            }

            /* old code
            ADDITION
//                item.amountAdded++
//                viewModel.upsert(item)

//                var valueNowAdded = item.quantity_added.toInt()
//                valueNowAdded += 1
//
//                var valueNowLeft = item.current_quantity.toInt()
//                valueNowLeft -= 1
//
//                val product = DataProduct(
//                    item.__v,
//                    item._id,
//                    item.category,
//                    item.category,
//                    valueNowLeft.toString(),
//                    item.fpo_id,
//                    item.multiple_photo_link,
//                    item.original_price,
//                    item.product,
//                    valueNowAdded.toString(),
//                    item.selling_price,
//                    item.updatedAt
//                )
//
//                viewModel.updateProduct(item._id, product)


                SUBTRACTION
//                viewModel.delete(item)
//                if(item.amountAdded > 0){
//                    item.amountAdded--
//                    viewModel.upsert(item)
//                }


//                var valueNowAdded = item.quantity_added.toInt()
//                valueNowAdded -= 1
//
//                var valueNowLeft = item.current_quantity.toInt()
//                valueNowLeft += 1
//
//                val product = DataProduct(
//                    item.__v,
//                    item._id,
//                    item.category,
//                    item.category,
//                    valueNowLeft.toString(),
//                    item.fpo_id,
//                    item.multiple_photo_link,
//                    item.original_price,
//                    item.product,
//                    valueNowAdded.toString(),
//                    item.selling_price,
//                    item.updatedAt
//                )
//
//                viewModel.updateProduct(item._id, product)



             */

            binding.ivAdd.setOnClickListener {

                var price = 0
                var items = 0
                viewModel.totalPrice.observe(viewLifecycleOwner){ price = it }
                viewModel.totalPrice.postValue(price + item.selling_price.toInt())

                viewModel.totalItems.observe(viewLifecycleOwner){ items = it }
                viewModel.totalItems.postValue(items + 1)

                added++
                amountAdded.postValue(added)
                addList.add(item)
            }
            binding.ivSubtract.setOnClickListener {
                if (added > 0){
                    added--
                    amountAdded.postValue(added)

                    var price = 0
                    var items = 0
                    viewModel.totalPrice.observe(viewLifecycleOwner){ price = it }
                    viewModel.totalPrice.postValue(price - item.selling_price.toInt())

                    viewModel.totalItems.observe(viewLifecycleOwner){ items = it }
                    viewModel.totalItems.postValue(items - 1)

                    addList.remove(item)
                }
            }

            Glide.with(itemView).load(item.multiple_photo_link).placeholder(R.drawable.fpo).into(binding.image)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }

}