package com.example.roorkeesevafpo.fragments.buyer

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.activity.OnBackPressedCallback
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.roorkeesevafpo.R
import com.example.roorkeesevafpo.repository.ShoppingRepository
import com.example.roorkeesevafpo.viewmodel.ShoppingViewModel
import com.example.roorkeesevafpo.viewmodel.ShoppingViewModelFactory
import com.example.roorkeesevafpo.adapters.ListAdapter
import com.example.roorkeesevafpo.databinding.FragmentListBinding
import com.example.roorkeesevafpo.db.ShoppingDatabase
import com.example.roorkeesevafpo.model.remote.DataProduct
import com.example.roorkeesevafpo.utils.Resource

class ListFragment : Fragment() {

    private lateinit var binding: FragmentListBinding
    private lateinit var listAdapter: ListAdapter
    private lateinit var addList: ArrayList<DataProduct>
    private lateinit var database: ShoppingDatabase
    private lateinit var repository: ShoppingRepository
    private lateinit var factory: ShoppingViewModelFactory
    private lateinit var viewModel: ShoppingViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        database = ShoppingDatabase(requireActivity())
        repository = ShoppingRepository(database)
        factory = ShoppingViewModelFactory(repository)
        viewModel = ViewModelProvider(this, factory)[ShoppingViewModel::class.java]

        binding.icBack.setOnClickListener {
            findNavController().navigateUp()
        }

        getProducts()
//        setUpSpinner()
//        setUpRecyclerView()
//        updateBottomBar(addList)

        val fpoList = arguments?.getStringArrayList("list")
        setUpSpinner(fpoList as MutableList<String>)

//        viewModel.getAllShoppingItems().observe(viewLifecycleOwner) { addList ->
//            "${addList.size} items".also { binding.itemsText.text = it }
//        }
//
//        viewModel.getTotalPrice().observe(viewLifecycleOwner) { total ->
//            "₹ $total".also { binding.tvTotalAmount.text = it }
//        }

        viewModel.totalItems.observe(viewLifecycleOwner){ totalItems ->
            "$totalItems items".also { binding.itemsText.text = it }
        }

        viewModel.totalPrice.observe(viewLifecycleOwner){ totalPrice ->
            "₹ $totalPrice".also { binding.tvTotalAmount.text = it }
        }

        binding.btnCart.setOnClickListener {
            var price = 0
            viewModel.totalPrice.observe(viewLifecycleOwner){ price = it }
            findNavController().navigate(R.id.action_listFragment_to_cartFragment, bundleOf("list" to addList, "totalPrice" to price))
        }

        binding.icSearch.setOnClickListener {
            findNavController().navigate(R.id.action_listFragment_to_searchFragment)
        }

        binding.spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
//                Toast.makeText(
//                    requireActivity(),
//                    "${parent!!.selectedItem} selected",
//                    Toast.LENGTH_SHORT
//                ).show()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}

        }

        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                findNavController().navigateUp()
            }
        }

        requireActivity().onBackPressedDispatcher.addCallback(callback)

    }

    private fun updateBottomBar(addList: ArrayList<DataProduct>) {

        viewModel.totalItems.observe(viewLifecycleOwner){ totalItems ->
            "$totalItems items".also { binding.itemsText.text = it }
        }

        viewModel.totalPrice.observe(viewLifecycleOwner){ totalPrice ->
            "₹ $totalPrice".also { binding.tvTotalAmount.text = it }
        }

        //Old code with database
//        "${addList.size} items".also { binding.itemsText.text = it }
//
//        var total = 0
//        for(items in addList) {
//            total += items.price!!
//        }
//
//        "₹ $total".also { binding.tvTotalAmount.text = it }

    }

    private fun getProducts(){
        viewModel.getProduct()
        viewModel.products.observe(viewLifecycleOwner) { response ->
            when (response) {
                is Resource.Success -> {
                    Log.d("success", response.data!!.data[0].product)
                    setRecyclerView(response.data.data)
                    updateBottomBar(addList)
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

    private fun setRecyclerView(data: List<DataProduct>) {

        binding.rvList.apply {
            listAdapter = ListAdapter(data, viewModel, viewLifecycleOwner)
            addList = listAdapter.addList
            adapter = listAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

    private fun setUpSpinner(data: MutableList<String>) {
//        val list = listOf("Vegetables", "Fruits", "Pulses", "Grains")
        binding.spinner.adapter = ArrayAdapter(requireContext(), R.layout.spinner_item, data)
    }

//    private fun setUpRecyclerView() {
//        binding.rvList.apply {
//            val list = listOf(
//                Item("Banana", 2, 0, 70, R.drawable.banana),
//                Item("Apple", 12, 0, 170, R.drawable.apple),
//                Item("Grapes", 12, 0, 10, R.drawable.grapes),
//                Item("Apple", 12, 0, 170, R.drawable.apple),
//                Item("Grapes", 12, 0, 10, R.drawable.grapes),
//                Item("Banana", 2, 0, 70, R.drawable.banana),
//                Item("Grapes", 12, 0, 10, R.drawable.grapes)
//            )
//            listAdapter = ListAdapter(list, viewModel)
//            addList = listAdapter.addList
//            adapter = listAdapter
//            layoutManager = LinearLayoutManager(requireContext())
//        }
//    }

}