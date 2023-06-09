package com.example.roorkeesevafpo.fragments.buyer

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context.NOTIFICATION_SERVICE
import android.content.pm.PackageManager
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.roorkeesevafpo.R
import com.example.roorkeesevafpo.adapters.CartAdapter
import com.example.roorkeesevafpo.databinding.FragmentCartBinding
import com.example.roorkeesevafpo.db.ShoppingDatabase
import com.example.roorkeesevafpo.model.local.Item
import com.example.roorkeesevafpo.model.remote.DataProduct
import com.example.roorkeesevafpo.repository.ShoppingRepository
import com.example.roorkeesevafpo.viewmodel.ShoppingViewModel
import com.example.roorkeesevafpo.viewmodel.ShoppingViewModelFactory

class CartFragment : Fragment() {

    private lateinit var binding: FragmentCartBinding
    private lateinit var cartAdapter: CartAdapter
    private lateinit var database: ShoppingDatabase
    private lateinit var repository: ShoppingRepository
    private lateinit var factory: ShoppingViewModelFactory
    private lateinit var viewModel: ShoppingViewModel

    private val CHANNEL_ID = "channelID"
    private val CHANNEL_NAME = "channelName"
    private lateinit var requestLauncher: ActivityResultLauncher<String>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCartBinding.inflate(inflater, container, false)
        return binding.root
    }


    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        database = ShoppingDatabase(requireActivity())
        repository = ShoppingRepository(database)
        factory = ShoppingViewModelFactory(repository)
        viewModel = ViewModelProvider(this, factory)[ShoppingViewModel::class.java]

//        val list = arguments?.getParcelableArrayList("list", DataProduct::class.java)


        "₹ ${arguments?.getInt("totalPrice")}".also { binding.tvTotalCharges.text = it }
        "₹ ${arguments?.getInt("totalPrice")}".also { binding.tvPrice.text = it }

        viewModel.getAllShoppingItems().observe(viewLifecycleOwner) {
            setUpRecyclerView(it)
        }

//        viewModel.getTotalPrice().observe(viewLifecycleOwner) { total ->
//            "₹ $total".also { binding.tvTotalCharges.text = it }
//            "₹ $total".also { binding.tvPrice.text = it }
//        }

        binding.icBack.setOnClickListener {
            findNavController().navigateUp()
        }

        binding.btnPlaceOrder.setOnClickListener {

//            val name = binding.etName.text.toString().trim()
//            val address = binding.etAddress.text.toString().trim()
//            val pinCode = binding.etPinCode.text.toString().trim()
//            val number = binding.etNumber.text.toString().trim()
//
//            if (TextUtils.isEmpty(name) || TextUtils.isEmpty(address) ||TextUtils.isEmpty(pinCode) ||TextUtils.isEmpty(number)){
//                Toast.makeText(requireContext(), "Fill the details first", Toast.LENGTH_SHORT).show()
//            } else {
//                placeOrderToDb()
//                notifySeller()
//            }

            placeOrderToDb()
            notifySeller()
        }


        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                findNavController().navigateUp()
            }
        }

        requireActivity().onBackPressedDispatcher.addCallback(callback)


        requestLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()) {
            if (it) createNotificationChannel()
            else Toast.makeText(requireContext(), "Permission not granted", Toast.LENGTH_SHORT)
                .show()
        }
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    private fun notifySeller() {

        //TODO - Only send to the seller

        when {
            ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.POST_NOTIFICATIONS
            ) ==
                    PackageManager.PERMISSION_GRANTED -> {
                createNotificationChannel()
            }
            shouldShowRequestPermissionRationale(Manifest.permission.POST_NOTIFICATIONS) -> {

            }
            else -> {
                requestLauncher.launch(
                    Manifest.permission.POST_NOTIFICATIONS
                )
            }
        }
    }

    private fun placeOrderToDb() {
        //TODO("Not yet implemented")
    }

    private fun setUpRecyclerView(list: List<Item>) {
        binding.rvItems.apply {
            cartAdapter = CartAdapter(list)
            adapter = cartAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

    private fun setRecyclerView(list: List<DataProduct>) {
        binding.rvItems.apply {

            val listNew = ArrayList<Item>()

            for (items in list){
                listNew.add(Item(items.product, items.current_quantity.toInt(), items.quantity_added.toInt(), items.selling_price.toInt(), items.multiple_photo_link[0].toString().toInt()))
            }

            cartAdapter = CartAdapter(listNew)
            adapter = cartAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

    private fun createNotificationChannel() {

        val manager =
            requireActivity().getSystemService(NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val Channel = NotificationChannel(
                CHANNEL_ID,
                CHANNEL_NAME,
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                lightColor = Color.BLUE
                enableLights(true)
            }
            manager.createNotificationChannel(Channel)
        }
        manager.cancelAll()

        val notify = NotificationCompat.Builder(requireContext(), CHANNEL_ID)
            .setContentTitle("New Order")
            .setContentText("New order placed for you.")
            .setSmallIcon(R.drawable.fpo)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .build()

        manager.notify(0, notify)
    }
}