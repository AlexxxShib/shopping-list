package com.example.shoppinglist.presentation

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModelProvider
import com.example.shoppinglist.databinding.ActivityMainBinding
import com.example.shoppinglist.ui.theme.ShoppingListTheme

class MainActivity : ComponentActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel

    private var shopListAdapter = ShopListAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerView()

        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        viewModel.shopList.observe(this) {
            Log.d("TEST", it.toString())

            shopListAdapter.shopList = it
        }
    }

    private fun setupRecyclerView() {
        with(binding.shopList) {
            adapter = shopListAdapter
            recycledViewPool.setMaxRecycledViews(ShopListAdapter.VIEW_TYPE_ENABLED, ShopListAdapter.POOL_SIZE)
            recycledViewPool.setMaxRecycledViews(ShopListAdapter.VIEW_TYPE_DISABLED, ShopListAdapter.POOL_SIZE)
        }
    }
}