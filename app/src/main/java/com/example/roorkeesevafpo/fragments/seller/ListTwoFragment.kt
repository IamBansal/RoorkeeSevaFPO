package com.example.roorkeesevafpo.fragments.seller

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.roorkeesevafpo.R
import com.example.roorkeesevafpo.adapters.ListTwoAdapter
import com.example.roorkeesevafpo.databinding.FragmentListTwoBinding
import com.example.roorkeesevafpo.model.local.Item

class ListTwoFragment : Fragment() {

    private lateinit var binding: FragmentListTwoBinding
    private lateinit var listTwoAdapter: ListTwoAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentListTwoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpRecyclerView()
        binding.icBack.setOnClickListener {
            findNavController().navigateUp()
        }
        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                findNavController().navigateUp()
            }
        }

        requireActivity().onBackPressedDispatcher.addCallback(callback)
    }

    private fun setUpRecyclerView() {
        binding.rvItems.apply {
            val list = listOf(
                Item("Banana", 2, 0, 0, R.drawable.banana),
                Item("Apple", 12, 0, 0, R.drawable.apple),
                Item("Grapes", 12, 0, 0, R.drawable.grapes),
            )
            listTwoAdapter = ListTwoAdapter(list)
            adapter = listTwoAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }
}