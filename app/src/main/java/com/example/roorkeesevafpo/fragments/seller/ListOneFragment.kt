package com.example.roorkeesevafpo.fragments.seller

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.roorkeesevafpo.R
import com.example.roorkeesevafpo.adapters.SellerListAdapter
import com.example.roorkeesevafpo.databinding.FragmentListOneBinding
import com.example.roorkeesevafpo.model.local.Item

class ListOneFragment : Fragment() {

    private lateinit var binding: FragmentListOneBinding
    private lateinit var sellerAdapter: SellerListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentListOneBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.icBack.setOnClickListener {
            findNavController().navigateUp()
        }

        binding.icSearch.setOnClickListener {
            findNavController().navigate(R.id.action_listOneFragment_to_searchFragment)
        }
        setUpRecyclerView()

        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                findNavController().navigateUp()
            }
        }

        requireActivity().onBackPressedDispatcher.addCallback(callback)
    }

    private fun setUpRecyclerView() {
        binding.rvList.apply {
            val list = listOf(
                Item("Banana", 2, 0, 0, R.drawable.banana),
                Item("Apple", 12, 0, 0, R.drawable.apple),
                Item("Grapes", 12, 0, 0, R.drawable.grapes),
            )
            sellerAdapter = SellerListAdapter(list)
            adapter = sellerAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

}