package ercanduman.visualizerestaurant.ui.main

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import ercanduman.visualizerestaurant.R
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val viewModel: AppViewModel by viewModels()
    private lateinit var appAdapter: AppAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        appAdapter = AppAdapter()
        setupRecyclerView()

        loadData()
    }

    private fun loadData() = lifecycleScope.launch {
        viewModel.getRestaurants().observe(this@MainActivity, {
            appAdapter.submitList(it)
        })
    }

    private fun setupRecyclerView() = main_recycler_view_restaurants.apply {
        adapter = appAdapter
    }
}