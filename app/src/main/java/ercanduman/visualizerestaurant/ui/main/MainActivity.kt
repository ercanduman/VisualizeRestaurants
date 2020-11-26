package ercanduman.visualizerestaurant.ui.main

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import ercanduman.visualizerestaurant.R
import ercanduman.visualizerestaurant.ui.utils.logd
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val viewModel: AppViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        lifecycleScope.launch {
            viewModel.getRestaurants().observe(this@MainActivity, {
                logd("Retrieved restaurants from local data source size: ${it.size}")
                logd(it.toString())
            })
        }
    }
}