package com.example.roorkeesevafpo.fragments.seller

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.roorkeesevafpo.R
import com.example.roorkeesevafpo.R.color.toolbarTextColor
import com.example.roorkeesevafpo.adapters.SellerUserAdpater
import com.example.roorkeesevafpo.databinding.FragmentFPOSellerBinding
import com.example.roorkeesevafpo.db.ShoppingDatabase
import com.example.roorkeesevafpo.model.remote.buyer.DataBuyer
import com.example.roorkeesevafpo.repository.ShoppingRepository
import com.example.roorkeesevafpo.utils.Resource
import com.example.roorkeesevafpo.viewmodel.ShoppingViewModel
import com.example.roorkeesevafpo.viewmodel.ShoppingViewModelFactory

@Suppress("DEPRECATION")
class FPOSellerFragment : Fragment() {

    private lateinit var binding: FragmentFPOSellerBinding
    private lateinit var sellerAdapter: SellerUserAdpater

    private lateinit var database: ShoppingDatabase
    private lateinit var repository: ShoppingRepository
    private lateinit var factory: ShoppingViewModelFactory
    private lateinit var viewModel: ShoppingViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFPOSellerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        database = ShoppingDatabase(requireActivity())
        repository = ShoppingRepository(database)
        factory = ShoppingViewModelFactory(repository)
        viewModel = ViewModelProvider(this, factory)[ShoppingViewModel::class.java]

        getBuyers()

        binding.cardPulse.setOnClickListener {
            findNavController().navigate(R.id.action_FPOSellerFragment_to_listOneFragment)
        }

        binding.cardFruits.setOnClickListener {
            findNavController().navigate(R.id.action_FPOSellerFragment_to_listOneFragment)
        }

        binding.cardGrain.setOnClickListener {
            findNavController().navigate(R.id.action_FPOSellerFragment_to_listOneFragment)
        }

        binding.cardVegetables.setOnClickListener {
            findNavController().navigate(R.id.action_FPOSellerFragment_to_listOneFragment)
        }

        binding.btnProduct.setOnClickListener {
            it.setBackgroundColor(resources.getColor(toolbarTextColor))
            binding.btnUser.setBackgroundColor(resources.getColor(R.color.white))
            binding.layoutProduct.visibility = View.VISIBLE
            binding.layoutUser.visibility = View.GONE
        }

        binding.btnProduct.setBackgroundColor(resources.getColor(toolbarTextColor))

        binding.btnUser.setOnClickListener {
            it.setBackgroundColor(resources.getColor(toolbarTextColor))
            binding.btnProduct.setBackgroundColor(resources.getColor(R.color.white))
            binding.layoutProduct.visibility = View.GONE
            binding.layoutUser.visibility = View.VISIBLE
        }

//        setUpRecyclerView(response.data!!.data)

        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                requireActivity().finish()
            }
        }

        requireActivity().onBackPressedDispatcher.addCallback(callback)

    }

    private fun getBuyers() {

        viewModel.getBuyer()
        viewModel.buyers.observe(viewLifecycleOwner) { response ->
            when (response) {
                is Resource.Success -> {
                    Log.d("success", response.data!!.data[0].name)
                    setUpRecyclerView(response.data.data)
                }
                is Resource.Error -> {
                    Log.d("error", response.message.toString())
                }
                is Resource.Loading -> {
                    Log.d("loading", "in loading state")
                }
            }
        }

    }

    private fun setUpRecyclerView(data: List<DataBuyer>) {
        binding.rvUser.apply {
            sellerAdapter = SellerUserAdpater(data)
            adapter = sellerAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

}