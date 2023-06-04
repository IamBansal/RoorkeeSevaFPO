package com.example.roorkeesevafpo.fragments.buyer

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.roorkeesevafpo.R
import com.example.roorkeesevafpo.adapters.FPOAdapter
import com.example.roorkeesevafpo.databinding.FragmentFPOBinding
import com.example.roorkeesevafpo.db.ShoppingDatabase
import com.example.roorkeesevafpo.repository.ShoppingRepository
import com.example.roorkeesevafpo.utils.Resource
import com.example.roorkeesevafpo.viewmodel.ShoppingViewModel
import com.example.roorkeesevafpo.viewmodel.ShoppingViewModelFactory

class FPOFragment : Fragment() {

    private lateinit var binding: FragmentFPOBinding
    private lateinit var database: ShoppingDatabase
    private lateinit var repository: ShoppingRepository
    private lateinit var factory: ShoppingViewModelFactory
    private lateinit var viewModel: ShoppingViewModel
    private lateinit var fpoAdapter: FPOAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFPOBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        database = ShoppingDatabase(requireActivity())
        repository = ShoppingRepository(database)
        factory = ShoppingViewModelFactory(repository)
        viewModel = ViewModelProvider(this, factory)[ShoppingViewModel::class.java]

        getFpo()

//        binding.cardPulse.setOnClickListener {
//            findNavController().navigate(R.id.action_fpoFragment_to_listFragment)
//        }
//
//        binding.cardFruits.setOnClickListener {
//            findNavController().navigate(R.id.action_fpoFragment_to_listFragment)
//        }
//
//        binding.cardGrain.setOnClickListener {
//            findNavController().navigate(R.id.action_fpoFragment_to_listFragment)
//        }
//
//        binding.cardVegetables.setOnClickListener {
//            findNavController().navigate(R.id.action_fpoFragment_to_listFragment)
//        }

        binding.icCart.setOnClickListener {
            findNavController().navigate(R.id.action_fpoFragment_to_cartFragment)
        }

        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                requireActivity().finish()
            }
        }

        requireActivity().onBackPressedDispatcher.addCallback(callback)

    }

    private fun getFpo(){
        viewModel.getFpo()
        viewModel.fpo.observe(viewLifecycleOwner) { response ->
            when (response) {
                is Resource.Success -> {
                    Log.d("success", response.data!!.data[0].product_items[0])
                    setRecyclerView(response.data.data[0].product_items)
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

    private fun setRecyclerView(productItems: List<String>) {
        binding.rvFpo.apply {
            fpoAdapter = FPOAdapter(productItems as ArrayList<String>)
            adapter = fpoAdapter
            layoutManager = GridLayoutManager(context, 2)
        }
    }

}