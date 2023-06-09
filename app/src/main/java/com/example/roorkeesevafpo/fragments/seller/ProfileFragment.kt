package com.example.roorkeesevafpo.fragments.seller

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.roorkeesevafpo.R
import com.example.roorkeesevafpo.adapters.CartAdapter
import com.example.roorkeesevafpo.databinding.FragmentProfileBinding
import com.example.roorkeesevafpo.model.local.Item

class ProfileFragment : Fragment() {

    private lateinit var binding: FragmentProfileBinding
    private lateinit var cartAdapter: CartAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.tvName.text = arguments?.getString("name")
        binding.tvNumber.text = arguments?.getString("num")
        binding.tvAddress.text = arguments?.getString("address")
        Glide.with(requireContext()).load(arguments?.getString("photo")).into(binding.ivPhoto)

        binding.icBack.setOnClickListener {
            findNavController().navigateUp()
        }

        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                findNavController().navigateUp()
            }
        }

        setUpRecyclerView()
        requireActivity().onBackPressedDispatcher.addCallback(callback)
    }

    private fun setUpRecyclerView() {

//        if (arguments?.getStringArrayList("cart")!!.size == 0){
//
//        }

        binding.rvItems.apply {
            val list = listOf(
                Item("Banana", 2, 0, 70, R.drawable.banana),
                Item("Banana", 2, 0, 70, R.drawable.banana),
                Item("Banana", 12, 0, 210, R.drawable.grapes)
            )
            cartAdapter = CartAdapter(list)
            adapter = cartAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

}