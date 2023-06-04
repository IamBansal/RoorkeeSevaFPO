package com.example.roorkeesevafpo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.roorkeesevafpo.databinding.ActivityStartBinding

class StartActivity : AppCompatActivity() {

    private lateinit var binding: ActivityStartBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStartBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buyer.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }
        binding.seller.setOnClickListener {
            startActivity(Intent(this, MainActivity2::class.java))
        }

    }
}